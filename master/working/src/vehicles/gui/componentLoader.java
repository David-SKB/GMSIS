package vehicles.gui;

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
/**
 *
 author Joshua Adam Scott
 */
public class componentLoader{
    
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
   
   public void showWarrantyFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Warranty fields left blank");
     alert.setHeaderText("A warranty information field has been left blank");
     alert.showAndWait();
   }
   
    public void showEngineFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Engine text field error");
     alert.setHeaderText("Error");
     alert.setContentText("Engine field must be in the form of number.number");
     alert.showAndWait();
   }
    
    public void showMileageFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Mileage text field error");
     alert.setHeaderText("Error");
     alert.setContentText("Mileage text field must be a whole number");
     alert.showAndWait();
   }
    
    public void showFuelStringFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Text field error");
     alert.setHeaderText("Error");
     alert.setContentText("Fuel text field can only contain letters A-Z");
     alert.showAndWait();
   }
    
     public void showColourStringFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Text field error");
     alert.setHeaderText("Error");
     alert.setContentText("Fuel text field can only contain letters A-Z");
     alert.showAndWait();
   }
     
     public void showManuStringFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Text field error");
     alert.setHeaderText("Error");
     alert.setContentText("Can only have letters in manufacturer search field");
     alert.showAndWait();
   }
     
     public void showNoResults(){
     Alert alert = new Alert(AlertType.INFORMATION);
     alert.setTitle("No results");
     alert.setHeaderText(null);
     alert.setContentText("Sorry your search returned no results");
     alert.showAndWait();
   }
     

     public void showDateFailure(){
     Alert alert = new Alert(AlertType.ERROR);
     alert.setTitle("Date error");
     alert.setHeaderText("Error");
     alert.setContentText("Date not in the correct format of dd/mm/yyyy");
     alert.showAndWait();
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