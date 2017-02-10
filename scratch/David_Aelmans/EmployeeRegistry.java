/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scratch.David_Aelmans;

/**
 *
 * @author dja30
 */
import Database.DBConnection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.*;

public class EmployeeRegistry {
    DBConnection conn;
    public EmployeeRegistry(){
        
    }
    public boolean addEmployee(String n, String d, int c){
        conn = DBConnection.getInstance();
        //insert booking into database
        conn.connect();
        String query = "INSERT INTO EMPLOYEES
        conn.update(query);
        conn.closeConnection();
        return true;
    }
    public boolean editEmployee
    {
        conn = DBConnection.getInstance();
        //edit booking in database
        conn.connect();
        String query = " FROM EMPLOYEE WHERE NAME = " + ;
        conn.update(query);
        conn.closeConnection();
        return true;
    }
            
    public boolean deleteEmployee(String ){
        conn = DBConnection.getInstance();
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM EMPLOYEE WHERE  = " + ;
        conn.update(query);
        conn.closeConnection();
        return true;
    }
    /*
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
*/
}
