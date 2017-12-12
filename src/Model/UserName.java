package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Benjamin
 */
public class UserName {
    private String username = "";
    
    public UserName() {
    }
    
    public String getUserName() throws SQLException {
        DBConnect connection = new DBConnect();
          
        try {
            //SQL FOR SELECTING ALL INCOME CATEGORIES
            String SQL = "SELECT * from BENJO.USERS";
            //ResultSet
            ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
            while(rs.next()){
                    username = rs.getString("USERNAME");
                }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error on fetching username");             
        }
        
        return username;
    }
}
