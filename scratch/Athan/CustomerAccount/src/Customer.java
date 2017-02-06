/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.List;

/**
 *
 * @author athanasiosgkanos
 */
public class Customer {
    private String fullName;
    private String address;
    private String postCode;
    private String phone;
    private String email;
    private String customerType;
    //private List<Booking> bkLists;
    
    public Customer(String fName, String addr, String pCode, String phone, String email, String cType){
        this.fullName = fName;
        this.address = addr;
        this.postCode = pCode;
        this.phone = phone;
        this.email = email;
        this.customerType = cType;
    }
    
    //public List<Booking> viewBookings(){
        
    //}
    
    public String getName(){
        return fullName;
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getPostCode(){
        return postCode;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getCustomerType(){
        return customerType;
    }
}
