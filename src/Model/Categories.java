package Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Model.DBConnect;
import Model.DBCreate;

/**
 *
 * @author benjaminfajic
 */
public class Categories {
    private List<String> categoriesIncome = new ArrayList<String>();
    private List<String> categoriesExpense = new ArrayList<String>();
    private String name;
    
    DBCreate db = new DBCreate();
    
    public Categories() {
        categoriesIncomeInitialize();
        categoriesExpenseInitialize();
    }
    
    public void categoriesIncomeInitialize() {
        getIncomeCategoriesFromDB();
    }
    
    public void categoriesExpenseInitialize() {
        getExpenseCategoriesFromDB();
    }
    
    public boolean addIncomeCategory(String newCategory) {
        String tempCategory = "";
        for(int i = 0; i < categoriesIncome.size(); i++) {
            tempCategory = categoriesIncome.get(i);
            if (tempCategory.equals(newCategory)) {
                break;
            } 
        }
        if (!tempCategory.equals(newCategory)) {
            if (newCategory.equals("")) {
                return false;
            }
            categoriesIncome.add(newCategory);
            db.insertCategory(newCategory, "Income");
            return true;
        }
        return false;
    }
    
    public boolean addExpenseCategory(String newCategory) {
        String tempCategory = "";
        for(int i = 0; i < categoriesExpense.size(); i++) {
            tempCategory = categoriesExpense.get(i);
            if (tempCategory.equals(newCategory)) {
                break;
            } 
        }
        if (!tempCategory.equals(newCategory)) {
            if (newCategory.equals("")) {
                return false;
            }
            categoriesExpense.add(newCategory);
            db.insertCategory(newCategory, "Expense");
            return true;
        }
        return false;
    }
    
    public boolean removeIncomeCategory(String removeCategory) {
        for(int i = 0; i < categoriesIncome.size(); i++) {
            String tempCategory = categoriesIncome.get(i);
            if (tempCategory.equals(removeCategory)) {
                if (removeCategory.equals("")) {
                return false;
                }
                categoriesIncome.remove(i);
                db.removeCategory(removeCategory);
                return true;
            }
        }
        return false;
    }
    
    public boolean removeExpenseCategory(String removeCategory) {
        for(int i = 0; i < categoriesExpense.size(); i++) {
            String tempCategory = categoriesExpense.get(i);
            if(tempCategory.equals(removeCategory)) {
                if (removeCategory.equals("")) {
                return false;
            }
                categoriesExpense.remove(i);
                db.removeCategory(removeCategory);
                return true;
            }
        }
        return false;
    }
    
    public List<String> getIncomeCategories() {
        return categoriesIncome;
    }
    
    public List<String> getExpenseCategories() {
        return categoriesExpense;
    }
    
    public String incomeToString() {
        String tempString = "";
        for(int i = 0; i < categoriesIncome.size(); i++) {
            tempString += "\n" + categoriesIncome.get(i);
        }
        return (tempString);
    }
    
    public String expenseToString() {
        String tempString = "";
        for(int i = 0; i < categoriesExpense.size(); i++) {
            tempString += "\n" + categoriesExpense.get(i);
        }
        return (tempString);
    }
    
    //CONNECTION DATABASE TO GET CATEGORIES
    public void getIncomeCategoriesFromDB() {
        DBConnect connection = new DBConnect();
          
        try {
            //SQL FOR SELECTING ALL INCOME CATEGORIES
            String SQL = "SELECT * from BENJO.CATEGORIES WHERE type = 'Income'";
            //ResultSet
            ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                    categoriesIncome.add(row.get(0));
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error on fetching category");             
        }
    }   
    
    public void getExpenseCategoriesFromDB() {
        DBConnect connection = new DBConnect();
          
        try {
            //SQL FOR SELECTING ALL INPUTS OF BUDGET
            String SQL = "SELECT * from BENJO.CATEGORIES WHERE type = 'Expense'";
            //ResultSet
            ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                    categoriesExpense.add(row.get(0));
                }
            }
        } catch(Exception e) {
              e.printStackTrace();
              System.out.println("Error on fetching category");             
        }
    }   
}
