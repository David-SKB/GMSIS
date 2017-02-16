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
    
    public boolean addCustomer(String sName, String fName, String address, String postcode, String phone, String email, String customerType){
        boolean success;
        DBInstance.connect();       
        String query = "INSERT INTO CUSTOMER (SURNAME, FIRSTNAME, ADDRESS, POSTCODE, PHONE, EMAIL, CUSTOMERTYPE) " + 
                           "VALUES ( '" + 
                            sName + "', '" +
                            fName + "', '" +
                            address + "', '" +
                            postcode + "', '" +
                            phone + "', '" + 
                            email + "', '" + 
                            customerType + "' );";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
    
    public boolean editCustomer
            (String sName, String fName, String address, String postcode, String phone, String email, String customerType, String oldP, String oldEmail){
        boolean success;
        DBInstance.connect();
        String query  = "UPDATE CUSTOMER \n" + 
                        "SET " +
                        "SURNAME = '" + sName + "', " +
                        "FIRSTNAME = '" + fName + "', " +
                        "ADDRESS = '" + address + "', " +
                        "POSTCODE = '" + postcode + "', " +
                        "PHONE = '" + phone + "', " + 
                        "EMAIL = '" + email + "', " +
                        "CUSTOMERTYPE = '" + customerType + "'\n" +
                        "WHERE PHONE = '" + oldP + "' \n" + 
                        "AND EMAIL = '" + oldEmail + "';";
        success = DBInstance.update(query);
        return success;
    }
    
    public boolean deleteCustomer(String sName, String fName, String phone, String email){
        boolean success;
        DBInstance.connect();
        String query = "DELETE FROM CUSTOMER\n" +
                       "WHERE PHONE = '" + phone + "'\n" +
                       "AND EMAIL = '" + email + "'\n" +
                       "AND SURNAME = '" + sName + "'\n" +
                       "AND FIRSTNAME = '" + fName  + "'; ";                                    
        success = DBInstance.update(query);
        return success;
    }
    
    public Customer searchCustomer(String ID){
        DBInstance.connect();
        try{
            String query = "SELECT * FROM CUSTOMER\n" +
                            "WHERE ID = '" + ID + "'; "; 
            ResultSet rs = DBInstance.query(query);
            Customer rCustomer;
            if(rs != null){
                String sName = rs.getString("SURNAME");
                String fName = rs.getString("FIRSTNAME");
                String address = rs.getString("ADDRESS");
                String postCode = rs.getString("POSTCODE");
                String phone = rs.getString("PHONE");
                String email = rs.getString("EMAIL");
                String customerType = rs.getString("CUSTOMERTYPE");
                rCustomer = new Customer(sName,fName,address,postCode,phone,email,customerType);
            }else{
                rCustomer = null;
            }
            DBInstance.closeConnection();
            return rCustomer;
        }catch(SQLException e){
            return null;
        }
    }
    
    /*public int calculateBill(){

    }*/
    
    public ArrayList<Customer> getActiveCustomers(){
        try{
            ArrayList<Customer> activeCustomers = new ArrayList<Customer>();
            DBInstance.connect();
            String query = "SELECT * FROM CUSTOMER;";
            ResultSet rs = DBInstance.query(query);
            
            while(rs.next()){
                String sName = rs.getString("SURNAME");
                String fName = rs.getString("FIRSTNAME");
                String address = rs.getString("ADDRESS");
                String postCode = rs.getString("POSTCODE");
                String phone = rs.getString("PHONE");
                String email = rs.getString("EMAIL");
                String customerType = rs.getString("CUSTOMERTYPE");
                activeCustomers.add(new Customer(sName,fName,address,postCode,phone,email,customerType));
            }
            return activeCustomers;
        }catch(SQLException e){
            return null;
        }
    }
}
