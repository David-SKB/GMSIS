/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.logic;

/**
 *
 * @author jr308
 */
import common.DBConnection;
import java.util.Date;
import java.sql.ResultSet;
import java.util.*;

public class Repair {
    
    DBConnection conn;
    private String vehicleRegistration;
    private ArrayList<String> parts;
    
    public Repair(String v){
        vehicleRegistration = v;
        parts = new ArrayList<String>();
    }
    
    public void addPart(String p){
        parts.add(p);
    }
    
    public void saveInDatabase(){
        conn = DBConnection.getInstance();
        //insert into database
        conn.connect();
        String query = "INSERT INTO REPAIRS (VEHICLEID, CUSTOMERID, TOTALCOST)" +
                       "VALUES(n, d, c);";
        conn.update(query);
        conn.closeConnection();
    }
}
