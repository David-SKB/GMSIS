/**
 * FXML Controller class
 *
 * @author Nexus
 */
package David_Aelmans;

import common.DBConnection;
import David_Aelmans.DiagRepairBooking;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private ChoiceBox entryCustomer;
	@FXML
	private ChoiceBox entryMechanic;

	private DiagRepairScreenController parentController;
	private DiagRepairBooking entry;
	private DBConnection conn;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{

	}
	
	public void reinit()
	{
		conn = DBConnection.getInstance();
		entryType.setItems(FXCollections.observableArrayList("Repair", new Separator(), "Maintenance"));
		entryType.getSelectionModel().select(entry.getType());
		
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
		
		String[] line = entry.getDate().split("\\s+");
		entryDate.setValue(LocalDate.parse(line[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		entryTime.setText(line[1]);
		entryDuration.setText(entry.getDuration());
		try
		{
			ObservableList<String> vehicleList = FXCollections.observableArrayList();		//vehicle choicebox
			ResultSet rsV = db.query("SELECT DISTINCT VehicleRegNo FROM BookingIntegrated ORDER BY VehicleRegNo;");
			while (rsV.next())
			{
				vehicleList.add(rsV.getString("VehicleRegNo"));
			}
			entryReg.setItems(vehicleList);
			entryReg.getSelectionModel().select(entry.getVehReg());
			
			ObservableList<String> customerList = FXCollections.observableArrayList();	//customer choicebox
			ResultSet rsC = db.query("SELECT DISTINCT CustomerID, CustomerFirstName, CustomerLastName FROM Customer ORDER BY CustomerFirstName;");
			while (rsC.next())
			{
				customerList.add(rsC.getString("CustomerID")+": "+rsC.getString("CustomerFirstName")+" "+rsC.getString("CustomerLastName"));
			}
			entryCustomer.setItems(customerList);
			entryCustomer.getSelectionModel().select(entry.getCustID()+": "+entry.getCustFirstName()+" "+entry.getCustLastName());
			
			ObservableList<String> mechanicList = FXCollections.observableArrayList();	//mechanic choicebox
			ResultSet rsM = db.query("SELECT DISTINCT * FROM Mechanic ORDER BY MechanicFirstName;");
			while (rsM.next())
			{
				mechanicList.add(rsM.getString("MechanicID"));	    //mechanicList.add(rsM.getString("MechanicID")+": "+rsM.getString("MechanicFirstName")+" "+rsM.getString("MechanicLastName"));
			}
			entryMechanic.setItems(mechanicList);
			entryMechanic.getSelectionModel().select(Integer.toString(entry.getMechID()));
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
	}

	@FXML
	private void editEntry(ActionEvent event)
	{
		String lineC = (String) entryCustomer.getSelectionModel().getSelectedItem();
		String[] custData = lineC.split("[\\s,:]+");
		String lineM = (String) entryMechanic.getSelectionModel().getSelectedItem();
		String[] mechData = lineM.split("[\\s,:]+");
		String sql = "UPDATE BookingIntegrated SET BookingType='"+(String) entryType.getSelectionModel().getSelectedItem()+"', BookingDate='"+entryDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" "+entryTime.getText()+"', BookingDuration='"+entryDuration.getText()+"', VehicleRegNo='"+(String) entryReg.getSelectionModel().getSelectedItem()+"', VehicleManufacturer='TEMP', VehicleMileage='0', CustomerID='"+custData[0]+"', CustomerFirstName='"+custData[1]+"', CustomerLastName='"+custData[2]+"', MechanicID='"+mechData[0]+"', MechanicDuration='00:00' WHERE BookingID="+entry.getID();
		db.update(sql);
		parentController.reset();
		Stage stage = (Stage) confirmButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void cancel(ActionEvent event)
	{
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void setEntry(Diagrep entry)
	{
		this.entry = entry;
	}

	public void setParentController(DiagrepScreenController parentController)
	{
		this.parentController = parentController;
	}
    
}
