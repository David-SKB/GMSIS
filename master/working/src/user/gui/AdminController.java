package user.gui;

import common.Authentication;
import common.DBConnection;
import common.Main;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import specialist.logic.*;
import user.logic.Employee;
import user.logic.Mechanic;
import user.logic.SPCRegistry;
import user.logic.UserRegistry;

/**
 *
 * @author athanasiosgkanos
 */
public class AdminController {
    DBConnection DB = DBConnection.getInstance();
    UserRegistry UR = UserRegistry.getInstance();
    ErrorChecks EC = ErrorChecks.getInstance();
    Repairs repairs = Repairs.getInstance();
    SPCRegistry SPCReg = SPCRegistry.getInstance();
    @FXML
    private TextField addIDTF, addFNTF, addLNTF, addPassTF, addHRateTF,
                      eFNTF, eIDTF, eLNTF, ePassTF, eHRateTF;
    @FXML
    private ToggleGroup userRights,userRightsE;
    @FXML
    private RadioButton MechanicRBE,AdminRBE;
    @FXML
    private TextField addSPCNameTF, addSPCAddrTF, addSPCPhoneTF, addSPCEmailTF,
                      editSPCNameTF, editSPCAddrTF, editSPCPhoneTF, editSPCEmailTF;
    @FXML
    private Text addUserStatus, editUserStatus, delUserStatus,
                 addSPCStatus, editSPCStatus, delSPCStatus;
    @FXML
    private TableView<Employee> userTV;
    @FXML
    private TableColumn idNumberTableC, firstNameColumn, lastNameTableC, hRateTableC,
                        sysAdmTableC, passTableC;
    @FXML
    private TableView<SPC> spcTV;
    @FXML
    private TableColumn spcNameTableC, spcAddrTableC, spcEmailTableC, 
                        spcPhoneTableC;
    @FXML
    private Button submitUserBTN,submitUserChangesBTN,submitSPCBTN,submitSPCChangesBTN;
    private final ObservableList<Employee> userData = FXCollections.observableArrayList();
    private final ObservableList<SPC> spcData = FXCollections.observableArrayList();
    private Employee tempUser;
    private SPC tempSPC;
    private int SPCID = 0;
    public static String ID;
    public void initialize() {
        /**********************************
         * ADMIN
         **********************************/
        idNumberTableC.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("IDNumber"));
        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("firstName"));
        lastNameTableC.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("surname"));
        hRateTableC.setCellValueFactory(
                new PropertyValueFactory<Mechanic, String>("hRate"));
        sysAdmTableC.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("sysAdmin"));
        passTableC.setCellValueFactory(
                new PropertyValueFactory<Employee, String>("password"));
        userTV.setRowFactory((TableView<Employee> tv) -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempUser = row.getItem();
                     loadOnEdit();
                }
            });
            return row;
        });
        //Input restrictions for add
        EC.SetNumberRestrictionPhone(addIDTF);
        EC.SetWordRestriction(addFNTF);
        EC.SetWordRestriction(addLNTF);
        EC.SetSpaceRestriction(addPassTF);//test
        EC.SetNumberRestriction(addHRateTF);
        
        //Input restrictions for edit
        EC.SetNumberRestrictionPhone(eIDTF);
        EC.SetWordRestriction(eFNTF);
        EC.SetWordRestriction(eLNTF);
        EC.SetSpaceRestriction(ePassTF);//test
        EC.SetNumberRestriction(eHRateTF);
        
        /********************************
         * SPC
         ********************************/
        spcNameTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>("NAMEX"));
        spcAddrTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>("ADDRESSX"));
        spcEmailTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>("EMAILX"));
        spcPhoneTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>("TELEPHONEX"));                
        spcTV.setRowFactory((TableView<SPC> tv) -> {
            TableRow<SPC> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempSPC = row.getItem();
                     loadOnEditSPC();
                     submitSPCChangesBTN.setDisable(false);
                }
            });
            return row;
        });
         EC.SetNumberRestrictionPhone(addSPCPhoneTF);
         EC.SetAddressRestriction(addSPCAddrTF);
         EC.SetWordSpaceRestriction(addSPCNameTF);
         
         EC.SetNumberRestrictionPhone(editSPCPhoneTF);
         EC.SetAddressRestriction(editSPCAddrTF);
         EC.SetWordSpaceRestriction(editSPCNameTF);
    }
    
    public void setUserID(String UID)
    {
        ID = UID;
    }
    
    public String getUserID()
    {
        return ID;
    }
     
    public void updateAnchorPane(AnchorPane AP){
         loadData(userData);loadDataSPC(spcData);
        ObservableList<Node> OL = AP.getChildren();
        SplitPane SP = (SplitPane) OL.get(0);
        ObservableList<Node> OL2 = SP.getItems();
        AnchorPane userAP = (AnchorPane)OL2.get(0);
        updateUserAP(userAP);
        AnchorPane spcAP = (AnchorPane)OL2.get(1);
        updateSPCAP(spcAP);
        //System.out.println("UserID " + ID);
    }
    
    private void updateUserAP(AnchorPane AP){
        ObservableList<Node> OL = AP.getChildren();
        TableView<Employee> userTV = (TableView)OL.get(0);
        userTV.setItems(userData);
    }
    
    private void updateSPCAP(AnchorPane AP){
        ObservableList<Node> OL = AP.getChildren();
        TableView<SPC> userTV = (TableView)OL.get(0);
        userTV.setItems(spcData);
    }
    
    /********************************
         * USER METHODS *
         ********************************/
    
    public void getUsers()
    {
        loadData(userData);       
        userTV.setItems(userData);
    }
    
    public void delUser()
    {
        if(tempUser != null)
        {
            String ID2 = Integer.toString(tempUser.getIDNumber());
            //System.out.println(ID + " " + tempUser.getIDNumber());
            if (ID.equals(ID2))
            {
                EC.TimedMsgRED(delUserStatus, "Cannot Delete Current User");
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deleting User: " + tempUser.getIDNumber());
                alert.setHeaderText("Are you sure you want to delete this user?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK)
                {
                    int IDNo = tempUser.getIDNumber();
                    boolean qStatus = UR.deleteUser(IDNo);

                    if(qStatus)
                    {
                        getUsers();
                    }
                    else
                    {
                        EC.TimedMsgRED(delUserStatus, "Could not delete user");
                    }
                    tempUser = null;
                }
                else
                {
                    tempUser = null;
                    getUsers();
                    clearUserDetailsOnEdit();//clear edit field
                    ClearEditUserStyles();
                }
            }
        }
        else
        {
            EC.TimedMsgRED(delUserStatus, "Double click from the list and press delete.");
        }
    }
    
    public void submitUserDetails()
    {
        ClearAddUserStyles();
        boolean IDValid = true;
        boolean IDStart = true;
        boolean valid = true;
        String CType = validateUserType(userRights);
        //VALIDATION
        if (addIDTF.getText().equals("") || !validateID(addIDTF.getText()))
        {
            addIDTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
            IDValid = false;
        }
        if(addIDTF.getText().startsWith("0"))
        {
            valid = false;
            IDStart = false;
        }
        if (addFNTF.getText().equals(""))
        {
            addFNTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        if (addLNTF.getText().equals(""))
        {
            addLNTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        if (addPassTF.getText().equals(""))
        {
            addPassTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        if (CType.equals("Mechanic"))
        {
            if (addHRateTF.getText().equals("") || !EC.DecimalPlaces(Double.parseDouble(addHRateTF.getText())))
            {
                addHRateTF.setStyle("-fx-border-color: #ff1e1e;");
                valid = false;
            }
        }
        //END VALIDATION
        if(valid)
        {
            if(CType.equals("Admin"))
            {
                adminSubmission(Integer.parseInt(addIDTF.getText()), addFNTF.getText(), addLNTF.getText(), addPassTF.getText());
            }
            else
            {
                userSubmission(Integer.parseInt(addIDTF.getText()), addFNTF.getText(), addLNTF.getText(), addPassTF.getText(), Double.parseDouble(addHRateTF.getText()));
            }
            getUsers();
        }
        else if (!IDValid)
        {
            EC.TimedMsgRED(addUserStatus, "ID must be 5 digits");
        }
        else if (!IDStart)
        {
            EC.TimedMsgRED(addUserStatus, "ID cannot begin with 0");
        }
        else
        {
            EC.TimedMsgRED(addUserStatus, "Invalid Input");
        }
    }
    
    
    public void submitUserChanges(ActionEvent evt)
    {
        ClearEditUserStyles();
        boolean IDValid = true;
        boolean valid = true;
        String CType = validateUserTypeOnEdit(userRightsE);
        //VALIDATION
        if (eIDTF.getText().equals("") || !validateID(eIDTF.getText()))
        {
            eIDTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
            IDValid = false;
        }
        if (eFNTF.getText().equals(""))
        {
            eFNTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        if (eLNTF.getText().equals(""))
        {
            eLNTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        if (ePassTF.getText().equals(""))
        {
            ePassTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        if (CType.equals("Mechanic"))
        {
            if (eHRateTF.getText().equals("") || !EC.DecimalPlaces(Double.parseDouble(eHRateTF.getText())))
            {
                eHRateTF.setStyle("-fx-border-color: #ff1e1e;");
                valid = false;
            }
        }
        //END VALIDATION

        if(valid)
        {
            if(CType.equalsIgnoreCase("Admin"))
            {
                adminSubmissionOnEdit(Integer.parseInt(eIDTF.getText()), eFNTF.getText(), eLNTF.getText(), ePassTF.getText());
                submitUserChangesBTN.setDisable(true);
            }
            else
            {
                userSubmissionOnEdit(Integer.parseInt(eIDTF.getText()), eFNTF.getText(), eLNTF.getText(), ePassTF.getText(),Double.parseDouble(eHRateTF.getText()));
                submitUserChangesBTN.setDisable(true);
            }
            getUsers();
        }
        else if (!IDValid)
        {
            EC.TimedMsgRED(editUserStatus, "ID must be 5 digits");
        }
        else
        {
            EC.TimedMsgRED(editUserStatus, "Invalid Input");
        }
    }
    
    private void ClearAddUserStyles()
    {
        addIDTF.setStyle(null);
        addFNTF.setStyle(null);
        addLNTF.setStyle(null);
        addPassTF.setStyle(null);
        addHRateTF.setStyle(null);
    }
    
    private void ClearEditUserStyles()
    {
        eIDTF.setStyle(null);
        eFNTF.setStyle(null);
        eLNTF.setStyle(null);
        ePassTF.setStyle(null);
        eHRateTF.setStyle(null);
    }
    
    private void loadOnEdit()
    {
        eIDTF.setText(String.valueOf(tempUser.getIDNumber()));
        eFNTF.setText(tempUser.getFirstName());
        eLNTF.setText(tempUser.getSurname());
        ePassTF.setText(tempUser.getPassword());
        boolean isAdmin = tempUser.getSysAdmin();
        if(isAdmin)
        {
            AdminRBE.setSelected(true);
            hideERate();
        }
        else
        {
            MechanicRBE.setSelected(true);
            eHRateTF.setText(String.valueOf(((Mechanic)tempUser).getHRate()));
            unhideERate();
        }
        submitUserChangesBTN.setDisable(false); 
    }
    
    private void loadData(ObservableList<Employee> dataList)
    {
        ArrayList<Employee> urAList = UR.getActiveUsers();
        dataList.removeAll(dataList);
        if(urAList != null && !urAList.isEmpty())
        {
            for(Employee ur : urAList)
            {
               dataList.add(ur);
            }
        }
    }
    
    private void adminSubmission(int ID, String firstName, String lastName, String password)
    {
        boolean addAdmin = UR.addAdmin(ID,password,lastName,firstName);
        if(addAdmin)
        {
            EC.TimedMsgGREEN(addUserStatus, "Successful");
            clearUserDetails();
        }
        else
        {
            EC.TimedMsgRED(addUserStatus, "User already exists.");
            clearUserDetails();
        }   
    }
    
    private void userSubmission(int ID, String firstName, String lastName, String password, double hRate)
    {
            boolean addUser = UR.addUser(ID, password, lastName, firstName, hRate);
            if(addUser)
            {
                EC.TimedMsgGREEN(addUserStatus, "Successful");
                clearUserDetails();
            }
            else
            {
                EC.TimedMsgRED(addUserStatus, "User already exists.");
                clearUserDetails();
            }   
    }
    
    public void clearUserDetails()
    {
        addIDTF.clear();
        addFNTF.clear();
        addLNTF.clear();
        addPassTF.clear();
        addHRateTF.clear();
        if (validateUserType(userRights).equals("Admin"))
        {
            addHRateTF.setVisible(false);
        }
        else
        {
            addHRateTF.setVisible(true);
        }
        ClearAddUserStyles();
    }
    
    private boolean validateID(String ID)
    {
        if(!checkNumeric(ID))
        {
            return false;
        }
        else if(ID.length() != 5)
        {
            return false;
        }
        return true;
    }
    
    private boolean checkNumeric(String checkData)
    {
        try
        {
            long result = Long.parseLong(checkData); 
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    private String validateUserType(ToggleGroup tGroup)
    {
        try
        {
            RadioButton toggleResult = (RadioButton) tGroup.getSelectedToggle();
            if(toggleResult.getText().equals("Admin"))
            {
                return "Admin";
            }
            else
            {
                return "Mechanic";
            }        
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
    
    private String validateUserTypeOnEdit(ToggleGroup tGroup)
    {
        try
        {
            RadioButton toggleResult = (RadioButton) tGroup.getSelectedToggle();
            if(toggleResult.getText().equals("Admin"))
            {
                return "Admin";
            }else
            {
                return "Mechanic";
            }        
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
    
    @FXML private void hideRate()
    {
        addHRateTF.setVisible(false);
    }
    
    @FXML private void unhideRate()
    {
        addHRateTF.setVisible(true);
    }
    
    @FXML private void hideERate()
    {
        eHRateTF.setVisible(false);
    }
    
    @FXML private void unhideERate()
    {
        eHRateTF.setVisible(true);
    }

    private void adminSubmissionOnEdit(int ID, String firstName, String lastName, String pass) 
    {
        boolean editAdmin = UR.editAdmin(ID,pass,lastName,firstName,true,tempUser.getIDNumber());
        if(editAdmin)
        {
            EC.TimedMsgGREEN(editUserStatus, "Successful");
            clearUserDetailsOnEdit();
        }else
        {
            
            EC.TimedMsgRED(editUserStatus, "User already exists.");
            clearUserDetailsOnEdit();
        } 
    }

    private void userSubmissionOnEdit(int ID, String firstName, String lastName, String pass, double hRate)
    {
        boolean editUser = UR.editUser(ID, pass, lastName, firstName, hRate,false,tempUser.getIDNumber());
        if(editUser)
        {
            EC.TimedMsgGREEN(editUserStatus, "Successful");
            clearUserDetailsOnEdit();
        }
        else
        {
            EC.TimedMsgRED(editUserStatus, "User already exists.");
            clearUserDetailsOnEdit();
        }      
    }
    
    private void clearUserDetailsOnEdit()
    {
        eFNTF.clear();
        eIDTF.clear();
        eLNTF.clear();
        ePassTF.clear();
        eHRateTF.clear();
        if (validateUserType(userRightsE).equals("Admin"))
        {
            eHRateTF.setVisible(false);
        }
        else
        {
            eHRateTF.setVisible(true);
        }
    }
    
    private boolean validateUserOnEdit(String userType)
    {
        try
        {
            boolean isAdmin = Boolean.parseBoolean(userType);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    /********************************
         * SPC METHODS *
         ********************************/
    
    public void getSPCs()
    {
        loadDataSPC(spcData);
        spcTV.setItems(spcData);
    }
    
    public void deleteSPC(ActionEvent evt){
        if(tempSPC != null)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting " + tempSPC.getNAMEX());
            alert.setHeaderText("Are you sure you want to delete " + tempSPC.getNAMEX() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean qStatus = SPCReg.deleteSPC(tempSPC.getNAMEX(), tempSPC.getADDRESSX(), tempSPC.getTELEPHONEX(), tempSPC.getEMAILX());
                if(qStatus)
                {
                    getSPCs();
                }
                else
                {
                    EC.TimedMsgRED(delSPCStatus, "Could not delete SPC");
                    getSPCs();
                }
                
            }
            tempSPC = null;
            clearSPCEditStyles();//clear edit field once deleted
            clearSPCDetailsEdit();
            submitSPCChangesBTN.setDisable(true);
        }
        else
        {
            EC.TimedMsgRED(delSPCStatus, "Double click from the list and press delete");
        }
    }
    
    public void submitSPCDetails(ActionEvent evt) throws SQLException
    {
        clearSPCStyles();//Clear any previous styles on the fields
        boolean telValid, emailValid, valid, exists;
        valid = true;
        exists = false;
        
        //VALIDATION
        if (addSPCNameTF.getText().equals(""))
        {
            addSPCNameTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        
        if (repairs.getSPCID(addSPCNameTF.getText()) != 0)
        {
            addSPCNameTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
            exists = true;
        }
        
        if (addSPCAddrTF.getText().equals(""))
        {
            addSPCAddrTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        
        telValid = EC.isPhone(addSPCPhoneTF.getText());//checks if valid phone no. or if empty
        if (!telValid || addSPCPhoneTF.getText().equals(""))
        {
            addSPCPhoneTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        
        emailValid = EC.isEmail(addSPCEmailTF.getText());//checks if valid email or if empty
        if (!emailValid || addSPCEmailTF.getText().equals(""))
        {
            addSPCEmailTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        //END VALIDATION
        
        
        if (valid)//if no errors found
        {
            submitSPC(addSPCNameTF.getText(), addSPCAddrTF.getText(), addSPCPhoneTF.getText(), addSPCEmailTF.getText());//add spc
            getSPCs();//Refresh table
        }
        else
        {
            if (exists)
            {
                EC.TimedMsgRED(addSPCStatus, "Centre Already Exists");
            }
            else
            {
                EC.TimedMsgRED(addSPCStatus, "Invalid Input");
            }
        }
    }
    
    public void clearSPCDetails()
    {
        addSPCNameTF.clear();
        addSPCAddrTF.clear();
        addSPCPhoneTF.clear();
        addSPCEmailTF.clear();
        clearSPCStyles();
    }
    
    public void clearSPCStyles()
    {
        addSPCNameTF.setStyle(null);
        addSPCAddrTF.setStyle(null);
        addSPCPhoneTF.setStyle(null);
        addSPCEmailTF.setStyle(null); 
    }
    
    public void clearSPCEditStyles()
    {
        editSPCNameTF.setStyle(null);
        editSPCAddrTF.setStyle(null);
        editSPCPhoneTF.setStyle(null);
        editSPCEmailTF.setStyle(null); 
    }
    
    private void clearSPCDetailsEdit()
    {
        editSPCNameTF.clear();
        editSPCAddrTF.clear();
        editSPCPhoneTF.clear();
        editSPCEmailTF.clear();
    }
    
    public void submitSPCChanges(ActionEvent evt){
        clearSPCEditStyles();//Clear any previous styles on the fields
        boolean telValid, emailValid, valid;
        valid = true;
        
        //VALIDATION
        if (editSPCNameTF.getText().equals(""))
        {
            editSPCNameTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        
        if (editSPCAddrTF.getText().equals(""))
        {
            editSPCAddrTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        
        telValid = EC.isPhone(editSPCPhoneTF.getText());//checks if valid phone no. or if empty
        if (!telValid || editSPCPhoneTF.getText().equals(""))
        {
            editSPCPhoneTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        
        emailValid = EC.isEmail(editSPCEmailTF.getText());//checks if valid email or if empty
        if (!emailValid || editSPCEmailTF.getText().equals(""))
        {
            editSPCEmailTF.setStyle("-fx-border-color: #ff1e1e;");
            valid = false;
        }
        //END VALIDATION

        if(valid)
        {
            submitSPConChange(editSPCNameTF.getText(), editSPCAddrTF.getText(), editSPCPhoneTF.getText(), editSPCEmailTF.getText());//edit spc
            getSPCs();
            submitSPCChangesBTN.setDisable(true);//disable the button again
        }
        else
        {
            EC.TimedMsgRED(editSPCStatus, "Invalid Input");
        }
    }
    
    private void loadOnEditSPC()
    {
        editSPCNameTF.setText(tempSPC.getNAMEX());
        editSPCAddrTF.setText(tempSPC.getADDRESSX());
        editSPCPhoneTF.setText(tempSPC.getTELEPHONEX());
        editSPCEmailTF.setText(tempSPC.getEMAILX());
        submitSPCChangesBTN.setDisable(false);
    }
    
    private void loadDataSPC(ObservableList<SPC> dataList)
    {
        ArrayList<SPC> spcList = SPCReg.getSPCs();
        dataList.removeAll(dataList);
        if(spcList != null  && !spcList.isEmpty())
        {
            for(SPC spc : spcList)
            {
               dataList.add(spc);
            }
        }
    }
    
    private void submitSPC(String spcName, String spcAddress,String spcPhone, String spcEmail)
    {
        boolean addSPC = SPCReg.addSPC(spcName, spcAddress, spcPhone, spcEmail);
        if(addSPC)
        {
            EC.TimedMsgGREEN(addSPCStatus, "Centre Added");
            clearSPCDetails();
        }
        else
        {
            EC.TimedMsgRED(addSPCStatus, "SPC already exists.");
            clearSPCDetails();
        }   
    }
    
    private void submitSPConChange(String spcName, String spcAddress, String spcPhone, String spcEmail)
    {
        if (spcTV.getSelectionModel().getSelectedItem() != null)//ensures sure row is still selected
        {
            SPCID = spcTV.getSelectionModel().getSelectedItem().getIDX();
        }
        boolean editSPC = SPCReg.editSPC(spcName, spcAddress, spcPhone, spcEmail, SPCID);
        if(editSPC)
        {
            EC.TimedMsgGREEN(editSPCStatus, "Centre Updated");
            clearSPCDetailsEdit();
        }
        else
        {
            EC.TimedMsgRED(editSPCStatus, "SPC already exists");
            clearSPCDetailsEdit();
        }          
    }
    
    @FXML
    private void WipeData()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Wipe System Data");
            alert.setHeaderText("Are you sure you want to reset the system?");
            alert.setContentText("This will wipe and reset all user data, current user will be logged out");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
            {
                boolean Success = UR.wipeData();
                Main.stage.close();
                Authentication authenticate = new Authentication();
                authenticate.start(new Stage()); 
            }  
    }
}
