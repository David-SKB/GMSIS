/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.logic;

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

    public boolean addBooking(String date, String start, int length, String type, int CID, int VID, int EID) {
        //insert booking into database
        conn.connect();
        String query = "INSERT INTO BOOKINGS (BOOKDATE, STARTTIME, DURATION, TYPE, CUSTOMERID, VEHICLEREGISTRATION, EMPLOYEEID) "
                + "VALUES( '"
                + date + "', '"
                + start + "', '"
                + length + "', '"
                + type + "', '"
                + CID + "', '"
                + VID + "', '"
                + EID + "');";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }

    public boolean editBooking(int ID, String date, int start, int length, int type, int CID, int VID, int miles, int EID) {
        //edit booking in database
        conn.connect();
        String query = "UPDATE BOOKINGS SET BOOKDATE = '"
                + date + "', STARTTIME = '"
                + start + "', DURATION = '"
                + length + "', TYPE = '"
                + type + "', CUSTOMERID = '"
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
        String query = "DELETE FROM BOOKINGS WHERE ID = " + ID;
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
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                resultBooking = new DiagRepairBooking(BID, date, start, length, type, cusID, vechID, mileage, empID);
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
            ArrayList<Booking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS;";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }

    //method to retrieve all DiagRepairBookings
    public ArrayList<DiagRepairBooking> getListDiagRepairBookings() {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS;";// WHERE TYPE = DIAGREP;";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }
    
    public ArrayList<DiagRepairBooking> searchBookingByDate(String date) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE DATE = '" + date + "';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = result.getString("ID");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<DiagRepairBooking> searchBookingByCustID(String CustID) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE CUSTOMERID = '" + CustID + "';";
            ResultSet result = conn.query(query);
            while (result.next()){
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<DiagRepairBooking> searchBookingByCustName(String firstName, String surname) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE CUSTID = (SELECT ID FROM CUSTOMER WHERE LIKE SURNAME = '%" + surname + "%' AND FIRSTNAME = '%" + firstName + "%';";
            ResultSet result = conn.query(query);
            while(result.next()){
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<DiagRepairBooking> searchBookingByVechID(String vechID) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE LIKE VEHICLEREGISTRATION = '%" + vechID + "%';";
            ResultSet result = conn.query(query);
            while(result.next()){
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<DiagRepairBooking> searchBookingByVechModel(String model) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE VECHID = (SELECT ID FROM VEHICLE WHERE LIKE MODEL = '%" + model + "%';";
            ResultSet result = conn.query(query);
            while(result.next()){
                String ID = result.getString("ID");
                String date = result.getString("DATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }
}
