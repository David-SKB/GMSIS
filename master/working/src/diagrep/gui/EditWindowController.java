/**
 * FXML Controller class
 *
 * @author Nexus
 */
package diagrep.gui;

import common.DBConnection;
import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import diagrep.logic.BookingRegistry;
import diagrep.logic.DiagRepairBooking;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class EditWindowController implements Initializable {

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
    private TextField entryMileage;
    @FXML
    private ChoiceBox entryCustomer;
    @FXML
    private ChoiceBox entryMechanic;

    private DiagRepairScreenController parentController;
    private DiagRepairBooking entry;
    private DBConnection conn;
    private final CustomerRegistry CR = CustomerRegistry.getInstance();
    private final VehicleRegistry VR = VehicleRegistry.getInstance();
    private final UserRegistry UR = UserRegistry.getInstance();
    private  final BookingRegistry BR = BookingRegistry.getInstance();
    private Date currentDate = new Date();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void reinit() {
        conn = DBConnection.getInstance();
        entryType.setItems(FXCollections.observableArrayList("Diagnosis", new Separator(), "Repair"));
        entryType.getSelectionModel().select(entry.getType());
        if (entry.getType().equals("Repair")) {
            entryType.getSelectionModel().selectFirst();
        } else {
            entryType.getSelectionModel().selectLast();
        }
        entryDate.setValue(LocalDate.parse(entry.getBookdate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        entryTime.setText(entry.getStarttime());
        entryDuration.setText(entry.getDuration());
        entryMileage.setText(entry.getMileage());
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
    private void editEntry(ActionEvent event) {
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
        BR.editBooking(date, entryTime.getText(), entryDuration.getText(), (String) entryType.getSelectionModel().getSelectedItem(), custID, reg, entryMileage.getText(), mechID);
        VR.changeMileage(v.getRegistration(), Integer.parseInt(entryMileage.getText()));
        parentController.reset();
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setEntry(DiagRepairBooking entry) {
        this.entry = entry;
    }

    public void setParentController(DiagRepairScreenController parentController) {
        this.parentController = parentController;
    }

}
