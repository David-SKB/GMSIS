/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.gui;

import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import diagrep.logic.BookingRegistry;
import diagrep.logic.DiagRepairBooking;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;

/**
 *
 * @author joshuascott
 */
public class VehicleCustomersController{
 
 @FXML
 private TableView<Customer>custDetails;
 @FXML
 private TextField nextBookingTextField,regTextField;
 @FXML
 private ObservableList<Customer> list = FXCollections.observableArrayList();
 @FXML
 private TableColumn firstNameCol;
 @FXML
 private TableColumn lastNameCol,addressCol,postCodeCol,phoneCol,emailCol,cTypeCol;
 @FXML
 private Label detailsLabel;
 
 private Customer c;
 private String custID;
 
 CustomerRegistry cr = CustomerRegistry.getInstance();
 BookingRegistry br = BookingRegistry.getInstance();
 VehicleRegistry vr = VehicleRegistry.getInstance();
         
 public void setCustomer(String reg){
   list.clear();
   ArrayList<Vehicle> vehicles = vr.searchReg(reg);
   if(vehicles != null){
    Vehicle v = vehicles.get(0);
     int id = v.getId();
    String custID = String.valueOf(id);
    c = cr.searchCustomerByID(custID);
    list.add(c);
    setTable();
    regTextField.setText(reg);
   }
   else{
    componentLoader cl = new componentLoader();
     cl.showNoResults();
   }
 }
 
 public void setReg(String id){
  int custID = Integer.parseInt(id);
  ArrayList<Vehicle> vehicle = vr.searchCustomerVehicles(custID);
   regTextField.setText(vehicle.get(0).getRegistration());
 }
 
 public void setTable(){
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("firstname"));
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("surname"));
        addressCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("address"));
        postCodeCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("postCode"));
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("phone"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("email"));
        cTypeCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("customerType"));
        custDetails.setItems(list);
 }
 
 /**
 public void getNextBooking(String id){
  ArrayList<DiagRepairBooking>booking = br.searchBookingByCustID(custID);
   DiagRepairBooking drb = booking.get(0);
   if(drb != null){
   String date = drb.getBookingDate();
   nextBookingTextField.setText(date); 
   }
   else{
    nextBookingTextField.setText("No booking");   
   }
 }
 **/
}
