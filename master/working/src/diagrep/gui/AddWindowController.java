/**
 *
 * References:
 * NOW_LOCAL_DATE(): http://stackoverflow.com/questions/31899692/how-to-set-default-value-for-javafx-datepicker-in-fxml
 */
package diagrep.gui;

import common.DBConnection;
import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import diagrep.logic.BookingRegistry;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import user.logic.Mechanic;
import user.logic.UserRegistry;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;

public class AddWindowController implements Initializable {

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ChoiceBox entryType;
    @FXML
    private DatePicker entryDate;
    @FXML
    private TextField entryTime;
    @FXML
    private TextField entryDuration;
    @FXML
    private ChoiceBox entryReg;
    @FXML
    private ChoiceBox entryCustomer;
    @FXML
    private ChoiceBox entryMechanic;

    private DiagRepairScreenController parentController;
    private DBConnection conn;
    private CustomerRegistry CR = CustomerRegistry.getInstance();
    private VehicleRegistry VR = VehicleRegistry.getInstance();
    private UserRegistry UR = UserRegistry.getInstance();
    private BookingRegistry BR = BookingRegistry.getInstance();
    private Date currentDate = new Date();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getInstance();
        entryType.setItems(FXCollections.observableArrayList("Diagnosis", new Separator(), "Repair"));
        entryType.getSelectionModel().selectFirst();	//set the options to search from in dropdown list	
        entryDate.setValue(NOW_LOCALDATE());
        
        entryTime.setText("09:00");
        entryDuration.setText("1");

        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();		//vehicle choicebox
        ArrayList<Vehicle> currentVehicle = VR.getAllVehicles();
        vehicleList.addAll(currentVehicle);
        entryReg.setItems(vehicleList);
        entryReg.getSelectionModel().selectFirst();	//set the options to search from in dropdown list

        ObservableList<Customer> customerList = FXCollections.observableArrayList();	//customer choicebox
        ArrayList<Customer> currentCustomers = CR.getActiveCustomers();
        customerList.addAll(currentCustomers);
        entryCustomer.setItems(customerList);
        entryCustomer.getSelectionModel().selectFirst();	//set the options to search from in dropdown list

        ObservableList<Mechanic> mechanicList = FXCollections.observableArrayList();	//mechanic choicebox
        ArrayList<Mechanic> currentMechs = UR.getMechanic();
        mechanicList.addAll(currentMechs);
        entryMechanic.setItems(mechanicList);
        entryMechanic.getSelectionModel().selectFirst();	//set the options to search from in dropdown list

    }

    @FXML
    public void addEntry() {
        Vehicle v = (Vehicle) entryReg.getValue();
        String reg = v.getRegistration();
        Customer c = (Customer) entryCustomer.getValue();
        String phone = c.getPhone();
        String email = c.getEmail();
        int id = CR.getCustomerID(phone, email);
        String custID = Integer.toString(id);
        Mechanic m = (Mechanic) entryMechanic.getValue();
        String mechID = Integer.toString(m.getIDNumber());
        String date = entryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Date CheckDate = new Date(date);
        if (CheckDate.compareTo(currentDate) < 0) {
            BR.addBooking(date, entryTime.getText(), entryDuration.getText(), (String) entryType.getSelectionModel().getSelectedItem(), custID, reg, mechID);
            parentController.reset();
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        } else {
            JOptionPane.showMessageDialog(null, "Bookings can only be made in the future.");
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public LocalDate NOW_LOCALDATE() {
        return LocalDate.now();
    }

    public LocalDate parseLocalDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(str, formatter);
    }

    public void setParentController(DiagRepairScreenController parentController) {
        this.parentController = parentController;
    }
    
    public void initiateBooking(Customer argCust){
        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();		//vehicle choicebox
        ArrayList<Vehicle> currentVehicle = 
            VR.searchCustomerVehicles(CR.getCustomerID(argCust.getPhone(), argCust.getEmail()));
        vehicleList.addAll(currentVehicle);
        entryReg.setItems(vehicleList);
        entryReg.getSelectionModel().selectFirst();	//set the options to search from in dropdown list

        ObservableList<Customer> customerList = FXCollections.observableArrayList();	//customer choicebox
        customerList.addAll(argCust);
        entryCustomer.setItems(customerList);
        entryCustomer.getSelectionModel().selectFirst();	//set the options to search from in dropdown list

        ObservableList<Mechanic> mechanicList = FXCollections.observableArrayList();	//mechanic choicebox
        ArrayList<Mechanic> currentMechs = UR.getMechanic();
        mechanicList.addAll(currentMechs);
        entryMechanic.setItems(mechanicList);
        entryMechanic.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
    }

}
