import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.Date;


public class Result extends Connector {

    private int belastning;
    private int antallRep;
    private int antallSet;
    private int varighet;
    private int distanse;
    private int ovelseId;
    private Date dato;
    private Time starttidspunkt;
    private String type;

    public Result(int ovelseId, String type, Date dato, Time starttidspunkt) {
        this.ovelseId = ovelseId;
        this.type = type;
        this.dato = dato;
        this.starttidspunkt = starttidspunkt;
    }


    public void hentResultat() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet res;
            switch (type) {
                case "styrke":
                case "kondisjon":
                    res = stmt.executeQuery("select belastning, antallRep, antallSet from Resultat " +
                                                 "where ovelseId=" + ovelseId + " and dato='" + dato +
                                                 "'and starttidspunkt=" + starttidspunkt + ";");
                    while (res.next()) {
                        this.belastning = res.getInt("belastning");
                        this.antallRep = res.getInt("antallRep");
                        this.antallSet = res.getInt("antallSet");
                    }
                    break;
                case "utholdenhet":
                    res = stmt.executeQuery("select varighet, distanse from Resultat " +
                                                 "where ovelseId=" + ovelseId + " and dato='" + dato +
                                                 "'and starttidspunkt=" + starttidspunkt + ";");
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
            System.out.println("db error during select of Resultat= " + e);
            return;
        }

    }

    public void lagResultat() {
        try {
            Statement stmt = conn.createStatement();
            switch (type) {
                case "styrke":
                case "kondisjon":
                    stmt.executeUpdate("INSERT INTO Resultat (belastning, antallRep, antallSet, dato, starttidspunkt, ovelseId) " +
                            "VALUES ('" + belastning + "'," + antallRep + ",'" + antallSet + "'," + dato +
                            "," + starttidspunkt + "," + ovelseId + ");");
                    break;
                case "utholdenhet":
                    stmt.executeUpdate("INSERT INTO Resultat (varighet, distanse, dato, starttidspunkt, ovelseId) " +
                            "VALUES ('" + varighet + "'," + distanse + "," + dato + "," + starttidspunkt +
                            "," + ovelseId + ");");
                    break;
                default:
                    System.out.println(type + " is no valid type!");
                    return;
            }
        } catch (Exception e) {
            System.out.println("db error during insert of resultat=" + e);
            return;
        }
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

    public int getOvelseId() {
        return ovelseId;
    }

    public void setOvelseId(int ovelseId) {
        this.ovelseId = ovelseId;
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
}