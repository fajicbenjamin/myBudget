package Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author benjaminfajic
 */
public class DBCreate {
    private Statement insertStatement = null;
    private Statement budgetStatement = null;
    private Statement categoriesStatement = null;
    
    public DBCreate() {
        // Set DB System Dir
        // Decide on the db system directory: <userhome>/.myBudget/
        String userHomeDir = System.getProperty("user.home", ".");
        String systemDir = userHomeDir + "/.myBudget";

        // Set the db system directory.
        System.setProperty("derby.system.home", systemDir);
    }
    
    public void createTables() {
        DBConnect db = new DBConnect();
        Connection c = db.getConnection();
        
        String budgetQuerry = "CREATE TABLE BENJO.BUDGET (" +
        "DATE VARCHAR(20) NOT NULL," +
        "DESCRIPTION    VARCHAR(50)," + 
        "CATEGORY   VARCHAR(20)," +
        "TYPE  VARCHAR(10)," +
        "AMOUNT       DECIMAL(7,2)," +
        "PRIMARY KEY (DATE))";
        
        String categoriesQuerry = "CREATE TABLE BENJO.CATEGORIES (" +
        "CATEGORY VARCHAR(20) NOT NULL," +
        "TYPE  VARCHAR(10)," +
        "PRIMARY KEY (CATEGORY))";
        
        try {
            budgetStatement = c.createStatement();
            budgetStatement.execute(budgetQuerry);
            budgetStatement.close();
            
            categoriesStatement = c.createStatement();
            categoriesStatement.execute(categoriesQuerry);
            categoriesStatement.close();
            
        } catch (SQLException sqlExcept) {
            sqlExcept.getSQLState();
        }
    }
    
    public void insertTransaction(String dateInfo, String descriptionInfo, String categoryInfo, String walletBank, String amountInfo) {
        DBConnect connection = new DBConnect();
        try {
            insertStatement = connection.getConnection().createStatement();
            insertStatement.execute("insert into BENJO.BUDGET values ('" + dateInfo +"','"
                    + descriptionInfo + "','" + categoryInfo +"','" + walletBank + "'," + Double.parseDouble(amountInfo) + ")");
            insertStatement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    
    public void insertCategory(String category, String type) {
        DBConnect connection = new DBConnect();
        try{
            insertStatement = connection.getConnection().createStatement();
            insertStatement.execute("insert into BENJO.CATEGORIES values ('" + category +"','"+ type + "')");
            insertStatement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }
    public void removeCategory(String category) {
        DBConnect connection = new DBConnect();
        try {
            //SQL FOR DELETING CATEGORY
            String SQL = "DELETE FROM BENJO.CATEGORIES WHERE CATEGORY = '" + category + "'";
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
