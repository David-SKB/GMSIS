package diagrep.gui;


import common.DBConnection;
import common.Main;
import diagrep.gui.AddWindowController;
import diagrep.gui.EditWindowController;
import diagrep.logic.DiagRepairBooking;
import java.awt.Frame;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class DiagRepairScreenController implements Initializable
{   
	@FXML
	private TextField searchField;
	@FXML
	private ChoiceBox searchOptions;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<diagrep.logic.DiagRepairBooking> diagrepTable;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, Integer> colID;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colType;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colDate;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colDuration;
	//@FXML
	//private TableColumn<diagrep.logic.DiagRepairBooking, String> colVeh;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colVehReg;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colVehManufacturer;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colVehMileage;
	//@FXML
	//private TableColumn<diagrep.logic.DiagRepairBooking, String> colCust;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colCustID;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colCustfirstName;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colCustlastName;
	//@FXML
	//private TableColumn<diagrep.logic.DiagRepairBooking, String> colMech;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colMechID;
	@FXML
	private TableColumn<diagrep.logic.DiagRepairBooking, String> colMechDuration;

	private ObservableList<diagrep.logic.DiagRepairBooking> dataList;
	private DBConnection conn;

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		conn = DBConnection.getInstance();
		searchOptions.setItems(FXCollections.observableArrayList("Vehicle Registration No.", new Separator(), "Vehicle Manufacturer", new Separator(), "Customer Name"));
		searchOptions.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
		colID.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, Integer>("ID"));
		colType.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("type"));
		colDate.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("date"));
		colDuration.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("duration"));

		colVehReg.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("vehReg"));
		colVehManufacturer.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("vehManufacturer"));
		colVehMileage.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("vehMileage"));
		
		colCustID.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("custID"));
		colCustfirstName.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("custFirstName"));
		colCustlastName.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("custLastName"));

		colMechID.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("mechID"));
		colMechDuration.setCellValueFactory(new PropertyValueFactory<diagrep.logic.DiagRepairBooking, String>("mechDuration"));
		displayTableData(null);
	}

	@FXML
	public void addEntry() throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addWindow.fxml"));     
		Parent root = (Parent) fxmlLoader.load(); 
		AddWindowController controllerA = fxmlLoader.<AddWindowController>getController();
		controllerA.setParentController(this);
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("Add New Diagnosis/Repair Booking");
		stage.show();
	}
		
	@FXML
	public void editEntry() throws IOException
	{
		BookingRegistry entry = diagrepTable.getSelectionModel().getSelectedItem();
		if (entry == null)
		{
			JOptionPane.showMessageDialog(null, "Please select a booking to edit.");
			return;
		}
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("editWindow.fxml"));     
		Parent root = (Parent) fxmlLoader.load();
		EditWindowController controllerE = fxmlLoader.<EditWindowController>getController();
		controllerE.setParentController(this);
		controllerE.setEntry(entry);
		controllerE.reinit();
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("Edit Diagnosis/Repair Booking");
		stage.show();
	}
	
	@FXML
	public boolean deleteEntry()
	{
		ObservableList<diagrep.logic.DiagRepairBooking> selectedBooking = diagrepTable.getSelectionModel().getSelectedItems();
		int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected entry?", "Confirm Delete", 0);
		if (choice == JOptionPane.NO_OPTION)	//user cancels delete operation
		{
			return false;
		}
		for (diagrep.logic.DiagRepairBooking d : selectedBooking)
		{
			String bookingID = (d.getBookingID());
			int start = dataList.size();		//no. of entries before delete
			conn.update("DELETE FROM BookingIntegrated WHERE BookingID=\'" + bookingID + "\';");
			dataList.clear();
			displayTableData(null);
			if (start <= dataList.size())	//if no. of entries did not decrease, delete failed
			 {
				JOptionPane.showMessageDialog(null, "Failed to delete the booking with ID: "+bookingID);
				return false;
			}
		}
		return true;
	}
/*	
	@FXML
	public void search()
	{
		String search = searchField.getText();
		String searchBy = (String) searchOptions.getSelectionModel().getSelectedItem();
		String sql = "";
		switch (searchBy)	
		{
			case "Vehicle Registration No.":
				sql = "SELECT * FROM BookingIntegrated WHERE VehicleRegNo LIKE \'%"+search+"%\';";
				break;
			case "Vehicle Manufacturer":
				sql = "SELECT * FROM BookingIntegrated WHERE VehicleManufacturer LIKE \'%"+search+"%\';";
				break;
			case "Customer Name":
				String[] names = search.split("\\s+");
				if (names.length == 2)		//searching with first and last name
				{
					sql = "SELECT * FROM BookingIntegrated WHERE CustomerFirstName LIKE \'%"+names[0]+"%\' AND CustomerLastName LIKE \'%"+names[1]+"%\';";
				}
				else
				{
					sql = "SELECT * FROM BookingIntegrated WHERE CustomerFirstName LIKE \'%"+names[0]+"%\' UNION Select * FROM BookingIntegrated WHERE CustomerLastName LIKE \'%"+names[0]+"%\';";
				}
				break;
		}
		dataList.clear();
		displayTableData(sql);
	}
*/	
	@FXML
	public void displayRegistry() throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("mechanicsRegistryWindow.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.setTitle("Mechanics Registry");
		stage.show();
	}
	
	@FXML
	public void reset()
	{
		searchField.setText(null);
		dataList.clear();
		displayTableData(null);
	}
	
	public void displayTableData(String sql)	//optional String to display data based on query
	{
		dataList = FXCollections.observableArrayList();
		try
		{
			if (sql == null)	//display all data normally instead
			{
				sql = "SELECT * FROM BookingIntegrated;";
			}
			ResultSet rs = conn.query(sql);
			while (rs.next())	//starts from before the first row of results
			{
				dataList.add(new DiagRepairBooking(rs.getInt("BookingID"), rs.getString("BookingType"), rs.getString("BookingDate"), rs.getString("BookingDuration"), rs.getString("VehicleRegNo"), rs.getString("VehicleManufacturer"), rs.getInt("VehicleMileage"), rs.getInt("CustomerID"), rs.getString("CustomerFirstName"), rs.getString("CustomerLastName"),  rs.getInt("MechanicID"), rs.getString("MechanicDuration")));
				diagrepTable.setItems(dataList);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
	}
	
	@FXML
	public void displayPastBookings()
	{
		dataList = FXCollections.observableArrayList();
		try
		{
			int choice = bookingPrompt("past");
			if (choice == -1)
			{
				return;
			}
			else if (choice == 0)
			{
				String sql = "SELECT * FROM BookingIntegrated;";
				ResultSet rs = conn.query(sql);
				while (rs.next())	//starts from before the first row of results
				{
					if(parseLocalDateTime(rs.getString("BookingDate")).compareTo(NOW_LOCALDATETIME()) < 0)
					{
						dataList.add(new DiagRepairBooking(rs.getInt("BookingID"), rs.getString("BookingType"), rs.getString("BookingDate"), rs.getString("BookingDuration"), rs.getString("VehicleRegNo"), rs.getString("VehicleManufacturer"), rs.getInt("VehicleMileage"), rs.getInt("CustomerID"), rs.getString("CustomerFirstName"), rs.getString("CustomerLastName"),  rs.getInt("MechanicID"), rs.getString("MechanicDuration")));
					}	
				}
				diagrepTable.setItems(dataList);
			}
			else
			{
				ArrayList<String> vehicleList = new ArrayList<String>();	
				ResultSet rsV = conn.query("SELECT DISTINCT VehicleRegNo FROM BookingIntegrated ORDER BY VehicleRegNo;");
				while (rsV.next())
				{
					vehicleList.add(rsV.getString("VehicleRegNo"));
				}
				Object[] vehicles = vehicleList.toArray();
				String input = (String) JOptionPane.showInputDialog(null, "Choose a vehicle to display its bookings", "Vehicle Choice", JOptionPane.QUESTION_MESSAGE, null, vehicles, vehicles[0]);
				if (input == null)
				{
					return;
				}
				String sql = "SELECT * FROM BookingIntegrated WHERE VehicleRegNo='"+input+"';";
				ResultSet rs = conn.query(sql);
				while (rs.next())	//starts from before the first row of results
				{
					if(parseLocalDateTime(rs.getString("BookingDate")).compareTo(NOW_LOCALDATETIME()) < 0)
					{
						dataList.add(diagrep.logic.DiagRepairBooking(rs.getInt("BookingID"), rs.getString("BookingType"), rs.getString("BookingDate"), rs.getString("BookingDuration"), rs.getString("VehicleRegNo"), rs.getString("VehicleManufacturer"), rs.getInt("VehicleMileage"), rs.getInt("CustomerID"), rs.getString("CustomerFirstName"), rs.getString("CustomerLastName"),  rs.getInt("MechanicID"), rs.getString("MechanicDuration")));					}
					
				}
				diagrepTable.setItems(dataList);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
	}
	
	@FXML
	public void displayFutureBookings()
	{
		dataList = FXCollections.observableArrayList();
		try
		{
			int choice = bookingPrompt("future");
			if (choice == -1)
			{
				return;
			}
			else if (choice == 0)
			{
				String sql = "SELECT * FROM BookingIntegrated;";
				ResultSet rs = conn.query(sql);
				while (rs.next())	//starts from before the first row of results
				{
					if(parseLocalDateTime(rs.getString("BookingDate")).compareTo(NOW_LOCALDATETIME()) >= 0)
					{
						dataList.add(new Diagrep(rs.getInt("BookingID"), rs.getString("BookingType"), rs.getString("BookingDate"), rs.getString("BookingDuration"), rs.getString("VehicleRegNo"), rs.getString("VehicleManufacturer"), rs.getInt("VehicleMileage"), rs.getInt("CustomerID"), rs.getString("CustomerFirstName"), rs.getString("CustomerLastName"),  rs.getInt("MechanicID"), rs.getString("MechanicDuration")));
					}	
				}
				diagrepTable.setItems(dataList);
			}
			else
			{
				ArrayList<String> vehicleList = new ArrayList<String>();	
				ResultSet rsV = conn.query("SELECT DISTINCT VehicleRegNo FROM BookingIntegrated ORDER BY VehicleRegNo;");
				while (rsV.next())
				{
					vehicleList.add(rsV.getString("VehicleRegNo"));
				}
				Object[] vehicles = vehicleList.toArray();
				String input = (String) JOptionPane.showInputDialog(null, "Choose a vehicle to display its bookings", "Vehicle Choice", JOptionPane.QUESTION_MESSAGE, null, vehicles, vehicles[0]);
				if (input == null)
				{
					return;
				}
				String sql = "SELECT * FROM BookingIntegrated WHERE VehicleRegNo='"+input+"';";
				ResultSet rs = conn.query(sql);
				while (rs.next())	//starts from before the first row of results
				{
					if(parseLocalDateTime(rs.getString("BookingDate")).compareTo(NOW_LOCALDATETIME()) >= 0)
					{
						dataList.add(new Diagrep(rs.getInt("BookingID"), rs.getString("BookingType"), rs.getString("BookingDate"), rs.getString("BookingDuration"), rs.getString("VehicleRegNo"), rs.getString("VehicleManufacturer"), rs.getInt("VehicleMileage"), rs.getInt("CustomerID"), rs.getString("CustomerFirstName"), rs.getString("CustomerLastName"),  rs.getInt("MechanicID"), rs.getString("MechanicDuration")));
					}
					
				}
				diagrepTable.setItems(dataList);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
	}
	
	public int bookingPrompt(String str)		//returns 0 if the user wants to view all vehicle bookings, and 1 for a specific vehicle
	{
		String[] options = { "All", "Specific" };
		return JOptionPane.showOptionDialog(null, "Would you like to view the "+str+" bookings of all vehicles, or a specific one?", "Booking Prompt", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
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
}
