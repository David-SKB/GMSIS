/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*; 

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerRegistry {
    DBConnection DBInstance = DBConnection.getInstance();
    private static CustomerRegistry CRInstance = null;
    
    private CustomerRegistry(){    
    }
    
    public static CustomerRegistry getInstance(){
        if(CRInstance == null){
            CRInstance = new CustomerRegistry();
        }
        return CRInstance;
    }
    
    public boolean addCustomer(String fullName, String address, String postcode, String phone, String email, String customerType){
        DBInstance.connect();
        boolean found = false;
        
        if(found){
            return false;
        }else{
            String query = "INSERT INTO CUSTOMER (FULLNAME, ADDRESS, POSTCODE, PHONE, EMAIL, CUSTOMERTYPE) " + 
                           "VALUES ( '" + 
                            fullName + "', '" +
                            address + "', '" +
                            postcode + "', '" +
                            phone + "', '" + 
                            email + "', '" + 
                            customerType + "' );";
                            DBInstance.update(query);
        }
        DBInstance.closeConnection();
        return true;
    }
    
    public boolean editCustomer(Customer cust){
        return false;
    }
    
    public boolean deleteCustomer(Customer cust){
        return false;
    }
    
    public List<Customer> getActiveCustomers(){
        return new ArrayList<Customer>();
    }
}
