import java.sql.*;


public class Workout extends Connector {
    private String dato;
    private int varighet;
    private int personligform;
    private int prestasjon;
    private Time starttidspunkt;
    private String mal;
    private String maal;
    private String formal_tips;
    private int luft_ventilasjon;
    private int antTilskuere;
    private int temperatur;
    private String værForhold;
    private String type;

    // For hentTreningsokt
    public Workout(String dato, Time starttidspunkt, String type) {
        this.dato = dato;
        this.starttidspunkt = starttidspunkt;
        this.type = type;
    }

    // For lagTreningsokt, utendors
    public Workout(String dato, Time startTidspunkt, int varighet, int personligform, int prestasjon, String formal_tips,
                   String type, int temperatur, String værForhold) {
        this(dato, startTidspunkt, type);
        this.varighet = varighet;
        this.personligform = personligform;
        this.prestasjon = prestasjon;
        this.formal_tips = formal_tips;
        this.temperatur = temperatur;
        this.værForhold = værForhold;
    }

    // Forlag lagTreningsokt, innendors
    public Workout(String dato, Time starttidspunkt, int varighet, int personligform, int prestasjon, String formal_tips,
                   String type, int luft_ventilasjon, int antTilskuere) {
        this(dato, starttidspunkt, type);
        this.varighet = varighet;
        this.personligform = personligform;
        this.prestasjon = prestasjon;
        this.formal_tips = formal_tips;
        this.luft_ventilasjon = luft_ventilasjon;
        this.antTilskuere = antTilskuere;
    }

    public void hentTreningsokt() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Treningsokt " +
                                                  "WHERE dato='" + dato +
                                                  "' AND starttidspunkt='" + starttidspunkt + "';");
            while (rs.next()) {
                this.personligform = rs.getInt("form");
                this.varighet = rs.getInt("varighet");
                this.prestasjon = rs.getInt("prestasjon");
            }
            switch (type){
                case "innendors":
                    rs=stmt.executeQuery("SELECT luftVent,antallTilskuere FROM Innendors " +
                                              "WHERE dato ='" + dato + "' AND starttidspunkt='" + starttidspunkt + "';");
                    while (rs.next()){
                        this.luft_ventilasjon=rs.getInt("luftVent");
                        this.antTilskuere=rs.getInt("antallTilskuere");
                    }
                    break;
                case "utendors":
                    rs=stmt.executeQuery("SELECT temp,vaertype FROM Utendors " +
                                              "WHERE dato ='" + dato + "' AND starttidspunkt='" + starttidspunkt + "';");
                    while (rs.next()) {
                        this.temperatur=rs.getInt("temp");
                        this.værForhold=rs.getString("vaertype");
                    }
                    break;
                default:
                    System.out.println(type + " is no valid type!");
            }
            // Skriv ut resultat
            System.out.println(this.toString());
        } catch (Exception e) {
            System.out.println("db error during select of treningsøkt: " + e);
        }
    }

    public void lagTreningsokt() throws Exception {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Treningsokt " + "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, dato);
            stmt.setTime(2, starttidspunkt);
            stmt.setInt(3, varighet);
            stmt.setInt(4, personligform);
            stmt.setInt(5, prestasjon);
            if(!formal_tips.equals("")){
                stmt.setString(6, formal_tips);
            }else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.executeUpdate();
            switch (type) {
                case "innendors":
                    stmt.executeUpdate("INSERT INTO Innendors() VALUES ('" + luft_ventilasjon +
                                            "','" + antTilskuere + "','" + dato + "','" + starttidspunkt + "')");
                    break;
                case "utendors":
                    stmt.executeUpdate("INSERT INTO Utendors() VALUES ('" + temperatur + "','" + værForhold +
                                            "','" + dato + "','" + starttidspunkt + "')");
                    break;
                default:
                    System.out.println(type + " is no valid type!");
            }
        } catch (Exception e) {
            System.out.println("db error during insert of Treningsøkt: " + e);
        }
    }

    public String getDato() {
        return dato;
    }

    public int getVarighet() {
        return varighet;
    }

    public int getPersonligform() {
        return personligform;
    }

    public int getPrestasjon() {
        return prestasjon;
    }

    public Time getStartTidspunkt() {
        return starttidspunkt;
    }

    public String getMal() {
        return mal;
    }

    public String getMaal() {
        return maal;
    }

    public String getFormal() {
        return formal_tips;
    }

    public int getLuft() {
        return luft_ventilasjon;
    }

    public int getAntTilskuere() {
        return antTilskuere;
    }

    public int getTemperatur() {
        return temperatur;
    }

    public String getVærForhold() {
        return værForhold;
    }

    public String getType(){
        return this.type;
    }

    @Override
    public String toString() {
        String s = "Treningsøkt hentet\n" + "--------------------------------\n" +
                "Type treningsøkt: " + this.getType() + "Dato: " + this.getDato() + "\nStarttidspunkt: " +
                this.getStartTidspunkt() + "\nVarighet: " + this.getVarighet() + "\nPersonlig form: " +
                this.getPersonligform() + "\nPrestasjon: " + this.getPrestasjon() + "\nFormål/Tips: " + this.getFormal();
        switch (this.type){
            case "innendors":
                return s + "\nVentelesajon: " + this.getLuft() + "\nAntall tilskuere: " + this.getAntTilskuere();
            case "utendors":
                return s + "\nTemperatur: " + this.getTemperatur() + "\nVær forhold: " + this.getVærForhold();
            default:
                return "No data";
        }
    }
}