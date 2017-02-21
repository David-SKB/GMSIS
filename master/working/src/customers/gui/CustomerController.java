package customers.gui;

import customers.logic.CustomerRegistry;
import common.DBConnection;
import customers.logic.Customer;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    /* ------------------------------------------------------------------
     * TODO - 1) Check if any updates were made and display on stats.
     *        2) Make a validation for email correctness.
     *        3) Add Delete customer option.
     *        4) Check the sqlite db error when building.
     *        5) Start implementing search bar.
     *        7) Start implementing SysAdmin methods LOGIC.
     *        9) Start implementing SysAdmin GUI Controller.
     * ------------------------------------------------------------------ */
/**
 *
 * @author athanasiosgkanos
 */
public class CustomerController {
    
    DBConnection db = DBConnection.getInstance();                                       //Instance of the database to open/close connection
    CustomerRegistry CR = CustomerRegistry.getInstance();                               //Instance of the Customer Registry to add/edit/delete customers from/to db.
    @FXML
    private TableView<Customer> customerDetails;                                        //FXML TableView. Table used to display rows of customers and their data.
    @FXML
    private TableColumn firstNameCol, lastNameCol, addressCol,                          //FXML TableColumn. Columns form the TableView element.
                        postCodeCol, phoneCol, emailCol, cTypeCol;
    @FXML
    private TextField firstNameTextField, lastNameTextField, addressTextField,          //FXML TextFields. Those start with e are for the Edit Customer T.Pane
                      postCodeTextField, phoneTextField, emailTextField,            
                      eFirstNameTextField, eLastNameTextField, eAddrTextField, 
                      ePostCodeTextField, ePhoneTextField, eEmailTextField;
    @FXML
    private ToggleGroup customerTypeToggle, eCustomerTypeToggle;                        //FXML ToggleGroup. First when adding a customer and second when editing.
    @FXML
    private Toggle eIndividualRadioButton, eBusinessRadionButton;                       //FXML Toggle. RadioButtons to identify if the customer type has been edited.
    @FXML
    private Text statusText, eStatusText;                                               //FXML Text. Display progress/erros when adding/editing customers.
    private final ObservableList<Customer> data = FXCollections.observableArrayList();  //FXML ObservableList. List that allows listeners to tack changes when occur.
    @FXML
    private ChoiceBox<Customer> delCustomersCBox = new ChoiceBox<>();
    private Customer tempCustomer;                                                      //Temporary Customer object used when editing its data from the list.

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
        delCustomersCBox.setItems(data);
        
        customerDetails.setRowFactory((TableView<Customer> tv) -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                     tempCustomer = row.getItem();
                     loadOnEditPane();
                }
            });
            return row;
        });

    }
    
    /* ------------------------------------------------------------------
     *  Method that handles the OnAction event of the submit button in
     *  the Add Customer Titled Pane. It takes customer information from
     *  the T.Pane, checks if they are valid and then adds a new customer
     *  in the database. (Prints success/fail upon completion).
     * ------------------------------------------------------------------ */        
    public void submitCustomerDetails(ActionEvent evt){
        boolean fValid,
                lValid,
                addrValid,
                postCValid,
                phoneValid,
                emailValid = true;
        
        String tempFName = firstNameTextField.getText(); 
        fValid = validateTextField(tempFName,firstNameTextField, "First Name");
                
        String tempLName = lastNameTextField.getText();
        lValid = validateTextField(tempLName,lastNameTextField, "Last Name");
        
        String tempAddr = addressTextField.getText();    
        addrValid = validateTextField(tempAddr,addressTextField, "Address");
        
        String tempPostC = postCodeTextField.getText();
        postCValid = validateTextField(tempPostC,postCodeTextField, "Post Code");
        
        String tempPhone = phoneTextField.getText(); 
        phoneValid = validatePhone(tempPhone, phoneTextField);
        
        String tempCType = validateCType(customerTypeToggle);
        
        String tempEmail = emailTextField.getText();
        emailValid = validateTextField(tempEmail,emailTextField, "Email");

        if(fValid && lValid && addrValid && postCValid && phoneValid && tempCType != null &&emailValid){
            submission(tempLName,tempFName,tempAddr,tempPostC,tempPhone,tempEmail,tempCType);
        }
    }
    
    /* ------------------------------------------------------------------------
     *  Method that handles the OnAction event of the submit changes button in
     *  the Edit Customer Titled Pane. It takes the edited customer data from
     *  the T.Pane, checks if they are valid, and calls the editCustomer 
     *  method from the registry to modify the data of a particular Customer 
     *  in the db.
     * ----------------------------------------------------------------------*/ 
    public void submitCustomerChanges(){
        boolean eFValid,
                eLValid,
                eAddrValid,
                ePostCValid,
                ePhoneValid,
                eEmailValid = true; 
        
        String fName = eFirstNameTextField.getText();
        eFValid = validateTextField(fName,eFirstNameTextField, "First Name");
        
        String lName = eLastNameTextField.getText();
        eLValid = validateTextField(lName,eLastNameTextField, "Last Name");
        
        String addr = eAddrTextField.getText();
        eAddrValid = validateTextField(addr,eAddrTextField, "Address");
        
        String postC = ePostCodeTextField.getText();
        ePostCValid = validateTextField(fName,ePostCodeTextField, "PostCode");
        
        String phone = ePhoneTextField.getText();
        ePhoneValid = validatePhone(phone, ePhoneTextField);
        
        String email = eEmailTextField.getText();
        eEmailValid = validateTextField(email,eEmailTextField, "Email");
        
        String cType = validateCType(eCustomerTypeToggle);
        
        if(eFValid && eLValid && eAddrValid && ePostCValid && ePhoneValid && eEmailValid){
            db.connect();
                boolean result = CR.editCustomer(lName, fName, addr, postC, phone, email, cType,tempCustomer.getPhone(),tempCustomer.getEmail());
            db.closeConnection();
            if(result){
                eStatusText.setText("Successful");
                eStatusText.setFill(Color.GREEN);
                clearCustomerDetailsOnEdit(new ActionEvent());
                getActiveCustomers(new ActionEvent());
            } 
        }
    }
    
    public void deleteCustomer(ActionEvent evt){
    
    }
    
    /* ------------------------------------------------------------------
     * HELPER -- Method loads the data on the Edit Customer T.Pane
     * from the Customer that was double-clicked on the list
     * ------------------------------------------------------------------ */  
    private void loadOnEditPane(){
        eFirstNameTextField.setText(tempCustomer.getFirstname());
        eLastNameTextField.setText(tempCustomer.getSurname());
        eAddrTextField.setText(tempCustomer.getAddress());
        ePostCodeTextField.setText(tempCustomer.getPostCode());
        ePhoneTextField.setText(tempCustomer.getPhone());
        eEmailTextField.setText(tempCustomer.getEmail());
        String cType = tempCustomer.getCustomerType();
        if(cType.equals("Individual")){
            eCustomerTypeToggle.selectToggle(eIndividualRadioButton);
        }else{
            eCustomerTypeToggle.selectToggle(eBusinessRadionButton);
        }
    }
    
    /* ------------------------------------------------------------------
     * HELPER -- Method submits a new customer calling the 
     * CustomerRegistry. It also indicates the success/failure of the
     * procedure.
     * ------------------------------------------------------------------ */  
    private void submission
        (String tempLName, String tempFName, String tempAddr, String tempPostC, String tempPhone, String tempEmail, String tempCType){
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
        try{
            RadioButton toggleResult = (RadioButton) customerTypeToggle.getSelectedToggle();
            toggleResult.setSelected(false);
        }catch(Exception e){}
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    statusText.setText("");
                }
            }, 
            1500
        );
    }
    
    
    /* ------------------------------------------------------------------
     * HELPER -- Method clears all the fields and radio buttons when 
     * the Submit Changes button is press and triggers the 
     * SubmitCustomerChanges method.
     * ------------------------------------------------------------------ */  
    public void clearCustomerDetailsOnEdit(ActionEvent evt){
        eFirstNameTextField.clear();
        eLastNameTextField.clear();
        eAddrTextField.clear();
        ePostCodeTextField.clear();
        ePhoneTextField.clear();
        eEmailTextField.clear();
        try{
            RadioButton toggleResult = (RadioButton) eCustomerTypeToggle.getSelectedToggle();
            toggleResult.setSelected(false);
        }catch(Exception e){}
        new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    eStatusText.setText("");
                }
            }, 
            1500
        );
    } 
    
    /* ------------------------------------------------------------------
     * HELPER -- Method called when a RadioButton has not been selected
     * from the ToggleGroup. It displayes an error message on the
     * statusText Text element at the bottomo of the T.Pane (red color).
     * ------------------------------------------------------------------ */  
    private void noToggleSelected(){
        statusText.setFill(Color.RED);
        statusText.setText("Please select a customer type");
        new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         statusText.setText("");
                      }
                 }, 
                1500 
            );
    }
    
    /* ------------------------------------------------------------------
     * HELPER -- Method checks if a value has been selected from the
     * ToggleGroup and if yes it returns its String value. If not it 
     * calls the helper method noToggleSelected to handle it.
     * ------------------------------------------------------------------ */  
    private String validateCType(ToggleGroup tGroup){
        try{
          RadioButton toggleResult = (RadioButton) tGroup.getSelectedToggle();
          if(toggleResult.getText().equals("Individual")){
               return "Individual";
           }else{
               return "Business";
           }
                  
        }catch(NullPointerException e){
                noToggleSelected();
                return null;
        }
    }
    
    /* ------------------------------------------------------------------
     * HELPER -- Changes the color of the argument TextField.
     * ------------------------------------------------------------------ */  
    private void setColor(TextField tf){
        tf.setStyle("-fx-text-inner-color: black;");
        tf.clear();
    }
    
    /* ------------------------------------------------------------------
     * HELPER -- Method checks if the data argument is empty and 
     * if it is, it displays error on the corresponding TextField.
     * ------------------------------------------------------------------ */  
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
    
    /* ------------------------------------------------------------------
     * HELPER -- Method checks if the phone argument is valid and 
     * if not displays error on TextField (red color).
     * ------------------------------------------------------------------ */   
    private boolean validatePhone(String phone, TextField tf){
        if(!checkNumeric(phone)){
            tf.setStyle("-fx-text-inner-color: red;");
            tf.setText("Invalid Number");
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
    
    /* ------------------------------------------------------------------
     * HELPER -- Method takes a list with all active customers from the
     * db and adds them to the ObservableList. (used to display contents)
     * ------------------------------------------------------------------ */
    private void loadData(ObservableList<Customer> dataList){
        db.connect();
        ArrayList<Customer> csAList = CR.getActiveCustomers();
        dataList.removeAll(dataList);
        if(csAList != null){
            for(Customer c : csAList){
               dataList.add(c);
            }
        }
        db.closeConnection();
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
