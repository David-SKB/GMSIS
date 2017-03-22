package vehicles.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.DBConnection;
import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import vehicles.logic.Template;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;


/**
 * FXML Controller class
 *
 * @author joshuascott
 */
public class VehicleGUIController implements Initializable {

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
                     updateMileTextField,
                     warrantyNameTextField,
                     warrantyAddressTextField,
                     warrantyExpiryTextField,
                     currWarrantyNameTextField,
                     currWarrantyAddressTextField,
                     currWarrantyExpiryTextField,
                     updateWarrantyNameTextField,
                     updateWarrantyAddressTextField,
                     updateWarrantyExpiryTextField,
                     customerIDTextField;
                     
   @FXML
   private Button carButton,
                  vanButton,
                  truckButton,
                  deleteButton,
                  editButton,
                  clearButton,
                  updateButton,
                  selectCustomerButton;
   @FXML
   private CheckBox warrantyCheckBox,
                    currWarrCheckBox,
                    updateWarrCheckBox;
   @FXML
   private ChoiceBox customerSelectorChoiceBox;
   @FXML
   private ObservableList<Vehicle> list = FXCollections.observableArrayList();
   @FXML
   private ObservableList<Customer> activeCustomers = FXCollections.observableArrayList();
   @FXML
   private TableView<Vehicle> vehDetails;
   @FXML
   private TableColumn regCol,idCol,makeCol,modelCol,engineCol,fuelCol,colCol,MOTCol,lastCol,
                       mileCol,warrCol,typeCol;
   @FXML
   private Vehicle tempVehicle;
   @FXML
   private Customer tempCustomer;
   @FXML
   ListView<Template> templateList = new ListView<Template>();
   @FXML
   ObservableList<Template> carsTemplate = FXCollections.observableArrayList();
    
   @FXML
   public void addTemplateCars(){
   Template car1 = new Template("Ford","Model",1.6,"PETROL");
   Template car2 = new Template("VW","Model",1.6,"PETROL");
   Template car3 = new Template("BMW","Model",1.6,"PETROL");
   Template car4 = new Template("Mercedes","Model",1.6,"PETROL");
   Template car5 = new Template("Mazda","Model",1.6,"PETROL");
   Template car6 = new Template("Audi","Model",1.6,"PETROL");
   Template car7 = new Template("Car1","Model",1.6,"PETROL");
   Template car8 = new Template("Car1","Model",1.6,"PETROL");
   Template car9 = new Template("Car1","Model",1.6,"PETROL");
   Template car10 = new Template("Car1","Model",1.6,"PETROL");
   Template car11 = new Template("Car1","Model",1.6,"PETROL");
   Template car12 = new Template("Car1","Model",1.6,"PETROL");
   Template car13 = new Template("Car1","Model",1.6,"PETROL");
   Template car14 = new Template("Car1","Model",1.6,"PETROL");
   Template car15 = new Template("Car1","Model",1.6,"PETROL");
   Template car16 = new Template("Car1","Model",1.6,"PETROL");
   Template car17 = new Template("Car1","Model",1.6,"PETROL");
   Template car18 = new Template("Car1","Model",1.6,"PETROL");
   Template car19 = new Template("Car1","Model",1.6,"PETROL");
   Template car20 = new Template("Car1","Model",1.6,"PETROL");
   carsTemplate.addAll(car1,car2,car3,car4,car5,car6,car7,car8,car9,car10,
    car11,car12,car13,car14,car15,car16,car17,car18,car19,car20);
   //set template cars into the list
    templateList.getSelectionModel().selectedItemProperty().addListener(
        (ObservableValue<? extends Template> ov, Template old_val, 
            Template new_val) -> {
      makeTextField.setText(new_val.getMake());
      modelTextField.setText(new_val.getModel());
       String engine = Double.toString(new_val.getEngineSize());
       engineTextField.setText(engine);
        fuelTextField.setText(new_val.getFuelType());
    });
   templateList.setItems(carsTemplate);
   templateList.setOrientation(Orientation.VERTICAL);
   templateList.setCellFactory(ComboBoxListCell.forListView(carsTemplate));
   templateList.toString();
  }
   
  @FXML 
  public void displayCustomers(){
   CustomerRegistry cr = CustomerRegistry.getInstance();
    ArrayList<Customer> currentCustomers = cr.getActiveCustomers();
    activeCustomers.addAll(currentCustomers);
     customerSelectorChoiceBox.setItems(activeCustomers);
     customerSelectorChoiceBox.getSelectionModel().selectFirst();
  }
  
  @FXML
  public void selectCustomerButton(ActionEvent event){
   Customer c = (Customer) customerSelectorChoiceBox.getValue();
    CustomerRegistry cr = CustomerRegistry.getInstance();
     String phone = c.getPhone();
     String email = c.getEmail();
     int id = cr.getCustomerID(phone, email);
     String idString = Integer.toString(id);
     customerIDTextField.setText(idString);
  }
  
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
  
  //reg,id,make,model,engine,fuel,colour,mot,last,mile,warranty
  //model,make,engine,fuelsize
  
  
  public void vehicleDisplay(ActionEvent event){
   displayCustomers();
   list.removeAll(list);
   ArrayList<Vehicle> all = new ArrayList<>();
   all = loadVehicles();
   list.addAll(all);
   vehDetails.setEditable(true);
   
    regCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle, String>("registration"));
     
    makeCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("model"));

    modelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("make"));

    engineCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,Integer>("engineSize"));
     
    fuelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("fuelType"));
    
    colCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("colour"));
    
    MOTCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("MOTRenewal"));
    
    warrCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("warranty"));
    
    lastCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("lastService"));
    
    mileCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,Integer>("currentMile"));
    
    typeCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle, String>("type"));
    
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
  
    public void loadDisplay(){
   //select customer
   activeCustomers.removeAll(activeCustomers);
   displayCustomers();
   addTemplateCars();
   list.removeAll(list);
   ArrayList<Vehicle> all = new ArrayList<>();
   all = loadVehicles();
   list.addAll(all);
   vehDetails.setEditable(true);
   
    regCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle, String>("registration"));
    
    makeCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("model"));

    modelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("make"));

    engineCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,Integer>("engineSize"));
     
    fuelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("fuelType"));
    
    colCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("colour"));
    
    MOTCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("MOTRenewal"));
    
    warrCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("warranty"));
    
    lastCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("lastService"));
    
    mileCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,Integer>("currentMile"));
    
     typeCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle, String>("type"));
    
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
   lastTextField.setText(temp.getLastService());
   mileageTextField.setText(String.valueOf(temp.getCurrentMile()));
   if(temp.getWarranty()){
    warrantyCheckBox.setSelected(true);
    ArrayList<String> details = new ArrayList();
     details = vr.getWarranty(temp.getRegistration());
      warrantyNameTextField.setText(details.get(0));
      warrantyAddressTextField.setText(details.get(0));
      warrantyExpiryTextField.setText(details.get(0));
   }
   else{
    warrantyCheckBox.setSelected(false);  
   }
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
    //DO REG AND MANUFACTURER SEARCH
    ArrayList<Vehicle>both = new ArrayList<>();
    String regText = regSearchTextField.getText();
    String manuText = manuSearchTextField.getText();
     both = vr.searchBoth(regText,manuText);
     if(both.isEmpty()){
      componentLoader cl = new componentLoader();
       cl.showNoResults();
       regSearchTextField.clear();
       manuSearchTextField.clear();
     }
     else{
     list.clear();
     list.addAll(both);
     vehDetails.setItems(list);
     }
   }
   else if(!regSearchTextField.getText().isEmpty() && manuSearchTextField.getText().isEmpty()){
    //DO REG SEARCH
    ArrayList<Vehicle>reg = new ArrayList<>();
    String text = regSearchTextField.getText();
     reg = vr.searchReg(text);
     if(reg.isEmpty()){
       componentLoader cl = new componentLoader();
       cl.showNoResults();
       regSearchTextField.clear();
       manuSearchTextField.clear();   
     }
     else{
     list.clear();
     list.addAll(reg);
     vehDetails.setItems(list);
    }
   }
   else{
    //DO MANUFACTURER SEARCH
    ArrayList<Vehicle>manu = new ArrayList<>();
    String text = manuSearchTextField.getText();
    //checks what being search only contains a-z
    if(containsLetters(text)){
     manu = vr.searchManu(text);
     if(manu.isEmpty()){
       componentLoader cl = new componentLoader();
       cl.showNoResults();
       regSearchTextField.clear();
       manuSearchTextField.clear();    
     }
     else{
     list.clear();
     list.addAll(manu);
     vehDetails.setItems(list);
     }
    }
    else{
     componentLoader cl = new componentLoader();
      cl.showManuStringFailure();
    }
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
     //warranty things
     warrantyNameTextField.clear();
     warrantyAddressTextField.clear();
     warrantyExpiryTextField.clear();
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
     vehicleDisplay(event);
  }
  
    @FXML
  public void addVanButton(ActionEvent event){
     addVanDetails(event);
     vehicleDisplay(event);
  }
  
    @FXML
  public void addTruckButton(ActionEvent event){
     addTruckDetails(event);
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
           warrantyNameCheck,
           warrantyAddressCheck,
           warrantyExpiryCheck,
           customerIDExists,
           mileageCheck = true;
   
   //check customer id
   String idString = customerIDTextField.getText();
   int id = Integer.parseInt(idString);
   //CHECK VEHICLE REGISTARITON 
   String reg = regTextField.getText();
    regCheck = checkTextField(reg);
   //CHECK VEHICLE MAKE TEXT FIELD    
   String make = makeTextField.getText();
    makeCheck = checkTextField(make); 
    //CHECK VEHICLE MODEL TEXT FIELD
   String model = modelTextField.getText();
    modelCheck = checkTextField(model);
    //CHECK ENGINE TEXT FIELD IS A DOUBLE
   String engineString = engineTextField.getText();
    eSizeCheck = checkTextField(engineString);
    double engine = 0;
    try{
    engine = Double.parseDouble(engineString);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
       cl.showEngineFailure();
       return;
    }
   //CHECK FUEL TEXT FIELD CONTAINS ONLY A-Z 
   String fuel = fuelTextField.getText();
    fuelCheck = checkTextField(fuel);
     if(!containsLetters(fuel)){
      componentLoader cl = new componentLoader();
       cl.showFuelStringFailure();
       return;
     }
   //CHECK COLOUR TEXT FIELD CONTAINS ONLY CHARS A-Z 
   String colour = colourTextField.getText();
    colourCheck = checkTextField(colour);
    if(!containsLetters(colour)){
     componentLoader cl = new componentLoader();
      cl.showColourStringFailure();
    }
   //CHECK MOT TEXT FIELD IS IN THE FORMAT OF DD/MM/YYYY
   String MOT = MOTTextField.getText();
    MOTCheck = checkTextField(MOT);
    if(!isValidDate("dd/mm/yyyy",MOT)){
      return;  
    }
   //CHECKS WARRANTY CHECKBOX CHECKED OR NOT
   boolean warranty; 
   warranty = warrantyCheckBox.isSelected();
   //CHECKS LASTS SERVICE TEXT FIELD IS IN THE FORM DD/MM/YYYY
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    if(!isValidDate("dd/mm/yyyy",last)){
      return;  
    }
    //CHECKS MILEAGE TEXT FIELD IS AN INTEGER 
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = 0;
    try{
     mileage = Integer.parseInt(mileageString);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
      cl.showMileageFailure();
       return;  
    }
    //IF ALL TEST CASES ARE TRUE ADD VEHICLE  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
      //CHECK IF WARRANTY IS SELECTED, IF SO CHECK WARRANTY INFO TEXT FIELDS
      if(warranty == true){
     //CHECK WARRANTY NAME
     String warrantyName = warrantyNameTextField.getText();
     warrantyNameCheck = checkTextField(warrantyName);
    //CHECK WARRANTY ADDRESS
    String warrantyAddress = warrantyAddressTextField.getText();
     warrantyAddressCheck = checkTextField(warrantyName);
    //CHECK EXPIRY DATE IS IN THE FORMAT OF DD/MM/YYYY
    String warrantyExpiry = warrantyExpiryTextField.getText();
     warrantyExpiryCheck = checkTextField(warrantyExpiry);
     if(!isValidDate("dd/mm/yyyy",warrantyExpiry)){
       return;  
     }
     //IF WARRANTY CONDITIONS ARE OK THEN ADDS CAR WITH WARRANTY INFORMATION
     if(warrantyNameCheck && warrantyAddressCheck && warrantyExpiryCheck){
       vr.addCar(reg,id,"CAR",make,model,engine,fuel,colour,MOT,warranty,last,mileage);
       vr.addWarranty(reg,warrantyName, warrantyAddress, warrantyExpiry);
     }
     else{
      componentLoader cl = new componentLoader();
       cl.showWarrantyFailure();
     }
      }
      //IF WARRANTY NOT SELECTED ADDS CAR WITHOUT WARRANTY INFORMATION
      else{
       vr.addCar(reg,id,"CAR",make,model,engine,fuel,colour,MOT,warranty,last,mileage);   
      }
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
           warrantyNameCheck,
           warrantyAddressCheck,
           warrantyExpiryCheck,
           mileageCheck = true;
   //CHECK CUSTOMER ID
   String idString = customerIDTextField.getText();
    int id = Integer.parseInt(idString);
   //CHECK VEHICLE REGISTRATION
   String reg = regTextField.getText();
    regCheck = checkTextField(reg);
   //CHECK VEHICLE MAKE 
   String make = makeTextField.getText();
    makeCheck = checkTextField(make); 
   //CHECK VEHICLE MODEL TEXT FIELD 
   String model = modelTextField.getText();
    modelCheck = checkTextField(model);
   //CHECK ENGINE TEXT FIELD IS A DOUBLE
   String engineString = engineTextField.getText();
    eSizeCheck = checkTextField(engineString);
    double engine = 0;
    try{
    engine = Double.parseDouble(engineString);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
       cl.showEngineFailure();
       return;
    }
    //CHECK FUEL TEXT FIELD ONLY A-Z
   String fuel = fuelTextField.getText();
    fuelCheck = checkTextField(fuel);
     if(!containsLetters(fuel)){
      componentLoader cl = new componentLoader();
       cl.showFuelStringFailure();
       return;
     }
   //CHECK COLOUR TEXT FIELD ONLY CONTAINS CHARS A-Z 
   String colour = colourTextField.getText();
    colourCheck = checkTextField(colour);
     if(!containsLetters(colour)){
      componentLoader cl = new componentLoader();
       cl.showColourStringFailure();
       return;
     }
   //CHECK MOT TEXT FIELD IS VALID DATE
   String MOT = MOTTextField.getText();
    MOTCheck = checkTextField(MOT);
    if(!isValidDate("dd/mm/yyyy",MOT)){
      return;  
    }
    //CHECK IF WARRANTY CHECKBOX IS SELECTED
   boolean warranty;
   warranty = warrantyCheckBox.isSelected();
   //CHECK LAST SERVICE IS VALID DATE
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    if(!isValidDate("dd/mm/yyyy",last)){
      return;  
    }
    //CHECK MILEAGE TEXT FIELD IS AN INTEGER
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
     int mileage = 0;
    try{
     mileage = Integer.parseInt(mileageString);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
      cl.showMileageFailure();
       return;  
    }
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
        if(warranty == true){
     //CHECK WARRANTY NAME FIELD NOT BLANK
     String warrantyName = warrantyNameTextField.getText();
     warrantyNameCheck = checkTextField(warrantyName);
    //CHECK ADDRESS FIELD NOT BLANK
    String warrantyAddress = warrantyAddressTextField.getText();
     warrantyAddressCheck = checkTextField(warrantyName);
    //CHECK EXPIRY IS A VALID DATE
    String warrantyExpiry = warrantyExpiryTextField.getText();
     warrantyExpiryCheck = checkTextField(warrantyExpiry);
     if(!isValidDate("dd/mm/yyyy",warrantyExpiry)){
      return;  
    }
     
     if(warrantyNameCheck && warrantyAddressCheck && warrantyExpiryCheck){
       vr.addVan(reg,id,"VAN",make,model,engine,fuel,colour,MOT,warranty,last,mileage);
       vr.addWarranty(reg,warrantyName, warrantyAddress, warrantyExpiry);
     }
     else{
      componentLoader cl = new componentLoader();
       cl.showWarrantyFailure();
     }
      }
      //IF WARRANTY NOT TRUE ADD VAN WITHOUT WARRANTY INFORMATION
      else{
      vr.addVan(reg,id,"VAN",make,model,engine,fuel,colour,MOT,warranty,last,mileage);
    }
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
           warrantyNameCheck,
           warrantyAddressCheck,
           warrantyExpiryCheck,
           mileageCheck = true;
   //CHECK CUSTOMER ID
   String idString = customerIDTextField.getText();
   int id = Integer.parseInt(idString);
   //CHECK REG TEXT FIELD NOT EMPTY
   String reg = regTextField.getText();
    regCheck = checkTextField(reg);
   //CHECK MAKE TEXT FIELD NOT EMPTY 
   String make = makeTextField.getText();
    makeCheck = checkTextField(make); 
   //CHECK MODEL TEXT FIELD NOT EMPTY 
   String model = modelTextField.getText();
    modelCheck = checkTextField(model);
   //CHECK ENGINE IS A DOUBLE 
   String engineString = engineTextField.getText();
    eSizeCheck = checkTextField(engineString);
    double engine = 0;
    try{
    engine = Double.parseDouble(engineString);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
       cl.showEngineFailure();
       return;
    }
   //CHECK FUEL TEXT FIELD 
   String fuel = fuelTextField.getText();
    fuelCheck = checkTextField(fuel);
     if(!containsLetters(fuel)){
      componentLoader cl = new componentLoader();
       cl.showFuelStringFailure();
       return;
     }
    //CHECK COLOUR TEXT FIELD
   String colour = colourTextField.getText();
    colourCheck = checkTextField(colour);
    if(!containsLetters(colour)){
      componentLoader cl = new componentLoader();
       cl.showColourStringFailure();
       return;
     }
   //CHECK MOT TEXT FIELD IS A VALID DATE IN THE FORM OF DD/MM/YYYY
   String MOT = MOTTextField.getText();
    MOTCheck = checkTextField(MOT);
    if(!isValidDate("dd/mm/yyyy",MOT)){
      return;  
    }
   //CHECKS IF WARRANTY CHECK BOX IS SELECTED
   boolean warranty;
   warranty = warrantyCheckBox.isSelected();
   //CHECK LAST SERVICE TEXT FIELD IS VALID DATE IN THE FORM OF DD/MM/YYYY 
   String last = lastTextField.getText();
    LastServiceCheck = checkTextField(last);
    if(!isValidDate("dd/mm/yyyy",last)){
      return;  
    }
    //CHECK MILEAGE IS AN INTEGER
   String mileageString = mileageTextField.getText();
    mileageCheck = checkTextField(mileageString);
    int mileage = 0;
    try{
     mileage = Integer.parseInt(mileageString);
    }
    catch(NumberFormatException e){
     componentLoader cl = new componentLoader();
      cl.showMileageFailure();
       return;  
    }
  
    if(regCheck && modelCheck && makeCheck && eSizeCheck && fuelCheck && colourCheck
            && MOTCheck && LastServiceCheck && mileageCheck){
        if(warranty == true){
          //check name
     String warrantyName = warrantyNameTextField.getText();
     warrantyNameCheck = checkTextField(warrantyName);
    //check address
    String warrantyAddress = warrantyAddressTextField.getText();
     warrantyAddressCheck = checkTextField(warrantyName);
    //CHECK EXPIRY DATE VALID IN THE FORM OF DD/MM/YYYY
    String warrantyExpiry = warrantyExpiryTextField.getText();
     warrantyExpiryCheck = checkTextField(warrantyExpiry);
     if(!isValidDate("dd/mm/yyyy",warrantyExpiry)){
      return;  
    }
     
     if(warrantyNameCheck && warrantyAddressCheck && warrantyExpiryCheck){
       vr.addTruck(reg,id,"TRUCK",make,model,engine,fuel,colour,MOT,warranty,last,mileage);
       vr.addWarranty(reg,warrantyName, warrantyAddress, warrantyExpiry);
       }
     else{
      componentLoader cl = new componentLoader();
       cl.showWarrantyFailure();
      }
     }
     //ELSE IF TRUCK DOESNT HAVE WARRANTY ADD IT 
     else{
      vr.addTruck(reg,id,"TRUCK",make,model,engine,fuel,colour,MOT,warranty,last,mileage);
     }
    } 
  }
  //METHOD CHECKS TEXT FIELD IS NOT EMPTY
  private boolean checkTextField(String text){
   if(text.equals("")){
    return false;   
   }   
   else{
    return true;   
   }
  }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      loadDisplay();
    }
    //CHECKS IF STRING IS IN THE FORM OF DD/MM/YYYY
    public static boolean isValidDate(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
           componentLoader cl = new componentLoader();
           cl.showDateFailure();
        }
        return date != null;
    }
    //CHECKS IF STRING ONLY CONTAINS CHARACTERS A-Z
    public static boolean containsLetters(String str){
     if(str.matches("[a-zA-Z]+$")){
      return true;   
     }   
     else{
      return false;   
     }
    }
}
