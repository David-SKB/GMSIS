package customers.logic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import common.DBConnection;
//import diagrep.logic.BookingRegistry;
//import diagrep.logic.DiagRepairBooking;
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
    //private BookingRegistry BR = BookingRegistry.getInstance();
    
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
        DBInstance.closeConnection();
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
        DBInstance.closeConnection();
        return success;
    }
    
    public ArrayList<Customer> searchCustomerWithName(String data,String cType,String searchType){
        DBInstance.connect();
        String custType = "Individual";
        if(cType.equalsIgnoreCase("Business")){
            custType = "Business";
        }
        try{
            String query = "SELECT * FROM CUSTOMER\n" +
                           "WHERE CUSTOMERTYPE = '" + custType + "' " +
                           "AND ( "  + searchType + " LIKE '%" + data +  "%'); ";
            ArrayList<Customer> searchedCustomers = new ArrayList<>();
            ResultSet rs = DBInstance.query(query);
            while(rs.next()){
                String sName = rs.getString("SURNAME");
                String fName = rs.getString("FIRSTNAME");
                String address = rs.getString("ADDRESS");
                String postCode = rs.getString("POSTCODE");
                String phone = rs.getString("PHONE");
                String email = rs.getString("EMAIL");
                String customerType = rs.getString("CUSTOMERTYPE");
                searchedCustomers.add(new Customer(sName,fName,address,postCode,phone,email,customerType));
            }
            DBInstance.closeConnection();
            return searchedCustomers;
        }catch(SQLException e){
            return null;
        }
    }
    
    public Customer searchCustomerByID(String ID){
        DBInstance.connect();
        try{
            String query = "SELECT * FROM CUSTOMER\n" +
                            "WHERE ID = '" + ID + "'; "; 
            ResultSet rs = DBInstance.query(query);
            Customer rCustomer;
            if(rs.isBeforeFirst()){
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
    
    public Customer searchCustomer(String ph, String mail){
        DBInstance.connect();
        try{
            String query = "SELECT * FROM CUSTOMER\n" +
                            "WHERE PHONE = '" + ph + "' " +
                            "AND EMAIL = '" + mail + "'; "; 
            ResultSet rs = DBInstance.query(query);
            Customer rCustomer;
            if(rs.isBeforeFirst()){
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
    
    public int getCustomerID(String ph, String mail){
        DBInstance.connect();
        try{
            String query = "SELECT ID FROM CUSTOMER\n" +
                            "WHERE PHONE = '" + ph + "' " +
                            "AND EMAIL = '" + mail + "'; "; 
            ResultSet rs = DBInstance.query(query);
            int ID;
            if(rs.isBeforeFirst()){
                ID = rs.getInt("ID");
            }else{
                return -1;
            }
            DBInstance.closeConnection();
                return ID;
        }catch(SQLException e){
            return -1;
        }
    }
    
    /*public ArrayList<DiagRepairBooking> getCustomerBookings(int custID){
        ArrayList<DiagRepairBooking> bookingList = BR.getCustomerBookings(custID);
        return bookingList;
    }*/
    
    /*public int calculateBill(DiagRepairBooking tempBooking){
        double duration = Double.parseDouble(tempBooking.getBookingLength());
        return 0;
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
            DBInstance.closeConnection();
            return activeCustomers;
        }catch(SQLException e){
            return null;
        }
    }
}
