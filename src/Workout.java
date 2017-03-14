import java.util.Date;
import java.sql.Time;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class Workout extends Connector{

    private Date dato;
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


    public Workout(Date date, Time startTidspunkt) {
        this.dato = date;
        this.starttidspunkt = startTidspunkt;
    }

    public void HentTreningsokt (Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select dato, starttidspunkt, personligform,varighet,prestasjon,from Treningsokt where dato=" + dato + " and starttidspunkt='" + starttidspunkt + "';");
            while (rs.next()) {
                dato =  rs.getDate("dato");
                starttidspunkt = rs.getTime("startTidspunkt");
                personligform=rs.getInt("personligform");
                varighet=rs.getInt("varighet");
                prestasjon=rs.getInt("prestasjon");

            }

        } catch (Exception e) {
            System.out.println("db error during select of treningsøkt= "+e);
            return;
        }

    }

    public void lagTreningsokt (Connection conn) throws Exception {
        try {
            Statement stmt = conn.createStatement();
            /*
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fDato = formatter.format(dato);
            */
            stmt.executeUpdate("insert into Treningsokt values ('"+dato+"',"+varighet+",'"+personligform+"','"+formal_tips+"','"+mal+"','"+værForhold+"',"+temperatur+","+antTilskuere+",'"+luft_ventilasjon+"');");
            return ;
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
}
