/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author joshuascott
 */
public class VehicleGUIController {

    DBConnection db = DBConnection.getInstance();
    VehicleRegistry vr = VehicleRegistry.getInstance();
    
   @FXML
   private TextField regTextField,
                     modelTextField,
                     makeTextField,
                     engineTextField,
                     fuelTextField,
                     colourTextField,
                     MOTTextField,
                     lastTextField,
                     mileageTextField;
   @FXML
   private Button carButton,
                  vanButton,
                  truckButton;
   
   
  public void carButton(ActionEvent event){
    addCarDetails(event);
     regTextField.clear();
     modelTextField.clear();
     makeTextField.clear();
     engineTextField.clear();
     fuelTextField.clear();
     colourTextField.clear();
     MOTTextField.clear();
     lastTextField.clear();
     mileageTextField.clear();
  }
   
  public void addCarDetails(ActionEvent event){
   boolean regCheck,
           modelCheck,
           makeCheck,
           eSizeCheck,
           fuelCheck,
           colourCheck,
           MOTCheck,
           LastServiceCheck,
           mileageCheck = true;
   
   String reg = regTextField.getText();
    regCheck = checkTextField(reg);
    
   String make = makeTextField.getText();
    makeCheck = checkTextField(make); 
    
   String model = modelTextField.getText();
    modelCheck = checkTextField(model);
    
   String engineString = engineTextField.getText();
    eSizeCheck = checkTextField(engineString);
    int engine = Integer.parseInt(engineString);
    
   String fuel = fuelTextField.getText();
    fuelCheck = checkTextField(fuel);
    
   String colour = colourTextField.getText();
    colourCheck = checkTextField(colour);
    
   String MOT = MOTTextField.getText();
    MOTCheck = checkTextField(MOT);
    
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = Integer.parseInt(mileageString);
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      vr.addCar(reg,0,make,model,engine,fuel,colour,MOT,last,mileage);
    }
    else{
     JOptionPane.showMessageDialog(null,"Empty field");   
    }
 }
  
 public void addVanDetails(ActionEvent event){
   boolean regCheck,
           modelCheck,
           makeCheck,
           eSizeCheck,
           fuelCheck,
           colourCheck,
           MOTCheck,
           LastServiceCheck,
           mileageCheck = true;
   
   String reg = regTextField.getText();
    regCheck = checkTextField(reg);
    
   String make = makeTextField.getText();
    makeCheck = checkTextField(make); 
    
   String model = modelTextField.getText();
    modelCheck = checkTextField(model);
    
   String engineString = engineTextField.getText();
    eSizeCheck = checkTextField(engineString);
    int engine = Integer.parseInt(engineString);
    
   String fuel = fuelTextField.getText();
    fuelCheck = checkTextField(fuel);
    
   String colour = colourTextField.getText();
    colourCheck = checkTextField(colour);
    
   String MOT = MOTTextField.getText();
    MOTCheck = checkTextField(MOT);
    
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = Integer.parseInt(mileageString);
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      vr.addVan(reg,0,make,model,engine,fuel,colour,MOT,last,mileage);
    }
    else{
     JOptionPane.showMessageDialog(null,"Empty field");   
    }
 }
    
 public void addTruckDetails(ActionEvent event){
   boolean regCheck,
           modelCheck,
           makeCheck,
           eSizeCheck,
           fuelCheck,
           colourCheck,
           MOTCheck,
           LastServiceCheck,
           mileageCheck = true;
   
   String reg = regTextField.getText();
    regCheck = checkTextField(reg);
    
   String make = makeTextField.getText();
    makeCheck = checkTextField(make); 
    
   String model = modelTextField.getText();
    modelCheck = checkTextField(model);
    
   String engineString = engineTextField.getText();
    eSizeCheck = checkTextField(engineString);
    int engine = Integer.parseInt(engineString);
    
   String fuel = fuelTextField.getText();
    fuelCheck = checkTextField(fuel);
    
   String colour = colourTextField.getText();
    colourCheck = checkTextField(colour);
    
   String MOT = MOTTextField.getText();
    MOTCheck = checkTextField(MOT);
    
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = Integer.parseInt(mileageString);
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      vr.addTruck(reg,0,make,model,engine,fuel,colour,MOT,last,mileage);
    }
    else{
     JOptionPane.showMessageDialog(null,"Empty field");   
    }
 }
  
  private boolean checkTextField(String text){
   if(text.equals("")){
    return false;   
   }   
   else{
    return true;   
   }
  }
  
}
