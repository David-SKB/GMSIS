/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import vehicle.*;


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
                     mileageTextField,
                     regSearchTextField,
                     manuSearchTextField,
                     currRegTextField,
                     currMakeTextField,
                     currModelTextField,
                     currEngineTextField,
                     currFuelTextField,
                     currColourTextField,
                     currMOTTextField,
                     currLastTextField,
                     currMileTextField,
                     updateRegTextField,
                     updateMakeTextField,
                     updateModelTextField,
                     updateEngineTextField,
                     updateFuelTextField,
                     updateColourTextField,
                     updateMOTTextField,
                     updateLastTextField,
                     updateMileTextField;
                     
   @FXML
   private Button carButton,
                  vanButton,
                  truckButton,
                  deleteButton,
                  editButton,
                  clearButton,
                  updateButton;
   @FXML
   private CheckBox warrantyCheckBox,
                    currWarrCheckBox,
                    updateWarrCheckBox;
   @FXML
   private ObservableList<Vehicle> list = FXCollections.observableArrayList();
   @FXML
   private TableView<Vehicle> vehDetails;
   @FXML
   private TableColumn regCol,idCol,makeCol,modelCol,engineCol,fuelCol,colCol,MOTCol,lastCol,
                       mileCol,warrCol;
   @FXML
   private Vehicle tempVehicle;
   
   
  @FXML
  public void updateButton(ActionEvent event){
   String oldReg = currRegTextField.getText();
   String updateReg = updateRegTextField.getText();
   String updateMake = updateMakeTextField.getText();
   String updateModel = updateModelTextField.getText();
   String updateEngine = updateEngineTextField.getText();
    int engine = Integer.parseInt(updateEngine);
   String updateFuel = updateFuelTextField.getText();
   String updateColour = updateColourTextField.getText();
   String updateMOT = updateMOTTextField.getText();
   String updateLast = updateLastTextField.getText();
   String updateMile = updateMileTextField.getText();
    int mile = Integer.parseInt(updateMile);
   boolean updateWarranty = updateWarrCheckBox.isSelected();
   vr.updateDetails(oldReg,updateReg,0,updateMake,updateModel,engine,updateFuel,updateColour,updateMOT,updateWarranty,updateLast,mile);
   clearButton(event);
   vehicleDisplay(event);
  }
  
  public void vehicleDisplay(ActionEvent event){
   list.removeAll(list);
   ArrayList<Vehicle> all = new ArrayList<>();
   all = loadVehicles();
   list.addAll(all);
   vehDetails.setEditable(true);
   
    regCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle, String>("registration"));
    //edit cell in tableview
    /*regCol.setCellFactory(TextFieldTableCell.forTableColumn());
    regCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setRegistration(t.getNewValue());
        }
    }
);*/
    makeCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("model"));
    
   /* makeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    makeCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setMake(t.getNewValue());
        }
    }
);*/
    
    modelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("make"));
    
   /* modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
    modelCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setModel(t.getNewValue());
        }
    }
); */
    
    engineCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,Integer>("engineSize"));
    
    /*engineCol.setCellFactory(TextFieldTableCell.forTableColumn());
    engineCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, Integer>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, Integer> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setEngineSize(t.getNewValue());
        }
    }
);*/
    
    fuelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("fuelType"));
    
   /* fuelCol.setCellFactory(TextFieldTableCell.forTableColumn());
    fuelCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setFuelType(t.getNewValue());
        }
    }
);*/
    
    colCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("colour"));
    
   /* colCol.setCellFactory(TextFieldTableCell.forTableColumn());
    colCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setColour(t.getNewValue());
        }
    }
);*/
    
    MOTCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("MOTRenewal"));
    
   /* MOTCol.setCellFactory(TextFieldTableCell.forTableColumn());
    MOTCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setMOTRenewal(t.getNewValue());
        }
    }
);*/
    
    warrCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("warranty"));
    
    /*warrCol.setCellFactory(TextFieldTableCell.forTableColumn());
    warrCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, Boolean>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, Boolean> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setWarranty(t.getNewValue());
        }
    }
);*/
    
    lastCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("lastService"));
    
   /* lastCol.setCellFactory(TextFieldTableCell.forTableColumn());
    lastCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, String>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, String> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setLastService(t.getNewValue());
        }
    }
); */
    
    mileCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,Integer>("currentMile"));
    
    /*mileCol.setCellFactory(TextFieldTableCell.forTableColumn());
    mileCol.setOnEditCommit(
    new EventHandler<CellEditEvent<Vehicle, Integer>>() {
        @Override
        public void handle(CellEditEvent<Vehicle, Integer> t) {
            ((Vehicle) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setCurrentMile(t.getNewValue());
        }
    }
);*/
       
    
    vehDetails.setItems(list);
    
    vehDetails.setRowFactory((TableView<Vehicle> tv) -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempVehicle = row.getItem();
                     loadDataChange(tempVehicle);                     
                }
            });
            return row;
        });
      
  }
  
  /*Stage editStage;
    Parent root;
     editStage = new Stage();
     root = FXMLLoader.load(getClass().getResource("EditTab.FXML"));
     editStage.setScene(new Scene(root));
     editStage.setTitle("Edit tab");*/
  
  @FXML
  public void editButton(ActionEvent event)throws Exception{
   //set current values in old section
   currRegTextField.setText(regTextField.getText());
   currMakeTextField.setText(makeTextField.getText());
   currModelTextField.setText(modelTextField.getText());
   currEngineTextField.setText(engineTextField.getText());
   currFuelTextField.setText(fuelTextField.getText());
   currColourTextField.setText(colourTextField.getText());
   currMOTTextField.setText(MOTTextField.getText());
   currLastTextField.setText(lastTextField.getText());
   currMileTextField.setText(mileageTextField.getText());
   currWarrCheckBox.setSelected(warrantyCheckBox.isSelected());
   //set current values in updated section
   updateRegTextField.setText(regTextField.getText());
   updateMakeTextField.setText(makeTextField.getText());
   updateModelTextField.setText(modelTextField.getText());
   updateEngineTextField.setText(engineTextField.getText());
   updateFuelTextField.setText(fuelTextField.getText());
   updateColourTextField.setText(colourTextField.getText());
   updateMOTTextField.setText(MOTTextField.getText());
   updateLastTextField.setText(lastTextField.getText());
   updateMileTextField.setText(mileageTextField.getText());
   updateWarrCheckBox.setSelected(warrantyCheckBox.isSelected());
  }
  
 
  
  public void loadDataChange(Vehicle temp){
   regTextField.setText(temp.getRegistration());
   modelTextField.setText(temp.getModel());
   makeTextField.setText(temp.getMake());
   engineTextField.setText(String.valueOf(temp.getEngineSize()));
   fuelTextField.setText(temp.getFuelType());
   colourTextField.setText(temp.getColour());
   MOTTextField.setText(String.valueOf(temp.getMOTRenewal()));
   warrantyCheckBox.setSelected(temp.getWarranty());
   lastTextField.setText(temp.getLastService());
   mileageTextField.setText(String.valueOf(temp.getCurrentMile()));
  }
   
  public ArrayList<Vehicle> loadVehicles(){
   db.connect();
    ArrayList<Vehicle> vehList = vr.getAllVehicles();
     db.closeConnection();
     return vehList;
  }
  
  @FXML
  public void searchButon(ActionEvent event){
   if(!regSearchTextField.getText().isEmpty() && !manuSearchTextField.getText().isEmpty()){
    //do both
    ArrayList<Vehicle>both = new ArrayList<>();
    String regText = regSearchTextField.getText();
    String manuText = manuSearchTextField.getText();
     both = vr.searchBoth(regText,manuText);
     list.clear();
     list.addAll(both);
     vehDetails.setItems(list);
   }
   else if(!regSearchTextField.getText().isEmpty() && manuSearchTextField.getText().isEmpty()){
    //do reg
    ArrayList<Vehicle>reg = new ArrayList<>();
    String text = regSearchTextField.getText();
     reg = vr.searchReg(text);
     list.clear();
     list.addAll(reg);
     vehDetails.setItems(list);
   }
   else{
    //do manu
    ArrayList<Vehicle>manu = new ArrayList<>();
    String text = manuSearchTextField.getText();
     manu = vr.searchManu(text);
     list.clear();
     list.addAll(manu);
     vehDetails.setItems(list);
   }   
  }
  
  @FXML 
  public void deleteButton(ActionEvent event){
   String reg = regTextField.getText();    
    componentLoader cl = new componentLoader();
     if(cl.showDialog()){
      vr.delete(reg);
      cl.showSuccess();
      vehicleDisplay(event);
      clearButton(event);
     }
     else{
      cl.showFailure();
     }
  }
  
  @FXML
  public void clearButton(ActionEvent event){
     //top text fields
     regTextField.clear();
     modelTextField.clear();
     makeTextField.clear();
     engineTextField.clear();
     fuelTextField.clear();
     colourTextField.clear();
     MOTTextField.clear();
     lastTextField.clear();
     mileageTextField.clear();
     warrantyCheckBox.setSelected(false);
     //edit current text fields
     currRegTextField.clear();
     currMakeTextField.clear();
     currModelTextField.clear();
     currEngineTextField.clear();
     currFuelTextField.clear();
     currColourTextField.clear();
     currMOTTextField.clear();
     currLastTextField.clear();
     currMileTextField.clear();
     currWarrCheckBox.setSelected(false);
     //edit old text fields
     updateRegTextField.clear();
     updateMakeTextField.clear();
     updateModelTextField.clear();
     updateEngineTextField.clear();
     updateFuelTextField.clear();
     updateColourTextField.clear();
     updateMOTTextField.clear();
     updateLastTextField.clear();
     updateMileTextField.clear();
     updateWarrCheckBox.setSelected(false);
 }
    

  @FXML
  public void addSearchedVehicles(ObservableList<Vehicle>list,ArrayList<Vehicle>data){
   if(data != null){
    for(Vehicle v : data){
      list.add(v);
    }   
   }   
  }
  
  public ArrayList<Vehicle> regSearch(String reg){
    ArrayList<Vehicle>result = vr.searchReg(reg);
     regSearchTextField.clear();
     return result;
  }
  
  public ArrayList<Vehicle> manuSearch(String manu){
   ArrayList<Vehicle>result = vr.searchManu(manu);
    manuSearchTextField.clear();
    return result;
  }
   
    @FXML
  public void addCarButton(ActionEvent event){
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
     vehicleDisplay(event);
  }
  
    @FXML
  public void addVanButton(ActionEvent event){
    addVanDetails(event); 
     regTextField.clear();
     modelTextField.clear();
     makeTextField.clear();
     engineTextField.clear();
     fuelTextField.clear();
     colourTextField.clear();
     MOTTextField.clear();
     lastTextField.clear();
     mileageTextField.clear();
     vehicleDisplay(event);
  }
  
    @FXML
  public void addTruckButton(ActionEvent event){
    addTruckDetails(event);
     regTextField.clear();
     modelTextField.clear();
     makeTextField.clear();
     engineTextField.clear();
     fuelTextField.clear();
     colourTextField.clear();
     MOTTextField.clear();
     lastTextField.clear();
     mileageTextField.clear();
     vehicleDisplay(event);
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
   
   boolean warranty; 
   warranty = warrantyCheckBox.isSelected();
    
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = Integer.parseInt(mileageString);
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      vr.addCar(reg,0,make,model,engine,fuel,colour,MOT,warranty,last,mileage);
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
    
   boolean warranty;
   warranty = warrantyCheckBox.isSelected();
    
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = Integer.parseInt(mileageString);
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      vr.addVan(reg,0,make,model,engine,fuel,colour,MOT,warranty,last,mileage);
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
    
   boolean warranty;
   warranty = warrantyCheckBox.isSelected();
    
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = Integer.parseInt(mileageString);
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      vr.addTruck(reg,0,make,model,engine,fuel,colour,MOT,warranty,last,mileage);
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
