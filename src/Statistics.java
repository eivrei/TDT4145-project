
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class Statistics extends Connector{

    public void getTopResultStrenght(int øvelses_id, Date startDato, Date sluttDato) {

        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select dato, belastning,antallRep,antallSet from Resultat where øvelses_id="+øvelses_id+" and Treningsokt>'"+startDato+"' and Treningsokt<'"+sluttDato+"' and belastning IS NOT NULL"+"order by belastning ;");
            while (rs.next()){
                System.out.println("{" + rs.getDate("dato") + "|" + rs.getInt("belastning") + "|" + rs.getInt("antallRep") + "|" + rs.getInt("antallSet") + "}");
            }

        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void getTopResultrunning(int øvelses_id,Date startDato, Date sluttDato) {

        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select dato,varighet,distanse from Resultat where øvelses_id="+øvelses_id+" and Treningsokt>'"+startDato+"' and Treningsokt<'"+sluttDato+"' and distanse IS NOT NULL"+"order by distanse ;");
            while (rs.next()) {
                System.out.println("{" + rs.getDate("dato") + "|" + rs.getInt("varighet") + "|" + rs.getInt("distanse"));
            }
        }




        catch (SQLException e) {
            System.out.println(e);
        }
    }


    public void getStatistics(Date startDato, Date sluttDato) {

        try {
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery("select SUM (varighet) from Resultat where Treningsokt>'"+startDato+"' and Treningsokt< sluttDato ;");
            int sumVarighet=0;
            while(rs.next()) {
                int i=rs.getInt(1);
                sumVarighet+= i;
            }

            System.out.println("I Perioden " + startDato + "til " + sluttDato + " har du trent i " + sumVarighet +"minutter");

        }

        catch (SQLException e) {
            System.out.println(e);
        }
    }


}