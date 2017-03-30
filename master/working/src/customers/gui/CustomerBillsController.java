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
import parts.logic.UsedPart;
import vehicles.logic.VehicleRegistry;

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerBillsController implements Initializable{
    
    @FXML
    private TableView<CustomerBill> futureBookings;
    @FXML
    private TableColumn futureBType,futureBDate,futureBReg, futureBCost,
                        futureBBill;
    @FXML
    private TableView<CustomerBill> pastBookings;
    @FXML
    private TableColumn pastBType,pastBDate,pastBReg,pastBCost,pastBBill;
    @FXML
    private TextField firstNameTF, lastNameTF, addressTF,cTypeTF,          
                      pCodeTF, phoneTF, emailTF;   
    @FXML
    private ListView<UsedPart> partsList;
    DBConnection db = DBConnection.getInstance();
    CustomerRegistry CR = CustomerRegistry.getInstance();
    BookingRegistry BR = BookingRegistry.getInstance();
    VehicleRegistry VR = VehicleRegistry.getInstance();
    PartRegistry PR = PartRegistry.getInstance();
    private ObservableList<CustomerBill> futureBObsList;
    private ObservableList<CustomerBill> pastBObsList;
    private ObservableList<UsedPart> partObsList;
    Customer tempCust;
    CustomerBill tempCustBillF;
    CustomerBill tempCustBillP;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        futureBType.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bookingType"));
        futureBDate.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bookingDate"));
        futureBReg.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("VehID"));
        futureBCost.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, Float>("cost"));
        futureBBill.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, Float>("bill"));
        
        futureBookings.setRowFactory((TableView<CustomerBill> tv) -> {
            TableRow<CustomerBill> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                    tempCustBillF = row.getItem();
                    loadParts(tempCustBillF.getBookingID());
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
        pastBCost.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("cost"));
        pastBBill.setCellValueFactory(
                new PropertyValueFactory<CustomerBill, String>("bill"));
        
        pastBookings.setRowFactory((TableView<CustomerBill> tv) -> {
            TableRow<CustomerBill> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                    tempCustBillP = row.getItem();
                    //loadParts(tempCustBillP.getBookingID());
                    //partsList.setItems(partObsList);
                }
            });
            return row;
        });
    }
    
    public void setUser(int ID){
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
                String billStatus = "Unsettled";
                float amount = 0;
                   if (parseLocalDateTime(DRP.getBookdate() + " " + DRP.getStarttime()).compareTo(NOW_LOCALDATETIME()) >= 0) {
                       amount = queryBill(DRP);
                       billStatus = checkWarranty(DRP);
                       futureBObsList.add(new CustomerBill(DRP,amount,billStatus));
                   }else if(parseLocalDateTime(DRP.getBookdate() + " " + DRP.getStarttime()).compareTo(NOW_LOCALDATETIME()) < 0){
                       amount = queryBill(DRP);
                       billStatus = checkWarranty(DRP);
                       pastBObsList.add(new CustomerBill(DRP,amount,billStatus));
                   }
            }
        }
        futureBookings.setItems(futureBObsList);
        pastBookings.setItems(pastBObsList);
    }
    
    private float queryBill(DiagRepairBooking DRP){
        db.connect();
        try{
            float account = 0;
            String query = "SELECT * FROM BILLS\n" +
                            "WHERE BILLID = " + (DRP).getId()+ "; "; 
            ResultSet rs = db.query(query);
            if(rs.isBeforeFirst()){
                account = rs.getFloat("DIAGREPCOST") + rs.getFloat("PARTSCOST") + rs.getFloat("SPCCOST");
                db.closeConnection();
                return account;
            }else{
                db.closeConnection();
                return 0;
            }
        }catch(SQLException e){
            db.closeConnection();
            return 0;
        }
    }
    
    private String checkWarranty(DiagRepairBooking DRP){
        boolean warranty = VR.checkWarranty(DRP.getVehreg());
            if(warranty){
                changeBillSettlement(DRP,1);
                return "Settled";
            }else{
                if(getBillStatus(Integer.parseInt(DRP.getId())) != 1){
                    changeBillSettlement(DRP,0);
                    return "Unsettled";
                }
                return "Settled";
            }
    }   
         
    
    private void loadParts(String bookingID){
        ArrayList<UsedPart> pList = PR. getUsedPartByBooking(bookingID);
        partObsList.removeAll(partObsList);
        if(pList != null &&
           !pList.isEmpty()){
            for(UsedPart p : pList){
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
    
    private void changeBillSettlement(DiagRepairBooking DRP, int status){
        db.connect();
            String bID = DRP.getId();
            String query = "UPDATE BILLS \n" + 
                           "SET " +
                           "STATUS = " + status + "\n" +
                            "WHERE BILLID = " + Integer.parseInt(bID)+ ";";
            db.update(query);
        db.closeConnection();
    }
    
    @FXML
    private void changeBillSettlementF(){
        int fStatus = 0;
        if(tempCustBillF != null){
            if(tempCustBillF.getBill().equalsIgnoreCase("Settled")){
                tempCustBillF.setBill("Unsettled");
                changeBillSettlement(tempCustBillF.getDRP(),0);
                changeCustomerBill(tempCustBillF,"Unsettled");
            }else{
                tempCustBillF.setBill("Settled");
                changeBillSettlement(tempCustBillF.getDRP(),1);
                changeCustomerBill(tempCustBillF,"Settled");
            }
        } 
        tempCustBillF = null;
    }
    
    private void changeCustomerBill(CustomerBill tempCB, String status){
       for(CustomerBill cb : futureBObsList){
           if(cb.getBookingID().equals(tempCB.getBookingID())){
               cb.setBill(status);
               System.out.println("changed");
           }
       }
       loadBookings(Integer.parseInt(tempCB.getDRP().getCust()));
    }
   
    private int getBillStatus(int id){
        db.connect();
        String query = "SELECT STATUS FROM BILLS\n" +
                        "WHERE BILLID = " + id + "; "; 
        try{
            ResultSet rs = db.query(query);
            int status = rs.getInt("STATUS");
            db.closeConnection();  
            return status;
        }catch(SQLException e){
            db.closeConnection();  
            return 0;
        }
    }
}
