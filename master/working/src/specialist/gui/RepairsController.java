package specialist.gui;

import common.DBConnection;
import customers.logic.Customer;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import static javafx.util.Duration.seconds;
import specialist.logic.*;
import org.controlsfx.control.textfield.TextFields;

public class RepairsController /*extends Application*/ implements Initializable
{
    private FXMLLoader fXMLLoader = new FXMLLoader();
    Repairs repairs = Repairs.getInstance();
    private DBConnection DBC = DBConnection.getInstance();
    private static ErrorChecks EC = ErrorChecks.getInstance();
    LocalDate Selection = LocalDate.now();
    DatePicker Previous = new DatePicker();
    
    //Send Vehicle Pane
    @FXML private TitledPane SendVehicle;
    @FXML private TextField RegNoVehicle;
    @FXML private ComboBox SPCIDVehicle;
    @FXML private DatePicker ExpDelVehicle;
    @FXML private DatePicker ExpRetVehicle;
    @FXML private TextField CostVehicle;
    @FXML private Button SubmitVehicleButton;
    
    //Send Part Pane
    @FXML private TitledPane SendPart;
    @FXML private TextField RegNoPart;
    @FXML private ComboBox IDPart;
    @FXML private ComboBox SPCIDPart;
    @FXML private DatePicker ExpDelPart;
    @FXML private DatePicker ExpRetPart;
    @FXML private Button SubmitPartButton;
    @FXML private Button UpdatePartButton;
    
    //Edit Vehicle Pane
    @FXML private TitledPane EditVehicle;
    @FXML private TextField RegNoVehicle2;
    @FXML private ComboBox SPCIDVehicle2;
    @FXML private DatePicker ExpDelVehicle2;
    @FXML private DatePicker ExpRetVehicle2;
    @FXML private TextField CostVehicle2;
    @FXML private Button UpdateVehicleButton;
    
    //Edit Part Pane
    @FXML private TitledPane EditPart;
    @FXML private TextField RegNoPart2;
    @FXML private ComboBox IDPart2;
    @FXML private ComboBox SPCIDPart2;
    @FXML private DatePicker ExpDelPart2;
    @FXML private DatePicker ExpRetPart2;
    @FXML private Button SubmitPartButton2;
    @FXML private Button UpdatePartButton2;
    
    //Main Table Pane T1
    @FXML private TableView<SearchMain> MainTable;
    @FXML private TableColumn T1ID;
    @FXML private TableColumn T1Fname;
    @FXML private TableColumn T1Lname;
    @FXML private TableColumn T1Reg;
    @FXML private TableColumn T1SPC;
    @FXML private TableColumn T1ExpDel;
    @FXML private TableColumn T1ExpRet;
    @FXML private TableColumn T1Cost;
    @FXML private TableColumn T1Type;
    @FXML private Text T1Header;
    
    //Parts Table Pane T2
    @FXML private TableView<RepairParts> PartsTable;
    @FXML private TableColumn T2RegNo;
    @FXML private TableColumn T2Name;
    @FXML private TableColumn T2SPC;
    @FXML private TableColumn T2ExpDel;
    @FXML private TableColumn T2ExpRet;
    @FXML private TableColumn T2Cost;
    @FXML private Text T2Header;
    
    //SPC Table Pane T3
    @FXML private TableView<ListSPC> SPCListTable;
    @FXML private TableColumn T3ID;
    @FXML private TableColumn T3SPC;
    
    //Vehicle Table Pane T4
    @FXML private TableView<DisplayVehicle> VehicleTable;
    @FXML private TableColumn T4Reg;
    @FXML private TableColumn T4Make;
    @FXML private TableColumn T4Model;
    @FXML private TableColumn T4Fuel;
    @FXML private TableColumn T4Mileage;
    @FXML private TableColumn T4Color;
    
    //Oustanding Repairs Table Pane T5
    @FXML private TableView<OutstandingMain> OutstandingTable;
    @FXML private TableColumn T5RegID;
    @FXML private TableColumn T5ExpDel;
    @FXML private TableColumn T5ExpRet;
    @FXML private TableColumn T5Cost;
    @FXML private TableColumn T5Type;
    
    //Customer Details Pane
    @FXML private Pane CustomerPane;
    @FXML private TextField FnameCustomer;
    @FXML private TextField LnameCustomer;
    @FXML private TextArea AddressCustomer;
    @FXML private TextField PostcodeCustomer;
    @FXML private TextField PhoneCustomer;
    @FXML private TextField EmailCustomer;
    @FXML private TextField CTypeCustomer;
    
    //Misc
    @FXML private TextField RegSearch;
    @FXML private TextField FirstNameSearch;
    @FXML private TextField LastNameSearch;
    @FXML private Toggle NameRadioButton;
    @FXML private Toggle RegRadioButton;
    @FXML private ToggleGroup SearchVehicleToggle;
    @FXML private ToggleGroup SendItemToggle;
    @FXML private Text T1SearchError;
    @FXML private Text AddError;
    @FXML private Text EditError;
    @FXML private Text AddErrorPart;
    @FXML private Text EditErrorPart;
    @FXML private Button RepairEditButton;
    @FXML private Button RepairDeleteButton;
    @FXML private Button SPCVehicleButton;
    @FXML private Button SPCOutstandingButton;
    @FXML private Text T1SearchErrorText;
    @FXML private Button ViewPartsButton;
    
    //
    //todo: make vehicle reg search get firstname and lastname so table collums dont keep shitfing, also do refresh on tab click, also green sucess messages
    //******************************************************
    @FXML private void SubmitVehicle() throws SQLException 
    {
        if (ValidateSubmitVehicle())
        {
            boolean added  = repairs.addVehicle(RegNoVehicle.getText(), repairs.getSPCID(SPCIDVehicle.getValue().toString()), EC.toDate(ExpDelVehicle), EC.toDate(ExpRetVehicle), Double.parseDouble(CostVehicle.getText()));
            //System.out.println("added: " + added);
            ClearAddVehicle();
        }
    }
    
    @FXML private void SubmitPart() throws SQLException 
    {
        if (ValidateSubmitPart())
        {
            boolean added  = repairs.addPart(RegNoPart.getText(), repairs.getPartID(IDPart.getValue().toString()), repairs.getSPCID(SPCIDPart.getValue().toString()), EC.toDate(ExpDelPart), EC.toDate(ExpRetPart));
            //System.out.println("added: " + added);
            ClearAddPart();
        }
    }
    
    @FXML private void EditVehicle() throws SQLException 
    {
        if (ValidateEditVehicle())
        {
            //get repair id of row to edit
            int RepairID = MainTable.getSelectionModel().getSelectedItem().getT1IDX();
            boolean added  = repairs.editVehicle(RegNoVehicle2.getText(), repairs.getSPCID(SPCIDVehicle2.getValue().toString()), EC.toDate(ExpDelVehicle2), EC.toDate(ExpRetVehicle2), Double.parseDouble(CostVehicle2.getText()), RepairID);
            //refresh table
            //System.out.println("Updated: " + added);
            RepairSearchHandler();
            ClearEditVehicle();
            EditVehicle.setDisable(true);
            EditError.setVisible(false);
        }
    }
    
    @FXML private void EditPart() throws SQLException 
    {
        if (ValidateEditPart())
        {
            //get repair id of row to edit
            int RepairID = PartsTable.getSelectionModel().getSelectedItem().getT2IDX();
            boolean added  = repairs.editPart(RegNoPart2.getText(), repairs.getPartID(IDPart2.getValue().toString()), repairs.getSPCID(SPCIDPart2.getValue().toString()), EC.toDate(ExpDelPart2), EC.toDate(ExpRetPart2), RepairID);
            //refresh table
            //System.out.println("Updated: " + added);
            HandleViewParts();
            ClearEditPart();
            EditVehicle.setDisable(true);
            EditError.setVisible(false);
        }
    }
    
    @FXML private boolean ValidateSubmitVehicle()
    {
        ClearAddVStyles();
        boolean valid = true;
        if (EC.isPlate(RegNoVehicle.getText()) == false || RegNoVehicle.getText() == null)
        {
            valid = false;
            RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (SPCIDVehicle == null)
        {
            valid = false;
            SPCIDVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpDelVehicle.getValue() == null)
        {
            valid = false;
            ExpDelVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpRetVehicle.getValue() == null)
        {
            valid = false;
            ExpRetVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        try 
        {
            Double.parseDouble(CostVehicle.getText());
        }
        catch (NumberFormatException ex)
        {
            CostVehicle.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        //***********************************
        if (valid == false)
        {
            AddErrMsg("Invalid Input");
            return false;
        }
        ClearAddVStyles();
        return true;
    }
    
    @FXML private boolean ValidateSubmitPart()
    {
        ClearAddPStyles();
        boolean valid = true;
        if (EC.isPlate(RegNoPart.getText()) == false || RegNoPart.getText() == null)
        {
            valid = false;
            RegNoPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (SPCIDPart == null)
        {
            valid = false;
            SPCIDPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpDelPart.getValue() == null)
        {
            valid = false;
            ExpDelPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpRetPart.getValue() == null)
        {
            valid = false;
            ExpRetPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        //***********************************
        if (valid == false)
        {
            AddErrMsgPart("Invalid Input");
            return false;
        }
        ClearAddPStyles();
        return true;
    }
    
    @FXML private boolean ValidateEditVehicle()
    {
        ClearAddUVStyles();
        boolean valid = true;
        if (EC.isPlate(RegNoVehicle2.getText()) == false || RegNoVehicle2.getText() == null)
        {
            valid = false;
            RegNoVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        else
        {
            if (RegNoVehicle2.getText().length() != 7)
            {
                valid = false;
                RegNoVehicle2.setStyle("-fx-border-color: #ff1e1e;");
            }
        }
        if (SPCIDVehicle2 == null)
        {
            valid = false;
            SPCIDVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpDelVehicle2.getValue() == null)
        {
            valid = false;
            ExpDelVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpRetVehicle2.getValue() == null)
        {
            valid = false;
            ExpRetVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        try 
        {
            Double.parseDouble(CostVehicle2.getText());
        }
        catch (NumberFormatException ex)
        {
            CostVehicle2.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        //***********************************
        if (valid == false)
        {
            EditError.setDisable(false);
            EditErrMsg("Invalid Input");
            return false;
        }
        ClearAddUVStyles();
        EditError.setDisable(true);
        return true;
    }
    
    @FXML private boolean ValidateEditPart()
    {
        ClearAddUPStyles();
        boolean valid = true;
        if (EC.isPlate(RegNoPart2.getText()) == false || RegNoPart2.getText() == null)
        {
            valid = false;
            RegNoPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (SPCIDPart2 == null)
        {
            valid = false;
            SPCIDPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpDelPart2.getValue() == null)
        {
            valid = false;
            ExpDelPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        
        if (ExpRetPart2.getValue() == null)
        {
            valid = false;
            ExpRetPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        //***********************************
        if (valid == false)
        {
            EditErrMsgPart("Invalid Input");
            return false;
        }
        ClearAddUPStyles();
        return true;
    }
    
    @FXML private void ClearAddVStyles()
    {
        RegNoVehicle.setStyle(null);
        SPCIDVehicle.setStyle(null);
        ExpDelVehicle.setStyle(null);
        ExpRetVehicle.setStyle(null);
        CostVehicle.setStyle(null);
    }
    
    @FXML private void ClearAddPStyles()
    {
        RegNoPart.setStyle(null);
        IDPart.setStyle(null);
        SPCIDPart.setStyle(null);
        ExpDelPart.setStyle(null);
        ExpRetPart.setStyle(null);
    }

    @FXML private void ClearAddUVStyles()
    {
        RegNoVehicle2.setStyle(null);
        SPCIDVehicle2.setStyle(null);
        ExpDelVehicle2.setStyle(null);
        ExpRetVehicle2.setStyle(null);
        CostVehicle2.setStyle(null);
    }
    
    @FXML private void ClearAddUPStyles()
    {
        RegNoPart2.setStyle(null);
        IDPart2.setStyle(null);
        SPCIDPart2.setStyle(null);
        ExpDelPart2.setStyle(null);
        ExpRetPart2.setStyle(null);
    }
    
    @FXML private void ToggleSendPart()
    {
        SendVehicle.setVisible(false);
        SendPart.setVisible(true);
    }
    
    private void ClearAddVehicle()
    {
        RegNoVehicle.setText(null);
        SPCIDVehicle.setValue(null);
        ExpDelVehicle.setValue(null);
        ExpRetVehicle.setValue(null);
        CostVehicle.setText(null);
    }
    
    @FXML private void ClearAddPart()
    {
        RegNoPart.setText(null);
        IDPart.setValue(null);
        SPCIDPart.setValue(null);
        ExpDelPart.setValue(null);
        ExpRetPart.setValue(null);
    }
    
    private void ClearEditVehicle()
    {
        RegNoVehicle2.setText(null);
        SPCIDVehicle2.setValue(null);
        ExpDelVehicle2.setValue(null);
        ExpRetVehicle2.setValue(null);
        CostVehicle2.setText(null);
    }
    
    @FXML private void ClearEditPart()
    {
        RegNoPart2.setText(null);
        IDPart2.setValue(null);
        SPCIDPart2.setValue(null);
        ExpDelPart2.setValue(null);
        ExpRetPart2.setValue(null);
    }
    
    @FXML private void ToggleSendVehicle()
    {
        SendVehicle.setVisible(true);
        SendPart.setVisible(false);
    }
    
    @FXML private void DisplaySPC() throws SQLException
    {
        T3ID.setCellValueFactory(
                new PropertyValueFactory<Repairs, Integer>("T3IDX"));
        T3SPC.setCellValueFactory(
                new PropertyValueFactory<Repairs, String>("T3SPCX"));
        
        ObservableList<ListSPC> SPCList = repairs.getSPCList();
        SPCListTable.setItems(SPCList);
    }
    
    @FXML private void RepairSearchHandler() throws SQLException
    {
        RadioButton val = (RadioButton)SearchVehicleToggle.getSelectedToggle();
        String criteria = val.getText();
        if (criteria.equals("Registration"))
            {
                //search by reg
                SearchByReg();
            }
            else
            {
                //search by name
                SearchByName();
            }
        PartsTable.setVisible(false);
        T2Header.setVisible(false);
        MainTable.setVisible(true);
        T1Header.setVisible(true);
        EditVehicle.setDisable(true);
        EditPart.setDisable(true);
        ClearAddUPStyles();
        ClearAddUVStyles();
        ClearEditPart();
        ClearEditVehicle();
    }
    
    @FXML private void SearchByReg() throws SQLException
    {
        T1Fname.setVisible(false);
        T1Lname.setVisible(false);
        
        T1ID.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Integer>("T1IDX"));
        T1Reg.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1REGX"));
        T1SPC.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1SPCX"));
        T1ExpDel.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1EXPDELX"));
        T1ExpRet.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1EXPRETX"));
        T1Cost.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Double>("T1COSTX"));
            
        if (EC.isPlate(RegSearch.getText()) == false)
        {
            RepairErrMsg("Invalid Plate");
        }
        else
        {
            ObservableList<SearchMain> resultList = repairs.searchReg(RegSearch.getText());
            if(resultList.isEmpty())
            {
                RepairErrMsg("No Results Found");
            }
            MainTable.setItems(resultList);
        }
    }
    
    @FXML private void SearchByName() throws SQLException
    {
        T1Fname.setVisible(true);
        T1Lname.setVisible(true);
        
        T1ID.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Integer>("T1IDX"));
        T1Fname.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1FNAMEX"));
        T1Lname.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1LNAMEX"));
        T1Reg.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1REGX"));
        T1SPC.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1SPCX"));
        T1ExpDel.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1EXPDELX"));
        T1ExpRet.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1EXPRETX"));
        T1Cost.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Double>("T1COSTX"));
            
        
        if (EC.isAlphanumeric(FirstNameSearch.getText()) == false || EC.isAlphanumeric(LastNameSearch.getText()) == false)
        {
            RepairErrMsg("Invalid Name");
            
        }
        else
        {
            ObservableList<SearchMain> resultList = repairs.searchName(FirstNameSearch.getText(), LastNameSearch.getText());

            if(resultList.isEmpty())
            {
                RepairErrMsg("No Results Found");
            }
            else
            {
                //T1SearchError.setVisible(false);
            }
            MainTable.setItems(resultList);
        }
    }
    
    
    
    @FXML private void SearchBySPC(int SPCID) throws SQLException
    {
        T1Fname.setVisible(true);
        T1Lname.setVisible(true);
        
        T1ID.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Integer>("T1IDX"));
        T1Fname.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1FNAMEX"));
        T1Lname.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1LNAMEX"));
        T1Reg.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1REGX"));
        T1SPC.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1SPCX"));
        T1ExpDel.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1EXPDELX"));
        T1ExpRet.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T1EXPRETX"));
        T1Cost.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Double>("T1COSTX"));
            
        ObservableList<SearchMain> resultList = repairs.searchSPC(SPCID);
        MainTable.setItems(resultList);
    }
    
    @FXML private void getCustomerByReg(String RegNo) throws SQLException
    {
        Customer CustomerDetails = repairs.searchCustomerWithReg(RegNo);
        if (CustomerDetails == null)
        {
            RepairErrMsg("Customer Not Found");
        }
        else
        {
            FnameCustomer.setText(CustomerDetails.getFirstname());
            LnameCustomer.setText(CustomerDetails.getSurname());
            AddressCustomer.setText(CustomerDetails.getAddress());
            PostcodeCustomer.setText(CustomerDetails.getPostCode());
            PhoneCustomer.setText((CustomerDetails.getPhone()));
            EmailCustomer.setText((CustomerDetails.getEmail()));
            CTypeCustomer.setText((CustomerDetails.getCustomerType()));
        }
    }
    
    @FXML private void VehicleDetails(String regNo) throws SQLException
    {
        T4Reg.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Integer>("T4REGX"));
        T4Make.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T4MAKEX"));
        T4Model.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T4MODELX"));
        T4Fuel.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T4FUELX"));
        T4Mileage.setCellValueFactory(
                new PropertyValueFactory<SearchReg, String>("T4MILEAGEX"));
        T4Color.setCellValueFactory(
                new PropertyValueFactory<SearchReg, Double>("T4COLORX"));
        
        ObservableList<DisplayVehicle> vehicleInfo = repairs.getVehicle(regNo);
        VehicleTable.setItems(vehicleInfo);
    }
    
    @FXML private void LoadOutstanding() throws SQLException
    {
        T5RegID.setCellValueFactory(
                new PropertyValueFactory<OutstandingMain, String>("T5REGNOX"));
        T5ExpDel.setCellValueFactory(
                new PropertyValueFactory<OutstandingMain, String>("T5EXPDELX"));
        T5ExpRet.setCellValueFactory(
                new PropertyValueFactory<OutstandingMain, String>("T5EXPRETX"));
        T5Cost.setCellValueFactory(
                new PropertyValueFactory<OutstandingMain, Integer>("T5COSTX"));
        T5Type.setCellValueFactory(
                new PropertyValueFactory<OutstandingMain, String>("T5TYPEX"));
        if (SPCListTable.getSelectionModel().getSelectedItem() != null)
        {
            ObservableList<OutstandingMain> VehicleList = repairs.getOutstanding(SPCListTable.getSelectionModel().getSelectedItem().getT3IDX());
            OutstandingTable.setItems(VehicleList);
        }
        else
        {
            //make error message saying no row selected
        }
    }
    
    @FXML private void VehicleRowClick() 
    {
        boolean vehicle  = true;
        boolean customer = true;
        String regNo = "";
        if (MainTable.getSelectionModel().getSelectedItem() != null)
        {
            regNo = MainTable.getSelectionModel().getSelectedItem().getT1REGX();
        }
        try 
        {
            VehicleDetails(regNo);
        } 
        catch (SQLException ex) 
        {
            vehicle = false;
        }
        try 
        {
            getCustomerByReg(regNo);
        } 
        catch (SQLException ex) 
        {
            customer = false;
        }
        
        if (customer == false & vehicle == true)
        {
            RepairErrMsg("Customer Not Found");
        }
        else if (customer == true & vehicle == false)
        {
            RepairErrMsg("Vehicle Not Found");
        }
        
        else if (customer == false & vehicle == false)
        {
            RepairErrMsg("Vehicle/Customer Not Found");
        }
        RepairEditButton.setDisable(false);
        RepairDeleteButton.setDisable(false);
        ViewPartsButton.setDisable(false);
    }
    
    @FXML private void SPCRowClick() 
    {/*
        int SPCID = SPCListTable.getSelectionModel().getSelectedItem().getT3IDX();
        System.out.println(SPCID);
        
        try 
        {
            SearchBySPC(SPCID);
        } 
        catch (SQLException ex) 
        {
            System.out.println("Select something fool");
        }*/
        SPCVehicleButton.setDisable(false);
        SPCOutstandingButton.setDisable(false);
    }
    
    @FXML private void HandleDelete() throws SQLException 
    {
        if (MainTable.isVisible())//If Repair Vehicles table is active
        {
            if (MainTable.getSelectionModel().getSelectedItem() == null)
            {
                RepairErrMsg("Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                return;
            }
            int RepairID = PartsTable.getSelectionModel().getSelectedItem().getT2IDX();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Vehicle Repair ID: " + RepairID);
            alert.setHeaderText("");
            alert.setContentText("Are you sure you wish to proceed?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean success  = repairs.deleteVehicleRepair(RepairID);
                //System.out.println("Deleted: " + success);
                RepairSearchHandler();
                ClearEditVehicle();
                ClearAddUVStyles();
                EditVehicle.setDisable(true);
            } else 
            {
                //Delete Cancelled
            }
        }
        else
        {
            if (PartsTable.getSelectionModel().getSelectedItem() == null)
            {
                RepairErrMsg("Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                return;
            }
            int RepairID = PartsTable.getSelectionModel().getSelectedItem().getT2IDX();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Part Repair ID: " + RepairID);
            alert.setHeaderText("");
            alert.setContentText("Are you sure you wish to proceed?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean success  = repairs.deletePartRepair(RepairID);
                //System.out.println("Deleted: " + success);
                HandleViewParts();
                ClearEditPart();
                ClearAddUPStyles();
                EditPart.setDisable(true);
            } else 
            {
                //Delete Cancelled
            }
        }
    }
    
    @FXML private void HandleEdit() throws SQLException 
    {
        if (MainTable.isVisible())//If Repair Vehicles table is active
        {
            if (MainTable.getSelectionModel().getSelectedItem() == null)
            {
                RepairErrMsg("Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                return;
            }
            LoadComboListSPC();
            //Set Update textfields with data
            EditVehicle.setVisible(true);
            EditPart.setVisible(false);
            RegNoVehicle2.setText(MainTable.getSelectionModel().getSelectedItem().getT1REGX());
            SPCIDVehicle2.setValue(MainTable.getSelectionModel().getSelectedItem().getT1SPCX());
            //convert dates
            LocalDate expDel = EC.StringtoLDate(MainTable.getSelectionModel().getSelectedItem().getT1EXPDELX());
            LocalDate expRet = EC.StringtoLDate(MainTable.getSelectionModel().getSelectedItem().getT1EXPRETX());
            Previous.setValue(expDel);//Needed for error checking
            ExpRetVehicle2.setValue(expRet);
            ExpDelVehicle2.setValue(expDel);
            //convert cost
            String cost = Double.toString(MainTable.getSelectionModel().getSelectedItem().getT1COSTX());
            CostVehicle2.setText(cost);
            EditVehicle.setDisable(false);
        }
        else
        {
            if (PartsTable.getSelectionModel().getSelectedItem() == null)
            {
                RepairErrMsg("Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                return;
            }
            LoadComboListSPC();
            LoadComboListPart();
            //Set Update textfields with data
            EditVehicle.setVisible(false);
            EditPart.setVisible(true);
            RegNoPart2.setText(PartsTable.getSelectionModel().getSelectedItem().getT2REGNOX());
            IDPart2.setValue(PartsTable.getSelectionModel().getSelectedItem().getT2NAMEX());
            SPCIDPart2.setValue(PartsTable.getSelectionModel().getSelectedItem().getT2SPCX());
            //convert dates
            LocalDate expDel = EC.StringtoLDate(PartsTable.getSelectionModel().getSelectedItem().getT2EXPDELX());
            LocalDate expRet = EC.StringtoLDate(PartsTable.getSelectionModel().getSelectedItem().getT2EXPRETX());
            Previous.setValue(expDel);//Needed for error checking
            ExpRetPart2.setValue(expRet);
            ExpDelPart2.setValue(expDel);
            //convert cost
            EditPart.setDisable(false);
        }
        
    }
    
    @FXML private void HandleViewParts() throws SQLException 
    {
        //Hide Vehicle Table
        MainTable.setVisible(false);
        T1Header.setVisible(false);
        ViewPartsButton.setDisable(true);
        //Show Parts Table
        PartsTable.setVisible(true);
        T2Header.setVisible(true);
        //do stuff
        
        T2RegNo.setCellValueFactory(
                new PropertyValueFactory<RepairParts, String>("T2REGNOX"));
        T2Name.setCellValueFactory(
                new PropertyValueFactory<RepairParts, String>("T2NAMEX"));
        T2SPC.setCellValueFactory(
                new PropertyValueFactory<RepairParts, String>("T2SPCX"));
        T2ExpDel.setCellValueFactory(
                new PropertyValueFactory<RepairParts, String>("T2EXPDELX"));
        T2ExpRet.setCellValueFactory(
                new PropertyValueFactory<RepairParts, String>("T2EXPRETX"));
        T2Cost.setCellValueFactory(
                new PropertyValueFactory<RepairParts, Double>("T2COSTX"));
        
        String RegNo = MainTable.getSelectionModel().getSelectedItem().getT1REGX();
        ObservableList<RepairParts> PartList = repairs.getPartRepairs(RegNo);
        PartsTable.setItems(PartList);
    }
    
    @FXML private void HandleCustomer() throws SQLException 
    {
        
        if (MainTable.getSelectionModel().getSelectedItem() == null)
        {
            RepairErrMsg("Please Select a Row");
            RepairEditButton.setDisable(true);
            RepairDeleteButton.setDisable(true);
            return;
        }
        getCustomerByReg(MainTable.getSelectionModel().getSelectedItem().getT1REGX());
    }
    
    @FXML private void ListSPCVehicles()
    {
        int SPCID = SPCListTable.getSelectionModel().getSelectedItem().getT3IDX();
        try 
        {
            SearchBySPC(SPCID);
        } 
        catch (SQLException ex) 
        {
            System.out.println("spc error saying to select a row");
        }
        MainTable.setVisible(true);
        PartsTable.setVisible(false);
        SPCVehicleButton.setDisable(true);
        RepairEditButton.setDisable(true);
        RepairDeleteButton.setDisable(true);
    }
    
    @FXML private void TestFunction() throws SQLException
    {
        LoadOutstanding();
    }
    
    /*public static void main (String args[]) throws Exception
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception 
    {
        Parent root = FXMLLoader.load( getClass().getResource("repairs.fxml") );
        Scene scene = new Scene(root,1168,748);
        window.setScene(scene);
        window.show();
    }*/
    
    @FXML private void RepairErrMsg(String msg)
    {
        T1SearchError.setText(msg);
        Timer timer = new Timer();
            timer.schedule( 
            new java.util.TimerTask() 
            {
                public void run() 
                {
                    T1SearchError.setText("");
                    timer.cancel();// Terminate the thread
                }
            }, 2000
            );
    }
    
    @FXML private void AddErrMsg(String msg)
    {
        AddError.setText(msg);
        Timer timer = new Timer();
            timer.schedule( 
            new java.util.TimerTask() 
            {
                public void run() 
                {
                    AddError.setText("");
                    timer.cancel();// Terminate the thread
                }
            }, 2000
            );
    }
    
    @FXML private void AddErrMsgPart(String msg)
    {
        AddErrorPart.setText(msg);
        Timer timer = new Timer();
            timer.schedule( 
            new java.util.TimerTask() 
            {
                public void run() 
                {
                    AddErrorPart.setText("");
                    timer.cancel();// Terminate the thread
                }
            }, 2000
            );
    }
    
    @FXML private void EditErrMsg(String msg)
    {
        EditError.setText(msg);
        Timer timer = new Timer();
            timer.schedule( 
            new java.util.TimerTask() 
            {
                public void run() 
                {
                    EditError.setText("");
                    timer.cancel();// Terminate the thread
                }
            }, 2000
            );
    }
    
    @FXML private void EditErrMsgPart(String msg)
    {
        EditErrorPart.setText(msg);
        Timer timer = new Timer();
            timer.schedule( 
            new java.util.TimerTask() 
            {
                public void run() 
                {
                    EditErrorPart.setText("");
                    timer.cancel();// Terminate the thread
                }
            }, 2000
            );
    }
    
    @FXML private void LoadComboListSPC() throws SQLException
    {
        ObservableList<String> SPCNameList = repairs.getSPCListCombo();
        SPCIDVehicle.setItems(SPCNameList);
        SPCIDVehicle2.setItems(SPCNameList);
        SPCIDPart.setItems(SPCNameList);
        SPCIDPart2.setItems(SPCNameList);
    }
    
    @FXML private void LoadComboListPart() throws SQLException
    {
        ObservableList<String> PartIDList = repairs.getPartListCombo();
        IDPart.setItems(PartIDList);
        IDPart2.setItems(PartIDList);
    }
    
    @FXML private void DPUpdateAV()
    {
        if (ExpDelVehicle.getValue() != null)
        {
            Selection = EC.StringtoLDate(EC.toString(ExpDelVehicle));
            ExpRetVehicle.setDayCellFactory(DCFRETURN);
            if (ExpRetVehicle.getValue() != null)
            {
                if (EC.StringtoLDate(EC.toString(ExpRetVehicle)).isBefore(Selection))
                ExpRetVehicle.setValue(null);
            }
        }
        
    }
    
    @FXML private void DPUpdateAP()
    {
        if (ExpDelPart.getValue() != null)
        {
            Selection = EC.StringtoLDate(EC.toString(ExpDelPart));
            ExpRetPart.setDayCellFactory(DCFRETURN);
            if (ExpRetPart.getValue() != null)
            {
                if (EC.StringtoLDate(EC.toString(ExpRetPart)).isBefore(Selection))
                ExpRetPart.setValue(null);
            }
        }
    }
    
    @FXML private void DPUpdateUV()
    {
        if (ExpDelVehicle2.getValue() != null)
        {
            Selection = EC.StringtoLDate(EC.toString(ExpDelVehicle2));
            if (Selection.isAfter(ExpRetVehicle2.getValue()))
            {
                ExpDelVehicle2.setValue(Previous.getValue());  
            }
            ExpRetVehicle2.setDayCellFactory(DCFRETURN);
        }
    }

    @FXML private void DPUpdateUP()
    {
        if (ExpDelPart2.getValue() != null)
        {
            Selection = EC.StringtoLDate(EC.toString(ExpDelPart2));
            ExpRetPart2.setDayCellFactory(DCFRETURN);
            if (ExpRetPart2.getValue() != null)
            {
                if (EC.StringtoLDate(EC.toString(ExpRetPart2)).isBefore(Selection))
                ExpRetPart2.setValue(null);
            }
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        try 
        {
            LoadComboListSPC();
            LoadComboListPart();
            //DisplaySPC();//needs tab refresh fucntion first
            ExpDelVehicle.setDayCellFactory(DCF);
            ExpDelPart.setDayCellFactory(DCF);
            ExpRetVehicle.setDayCellFactory(DCF);
            ExpRetPart.setDayCellFactory(DCF);
        } catch (SQLException ex) 
        {
            Logger.getLogger(RepairsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        EC.SetNumberRestriction(CostVehicle);
        EC.SetNumberRestriction(CostVehicle2);
        //Autocomplete stuff
        ArrayList<String> VehicleList;
        try 
        {
            VehicleList = repairs.getAllVehicles();
        } 
        catch (SQLException ex) 
        {
            VehicleList = null;
        }
        if (VehicleList !=null)
        {
            TextFields.bindAutoCompletion(RegNoVehicle, VehicleList);
            TextFields.bindAutoCompletion(RegNoVehicle2, VehicleList);
            TextFields.bindAutoCompletion(RegNoPart, VehicleList);
        }
    }
    
    private Callback< DatePicker, DateCell > DCF = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem( LocalDate item , boolean empty )
        {
            super.updateItem( item , empty );
            
            // disables all past dates + colours them red
            if (item.isBefore(LocalDate.now()))
            {
                this.setDisable (true)                        ;
                this.setStyle(" -fx-background-color: #FFD3D3; ") ;
            }
        }
    };
    
    private Callback< DatePicker, DateCell > DCFRETURN = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem(LocalDate item , boolean empty)
        {
            // Must call super
            super.updateItem(item, empty);
            
            // disable all past dates + colours them red
            if (item.isBefore(Selection))
            {
                this.setDisable (true)                        ;
                this.setStyle(" -fx-background-color: #FFD3D3; ") ;
            }
        } 
    };  
}
//old style sheet for interface
/*
.tab-pane .tab-header-area .tab-header-background {
    -fx-opacity: 0;
}

.tab-pane
{
    -fx-tab-min-width:90px;
}

.tab{
    -fx-background-insets: 0 1 0 1,0,0;
}
.tab-pane .tab
{
    -fx-background-color: #ceae82;

}

.tab-pane .tab:selected
{
    -fx-background-color: #342511;

}

.tab .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #ffffff;
    -fx-font-size: 12px;
    -fx-font-weight: bold;
}

.tab:selected .tab-label {
    -fx-alignment: CENTER;
    -fx-text-fill: #ffffff;
    -fx-font-style: oblique;
}

.tab-pane:top *.tab-header-area {
    -fx-background-insets: 0, 0 0 1 0;
    -fx-background-color: #614f20;
}
*/