/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;
import vehicles.logic.Warranty;

/**
 * FXML Controller class
 *
 * @author joshuascott
 */
public class EditTabController{
    
    VehicleRegistry vr = VehicleRegistry.getInstance();

@FXML
private Label currentDetailsLabel,
              currRegLabel,
              currMakeLabel,
              currModelLabel,
              currEngineLabel,
              currFuelLabel,
              currColourLabel,
              currMOTLabel,
              currLastLabel,
              currMileageLabel,
              currWarrantyNameLabel,
              currWarrantyAddressLabel,
              currWarrantyExpiryLabel,
              updatedDetailsLabel,
              updateRegLabel,
              updateMakeLabel,
              updateModelLabel,
              updateEngineLabel,
              updateFuelLabel,
              updateColourLabel,
              updateMOTLabel,
              updateLastLabel,
              updateMileageLabel,
              updateWarrantyNameLabel,
              updateWarrantyAddressLabel,
              updateWarrantyExpiryLabel;
@FXML
private CheckBox currWarrantyCheckBox,updateWarrantyCheckBox;
@FXML
private Button updateButton;
@FXML
private TextField currRegTextField,
                  currMakeTextField,
                  currModelTextField,
                  currEngineTextField,
                  currFuelTextField,
                  currColourTextField,
                  currMOTTextField,
                  currLastTextField,
                  currMileageTextField,
                  updateRegTextField,
                  updateMakeTextField,
                  updateModelTextField,
                  updateEngineTextField,
                  updateFuelTextField,
                  updateColourTextField,
                  updateMOTTextField,
                  updateLastTextField,
                  updateMileageTextField,
                  currWarrantyNameTextField,
                  currWarrantyAddressTextField,
                  currWarrantyExpiryTextField,
                  updateWarrantyNameTextField,
                  updateWarrantyAddressTextField,
                  updateWarrantyExpiryTextField;
 
 public void updateButton(ActionEvent event){
   String oldReg = currRegTextField.getText();
   String updateReg = updateRegTextField.getText();
   String updateMake = updateMakeTextField.getText();
   String updateModel = updateModelTextField.getText();
   String updateEngine = updateEngineTextField.getText();
    double engine = 0;
    try{
     engine = Double.parseDouble(updateEngine);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
      cl.showEngineFailure();
    }
   String updateFuel = updateFuelTextField.getText();
   String updateColour = updateColourTextField.getText();
   String updateMOT = updateMOTTextField.getText();
   String updateLast = updateLastTextField.getText();
   String updateMile = updateMileageTextField.getText();
    int mile = Integer.parseInt(updateMile);
   boolean updateWarranty = updateWarrantyCheckBox.isSelected();
   String warrantyName = updateWarrantyNameTextField.getText();
   String warrantyAddress = updateWarrantyAddressTextField.getText();
   String expiry = updateWarrantyExpiryTextField.getText();
   //if has warranty and update to no warranty delete warranty record in db
   if(currWarrantyCheckBox.isSelected() && !updateWarrantyCheckBox.isSelected()){
     vr.deleteWarranty(updateReg);
   }
   if(!currWarrantyCheckBox.isSelected() && updateWarrantyCheckBox.isSelected()){
    vr.addWarranty(oldReg, warrantyName, warrantyAddress, expiry);
   }
   vr.updateDetails(oldReg,updateReg,0,updateMake,updateModel,engine,updateFuel,updateColour,updateMOT,updateWarranty,updateLast,mile);
   vr.updateWarranty(oldReg,warrantyName,warrantyAddress,expiry);
    componentLoader cl = new componentLoader();
     cl.showUpdateSuccess();
 }
    
 public void set(Vehicle v){
     //current details
  currRegTextField.setText(v.getRegistration());
  currMakeTextField.setText(v.getMake());
  currModelTextField.setText(v.getModel());
  double engineDouble = v.getEngineSize();
  String engine = String.valueOf(engineDouble);
  currEngineTextField.setText(engine);
  currFuelTextField.setText(v.getFuelType());
  currColourTextField.setText(v.getColour());
  currMOTTextField.setText(v.getMOTRenewal());
  currLastTextField.setText(v.getLastService());
  int miles = v.getCurrentMile();
  String mileage = String.valueOf(miles);
  currMileageTextField.setText(mileage);
  if(v.getWarranty()){
   ArrayList<Warranty> list = vr.getWarranty(v.getRegistration());
   Warranty warranty = list.get(0);
   currWarrantyCheckBox.setSelected(true);
   currWarrantyNameTextField.setText(warranty.getName());
   currWarrantyAddressTextField.setText(warranty.getAddress());
   currWarrantyExpiryTextField.setText(warranty.getExpiry());
  }
  //updated details
  updateRegTextField.setText(v.getRegistration());
  updateMakeTextField.setText(v.getMake());
  updateModelTextField.setText(v.getModel());
  double en = v.getEngineSize();
  String eng = String.valueOf(en);
  updateEngineTextField.setText(eng);
  updateFuelTextField.setText(v.getFuelType());
  updateColourTextField.setText(v.getColour());
  updateMOTTextField.setText(v.getMOTRenewal());
  updateLastTextField.setText(v.getLastService());
  int currMiles = v.getCurrentMile();
  String currMile = String.valueOf(miles);
  updateMileageTextField.setText(currMile);
  if(v.getWarranty()){
   ArrayList<Warranty> list = vr.getWarranty(v.getRegistration());
   Warranty warranty = list.get(0);
   updateWarrantyCheckBox.setSelected(true);
   updateWarrantyNameTextField.setText(warranty.getName());
   updateWarrantyAddressTextField.setText(warranty.getAddress());
   updateWarrantyExpiryTextField.setText(warranty.getExpiry());
  }
 }
 
}