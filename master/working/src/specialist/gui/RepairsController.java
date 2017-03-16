package specialist.gui;

import common.DBConnection;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class RepairsController /*extends Application*/ implements Initializable
{
    private FXMLLoader fXMLLoader = new FXMLLoader();
    Repairs repairs = Repairs.getInstance();
    private DBConnection DBC = DBConnection.getInstance();
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
    @FXML private TextField NamePart;
    @FXML private TextField DescPart;
    @FXML private TextField IDPart;
    @FXML private ComboBox SPCIDPart;
    @FXML private DatePicker ExpDelPart;
    @FXML private DatePicker ExpRetPart;
    @FXML private TextField CostPart;
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
    //@FXML private TableColumn T1;
    
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
    
    //Customer Details Pane
    @FXML private Pane CustomerPane;
    @FXML private Button ViewCustomerButton;
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
    @FXML private Button RepairEditButton;
    @FXML private Button RepairDeleteButton;
    @FXML private Button SPCVehicleButton;
    @FXML private Text T1SearchErrorText;
    
    
    //requirements left: 7, 8, 10, 12, 14
    //todo: edit onlcick retrieve data and put in textfields
    //******************************************************
    @FXML private void SubmitVehicle() throws SQLException 
    {
        if (ValidateSubmitVehicle())
        {
            boolean added  = repairs.addVehicle(RegNoVehicle.getText(), repairs.getSPCID(SPCIDVehicle.getValue().toString()), toDate(ExpDelVehicle), toDate(ExpRetVehicle), Double.parseDouble(CostVehicle.getText()));
            System.out.println("added: " + added);
            clearAddVehicle();
        }
    }
    
    @FXML private void SubmitPart() throws SQLException 
    {
        boolean added  = repairs.addPart(NamePart.getText(), DescPart.getText(), Integer.parseInt(IDPart.getText()), repairs.getSPCID(SPCIDPart.getValue().toString()), toDate(ExpDelPart), toDate(ExpRetPart), Double.parseDouble(CostPart.getText()));
        System.out.println("added: " + added);
        ClearAddPart();
    }
    
    @FXML private void EditVehicle() throws SQLException 
    {
        if (ValidateEditVehicle())
        {
            //get repair id of row to edit
            int RepairID = MainTable.getSelectionModel().getSelectedItem().getT1IDX();
            //int RepairID = repairs.getSPCID(MainTable.getSelectionModel().getSelectedItem().getT1SPCX());
            System.out.println(RepairID);
            boolean added  = repairs.editVehicle(RegNoVehicle2.getText(), repairs.getSPCID(SPCIDVehicle2.getValue().toString()), toDate(ExpDelVehicle2), toDate(ExpRetVehicle2), Double.parseDouble(CostVehicle2.getText()), RepairID);
            //refresh table
            System.out.println("Updated: " + added);
            RepairSearchHandler();
            clearEditVehicle();
            EditVehicle.setDisable(true);
            EditError.setVisible(false);
        }
    }
    
    @FXML private boolean ValidateSubmitVehicle()
    {
        ClearAddVStyles();
        boolean valid = true;
        if (repairs.isPlate(RegNoVehicle.getText()) == false)
        {
            valid = false;
            RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
        }
        else
        {
            if (RegNoVehicle.getText().length() != 7)
            {
                valid = false;
                RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
            }
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
    
    @FXML private boolean ValidateEditVehicle()
    {
        ClearAddUVStyles();
        boolean valid = true;
        if (repairs.isPlate(RegNoVehicle2.getText()) == false)
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
    
    @FXML private void ClearAddVStyles()
    {
        RegNoVehicle.setStyle(null);
        SPCIDVehicle.setStyle(null);
        ExpDelVehicle.setStyle(null);
        ExpRetVehicle.setStyle(null);
        CostVehicle.setStyle(null);
    }

    @FXML private void ClearAddUVStyles()
    {
        RegNoVehicle2.setStyle(null);
        SPCIDVehicle2.setStyle(null);
        ExpDelVehicle2.setStyle(null);
        ExpRetVehicle2.setStyle(null);
        CostVehicle2.setStyle(null);
    }
    
    @FXML private void ToggleSendPart()
    {
        SendVehicle.setVisible(false);
        SendPart.setVisible(true);
    }
    
    private void clearAddVehicle()
    {
        RegNoVehicle.setText(null);
        SPCIDVehicle.setValue(null);
        ExpDelVehicle.setValue(null);
        ExpRetVehicle.setValue(null);
        CostVehicle.setText(null);
    }
    
    @FXML private void ClearAddPart()
    {
        NamePart.setText(null);
        DescPart.setText(null);
        IDPart.setText(null);
        SPCIDPart.setValue(null);
        ExpDelPart.setValue(null);
        ExpRetPart.setValue(null);
        CostPart.setText(null);
    }
    
    private void clearEditVehicle()
    {
        RegNoVehicle2.setText(null);
        SPCIDVehicle2.setValue(null);
        ExpDelVehicle2.setValue(null);
        ExpRetVehicle2.setValue(null);
        CostVehicle2.setText(null);
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
            
        if (repairs.isPlate(RegSearch.getText()) == false)
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
            
        
        if (repairs.isAlphanumeric(FirstNameSearch.getText()) == false || repairs.isAlphanumeric(LastNameSearch.getText()) == false)
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
    
    @FXML private void VehicleRowClick() 
    {
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
            RepairErrMsg("Vehicle Not Found");//make one for vehicle details table
        }
        RepairEditButton.setDisable(false);
        RepairDeleteButton.setDisable(false);
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
    }
    
    @FXML private void HandleDelete() throws SQLException 
    {
        if (MainTable.getSelectionModel().getSelectedItem() == null)
        {
            RepairErrMsg("Please select a row");
            RepairEditButton.setDisable(true);
            RepairDeleteButton.setDisable(true);
            return;
        }
        int RepairID = MainTable.getSelectionModel().getSelectedItem().getT1IDX();
        System.out.println(RepairID);
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Repair " + RepairID);
        alert.setHeaderText("");
        alert.setContentText("Are you sure you wish to proceed?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            boolean success  = repairs.deleteVehicleRepair(RepairID);
            System.out.println("Deleted: " + success);
            RepairSearchHandler();
            clearEditVehicle();
            EditVehicle.setDisable(true);
        } else 
        {
            //Delete Cancelled
        }
    }
    
    @FXML private void HandleEdit() throws SQLException 
    {
        
        if (MainTable.getSelectionModel().getSelectedItem() == null)
        {
            RepairErrMsg("Please select a row");
            RepairEditButton.setDisable(true);
            RepairDeleteButton.setDisable(true);
            return;
        }
        LoadComboListSPC();
        //Set Update textfields with data
        EditVehicle.setVisible(true);
        //EditPart.setVisible(false);//needs to be made
        RegNoVehicle2.setText(MainTable.getSelectionModel().getSelectedItem().getT1REGX());
        SPCIDVehicle2.setValue(MainTable.getSelectionModel().getSelectedItem().getT1SPCX());
        //convert dates
        LocalDate expDel = StringtoLDate(MainTable.getSelectionModel().getSelectedItem().getT1EXPDELX());
        LocalDate expRet = StringtoLDate(MainTable.getSelectionModel().getSelectedItem().getT1EXPRETX());
        Previous.setValue(expDel);//Needed for error checking
        ExpRetVehicle2.setValue(expRet);
        ExpDelVehicle2.setValue(expDel);
        //convert cost
        String cost = Double.toString(MainTable.getSelectionModel().getSelectedItem().getT1COSTX());
        CostVehicle2.setText(cost);
        EditVehicle.setDisable(false);
    }
    
    @FXML private String toString(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        return sqlDate.toString();
    }
    
    @FXML private Date toDate(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        return sqlDate;
    }
    
    @FXML private LocalDate StringtoLDate(String value)
    {

        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate newDate = LocalDate.parse(value, DATE_FORMAT);
        
        
        return newDate;
    }
    
    @FXML private void ListSPCVehicles()
    {
        int SPCID = SPCListTable.getSelectionModel().getSelectedItem().getT3IDX();
        System.out.println(SPCID);
        
        try 
        {
            SearchBySPC(SPCID);
        } 
        catch (SQLException ex) 
        {
            System.out.println("Select something fool");
        }
        SPCVehicleButton.setDisable(true);
        RepairEditButton.setDisable(true);
        RepairDeleteButton.setDisable(true);
    }
    
    @FXML private void TestFunction()
    {
        //RegNoVehicle.setStyle("-fx-border-color: #ff1e1e;");
        //SPCIDVehicle.setStyle(null);
        //T1SearchError.setText("wag1");
        RepairErrMsg("wtf");
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
        System.out.println("OI");
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
    
    @FXML private void LoadComboListSPC() throws SQLException
    {
        ObservableList<String> SPCNameList = repairs.getSPCListCombo();
        SPCIDVehicle.setItems(SPCNameList);
        SPCIDVehicle2.setItems(SPCNameList);
        SPCIDPart.setItems(SPCNameList);
    }
    
    @FXML private void DPUpdateAV()
    {
        Selection = StringtoLDate(toString(ExpDelVehicle));
        ExpRetVehicle.setDayCellFactory(DCFRETURN);
        if (ExpRetVehicle.getValue() != null)
        {
            if (StringtoLDate(toString(ExpRetVehicle)).isBefore(Selection))
            ExpRetVehicle.setValue(null);
        }
    }
    
    @FXML private void DPUpdateAP()
    {
        Selection = StringtoLDate(toString(ExpDelPart));
        ExpRetPart.setDayCellFactory(DCFRETURN);
        if (ExpRetPart.getValue() != null)
        {
            if (StringtoLDate(toString(ExpRetPart)).isBefore(Selection))
            ExpRetPart.setValue(null);
        }
    }
    
    @FXML private void DPUpdateUV()
    {

        Selection = StringtoLDate(toString(ExpDelVehicle2));
        if (Selection.isAfter(ExpRetVehicle2.getValue()))
        {
            ExpDelVehicle2.setValue(Previous.getValue());  
        }
        ExpRetVehicle2.setDayCellFactory(DCFRETURN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        try 
        {
            //NEEDS TO BE FINISHED
            LoadComboListSPC();
            ExpDelVehicle.setDayCellFactory(DCF);
            ExpDelPart.setDayCellFactory(DCF);
            ExpRetVehicle.setDayCellFactory(DCF);
            ExpRetPart.setDayCellFactory(DCF);
        } catch (SQLException ex) 
        {
            Logger.getLogger(RepairsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Callback< DatePicker, DateCell > DCF = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem( LocalDate item , boolean empty )
        {
            // Must call super
            super.updateItem( item , empty );
            
            // disable all past dates + colours them red
            if (  item.isBefore( LocalDate.now() )  )
            {
                this.setDisable ( true )                        ;
                this.setStyle(" -fx-background-color: #FFD3D3; ") ;
            }
        }
    };
    
    private Callback< DatePicker, DateCell > DCFRETURN = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem( LocalDate item , boolean empty )
        {
            // Must call super
            super.updateItem( item , empty );
            
            // disable all past dates + colours them red
            if (item.isBefore(Selection))
            {
                this.setDisable ( true )                        ;
                this.setStyle(" -fx-background-color: #FFD3D3; ") ;
            }
        } 
    };  
}
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