package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;

/**
 *
 * @author benjaminfajic
 */
public class MyDialog {
    
    public MyDialog() {
        
    }
    
    public String chooseIncomeExpense() {
        List<String> choices = new ArrayList<>();
        String choice = "";
        choices.add("Income");
        choices.add("Expense");

        ChoiceDialog<String> dialogChoice = new ChoiceDialog<>("", choices);
        dialogChoice.setTitle("Choice Dialog");
        dialogChoice.setHeaderText("Chose wheter you want to record income or expense");
        dialogChoice.setContentText("Choose your action:");

        // get result of choice dialog
        Optional<String> resultChoice = dialogChoice.showAndWait();
        if (resultChoice.isPresent()){
            choice = resultChoice.get(); // for example, handle it like this
        }
        
        return choice;
    }
    
    public String addCategory() {
        String nameOfCategory = "";
        TextInputDialog dialogAdd = new TextInputDialog();
        dialogAdd.setTitle("Changing Categories Dialog");
        dialogAdd.setHeaderText("Enter name of category you want to add");
        dialogAdd.setGraphic(new ImageView(this.getClass().getResource("/img/icon.png").toString()));

        // Traditional way to get the response value.
        Optional<String> result = dialogAdd.showAndWait();
        if (result.isPresent()){
            nameOfCategory = result.get();
        }
        return nameOfCategory;
    }
    
    public String removeCategory() {
        String nameOfCategory = "";
        TextInputDialog dialogAdd = new TextInputDialog();
        dialogAdd.setTitle("Changing Categories Dialog");
        dialogAdd.setHeaderText("Enter name of category you want to remove");
        dialogAdd.setGraphic(new ImageView(this.getClass().getResource("/img/icon.png").toString()));

        // Traditional way to get the response value.
        Optional<String> result = dialogAdd.showAndWait();
        if (result.isPresent()){
            nameOfCategory = result.get();
        }
        return nameOfCategory;
    }
    
    public void categoryAdded() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Add Category Dialog");
        alert.setHeaderText("Information");
        alert.setContentText("You have successfully added new category!");

        alert.showAndWait();
    }
    
    public void categoryAlreadyExist() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Add Category Dialog");
        alert.setHeaderText("Information");
        alert.setContentText("Entered category is registered before! \n(or your input is empty)");

        alert.showAndWait();
    }
    
    public void categoryRemoved() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Remove Category Dialog");
        alert.setHeaderText("Information");
        alert.setContentText("You have successfully removed category!");

        alert.showAndWait();
    }
    
    public void categoryAlreadyRemoved() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Remove Category Dialog");
        alert.setHeaderText("Information");
        alert.setContentText("Entered category does not exist! \n(or your input is empty)");

        alert.showAndWait();
    }
}
