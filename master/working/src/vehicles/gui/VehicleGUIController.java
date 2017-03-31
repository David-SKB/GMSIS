package vehicles.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import common.DBConnection;
import common.Main;
import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import diagrep.logic.BookingRegistry;
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
import javafx.scene.Node;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import vehicles.logic.Template;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;
import vehicles.logic.Warranty;


/**
 * FXML Controller class
 *
 * @author joshuascott
 */
public class VehicleGUIController implements Initializable {

    DBConnection db = DBConnection.getInstance();
    VehicleRegistry vr = VehicleRegistry.getInstance();
    BookingRegistry br = BookingRegistry.getInstance();
    CustomerRegistry cr = CustomerRegistry.getInstance();
    
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
                  selectCustomerButton,
                  detailsButton,
                  searchCarButton,
                  searchVanButton,
                  searchTruckButton,
                  vehicleDetailsButton;
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
                       mileCol,warrCol,typeCol,nextBookCol;
   @FXML
   private Vehicle tempVehicle;
   @FXML
   private Customer tempCustomer;
   @FXML
   ListView<Template> templateList = new ListView<Template>();
   @FXML
   ObservableList<Template> carsTemplate = FXCollections.observableArrayList();

   
   //button that searches and displays only all cars in the table   
   @FXML
   public void searchCarButton(ActionEvent event){
    ArrayList<Vehicle> cars = vr.searchCar();
    list.clear();
    list.addAll(cars);
    vehDetails.setItems(list);
   }
   
  //button that searches and displays only all vans in the table
   @FXML
   public void searchVanButton(ActionEvent event){
    ArrayList<Vehicle> vans = vr.searchVan();
    list.clear();
    list.addAll(vans);
    vehDetails.setItems(list);  
   }
  //button that searches and displays only all trucks in the table 
   @FXML
   public void searchTruckButton(ActionEvent event){
    ArrayList<Vehicle> trucks = vr.searchTruck();
    list.clear();
    list.addAll(trucks);
    vehDetails.setItems(list);   
   }
   //button when clicked gets list of parts used and past/future bookings for vehicle
   //I got this method from Athanasios's module as he already had it working.
   @FXML
   public void vehicleDetailsButton(ActionEvent event){
     String reg = regTextField.getText();
     Parent root;
      if(reg.equals("")){
         componentLoader cl = new componentLoader();
        cl.showRegFailure();  
      }
      else{
       try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./VehicleDetails.fxml"));
        root = (Parent)fxmlLoader.load();
        VehicleDetailsController vdc = fxmlLoader.getController();
         vdc.setDetails(reg);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Vehicle details");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Main.stage);
                stage.show();
       }
       catch(IOException e){
       componentLoader cl = new componentLoader();
        cl.showRegFailure();   
       }
      }
   }
   
   //gets customer details and next booking date for a specific vehicle 
   @FXML
   public void detailsButton(ActionEvent event){
    String reg = regTextField.getText();
      Parent root;
      if(reg.equals("")){
       componentLoader cl = new componentLoader();
        cl.showRegFailure();
      }
      else{
      try{
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./vehicleCustomers.fxml")); 
       root = (Parent)fxmlLoader.load();
       VehicleCustomersController vcc = fxmlLoader.getController();
       vcc.setCustomer(reg);
       //controller
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Customer details");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Main.stage);
                stage.show();
      }
      catch(IOException e){
       componentLoader cl = new componentLoader();
        cl.showRegFailure();
      }
      }
     }
   //updates everything everytime tab is loaded  
   public void updateAnchorPane(AnchorPane AP){
       ObservableList<Node> OL = AP.getChildren();
       TableView<Vehicle> vehTV = null;
       ChoiceBox cBox = null;
       for(Node n : OL){
           if(n instanceof TableView &&
              (n.getId()).equalsIgnoreCase("vehDetails")){
                vehTV = (TableView<Vehicle>)n;
           }else if(n instanceof ChoiceBox &&
                   (n.getId()).equalsIgnoreCase("customerSelectorChoiceBox")){
                cBox = (ChoiceBox)n;
           }
       }
       if(vehTV != null &&
          cBox != null){
            ArrayList<Customer> currentCustomers = cr.getActiveCustomers();
            activeCustomers.removeAll(activeCustomers);
            activeCustomers.addAll(currentCustomers);
            cBox.setItems(activeCustomers);
            cBox.getSelectionModel().selectFirst();
            ArrayList<Vehicle> all = new ArrayList<>();
            all = loadVehicles();
            list.removeAll(list);
            list.addAll(all);
            vehTV.setItems(list);
       }
   }
   //list of 20 template cars  
   @FXML
   public void addTemplateCars(){
   Template car1 = new Template("Ford","Focus",1.2,"Petrol");
   Template car2 = new Template("VW","Golf",1.8,"Diesel");
   Template car3 = new Template("Honda","Civic",1.6,"Petrol");
   Template car4 = new Template("BMW","X3",1.8,"Diesel");
   Template car5 = new Template("Land rover","Discovery",2.0,"Petrol");
   Template car6 = new Template("Audi","A3",1.6,"Diesel");
   Template car7 = new Template("Audi","A4",2.0,"Petrol");
   Template car8 = new Template("BMW","5 series",3.0,"Petrol");
   Template car9 = new Template("BMW","1 series",2.0,"Diesel");
   Template car10 = new Template("Mercedes-Benz","A-class",2.0,"Petrol");
   Template car11 = new Template("Mercedes-Benz","E-class",3.0,"Diesel");
   Template car12 = new Template("VW","Tiguan",2.0,"Diesel");
   Template car13 = new Template("Ford","Transit",1.8,"Petrol");
   Template car14 = new Template("VW","Transporter",2.0,"Petrol");
   Template car15 = new Template("Citroen","Berlingo",1.6,"Petrol");
   Template car16 = new Template("Mitsubishi","L200",2.5,"Petrol");
   Template car17 = new Template("Toyota","Hilux",2.0,"Diesel");
   Template car18 = new Template("Fiat","500",1.2,"Diesel");
   Template car19 = new Template("VW","Polo",1.4,"Diesel");
   Template car20 = new Template("Ford","Fiesta",1.2,"Petrol");
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
  //displays all active customers in choice box 
  @FXML 
  public void displayCustomers(){
    activeCustomers.clear();
    ArrayList<Customer> currentCustomers = cr.getActiveCustomers();
    activeCustomers.addAll(currentCustomers);
     customerSelectorChoiceBox.setItems(activeCustomers);
     customerSelectorChoiceBox.getSelectionModel().selectFirst();
  }
  //handles the selecting of the customer from choice box via a button
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
  
  //displays all vehicles
  public void vehicleDisplay(ActionEvent event){
   displayCustomers();
   list.removeAll(list);
   ArrayList<Vehicle> all = new ArrayList<>();
   all = loadVehicles();
   list.addAll(all);
    
    vehDetails.setItems(list);
     
  }
   //loads everything on tab
    public void loadDisplay(){
   //select customer
    activeCustomers.removeAll(activeCustomers);
    displayCustomers();
    addTemplateCars();
    list.removeAll(list);
    ArrayList<Vehicle> all = new ArrayList<>();
    all = loadVehicles();
    list.addAll(all);
    vehDetails.setItems(list);
   
  }
  
  public Vehicle getVehicle(){
   String reg = regTextField.getText();
   String idText = customerIDTextField.getText();
    int id = Integer.parseInt(idText);
   String make = makeTextField.getText();
   String model = modelTextField.getText();
   String engineText = engineTextField.getText();
    double engine = Double.parseDouble(engineText);
   String fuel = fuelTextField.getText();
   String colour = colourTextField.getText();
   String MOT = MOTTextField.getText();
   String last = lastTextField.getText();
   String mileText = mileageTextField.getText();
    int mileage = Integer.parseInt(mileText);
   boolean warranty = false;
   if(warrantyCheckBox.isSelected()){
    warranty = true;    
   }
   String type = tempVehicle.getType();
   Vehicle v = new Vehicle(reg,id,type,make,model,engine,fuel,colour,MOT,warranty,last,mileage);
   return v;
  }
  
  //button when clicked opens edit tab to allow updates and changes
  @FXML
  public void editButton(ActionEvent event)throws Exception{
      Parent root;
      Vehicle v = getVehicle();
       try{
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./EditTab.fxml")); 
       root = (Parent)fxmlLoader.load();
       EditTabController etc = fxmlLoader.getController();
       etc.set(tempVehicle);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Edit tab");
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(Main.stage);
                stage.show();
      }
      catch(IOException e){
       componentLoader cl = new componentLoader();
        cl.showVehicleFailure();
      }
  }
  
  public void loadDataChange(Vehicle temp){
   regTextField.setText(temp.getRegistration());
   customerIDTextField.setText(String.valueOf(temp.getId()));
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
    ArrayList<Warranty> details = new ArrayList();
     details = vr.getWarranty(temp.getRegistration());
      warrantyNameTextField.setText(details.get(0).getName());
      warrantyAddressTextField.setText(details.get(0).getAddress());
      warrantyExpiryTextField.setText(details.get(0).getExpiry());
   }
   else{
    warrantyCheckBox.setSelected(false);  
   }
  }
  //loads all current vehicles 
  public ArrayList<Vehicle> loadVehicles(){
   db.connect();
    ArrayList<Vehicle> vehList = vr.getAllVehicles();
     db.closeConnection();
     return vehList;
  }
 //search fucntionality 
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
  //the delete button
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
  //clears all text fields
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
     warrantyNameTextField.clear();
     warrantyAddressTextField.clear();
     warrantyExpiryTextField.clear();
     customerIDTextField.clear();
 }
    
 //add searched vehicles to vehicle list
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
   //handles the add car event   
    @FXML
  public void addCarButton(ActionEvent event){
     addCarDetails(event);
     vehicleDisplay(event);
  }
  //handles the add van button
    @FXML
  public void addVanButton(ActionEvent event){
     addVanDetails(event);
     vehicleDisplay(event);
  }
  //handles the add truck button
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
    int id = 0;
   try{
    id = Integer.parseInt(idString);
   }
   catch(NumberFormatException e){
    componentLoader cl = new componentLoader();
     cl.showIDFailure();
   }
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
    int id = 0;
   try{
    id = Integer.parseInt(idString);
   }
   catch(NumberFormatException e){
    componentLoader cl = new componentLoader();
     cl.showIDFailure();
   }
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
    int id = 0;
   try{
    id = Integer.parseInt(idString);
   }
   catch(NumberFormatException e){
    componentLoader cl = new componentLoader();
     cl.showIDFailure();
   }
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
 //initializes table view when tab loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
