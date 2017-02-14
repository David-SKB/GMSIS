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

    public BookingRegistry() {

    }

    public boolean addBooking(int ID, Date date, int length, int CID, int VID, int miles, int EID) {
        conn = DBConnection.getInstance();
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
        conn.update(query);
        conn.closeConnection();
        return true;
    }

    public boolean editBooking(int ID, Date date, int length, int CID, int VID, int miles, int EID) {
        conn = DBConnection.getInstance();
        //edit booking in database
        conn.connect();
        String query = "UPDATE BOOKINGS SET BOOKDATE = '"
                + date + "', TIME = '"
                + length + "', TYPE = 'Diagnosis and Repair', CUSTOMERID = '" 
                + CID + "', VEHICLEREGISTRATION = '"
                + VID + "', MILAGE = '"
                + miles + "', EMPLOYEEID = '"
                + EID + "' WHERE ID = '" + ID + "';";
        conn.update(query);
        conn.closeConnection();
        return true;
    }

    public boolean deleteBooking(int ID) {
        conn = DBConnection.getInstance();
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM BOOKINGS WHERE ID =  " + ID + "');";
        conn.update(query);
        conn.closeConnection();
        return true;
    }

    public ArrayList<Booking> getBooking(String i) {
        ArrayList<Booking> BookingList = new ArrayList<Booking>();
        conn = DBConnection.getInstance();
        conn.connect();
        String query = "SELECT * FROM BOOKINGS;";

    }
}
