package Model;

import java.sql.Statement;

/**
 *
 * @author benjaminfajic
 */
public class ResetData {
    
    public ResetData() {
        DBConnect connection = new DBConnect();
        
        try{
            //SQL FOR RESETING DATA
            String SQL = "DELETE FROM BENJO.BUDGET";
            Statement stmt = null;
            stmt = connection.getConnection().createStatement();
            stmt.execute(SQL);
            stmt.close();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data"); 
        }
    }
}
