package Model;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

/**
 *
 * @author benjaminfajic
 */
public class LastInput {
    public Label amountInfo;
    public Label descriptionInfo;
    public Label dateInfo;
    
    public LastInput(Label amountInfo, Label descriptionInfo, Label dateInfo) {
        DBConnect connection = new DBConnect();
          
          try{
            //SQL FOR SELECTING ALL INPUTS OF BUDGET
            String SQL = "SELECT * FROM BENJO.BUDGET ORDER BY date DESC";
            //ResultSet
            ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
            
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                amountInfo.setText(row.get(4) + " KM");
                descriptionInfo.setText(row.get(1));
                dateInfo.setText(row.get(0));
                break;
            }
    } catch(Exception e){
              e.printStackTrace();
              System.out.println("Error on Building Data");             
          }
    }
}
