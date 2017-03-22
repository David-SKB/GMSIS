package diagrep.gui;

import common.DBConnection;
import common.Main;
import diagrep.gui.AddWindowController;
import diagrep.logic.Booking;
import diagrep.logic.BookingRegistry;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;

public class DiagRepairScreenController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox searchOptions;
    @FXML
    private Button searchButton;
    @FXML
    private TableView<DiagRepairBooking> diagrepTable;
    @FXML
    private TableColumn<DiagRepairBooking, Integer> colID;
    @FXML
    private TableColumn<DiagRepairBooking, String> colDate;
    @FXML
    private TableColumn<DiagRepairBooking, String> colStartTime;
    @FXML
    private TableColumn<DiagRepairBooking, String> colDuration;
    @FXML
    private TableColumn<DiagRepairBooking, String> colType;
    //@FXML
    //private TableColumn<DiagRepairBooking, String> colCust;
    @FXML
    private TableColumn<DiagRepairBooking, String> colCustID;
    @FXML
    private TableColumn<DiagRepairBooking, String> colCustfirstName;
    @FXML
    private TableColumn<DiagRepairBooking, String> colCustlastName;
    //@FXML
    //private TableColumn<DiagRepairBooking, String> colVeh;
    @FXML
    private TableColumn<DiagRepairBooking, String> colVehReg;
    @FXML
    private TableColumn<DiagRepairBooking, String> colVehManufacturer;
    @FXML
    private TableColumn<DiagRepairBooking, String> colVehMileage;
    //@FXML
    //private TableColumn<DiagRepairBooking, String> colMech;
    @FXML
    private TableColumn<DiagRepairBooking, String> colMechID;
    @FXML
    private TableColumn<DiagRepairBooking, String> colMechFirstName;
    @FXML
    private TableColumn<DiagRepairBooking, String> colMechLastName;
    @FXML
    private TableColumn<DiagRepairBooking, String> colMechRate;

    private ObservableList<DiagRepairBooking> dataList;
    private DBConnection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getInstance();
        searchOptions.setItems(FXCollections.observableArrayList("Vehicle Registration No.", new Separator(), "Vehicle Manufacturer", new Separator(), "Customer Name"));
        searchOptions.getSelectionModel().selectFirst();	//set the options to search from in dropdown list
        colID.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, Integer>("ID"));
        colDate.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("type"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("starttime"));
        colDate.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("date"));
        colDuration.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("duration"));

        colVehReg.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("vehReg"));
        colVehManufacturer.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("vehManufacturer"));
        colVehMileage.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("vehMileage"));

        colCustID.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("custID"));
        colCustfirstName.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("custFirstName"));
        colCustlastName.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("custLastName"));

        colMechID.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("mechID"));
        colMechFirstName.setCellValueFactory(new PropertyValueFactory<>("mechFirstName"));
        colMechLastName.setCellValueFactory(new PropertyValueFactory<>("mechLastName"));
        colMechRate.setCellValueFactory(new PropertyValueFactory<DiagRepairBooking, String>("mechRate"));
        displayTableData(null);
    }

    @FXML
    public void addEntry() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addWindow.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        AddWindowController controllerA = fxmlLoader.<AddWindowController>getController();
        controllerA.setParentController(this);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add New Diagnosis/Repair Booking");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.stage);
        stage.show();
    }

    @FXML
    public void editEntry() throws IOException {
        DiagRepairBooking entry = diagrepTable.getSelectionModel().getSelectedItem();
        if (entry == null) {
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
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.stage);
        stage.show();
    }

    @FXML
    public boolean deleteEntry() {
        BookingRegistry BR = BookingRegistry.getInstance();
        ObservableList<DiagRepairBooking> selectedBooking = diagrepTable.getSelectionModel().getSelectedItems();
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the selected entry?", "Confirm Delete", 0);
        if (choice == JOptionPane.NO_OPTION) //user cancels delete operation
        {
            return false;
        }
        for (DiagRepairBooking d : selectedBooking) {
            String bookingID = (d.getBookingID());
            int start = dataList.size();		//no. of entries before delete
            BR.deleteBooking(bookingID);
            dataList.clear();
            displayTableData(null);
            if (start <= dataList.size()) //if no. of entries did not decrease, delete failed
            {
                JOptionPane.showMessageDialog(null, "Failed to delete the booking with ID: " + bookingID);
                return false;
            }
        }
        return true;
    }

    @FXML
    public void search() {
        BookingRegistry BR = BookingRegistry.getInstance();
        String search = searchField.getText();
        String searchBy = (String) searchOptions.getSelectionModel().getSelectedItem();
        ArrayList<DiagRepairBooking> result = new ArrayList<>();
        switch (searchBy) {
            case "Vehicle Registration No.":
                result = BR.searchBookingByVechID(search);
                break;
            case "Vehicle Manufacturer":
                result = BR.searchBookingByVechModel(search);
                break;
            case "Customer Name":
                String[] names = search.split("\\s+");
                result = BR.searchBookingByCustName(names[0], names[1]);
                break;
        }
        dataList.clear();
        displayTableData(result);
    }

    @FXML
    public void reset() {
        searchField.setText(null);
        dataList.clear();
        displayTableData(null);
    }

    public void displayTableData(ArrayList<DiagRepairBooking> result) //optional ArrayList to display data based on query
    {
        dataList = FXCollections.observableArrayList();
        BookingRegistry BR = BookingRegistry.getInstance();
        if (result == null) //display all data normally instead
        {
            result = BR.getListBookings();
        }
        for (int i = 0; i < result.size(); i++) {
            dataList.add(result.get(i));
            diagrepTable.setItems(dataList);
        } 
    }

    @FXML
    public void displayPastBookings() {
        dataList = FXCollections.observableArrayList();
        BookingRegistry BR = BookingRegistry.getInstance();

        int choice = bookingPrompt("past");
        if (choice == -1) {
            return;
        } else if (choice == 0) {
            ArrayList<DiagRepairBooking> result = BR.getListBookings();
            for (int i = 0; i < result.size(); i++) {
                if (parseLocalDateTime(result.get(i).getBookingDate()).compareTo(NOW_LOCALDATETIME()) < 0) {
                    dataList.add(result.get(i));
                }
            }
            diagrepTable.setItems(dataList);
        } else {
            VehicleRegistry VR = VehicleRegistry.getInstance();
            ArrayList<Integer> vehicleList = new ArrayList<Integer>();
            ArrayList<Vehicle> Vresult = VR.getAllVehicles();
            for (int i = 0; i < Vresult.size(); i++) {
                vehicleList.add(Vresult.get(i).getId());
            }
            Object[] vehicles = vehicleList.toArray();
            String input = (String) JOptionPane.showInputDialog(null, "Choose a vehicle to display its bookings", "Vehicle Choice", JOptionPane.QUESTION_MESSAGE, null, vehicles, vehicles[0]);
            if (input == null) {
                return;
            }
            ArrayList<DiagRepairBooking> result = BR.searchBookingByVechID(input);
            for (int i = 0; i < result.size(); i++) {
                if (parseLocalDateTime(result.get(i).getBookingDate()).compareTo(NOW_LOCALDATETIME()) < 0) {
                    dataList.add(result.get(i));
                }
            }
            diagrepTable.setItems(dataList);
        }
    }

    @FXML
    public void displayFutureBookings() {
        dataList = FXCollections.observableArrayList();
        BookingRegistry BR = BookingRegistry.getInstance();
        int choice = bookingPrompt("future");
        if (choice == -1) {
            return;
        } else if (choice == 0) {
            ArrayList<DiagRepairBooking> result = BR.getListBookings();
            for (int i = 0; i < result.size(); i++) {
                if (parseLocalDateTime(result.get(i).getBookingDate()).compareTo(NOW_LOCALDATETIME()) >= 0) {
                    dataList.add(result.get(i));
                }
            }
            diagrepTable.setItems(dataList);
        } else {
            VehicleRegistry VR = VehicleRegistry.getInstance();
            ArrayList<Integer> vehicleList = new ArrayList<Integer>();
            ArrayList<Vehicle> Vresult = VR.getAllVehicles();
            for (int i = 0; i < Vresult.size(); i++) {
                vehicleList.add(Vresult.get(i).getId());
            }
            Object[] vehicles = vehicleList.toArray();
            String input = (String) JOptionPane.showInputDialog(null, "Choose a vehicle to display its bookings", "Vehicle Choice", JOptionPane.QUESTION_MESSAGE, null, vehicles, vehicles[0]);
            if (input == null) {
                return;
            }
            ArrayList<DiagRepairBooking> result = BR.searchBookingByVechID(input);
            for (int i = 0; i < result.size(); i++) {
                if (parseLocalDateTime(result.get(i).getBookingDate()).compareTo(NOW_LOCALDATETIME()) >= 0) {
                    dataList.add(result.get(i));
                }
            }
            diagrepTable.setItems(dataList);
        }
    }

    public int bookingPrompt(String str) //returns 0 if the user wants to view all vehicle bookings, and 1 for a specific vehicle
    {
        String[] options = {"All", "Specific"};
        return JOptionPane.showOptionDialog(null, "Would you like to view the " + str + " bookings of all vehicles, or a specific one?", "Booking Prompt", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public LocalDateTime NOW_LOCALDATETIME() {
        return LocalDateTime.now();
    }

    public LocalDateTime parseLocalDateTime(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(str, formatter);
    }
}
