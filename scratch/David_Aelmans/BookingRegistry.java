/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scratch.David_Aelmans;

/**
 *
 * @author David Aelmans
 */
import Database.DBConnection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.*;

public class BookingRegistry {
    DBConnection conn;
    public BookingRegistry(){
        
    }
    public boolean addBooking(int ID, Date date, int length, int CID, int VID, int miles, int EID){
        conn = DBConnection.getInstance();
        //insert booking into database
        conn.connect();
        String query = "INSERT INTO BOOKINGS (ID, BOOKDATE, TIME, TYPE, CUSTOMERID, VEHICLEREGISTRATION, MILAGE, EMPLOYEEID)" +
                       "VALUES( '" + 
                            ID + "', '" +
                            date + "', '" +
                            length + "', 'Diagnosis and Repair', '"+
                            CID + "', '" + 
                            VID + "', '" +
                            miles + "', '" +
                            EID + "');";
        conn.update(query);
        conn.closeConnection();
        return true;
    }
    public boolean editBooking()
    {
        conn = DBConnection.getInstance();
        //edit booking in database
        conn.connect();
        String query = " FROM BOOKINGS WHERE NAME = " + ;
        conn.update(query);
        conn.closeConnection();
        return true;
    }
            
    public boolean deleteBooking(String ){
        conn = DBConnection.getInstance();
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM BOOKINGS WHERE  = " + ;
        conn.update(query);
        conn.closeConnection();
        return true;
    }
    
    public ArrayList<Booking> getBooking(String ){
        conn = DBCoonection.getInstance();
        //retreive a list of all bookings
        String query = "DELETE FROM BOOKINGS WHERE  = " + ;
        conn.update(query);
        conn.closeConnection();
        return bookingList;
    }
}
