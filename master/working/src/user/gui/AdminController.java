package user.gui;

import common.DBConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import user.logic.UserRegistry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author athanasiosgkanos
 */
public class AdminController {
    DBConnection DB = DBConnection.getInstance();
    UserRegistry UR = UserRegistry.getInstance();
    @FXML
    private TextField addIDTF, addFNTF, addLNTX, addPassTF, addHRateTF,
                      eFNTF, eIDTF, eLNTF, ePassTF, eHRateTF;
    @FXML
    private ToggleGroup userRights;
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
    private final ObservableList<Employee> data = FXCollections.observableArrayList();
    private Employee tempUser;
    
    public void getUsers(ActionEvent evt){
        loadData(data);
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
        userTV.setItems(data);
        
        userTV.setRowFactory((TableView<Employee> tv) -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempUser = row.getItem();
                }
            });
            return row;
        });
    }
    
    public void delUser(ActionEvent evt){
        if(tempUser != null){
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
        boolean IDValid = true;
        boolean fnValid = true;
        boolean lnValid = true;
        boolean passValid = true;
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
        
        String tempCType = validateUserType(userRights,"OnAdd");
        
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
    
    }
    
    public void getSPCs(ActionEvent evt){
    
    }
    
    public void deleteSPC(ActionEvent evt){
    
    }
    
    public void submitSPCDetails(ActionEvent evt){
    
    }
    
    public void clearSPCDetails(ActionEvent evt){
    
    }
    
    public void submitSPCChanges(ActionEvent evt){
    
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
    
    private String validateUserType(ToggleGroup tGroup, String TPane){
        try{
          RadioButton toggleResult = (RadioButton) tGroup.getSelectedToggle();
          if(toggleResult.getText().equals("Admin")){
               return "Admin";
           }else{
               return "Mechanic";
           }
                  
        }catch(NullPointerException e){
                noToggleSelected(TPane);
                return null;
        }
    }
    
    private void noToggleSelected(String TPane){
        Text status;
        if(TPane.equals("OnAdd")){
            status = addUserStatus;
        }else{
            status = editUserStatus;
        }
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
    
    public void hideRate(ActionEvent evt){
        addHRateTF.setVisible(false);
    }
    
    public void unhideRate(ActionEvent evt){
        addHRateTF.setVisible(true);
    }
}
