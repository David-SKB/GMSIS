/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.gui;

import common.DBConnection;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import parts.logic.Part;
import parts.logic.PartRegistry;
import parts.logic.UsedPart;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleBill;
import vehicles.logic.VehicleRegistry;

/**
 * FXML Controller class
 *
 * @author joshuascott
 */
public class VehicleDetailsController{

 @FXML
 private Label pastBookingLabel,
               futureBookingLabel,
               partsUsedLabel;
 @FXML
 private TableView <VehicleBill> pastBookingsTable;
 @FXML
 private TableView <VehicleBill> futureBookingsTable;
 @FXML
 private ListView partsUsedList;
 
 @FXML
 private TableColumn pastTypeCol,
                     pastDateCol,
                     pastRegCol,
                     pastCostCol,
                     futureTypeCol,
                     futureDateCol,
                     futureRegCol,
                     futureCostCol;
 @FXML
 private ObservableList<VehicleBill> past = FXCollections.observableArrayList();
 private ObservableList<VehicleBill> future = FXCollections.observableArrayList();
 private ObservableList<UsedPart> parts = FXCollections.observableArrayList();
 
 DBConnection db = DBConnection.getInstance();
 CustomerRegistry cr = CustomerRegistry.getInstance();
 BookingRegistry br = BookingRegistry.getInstance();
 VehicleRegistry vr = VehicleRegistry.getInstance();
 PartRegistry pr = PartRegistry.getInstance();
 @FXML
 private VehicleBill tempVehicleBill;
 
 public void setDetails(String reg){
  getBookings(reg);   
 }
 
 public void getParts(String bookID){
  parts.clear();
   ArrayList<UsedPart> partsUsed = new ArrayList();
   pr.getUsedPartByBooking(bookID);
    parts.addAll(partsUsed);
     partsUsedList.setItems(parts);
 }
 
 public void getBookings(String reg){
  past.clear();
  future.clear();
  ArrayList<Vehicle> v = vr.searchReg(reg);
  int id = v.get(0).getId();
  ArrayList<DiagRepairBooking> bookingList = new ArrayList();
   bookingList = br.searchBookingByCustID(String.valueOf(id));
    if(bookingList != null){
     for(DiagRepairBooking drb : bookingList){
      float amount = 0;
      if (parseLocalDateTime(drb.getBookdate() + " " + drb.getStarttime()).compareTo(NOW_LOCALDATETIME()) >= 0){
       amount = getBillCost(drb);
       future.add(new VehicleBill(drb,amount));
      }
      else if(parseLocalDateTime(drb.getBookdate() + " " + drb.getStarttime()).compareTo(NOW_LOCALDATETIME()) < 0){
       amount = getBillCost(drb);
       past.add(new VehicleBill(drb,amount));
      }
     }
     pastBookingsTable.setItems(past);
     futureBookingsTable.setItems(future);
     
    }
    else{
      //display no bookings pop up  
    }
 }
  
     private float getBillCost(DiagRepairBooking drb){
        db.connect();
        try{
            float account = 0;
            String query = "SELECT * FROM BILLS\n" +
                            "WHERE BILLID = " + (drb).getId()+ "; "; 
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
 
 
 public void setTable(){
           //past bookings table
           pastTypeCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("bookingType"));
           pastDateCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("bookingDate")); 
           pastRegCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("VehID")); 
           pastCostCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("cost"));
           
            pastBookingsTable.setRowFactory((TableView<VehicleBill> tv) -> {
            TableRow<VehicleBill> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempVehicleBill = row.getItem();
                     getParts(tempVehicleBill.getBookingID());                    
                }
            });
            return row;
        });
           
          //future bookings table
           futureTypeCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("bookingType"));
           futureDateCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("bookingDate")); 
           futureRegCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("VehID")); 
           futureCostCol.setCellValueFactory(
              new PropertyValueFactory<VehicleBill, String>("cost"));
           
         
 }
 
     
    private LocalDateTime NOW_LOCALDATETIME() {
        return LocalDateTime.now();
    }

    private LocalDateTime parseLocalDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(str, formatter);
    }
    
}
