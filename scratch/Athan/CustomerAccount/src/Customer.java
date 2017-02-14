/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author athanasiosgkanos
 */
public class Customer {
    private final SimpleStringProperty surname;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty address;
    private final SimpleStringProperty postCode;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty email;
    private final SimpleStringProperty customerType;
    
    
    public Customer(String sName, String fName, String addr, String pCode, String phone, String email, String cType){
        this.surname = new SimpleStringProperty(sName);
        this.firstname = new SimpleStringProperty(fName);
        this.address = new SimpleStringProperty(addr);
        this.postCode = new SimpleStringProperty(pCode);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.customerType = new SimpleStringProperty(cType);
    }
    
    
    public String getSurname(){
        return surname.get();
    }
    
    public String getFirstname(){
        return firstname.get();
    }
    
    public String getAddress(){
        return address.get();
    }
    
    public String getPostCode(){
        return postCode.get();
    }
    
    public String getPhone(){
        return phone.get();
    }
    
    public String getEmail(){
        return email.get();
    }
    
    public String getCustomerType(){
        return customerType.get();
    }
}
