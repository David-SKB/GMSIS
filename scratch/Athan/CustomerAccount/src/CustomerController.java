import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerController {
    
    DBConnection db = DBConnection.getInstance();
    CustomerRegistry CR = CustomerRegistry.getInstance();
    @FXML
    private AnchorPane customerTab;
    @FXML
    private Button getActiveCustomers;
    @FXML
    private TableView<Customer> customerDetails;
    @FXML
    private TableColumn firstNameCol;
    @FXML
    private TableColumn lastNameCol;
    @FXML
    private TableColumn addressCol;
    @FXML
    private TableColumn postCodeCol;
    @FXML
    private TableColumn phoneCol;
    @FXML
    private TableColumn emailCol;
    @FXML
    private TableColumn cTypeCol;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postCodeTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private ToggleGroup customerTypeToggle;
    private final ObservableList<Customer> data = FXCollections.observableArrayList();

 
    public void getActiveCustomers(ActionEvent event){
        loadData(data);
        customerDetails.setEditable(true);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("firstname"));
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("surname"));
        addressCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("address"));
        postCodeCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("postCode"));
        phoneCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("phone"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("email"));
        cTypeCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("customerType"));
        customerDetails.setItems(data);
    }
    
    private void loadData(ObservableList<Customer> dataList){
        db.connect();
        ArrayList<Customer> csAList = CR.getActiveCustomers();
        
        if(csAList != null){
            for(Customer c : csAList){
               dataList.add(c);
            }
        }
        
        db.closeConnection();
    }
    
    public void submitCustomerDetails(ActionEvent evt){
        String tempFName = firstNameTextField.getText();
        String tempLName = lastNameTextField.getText();
        String tempAddr = addressTextField.getText();
        String tempPostC = postCodeTextField.getText();
        String tempPhone = phoneTextField.getText();
        RadioButton toggleResult = (RadioButton) customerTypeToggle.getSelectedToggle();
        String tempCType;
        if(toggleResult.getText().equals("Individual")){
            tempCType = "Individual";
        }else{
            tempCType = "Business";
        }
        System.out.println(tempFName + " " + 
                           tempLName + " " +
                           tempAddr + " " +
                           tempPostC + " " +
                           tempPhone + " " +
                           tempCType);
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        postCodeTextField.clear();
        phoneTextField.clear();
        toggleResult.setSelected(false);
    }
    
    public void clearCustomerDetails(ActionEvent evt){
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        postCodeTextField.clear();
        phoneTextField.clear();
        RadioButton toggleResult = (RadioButton) customerTypeToggle.getSelectedToggle();
        toggleResult.setSelected(false);
    }
}
