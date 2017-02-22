package user.gui;

import common.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import user.logic.Employee;
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
    private TextField addIDTF, addFNTF, addLNTX, ddPassTF, addHRateTF,
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
    private TableColumn firstNameTableC, lastNameTableC, hRateTableC,
                        sysAdmTableC, passTableC;
    @FXML
    private TableView spcTV;
    @FXML
    private TableColumn spcNameTableC, spcAddrTableC, spcEmailTableC, 
                        spcPhoneTableC;
    
    public void getUsers(ActionEvent evt){
        
    }
    
    public void delUser(ActionEvent evt){
    
    }
    
    public void submitUserDetails(ActionEvent etv){
    
    }
    
    public void clearUserDetails(ActionEvent evt){
    
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
}
