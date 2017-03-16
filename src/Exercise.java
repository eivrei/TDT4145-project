import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

public class Exercise extends Connector{
    private int ovelseId;
    private String navn;
    private String beskrivelse;
    private int belastning;
    private int antallRep;
    private int antallSet;
    private int varighet;
    private int distanse;
    private String øvelsesGruppe;
    private String type;

    // For hentOvelse
    public Exercise(int ovelseId, String type){
        this.ovelseId = ovelseId;
        this.type = type;
    }

    // For lagOvelse, styrke/kondisjon
    public Exercise(String type, String navn, String beskrivelse, int belastning, int antallRep, int antallSet){
        this.type = type;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.belastning = belastning;
        this.antallRep = antallRep;
        this.antallSet = antallSet;
    }

    // For lagOvelse, utholdenhet
    public Exercise(String type, String navn, String beskrivelse, int varighet, int distanse){
        this.type = type;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.varighet = varighet;
        this.distanse = distanse;
    }

    public void hentOvelse() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet res;
            switch (type) {
                case "styrke":
                case "kondisjon":
                    res = stmt.executeQuery("SELECT navn, beskrivelse, belastning, antallRep, antallSett " +
                                                 "FROM Ovelser JOIN StyrkeKondisjon ON id = ovelseId " +
                                                 "WHERE ovelseId = '" + ovelseId + "';");
                    if (res.next()) {
                        this.navn = res.getString("navn");
                        this.beskrivelse = res.getString("beskrivelse");
                        this.belastning = res.getInt("belastning");
                        this.antallRep = res.getInt("antallRep");
                        this.antallSet = res.getInt("antallSett");
                    }
                    break;
                case "utholdenhet":
                    res = stmt.executeQuery("SELECT navn, beskrivelse, varighet, distanse " +
                                                 "FROM Ovelser JOIN Utholdenhet ON id = ovelseId " +
                                                 "WHERE ovelseId = '" + ovelseId + "';");
                    if (res.next()) {
                        this.navn = res.getString("navn");
                        this.beskrivelse = res.getString("beskrivelse");
                        this.varighet = res.getInt("varighet");
                        this.distanse = res.getInt("distanse");
                    }
                    break;
                default:
                    System.out.println(type + " is no valid type!");
            }
            // Skriv ut resultat
            System.out.println(this.toString());
        } catch (Exception e) {
            System.out.println("db error during select of ovelse: " + e);
        }
    }

    public void lagOvelse() throws Exception {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO Ovelser (navn, beskrivelse) VALUES('" + navn + "','" + beskrivelse + "');", Statement.RETURN_GENERATED_KEYS);

            // Get ovelseId from last insertion
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                ovelseId=rs.getInt(1);
            }
            rs.close();

            switch (type) {
                case "styrke":
                case "kondisjon":
                    stmt.executeUpdate("INSERT INTO StyrkeKondisjon " +
                                           "VALUES('" + belastning + "','" + antallRep + "','" + antallSet + "','" +
                                            ovelseId + "');");
                    break;
                case "utholdenhet":
                    PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Utholdenhet VALUES (?, ?, ?)");

                    // Check if values 'varighet' and 'distanse' should be NULL in the database
                    if (varighet!= 0){
                        pstmt.setInt(1, varighet);
                    }else {
                        pstmt.setNull(1, Types.INTEGER);
                    }
                    if (distanse != 0){
                        pstmt.setInt(2, distanse);
                    }else {
                        pstmt.setNull(2, Types.INTEGER);
                    }
                    pstmt.setInt(3, ovelseId);
                    pstmt.executeUpdate();
                    break;
                default:
                    System.out.println(type + " is no valid type!");
            }
        }
        catch (Exception e) {
            System.out.println("db error during insert of ovelse: " + e);
            throw new Exception();
        }
    }

    public int getOvelseId() {
        return ovelseId;
    }

    public String getNavn() {
        return navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public int getBelastning() {
        return belastning;
    }

    public int getAntallRep() {
        return antallRep;
    }

    public int getAntallSet() {
        return antallSet;
    }

    public int getVarighet() {
        return varighet;
    }

    public int getDistanse() {
        return distanse;
    }

    public String getØvelsesGruppe() {
        return øvelsesGruppe;
    }

    private String getType() {return this.type; }

    @Override
    public String toString() {
        String s = "Øvelse hentet\n" + "---------------------------------\n" +
                   "Navn: " + this.getNavn() + "\nType øvelse: " + this.getType() + "\nBeskrivelse: " +
                   this.getBeskrivelse();
        switch (this.type){
            case "styrke":
            case "kondisjon":
                return s + "Belastning: " + this.getBelastning() + "\nAntall Repetisjoner: " + this.getAntallRep() +
                       "\nAntall Sett: " + this.getAntallSet();
            case "utholdenhet":
                return s + this.getBeskrivelse() + "\nVarighet: " + this.getVarighet() +
                       "\nDistanse: " + this.getDistanse();
            default:
                return "No data";
        }
    }
}