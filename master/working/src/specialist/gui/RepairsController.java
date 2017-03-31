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
import javafx.scene.Node;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import static javafx.util.Duration.seconds;
import specialist.logic.*;
import org.controlsfx.control.textfield.TextFields;
import static javafx.application.Application.launch;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;

public class RepairsController /*extends Application*/ implements Initializable
{
    private FXMLLoader fXMLLoader = new FXMLLoader();
    Repairs repairs = Repairs.getInstance();
    private DBConnection DBC = DBConnection.getInstance();
    private static ErrorChecks EC = ErrorChecks.getInstance();
    LocalDate Selection = LocalDate.now();
    DatePicker Previous = new DatePicker();
    int CurrentSPC = 0;
    int PreviousSPC = -1;
    boolean SearchedBySPC = false;
    boolean JustDeleted = false;
    boolean PartsBySPC = false;
    static boolean tabSwitched = false;
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
    @FXML private TableColumn T5SPCID;
    
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
    @FXML private Text SPCError;
    @FXML private Text OutstandingError;
    @FXML private Button RepairEditButton;
    @FXML private Button RepairDeleteButton;
    @FXML private Button ViewPartsButton;
    @FXML private Button ViewPartsSPCButton;
    @FXML private Button ToggleOutstandingButton;
    @FXML private Text OutstandingTitle;
    
    /***************************************
     * UPDATE SPC ANCHOR PANE METHOD 
     * Modify it as you wish. 
     * I have imported: (1) import javafx.scene.Node; (2) import javafx.scene.layout.AnchorPane;
     * Inside your fxml I named fx:id of Pane "MainPane"
     * You need to add the methods that update the components
     *************************************/
    public void updateAnchorPane(AnchorPane AP) throws SQLException
    {
        ObservableList<Node> OL = AP.getChildren();
        Pane mainPane = null;
        for(Node n : OL)
        {
            if(n instanceof Pane && (n.getId().equalsIgnoreCase("MainPane")))
            {
                mainPane = (Pane)n;
            }
        }
        if(mainPane != null)
        {
            OL = mainPane.getChildren();            
            TableView<OutstandingMain> outstandingT = null;
            TableView<ListSPC> spcListTable = null;
            Text SPCErrorTemp = null;
            Text OutstandingErrorTemp = null;
            Button ViewPartsSPCButtonTemp = null;
            Text OutstandingTitleTemp = null;
            Button ToggleOutstandingButtonTemp = null;
            
            for(Node n : OL)//Loop to find all the children that will be updated
            {       
                if(n.getId() == null)
                {
                    //Avoid
                }
                else if(n instanceof TableView && (n.getId().equalsIgnoreCase("OutstandingTable")))
                {
                    outstandingT = (TableView<OutstandingMain>)n;
                }
                else if(n instanceof TableView && (n.getId().equalsIgnoreCase("SPCListTable")))
                {
                    spcListTable = (TableView<ListSPC>)n;
                }
                else if(n instanceof Text && (n.getId().equalsIgnoreCase("SPCError")))
                {
                    SPCErrorTemp = (Text)n;
                }
                else if(n instanceof Text && (n.getId().equalsIgnoreCase("OutstandingError")))
                {
                    OutstandingErrorTemp = (Text)n;
                }
                else if(n instanceof Button && (n.getId().equalsIgnoreCase("ViewPartsSPCButton")))
                {
                    ViewPartsSPCButtonTemp = (Button)n;
                }
                else if(n instanceof Text && (n.getId().equalsIgnoreCase("OutstandingTitle")))
                {
                    OutstandingTitleTemp = (Text)n;
                }
                else if(n instanceof Button && (n.getId().equalsIgnoreCase("ToggleOutstandingButton")))
                {
                    ToggleOutstandingButtonTemp = (Button)n;
                }
            }
            if(outstandingT != null && spcListTable != null && SPCErrorTemp !=null & OutstandingErrorTemp !=null)
            {
                //CALL METHODS THAT UPDATE COMPONENT
                ObservableList<OutstandingMain> VehicleList = repairs.getOutstanding();
                outstandingT.setItems(VehicleList);
                ObservableList<ListSPC> SPCList = repairs.getSPCList();
                if (SPCList.isEmpty())
                {
                    spcListTable.setItems(null);
                    SPCErrorTemp.setFill(Color.RED);
                    SPCErrorTemp.setText("No Centres Found");
                    ViewPartsSPCButtonTemp.setVisible(false);
                }
                else
                {
                    
                    SPCErrorTemp.setText("");
                    spcListTable.setItems(SPCList);
                    ViewPartsSPCButtonTemp.setVisible(true);
                }
                
                OutstandingTitleTemp.setText("Outstanding Repairs");
                ToggleOutstandingButtonTemp.setText("Show Returned");
                if (outstandingT.getItems().isEmpty())
                {
                    OutstandingErrorTemp.setText("No Repairs Found");
                }
                else
                {
                    OutstandingErrorTemp.setText("");
                }
                tabSwitched = true;
            }
        }
    }
    //
    //todo: nada
    //******************************************************
    @FXML private void SubmitVehicle() throws SQLException 
    {
        if (ValidateSubmitVehicle())
        {
            if (repairs.VehicleScheduled(RegNoVehicle.getText()))
            {
                EC.TimedMsgRED(AddError, "Vehicle Already Scheduled");
            }
            else if (repairs.VehicleSent(RegNoVehicle.getText()))
            {
                EC.TimedMsgRED(AddError, "Vehicle Currently in Repair");
            }
            else
            {
                boolean added  = repairs.addVehicle(RegNoVehicle.getText(), repairs.getSPCID(SPCIDVehicle.getValue().toString()), EC.toDate(ExpDelVehicle), EC.toDate(ExpRetVehicle), Double.parseDouble(CostVehicle.getText()));
                //System.out.println("added: " + added);
                ClearAddVehicle();
                if (added)
                {
                    EC.TimedMsgGREEN(AddError, "Vehicle Sent");
                    LoadOutstanding();
                }
            }
        }
    }
    
    @FXML private void SubmitPart() throws SQLException 
    {
        if (ValidateSubmitPart())
        {
            int PartID = repairs.getPartID(IDPart.getValue().toString());
            boolean added  = repairs.addPart(RegNoPart.getText(), PartID, repairs.getSPCID(SPCIDPart.getValue().toString()), EC.toDate(ExpDelPart), EC.toDate(ExpRetPart));
            //System.out.println("added: " + added);
            ClearAddPart();
            if (added)
            {
                EC.TimedMsgGREEN(AddErrorPart, "Part Sent");
                repairs.removeStock(PartID);//removes one stock for the part sent
                LoadOutstanding();
            }
        }
    }
    
    @FXML private void EditVehicle() throws SQLException 
    {
        if (ValidateEditVehicle())
        {
            //get repair ID of row to edit
            int RepairID = MainTable.getSelectionModel().getSelectedItem().getT1IDX();
            boolean added  = repairs.editVehicle(RegNoVehicle2.getText(), repairs.getSPCID(SPCIDVehicle2.getValue().toString()), EC.toDate(ExpDelVehicle2), EC.toDate(ExpRetVehicle2), Double.parseDouble(CostVehicle2.getText()), RepairID);
            //refresh table
            //System.out.println("Updated: " + added);
            RepairSearchHandler();
            ClearEditVehicle();
            EditVehicle.setDisable(true);
            if (added)
            {
                EC.TimedMsgGREEN(EditError, "Vehicle Updated");
                LoadOutstanding();
            }
        }
    }
    
    @FXML private void EditPart() throws SQLException 
    {
        if (ValidateEditPart())
        {
            //get repair ID of row to edit
            int RepairID = PartsTable.getSelectionModel().getSelectedItem().getT2IDX();
            boolean added  = repairs.editPart(RegNoPart2.getText(), repairs.getPartID(IDPart2.getValue().toString()), repairs.getSPCID(SPCIDPart2.getValue().toString()), EC.toDate(ExpDelPart2), EC.toDate(ExpRetPart2), RepairID);
            //refresh table
            //System.out.println("Updated: " + added);
            if (PartsBySPC)
            {
                ViewPartsSPC();
            }
            else
            {
                HandleViewParts();
            }
            ClearEditPart();
            EditPart.setDisable(true);
            if (added)
            {
                EC.TimedMsgGREEN(EditErrorPart, "Part Updated");
                LoadOutstanding();
            }
        }
    }
    
    @FXML private boolean ValidateSubmitVehicle()
    {
        ClearAddVStyles();
        boolean valid = true;
        boolean vehiclefound = true;
        if (RegNoVehicle.getText() == null)
        {
            valid = false;
            RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (EC.isPlate(RegNoVehicle.getText()) == false)
        {
            valid = false;
            RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (repairs.findVehicle(EC.toUpperPlate(RegNoVehicle.getText())) == false)
        {
            valid = false;
            vehiclefound = false;
            RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (SPCIDVehicle.getValue() == null)
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
            double cost = Double.parseDouble(CostVehicle.getText());
            if (EC.DecimalPlaces(cost) == false)
            {
                CostVehicle.setStyle("-fx-border-color: #ff1e1e;");
                valid = false;
            }
        }
        catch (NullPointerException|NumberFormatException ex)
        {
            CostVehicle.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        //********************************************************
        if (valid == false)
        {
            if (vehiclefound == true)
            {
                EC.TimedMsgRED(AddError, "Invalid Input");
                return false;
            }
            EC.TimedMsgRED(AddError, "Vehicle Not Found");
            return false;
        }
        ClearAddVStyles();
        return true;
    }
    
    @FXML private boolean ValidateSubmitPart()
    {
        ClearAddPStyles();
        boolean valid = true;
        boolean vehiclefound = true;
        if (RegNoPart.getText() == null)
        {
            valid = false;
            RegNoPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if(EC.isPlate(RegNoPart.getText()) == false )
        {
            valid = false;
            RegNoPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (repairs.findVehicle(EC.toUpperPlate(RegNoPart.getText())) == false)
        {
            valid = false;
            vehiclefound = false;
            RegNoPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (IDPart.getValue() == null)
        {
            valid = false;
            IDPart.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (SPCIDPart.getValue() == null)
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
        //****************************************************
        if (valid == false)
        {
            if (vehiclefound == true)
            {
                EC.TimedMsgRED(AddErrorPart, "Invalid Input");
                return false;
            }
            EC.TimedMsgRED(AddErrorPart, "Vehicle Not Found");
            return false;
        }
        ClearAddPStyles();
        return true;
    }
    
    @FXML private boolean ValidateEditVehicle()
    {
        ClearAddUVStyles();
        boolean valid = true;
        boolean vehiclefound = true;
        if (RegNoVehicle2.getText() == null)
        {
            valid = false;
            RegNoVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (EC.isPlate(RegNoVehicle2.getText()) == false)
        {
            valid = false;
            RegNoVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (repairs.findVehicle(EC.toUpperPlate(RegNoVehicle2.getText())) == false)
        {
            valid = false;
            vehiclefound = false;
            RegNoVehicle2.setStyle("-fx-border-color: #ff1e1e;");
        }
        else
        {
            if (RegNoVehicle2.getText().length() > 7)
            {
                valid = false;
                RegNoVehicle2.setStyle("-fx-border-color: #ff1e1e;");
            }
        }
        if (SPCIDVehicle2.getValue() == null)
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
            double cost = Double.parseDouble(CostVehicle2.getText());
            if (EC.DecimalPlaces(cost) == false)
            {
                CostVehicle2.setStyle("-fx-border-color: #ff1e1e;");
                valid = false;
            }
        }
        catch (NullPointerException|NumberFormatException ex)
        {
            CostVehicle2.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        //**********************************************************
        if (valid == false)
        {
            if (vehiclefound == true)
            {
                EC.TimedMsgRED(EditError, "Invalid Input");
                return false;
            }
            EC.TimedMsgRED(EditError, "Vehicle Not Found");
            return false;
        }
        ClearAddUVStyles();
        return true;
    }
    
    @FXML private boolean ValidateEditPart()
    {
        ClearAddUPStyles();
        boolean valid = true;
        boolean vehiclefound = false;
        if (RegNoPart2.getText() == null)
        {
            valid = false;
            RegNoPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (EC.isPlate(RegNoPart2.getText()) == false)
        {
            valid = false;
            RegNoPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        else if (repairs.findVehicle(EC.toUpperPlate(RegNoPart2.getText())) == false)
        {
            valid = false;
            vehiclefound = false;
            RegNoPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (IDPart2.getValue() == null)
        {
            valid = false;
            IDPart2.setStyle("-fx-border-color: #ff1e1e;");
        }
        if (SPCIDPart2.getValue() == null)
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
        //*****************************************************
        if (valid == false)
        {
            if (vehiclefound == true)
            {
                EC.TimedMsgRED(EditErrorPart, "Invalid Input");
                return false;
            }
            EC.TimedMsgRED(EditErrorPart, "Vehicle Not Found");
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
    
    @FXML private void ToggleOutstanding() throws SQLException
    {
        String visible = ToggleOutstandingButton.getText();
        if (visible.equals("Show Outstanding"))
        {
            //Display Returned Items
            ToggleOutstandingButton.setText("Show Returned");
            OutstandingTitle.setText("Outstanding Repairs");
            LoadOutstanding();
        }
        else
        {
            //Display Outstanding Items
            ToggleOutstandingButton.setText("Show Outstanding");
            OutstandingTitle.setText("Returned Repairs");
            LoadReturned();
        }
        
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
        ObservableList<ListSPC> SPCList = repairs.getSPCList();
        if (SPCList.isEmpty())
        {
            EC.TimedMsgRED(SPCError, "No Centres Found");
        }
        else
        {
            SPCListTable.setItems(SPCList);
        }
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
        SearchedBySPC = false;
    }
    
    @FXML private void SearchByReg() throws SQLException
    {
        T1ID.setCellValueFactory(
                new PropertyValueFactory<SearchMain, Integer>("T1IDX"));
        T1Fname.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1FNAMEX"));
        T1Lname.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1LNAMEX"));
        T1Reg.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1REGX"));
        T1SPC.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1SPCX"));
        T1ExpDel.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1EXPDELX"));
        T1ExpRet.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1EXPRETX"));
        T1Cost.setCellValueFactory(
                new PropertyValueFactory<SearchMain, Double>("T1COSTX"));
            
        if (EC.isPlate(RegSearch.getText()) == false)
        {
            EC.TimedMsgRED(T1SearchError, "Invalid Plate");
        }
        else
        {
            ObservableList<SearchMain> resultList = repairs.searchReg(RegSearch.getText());
            if(resultList.isEmpty())
            {
                EC.TimedMsgRED(T1SearchError, "No Vehicles Found");
            }
            MainTable.setItems(resultList);
        }
    }
    
    @FXML private void SearchByName() throws SQLException
    {
        T1ID.setCellValueFactory(
                new PropertyValueFactory<SearchMain, Integer>("T1IDX"));
        T1Fname.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1FNAMEX"));
        T1Lname.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1LNAMEX"));
        T1Reg.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1REGX"));
        T1SPC.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1SPCX"));
        T1ExpDel.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1EXPDELX"));
        T1ExpRet.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1EXPRETX"));
        T1Cost.setCellValueFactory(
                new PropertyValueFactory<SearchMain, Double>("T1COSTX"));
            
        
        if (EC.isAlphanumeric(FirstNameSearch.getText()) == false || EC.isAlphanumeric(LastNameSearch.getText()) == false)
        {
            EC.TimedMsgRED(T1SearchError, "Invalid Name");
            
        }
        else
        {
            ObservableList<SearchMain> resultList = repairs.searchName(FirstNameSearch.getText(), LastNameSearch.getText());

            if(resultList.isEmpty())
            {
                T1SearchError.setFill(Color.RED);
                EC.TimedMsgRED(T1SearchError, "No Vehicles Found");
            }
            MainTable.setItems(resultList);
        }
    }
    
    
    
    @FXML private void SearchBySPC(int SPCID) throws SQLException
    {
        T1ID.setCellValueFactory(
                new PropertyValueFactory<SearchMain, Integer>("T1IDX"));
        T1Fname.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1FNAMEX"));
        T1Lname.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1LNAMEX"));
        T1Reg.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1REGX"));
        T1SPC.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1SPCX"));
        T1ExpDel.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1EXPDELX"));
        T1ExpRet.setCellValueFactory(
                new PropertyValueFactory<SearchMain, String>("T1EXPRETX"));
        T1Cost.setCellValueFactory(
                new PropertyValueFactory<SearchMain, Double>("T1COSTX"));
            
        ObservableList<SearchMain> resultList = repairs.searchSPC(SPCID);
        MainTable.setItems(resultList);
        if (resultList.isEmpty())
        {
            EC.TimedMsgRED(T1SearchError, "No Vehicles Found");
        }
    }
    
    @FXML private boolean getCustomerByReg(String RegNo) throws SQLException
    {
        Customer CustomerDetails = repairs.searchCustomerWithReg(RegNo);
        if (CustomerDetails == null)
        {
            return false;
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
            return true;
        }
    }
    
    @FXML private boolean VehicleDetails(String regNo) throws SQLException
    {
        T4Reg.setCellValueFactory(
                new PropertyValueFactory<DisplayVehicle, Integer>("T4REGX"));
        T4Make.setCellValueFactory(
                new PropertyValueFactory<DisplayVehicle, String>("T4MAKEX"));
        T4Model.setCellValueFactory(
                new PropertyValueFactory<DisplayVehicle, String>("T4MODELX"));
        T4Fuel.setCellValueFactory(
                new PropertyValueFactory<DisplayVehicle, String>("T4FUELX"));
        T4Mileage.setCellValueFactory(
                new PropertyValueFactory<DisplayVehicle, String>("T4MILEAGEX"));
        T4Color.setCellValueFactory(
                new PropertyValueFactory<DisplayVehicle, Double>("T4COLORX"));
        
        ObservableList<DisplayVehicle> vehicleInfo = repairs.getVehicle(regNo);
        VehicleTable.setItems(vehicleInfo);
        if (vehicleInfo.isEmpty())
        {
            return false;
        }
        return true;
    }
    
    @FXML private void LoadOutstanding() throws SQLException
    {
        ObservableList<OutstandingMain> VehicleList = repairs.getOutstanding();
        OutstandingTable.setItems(VehicleList);
        if (VehicleList.isEmpty())
        {
            OutstandingError.setText("No Repairs Found");
        }
        else
        {
            OutstandingError.setText("");
        }
    }
    
    @FXML private void LoadReturned() throws SQLException
    {
        ObservableList<OutstandingMain> VehicleList = repairs.getReturned();
        OutstandingTable.setItems(VehicleList);
        if (VehicleList.isEmpty())
        {
            OutstandingError.setText("No Repairs Found");
        }
        else
        {
            OutstandingError.setText("");
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
            try 
            {
                VehicleDetails(regNo);
                VehicleTable.setVisible(true);
            } 
            catch (SQLException ex) 
            {
                vehicle = false;
                VehicleTable.setVisible(false);
            }
            try 
            {
                getCustomerByReg(regNo);
                CustomerPane.setVisible(true);
                
            } 
            catch (SQLException ex) 
            {
                customer = false;
                CustomerPane.setVisible(false);
            }

            if (customer == false & vehicle == true)
            {
                EC.TimedMsgRED(T1SearchError, "Customer Not Found");
            }
            else if (customer == true & vehicle == false)
            {
                EC.TimedMsgRED(T1SearchError, "Vehicle Not Found");
            }

            else if (customer == false & vehicle == false)
            {
                EC.TimedMsgRED(T1SearchError, "Vehicle/Customer Not Found");
            }
            RepairEditButton.setDisable(false);
            RepairDeleteButton.setDisable(false);
            ViewPartsButton.setDisable(false);
        }
    }
    
    @FXML private void PartRowClick() 
    {
        boolean vehicle  = true;
        boolean customer = true;
        String regNo = "";
        if (PartsTable.getSelectionModel().getSelectedItem() != null)
        {
            regNo = PartsTable.getSelectionModel().getSelectedItem().getT2REGNOX();
            try 
            {
                VehicleDetails(regNo);
                VehicleTable.setVisible(true);
            } 
            catch (SQLException ex) 
            {
                vehicle = false;
                VehicleTable.setVisible(false);
            }
            try 
            {
                getCustomerByReg(regNo);
                CustomerPane.setVisible(true);
                
            } 
            catch (SQLException ex) 
            {
                customer = false;
                CustomerPane.setVisible(false);
            }

            if (customer == false & vehicle == true)
            {
                EC.TimedMsgRED(T1SearchError, "Customer Not Found");
            }
            else if (customer == true & vehicle == false)
            {
                EC.TimedMsgRED(T1SearchError, "Vehicle Not Found");
            }

            else if (customer == false & vehicle == false)
            {
                EC.TimedMsgRED(T1SearchError, "Vehicle/Customer Not Found");
            }
            RepairEditButton.setDisable(false);
            RepairDeleteButton.setDisable(false);
        }
    }
    
    @FXML private void SPCRowClick() 
    {
        if (SPCListTable.getSelectionModel().getSelectedItem() !=null)
        {
            ListSPCVehicles();
            //set disable to false
            ViewPartsSPCButton.setDisable(false);
        }
        else
        {
            EC.TimedMsgRED(SPCError, "Select a row to view vehicles");
        }
    }
    
    @FXML private void ViewPartsSPC() throws SQLException 
    {
        //Show Parts Table
        T1Header.setVisible(false);
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
        
        if (SPCListTable.getSelectionModel().getSelectedItem() !=null)
        {
            ObservableList<RepairParts> PartList = repairs.getPartRepairsSPC(SPCListTable.getSelectionModel().getSelectedItem().getT3IDX());
            PartsTable.setItems(PartList);
            MainTable.setVisible(false);
            PartsTable.setVisible(true);
            ViewPartsButton.setDisable(true);
            PartsBySPC = true;
            if (PartList.isEmpty())
            {
                EC.TimedMsgRED(T1SearchError, "No Parts Found");
            }
        }
    }
    
    @FXML private void HandleDelete() throws SQLException 
    {
        if (MainTable.isVisible())//If Repair Vehicles table is active
        {
            if (MainTable.getSelectionModel().getSelectedItem() == null)
            {
                EC.TimedMsgRED(T1SearchError, "Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                ViewPartsButton.setDisable(true);
                return;
            }
            int RepairID = MainTable.getSelectionModel().getSelectedItem().getT1IDX();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Vehicle Repair ID: " + RepairID);
            alert.setHeaderText("");
            alert.setContentText("Are you sure you wish to delete?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean success  = repairs.deleteVehicleRepair(RepairID);
                //System.out.println("Deleted: " + success);
                ClearEditVehicle();
                ClearAddUVStyles();
                EditVehicle.setDisable(true);
                if (success)
                {
                    EC.TimedMsgGREEN(T1SearchError, "Delete Successful");
                    if (SearchedBySPC)
                    {
                        JustDeleted = true;
                        ListSPCVehicles();
                        LoadOutstanding();
                    }
                    else
                    {
                        RepairSearchHandler();
                        LoadOutstanding();
                    }
                }
            } else 
            {
                //Delete Cancelled
            }
        }
        else
        {
            if (PartsTable.getSelectionModel().getSelectedItem() == null)
            {
                EC.TimedMsgRED(T1SearchError, "Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                ViewPartsButton.setDisable(true);
                return;
            }
            int RepairID = PartsTable.getSelectionModel().getSelectedItem().getT2IDX();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Delete Part Repair ID: " + RepairID);
            alert.setHeaderText("");
            alert.setContentText("Are you sure you wish to delete?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean success  = repairs.deletePartRepair(RepairID);
                //System.out.println("Deleted: " + success);
                HandleViewParts();
                ClearEditPart();
                ClearAddUPStyles();
                EditPart.setDisable(true);
                if (success)
                {
                    EC.TimedMsgGREEN(T1SearchError, "Delete Successful");
                    LoadOutstanding();
                }
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
                EC.TimedMsgRED(T1SearchError, "Please Select a Row");
                RepairEditButton.setDisable(true);
                RepairDeleteButton.setDisable(true);
                ViewPartsButton.setDisable(true);
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
                EC.TimedMsgRED(T1SearchError, "Please Select a Row");
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
        if(MainTable.getSelectionModel().getSelectedItem() !=null)
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
            SearchedBySPC = false;
            PartsBySPC = false;
            if (PartList.isEmpty())
            {
                EC.TimedMsgRED(T1SearchError, "No Parts Found");
            }
        }
        else
        {
            ViewPartsButton.setDisable(true);
            RepairEditButton.setDisable(true);
            RepairDeleteButton.setDisable(true);
            EC.TimedMsgRED(T1SearchError, "Please Select a Row"); 
        }
        
    }
    
    @FXML private void HandleCustomer() throws SQLException 
    {
        if (MainTable.getSelectionModel().getSelectedItem() == null)
        {
            EC.TimedMsgRED(T1SearchError, "Please Select a Row");
            RepairEditButton.setDisable(true);
            RepairDeleteButton.setDisable(true);
            return;
        }
        getCustomerByReg(MainTable.getSelectionModel().getSelectedItem().getT1REGX());
    }
    
    @FXML private void ListSPCVehicles()
    {
        if (SPCListTable.getSelectionModel().getSelectedItem() !=null)
        {
            CurrentSPC = SPCListTable.getSelectionModel().getSelectedItem().getT3IDX();
        }
        
        try 
        {
            if (JustDeleted)
            {
                SearchBySPC(PreviousSPC);
                JustDeleted = false;
            }
            else
            {
                SearchBySPC(CurrentSPC);
                PreviousSPC = CurrentSPC;
            }
            SearchedBySPC = true;
        } 
        catch (SQLException ex) 
        {
            EC.TimedMsgRED(SPCError, "Please Select a Row");
        }
        MainTable.setVisible(true);
        T1Header.setVisible(true);
        
        PartsTable.setVisible(false);
        T2Header.setVisible(false);
        
        RepairEditButton.setDisable(true);
        RepairDeleteButton.setDisable(true);
        ViewPartsButton.setDisable(true);
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
    
    @FXML private void DPUpdateAV()//Date Picker Restriction for Add Vehicle
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
    
    @FXML private void DPUpdateAP()//Date Picker Restriction for Add Part
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
    
    @FXML private void DPUpdateUV()//Date Picker Restriction for Update Vehicle
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

    @FXML private void DPUpdateUP()//Date Picker Restriction for Update Part
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
        //Initialise Outstanding Repairs Table
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
        T5SPCID.setCellValueFactory(
                new PropertyValueFactory<OutstandingMain, String>("T5SPCNAMEX"));
        
        //Initialise SPC Table
        T3ID.setCellValueFactory(
                new PropertyValueFactory<Repairs, Integer>("T3IDX"));
        T3SPC.setCellValueFactory(
                new PropertyValueFactory<Repairs, String>("T3SPCX"));
        //Set DatePicker Restrictions
        ExpDelVehicle.setDayCellFactory(DCF);
        ExpDelPart.setDayCellFactory(DCF);
        ExpRetVehicle.setDayCellFactory(DCF);
        ExpRetPart.setDayCellFactory(DCF);
        //Set Number Restriction
        EC.SetNumberRestriction(CostVehicle);
        EC.SetNumberRestriction(CostVehicle2);
        EC.SetWordRestriction(FirstNameSearch);
        EC.SetWordRestriction(LastNameSearch);
        //Autocomplete stuff
        UpdateAC();
    }
    //DatePicker Stuff
    private Callback< DatePicker, DateCell > DCF = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem(LocalDate item , boolean empty)
        {
            super.updateItem(item ,empty);
            // disables all past dates + colours them red
            if (item.isBefore(LocalDate.now()))
            {
                this.setDisable (true)                        ;
                this.setStyle("-fx-background-color: #FFD3D3;") ;
            }
        }
    };
    
    private Callback< DatePicker, DateCell > DCFRETURN = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem(LocalDate item , boolean empty)
        {
            super.updateItem(item, empty);
            // disable all past dates + colours them red
            if (item.isBefore(Selection))
            {
                this.setDisable (true)                        ;
                this.setStyle("-fx-background-color: #FFD3D3;") ;
            }
        } 
    };  
    
    @FXML private void checkUpdate() throws SQLException 
    {
        if (tabSwitched)
        {
            UpdateAC();
            tabSwitched = false;
        }
    }
    
    private void UpdateAC()
    {
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
            double size = 175.0;
            TextFields.bindAutoCompletion(RegNoVehicle, VehicleList).setMaxWidth(size);
            TextFields.bindAutoCompletion(RegNoVehicle2, VehicleList).setMaxWidth(size);
            TextFields.bindAutoCompletion(RegNoPart, VehicleList).setMaxWidth(size);
            TextFields.bindAutoCompletion(RegNoPart2, VehicleList).setMaxWidth(size);
        };
    }
    
    /*public static void main (String args[]) throws Exception
    {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("repairs.fxml"));
        Scene scene = new Scene(root,1168,748);
        window.setScene(scene);
        window.show();
    }*/
}