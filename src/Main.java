import java.sql.Time;
import java.util.Scanner;

public class Main {

    public void registrerTreningsokt(Scanner scanner) throws Exception{
        System.out.print("Skriv inn dato og starttidspunkt (yyyy-mm-dd hh:mm:ss): ");
        String[] datoTid = scanner.nextLine().split(" ");
        String dato = datoTid[0];
        Time tid = new Time(Integer.parseInt(datoTid[1].split(":")[0]),
                            Integer.parseInt(datoTid[1].split(":")[1]),
                            Integer.parseInt(datoTid[1].split(":")[2]));
        System.out.print("Skriv inn varighet i min: ");
        int varighet = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn form (1-10): ");
        int form = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn prestasjon (1-10): ");
        int prestasjon = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn formål/tips for økten: ");
        String formaal = scanner.nextLine();
        System.out.print("Skriv inn type treningsøkt (utendors | innendors): ");
        String type = scanner.nextLine().toLowerCase();
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
                System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
                registrerTreningsokt(scanner);
        }
    }

    public void hentTreningsokt(Scanner scanner){
        System.out.print("Skriv inn dato og starttidspunkt (yyyy-mm-dd hh:mm:ss): ");
        String[] datoTid = scanner.nextLine().split(" ");
        String dato = datoTid[0];
        Time tid = new Time(Integer.parseInt(datoTid[1].split(":")[0]),
                Integer.parseInt(datoTid[1].split(":")[1]),
                Integer.parseInt(datoTid[1].split(":")[2]));
        System.out.print("Skriv inn type treningsøkt (utendors | innendors): ");
        String type = scanner.nextLine().toLowerCase();
        switch (type){
            case "utendors":
                Workout uWorkout = new Workout(dato, tid, type);
                uWorkout.connect();
                uWorkout.hentTreningsokt();
                break;
            case "innendors":
                Workout iWorkout = new Workout(dato, tid, type);
                iWorkout.connect();
                iWorkout.hentTreningsokt();
                break;
            default:
                System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
                hentTreningsokt(scanner);
        }
    }

    public void registrerOvelse(Scanner scanner) throws Exception{
        System.out.print("Skriv inn navn på øvelse: ");
        String navn = scanner.nextLine();
        System.out.print("Skriv inn beskrivelse: ");
        String beskrivelse = scanner.nextLine();
        System.out.print("Skriv inn type treningsøkt (styrke | kondisjon | utholdenhet): ");
        String type = scanner.nextLine().toLowerCase();
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
                System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
                registrerOvelse(scanner);
        }

    }

    public void hentOvelse(Scanner scanner){
        System.out.print("Skriv inn øvelseID: ");
        int ovelseId = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn type treningsøkt (styrke | kondisjon | utholdenhet): ");
        String type = scanner.nextLine().toLowerCase();
        switch (type){
            case "styrke":
            case "kondisjon":
            case "utholdenhet":
                Exercise exercise = new Exercise(ovelseId, type);
                exercise.connect();
                exercise.hentOvelse();
                break;
            default:
                System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
                hentOvelse(scanner);
        }
    }

    public void registrerResultat(Scanner scanner){
        System.out.print("Skriv inn øvelseID: ");
        int ovelseID = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn dato og starttidspunkt (yyyy-mm-dd hh:mm:ss): ");
        String[] datoTid = scanner.nextLine().split(" ");
        String dato = datoTid[0];
        Time tid = new Time(Integer.parseInt(datoTid[1].split(":")[0]),
                Integer.parseInt(datoTid[1].split(":")[1]),
                Integer.parseInt(datoTid[1].split(":")[2]));
        System.out.print("Skriv inn type treningsøkt (styrke | kondisjon | utholdenhet): ");
        String type = scanner.nextLine().toLowerCase();
        switch (type) {
            case "styrke":
            case "kondisjon":
                System.out.print("Skriv inn belastning i kg: ");
                int belastning = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall repitisjoner: ");
                int antallRep = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn antall sett: ");
                System.out.print("Skriv inn antall sett: ");
                int antallSett = Integer.parseInt(scanner.nextLine());
                Result kResult = new Result(ovelseID, type, dato, tid, belastning, antallRep, antallSett);
                kResult.connect();
                kResult.lagResultat();
                break;
            case "utholdenhet":
                System.out.print("Skriv inn varighet i min: ");
                int varighet = Integer.parseInt(scanner.nextLine());
                System.out.print("Skriv inn distanse i meter: ");
                int distanse = Integer.parseInt(scanner.nextLine());
                Result uResult = new Result(ovelseID, type, dato, tid, varighet, distanse);
                uResult.connect();
                uResult.lagResultat();
                break;
            default:
                System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
                registrerResultat(scanner);
        }

    }

    public void hentResultat(Scanner scanner){
        System.out.print("Skriv inn øvelseID: ");
        int ovelseID = Integer.parseInt(scanner.nextLine());
        System.out.print("Skriv inn dato og starttidspunkt (yyyy-mm-dd hh:mm:ss): ");
        String[] datoTid = scanner.nextLine().split(" ");
        String dato = datoTid[0];
        Time tid = new Time(Integer.parseInt(datoTid[1].split(":")[0]),
                Integer.parseInt(datoTid[1].split(":")[1]),
                Integer.parseInt(datoTid[1].split(":")[2]));
        System.out.print("Skriv inn type treningsøkt (styrke | kondisjon | utholdenhet): ");
        String type = scanner.nextLine().toLowerCase();
        switch (type){
            case "styrke":
            case "kondisjon":
            case "utholdenhet":
                Result result = new Result(ovelseID, type, dato, tid);
                result.connect();
                result.hentResultat();
                break;
            default:
                System.out.println("Dette er ikke en gyldig type. Dumt valg, for nå må du skrive inn alt på nytt!\n\n");
                hentResultat(scanner);
        }
    }

    public void hentStatistikk(Scanner scanner){
        System.out.print("Skriv inn start dato for perioden: ");
        String startDato = scanner.nextLine();
        System.out.print("Skriv inn slutt dato for perioden: ");
        String sluttDato = scanner.nextLine();
        System.out.print("Skriv inn typen statistikk du ønsker (topRunning | topStrenght | period): ");
        String sType = scanner.nextLine();
        Statistics stats = new Statistics();
        stats.connect();
        switch (sType){
            case "topRunning":
                System.out.print("Skriv inn øvelseID: ");
                int runOvelseID = Integer.parseInt(scanner.nextLine());
                stats.getTopResultRunning(runOvelseID, startDato, sluttDato);
                break;
            case "topStrenght":
                System.out.print("Skriv inn øvelseID: ");
                int strOvelseID = Integer.parseInt(scanner.nextLine());
                stats.getTopResultStrenght(strOvelseID, startDato, sluttDato);
                break;
            case "period":
                stats.getStatistics(startDato, sluttDato);
                break;
            default:
                System.out.println("Vi har ikke noen statestikk for " + sType + " ennå, du får prøve en av de oppgitte alternativene.");
                hentStatistikk(scanner);
        }
    }

    public static void main(String[] args) throws Exception{
        Main main = new Main();
        Scanner scanner=new Scanner(System.in);
        String input = "";
        System.out.println("             Velkommen til CoolBoys-treningsdagbok\n===================================================================");
        System.out.print("Skriv inn brukernavn: ");
        String brukernavn = scanner.nextLine();
        System.out.print("Skriv inn passord: ");
        String passord = scanner.nextLine();
        System.out.println("Du er nå logget inn med brukernavn '" + brukernavn + "' og passord '" + passord + "'");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Du har følgende muligheter:\n" +
                           "1. Registrere ny treningsøkt\n" +
                           "2. Hente ut tidligere treningsøkt\n" +
                           "3. Registrere ny øvelse\n" +
                           "4. Hente ut øvelse\n" +
                           "5. Registrere nytt resultat\n" +
                           "6. Hente ut tidligere resultat\n" +
                           "7. Hente ut statistikk for en gitt tidsperiode\n" +
                           "Skriv quit for å avslutte programmet");
        System.out.println("-------------------------------------------------------------------");
        while (!input.toLowerCase().equals("quit")) {
            System.out.print("\nSkriv inn ønsket funksjon med sitt respektive tall: ");
            input = scanner.nextLine();
            switch (input) {
                case "1":
                    main.registrerTreningsokt(scanner);
                    break;
                case "2":
                    main.hentTreningsokt(scanner);
                    break;
                case "3":
                    main.registrerOvelse(scanner);
                    break;
                case "4":
                    main.hentOvelse(scanner);
                    break;
                case "5":
                    main.registrerResultat(scanner);
                    break;
                case "6":
                    main.hentResultat(scanner);
                    break;
                case "7":
                    main.hentStatistikk(scanner);
                    break;
                default:
                    System.out.println("Dette er ikke en gyldig operasjon...\n");
            }
        }
        scanner.close();
        System.out.println("\n\nDa var du fornøyd ja. Ha en fortreffelig aften B-)");
    }
}