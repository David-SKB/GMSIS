package specialist.gui;

import common.DBConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import specialist.logic.*;

public class RepairsController extends Application
{
    Repairs repairs = Repairs.getInstance();
    private DBConnection DBC = DBConnection.getInstance();
    //Send Vehicle Pane
    @FXML private TitledPane SendVehicle;
    @FXML private TextField RegNoVehicle;
    @FXML private TextField SPCIDVehicle;
    @FXML private DatePicker ExpDelVehicle;
    @FXML private DatePicker ExpRetVehicle;
    @FXML private TextField CostVehicle;
    @FXML private Button SubmitVehicleButton;
    
    //Send Part Pane
    @FXML private TitledPane SendPart;
    @FXML private TextField NamePart;
    @FXML private TextField DescPart;
    @FXML private TextField IDPart;
    @FXML private DatePicker ExpDelPart;
    @FXML private DatePicker ExpRetPart;
    @FXML private TextField CostPart;
    @FXML private Button SubmitPartButton;
    @FXML private Button UpdatePartButton;
    
    //Edit Vehicle Pane
    @FXML private TitledPane EditVehicle;
    @FXML private TextField RegNoVehicle2;
    @FXML private TextField SPCIDVehicle2;
    @FXML private TextField ExpDelVehicle2;
    @FXML private TextField ExpRetVehicle2;
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
    
    //Misc
    @FXML private TextField RegSearch;
    @FXML private TextField FirstNameSearch;
    @FXML private TextField LastNameSearch;
    @FXML private Toggle NameRadioButton;
    @FXML private Toggle RegRadioButton;
    @FXML private ToggleGroup SearchVehicleToggle;
    @FXML private ToggleGroup SendItemToggle;
    @FXML private Label T1SearchError;
    @FXML private Button RepairEditButton;
    @FXML private Button RepairDeleteButton;
    
    //requirements left: 7, 8, 10, 12, 14
    //******************************************************
    @FXML private void SubmitVehicle() 
    {
        boolean added  = repairs.addVehicle(RegNoVehicle.getText(), Integer.parseInt(SPCIDVehicle.getText()), toDate(ExpDelVehicle), toDate(ExpRetVehicle), Double.parseDouble(CostVehicle.getText()));
        System.out.println("added: " + added);
    }
    
    @FXML private void SubmitPart() 
    {
        boolean added  = repairs.addPart(NamePart.getText(), DescPart.getText(), Integer.parseInt(IDPart.getText()), toDate(ExpDelPart), toDate(ExpRetPart), Double.parseDouble(CostPart.getText()));
        System.out.println("added: " + added);
    }
    
    @FXML private void ToggleSendPart()
    {
        SendVehicle.setVisible(false);
        SendPart.setVisible(true);
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
        DBC.closeConnection();
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
            else
            {
                T1SearchError.setVisible(false);
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
                T1SearchError.setVisible(false);
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
        String regNo = MainTable.getSelectionModel().getSelectedItem().getT1REGX();
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
    }
    
    @FXML private void HandleDelete() throws SQLException 
    {
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
        } else 
        {
            //Delete Cancelled
        }
    }
    
    @FXML private String toString(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        //System.out.println(sqlDate);
        return sqlDate.toString();
    }
    
    @FXML private Date toDate(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        //System.out.println(sqlDate);
        return sqlDate;
    }
    
    @FXML private void TestFunction()
    {
        //T1Type.setVisible(false);
        //T1SearchError.setText("hi der");
        //System.out.println("line contains: " + FirstNameSearch.getText());
        //RepairEditButton.setDisable(false);
        //T1SearchError.setText("wag1ggg");
        //T1SearchError.setVisible(true);
        //System.out.println(repairs.isPlate(FirstNameSearch.getText()));
        
        
        /*LocalDate localDate = DelDateV.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date testdate = (Date) Date.from(instant);
        System.out.println(localDate + "\n" + instant + "\n" + testdate);*/
        
        //java.sql.Date sqlDate = java.sql.Date.valueOf(DelDateV.getValue());
        //System.out.println(toString(DelDateV));
        
    }
    
    
    
    public static void main (String args[]) throws Exception
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
    }
    
    @FXML private void RepairErrMsg(String msg)
    {
        T1SearchError.setText(msg);
        T1SearchError.setVisible(true);
    }
    
}
/*


private Callback< DatePicker, DateCell > dayCellFactory = (final DatePicker datePicker1) -> new DateCell()
    {
        
        @Override
        public void updateItem( LocalDate item , boolean empty )
        {
            
            // Must call super
            super.updateItem( item , empty );
            
            // disable all sundays + colours them red
            DayOfWeek day = DayOfWeek.from( item );
            if ( day == DayOfWeek.SUNDAY )
            {
                
                this.setDisable ( true );
                this.setStyle(" -fx-background-color: #ff0000; ") ;
                
            }
            
            // disable all past dates + colours them red
            if (  item.isBefore( LocalDate.now() )  )
            {
                
                this.setDisable ( true )                        ;
                this.setStyle(" -fx-background-color: #ff0000; ") ;
                
            }

        }
        
    };


public void showError(String msg) 
{
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(msg);
    alert.showAndWait();
}





















*/