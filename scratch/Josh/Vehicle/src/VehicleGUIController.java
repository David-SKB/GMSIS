/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Database.DBConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
                     mileageTextField,
                     regSearchTextField,
                     manuSearchTextField;
   @FXML
   private Button carButton,
                  vanButton,
                  truckButton,
                  deleteButton,
                  editButton,
                  clearButton;
   @FXML
   private CheckBox warrantyCheckBox;
   @FXML
   private ObservableList<Vehicle> list = FXCollections.observableArrayList();
   @FXML
   private TableView<Vehicle> vehDetails;
   @FXML
   private TableColumn regCol,idCol,makeCol,modelCol,engineCol,fuelCol,colCol,MOTCol,lastCol,
                       mileCol,warrCol;
   private Vehicle tempVehicle;
   
   //reg,colour,mot,warranty,lastservice,mileage
   
  public void vehicleDisplay(ActionEvent event){
   loadVehicles(list);
   vehDetails.setEditable(true);
   
    regCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("registration"));
    
    idCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("customerId"));
    
    makeCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("make"));
    
    modelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("model"));
    
    engineCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("engineSize"));
    
    fuelCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("fuelType"));
    
    colCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("colour"));
    
    MOTCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("MOTRenewal"));
    
    lastCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("lastService"));
    
    mileCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("currentMile"));
    
    warrCol.setCellValueFactory(
       new PropertyValueFactory<Vehicle,String>("warranty"));
    
    vehDetails.setItems(list);
    
    vehDetails.setRowFactory((TableView<Vehicle> tv) -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempVehicle = row.getUserData();
                     loadDataChange(tempVehicle);
                     
                }
            });
            return row;
        });
    
   // vehDetails.getColumns().addAll(regCol,idCol,makeCol,modelCol,engineCol,fuelCol,
   //          colCol,MOTCol,lastCol,mileCol,warrCol);
  
  }
  
  public void loadDataChange(Vehicle temp){
   regTextField.setText(temp.getReg());
   modelTextField.setText(temp.getModel());
   makeTextField.setText(temp.getMake());
   engineTextField.setText(temp.getEngine());
   fuelTextField.setText(temp.getFuel());
   colourTextField.setText(temp.getColour());
   MOTTextField.setText(temp.getMOT());
   lastTextField.setText(temp.getLast());
   mileageTextField.setText(temp.getMile());
  }
   
  public void loadVehicles(ObservableList<Vehicle>list){
   db.connect();
    ArrayList<Vehicle> vehList = vr.getAllVehicles();
    if(vehList != null){
     for(Vehicle v : vehList){
      list.add(v);
     }   
    }
    db.closeConnection();
  }
  @FXML 
  public void deleteButton(ActionEvent event){

  }
  @FXML
  public void editButton(ActionEvent event){
      
  }
  @FXML
  public void clearButton(ActionEvent event){
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
  }
  @FXML 
  public void regSearchButton(ActionEvent event){
    ArrayList<Vehicle>result = new ArrayList();
    String reg = regSearchTextField.getText();
     if(checkTextField(reg)){
       result = regSearch(reg);
       addSearchedVehicles(list,result);
       vehDetails.refresh();
       vehDetails.setItems(list);
     }
     else{
       JOptionPane.showMessageDialog(null,"reg search field is empty");   
     }
  }
  
  public void addSearchedVehicles(ObservableList<Vehicle>list,ArrayList<Vehicle>data){
   if(data != null){
    for(Vehicle v : data){
      list.add(v);
    }   
   }   
  }
  @FXML
  public void manuSearchButton(ActionEvent event){
   ArrayList<Vehicle>result = new ArrayList();
   String manu = manuSearchTextField.getText();
   if(checkTextField(manu)){
     result = manuSearch(manu);
     addSearchedVehicles(list,result);
     vehDetails.setItems(list);
     vehDetails.refresh();
   }
   else{
     JOptionPane.showMessageDialog(null,"manu search field is empty");   
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
