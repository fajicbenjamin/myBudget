package Controllers;

import Model.Categories;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import Model.BalanceAmount;
import Model.Input;
import Model.LastInput;
import Model.MyMenu;
import Model.UserName;
import java.sql.SQLException;
import javafx.scene.control.TableView;

/**
 *
 * @author benjaminfajic
 */
public class DashboardController implements Initializable {

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
    
    Categories categories;
    
    @FXML
    private Label balanceAmount;
    @FXML
    private Label bankAmount;
    @FXML
    private Label username;
    
    public Label amountInfo;
    public Label descriptionInfo;
    public Label dateInfo;
    
    @FXML
    private Button seeAll;
    
    // NEEDED FOR SAKE OF MENU
    private TableView<Input> tableview;
    
    @FXML
    private void handleMenuItem(ActionEvent event) throws IOException {
        MyMenu menu = new MyMenu("dashboard", event, fileClose, addCategory, removeCategory, helpAbout, resetData, amountInfo, descriptionInfo, dateInfo, balanceAmount, bankAmount, tableview);
    }
    
    @FXML
    private void handleTransaction(ActionEvent event) throws Exception {
        Parent transaction = FXMLLoader.load(getClass().getResource("/Views/Transaction.fxml"));

        Scene transactionScene = new Scene(transaction, 700, 450);
        Stage transactionStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        transactionStage.setScene(transactionScene);
        transactionStage.show();
    }
    
    @FXML
    private void handleSeeAll(ActionEvent event) throws Exception {
        Parent transaction = FXMLLoader.load(getClass().getResource("/Views/Table.fxml"));

        Scene transactionScene = new Scene(transaction, 700, 450);
        Stage transactionStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        transactionStage.setScene(transactionScene);
        transactionStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set username on label in UI from database
        UserName user = new UserName();
        try {
            username.setText(user.getUserName());
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            lastInput();
        } catch (Exception ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            handleBalanceAmount();
        } catch (Exception ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            handleBankAmount();
        } catch (Exception ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void lastInput() {
        LastInput ls = new LastInput(amountInfo, descriptionInfo, dateInfo);
    }
    
    public void handleBalanceAmount() throws Exception {
        BalanceAmount ba = new BalanceAmount("Wallet");
        balanceAmount.setText(ba.getBalanceAmount());
    }
    
    public void handleBankAmount() throws Exception {
        BalanceAmount ba = new BalanceAmount("Bank");
        bankAmount.setText(ba.getBalanceAmount());
    }
}
