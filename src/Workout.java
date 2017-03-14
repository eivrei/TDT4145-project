
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;





public class Workout{

    private Date dato;
    private int varighet;
    private int personligform;
    private int prestasjon;
    private String startTidspunkt;
    private String mal;
    private String maal;
    private String formal_tips;
    private int luft_ventilasjon;
    private int antTilskuere;
    private int temperatur;
    private String værForhold;


    public Workout(Date date, String startTidspunkt) {
        this.dato = date;
        this.startTidspunkt = startTidspunkt;
    }

    public void initialize (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select dato, startTidspunkt from Treningsøkt where dato=" + dato);
            while (rs.next()) {
                dato =  rs.getDate("dato");
                startTidspunkt = rs.getString("startTidspunkt");
            }

        } catch (Exception e) {
            System.out.println("db error during select of treningsøkt= "+e);
            return;
        }

    }

    public void refresh (Connection conn) {
        initialize (conn);
    }

    public String save (Connection conn) throws Exception {
        try {
            Statement stmt = conn.createStatement();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fDato = formatter.format(dato);
            stmt.executeUpdate("insert into Treningsøkt values ('"+fDato+"',"+varighet+",'"+personligform+"','"+formal_tips+"','"+mal+"','"+værForhold+"',"+temperatur+","+antTilskuere+",'"+luft_ventilasjon+"');");
            return fDato;
        } catch (Exception e) {
            System.out.println("db error during insert of Treningsøkt="+e);
            throw new Exception();
        }
    }


    public Date getDate(){
        return dato;
    }

    public void setDate(Date date) {
        this.dato=date;
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

    public String getStartTidspunkt() {
        return startTidspunkt;
    }

    public void setStartTidspunkt(String startTidspunkt) {
        this.startTidspunkt = startTidspunkt;
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
}
