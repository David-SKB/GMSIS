package customers.gui;

import customers.logic.CustomerRegistry;
import common.DBConnection;
import customers.logic.Customer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    /* ------------------------------------------------------------------
     * TODO - 4) Check the sqlite db error when building.
     *        5) Start implementing search bar.
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
                      ePostCodeTextField, ePhoneTextField, eEmailTextField,
                      searchBar;
    @FXML
    private ToggleGroup customerTypeToggle, eCustomerTypeToggle;                        //FXML ToggleGroup. First when adding a customer and second when editing.
    @FXML
    private Toggle eIndividualRadioButton, eBusinessRadionButton;                       //FXML Toggle. RadioButtons to identify if the customer type has been edited.
    @FXML
    private Text statusText, eStatusText, delStatus;                                    //FXML Text. Display progress/erros when adding/editing customers.
    private final ObservableList<Customer> obsListData = FXCollections.observableArrayList();  //FXML ObservableList. List that allows listeners to tack changes when occur.
    @FXML
    private ChoiceBox<Customer> delCustomersCBox;
    @FXML
    private ChoiceBox<Customer> showVehiclesCBox;
    @FXML 
    private ChoiceBox<Customer> showBillCBox;
    private Customer tempCustomer;                                                      //Temporary Customer object used when editing its data from the list.

    public CustomerController() {
        this.showBillCBox = new ChoiceBox<>();
        this.showVehiclesCBox = new ChoiceBox<>();
        this.delCustomersCBox = new ChoiceBox<>();
    }

    /* ------------------------------------------------------------------
     * Method handles the OnAction event of getActiveCustomers
     * button. Returns all the active customers from the db
     * and displayes them on the Cells of the customerDetails TableView
     * ------------------------------------------------------------------ */
    public void getActiveCustomers(ActionEvent event){
        loadData(obsListData);
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
        customerDetails.setItems(obsListData);
        delCustomersCBox.setItems(obsListData);
        showVehiclesCBox.setItems(obsListData);
        showBillCBox.setItems(obsListData);
        
        
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
        
        String tempCType = validateCType(customerTypeToggle,"OnAdd");
        
        String tempEmail = emailTextField.getText();
        emailValid = validateTextField(tempEmail,emailTextField, "Email");

        if(fValid && lValid && addrValid && postCValid && phoneValid && tempCType != null &&emailValid){
            submission(tempLName,tempFName,tempAddr,tempPostC,tempPhone,tempEmail,tempCType);
            getActiveCustomers(new ActionEvent());
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
        
        String cType = validateCType(eCustomerTypeToggle,"OnEdit");
        
        if(eFValid && eLValid && eAddrValid && ePostCValid && ePhoneValid && eEmailValid){
            db.connect();
                boolean changed = checkIfChanged(tempCustomer,lName, fName, addr, postC, phone, email, cType);
                if(changed){
                    boolean result = CR.editCustomer(lName, fName, addr, postC, phone, email, cType,tempCustomer.getPhone(),tempCustomer.getEmail());
                    if(result){
                        eStatusText.setText("Successful");
                        eStatusText.setFill(Color.GREEN);
                        clearCustomerDetailsOnEdit(new ActionEvent());
                        getActiveCustomers(new ActionEvent());
                    }
                }else{
                    eStatusText.setText("Nothing to update.");
                    eStatusText.setFill(Color.RED);
                    clearCustomerDetailsOnEdit(new ActionEvent());
                }
            db.closeConnection();
 
        }
    }
    
    public void deleteCustomer(ActionEvent evt){
        Customer c = delCustomersCBox.getSelectionModel().getSelectedItem();
        if(c != null){
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Deleting Customer");
            alert.setHeaderText("Are you sure you want to delete this customer?");
            alert.setContentText("Customer Details: " + c.getFirstname() + " " + c.getSurname() + 
                    " " + c.getPhone() + " " + c.getEmail());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                db.connect();
                String sName = c.getSurname();
                String fName = c.getFirstname();
                String phone = c.getPhone();
                String email = c.getEmail();
                CR.deleteCustomer(sName, fName, phone, email);
                db.closeConnection();
                getActiveCustomers(new ActionEvent());
            } else {
                getActiveCustomers(new ActionEvent());
            }
        }else{
            delStatus.setText("Please select a Customer for deletion.");
            delStatus.setFill(Color.RED);
            new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    delStatus.setText("");
                }
            }, 
            2000
        );
        }
    }
    
    private boolean checkIfChanged
        (Customer oldC, String lName, String fName, String addr, String postC, 
                    String phone, String email, String cType){
            
            if(oldC.getSurname().equalsIgnoreCase(lName)    &&
               oldC.getFirstname().equalsIgnoreCase(fName)  &&
               oldC.getAddress().equalsIgnoreCase(addr)     &&
               oldC.getPostCode().equalsIgnoreCase(postC)   &&
               oldC.getPhone().equalsIgnoreCase(phone)      &&
               oldC.getEmail().equalsIgnoreCase(email)      &&
               oldC.getCustomerType().equalsIgnoreCase(cType)){
                return false;
            }else{
                return true;
            }
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
        eEmailTextField.setAlignment(Pos.CENTER_LEFT);
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
   
    public void logoutCustomer(ActionEvent evt){
        System.exit(0);
    }
    
    public void searchCustomerBar(ActionEvent evt){
        String inputData = searchBar.getText();
        String[] data = inputData.split("\\s+");
        if(data.length == 2){
            getActiveCustomers(new ActionEvent());
            String sName = data[0].substring(0,1).toUpperCase() + data[0].substring(1);
            String fName = data[1].substring(0,1).toUpperCase() + data[1].substring(1);
            boolean found = loadSearchedData(obsListData,sName,fName);
            if(found){
                customerDetails.setItems(obsListData);
                delCustomersCBox.setItems(obsListData);
                showVehiclesCBox.setItems(obsListData);
                showBillCBox.setItems(obsListData);
            }else{
                obsListData.removeAll(obsListData);
                searchBar.setText("");
                searchBar.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
                new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                    public void run() {
                        searchBar.setStyle("");
                    }
                }, 
                1500
                );
                }                
        }else{
            searchBar.setText("");
            searchBar.setStyle("-fx-border-color: red ; -fx-border-width: 1px ;");
            new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    searchBar.setStyle("");
                }
            }, 
            1500
            );
        }        
    }
    
    public void showCustomerVehicles(ActionEvent evt){
        Customer c = showVehiclesCBox.getSelectionModel().getSelectedItem();
        if(c != null){
            Parent root;
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./CustomerVehicles.fxml"));
                root = (Parent)fxmlLoader.load();
                CustomerVehiclesController CVC = fxmlLoader.<CustomerVehiclesController>getController();
                int ID = CR.getCustomerID(c.getPhone(), c.getEmail());
                if(ID != -1){
                    CVC.setUser(ID);
                }
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Customer's Vehicle");
                stage.show();
            }catch(IOException e){}
        }else{
            delStatus.setText("Please select a Customer to view Vehicles.");
            delStatus.setFill(Color.RED);
            new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    delStatus.setText("");
                }
            }, 
            2000
            );
        }
    }
    
    public void showCustomerBills(ActionEvent evt){
        Customer c = showBillCBox.getSelectionModel().getSelectedItem();
        if(c != null){
            Parent root;
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("./CustomerBills.fxml"));
                root = (Parent)fxmlLoader.load();
                CustomerBillsController CVC = fxmlLoader.<CustomerBillsController>getController();
                int ID = CR.getCustomerID(c.getPhone(), c.getEmail());
                if(ID != -1){
                    CVC.setUser(ID);
                }
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Customer's Bill");
                stage.show();
            }catch(IOException e){}
        }else{
            delStatus.setText("Please select a Customer to view Bills.");
            delStatus.setFill(Color.RED);
            new java.util.Timer().schedule( 
            new java.util.TimerTask() {
                public void run() {
                    delStatus.setText("");
                }
            }, 
            2000
            );
        }
    }
    
    /* ------------------------------------------------------------------
     * HELPER -- Method called when a RadioButton has not been selected
     * from the ToggleGroup. It displayes an error message on the
     * statusText Text element at the bottomo of the T.Pane (red color).
     * ------------------------------------------------------------------ */  
    private void noToggleSelected(String TPane){
        Text status;
        if(TPane.equals("OnAdd")){
            status = statusText;
        }else{
            status = eStatusText;
        }
        status.setFill(Color.RED);
        status.setText("Please select a customer type");
        new java.util.Timer().schedule( 
                new java.util.TimerTask() {
                      public void run() {
                         status.setText("");
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
    private String validateCType(ToggleGroup tGroup, String TPane){
        try{
          RadioButton toggleResult = (RadioButton) tGroup.getSelectedToggle();
          if(toggleResult.getText().equals("Individual")){
               return "Individual";
           }else{
               return "Business";
           }
                  
        }catch(NullPointerException e){
                noToggleSelected(TPane);
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
        }else if(fieldName.equals("Email")){
            Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            Matcher mat = pattern.matcher(cData);
            if(mat.matches()){
                return true;
            }else{
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
        ArrayList<Customer> csAList = CR.getActiveCustomers();
        dataList.removeAll(dataList);
        if(csAList != null){
            for(Customer c : csAList){
               dataList.add(c);
            }
        }
    }
    
    private boolean loadSearchedData(ObservableList<Customer> dataList, String sName, String fName){
        ArrayList<Customer> csAList = CR.searchCustomerWithName(sName,fName);
        dataList.removeAll(dataList);
        if(csAList != null &&
           !csAList.isEmpty()){
            for(Customer c : csAList){
                dataList.add(c);
            }
            return true;
        }
        return false;
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
