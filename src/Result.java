import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Time;


public class Result extends Connector {
    private int belastning;
    private int antallRep;
    private int antallSett;
    private int varighet;
    private int distanse;
    private int ovelseId;
    private String dato;
    private Time starttidspunkt;
    private String type;
    private String ovelseNavn;

    // For henteResultat
    public Result(int ovelseId, String type, String dato, Time starttidspunkt) {
        this.ovelseId = ovelseId;
        this.type = type;
        this.dato = dato;
        this.starttidspunkt = starttidspunkt;
    }

    // For lagResultat, styrke/kondisjon
    public Result(int ovelseId, String type, String dato, Time starttidspunkt, int belastning, int antallRep, int antallSett){
        this(ovelseId, type, dato, starttidspunkt);
        this.belastning = belastning;
        this.antallRep = antallRep;
        this.antallSett = antallSett;
    }

    // For lagResultat, utholdenhet
    public Result(int ovelseId, String type, String dato, Time starttidspunkt, int varighet, int distanse){
        this(ovelseId, type, dato, starttidspunkt);
        this.varighet = varighet;
        this.distanse = distanse;
    }

    public void hentResultat() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet res;
            switch (type) {
                case "styrke":
                case "kondisjon":
                    res = stmt.executeQuery("SELECT navn, belastning, antallRep, antallSett FROM Resultat JOIN Ovelser ON ovelseId = id " +
                                                 "WHERE ovelseId='" + ovelseId + "' AND dato='" + dato +
                                                 "'AND starttidspunkt='" + starttidspunkt + "';");
                    while (res.next()) {
                        this.ovelseNavn = res.getString("navn");
                        this.belastning = res.getInt("belastning");
                        this.antallRep = res.getInt("antallRep");
                        this.antallSett = res.getInt("antallSett");
                    }
                    break;
                case "utholdenhet":
                    res = stmt.executeQuery("SELECT navn, varighet, distanse FROM Resultat JOIN Ovelser ON ovelseId = id " +
                                                 "WHERE ovelseId='" + ovelseId + "' AND dato='" + dato +
                                                 "'AND starttidspunkt='" + starttidspunkt + "';");
                    while (res.next()) {
                        this.ovelseNavn = res.getString("navn");
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
            System.out.println("db error during select of Resultat: " + e);
        }
    }

    public void lagResultat() {
        try {
            Statement stmt = conn.createStatement();
            switch (type) {
                case "styrke":
                case "kondisjon":
                    stmt.executeUpdate("INSERT INTO Resultat (belastning, antallRep, antallSett, dato, starttidspunkt, ovelseId) " +
                            "VALUES ('" + belastning + "','" + antallRep + "','" + antallSett + "','" + dato +
                            "','" + starttidspunkt + "','" + ovelseId + "');");
                    break;
                case "utholdenhet":
                    stmt.executeUpdate("INSERT INTO Resultat (varighet, distanse, dato, starttidspunkt, ovelseId) " +
                            "VALUES ('" + varighet + "','" + distanse + "','" + dato + "','" + starttidspunkt +
                            "','" + ovelseId + "');");
                    break;
                default:
                    System.out.println(type + " is no valid type!");
            }
        } catch (Exception e) {
            System.out.println("db error during insert of resultat: " + e);
        }
    }

    public int getBelastning() {
        return belastning;
    }

    public int getAntallRep() {
        return antallRep;
    }

    public int getAntallSett() {
        return antallSett;
    }

    public int getOvelseId() {
        return ovelseId;
    }

    public int getVarighet() {
        return varighet;
    }

    public int getDistanse() {
        return distanse;
    }

    public String getOvelseNavn() {
        return ovelseNavn;
    }

    public String getDato() {
        return dato;
    }

    @Override
    public String toString() {
        String s = "Resultat hentet\n" + "---------------------------------\n" +
                   "Øvelse: " + this.getOvelseNavn() + "\nDato: " + this.getDato();
        switch (this.type){
            case "styrke":
            case "kondisjon":
                return s + "\nBelastning: " + this.getBelastning() + "\nAntall repitisjoner: " + this.getAntallRep() +
                           "\nAntall sett: " + this.getAntallSett();
            case "utholdenhet":
                return s + "\nVarighet: " + this.getVarighet() + "\nDistanse: " + this.getDistanse();
            default:
                return "No data";
        }
    }
}