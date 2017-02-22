/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scratch.David_Aelmans;

/**
 * NEED TO FIX DATES
 * BILLS
 * RS class?
 * TRY CATCH LOOPS FOR CUSTOMER/VEHICLE
 * ALSO NOTHING WORKS, MAY WANT TO GET ON THAT
 * @author David Aelmans
 */
import common.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*; 

public class BookingRegistry {

    DBConnection conn = DBConnection.getInstance();
    private static BookingRegistry BRInstance = null;

    public static BookingRegistry getInstance(){
        if(BRInstance == null){
            BRInstance = new BookingRegistry();
        }
        return BRInstance;
    }

    public boolean addBooking(int ID, String date, int length, int CID, int VID, int miles, int EID) {
        //insert booking into database
        conn.connect();
        String query = "INSERT INTO BOOKINGS (ID, BOOKDATE, TIME, TYPE, CUSTOMERID, VEHICLEREGISTRATION, MILAGE, EMPLOYEEID)"
                + "VALUES( '"
                + ID + "', '"
                + date + "', '"
                + length + "', 'Diagnosis and Repair', '"
                + CID + "', '"
                + VID + "', '"
                + miles + "', '"
                + EID + "');";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }

    public boolean editBooking(int ID, String date, int length, int CID, int VID, int miles, int EID) {
        //edit booking in database
        conn.connect();
        String query = "UPDATE BOOKINGS SET BOOKDATE = '"
                + date + "', TIME = '"
                + length + "', TYPE = 'Diagnosis and Repair', CUSTOMERID = '" 
                + CID + "', VEHICLEREGISTRATION = '"
                + VID + "', MILAGE = '"
                + miles + "', EMPLOYEEID = '"
                + EID + "' WHERE ID = '" + ID + "';";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }

    public boolean deleteBooking(int ID) {
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM BOOKINGS WHERE ID =  " + ID + "');";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }
    /*
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
    
    public int calculateCost
    
*/
    
    
    public ArrayList<Booking> getListBookings() {
        ArrayList<Booking> BookingList = new ArrayList<Booking>();
        conn = DBConnection.getInstance();
        conn.connect();
        String query = "SELECT * FROM BOOKINGS;";

    /*    public ArrayList<Customer> getActiveCustomers(){
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
    //public Booking getBooking() "customer and vehicle details and next booking date"
    //The user should be able to search for a booking by partial or full vehicle registration number, vehicle manufacturer or customer surname and firstname.
}
