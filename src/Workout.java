
import java.util.Date;
import java.util.Time;
import java.s




public class Workout{

    private Date date;
    private int varighet;
    private int personligform;
    private int prestasjon
    private Time startTidspunkt;
    private String mal;
    private String maal;
    private String formal/tips;
    private int luft/ventilasjon;
    private int antTilskuere;
    private int temperatur;
    private String værForhold;

    public Workout(Date date, Time startTidspunkt) {
        this.date = date;
        this.startTidspunkt = startTidspunkt;
    }


    public Date getDate(){
        return date;
    }

    public void setDate(Date date) {
        this.date=date;
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
        return startTidspunkt;
    }

    public void setStartTidspunkt(Time startTidspunkt) {
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
        return formal;
    }

    public void setFormal(String formal) {
        this.formal = formal;
    }

    public int getLuft() {
        return luft;
    }

    public void setLuft(int luft) {
        this.luft = luft;
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
