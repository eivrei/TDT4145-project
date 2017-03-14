
import sun.java2d.pipe.SpanShapeRenderer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Statistics extends Connector{

    public void getTopResultStrenght(int ovelseId, String startDato, String sluttDato) {

        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select dato, belastning,antallRep,antallSett from Resultat where ovelseId='"+ovelseId+"' and dato BETWEEN '"+startDato+"' and '"+sluttDato+"' and belastning IS NOT NULL order by belastning ;");
            while (rs.next()){
                System.out.println("{" + rs.getDate("dato") + "|" + rs.getInt("belastning") + "|" + rs.getInt("antallRep") + "|" + rs.getInt("antallSett") + "}");
            }

        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void getTopResultrunning(int ovelseId, String startDato, String sluttDato) {

        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select dato,varighet,distanse from Resultat where ovelseId='"+ovelseId+"' and dato BETWEEN '"+startDato+"' and'"+sluttDato+"' and distanse IS NOT NULL order by distanse ;");
            while (rs.next()) {
                System.out.println("{" + rs.getDate("dato") + "|" + rs.getInt("varighet") + "|" + rs.getInt("distanse") + "}");
            }
        }




        catch (SQLException e) {
            System.out.println(e);
        }
    }


    public void getStatistics(String startDato, String sluttDato) {

        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select SUM(varighet) from Treningsokt where dato BETWEEN '"+startDato+"' and '"+sluttDato+"' ;");
            int sumVarighet=0;
            while(rs.next()) {
                int i=rs.getInt(1);
                sumVarighet+= i;
            }

            System.out.println("I Perioden " + startDato + " til " + sluttDato + " har du trent i " + sumVarighet +" minutter.");

        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Statistics test = new Statistics();
        test.connect();
        test.getStatistics("2017.03.01","2017.04.01");
        test.getTopResultrunning(5, "2017.03.01", "2017.04.01");
        test.getTopResultStrenght(2, "2017.03.01", "2017.04.01");
    }

}