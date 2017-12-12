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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

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
        Stage dashboardStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
               
        Scene dashboardScene = new Scene(dashboard, 700, 450);
      
        dashboardStage.setScene(dashboardScene);
        dashboardStage.getIcons().add(new Image("/img/icon.png"));
        dashboardStage.show();
        
        
    }
        
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBCreate dbCreate = new DBCreate();
        dbCreate.createTables();
        try {
            dbCreate.checkIfUserExists();
        } catch (SQLException ex) {
            Logger.getLogger(WelcomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
