import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;


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


    // for Hent treningsokt

    public Workout(String dato, Time starttidspunkt, String type) {
        this.dato = dato;
        this.starttidspunkt = starttidspunkt;
        this.type = type;
    }

    //for lag treningsokt Utendors

    public Workout(String dato, Time startTidspunkt, int varighet,int personligform,int prestasjon,String formal_tips,String type,int temperatur,String værForhold) {
        this.dato = dato;
        this.starttidspunkt = startTidspunkt;
        this.type = type;
        this.varighet = varighet;
        this.personligform = personligform;
        this.prestasjon = prestasjon;
        this.formal_tips = formal_tips;
        this.temperatur = temperatur;
        this.værForhold = værForhold;
    }

        // for lag treningsokt Innendors


        public Workout(String dato,Time starttidspunkt, int varighet, int personligform, int prestasjon, String formal_tips, String type,int luft_ventilasjon, int antTilskuere) {
            this.dato = dato;
            this.varighet = varighet;
            this.personligform = personligform;
            this.prestasjon = prestasjon;
            this.starttidspunkt = starttidspunkt;
            this.formal_tips = formal_tips;
            this.luft_ventilasjon = luft_ventilasjon;
            this.antTilskuere = antTilskuere;
            this.type = type;
        }

    public void hentTreningsokt() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Treningsokt where dato='" + dato + "' and starttidspunkt='" + starttidspunkt + "';");

            while (rs.next()) {
                this.personligform = rs.getInt("form");
                this.varighet = rs.getInt("varighet");
                this.prestasjon = rs.getInt("prestasjon");

            }
            switch (type){
                case "innendors":
                    rs=stmt.executeQuery("SELECT luftVent,antallTilskuere FROM Innendors WHERE dato ='"+dato+"' AND starttidspunkt='"+starttidspunkt+"';");
                    while (rs.next()){
                        this.luft_ventilasjon=rs.getInt("luftVent");
                        this.antTilskuere=rs.getInt("antallTilskuere");

                    }
                    break;

                case "utendors":
                    rs=stmt.executeQuery("SELECT temp,vaertype FROM Utendors WHERE dato ='"+dato+"' AND starttidspunkt='"+starttidspunkt+"';");
                    while (rs.next()) {
                        this.temperatur=rs.getInt("temp");
                        this.værForhold=rs.getString("vaertype");

                    }
                    break;

                default:
                    System.out.println(type + " is no valid type!");

            }
            // Lag en toString metode og kjør den her TODO
            System.out.println("Treningsøkt hentet\n" + "--------------------------------\n" + this.toString());

        } catch (Exception e) {
            System.out.println("db error during select of treningsøkt= " + e);

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
                    stmt.executeUpdate("INSERT INTO Innendors()" + "VALUES ('"+luft_ventilasjon+"','"+antTilskuere+"','"+dato+"','"+starttidspunkt+"')");
                    break;
                case "utendors":
                    stmt.executeUpdate("INSERT INTO Utendors()"+"VALUES ('"+temperatur+"','"+værForhold+"','"+dato+"','"+starttidspunkt+"')");
                    break;
                default:
                    System.out.println(type + " is no valid type!");
            }


        } catch (Exception e) {
            System.out.println("db error during insert of Treningsøkt: " + e);
//            throw new Exception();
        }
    }


    public String getDato() {
        return dato;
    }

    public void setDate(String dato) {
        this.dato = dato;
    }

    public int getVarighet() {
        return varighet;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }

    public int getPersonligform() {
        return personligform;
    }

    public void setPersonligform(int personligform) {
        this.personligform = personligform;
    }

    public int getPrestasjon() {
        return prestasjon;
    }

    public void setPrestasjon(int prestasjon) {
        this.prestasjon = prestasjon;
    }

    public Time getStartTidspunkt() {
        return starttidspunkt;
    }

    public void setStartTidspunkt(Time startTidspunkt) {
        this.starttidspunkt = startTidspunkt;
    }

    public String getMal() {
        return mal;
    }

    public void setMal(String mal) {
        this.mal = mal;
    }

    public String getMaal() {
        return maal;
    }

    public void setMaal(String maal) {
        this.maal = maal;
    }

    public String getFormal() {
        return formal_tips;
    }

    public void setFormal(String formal_tips) {
        this.formal_tips = formal_tips;
    }

    public int getLuft() {
        return luft_ventilasjon;
    }

    public void setLuft(int luft_ventilasjon) {
        this.luft_ventilasjon = luft_ventilasjon;
    }

    public int getAntTilskuere() {
        return antTilskuere;
    }

    public void setAntTilskuere(int antTilskuere) {
        this.antTilskuere = antTilskuere;
    }

    public int getTemperatur() {
        return temperatur;
    }

    public void setTemperatur(int temperatur) {
        this.temperatur = temperatur;
    }

    public String getVærForhold() {
        return værForhold;
    }

    public void setVærForhold(String værForhold) {
        this.værForhold = værForhold;
    }

    private String getType(){ return this.type; }

    @Override
    public String toString() {
        switch (this.type){
            case "innendors":
                return ("Type treningsøkt: " + this.getType() + "\nDato: " + this.getDato() + "\nStarttidspunkt: " +
                        this.getStartTidspunkt() + "\nVarighet: " + this.getVarighet() + "\nPersonlig form: " +
                        this.getPersonligform() + "\nPrestasjon: " + this.getPrestasjon() + "\nFormål/Tips: " +
                        this.getFormal() + "\nVentelesajon: " + this.getLuft() + "\nAntall tilskuere: " + this.getAntTilskuere());
            case "utendors":
                return ("Type treningsøkt: " + this.getType() + "Dato: " + this.getDato() + "\nStarttidspunkt: " +
                        this.getStartTidspunkt() + "\nVarighet: " + this.getVarighet() + "\nPersonlig form: " +
                        this.getPersonligform() + "\nPrestasjon: " + this.getPrestasjon() + "\nFormål/Tips: " +
                        this.getFormal() + "\nTemperatur: " + this.getTemperatur() + "\nVær forhold: " + this.getVærForhold());
            default:
                return "No data";
        }
    }

        public static void main(String[] args) throws Exception{
//
          Time starttidspunkt=new Time(12,55,00);
//        Workout workout=new Workout("2017-03-14",starttidspunkt,200,6,6,"","innendors",2,0);
            Workout workout=new Workout("2017-03-14",starttidspunkt,"innendors");
            workout.connect();
//
//        workout.lagTreningsokt();
//
            workout.hentTreningsokt();
////        System.out.println(workout.getLuft());
//
//
    }

}