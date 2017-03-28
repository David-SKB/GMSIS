/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.logic;

/**
 *
 * @author JR
 */
import common.DBConnection;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import parts.gui.Delivery;

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
     public ArrayList<Part> getStockParts(){
        conn = DBConnection.getInstance();
        //insert into database
        try{
        conn.connect();
        String query = "SELECT * FROM STOCKPARTS;";
        ResultSet rs = conn.query(query);
        ArrayList<Part> partlist = new ArrayList<Part>();
        while (rs.next())
        {
            int id = rs.getInt("ID");
            String name = rs.getString("NAME");
            String description = rs.getString("DESCRIPTION");
            BigDecimal cost = new BigDecimal(rs.getString("COST"));
            int stock = rs.getInt("STOCK");
            partlist.add(new Part(id,name,description,cost,stock));
        }
        conn.closeConnection();
        return partlist;
        }catch(SQLException e){
            System.out.println("IN exception");
            return null;
        }
    }
     //returns all parts relevant to a given booking
     public ArrayList<Part> getUsedParts(String id){
        conn = DBConnection.getInstance();
        //insert into database
        try{
        conn.connect();
        String query = "SELECT * FROM USEDPARTS WHERE BOOKINGID = " + id + ";";
        ResultSet rs = conn.query(query);
        ArrayList<Part> partlist = new ArrayList<Part>();
        while (rs.next())
        {
            String name = rs.getString("NAME");
            String description = rs.getString("DESCRIPTION");
            BigDecimal cost = new BigDecimal(rs.getString("COST"));
            int stock = rs.getInt("STOCK");
            partlist.add(new Part(name,description,cost,stock));
        }
        conn.closeConnection();
        return partlist;
        }catch(SQLException e){
            return null;
        }
    }
    //add part to stock
    public void addPart(String n, String d, BigDecimal c, int stock){
        conn = DBConnection.getInstance();
        //insert into database
        conn.connect();
        String query = "INSERT INTO STOCKPARTS (NAME, DESCRIPTION, COST, STOCK)" +
                       "VALUES('" + n + "','" + d + "','" + c + "', " + stock + ");";
        conn.update(query);
        conn.closeConnection();
    }
    public void updateStock(int id, int quantity){
        conn = DBConnection.getInstance();
        //insert into database
        conn.connect();
        String query = "UPDATE STOCKPARTS SET STOCK = STOCK + " + quantity + "  WHERE ID = " + id + ";";
        conn.update(query);
        
        conn.closeConnection();
    }
    //delete part from stock
    public boolean deletePart(int id){
        boolean success;
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "DELETE FROM STOCKPARTS WHERE ID = " +  1 + ";";
        System.out.println(query);
        success = conn.update(query);
        System.out.println(success);
        System.out.println("in delete " + id);
        conn.closeConnection();
        return success;
    }
    //edit part in stock
    public void editStockPart(int id, String name, String description, BigDecimal cost, int stocklevel){
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "UPDATE STOCKPARTS SET NAME = '" + name + "', DESCRIPTION = '" + description 
                +"', COST = '" + cost + "', STOCK = " + stocklevel + " WHERE ID = " + id + ";";
        System.out.println("edit part test" + id);
        conn.update(query);
        conn.closeConnection();
    }
    //take part from stock to use in repair (delete from stock parts then add to used parts)
    public boolean usePart(String bId, String vId, String cId, int partID, String wEnd, String wStart, BigDecimal cost){
        boolean success;
        //deletePart(partName);
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "INSERT INTO USEDPARTS (BOOKINGID, VEHICLEID, " +
                " CUSTOMERID, PARTID, WARRANTYEND, WARRANTYSTART, COST)" +
                           "VALUES ( '" + 
                            bId + "', '" +
                            vId + "', '" +
                            cId + "', " +
                            partID + ", '" +
                            wEnd + "', '" + 
                            wStart + "', '" + 
                            cost + "' );";
        success = conn.update(query);
        conn.closeConnection();
        return success;
    }
    //Add delivery
    public boolean addDelivery(int partID, int quantity, Date date){
        boolean success;
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "INSERT INTO DELIVERIES (PARTID, QUANTITY, DELIVERYDATE) VALUES ( " + partID +", "  + quantity + ", " + date.toString() + ");";
        System.out.println("in addDelievery " + partID);
        success = conn.update(query);
        conn.closeConnection();
        return success;
    }
    
    //get all deliveries
    public ArrayList<Delivery> getDeliveries(){
        conn = DBConnection.getInstance();
        //delete from database
        try{
            conn.connect();
            String query = "SELECT STOCKPARTS.NAME, DELIVERIES.QUANTITY, DELIVERIES.DELIVERYDATE \n" +
"                 FROM STOCKPARTS INNER JOIN DELIVERIES\n" +
"                 ON STOCKPARTS.ID = DELIVERIES.PARTID;";
            
            ResultSet rs = conn.query(query);
            System.out.println("getDeliveries test");
            ArrayList<Delivery> deliverylist = new ArrayList<Delivery>();
            while (rs.next())
            {
                System.out.println("getDeliveries test2");
                String name = rs.getString(1);
                int quantity = rs.getInt(2);
                String date = rs.getString(3);
                deliverylist.add(new Delivery(name,quantity,date));
            }
            conn.closeConnection();
            return deliverylist;
        }catch(SQLException e){
            System.out.println("IN exception");
            return null;
        }
    }
    
    //search for all parts used to repair a vehicle
    //can search by vehicle or customer
    public ArrayList<Part> searchStockParts(String id, String searchBy){
        conn = DBConnection.getInstance();
        try{
        //delete from database
        conn.connect();
        String query = "SELECT * FROM STOCKPARTS WHERE " + searchBy 
                + " LIKE '%" + id + "%';";
        ResultSet rs = conn.query(query);
        ArrayList<Part> partlist = new ArrayList<Part>();
        while (rs.next())
        {
            System.out.println("registry test");
            String name = rs.getString("NAME");
            System.out.println(name);
            String description = rs.getString("DESCRIPTION");
            System.out.println(description);
            BigDecimal cost = new BigDecimal(rs.getString("COST"));
            System.out.println(cost);
            int stock = rs.getInt("STOCK");
            System.out.println(stock);
            partlist.add(new Part(name,description,cost,stock));
        }
        conn.closeConnection();
        return partlist;
        }catch(SQLException e){
            System.out.println("IN exception");
            return null;
        }
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
