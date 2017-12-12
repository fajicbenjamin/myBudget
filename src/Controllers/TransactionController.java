package Controllers;

import Model.Categories;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import Model.DBCreate;
import Model.Input;
import Model.MyMenu;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

/**
 *
 * @author benjaminfajic
 */
public class TransactionController implements Initializable {

    // MENU
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
    
    // NEEDED FOR MENU MORE THAN FOR THIS CONTROLLER
    public Label amountInfo;
    public Label descriptionInfo;
    public Label dateInfo;
    public Label balanceAmount;
    public Label bankAmount;
    private TableView<Input> tableview;
    
    // NEEDED FOR SUBMITING TRANSACTION
    public String walletBankTemp;
    public String amountInfoTemp;
    public String descriptionInfoTemp;
    public String dateInfoTemp;
    public String categoryInfoTemp;
    
    //FIELDS FOR TRANSACTION
    @FXML
    private JFXComboBox<String> categoryBox;
    @FXML
    private JFXComboBox<String> incomeExpenseBox;
    @FXML
    private JFXRadioButton radioWallet;
    @FXML
    private JFXRadioButton radioBank;
    @FXML
    private JFXTextField descriptionField;
    @FXML
    private JFXTextField amountField;
    
    public String radioToggled;
    ToggleGroup group = new ToggleGroup();
    Categories categories = new Categories();
   
    
    
    @FXML
    private void handleMenuItem(ActionEvent event) throws IOException {
        MyMenu menu = new MyMenu("", event, fileClose, addCategory, removeCategory, helpAbout, resetData, amountInfo, descriptionInfo, dateInfo, balanceAmount, bankAmount, tableview);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        incomeExpenseBox.setStyle("-fx-focus-color: transparent;");
        incomeExpenseBox.setStyle("-fx-faint-focus-color: transparent");
        categoryBox.setStyle("-fx-focus-color: transparent;");
        categoryBox.setStyle("-fx-faint-focus-color: transparent");
        ObservableList<String> options = FXCollections.observableArrayList("Income", "Expense");
        incomeExpenseBox.setItems(options);
        radioWallet.setToggleGroup(group);
        radioBank.setToggleGroup(group);
    }
    
    @FXML
    private void incomeExpenseBoxClicked(ActionEvent event) {
        String value = incomeExpenseBox.getValue();
        categories = new Categories();
        
        if(value.equals("Income")) {
            categoryBox.setItems(FXCollections.observableArrayList(categories.getIncomeCategories()));
        } else {
            categoryBox.setItems(FXCollections.observableArrayList(categories.getExpenseCategories()));
        }
    }
        
    @FXML
    public void handleSubmit(ActionEvent event) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        RadioButton chosenButton = (RadioButton) group.getSelectedToggle();
        
        if(chosenButton == null || amountField.getText().equals("")
                || descriptionField.getText().equals("") || categoryBox.getValue() == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Input Error Dialog");
            alert.setHeaderText("Information");
            alert.setContentText("You have to fill all needed info!");

            alert.showAndWait();
        } else {
            // check if amountfield is number entered
            try {
                Double.parseDouble(amountField.getText());
                walletBankTemp = chosenButton.getText();
                amountInfoTemp = amountField.getText();
                descriptionInfoTemp = descriptionField.getText();
                dateInfoTemp = sdf.format(new Date());
                categoryInfoTemp = categoryBox.getValue();

                if(incomeExpenseBox.getValue().equals("Expense")) {
                    amountInfoTemp = "-" + amountInfoTemp;
                }

                DBCreate db = new DBCreate();               
                db.insertTransaction(dateInfoTemp, descriptionInfoTemp, categoryInfoTemp, walletBankTemp, amountInfoTemp);

                
                Parent dashboard = FXMLLoader.load(getClass().getResource("/Views/Dashboard.fxml"));

                Scene dashboardScene = new Scene(dashboard, 700, 450);
                Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                dashboardStage.setScene(dashboardScene);
                dashboardStage.show();
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Transaction Dialog");
                alert.setHeaderText("Information");
                alert.setContentText("You have successfully entered transaction!");

                alert.showAndWait();
            }
            catch (NumberFormatException e) {
                 Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Input Error Dialog");
                alert.setHeaderText("Information");
                alert.setContentText("You entered text in field for amount!");

                alert.showAndWait();
            }
        }
    }
    
    @FXML
    public void handleCancel(ActionEvent event) throws Exception {
        Parent dashboard = FXMLLoader.load(getClass().getResource("/Views/Dashboard.fxml"));

        Scene dashboardScene = new Scene(dashboard, 700, 450);
        Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.show();
    }
}
