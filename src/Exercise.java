import java.sql.ResultSet;
import java.sql.Statement;

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

    public Exercise(int ovelseId, String type){
        this.ovelseId = ovelseId;
        this.type = type;
    }

    public void hentOvelse() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet res;
            switch (type) {
                case "styrke":
                case "kondisjon":
                    res = stmt.executeQuery("SELECT navn, beskrivelse, belastning, antallRep, antallSet " +
                                                 "FROM Ovelser NATURAL JOIN StyrkeKondisjon " +
                                                 "WHERE ovelseId = " + ovelseId + ";");
                    while (res.next()) {
                        this.navn = res.getString("navn");
                        this.beskrivelse = res.getString("beskrivelse");
                        this.belastning = res.getInt("belastning");
                        this.antallRep = res.getInt("antallRep");
                        this.antallSet = res.getInt("antallSet");
                    }
                    break;
                case "utholdenhet":
                    res = stmt.executeQuery("SELECT navn, beskrivelse, varighet, distanse " +
                                                 "FROM Ovelser NATURAL JOIN StyrkeKondisjon " +
                                                 "WHERE ovelseId = " + ovelseId + ";");
                    while (res.next()) {
                        this.varighet = res.getInt("varighet");
                        this.distanse = res.getInt("distanse");
                    }
                    break;
                default:
                    System.out.println(type + " is no valid type!");
                    return;
            }
        } catch (Exception e) {
            System.out.println("db error during select of ovelseId= "+e);
            return;
        }

    }

    public void lagOvelse () throws Exception {
        try {
            Statement stmt = conn.createStatement();
            ovelseId = stmt.executeUpdate("INSERT INTO Ovelse(navn, beskrivelse) " +
                                               "VALUES('" + navn + "'," + beskrivelse + ");", Statement.RETURN_GENERATED_KEYS);
            switch (type) {
                case "styrke":
                case "kondisjon":
                    stmt.executeQuery("INSERT INTO StyrkeKondisjon " +
                                           "VALUES('" + belastning + "'," + antallRep + "," + antallSet + "," + ovelseId + ");");
                    break;
                case "utholdenhet":
                    stmt.executeQuery("INSERT INTO Utholdenhet " +
                            "VALUES('" + varighet + "'," + distanse + "," + ovelseId + ");");
                    break;
                default:
                    System.out.println(type + " is no valid type!");
                    return;
            }
        }
        catch (Exception e) {
            System.out.println("db error during insert of ovelseId="+e);
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
}