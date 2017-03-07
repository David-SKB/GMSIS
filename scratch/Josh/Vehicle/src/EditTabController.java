/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import vehicle.Vehicle;

/**
 * FXML Controller class
 *
 * @author joshuascott
 */
public class EditTabController {

    @FXML
    private AnchorPane newWarrTextField;
    @FXML
    private TextField currRegTextField;
    @FXML
    private TextField currMakeTextField;
    @FXML
    private TextField currModelTextField;
    @FXML
    private TextField currEngineTextField;
    @FXML
    private TextField currFuelTextField;
    @FXML
    private TextField currColourTextField;
    @FXML
    private TextField currMOTTextField;
    @FXML
    private TextField currLastTextField;
    @FXML
    private TextField currMileTextField;
    @FXML
    private CheckBox currWarrCheckBox;
    @FXML
    private TextField newRegTextField;
    @FXML
    private TextField newMakeTextField;
    @FXML
    private TextField newModelTextField;
    @FXML
    private TextField newEngineTextField;
    @FXML
    private TextField newFuelTextField;
    @FXML
    private TextField newColourTextField;
    @FXML
    private TextField newMOTTextField;
    @FXML
    private TextField newLastTextField;
    @FXML
    private TextField newMileTextField;
    @FXML
    private CheckBox newWarrCheckBox;
    @FXML
    private Button updateButton;
    @FXML
    private Button quitButton;
        
    public void setFields(Vehicle v){
     currRegTextField.setText(v.getRegistration());
     currMakeTextField.setText(v.getMake());
     currModelTextField.setText(v.getModel());
     currEngineTextField.setText(String.valueOf(v.getEngineSize()));
     currFuelTextField.setText(v.getFuelType());
     currColourTextField.setText(v.getColour());
     currMOTTextField.setText(String.valueOf(v.getMOTRenewal()));
     currWarrCheckBox.setSelected(v.getWarranty());
     currLastTextField.setText(v.getLastService());
     currMileTextField.setText(String.valueOf(v.getCurrentMile()));   
    }
    
    @FXML   
    public void editButton(){
        
    }
    @FXML
    public void quitButton(){
      
    }
}
