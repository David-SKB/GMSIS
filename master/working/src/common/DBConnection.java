/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
/**
 *
 * @author JR
 */
public class DBConnection {
    private static DBConnection instance = null;
    private Connection c;
    private Statement stmt;
    private DBConnection(){    
    }
    public static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }
    public void connect(){
        try{
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:test.db");
        System.out.println("Opened database successfully");
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }  
    }
    public void closeConnection(){
        try{
            stmt.close();
            c.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
    }
    public ResultSet query(String query){
        
        try {
            stmt = c.createStatement();
            String sql = query;
            ResultSet rs = stmt.executeQuery(sql);
            return rs;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
            return null;
        }
        //System.out.println("Table created successfully");
    }
    
    public void update(String query){
        
        try {
            stmt = c.createStatement();
            String sql = query;
            stmt.executeUpdate(sql);
            

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //System.exit(0);
        }
        //System.out.println("Table created successfully");
    }
}

  
    
  

