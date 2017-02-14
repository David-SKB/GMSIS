/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;

/**
 *
 * @author JR
 */
import Database.DBConnection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.*;

public class PartRegistry {
    private static PartRegistry instance = null;
    DBConnection conn;
    private PartRegistry(){
        
    }
    public static PartRegistry getInstance(){
        if(instance == null){
            instance = new PartRegistry();
        }
        return instance;
    }
    
    //add part to stock
    public void addPart(String n, String d, int c){
        conn = DBConnection.getInstance();
        //insert into database
        conn.connect();
        String query = "INSERT INTO STOCKPARTS (NAME, DESCRIPTION, COST)" +
                       "VALUES(" + n + "," + d + "," + c + ");";
        conn.update(query);
        conn.closeConnection();
    }
    //delete part from stock
    public void deletePart(String part){
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "DELETE FROM STOCKPARTS WHERE NAME = " + part;
        conn.update(query);
        conn.closeConnection();
    }
    //take part from stock to use in repair (delete from stock parts then add to used parts)
    public boolean usePart(int bId, int vId, int cId, String partName, Date wEnd, Date wStart, int cost){
        boolean success;
        deletePart(partName);
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "INSERT INTO USEDPARTS (BOOKINGID, VEHICLEID, " +
                " CUSTOMERID, PARTNAME, WARRANTYEND, WARRANTYSTART, COST)" +
                           "VALUES ( '" + 
                            bId + "', '" +
                            vId + "', '" +
                            cId + "', '" +
                            partName + "', '" +
                            wEnd + "', '" + 
                            wStart + "', '" + 
                            cost + "' );";
        success = conn.update(query);
        conn.closeConnection();
        return success;
    }
    //search for all parts used to repair a vehicle
    //can search by vehicle or customer
    public String searchUsedParts(int id, String searchBy){
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "SELECT * FROM USED PARTS WHERE " + searchBy 
                + " = " + id + ";";
        ResultSet rs = conn.query(query);
        conn.closeConnection();
        return null;
    }
    //calculates the total cost of all parts used in a specific repair
    //(parts assosicated with a specific booking)
    public int calculateBill(int bookingId){
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "SELECT COST FROM USED PARTS WHERE BOOKINGID = " 
                + bookingId + ";"; 
        ResultSet rs = conn.query(query);
        int totalCost = 0;
        try{
            while(rs.next()){
                totalCost += Integer.parseInt(rs.getString("COST"));
            }
        } catch( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
        conn.closeConnection();
        return totalCost;
    }
}
