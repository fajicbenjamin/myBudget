package Model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author benjaminfajic
 */
public class DBConnect {
    private String dbURL = "jdbc:derby:myBudget;create=true;user=benjo;password=benjo";
    Connection c;
    
    public DBConnect() {
          
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            //Get a connection
            c = DriverManager.getConnection(dbURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return c;
    }
}
