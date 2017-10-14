package Model;

import Controllers.DashboardController;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 *
 * @author benjaminfajic
 */
public class MyMenu {
    Categories categories = new Categories();
    
    @FXML
    private MenuItem fileClose;
    @FXML
    private MenuItem addCategory;
    @FXML
    private MenuItem removeCategory;
    @FXML
    private MenuItem helpAbout;
    @FXML
    private MenuItem resetData;
    
    public Label amountInfo;
    public Label descriptionInfo;
    public Label walletBank;
    public Label dateInfo;
    public Label categoryInfo;
    public Label balanceAmount;
    public Label bankAmount;
    
    public MyMenu(ActionEvent event, MenuItem fileClose, MenuItem addCategory, MenuItem removeCategory, MenuItem helpAbout, MenuItem resetData, Label amountInfo, Label descriptionInfo, Label walletBank, Label dateInfo, Label categoryInfo, Label balanceAmount, Label bankAmount) throws IOException {
        MyDialog dialog = new MyDialog();
        
        if (event.getSource() == fileClose) {
            System.exit(0);
        } else if (event.getSource() == addCategory) {
            String tempMenu = dialog.chooseIncomeExpense();
            
            if (tempMenu.equals("Income")) {
            
                if(categories.addIncomeCategory(dialog.addCategory())) {
                    dialog.categoryAdded();
                } else {
                    dialog.categoryAlreadyExist();
                }
                
            } else if (tempMenu.equals("Expense")){
                
                if(categories.addExpenseCategory(dialog.addCategory())) {
                    dialog.categoryAdded();
                } else {
                    dialog.categoryAlreadyExist();
                }
                
            } else {
                //System.out.println("ništa");
            }
            
        } else if (event.getSource() == removeCategory){ 
            String tempMenu = dialog.chooseIncomeExpense();
                if (tempMenu.equals("Income")) {
            
                    if(categories.removeIncomeCategory(dialog.removeCategory())) {
                        dialog.categoryRemoved();
                    } else {
                        dialog.categoryAlreadyRemoved();
                    }
                    
                } else if (tempMenu.equals("Expense")){
                    
                    if(categories.removeExpenseCategory(dialog.removeCategory())) {
                        dialog.categoryRemoved();
                    } else {
                        dialog.categoryAlreadyRemoved();
                    }
                }
        } else if (event.getSource() == resetData) {
            Alert alert1 = new Alert(AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation Dialog");
            alert1.setHeaderText("Are you sure you want to reset data stored in database? \nAll your data will be lost.");
            alert1.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert1.showAndWait();
            if (result.get() == ButtonType.OK){
                try {
                    ResetData reset = new ResetData();
                } catch (Exception ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                //initialize again dashboard window values
                walletBank.setText("");
                amountInfo.setText("");
                descriptionInfo.setText("");
                dateInfo.setText("");
                categoryInfo.setText("");
                balanceAmount.setText("null KM");
                bankAmount.setText("null KM");
                //confirming alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reset data");
                alert.setHeaderText("Information");
                alert.setContentText("You successfully deleted all entered data!");
                alert.showAndWait();
            } else {
                // ... user chose CANCEL or closed the dialog
            }
            
            
        } else if(event.getSource() == helpAbout) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About myBudget");
            alert.setHeaderText("");
            alert.setContentText("Version v1.0\n\nDeveloped and designed by Benjamin Fajić. "
                    + "Application is written in Java language, using JavaFX for UI components. "
                    + "Used embedded Java DB (Apache Derby) database for storing data."
                    + "\n\nThank you for using this free application. I hope you like it. "
                    + "If you paid for it, get a refund!"
                    + "\n\nBenjamin Fajić"
                    + "\n2017 \u00a9 All rights reserved.");
            alert.setGraphic(new ImageView(this.getClass().getResource("/img/icon.png").toString()));
            alert.showAndWait();
        }
    }
}
