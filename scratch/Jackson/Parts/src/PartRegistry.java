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
    DBConnection conn;
    public PartRegistry(){
        
    }
    public void addPart(String n, String d, int c){
        conn = DBConnection.getInstance();
        //insert into database
        conn.connect();
        String query = "INSERT INTO STOCKPARTS (NAME, DESCRIPTION, COST)" +
                       "VALUES(n, d, c);";
        conn.update(query);
        conn.closeConnection();
    }
    public void deletePart(String part){
        conn = DBConnection.getInstance();
        //delete from database
        conn.connect();
        String query = "DELETE FROM STOCKPARTS WHERE NAME = " + part;
        conn.update(query);
        conn.closeConnection();
    }
}
