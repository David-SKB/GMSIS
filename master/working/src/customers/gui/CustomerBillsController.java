/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers.gui;

import common.DBConnection;
import customers.logic.Customer;
import customers.logic.CustomerBill;
import customers.logic.CustomerRegistry;
import diagrep.logic.Booking;
import diagrep.logic.BookingRegistry;
import diagrep.logic.DiagRepairBooking;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import parts.logic.Part;
import parts.logic.PartRegistry;
import vehicles.logic.VehicleRegistry;

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerBillsController implements Initializable{
    
    @FXML
    private TableView<CustomerBill> futureBookings;
    @FXML
    private TableColumn futureBType,futureBDate,futureBReg,
                        futureBBill;
    @FXML
    private TableView<CustomerBill> pastBookings;
    @FXML
    private TableColumn pastBType,pastBDate,pastBReg,pastBBill;
    @FXML
    private TextField firstNameTF, lastNameTF, addressTF,cTypeTF,          
                      pCodeTF, phoneTF, emailTF;   
    @FXML
    private ListView<Part> partsList;
    
    CustomerRegistry CR = CustomerRegistry.getInstance();
    BookingRegistry BR = BookingRegistry.getInstance();
    VehicleRegistry VR = VehicleRegistry.getInstance();
    PartRegistry PR = PartRegistry.getInstance();
    private ObservableList<CustomerBill> futureBObsList;
    private ObservableList<CustomerBill> pastBObsList;
    private ObservableList<Part> partObsList;
    Customer tempCust;
    CustomerBill tempCustBill;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("init");
        futureBType.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bookingType"));
        futureBDate.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bookingDate"));
        futureBReg.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("VehID"));
        futureBBill.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, Float>("bill"));
        
        futureBookings.setRowFactory((TableView<CustomerBill> tv) -> {
            TableRow<CustomerBill> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                    tempCustBill= row.getItem();
                    loadParts(tempCustBill.getBookingID());
                    partsList.setItems(partObsList);
                }
            });
            return row;
        });
        
        pastBType.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bookingType"));
        pastBDate.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bookingDate"));
        pastBReg.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("VehID"));
        pastBBill.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bill"));
        
        pastBookings.setRowFactory((TableView<CustomerBill> tv) -> {
            TableRow<CustomerBill> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                    tempCustBill= row.getItem();
                    loadParts(tempCustBill.getBookingID());
                    partsList.setItems(partObsList);
                }
            });
            return row;
        });
    }
    
    public void setUser(int ID){
        System.out.println("setuser");
        futureBObsList = FXCollections.observableArrayList();
        pastBObsList = FXCollections.observableArrayList();
        partObsList = FXCollections.observableArrayList();
        this.tempCust = CR.searchCustomerByID(String.valueOf(ID));
        setUserDetails();
        loadBookings(ID);
        futureBookings.setItems(futureBObsList);
        pastBookings.setItems(pastBObsList);
    }
    
    private void setUserDetails(){
        firstNameTF.setText(tempCust.getFirstname());
        lastNameTF.setText(tempCust.getSurname());
        addressTF.setText(tempCust.getAddress());
        cTypeTF.setText(tempCust.getCustomerType());
        pCodeTF.setText(tempCust.getPostCode());
        phoneTF.setText(tempCust.getPhone());
        emailTF.setText(tempCust.getEmail());
    }
    
    private void loadBookings(int ID){
         ArrayList<DiagRepairBooking> bList = BR.searchBookingByCustID(String.valueOf(ID));
         futureBObsList.removeAll(futureBObsList);
         pastBObsList.removeAll(pastBObsList);
         if(bList != null &&
           !bList.isEmpty()){
            for(DiagRepairBooking DRP : bList){
                   if (parseLocalDateTime(DRP.getBookingDate()).compareTo(NOW_LOCALDATETIME()) >= 0) {
                       System.out.println("in future");
                       futureBObsList.add(new CustomerBill(DRP,queryBill(DRP)));
                   }else if(parseLocalDateTime(DRP.getBookingDate()).compareTo(NOW_LOCALDATETIME()) < 0){
                       pastBObsList.add(new CustomerBill(DRP,queryBill(DRP)));
                       System.out.println("in pas");
                   }
            }
        }
    }
    
    private float queryBill(DiagRepairBooking DRP){
        DBConnection DB = DBConnection.getInstance();
        DB.connect();
        try{
            float account = 0;
            String query = "SELECT * FROM BILLS\n" +
                            "WHERE BILLID = '" + ((Booking)DRP).getBookingID() + "'; "; 
            ResultSet rs = DB.query(query);
            if(rs.isBeforeFirst()){
                account = rs.getFloat("DIAGREPCOST") + rs.getFloat("PARTSCOST") + rs.getFloat("SPCCOST");
            }else{
                return 0;
            }
            DB.closeConnection();
            boolean warranty = VR.checkWarranty(DRP.getVechID());
            if(warranty){
                return 0;
            }else{
                return account;
            }
        }catch(SQLException e){
            return 0;
        }
    }
    
    private void loadParts(String bookingID){
        ArrayList<Part> pList = PR.getUsedParts(bookingID);
        partObsList.removeAll(partObsList);
        if(pList != null &&
           !pList.isEmpty()){
            for(Part p : pList){
                partObsList.add(p);
            }
        }
    }
    
    private LocalDateTime NOW_LOCALDATETIME() {
        return LocalDateTime.now();
    }

    private LocalDateTime parseLocalDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(str, formatter);
    }
}
