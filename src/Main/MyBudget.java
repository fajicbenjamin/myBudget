package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author benjaminfajic
 */
public class MyBudget extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Welcome.fxml"));
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/img/icon.png"));
        stage.setTitle("myBudget (version 1.0.3)");
        stage.setScene(scene);
        stage.show();
        
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
