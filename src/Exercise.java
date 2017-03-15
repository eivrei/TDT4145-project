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

    // For lagStyrkeOvelse
    public Exercise(String type, String navn, String beskrivelse, int belastning, int antallRep, int antallSet){
        this.type = type;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        this.belastning = belastning;
        this.antallRep = antallRep;
        this.antallSet = antallSet;
        this.varighet = varighet;
        this.distanse = distanse;
    }

    // For lagUtholdenhetsOvelse
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
                                                 "FROM Ovelser NATURAL JOIN StyrkeKondisjon " +
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
                                                 "FROM Ovelser NATURAL JOIN Utholdenhet " +
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
            // lag toString og kjør den her!!! TODO
            System.out.println(getNavn());

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
                                           "VALUES('" + belastning + "','" + antallRep + "','" + antallSet + "','"+ ovelseId + "');");
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

    public int getØvelses() {
        return ovelseId;
    }

    public void setOvelseId(int øvelsesId) {
        this.ovelseId = øvelsesId;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public int getBelastning() {
        return belastning;
    }

    public void setBelastning(int belastning) {
        this.belastning = belastning;
    }

    public int getAntallRep() {
        return antallRep;
    }

    public void setAntallRep(int antallRep) {
        this.antallRep = antallRep;
    }

    public int getAntallSet() {
        return antallSet;
    }

    public void setAntallSet(int antallSet) {
        this.antallSet = antallSet;
    }

    public int getVarighet() {
        return varighet;
    }

    public void setVarighet(int varighet) {
        this.varighet = varighet;
    }

    public int getDistanse() {
        return distanse;
    }

    public void setDistanse(int distanse) {
        this.distanse = distanse;
    }

    public String getØvelsesGruppe() {
        return øvelsesGruppe;
    }

    public void setØvelsesGruppe(String øvelsesGruppe) {
        this.øvelsesGruppe = øvelsesGruppe;
    }

    public static void main(String[] args) throws Exception{
//        Exercise ex = new Exercise("styrke", "Benkpress", "Vanlig benkpress på benk", 20, 10, 4);
//        Exercise ex = new Exercise("utholdenhet", "3000 meter", "Løpe 3000 meter på bane", 0, 3000);
        Exercise ex = new Exercise(27, "utholdenhet");
        ex.connect();
//        ex.lagOvelse();
        ex.hentOvelse();
        System.out.println("navn: " + ex.getNavn() + ", varighet: " + ex.getVarighet() + ", distanse: " + ex.getDistanse());
    }
}