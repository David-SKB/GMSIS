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
import user.logic.Employee;
import user.logic.Mechanic;
import user.logic.UserRegistry;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;

public class BookingRegistry {

    DBConnection conn = DBConnection.getInstance();
    private static BookingRegistry BRInstance = null;
    
    //Only one instance of the registry at any time
    public static BookingRegistry getInstance() {
        if (BRInstance == null) {
            BRInstance = new BookingRegistry();
        }
        return BRInstance;
    }

    //insert booking into database
    public boolean addBooking(String date, String start, String length, String type, String CID, String VID, String EID) {
        conn.connect();
        String query = "INSERT INTO BOOKINGS (BOOKDATE, STARTTIME, DURATION, TYPE, CUSTOMERID, VEHICLEREGISTRATION, EMPLOYEEID) "
                + "VALUES( '"
                + date + "', '" 
                + start + "', '"
                + length + "', '"
                + type + "', "
                + Integer.parseInt(CID) + ", '"
                + VID + "', '"
                + EID + "');";
        boolean result = conn.update(query);
        if(result){
            int ID = Integer.parseInt(findID(date, start, length, type, CID, VID, EID));
            result = addBill(ID,length, EID);
        }
        conn.closeConnection();
        return result;
    }
    
    //inserts a bill created from booking into bills table
    public boolean addBill(int ID,String length, String EID){
        UserRegistry UR = UserRegistry.getInstance();
        Mechanic mech = (Mechanic) UR.searchUserByID(EID);
        double cost = mech.getHRate() * Integer.parseInt(length);
        String query = "INSERT INTO BILLS VALUES ("+ID+", " +cost+", 0, 0, 0);";
        boolean result = conn.update(query);
        return result;
    }

    //edit booking in database
    public boolean editBooking(String date, String start, String length, String type, String CID, String VID, String miles, String EID) {
        conn.connect();
        String ID = this.findID(date, start, length, type, CID, VID, EID);
        String query = "UPDATE BOOKINGS SET BOOKDATE = '"
                + date + "', STARTTIME = '"
                + start + "', DURATION = '"
                + length + "', TYPE = '"
                + type + "', CUSTOMERID = '"
                + CID + "', VEHICLEREGISTRATION = '"
                + VID + "', MILEAGE = '"
                + miles + "', EMPLOYEEID = '"
                + EID + "' WHERE ID = " + ID + ";";
        boolean result = conn.update(query);
        if(result){
            result = editBill(ID ,length, EID);
            VehicleRegistry VR = VehicleRegistry.getInstance();
            Vehicle rV = VR.searchForEdit(VID);
            rV.setCurrentMile(Integer.parseInt(miles));
        }
        conn.closeConnection();
        return result;
    }
    
    //updates bill for the recently edited booking
    public boolean editBill(String ID, String length, String EID){
        UserRegistry UR = UserRegistry.getInstance();
        Mechanic mech = (Mechanic) UR.searchUserByID(EID);
        double cost = mech.getHRate() * Integer.parseInt(length);
        String query = "UPDATE BILLS SET DIAGREPCOST = "+cost+", STATUS = 0 WHERE BILLID = "+ID+";";
        boolean result = conn.update(query);
        return result;
    }

    //removes booking from database
    public boolean deleteBooking(String ID) {
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM BOOKINGS WHERE ID = " + Integer.parseInt(ID) + ";";
        boolean result = conn.update(query);
        conn.closeConnection();
        return result;
    }

    //search a booking by its ID
    public DiagRepairBooking searchBookingID(String ID) {
        conn.connect();
        try {
            String query = "SELECT * FROM BOOKINGS "
                    + "WHERE ID = " + ID + ";";
            ResultSet result = conn.query(query);
            DiagRepairBooking resultBooking;
            if (result != null) {
                String BID = Integer.toString(result.getInt("ID"));
                String date = result.getString("BOOKDATE");
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

    //Gets a list of all bookings in the database
    public ArrayList<DiagRepairBooking> getListBookings() {
        try {
            
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS;";
            ResultSet result = conn.query(query);
            while (result.next()) {
                  
                String ID = Integer.toString(result.getInt("ID"));
          
                String date = result.getString("BOOKDATE");
           
                String start = result.getString("STARTTIME");
             
                String length = result.getString("DURATION");
                
                String type = result.getString("TYPE");
               
                String cusID = result.getString("CUSTOMERID");
      
                String vechID = result.getString("VEHICLEREGISTRATION");
         
                String mileage = result.getString("MILEAGE");
                if(mileage==null){
                    mileage = "N/A";
                }
                String empID = result.getString("EMPLOYEEID");
  
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
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
          
                String date = result.getString("BOOKDATE");
           
                String start = result.getString("STARTTIME");
             
                String length = result.getString("DURATION");
                
                String type = result.getString("TYPE");
               
                String cusID = result.getString("CUSTOMERID");
      
                String vechID = result.getString("VEHICLEREGISTRATION");
         
                String mileage = result.getString("MILEAGE");
                if(mileage==null){
                    mileage = "N/A";
                }
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            //return null;
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            BookingList.add(new DiagRepairBooking("dfsf", "dfsf", "dfsf", "dfsf", "dfsf", "dfsf", "dfsf", "dfsf", "dfsf"));
             return BookingList;
        }
    }

    //Finds all bookings from a given date
    public ArrayList<DiagRepairBooking> searchBookingByDate(String date) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE DATE = '" + date + "';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }
    
    //Finds all bookings associated with a given customer ID
    public ArrayList<DiagRepairBooking> searchBookingByCustID(String CustID) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE CUSTOMERID = " + Integer.parseInt(CustID) + ";";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
                String date = result.getString("BOOKDATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = String.valueOf(result.getInt("CUSTOMERID"));
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Finds all bookings associated with a customer, found with full or partial first name, surname or full name. 
    public ArrayList<DiagRepairBooking> searchBookingByCustName(String firstName, String surname) {
        try {
            if (firstName == null || firstName.isEmpty() || surname == null || surname.isEmpty()) {
                return null;
            }
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE CUSTID = (SELECT ID FROM CUSTOMER WHERE LIKE SURNAME = '%" + surname + "%' AND FIRSTNAME = '%" + firstName + "%';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
                String date = result.getString("BOOKDATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            return null;
        }
    }
    
    //Retrieves all bookings for a given customer first name.
    public ArrayList<DiagRepairBooking> searchBookingByCustomerFirstName(String firstName) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS INNER JOIN CUSTOMER ON BOOKINGS.CUSTOMERID = CUSTOMER.ID WHERE CUSTOMER.FIRSTNAME LIKE '%" + firstName + "%';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
                String date = result.getString("BOOKDATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = String.valueOf(result.getInt("CUSTOMERID"));
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    //Retrieves all bookings for a given customer surname.
    public ArrayList<DiagRepairBooking> searchBookingByCustomerSurname(String surname) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS INNER JOIN CUSTOMER ON BOOKINGS.CUSTOMERID = CUSTOMER.ID WHERE CUSTOMER.SURNAME LIKE '%" + surname + "%';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
                String date = result.getString("BOOKDATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = String.valueOf(result.getInt("CUSTOMERID"));
                String vechID = result.getString("VEHICLEREGISTRATION");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Finds all bookings associated with a given vehicle ID.
    public ArrayList<DiagRepairBooking> searchBookingByVechID(String vechID) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE VEHICLEREGISTRATION LIKE '%" + vechID + "%';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
                String date = result.getString("BOOKDATE");
                String start = result.getString("STARTTIME");
                String length = result.getString("DURATION");
                String type = result.getString("TYPE");
                String cusID = result.getString("CUSTOMERID");
                String mileage = result.getString("MILEAGE");
                String empID = result.getString("EMPLOYEEID");
                BookingList.add(new DiagRepairBooking(ID, date, start, length, type, cusID, vechID, mileage, empID));
            }
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    //Finds all bookings with a specific model of vehicle
    public ArrayList<DiagRepairBooking> searchBookingByVechModel(String model) {
        try {
            ArrayList<DiagRepairBooking> BookingList = new ArrayList<>();
            conn = DBConnection.getInstance();
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE VECHID = (SELECT REGISTRATION FROM VEHICLE WHERE LIKE MODEL = '%" + model + "%';";
            ResultSet result = conn.query(query);
            while (result.next()) {
                String ID = Integer.toString(result.getInt("ID"));
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
            conn.closeConnection();
            return BookingList;
        } catch (SQLException e) {
            
            return null;
        }
    }

    //Finds the ID of a booking given its other details.
    public String findID(String date, String start, String length, String type, String CID, String VID, String EID) {
        try {
            conn.connect();
            String query = "SELECT * FROM BOOKINGS WHERE BOOKDATE = '"
                    + date + "' AND STARTTIME = '"
                    + start + "' AND DURATION = '"
                    + length + "' AND TYPE = '"
                    + type + "' AND CUSTOMERID = "
                    + CID + " AND VEHICLEREGISTRATION = '"
                    + VID + "'AND EMPLOYEEID = '"
                    + EID + "';";
            ResultSet rs = conn.query(query);
            String ID = Integer.toString(rs.getInt("ID"));
            conn.closeConnection();
            return ID;
        }catch (SQLException e) {
            return null;
        }
    }
}
