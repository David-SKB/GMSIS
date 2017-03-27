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


public class AddWindowController implements Initializable
{
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		conn = DBConnection.getInstance();
		entryType.setItems(FXCollections.observableArrayList("Diagnosis", new Separator(), "Repair"));
		entryType.getSelectionModel().selectFirst();	//set the options to search from in dropdown list	
		entryDate.setValue(NOW_LOCALDATE());
		entryTime.setText("09:00");
		entryDuration.setText("1 Hour");
			
			ObservableList<String> vehicleList = FXCollections.observableArrayList();		//vehicle choicebox
			ArrayList<Vehicle> rsV = new ArrayList<Vehicle>();
                        rsV = VR.getAllVehicles();
                        for (int i = 0; i<rsV.size(); i++){
                            vehicleList.add(rsV.get(i).getRegistration());
                        }
			entryReg.setItems(vehicleList);
			entryReg.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
			
			ObservableList<String> customerList = FXCollections.observableArrayList();	//customer choicebox
			ArrayList<Customer> rsC = new ArrayList<Customer>();
                        rsC = CR.getActiveCustomers();
                        for (int i = 0; i<rsC.size(); i++){
                            customerList.add(rsC.get(i).getFirstname());
                            customerList.add(rsC.get(i).getFirstname());
                        }
			entryCustomer.setItems(customerList);
			entryCustomer.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
			
			ObservableList<String> mechanicList = FXCollections.observableArrayList();	//mechanic choicebox
			ArrayList<Mechanic> rsU = new ArrayList<Mechanic>();
                        rsU = UR.getMechanic();
                        for (int i = 0; i<rsU.size(); i++){
                            mechanicList.add(rsU.get(i).getFirstName());
                            mechanicList.add(rsU.get(i).getSurname());
                        }
			entryMechanic.setItems(mechanicList);
			entryMechanic.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
		
	}
	
	@FXML
	public void addEntry()
	{
                 
		String lineC = (String) entryCustomer.getSelectionModel().getSelectedItem();
		String[] custData = lineC.split("[\\s,:]+");
		String lineM = (String) entryMechanic.getSelectionModel().getSelectedItem();
		String[] mechData = lineM.split("[\\s,:]+");
		String date = entryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		if (parseLocalDateTime(date).compareTo(NOW_LOCALDATETIME()) > 0)
		{
			BR.addBooking(date, entryTime.getText(), entryDuration.getText(),(String) entryType.getSelectionModel().getSelectedItem(), custData[0],(String) entryReg.getSelectionModel().getSelectedItem(), mechData[0]);
			parentController.reset();
			Stage stage = (Stage) confirmButton.getScene().getWindow();
			stage.close();
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Bookings can only be made in the future.");
		}
	}
	
	@FXML
	public void cancel()
	{
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public LocalDate NOW_LOCALDATE()
	{
		return LocalDate.now();
	}
	
	public LocalDateTime NOW_LOCALDATETIME()
	{
		return LocalDateTime.now();
	}
	
	public LocalDateTime parseLocalDateTime(String str)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return LocalDateTime.parse(str, formatter);
	}
	
	public void setParentController(DiagRepairScreenController parentController)
	{
	    this.parentController = parentController;
	}

}
