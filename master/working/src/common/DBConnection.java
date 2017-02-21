package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
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
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }  
    }
    public void closeConnection(){
        try{
            stmt.close();
            c.close();
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public ResultSet query(String query){
        
        try {
            stmt = c.createStatement();
            String sql = query;
            ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (Exception e ) {
            return null;
        }
    }
    
    public boolean update(String query){        
        try {
            stmt = c.createStatement();
            String sql = query;
            stmt.executeUpdate(sql);           
            return true;
        } catch ( Exception e ) {
            return false;
        }
    }   
}

  
    
  

