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
    public boolean addBooking(String n, String d, int c){
        conn = DBConnection.getInstance();
        //insert booking into database
        conn.connect();
        String query = "INSERT INTO BOOKINGS (NAME, DESCRIPTION, COST)" +
                       "VALUES(n, d, c);";
        conn.update(query);
        conn.closeConnection();
    }
    public boolean editBooking
    {
        conn = DBConnection.getInstance();
        //edit booking in database
        conn.connect();
        String query = " FROM BOOKINGS WHERE NAME = " + ;
        conn.update(query);
        conn.closeConnection();
    }
            
    public boolean deleteBooking(String ){
        conn = DBConnection.getInstance();
        //delete booking from database
        conn.connect();
        String query = "DELETE FROM BOOKINGS WHERE  = " + ;
        conn.update(query);
        conn.closeConnection();
    }
}
