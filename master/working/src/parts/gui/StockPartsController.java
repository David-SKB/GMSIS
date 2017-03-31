/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import parts.logic.PartRegistry;

import common.DBConnection;
import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import diagrep.logic.BookingRegistry;
import diagrep.logic.DiagRepairBooking;
import java.io.IOException;
import java.math.BigDecimal;
import parts.logic.Part;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import parts.logic.UsedPart;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;
import specialist.logic.ErrorChecks;
//

/**
 * FXML Controller class
 *
 * @author jr308
 */
public class StockPartsController implements Initializable {
    private boolean loadAllUsed = true;
    private String tableToSearch;
    private PartRegistry partR = PartRegistry.getInstance();
    private BookingRegistry bookingR = BookingRegistry.getInstance();
    private CustomerRegistry customerR = CustomerRegistry.getInstance();
    private VehicleRegistry vehicleR = VehicleRegistry.getInstance();
    private ErrorChecks EC = ErrorChecks.getInstance();
    
    private final ObservableList<Part> oPartList = FXCollections.observableArrayList();
    //Stock Parts
    
    @FXML
    private ObservableList<String> cmBoxOptions;
    @FXML
    private ComboBox searchBy = new ComboBox();
    @FXML
    private AnchorPane stockParts;

    @FXML
    private AnchorPane usedParts;

    @FXML
    private AnchorPane repairs;

    private Part selectedPart;

    //stock parts gui
    @FXML
    private TableView<Part> stockTable;
    @FXML
    private TableColumn stockIDCol, nameCol, descriptionCol, costCol, //FXML TableColumn. Columns form the TableView element.
            stockCol;
    @FXML
    private TextField quantityTextField, searchTextField;
    @FXML
    private TextArea partNameTextArea, partDescriptionTextArea,
            partCostTextArea, partStockLevelTextArea;

    @FXML
    private DatePicker deliveryDatePicker, deliveryDatePickerQuantity;

    //used parts gui
    private final ObservableList<UsedPart> oUsedPartList = FXCollections.observableArrayList();
    private UsedPart selectedUsedPart;
    @FXML
    private TableView<UsedPart> usedPartsTable;
    @FXML
    private TableColumn usedPartIdCol, nameCol1, descriptionCol1, costCol1, //FXML TableColumn. Columns form the TableView element.
            vehicleCol1, customerCol1, firstNameCol1,
            lastNameCol1, repairIDCol1, warrantyStartCol, warrantyEndCol;
    @FXML
    private TextArea usedPartNameTextArea, usedPartDescriptionTextArea,
            usedPartCostTextArea, usedPartStockLevelTextArea,
            dateInstalledTextArea, warrantyEndsTextArea,
            vehicleRegistrationTextArea, customerNameTextArea,
            repairIDTextArea;

    //repairs gui
    private final ObservableList<RepairWrapper> oRepairList = FXCollections.observableArrayList();
    int currentRepairID;
    @FXML
    private DatePicker usedPartInstallationDatePicker;
    private RepairWrapper selectedRepair;
    @FXML
    private TableView<RepairWrapper> repairTable;
    @FXML
    private TableColumn repairIDCol, rVehicleRegistrationCol, //FXML TableColumn. Columns form the TableView element.
            rCustomerCol1, rFirstNameCol,
            rLastNameCol, rDateCol;
    @FXML
    private TableView<Part> rStockTable;
    @FXML
    private TableColumn rNameCol, rDescriptionCol, rCostCol, //FXML TableColumn. Columns form the TableView element.
            rStockCol;
    @FXML
    private TextField repairStockSearchTextField;
    //deliveries

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test");
        //partR.addPart("axis", "circular", 100);
        System.out.println("test2");
        stockParts.setVisible(true);
        usedParts.setVisible(false);
        repairs.setVisible(false);
        setupRowListeners();
        loadAllParts();
        loadStockParts();
        tableToSearch = "STOCKPARTS";
        cmBoxOptions =  FXCollections.observableArrayList("Part Name");
        searchBy.setItems(cmBoxOptions);
        searchBy.getSelectionModel().selectFirst();
        //EC.DisableDatesBefore(deliveryDatePicker, LocalDate.now());
        //EC.DisableDatesBefore(deliveryDatePickerQuantity, LocalDate.now());
        //EC.DisableDatesBefore(usedPartInstallationDatePicker, LocalDate.now());
        EC.SetWordSpaceRestriction(partNameTextArea);
        EC.SetAddressRestriction(partDescriptionTextArea);
        
        
    }

    //STOCK METHODS
    //loads oList into stock table
    public void loadStockParts() {//ActionEvent event){

        //System.out.println("test3");
        //loadAllParts();
        stockTable.setEditable(true);
        stockIDCol.setCellValueFactory(
                new PropertyValueFactory<Part, Integer>("id"));
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("description"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("cost"));
        stockCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("stocklevel"));
        stockTable.setItems(oPartList);
        //System.out.println("test3");
    }

    public void loadAllParts() {//ActionEvent event){
        System.out.println("test4");
        oPartList.clear();
        ArrayList<Part> partlist = partR.getStockParts();
        System.out.println(partlist == null);
        System.out.println(partlist == null);
        if (partlist != null) {
            System.out.println("inside if");
            for (int i = 0; i < partlist.size(); i++) {
                System.out.println("inside for");
                oPartList.add(partlist.get(i));
            }
        }
        //loadStockParts();
    }

    public void updateStockLevel(ActionEvent event) {
        selectedPart = null;
        selectedPart = stockTable.getSelectionModel().getSelectedItem();
        if(!increaseStockQuantityValidation())
            return;
        System.out.println(selectedPart.getName());
        partR.updateStock(selectedPart.getId(), Integer.parseInt(quantityTextField.getText()));
        loadAllParts();
        partR.addDelivery(selectedPart.getId(), Integer.parseInt(quantityTextField.getText()), EC.toString(deliveryDatePickerQuantity));
    }

    public void addStockPart(ActionEvent event) {
        if(!addStockPartValidation())
            return;
        String name = partNameTextArea.getText();
        String description = partDescriptionTextArea.getText();
        BigDecimal cost = new BigDecimal((partCostTextArea.getText()));
        String quantity = partStockLevelTextArea.getText();
        partR.addPart(name, description, cost, Integer.parseInt(quantity));
        loadAllParts();
        int id = oPartList.get(oPartList.size()-1).getId();
        System.out.println(EC.toString(deliveryDatePicker));
        partR.addDelivery(id, Integer.parseInt(quantity), EC.toString(deliveryDatePicker));
    }
    
    public void deleteStockPart(ActionEvent event) {
        selectedPart = stockTable.getSelectionModel().getSelectedItem();
        if(!editAndDeleteValidation())
            return;
        boolean success;
        success = partR.deletePart(selectedPart.getId());
        loadAllParts();
    }

    
    private void setupRowListeners() {
        stockTable.setRowFactory(table -> {
            TableRow<Part> row = new TableRow<>();

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty())) {
                    selectedPart = stockTable.getSelectionModel().getSelectedItem();
                    loadPartIntoFields();
                }
            });
            return row;
        });
    }

    public void loadPartIntoFields() {
        partNameTextArea.setText(selectedPart.getName());
        partDescriptionTextArea.setText(selectedPart.getDescription());
        partCostTextArea.setText(selectedPart.getCost());
        partStockLevelTextArea.setText(String.valueOf(selectedPart.getStocklevel()));
    }

    //USED PARTS METHODS
    public void loadUsedPartsTable() {//ActionEvent event){

        System.out.println("test3");
        usedPartsTable.setEditable(true);
        usedPartIdCol.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("Id"));
        nameCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("name"));
        descriptionCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("description"));
        costCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("cost"));
        vehicleCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("VehicleRegistration"));
        firstNameCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("CustomerFirstName"));
        lastNameCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("CustomerSurname"));
        repairIDCol1.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("RepairID"));
        warrantyStartCol.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("warrantyStart"));
        warrantyEndCol.setCellValueFactory(
                new PropertyValueFactory<UsedPart, String>("warrantyEnd"));
        usedPartsTable.setItems(oUsedPartList);

    }
    
    public void loadUsedParts() {//ActionEvent event){

        oUsedPartList.clear();
        ArrayList<UsedPart> usedPartList = partR.getAllUsedParts();
        
        if (usedPartList != null) {
            for (int i = 0; i < usedPartList.size(); i++) {
                oUsedPartList.add(usedPartList.get(i));
            }
        }

    }
    
    public void loadUsedPartsRepair(int id) {//ActionEvent event){

        oUsedPartList.clear();
        ArrayList<UsedPart> usedPartList = partR.getUsedPartsByRepair(id);
        
        if (usedPartList != null) {
            for (int i = 0; i < usedPartList.size(); i++) {
                oUsedPartList.add(usedPartList.get(i));
            }
        }

    }
    
    public void deleteUsedPart(ActionEvent event) {
        selectedUsedPart = null;
        selectedUsedPart = usedPartsTable.getSelectionModel().getSelectedItem();
        if(!viewDetailsAndDeleteValidation())
            return;
        boolean success;
        success = partR.deleteUsedPart(Integer.parseInt(selectedUsedPart.getId()));
        partR.updateBill("-"+selectedUsedPart.getCost().toString(), selectedUsedPart.getRepairID());
        if (loadAllUsed)
            loadUsedParts();
        else
            loadUsedPartsRepair(currentRepairID);
        loadUsedPartsTable();
        
    }

    //REPAIRS METHODS
    //load data into stock table
    public void loadRStockParts() {//ActionEvent event){
        rStockTable.setEditable(true);
        rNameCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name"));
        rDescriptionCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("description"));
        rCostCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("cost"));
        rStockCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("stocklevel"));
        rStockTable.setItems(oPartList);
    }

    //load data into repairs table
    public void loadRepairsTable() {//ActionEvent event){

        repairTable.setEditable(true);
        repairIDCol.setCellValueFactory(
                new PropertyValueFactory<RepairWrapper, String>("repairID"));
        rVehicleRegistrationCol.setCellValueFactory(
                new PropertyValueFactory<RepairWrapper, String>("vehicleRegistration"));
        rFirstNameCol.setCellValueFactory(
                new PropertyValueFactory<RepairWrapper, String>("CustomerFirstName"));
        rLastNameCol.setCellValueFactory(
                new PropertyValueFactory<RepairWrapper, String>("CustomerSurname"));
        rDateCol.setCellValueFactory(
                new PropertyValueFactory<RepairWrapper, String>("date"));
        repairTable.setItems(oRepairList);

    }

    //loads relevant data in RepairWrapper
    public void loadAllRepairs() {//ActionEvent event){

        System.out.println("test4");
        oRepairList.clear();
        ArrayList<RepairWrapper> repairList = new ArrayList<RepairWrapper>();
        ArrayList<DiagRepairBooking> bookings = bookingR.getListBookings();
        for (int i = 0; i < bookings.size(); i++) {
            Vehicle vehicle = vehicleR.searchForEdit(bookings.get(i).getVehreg());
            Customer customer = customerR.searchCustomerByID(bookings.get(i).getCust());
            repairList.add(new RepairWrapper(customer, vehicle, bookings.get(i)));
        }
        if (repairList != null) {
            System.out.println("inside if");
            for (int i = 0; i < repairList.size(); i++) {
                System.out.println("repair cust id" + repairList.get(i).getCustomerID());
                oRepairList.add(repairList.get(i));
            }
        }

    }

    public void addPartToRepair(ActionEvent event) {
        selectedPart = null;
        selectedRepair = null;
        selectedPart = rStockTable.getSelectionModel().getSelectedItem();
        selectedRepair = repairTable.getSelectionModel().getSelectedItem();
        if(!addPartToRepairValidation())
            return;
        String repairID = selectedRepair.getRepairID();
        int partId = selectedPart.getId();
        BigDecimal cost =  new BigDecimal(selectedPart.getCost());
        //Date warrantyStart = 
        boolean success = partR.usePart(repairID, partId, EC.addYear(usedPartInstallationDatePicker),EC.toString(usedPartInstallationDatePicker), cost);
        if (selectedPart.getStocklevel() > 0) {
            partR.updateStock(selectedPart.getId(), -1);
        }
        partR.updateBill(cost.toString(), repairID);
        loadAllParts();
        loadRStockParts();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SUCCESS");
        alert.setHeaderText(null);
        alert.setContentText("Part Added to Repair Successfully");
        alert.showAndWait();
        
    }

    //view Deliveries
    
    public void viewDeliveries(ActionEvent event){
        try {
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("viewDeliveries.fxml"));//.openStream()); 
                //ViewDeliveriesController controller = (ViewDeliveriesController)loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(root.getScene().getWindow());
                stage.setScene(scene);
                stage.showAndWait();
            } catch(IOException ex) {
                System.err.println("Error: "+ex);
            }
    }

    //view Edit Part
    public void viewEditPart(ActionEvent event){
        try {
                selectedPart = stockTable.getSelectionModel().getSelectedItem();
                if(!editAndDeleteValidation())
                    return;
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("EditPart.fxml").openStream()); 
                EditPartController controller = (EditPartController)loader.getController();
                controller.loadPart(selectedPart);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(root.getScene().getWindow());
                stage.setScene(scene);
                stage.showAndWait();
                loadAllParts();
                loadStockParts();
            } catch(IOException ex) {
                System.err.println("Error: "+ex);
            }
    }
    
    public void viewVehicleDetails(ActionEvent event){
        try {
                selectedUsedPart = null;
                selectedUsedPart = usedPartsTable.getSelectionModel().getSelectedItem();
                if(!viewDetailsAndDeleteValidation())
                    return;
                Vehicle veh = vehicleR.searchForEdit(selectedUsedPart.getVehicleRegistration());
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("viewVehicleDetails.fxml").openStream()); 
                viewVehicleController controller = (viewVehicleController)loader.getController();
                controller.loadVehicleData(veh);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(root.getScene().getWindow());
                stage.setScene(scene);
                stage.showAndWait();
                loadAllParts();
                loadStockParts();
            } catch(IOException ex) {
                System.err.println("Error: "+ ex);
            }
    }
    
    public void viewCustomerDetails(ActionEvent event){
        try {
                selectedUsedPart = null;
                selectedUsedPart = usedPartsTable.getSelectionModel().getSelectedItem();
                if(!viewDetailsAndDeleteValidation())
                    return;
                Customer cust = customerR.searchCustomerByID(selectedUsedPart.getCustomerID());
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("viewCustomerDetails.fxml").openStream()); 
                viewCustomerController controller = (viewCustomerController)loader.getController();
                controller.loadCustomerData(cust);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(root.getScene().getWindow());
                stage.setScene(scene);
                stage.showAndWait();
                loadAllParts();
                loadStockParts();
            } catch(IOException ex) {
                System.err.println("Error: "+ex);
            }
    }

    
    //SEARCH METHODS
    public void search(ActionEvent event) {
        if(tableToSearch == "STOCKPARTS")
            searchParts();
        else if (tableToSearch == "USEDPARTS")
            searchUsedParts();
        else
            searchRepairs();
    }
    public void searchParts() {
        oPartList.clear();
        ArrayList<Part> partlist = partR.searchStockParts(searchTextField.getText(), "NAME");
        System.out.println(partlist == null);
        if (partlist != null) {
            System.out.println("inside if");
            for (int i = 0; i < partlist.size(); i++) {
                System.out.println("inside for");
                oPartList.add(partlist.get(i));
            }
        }
        loadStockParts();
    }
    
    public void searchUsedParts() {
        oUsedPartList.clear();
        ArrayList<UsedPart> partlist;
        if(searchBy.getValue().toString().equalsIgnoreCase("Vehicle Registration"))
            partlist = partR.getUsedPartsByVehicle(searchTextField.getText());
        else 
            partlist = partR.getUsedPartsByRepair(Integer.parseInt(searchTextField.getText()));
        if (partlist != null) {
            System.out.println("inside if");
            for (int i = 0; i < partlist.size(); i++) {
                System.out.println("inside for");
                oUsedPartList.add(partlist.get(i));
            }
        }
        loadStockParts();
    }
    
    public void searchRepairs() {
        
        
        oRepairList.clear();
        ArrayList<RepairWrapper> repairList = new ArrayList<RepairWrapper>();
        ArrayList<DiagRepairBooking> bookings;
        if (searchTextField.getText().isEmpty())
        {
            bookings = bookingR.getListBookings();
        }
        else if(searchBy.getValue().toString().equalsIgnoreCase("Vehicle Registration"))
            bookings = bookingR.searchBookingByVechID(searchTextField.getText());
        else if(searchBy.getValue().toString().equalsIgnoreCase("First Name"))
            bookings = bookingR.searchBookingByCustomerFirstName(searchTextField.getText());
        else
            bookings = bookingR.searchBookingByCustomerSurname(searchTextField.getText());
        for (int i = 0; i < bookings.size(); i++) {
            Vehicle vehicle = vehicleR.searchForEdit(bookings.get(i).getVehreg());
            Customer customer = customerR.searchCustomerByID(bookings.get(i).getCust());
            repairList.add(new RepairWrapper(customer, vehicle, bookings.get(i)));
        }
        if (repairList != null) {
            System.out.println("inside if");
            for (int i = 0; i < repairList.size(); i++) {
                System.out.println("repair cust id" + repairList.get(i).getCustomerID());
                oRepairList.add(repairList.get(i));
            }
        }
        loadRepairsTable();
    }

    public void searchrParts(ActionEvent Event) {
        
        oPartList.clear();
        ArrayList<Part> partlist = partR.searchStockParts(repairStockSearchTextField.getText(), "NAME");
        System.out.println(partlist == null);
        if (partlist != null) {
            System.out.println("inside if");
            for (int i = 0; i < partlist.size(); i++) {
                System.out.println("inside for");
                oPartList.add(partlist.get(i));
            }
        }
        loadRStockParts();
    }
    
    //CHANGING ANCHOR METHODS
    public void viewUsedPartsAnchor(ActionEvent event) {
        cmBoxOptions =  FXCollections.observableArrayList("Repair ID","Vehicle Registration");
        searchBy.setItems(cmBoxOptions);
        searchBy.getSelectionModel().selectFirst();
        tableToSearch = "USEDPARTS";
        loadAllUsed = true;
        repairs.setVisible(false);
        stockParts.setVisible(false);
        usedParts.setVisible(true);
        loadUsedParts();
        loadUsedPartsTable();
    }

    public void viewStockPartsAnchor(ActionEvent event) {
        cmBoxOptions =  FXCollections.observableArrayList("Part Name");
        searchBy.setItems(cmBoxOptions);
        searchBy.getSelectionModel().selectFirst();
        tableToSearch = "STOCKPARTS";
        repairs.setVisible(false);
        usedParts.setVisible(false);
        stockParts.setVisible(true);
        loadAllParts();
        loadStockParts();
    }

    public void viewRepairsAnchor(ActionEvent event) {
        cmBoxOptions =  FXCollections.observableArrayList("First Name","Last Name","Vehicle Registration");
        searchBy.setItems(cmBoxOptions);
        searchBy.getSelectionModel().selectFirst();
        tableToSearch = "BOOKINGS";
        stockParts.setVisible(false);
        usedParts.setVisible(false);
        repairs.setVisible(true);
        loadAllParts();
        loadRStockParts();
        loadAllRepairs();
        loadRepairsTable();
    }
    
    public void viewUsedPartsAnchorForRepair(ActionEvent event) {
        selectedRepair = null;
        selectedRepair = repairTable.getSelectionModel().getSelectedItem();
        if(!viewRepairPartsValidation())
            return;
        currentRepairID = Integer.parseInt(selectedRepair.getRepairID());
        cmBoxOptions =  FXCollections.observableArrayList("Repair ID","Vehicle Registration");
        searchBy.setItems(cmBoxOptions);
        searchBy.getSelectionModel().selectFirst();
        tableToSearch = "USEDPARTS";
        loadAllUsed = false;
        repairs.setVisible(false);
        stockParts.setVisible(false);
        usedParts.setVisible(true);
        loadUsedPartsRepair(Integer.parseInt(selectedRepair.getRepairID()));
        loadUsedPartsTable();
    }
    
    //validation for stock parts
    
    public boolean addStockPartValidation(){
        boolean flag = true;
        String errors = "You have inputted the following information incorrectly:\n";
        if(partNameTextArea.getText().trim().isEmpty())
        {
            errors += "Part Name is required\n";
            flag = false;
        }
        if(partDescriptionTextArea.getText().trim().isEmpty())
        {
            errors += "Part Description is required\n";
            flag = false;
        }
        if(partCostTextArea.getText().trim().isEmpty())
        {
            errors += "Part Cost is required\n";
            flag = false;
        }
        else
        {
            try{
                Double.parseDouble(partCostTextArea.getText().trim());
            }catch(NumberFormatException e){
                errors += "Part Cost must be a decimal number\n";
                flag = false;
            }
        }
        if(partStockLevelTextArea.getText().trim().isEmpty())
        {
            errors += "Quantity is required\n";
            flag = false;
        }
        else
        {
            try{
                int n = Integer.parseInt(partStockLevelTextArea.getText().trim());
                if(n<1){
                    errors += "Quantity must be an integer greater than 0\n";
                    flag = false;
                }
            }catch(NumberFormatException e){
                errors += "Quantity must be an integer\n";
                flag = false;
            }
        }
        if(deliveryDatePicker.getValue()==null)
        {
            errors += "Delivery Date is required\n";
            flag = false;
        }
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }
    
    public boolean increaseStockQuantityValidation(){
        boolean flag = true;
        String errors = "You have inputted the following information incorrectly:\n";
        if(selectedPart==null)
        {
            errors += "A part from the table must be selected\n";
            flag = false;
        }
        if(quantityTextField.getText().trim().isEmpty())
        {
            errors += "Quantity is required\n";
            flag = false;
        }
        else
        {
            try{
                int n = Integer.parseInt(quantityTextField.getText().trim());
                if(n<1){
                    errors += "Quantity must be an integer greater than 0\n";
                    flag = false;
                }
            }catch(NumberFormatException e){
                errors += "Quantity must be an integer\n";
                flag = false;
            }
        }
        if(deliveryDatePickerQuantity.getValue()==null)
        {
            errors += "Delivery Date is required\n";
            flag = false;
        }
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }

    public boolean editAndDeleteValidation(){
        boolean flag = true;
        String errors = "";
        if(selectedPart==null)
        {
            errors += "Select a Part from the table\n";
            flag = false;
        }
        
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }
    
    //validation for used parts
    public boolean viewDetailsAndDeleteValidation(){
        boolean flag = true;
        String errors = "";
        if(selectedUsedPart==null)
        {
            errors += "Select a Part from the table\n";
            flag = false;
        }
        
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }
    
    //validation for repairs
    public boolean addPartToRepairValidation(){
        boolean flag = true;
        String errors = "";
        if(selectedRepair==null)
        {
            errors += "Select a Repair from the table\n";
            flag = false;
        }
        if(selectedPart==null)
        {
            errors += "Select a Part from the table\n";
            flag = false;
        }
        else if(partR.getStockPartCount(selectedPart.getId())<=0)
        {
            errors += "This part is currenly out of stock\n";
            flag = false;
        }
        if(usedPartInstallationDatePicker.getValue()==null)
        {
            errors += "Installation Date is required\n";
            flag = false;
        }
        else if(partR.countUsedParts(selectedRepair.getVehicleRegistration())>=10)
        {
            errors += "You cannot add another part to this vehicle\n";
            flag = false;
        }
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }
    
    public boolean viewRepairPartsValidation(){
        boolean flag = true;
        String errors = "";
        if(selectedRepair==null)
        {
            errors += "Select a Repair from the table\n";
            flag = false;
        }
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }
}
//AnchorPane pane = FXMLLoader.load(getClass().getResource("mainFXML.fxml"));
//rootPane.getChildren().setAll(pane);
