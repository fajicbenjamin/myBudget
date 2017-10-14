package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import Model.DBCreate;

/**
 *
 * @author benjaminfajic
 */
public class WelcomeController implements Initializable {
    
    @FXML
    private Button startApp;
    
    @FXML
    private void startAppClicked(ActionEvent event) throws Exception {
        Parent dashboard = FXMLLoader.load(getClass().getResource("/Views/Dashboard.fxml"));
            
        Scene dashboardScene = new Scene(dashboard, 700, 450);
        Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.show();
        
    }
        
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBCreate dbCreate = new DBCreate();
        dbCreate.createTables();
    }    
    
}
