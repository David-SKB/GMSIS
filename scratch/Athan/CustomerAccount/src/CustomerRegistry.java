/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.ResultSet;
import java.sql.SQLException;
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
        boolean success;
        DBInstance.connect();       
        String query = "INSERT INTO CUSTOMER (FULLNAME, ADDRESS, POSTCODE, PHONE, EMAIL, CUSTOMERTYPE) " + 
                           "VALUES ( '" + 
                            fullName + "', '" +
                            address + "', '" +
                            postcode + "', '" +
                            phone + "', '" + 
                            email + "', '" + 
                            customerType + "' );";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
    
    public boolean editCustomer(String fullName, String address, String postcode, String phone, String email, String customerType){
        boolean success;
        DBInstance.connect();
        String query  = "UPDATE CUSTOMER \n" + 
                        "SET " +
                        "FULLNAME = '" + fullName + "', " +
                        "ADDRESS = '" + address + "', " +
                        "POSTCODE = '" + postcode + "', " +
                        "PHONE = '" + phone + "', " + 
                        "EMAIL = '" + email + "', " +
                        "CUSTOMERTYPE = '" + customerType + "'\n" +
                        "WHERE PHONE = '" + phone + "' \n" + 
                        "AND EMAIL = '" + email + "';";
        success = DBInstance.update(query);
        return success;
    }
    
    public boolean deleteCustomer(String fullName, String address, String postcode, String phone, String email, String customerType){
        boolean success;
        DBInstance.connect();
        String query = "DELETE FROM CUSTOMER\n" +
                       "WHERE PHONE = '" + phone + "'\n" +
                       "AND EMAIL = '" + email + "'; ";
        success = DBInstance.update(query);
        return success;
    }
    
    public ArrayList<Customer> getActiveCustomers(){
        try{
            ArrayList<Customer> activeCustomers = new ArrayList<Customer>();
            ResultSet rs;
            DBInstance.connect();
            String query = "SELECT * FROM CUSTOMER;";
            rs = DBInstance.query(query);
            
            while(rs.next()){
                String fullName = rs.getString("FULLNAME");
                String address = rs.getString("ADDRESS");
                String postCode = rs.getString("POSTCODE");
                String phone = rs.getString("PHONE");
                String email = rs.getString("EMAIL");
                String customerType = rs.getString("CUSTOMERTYPE");
                activeCustomers.add(new Customer(fullName,address,postCode,phone,email,customerType));
            }
            return activeCustomers;
        }catch(SQLException e){
            return null;
        }
    }
}
