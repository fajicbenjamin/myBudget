/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author benjaminfajic
 */
public class BalanceAmount {
    public String balanceAmountTemp;
    
    public BalanceAmount(String type) {
          
        try {
            DBConnect connection = new DBConnect();
            
            String SQL = "SELECT sum(amount) FROM BENJO.BUDGET WHERE type = '" + type + "'";
            //ResultSet
            ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                balanceAmountTemp = row.get(0);
                break;
            }
        } catch(Exception e) {
              e.printStackTrace();
              System.out.println("Error on Building Data");             
        }
    }
    
    public String getBalanceAmount() {
        return (balanceAmountTemp + " KM");
    }
}
