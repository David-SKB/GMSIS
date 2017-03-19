/**
 * 
 * References:
 * NOW_LOCAL_DATE(): http://stackoverflow.com/questions/31899692/how-to-set-default-value-for-javafx-datepicker-in-fxml
 */
package diagrep.gui;

import common.DBConnection;
import diagrep.logic.BookingRegistry;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		conn = DBConnection.getInstance();
		entryType.setItems(FXCollections.observableArrayList("Diagnosis", new Separator(), "Repair"));
		entryType.getSelectionModel().selectFirst();	//set the options to search from in dropdown list	
		entryDate.setValue(NOW_LOCALDATE());
		entryTime.setText("09:00");
		entryDuration.setText("01:00");
		
		try	//TODO: Integrate DB Vehicle table
		{
			ObservableList<String> vehicleList = FXCollections.observableArrayList();		//vehicle choicebox
			ResultSet rsV = conn.query("SELECT REGISTRATION FROM VEHICLE ORDER BY VehicleRegNo;");
			while (rsV.next())
			{
				vehicleList.add(rsV.getString("REGISTRATION"));
			}
			entryReg.setItems(vehicleList);
			entryReg.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
			
			ObservableList<String> customerList = FXCollections.observableArrayList();	//customer choicebox
			ResultSet rsC = conn.query("SELECT ID, SURNAME, FIRSTNAME FROM CUSTOMER ORDER BY SURNAME;");
			while (rsC.next())
			{
				customerList.add(rsC.getString("ID")+": "+rsC.getString("FIRSTNAME")+" "+rsC.getString("SURNAME"));
			}
			entryCustomer.setItems(customerList);
			entryCustomer.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
			
			ObservableList<String> mechanicList = FXCollections.observableArrayList();	//mechanic choicebox
			ResultSet rsM = conn.query("SELECT * FROM USER WHERE NOT SYSADM = 'FALSE' ORDER BY SURNAME;");
			while (rsM.next())
			{
				mechanicList.add(rsM.getString("ID")+": "+rsM.getString("FIRSTNAME")+" "+rsM.getString("SURNAME"));
			}
			entryMechanic.setItems(mechanicList);
			entryMechanic.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		
	}
	
	@FXML
	public void addEntry()
	{
                BookingRegistry BR = BookingRegistry.getInstance(); 
		String lineC = (String) entryCustomer.getSelectionModel().getSelectedItem();
		String[] custData = lineC.split("[\\s,:]+");
		String lineM = (String) entryMechanic.getSelectionModel().getSelectedItem();
		String[] mechData = lineM.split("[\\s,:]+");
		String date = entryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		if (parseLocalDateTime(date).compareTo(NOW_LOCALDATETIME()) > 0)
		{
			BR.addBooking(date, entryTime.getText(), Integer.parseInt(entryDuration.getText()),(String) entryType.getSelectionModel().getSelectedItem(), Integer.parseInt(custData[0]), (int) entryReg.getSelectionModel().getSelectedItem(), Integer.parseInt(mechData[0]));
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return LocalDateTime.parse(str, formatter);
	}
	
	public void setParentController(DiagRepairScreenController parentController)
	{
	    this.parentController = parentController;
	}

}
