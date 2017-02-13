import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    
    
    
}
