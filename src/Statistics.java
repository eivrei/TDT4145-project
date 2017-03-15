import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Statistics extends Connector{
    public void getTopResultStrenght(int ovelseId, String startDato, String sluttDato) {
        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT dato, belastning, antallRep, antallSett FROM Resultat " +
                                                     "WHERE ovelseId='"+ovelseId+"' AND dato BETWEEN '"+startDato+
                                                     "' AND '"+sluttDato+"' AND belastning IS NOT NULL " +
                                                     "ORDER BY belastning ;");
            System.out.println("\n{   Dato   | Belastning | Antall Repetisjoner | Antall Sett}");
            int nr = 0;
            while (rs.next() && nr < 5){
                System.out.println("{" + rs.getDate("dato") + "| " + String.format("%-11s",rs.getInt("belastning")) +
                        "| " + String.format("%-20s",rs.getInt("antallRep")) + "| " + String.format("%-11s", rs.getInt("antallSett")) + "}");
                nr ++;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void getTopResultRunning(int ovelseId, String startDato, String sluttDato) {
        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT dato, varighet, distanse FROM Resultat " +
                                                     "WHERE ovelseId='"+ovelseId+"' AND dato BETWEEN '"+startDato+"' " +
                                                     "AND'"+sluttDato+"' AND distanse IS NOT NULL " +
                                                     "ORDER BY distanse ;");
            System.out.println("\n{   Dato   | Varighet | Distanse}");
            int nr = 0;
            while (rs.next() && nr < 5) {
                System.out.println("{" + rs.getDate("dato") + "| " + String.format("%-9s",rs.getInt("varighet")) + "| "
                                   + String.format("%-8s",rs.getInt("distanse"))+ "}");
                nr ++;
            }
        }
        catch (SQLException e) {
            System.out.println("Error getting topResultsRunning: " + e);
        }
    }

    public void getStatistics(String startDato, String sluttDato) {
        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT SUM(varighet), COUNT(*) FROM Treningsokt " +
                                                     "WHERE dato BETWEEN '"+startDato+"' AND '"+sluttDato+"' ;");
            int sumVarighet=0;
            int antallOkter=0;
            while(rs.next()) {
                int i=rs.getInt(1);
                int j=rs.getInt(2);
                antallOkter += j;
                sumVarighet+= i;
            }
            System.out.println("\nI Perioden " + startDato + " til " + sluttDato + " har du trent i "
                               + sumVarighet +" minutter fordelt på " + antallOkter + " økter.");
        }
        catch (SQLException e) {
            System.out.println("Error getting statistics " + e);
        }
    }

    public static void main(String[] args) {
        Statistics test = new Statistics();
        test.connect();
        //test.getStatistics("2017.03.01","2017.04.01");
        test.getTopResultRunning(5, "2017.03.01", "2017.04.01");
        test.getTopResultStrenght(1, "2017.03.01", "2017.04.01");
    }
}