public class Exercise extends Connector{

    private int øvelsesId;
    private String navn;
    private String beskrivelse;
    private int belastning;
    private int antallRep;
    private int antallSet;
    private int varighet;
    private int distanse;
    private String øvelsesGruppe;

    

    public int getØvelsesId() {
        return øvelsesId;
    }

    public void setØvelsesId(int øvelsesId) {
        this.øvelsesId = øvelsesId;
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