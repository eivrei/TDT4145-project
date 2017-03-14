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
            while (rs.next()){
                System.out.println("{" + rs.getDate("dato") + "|" + rs.getInt("belastning") +
                        "|" + rs.getInt("antallRep") + "|" + rs.getInt("antallSett") + "}");
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
            while (rs.next()) {
                System.out.println("{" + rs.getDate("dato") + "|" + rs.getInt("varighet") + "|"
                                   + rs.getInt("distanse") + "}");
            }
        }
        catch (SQLException e) {
            System.out.println("Error getting topResultsRunning: " + e);
        }
    }

    public void getStatistics(String startDato, String sluttDato) {
        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("SELECT SUM(varighet) FROM Treningsokt " +
                                                     "WHERE dato BETWEEN '"+startDato+"' AND '"+sluttDato+"' ;");
            int sumVarighet=0;
            while(rs.next()) {
                int i=rs.getInt(1);
                sumVarighet+= i;
            }
            System.out.println("I Perioden " + startDato + " til " + sluttDato + " har du trent i "
                               + sumVarighet +" minutter.");
        }
        catch (SQLException e) {
            System.out.println("Error getting statistics " + e);
        }
    }

    public static void main(String[] args) {
        Statistics test = new Statistics();
        test.connect();
        test.getStatistics("2017.03.01","2017.04.01");
        test.getTopResultRunning(5, "2017.03.01", "2017.04.01");
        test.getTopResultStrenght(2, "2017.03.01", "2017.04.01");
    }
}