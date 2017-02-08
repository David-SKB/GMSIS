/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import common.DBConnection;
import java.sql.ResultSet;
import java.util.*;
/**
 *
 * @author JR
 */
public class TestClass {
    public static void main(String[] args){
        DBConnection c = DBConnection.getInstance();
        //insert into database
        c.connect();
        String query = "INSERT INTO 'AUTHENTICATION'"
                + "VALUES ('TEST2', 'PASSWORD123', 1);";
        c.update(query);
        c.closeConnection();
        
        //query database
        c.connect();
        query = "SELECT USERNAME FROM AUTHENTICATION;";
        ResultSet rs = c.query(query);
        ArrayList<String> usernames = new ArrayList<String>();
        try{
            while(rs.next()){
                usernames.add(rs.getString("USERNAME"));
            }
        } catch( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
        c.closeConnection();
        System.out.println("List of users: " + usernames);
    }
}
