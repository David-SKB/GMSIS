/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import customers.logic.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import vehicles.logic.Vehicle;

/**
 *
 * @author jr308
 */
public class viewCustomerController implements Initializable {
    @FXML
    private TextArea cFirstName, cLastName,cAddress, cPostCode, cPhone, cEmail,
            cType;
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void loadCustomerData(Customer c){
        cFirstName.setText(c.getFirstname());
        cLastName.setText(c.getSurname());
        cAddress.setText(c.getAddress());
        cPostCode.setText(c.getPostCode());
        cPhone.setText(c.getPhone());
        cEmail.setText(c.getEmail());
        cType.setText(c.getCustomerType());  
    }
}