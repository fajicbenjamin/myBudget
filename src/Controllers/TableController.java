package Controllers;

import Model.Categories;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.DBConnect;
import Model.Input;
import Model.MyMenu;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Statement;
import java.util.Optional;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Benjamin
 */
public class TableController implements Initializable {
    
    
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
    
    // BUTTONS
    @FXML
    private Button backButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearFilter;
    
    // SEARCH FIELDS
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXTextField descriptionField;
    @FXML
    private JFXTextField categoryField;
    
    private String filterDate = "";
    private String filterDescription = "";
    private String filterCategory = "";
    
    
    //TABLE VIEW AND DATA
    private ObservableList<Input> data;
    @FXML
    private TableView<Input> tableview;
    @FXML
    private TableColumn c1;
    @FXML
    private TableColumn c2;
    @FXML
    private TableColumn c3;
    @FXML
    private TableColumn c4;
    @FXML
    private TableColumn c5;
    
    Categories categories;

    /*MAIN EXECUTOR
    public static void main(String[] args) {
        launch(args);
    }*/

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buildData();
    }    
    
    // REGULAR CONNECTION TO DATABASE AND FETCH ALL DATA
    public void buildData(){
        DBConnect connection = new DBConnect();
        data = FXCollections.observableArrayList();
        
        try {
            //SQL FOR SELECTING ALL INPUTS OF BUDGET
            String SQL = "SELECT * from BENJO.BUDGET ORDER BY date DESC";
            //ResultSet
            ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);

            //setCellValueFactory
            c1.setCellValueFactory(
                new PropertyValueFactory<>("timestamp"));
            c2.setCellValueFactory(
                new PropertyValueFactory<>("description"));
            c3.setCellValueFactory(
                new PropertyValueFactory<>("category"));
            c4.setCellValueFactory(
                new PropertyValueFactory<>("type"));
            c5.setCellValueFactory(
                new PropertyValueFactory<>("amount"));
            
            //fetch data
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));
            }
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
            
            //DELETE ROW FROM TABLE
            tableview.setRowFactory(new Callback<TableView<Input>, TableRow<Input>>() {  
            @Override  
            public TableRow<Input> call(TableView<Input> tableView) {  
                final TableRow<Input> row = new TableRow<>();  
                final ContextMenu contextMenu = new ContextMenu();  
                final MenuItem removeMenuItem = new MenuItem("Delete");  
                removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {  
                    @Override  
                    public void handle(ActionEvent event) { 
                        
                        //ASK IF WANT TO DELETE
                        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                        alert1.setTitle("Confirmation Dialog");
                        alert1.setHeaderText("Are you sure you want to delete this row?");
                        //alert1.setContentText("Are you ok with this?");

                        Optional<ButtonType> result = alert1.showAndWait();
                        if (result.get() == ButtonType.OK){
                        
                            // DELETE FROM DATABASE
                            DBConnect connection = new DBConnect();
                            Input selectedRow = tableView.getSelectionModel().getSelectedItem();

                            try {
                                //SQL FOR DELETING ROW
                                String SQL = "DELETE FROM BENJO.BUDGET WHERE date LIKE '" + selectedRow.getTimestamp() + "%'";
                                Statement stmt = null;
                                stmt = connection.getConnection().createStatement();
                                stmt.execute(SQL);
                                stmt.close();
                            } catch(Exception e){
                                e.printStackTrace();
                                System.out.println("Error on Building Data"); 
                            }
                            // DELETE FROM GUI
                            tableview.getItems().remove(row.getItem());  
                        } else {
                            // ... user chose CANCEL or closed the dialog
                        } 
                    }
                });  
                contextMenu.getItems().add(removeMenuItem);  
               // Set context menu on row, but use a binding to make it only show for non-empty rows:  
                row.contextMenuProperty().bind(  
                        Bindings.when(row.emptyProperty())  
                        .then((ContextMenu)null)  
                        .otherwise(contextMenu)  
                );  
                return row ;  
            }  
        });  
            
        } catch (Exception e){
              e.printStackTrace();
              System.out.println("Error on Fetching Data");             
        }
    }

    @FXML
    private void handleMenuItem(ActionEvent event) throws IOException {
        MyMenu menu = new MyMenu("table", event, fileClose, addCategory, removeCategory, helpAbout, resetData, amountInfo, descriptionInfo, dateInfo, balanceAmount, bankAmount, tableview);
    }
    
    @FXML
    private void handleFilter(ActionEvent event) {
        LocalDate isoDate = datePicker.getValue();
        ChronoLocalDate chronoDate = ((isoDate != null) ? datePicker.getChronology().date(isoDate) : null);
        if (chronoDate != null)
            filterDate = datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        filterDescription = descriptionField.getText();
        filterCategory = categoryField.getText();
        if(chronoDate == null && filterDescription.equals("") && filterCategory.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Filter search Dialog");
            alert.setHeaderText("Information");
            alert.setContentText("You did not enter any parameters for search!");

            alert.showAndWait();
        } else {
            filterDateBuild(filterDate, filterDescription, filterCategory);
        }
        
    }

    @FXML
    private void backButtonHandle(ActionEvent event) throws Exception {
        Parent transaction = FXMLLoader.load(getClass().getResource("/Views/Dashboard.fxml"));

        Scene transactionScene = new Scene(transaction, 700, 450);
        Stage transactionStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        transactionStage.setScene(transactionScene);
        transactionStage.show();
    }
    
    public void filterDateBuild(String date, String description, String category){
        DBConnect connection = new DBConnect();
        data = FXCollections.observableArrayList();
        
        try {
            //setCellValueFactory
            c1.setCellValueFactory(
                new PropertyValueFactory<>("timestamp"));
            c2.setCellValueFactory(
                new PropertyValueFactory<>("description"));
            c3.setCellValueFactory(
                new PropertyValueFactory<>("category"));
            c4.setCellValueFactory(
                new PropertyValueFactory<>("type"));
            c5.setCellValueFactory(
                new PropertyValueFactory<>("amount"));
            
            if (!date.equals("") && !description.equals("") && !category.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE date LIKE '" + date + "%' AND DESCRIPTION LIKE '%" + description +"%' AND CATEGORY LIKE '%" + category + "%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }   
                tableview.setItems(data);
            } else if (!date.equals("") && !description.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE date like '" + date + "%' AND DESCRIPTION LIKE '%" + description +"%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }   
                tableview.setItems(data);
            } else if (!date.equals("") && !category.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE date like '" + date + "%' AND CATEGORY LIKE '%" + category + "%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }   
                tableview.setItems(data);
            } else if (!description.equals("") && !category.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE description like '%" + description + "%' AND CATEGORY LIKE '%" + category + "%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }   
                tableview.setItems(data);
            } else if (!date.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE date like '" + date + "%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
            
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }
                tableview.setItems(data);
            } else if (!description.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE DESCRIPTION LIKE '%" + description +"%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }   
                tableview.setItems(data);
            } else if (!category.equals("")) {
                String SQL = "SELECT * from BENJO.BUDGET WHERE CATEGORY LIKE '%" + category + "%' ORDER BY date";
                ResultSet rs = connection.getConnection().createStatement().executeQuery(SQL);
                while(rs.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    data.add(new Input(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4)));

                }   
                tableview.setItems(data);
            }
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");             
        }
    }
    
    @FXML
    private void handleClearFilter (ActionEvent event) {
        descriptionField.setText("");
        categoryField.setText("");
        datePicker.getEditor().clear();
        datePicker.setValue(null);
        filterDate = "";
        filterDescription = "";
        filterCategory = "";
        buildData();
    }
        
}
