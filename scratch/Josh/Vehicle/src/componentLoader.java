import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;

import javafx.stage.Stage;
import vehicle.Vehicle;
/**
 *
 author Joshua Adam Scott
 */
public class componentLoader extends Application{
    private Parent rootNode;
    
    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
       Parent parent = FXMLLoader.load(getClass().getResource("vehicle.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show();
         
 
    }
    
    public void editTab(Stage editStage,Vehicle v)throws Exception{
     Parent parent = FXMLLoader.load(getClass().getResource("EditTab.fxml"));
      Scene scene = new Scene(parent);
      editStage.setTitle("Edit tab");
      editStage.setScene(scene);
      editStage.show();
    }
    
   public boolean showDialog(){
     boolean decision = false;
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     alert.setTitle("Confirm deletion");
     alert.setHeaderText("Are you sure you want to delete this vehicle");
     alert.setContentText("Confirm that you want to delete this record");
     
    Optional<ButtonType> result = alert.showAndWait();
     if(result.get() == ButtonType.OK){
      alert.close();
      decision = true;
     }
     else{
      alert.close();
     }
    return decision;
   }
   
   public void showSuccess(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setTitle("SUCCESS");
     alert.setHeaderText(null);
     alert.setContentText("Record successfully deleted");  
   }
   public void showFailure(){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setTitle("FAILURE");
     alert.setHeaderText(null);
     alert.setContentText("Record not deleted");   
   }
 }