import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
    private TextField emailTextField;
    @FXML
    private ToggleGroup customerTypeToggle;
    @FXML
    private Text statusText;
    private final ObservableList<Customer> data = FXCollections.observableArrayList();

    /* ------------------------------------------------------------------
     * Method handles the OnAction event of getActiveCustomers
     * button. Returns all the active customers from the db
     * and displayes them on the Cells of the customerDetails TableView
     * ------------------------------------------------------------------ */
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
    
    /* ------------------------------------------------------------------
     * HELPER -- Method takes a list with all active customers from the
     * db and adds them to the ObservableList. (used to display contents)
     * ------------------------------------------------------------------ */
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
    
    /* ------------------------------------------------------------------
     *  Method that handles the OnAction event of the submit button in
     *  the Add Customer Titled Pane. It takes customer information from
     *  the T.Pane, checks if they are valid and then adds a new customer
     *  in the database. (Prints success/fail upon completion).
     * ------------------------------------------------------------------ */        
    public void submitCustomerDetails(ActionEvent evt){
        boolean success = false;
        String tempFName = firstNameTextField.getText(); //HANDLE WHEN EMPTY
        String tempLName = lastNameTextField.getText(); //HANDLE WHEN EMPTY
        String tempAddr = addressTextField.getText();       
        String tempPostC = postCodeTextField.getText();
        String tempPhone = phoneTextField.getText();
        if(checkNumeric(phoneTextField.getText())){
            success = true;
        }else{
            phoneTextField.setText("Invalid Number");                                   
        }
        String tempEmail = emailTextField.getText();
        RadioButton toggleResult = (RadioButton) customerTypeToggle.getSelectedToggle();//HANDLE WHEN NOT SELECTED
        String tempCType;
        if(toggleResult.getText().equals("Individual")){
            tempCType = "Individual";
        }else{
            tempCType = "Business";
        }
        if(success){
            boolean addCustomer = CR.addCustomer(tempLName,tempFName,tempAddr,tempPostC,tempPhone,tempEmail,tempCType);
            if(addCustomer){
                statusText.setText("Successful");
                statusText.setFill(Color.GREEN);
                clearCustomerDetails(new ActionEvent());
            }else{
                statusText.setText("Customer already exists.");
                statusText.setFill(Color.RED);
                clearCustomerDetails(new ActionEvent());
            }
        }
    }
    
    
    /* ------------------------------------------------------------------
     * Method handles OnAction event of the Clear button in the 
     * Add Cutomer Titled Pane. When the button is pressed, this method
     * will clear all the TextFields and Toggles appearing in the 
     * Titled Pane. (The status text is cleared after 1s.)
     * ------------------------------------------------------------------ */ 
    public void clearCustomerDetails(ActionEvent evt){
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        postCodeTextField.clear();
        phoneTextField.clear();
        emailTextField.clear();
        RadioButton toggleResult = (RadioButton) customerTypeToggle.getSelectedToggle();
        toggleResult.setSelected(false);
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    statusText.setText("");
                }
            }, 
            1000 
        );
    }
     
    /* ------------------------------------------------------------------
     * HELPER -- Method takes a String type parameter and tries to cast
     * it into primitive type long. If it throws exception it returns 
     * false indicating that the argument cannot be converted in numeric.
     * ------------------------------------------------------------------ */
    private boolean checkNumeric(String checkData){
        try{
            long result = Long.parseLong(checkData); 
            return true;
        }catch(Exception e){
            return false;
        }
    }
}
