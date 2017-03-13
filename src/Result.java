
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Date;

public class Result {

    private int belastning;
    private int antallRep;
    private int antallSet;
    private int varighet;
    private int distanse;
    private String øvelse;
    private String kommentar;
    private Date dato;

    public Result(String øvelse) {
        this.øvelse = øvelse;
    }


    public void initialize(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery("select kommentar from Resultat where øvelse=" + øvelse + " and dato='" + dato + "';");
            while (res.next()) {
                this.kommentar = res.getString("kommentar");
            }


        } catch (Exception e) {
            System.out.println("db error during select of Resultat= " + e);
            return;
        }

    }

    public void refresh(Connection conn) {
        initialize(conn);
    }

    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            //kan bygge opp string i forkant for å lage en 'modulær' string
            stmt.executeUpdate("insert into Resultat values ('" + dato + "'," + øvelse + ",'" + kommentar + "','" + belastning + "'," + antallRep + "," + antallSet  + ",'" + varighet + "');");
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

    public String getØvelse() {
        return øvelse;
    }

    public void setØvelse(String øvelse) {
        this.øvelse = øvelse;
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