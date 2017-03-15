import java.sql.Time;
import java.util.Scanner;

public class Main {
    Scanner scanner;
    public Main (Scanner scanner) {
        this.scanner = scanner;
    }

    // All possible actions to the database:
    public void registrerTreningsokt() throws Exception{
        String dato = this.getDato("");
        Time tid = this.getTid();
        System.out.print("Skriv inn varighet i min: ");
        int varighet = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn form (1-10): ");
        int form = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn prestasjon (1-10): ");
        int prestasjon = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn formål/tips for økten: ");
        String formaal = scanner.nextLine();
        String type = this.getType("utendors | innendors");
        switch (type){
            case "utendors":
                System.out.print("Skriv inn temperatur: ");
                int temperatur = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn værtype: ");
                String vaertype = scanner.nextLine();
                Workout uWorkout = new Workout(dato, tid, varighet, form, prestasjon, formaal, type, temperatur, vaertype);
                uWorkout.connect();
                uWorkout.lagTreningsokt();
                break;
            case "innendors":
                System.out.print("Skriv inn luft-/ventilasjonsdata (1-10): ");
                int luftVent = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall tilskuere: ");
                int antallTilskuere = Integer.parseInt(scanner.nextLine());
                Workout iWorkout = new Workout(dato, tid, varighet, form, prestasjon, formaal, type, luftVent, antallTilskuere);
                iWorkout.connect();
                iWorkout.lagTreningsokt();
                break;
            default:
                this.printFeilType();
                registrerTreningsokt();
        }
    }

    public void hentTreningsokt(){
        String dato = this.getDato("");
        Time tid = this.getTid();
        String type = this.getType("utendors | innendors");
        if(type.matches("utendors|innendors")){
            Workout workout = new Workout(dato, tid, type);
            workout.connect();
            workout.hentTreningsokt();
        }else {
            this.printFeilType();
            hentTreningsokt();
        }
    }

    public void registrerOvelse() throws Exception{
        System.out.print("Skriv inn navn på øvelse: ");
        String navn = scanner.nextLine();
        System.out.print("Skriv inn beskrivelse: ");
        String beskrivelse = scanner.nextLine();
        String type = this.getType("styrke | kondisjon | utholdenhet");
        switch (type){
            case "styrke":
            case "kondisjon":
                System.out.print("Skriv inn belastning i kg: ");
                int belastning = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall repitisjoner: ");
                int antallRep = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall sett: ");
                int antallSett = Integer.parseInt(scanner.nextLine());
                Exercise kExercise = new Exercise(type, navn, beskrivelse, belastning, antallRep, antallSett);
                kExercise.connect();
                kExercise.lagOvelse();
                break;
            case "utholdenhet":
                System.out.print("Skriv inn varighet i min: ");
                int varighet = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn distanse i meter: ");
                int distanse = Integer.parseInt(scanner.nextLine());
                Exercise uExercise = new Exercise(type, navn, beskrivelse, varighet, distanse);
                uExercise.connect();
                uExercise.lagOvelse();
                break;
            default:
                this.printFeilType();
                registrerOvelse();
        }

    }

    public void hentOvelse(){
        int ovelseId = this.getOvelseId();
        String type = this.getType("styrke | kondisjon | utholdenhet");
        if(type.matches("styrke|kondisjon|utholdenhet")){
            Exercise exercise = new Exercise(ovelseId, type);
            exercise.connect();
            exercise.hentOvelse();
        }else{
            this.printFeilType();
            hentOvelse();
        }
    }

    public void registrerResultat(){
        int ovelseId = this.getOvelseId();
        String dato = this.getDato("");
        Time tid = this.getTid();
        String type = this.getType("styrke | kondisjon | utholdenhet");
        switch (type) {
            case "styrke":
            case "kondisjon":
                System.out.print("Skriv inn belastning i kg: ");
                int belastning = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall repitisjoner: ");
                int antallRep = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall sett: ");
                int antallSett = Integer.parseInt(scanner.nextLine());
                Result kResult = new Result(ovelseId, type, dato, tid, belastning, antallRep, antallSett);
                kResult.connect();
                kResult.lagResultat();
                break;
            case "utholdenhet":
                System.out.print("Skriv inn varighet i min: ");
                int varighet = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn distanse i meter: ");
                int distanse = Integer.parseInt(scanner.nextLine());
                Result uResult = new Result(ovelseId, type, dato, tid, varighet, distanse);
                uResult.connect();
                uResult.lagResultat();
                break;
            default:
                this.printFeilType();
                registrerResultat();
        }

    }

    public void hentResultat(){
        int ovelseId = this.getOvelseId();
        String dato = this.getDato("");
        Time tid = this.getTid();
        String type = this.getType("styrke | kondisjon | utholdenhet");
        if(type.matches("styrke|kondisjon|utholdenhet")){
            Result result = new Result(ovelseId, type, dato, tid);
            result.connect();
            result.hentResultat();
        }else {
            this.printFeilType();
            hentResultat();
        }
    }

    public void hentStatistikk(){
        String startDato = this.getDato("start");
        String sluttDato = this.getDato("slutt");
        String sType = this.getType("topRunning | topStrenght | generelt");
        int ovelseId = this.getOvelseId();
        Statistics stats = new Statistics();
        stats.connect();
        switch (sType){
            case "topRunning":
                stats.getTopResultRunning(ovelseId, startDato, sluttDato);
                break;
            case "topStrenght":
                stats.getTopResultStrenght(ovelseId, startDato, sluttDato);
                break;
            case "generelt":
                stats.getStatistics(startDato, sluttDato);
                break;
            default:
                this.printFeilType();
                hentStatistikk();
        }
    }

    // Help methods:
    private void startApplication() {
        System.out.println("             Velkommen til CoolBoys-treningsdagbok" +
                           "\n==================================================================");
        System.out.print("Skriv inn brukernavn: ");
        String brukernavn = scanner.nextLine();
        System.out.print("Skriv inn passord: ");
        String passord = scanner.nextLine();
        System.out.println("Du er nå logget inn med brukernavn '" + brukernavn + "' og passord '" + passord + "'");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Du har følgende muligheter:\n" +
                "1. Registrere ny treningsøkt\n" +
                "2. Hente ut tidligere treningsøkt\n" +
                "3. Registrere ny øvelse\n" +
                "4. Hente ut øvelse\n" +
                "5. Registrere nytt resultat\n" +
                "6. Hente ut tidligere resultat\n" +
                "7. Hente ut statistikk for en gitt tidsperiode\n" +
                "8. Lag en ny treningsmal\n\n" +
                "Skriv quit for å avslutte programmet");
        System.out.println("------------------------------------------------------------------");
    }

    private int getOvelseId() {
        System.out.print("Skriv inn øvelseID: ");
        return Integer.parseInt(scanner.nextLine());
    }

    private String getType(String muligeInput) {
        System.out.print("Skriv inn type (" + muligeInput + "): ");
        return scanner.nextLine().toLowerCase();
    }

    private String getDato(String prefix) {
        System.out.print("Skriv inn " + prefix + "dato (yyyy-mm-dd): ");
        return scanner.nextLine();
    }

    private Time getTid() {
        System.out.print("Skriv inn starttidspunkt (hh:mm:ss): ");
        String[] tid = scanner.nextLine().split(":");
        return new Time(Integer.parseInt(tid[0]), Integer.parseInt(tid[1]), Integer.parseInt(tid[2]));
    }

    private void printFeilType() {
        System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        Main main = new Main(scanner);
        main.startApplication();
        String input = "";
        while (!input.toLowerCase().equals("quit")) {
            System.out.print("\nSkriv inn ønsket funksjon med sitt respektive tall: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    main.registrerTreningsokt();
                    break;
                case "2":
                    main.hentTreningsokt();
                    break;
                case "3":
                    main.registrerOvelse();
                    break;
                case "4":
                    main.hentOvelse();
                    break;
                case "5":
                    main.registrerResultat();
                    break;
                case "6":
                    main.hentResultat();
                    break;
                case "7":
                    main.hentStatistikk();
                    break;
                case "8":
                    System.out.print("Sikker på at du vil lage ny mal? ");
                    input = scanner.nextLine().toLowerCase();
                    if (input.equals("ja")){
                        System.out.println("Nei, det er du faktisk ikke");
                    }else{
                        System.out.println("Det var fornuftig det! #flinkgutt");
                    }
                    break;
                case "quit":
                    break;
                default:
                    System.out.println("Dette er ikke en gyldig operasjon...\n");
            }
        }
        scanner.close();
        System.out.println("\n\nDa var du fornøyd ja. Ha en fortreffelig aften B-)");
    }
}