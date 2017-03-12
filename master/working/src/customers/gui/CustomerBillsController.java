/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers.gui;

import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerBillsController {
    
    @FXML
    private TextField firstNameTF, lastNameTF, addressTF,cTypeTF,          
                      pCodeTF, phoneTF, emailTF;   
    
    CustomerRegistry CR = CustomerRegistry.getInstance();
    Customer tempCust;
    
    public void setUser(int ID){
        this.tempCust = CR.searchCustomerByID(String.valueOf(ID));
        setUserDetails();
    }
    
    private void setUserDetails(){
        firstNameTF.setText(tempCust.getFirstname());
        lastNameTF.setText(tempCust.getSurname());
        addressTF.setText(tempCust.getAddress());
        cTypeTF.setText(tempCust.getCustomerType());
        pCodeTF.setText(tempCust.getPostCode());
        phoneTF.setText(tempCust.getPhone());
        emailTF.setText(tempCust.getEmail());
    }
}
