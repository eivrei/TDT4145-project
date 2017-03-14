import java.sql.*;
import java.util.Properties;

public abstract class Connector {
    protected Connection conn;
    public Connector() {
    }
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // Properties for user and password. Here the user and password are both 'paulr'
            Properties p = new Properties();
            p.put("user", "eivindre_db");
            p.put("password", "Eivinderbest");
            conn = DriverManager.getConnection("jdbc:mysql://mysql.stud.ntnu.no/eivindre_TDT4145?autoReconnect=true&useSSL=false",p);
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}
