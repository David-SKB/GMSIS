package user.gui;

import common.DBConnection;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    SPCRegistry SPCReg = SPCRegistry.getInstance();
    @FXML
    private TextField addIDTF, addFNTF, addLNTX, addPassTF, addHRateTF,
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
    private TableView spcTV;
    @FXML
    private TableColumn spcNameTableC, spcAddrTableC, spcEmailTableC, 
                        spcPhoneTableC;
    private final ObservableList<Employee> userData = FXCollections.observableArrayList();
    private final ObservableList<SPC> spcData = FXCollections.observableArrayList();
    private Employee tempUser;
    private SPC tempSPC;
    
    public void getUsers(ActionEvent evt){
        loadData(userData);
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
        userTV.setItems(userData);
        
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
    }
    
    public void delUser(ActionEvent evt){
        if(tempUser != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting User");
            alert.setHeaderText("Are you sure you want to delete this user?");
            alert.setContentText("User Details: " + tempUser.getIDNumber() + " " + tempUser.getFirstName() + 
                    " " + tempUser.getSurname() + " " + tempUser.getPassword());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DB.connect();
            
                int IDNo = tempUser.getIDNumber();
                boolean qStatus = UR.deleteUser(IDNo);
            
                DB.closeConnection();
                if(qStatus){
                    getUsers(new ActionEvent());
                }else{
                    delUserStatus.setText("Could not delete user.");
                    delUserStatus.setFill(Color.RED);
                    new java.util.Timer().schedule( 
                    new java.util.TimerTask() {
                         public void run() {
                             delUserStatus.setText("");
                        }
                    }, 
                    2000
                    );
                    }
            }else{
                getUsers(new ActionEvent());
            }
        }else{
            delUserStatus.setText("Double click from the list and press delete.");
            delUserStatus.setFill(Color.RED);
            new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    delUserStatus.setText("");
                }
            }, 
            2000
            );
        }
    }
    
    public void submitUserDetails(ActionEvent etv){
        boolean IDValid,
                fnValid,
                lnValid,
                passValid;
        boolean hRateValid = true;
        boolean userType = true;
                
        String tempID =  addIDTF.getText(); 
        IDValid = validateID(tempID, addIDTF);

        String tempFN = addFNTF.getText();
        fnValid = validateTextField(tempFN,addFNTF, "First Name");
        
        String tempLN = addLNTX.getText();    
        lnValid = validateTextField(tempLN,addLNTX, "Last Name");
        
        String tempPass = addPassTF.getText();
        passValid = validateTextField(tempPass,addPassTF, "Password");
      
        String tempCType = validateUserType(userRights);
        
        String tempHRate = addHRateTF.getText();
        if(tempCType == null){
            hRateValid = validateRate(tempHRate, addHRateTF);
        }else if(tempCType.equalsIgnoreCase("Mechanic")){
            hRateValid = validateRate(tempHRate, addHRateTF);
        } 

        if(IDValid && fnValid && lnValid && passValid && hRateValid && userType){
            if(tempCType.equals("Admin")){
                adminSubmission(Integer.parseInt(tempID),tempFN,tempLN,tempPass);
            }else{
                userSubmission(Integer.parseInt(tempID),tempFN,tempLN,tempPass,Double.parseDouble(tempHRate));
            }
            getUsers(new ActionEvent());
        }
    }
    
    
    public void submitUserChanges(ActionEvent evt){
        boolean IDValid,
                fnValid,
                lnValid,
                passValid;
        boolean hRateValid = true;
        boolean userType = true;
         

        String tempID =  eIDTF.getText(); 
        IDValid = validateID(tempID, eIDTF);

        String tempFN = eFNTF.getText();
        fnValid = validateTextField(tempFN,eFNTF, "First Name");
        
        String tempLN = eLNTF.getText();    
        lnValid = validateTextField(tempLN,eLNTF, "Last Name");
          
        String tempPass = ePassTF.getText();
        passValid = validateTextField(tempPass,ePassTF, "Password");

        String tempCType = validateUserType(userRightsE);
        String tempHRate = eHRateTF.getText();
        if(tempCType == null){
            hRateValid = validateRate(tempHRate, eHRateTF);
        }else if(tempCType.equalsIgnoreCase("Mechanic")){
            hRateValid = validateRate(tempHRate, eHRateTF);
        } 

        if(IDValid && fnValid && lnValid && passValid && hRateValid && userType){
            if(tempCType.equalsIgnoreCase("Admin")){
                adminSubmissionOnEdit(Integer.parseInt(tempID),tempFN,tempLN,tempPass);
            }else{
                userSubmissionOnEdit(Integer.parseInt(tempID),tempFN,tempLN,tempPass,Double.parseDouble(tempHRate));
            }
            getUsers(new ActionEvent());
        }
    }
    
    public void getSPCs(ActionEvent evt){
        loadDataSPC(spcData);                        
        spcNameTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>(""));
        spcAddrTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>(""));
        spcEmailTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>(""));
        spcPhoneTableC.setCellValueFactory(
                new PropertyValueFactory<SPC, String>(""));
        spcTV.setItems(spcData);
        
        spcTV.setRowFactory((TableView<SPC> tv) -> {
            TableRow<SPC> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempSPC = row.getItem();
                     loadOnEdit();
                }
            });
            return row;
        });
    }
    
    public void deleteSPC(ActionEvent evt){
        if(tempSPC != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting SPC Centre");
            alert.setHeaderText("Are you sure you want to delete this SPC Centre?");
            alert.setContentText("SPC Details: " + tempSPC.getName() + " " + tempSPC.getAddress() + 
                    " " + tempSPC.getTelephone() + " " + tempSPC.getEmail());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                DB.connect();
                   
                boolean qStatus = SPCReg.deleteSPC(tempSPC.getName(),tempSPC.getAddress(),
                                 tempSPC.getTelephone(),tempSPC.getEmail());
                
                DB.closeConnection();
                if(qStatus){
                    getSPCs(new ActionEvent());
                }else{
                    delSPCStatus.setText("Could not delete SPC.");
                    delSPCStatus.setFill(Color.RED);
                    new java.util.Timer().schedule( 
                    new java.util.TimerTask() {
                         public void run() {
                             delSPCStatus.setText("");
                        }
                    }, 
                    2000
                    );
                    }
            }else{
                getSPCs(new ActionEvent());
            }
        }else{
            delSPCStatus.setText("Double click from the list and press delete.");
            delSPCStatus.setFill(Color.RED);
            new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    delSPCStatus.setText("");
                }
            }, 
            2000
            );
        }
    }
    
    public void submitSPCDetails(ActionEvent evt){
        boolean nameValid,
                addressValid,
                telValid,
                emailValid;
        
        String tempName =  addSPCNameTF.getText(); 
        nameValid = validateTextField(tempName, addSPCNameTF, "SPC Name");

        String tempAddress = addSPCAddrTF.getText();
        addressValid = validateTextField(tempAddress, addSPCAddrTF, "Address");
        
        String tempPhone = addSPCPhoneTF.getText();    
        telValid = validateTextField(tempPhone, addSPCPhoneTF, "Telephone Number");
        
        String tempEmail = addSPCEmailTF.getText();
        emailValid = validateTextField(tempEmail, addSPCEmailTF, "Email");

        if(nameValid && addressValid && telValid && emailValid){
            submitSPC(tempName,tempAddress,tempPhone,tempEmail);
        }
            getSPCs(new ActionEvent());
    }
    
    public void clearSPCDetails(ActionEvent evt){ 
        addSPCNameTF.clear();
        addSPCAddrTF.clear();
        addSPCPhoneTF.clear();
        addSPCEmailTF.clear();
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                     addSPCStatus.setText("");
                }
            }, 
            1500
        );
    }
    
    public void submitSPCChanges(ActionEvent evt){
        boolean nameValid,
                addressValid,
                telValid,
                emailValid;

        String tempName =  editSPCNameTF.getText(); 
        nameValid = validateTextField(tempName, editSPCNameTF, "SPC Name");

        String tempAddress = editSPCAddrTF.getText();
        addressValid = validateTextField(tempAddress, editSPCAddrTF, "Address");
        
        String tempPhone = editSPCPhoneTF.getText();    
        telValid = validateTextField(tempPhone, editSPCPhoneTF, "Telephone Number");
        
        String tempEmail = editSPCEmailTF.getText();
        emailValid = validateTextField(tempEmail, editSPCEmailTF, "Email");

        if(nameValid && addressValid && telValid && emailValid){
            submitSPConChange(tempName,tempAddress,tempPhone,tempEmail,tempName,tempAddress,tempPhone,tempEmail);
        }
            getSPCs(new ActionEvent());
    }
    
    private void loadOnEdit(){
        eIDTF.setText(String.valueOf(tempUser.getIDNumber()));
        eFNTF.setText(tempUser.getFirstName());
        eLNTF.setText(tempUser.getSurname());
        ePassTF.setText(tempUser.getPassword());
        boolean isAdmin = tempUser.getSysAdmin();
        if(isAdmin){
            AdminRBE.setSelected(true);
            hideERate();    
        }else{
            MechanicRBE.setSelected(true);
            eHRateTF.setText(String.valueOf(((Mechanic)tempUser).getHRate()));
            unhideERate();
        }
    }
    
    private void loadData(ObservableList<Employee> dataList){
        DB.connect();
        ArrayList<Employee> urAList = UR.getActiveUsers();
        dataList.removeAll(dataList);
        if(urAList != null){
            for(Employee ur : urAList){
               dataList.add(ur);
            }
        }
        DB.closeConnection();
    }
    
    private void loadDataSPC(ObservableList<SPC> dataList){
        DB.connect();
        ArrayList<SPC> spcList = SPCReg.getSPCs();
        dataList.removeAll(dataList);
        if(spcList != null){
            for(SPC spc : spcList){
               dataList.add(spc);
            }
        }
        DB.closeConnection();
    }
    
    private void adminSubmission(int ID, String firstName, String lastName, String password){
            boolean addAdmin = UR.addAdmin(ID,password,lastName,firstName);
            if(addAdmin){
                addUserStatus.setText("Successful");
                addUserStatus.setFill(Color.GREEN);
                clearUserDetails(new ActionEvent());
            }else{
                addUserStatus.setText("Admin already exists.");
                addUserStatus.setFill(Color.RED);
                clearUserDetails(new ActionEvent());
            }   
    }
    
    private void userSubmission(int ID, String firstName, String lastName, String password, double hRate){
            boolean addUser = UR.addUser(ID, password, lastName, firstName, hRate);
            if(addUser){
                addUserStatus.setText("Successful");
                addUserStatus.setFill(Color.GREEN);
                clearUserDetails(new ActionEvent());
            }else{
                addUserStatus.setText("Users already exists.");
                addUserStatus.setFill(Color.RED);
                clearUserDetails(new ActionEvent());
            }   
    }
    
    public void clearUserDetails(ActionEvent evt){
        addIDTF.clear();
        addFNTF.clear();
        addLNTX.clear();
        addPassTF.clear();
        addHRateTF.clear();
        addHRateTF.setVisible(true);
        try{
            RadioButton toggleResult = (RadioButton) userRights.getSelectedToggle();
            toggleResult.setSelected(false);
        }catch(Exception e){}
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    addUserStatus.setText("");
                }
            }, 
            1500
        );
    }
    
    private boolean validateID(String ID, TextField tf){
        if(!checkNumeric(ID)){
            tf.setStyle("-fx-text-inner-color: red;");
            tf.setText("Invalid ID");
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         setColor(tf);
                      }
                 }, 
                1500
            );
            return false;
        }else if(ID.length() > 5){
            tf.setStyle("-fx-text-inner-color: red;");
            tf.setText("MAX 5 Digits");
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         setColor(tf);
                      }
                 }, 
                1500
            );
            return false;
        }
        return true;
    }
    
    private boolean checkNumeric(String checkData){
        try{
            long result = Long.parseLong(checkData); 
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private void setColor(TextField tf){
        tf.setStyle("-fx-text-inner-color: black;");
        tf.clear();
    }
    
    private boolean validateTextField(String cData, TextField tf, String fieldName){
        if(cData.equals("")){
            tf.setStyle("-fx-text-inner-color: red;");
            tf.setText("Invalid " + fieldName);
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         setColor(tf);
                      }
                 }, 
                1500
            );
            return false;
        }
        return true;
    }
    
    private boolean validateRate(String rate, TextField tf){
        if(!checkRate(rate)){
            tf.setStyle("-fx-text-inner-color: red;");
            tf.setText("Invalid Rate");
            new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         setColor(tf);
                      }
                 }, 
                1500
            );
            return false;
        }
        return true;
    }
    
    public boolean checkRate(String r){
        try{
            double rate = Double.parseDouble(r);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private String validateUserType(ToggleGroup tGroup){
        try{
            RadioButton toggleResult = (RadioButton) tGroup.getSelectedToggle();
            if(toggleResult.getText().equals("Admin")){
                return "Admin";
            }else{
                return "Mechanic";
            }        
        }catch(NullPointerException e){
                noToggleSelected();
                return null;
        }
    }
    
    private void noToggleSelected(){
        Text status;
        status = addUserStatus;
        status.setFill(Color.RED);
        status.setText("Select user rigths");
        new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         status.setText("");
                      }
                 }, 
                1500 
            );
    }
    
    private void hideERate(){
        eHRateTF.setVisible(false);
    }
    
    private void unhideERate(){
        eHRateTF.setVisible(true);
    }
    
    public void hideRateE(ActionEvent evt){
        eHRateTF.setVisible(false);
    }
    
    public void unhideRateE(ActionEvent evt){
        eHRateTF.setVisible(true);
    }
    
    public void hideRate(ActionEvent evt){
            addHRateTF.setVisible(false);
    }
    
    public void unhideRate(ActionEvent evt){
        addHRateTF.setVisible(true);
    }

    private void adminSubmissionOnEdit(int ID, String firstName, String lastName, String pass) {
        boolean editAdmin = UR.editAdmin(ID,pass,lastName,firstName,true,ID);
        if(editAdmin){
            editUserStatus.setText("Successful");
            editUserStatus.setFill(Color.GREEN);
            clearUserDetailsOnEdit(new ActionEvent());
        }else{
            editUserStatus.setText("Admin already exists.");
            editUserStatus.setFill(Color.RED);
            clearUserDetailsOnEdit(new ActionEvent());
        } 
    }

    private void userSubmissionOnEdit(int ID, String firstName, String lastName, String pass, double hRate) {
        boolean editUser = UR.editUser(ID, pass, lastName, firstName, hRate,false,ID);
        if(editUser){
            editUserStatus.setText("Successful");
            editUserStatus.setFill(Color.GREEN);
            clearUserDetailsOnEdit(new ActionEvent());
        }else{
            editUserStatus.setText("Users already exists.");
            editUserStatus.setFill(Color.RED);
            clearUserDetailsOnEdit(new ActionEvent());
        }      
    }
    
    private void clearUserDetailsOnEdit(ActionEvent evt){
        eFNTF.clear();
        eIDTF.clear();
        eLNTF.clear();
        ePassTF.clear();
        eHRateTF.clear();
        addHRateTF.setVisible(true);
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    editUserStatus.setText("");
                }
            }, 
            1500
        );
    }
    
    private boolean validateUserOnEdit(String userType){
        try{
            boolean isAdmin = Boolean.parseBoolean(userType);
            return true;
        }catch(Exception e){
            return false;
        }
    }
    
    private void submitSPC(String spcName, String spcAddress,String spcPhone, String spcEmail){
        boolean addSPC = SPCReg.addSPC(spcName, spcAddress, spcPhone, spcEmail);
        if(addSPC){
            addSPCStatus.setText("Successful");
            addSPCStatus.setFill(Color.GREEN);
            clearSPCDetails(new ActionEvent());
        }else{
            addSPCStatus.setText("SPC already exists.");
            addSPCStatus.setFill(Color.RED);
            clearSPCDetails(new ActionEvent());
        }   
    }
    
    private void submitSPConChange(String spcName, String spcAddress, String spcPhone, String spcEmail,
                                   String oldName, String oldAddress, String oldPhone, String oldEmail){
        boolean editSPC = SPCReg.editSPC(spcName, spcAddress, spcPhone, spcEmail,
                                         oldName, oldAddress, oldPhone, oldEmail);
        if(editSPC){
            editSPCStatus.setText("Successful");
            editSPCStatus.setFill(Color.GREEN);
            clearSPCDetails(new ActionEvent());
        }else{
            editSPCStatus.setText("SPC already exists.");
            editSPCStatus.setFill(Color.RED);
            clearSPCDetails(new ActionEvent());
        }          
    }
}
