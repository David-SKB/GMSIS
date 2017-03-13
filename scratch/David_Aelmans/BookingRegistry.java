/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scratch.David_Aelmans;

/**
 * @author David Aelmans
 */
import common.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BookingRegistry {
    
    DBConnection conn = DBConnection.getInstance();
    private static BookingRegistry BRInstance = null;

    public static BookingRegistry getInstance() {
        if (BRInstance == null) {
            BRInstance = new BookingRegistry();
        }
        return BRInstance;
    }

    public boolean addBooking(int ID, String date, int length, int CID, int VID, int miles, int EID) {
        //insert booking into database
        conn.connect();
        String query = "INSERT INTO BOOKINGS (ID, BOOKDATE, TIME, TYPE, CUSTOMERID, VEHICLEREGISTRATION, MILEAGE, EMPLOYEEID) "
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
                + VID + "', MILEAGE = '"
                + miles + "', EMPLOYEEID = '"
                + EID + "' WHERE ID = '" + ID + "';";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }

    public boolean deleteBooking(int ID) {
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM BOOKINGS WHERE ID = "+ ID +");";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }

    public Booking searchBookingID(int ID) {
        conn.connect();
        try {
            String query = "SELECT * FROM BOOKINGS\n"
                    + "WHERE ID = '" + ID + "'; ";
            ResultSet result = conn.query(query);
            Booking resultBooking;
            if (result != null) {
                String BID = result.getString("ID");
                String date = result.getString("DATE");
                String length = result.getString("TIME");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                resultBooking = new DiagRepairBooking(BID, date, length, cusID, vechID, mileage, empID);
            } else {
                resultBooking = null;
            }
            conn.closeConnection();
            return resultBooking;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<Booking> getListBookings() {
        try {
            ArrayList<Booking> BookingList = new ArrayList<Booking>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS;";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String length = result.getString("TIME");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, length, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }
        //public Booking getBooking() "customer and vehicle details and next booking date"
        //The user should be able to search for a booking by partial or full vehicle registration number, vehicle manufacturer or customer surname and firstname.
}
