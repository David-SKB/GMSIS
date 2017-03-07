
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

/**
 *
 * @author joshuascott
 */
public class Test {
    
    public static void main(String[] args) {
     
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     alert.setTitle("Confirm deletion");
     alert.setHeaderText("Are you sure you want to delete this vehicle");
     alert.setContentText("Confirm that you want to delete this record");
     
    Optional<ButtonType> result = alert.showAndWait();
     if(result.get() == ButtonType.OK){
      alert.close();
     }
     else{
      alert.close();
     }
    }
}
