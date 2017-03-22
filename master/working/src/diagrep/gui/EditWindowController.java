/**
 * FXML Controller class
 *
 * @author Nexus
 */
package diagrep.gui;

import common.DBConnection;
import diagrep.logic.BookingRegistry;
import diagrep.logic.DiagRepairBooking;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void reinit() {
        conn = DBConnection.getInstance();
        entryType.setItems(FXCollections.observableArrayList("Diagnosis", new Separator(), "Repair"));
        entryType.getSelectionModel().select(entry.getBookingType());

        /*
		if (entry.getType().equals("Repair"))
		{
			entryType.getSelectionModel().selectFirst();	
		}
		else
		{
			entryType.getSelectionModel().selectLast();
		}
         */
        String[] line = entry.getBookingDate().split("\\s+");
        entryDate.setValue(LocalDate.parse(line[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        entryTime.setText(line[1]);
        entryDuration.setText(entry.getBookingDuration());
        entryMileage.setText(entry.getMilage());
        try {
            ObservableList<String> vehicleList = FXCollections.observableArrayList();		//vehicle choicebox
            ResultSet rsV = conn.query("SELECT REGISTRATION FROM VEHICLE ORDER BY VehicleRegNo;");
            while (rsV.next()) {
                vehicleList.add(rsV.getString("REGISTRATION"));
            }
            entryReg.setItems(vehicleList);
            entryReg.getSelectionModel().select(entry.getVechID());	//set the options to search from in dropdown list

            ObservableList<String> customerList = FXCollections.observableArrayList();	//customer choicebox
            ResultSet rsC = conn.query("SELECT ID, SURNAME, FIRSTNAME FROM CUSTOMER ORDER BY SURNAME;");
            while (rsC.next()) {
                customerList.add(rsC.getString("ID") + ": " + rsC.getString("FIRSTNAME") + " " + rsC.getString("SURNAME"));
            }
            entryCustomer.setItems(customerList);
            entryCustomer.getSelectionModel().select(entry.getCustID());	//set the options to search from in dropdown list

            ObservableList<String> mechanicList = FXCollections.observableArrayList();	//mechanic choicebox
            ResultSet rsM = conn.query("SELECT * FROM USER WHERE NOT SYSADM = 'FALSE' ORDER BY SURNAME;");
            while (rsM.next()) {
                mechanicList.add(rsM.getString("ID") + ": " + rsM.getString("FIRSTNAME") + " " + rsM.getString("SURNAME"));
            }
            entryMechanic.setItems(mechanicList);
            entryMechanic.getSelectionModel().select(entry.getEmpID());	//set the options to search from in dropdown list
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @FXML
    private void editEntry(ActionEvent event) {
        BookingRegistry BR = BookingRegistry.getInstance();
        VehicleRegistry VR = VehicleRegistry.getInstance();
        String lineC = (String) entryCustomer.getSelectionModel().getSelectedItem();
        String[] custData = lineC.split("[\\s,:]+");
        String lineM = (String) entryMechanic.getSelectionModel().getSelectedItem();
        String[] mechData = lineM.split("[\\s,:]+");
        String date = entryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String ID = BR.findID(date, entryTime.getText(), entryDuration.getText(), (String) entryType.getSelectionModel().getSelectedItem(),custData[0], (String) entryReg.getSelectionModel().getSelectedItem(), mechData[0]);
        BR.editBooking(ID, date, entryTime.getText(), entryDuration.getText(), (String) entryType.getSelectionModel().getSelectedItem(),custData[0], (String) entryReg.getSelectionModel().getSelectedItem(), entryMileage.getText(), mechData[0]);
        VR.changeMileage((String) entryReg.getSelectionModel().getSelectedItem());
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
