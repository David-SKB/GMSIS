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
import javafx.scene.control.ChoiceBox;
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
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;
//

/**
 * FXML Controller class
 *
 * @author jr308
 */
public class StockPartsController implements Initializable {

    private PartRegistry partR = PartRegistry.getInstance();
    private BookingRegistry bookingR = BookingRegistry.getInstance();
    private CustomerRegistry customerR = CustomerRegistry.getInstance();
    private VehicleRegistry vehicleR = VehicleRegistry.getInstance();

    private final ObservableList<Part> oPartList = FXCollections.observableArrayList();
    //Stock Parts
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
    @FXML
    private TableView<Part> usedTable;
    @FXML
    private TableColumn nameCol1, descriptionCol1, costCol1, //FXML TableColumn. Columns form the TableView element.
            vehicleCol1, customerCol1, firstNamecol1,
            lastNameCol1, repairIDCol1;
    @FXML
    private TextArea usedPartNameTextArea, usedPartDescriptionTextArea,
            usedPartCostTextArea, usedPartStockLevelTextArea,
            dateInstalledTextArea, warrantyEndsTextArea,
            vehicleRegistrationTextArea, customerNameTextArea,
            repairIDTextArea;

    //repairs gui
    private final ObservableList<RepairWrapper> oRepairList = FXCollections.observableArrayList();
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
        loadStockParts();
    }

    public void updateStockLevel(ActionEvent event) {
        System.out.println("in update stock level");
        Part selectedPart = stockTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedPart.getName());
        partR.updateStock(selectedPart.getId(), Integer.parseInt(quantityTextField.getText()));
        loadAllParts();
        partR.addDelivery(selectedPart.getId(), Integer.parseInt(quantityTextField.getText()), java.sql.Date.valueOf(deliveryDatePickerQuantity.getValue()));
    }

    public void addStockPart(ActionEvent event) {
        String name = partNameTextArea.getText();
        String description = partDescriptionTextArea.getText();
        BigDecimal cost = new BigDecimal((partCostTextArea.getText()));
        String quantity = partStockLevelTextArea.getText();
        partR.addPart(name, description, cost, Integer.parseInt(quantity));
        loadAllParts();
        int id = oPartList.get(oPartList.size()-1).getId();
        partR.addDelivery(id, Integer.parseInt(quantity), java.sql.Date.valueOf(deliveryDatePicker.getValue()));
    }
    
    public void deleteStockPart(ActionEvent event) {
        selectedPart = stockTable.getSelectionModel().getSelectedItem();
        boolean success;
        success = partR.deletePart(selectedPart.getId());
        loadAllParts();
    }

    public void searchParts(ActionEvent event) {
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
    public void loadUsedParts() {//ActionEvent event){

        System.out.println("test3");
        loadAllParts();
        stockTable.setEditable(true);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("description"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("cost"));
        stockCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("stocklevel"));
        stockTable.setItems(oPartList);

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
                new PropertyValueFactory<RepairWrapper, String>("firstName"));
        rLastNameCol.setCellValueFactory(
                new PropertyValueFactory<RepairWrapper, String>("lastName"));
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
                System.out.println("inside for");
                oRepairList.add(repairList.get(i));
            }
        }

    }

    public void addPartToRepair(ActionEvent event) {

        Part selectedPart = rStockTable.getSelectionModel().getSelectedItem();
        RepairWrapper selectedRepair = repairTable.getSelectionModel().getSelectedItem();
        partR.usePart(selectedRepair.getRepairID(), selectedRepair.getVehicleRegistration(), selectedRepair.getCustomerID(), selectedPart.getId(), new Date(2017, 01, 01).toString(), new Date(2018, 01, 01).toString(), new BigDecimal(selectedPart.getCost()));
        /*if (selectedPart.getStocklevel() < 2) {
            partR.deletePart(selectedPart.getId());
        } else {
            partR.updateStock(selectedPart.getId(), -1);
        }*/
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
                Part selectedPart = stockTable.getSelectionModel().getSelectedItem();
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

    
    //CHANGING ANCHOR METHODS
    public void viewUsedPartsAnchor(ActionEvent event) {
        repairs.setVisible(false);
        stockParts.setVisible(false);
        usedParts.setVisible(true);
    }

    public void viewStockPartsAnchor(ActionEvent event) {
        repairs.setVisible(false);
        usedParts.setVisible(false);
        stockParts.setVisible(true);
        loadAllParts();
        loadStockParts();
    }

    public void viewRepairsAnchor(ActionEvent event) {
        stockParts.setVisible(false);
        usedParts.setVisible(false);
        repairs.setVisible(true);
        loadAllParts();
        loadRStockParts();
        loadAllRepairs();
        loadRepairsTable();
    }

}
//AnchorPane pane = FXMLLoader.load(getClass().getResource("mainFXML.fxml"));
//rootPane.getChildren().setAll(pane);
