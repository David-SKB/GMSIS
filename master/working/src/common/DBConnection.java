package common;

import java.io.InputStream;
import java.net.URL;
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
            instance.init(); 
        }
 
        return instance;
    }
    public void connect(){
        /*try{
            c = DriverManager.getConnection("jdbc:sqlite:src/database/gmsisdb.db");
            stmt = c.createStatement();
            String sql = "PRAGMA foreign_keys = ON";
            stmt.executeUpdate(sql);
            System.out.println( "connrcted");
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }  */
    }
     public void init(){
        try{
            Class.forName("org.sqlite.JDBC");
            //c = DriverManager.getConnection("jdbc:sqlite:src/database/gmsisdb.db");
            c = DriverManager.getConnection("jdbc:sqlite::resource:database/gmsisdb.db");//allows db to be embed within jar
            stmt = c.createStatement();
            String sql = "PRAGMA foreign_keys = ON";
            stmt.executeUpdate(sql);
            //System.out.println( "connected");
        }catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }  
    }
    
    public void closeConnection(){
       /* try{
            stmt.close();
            c.close();
            System.out.println( "connrction closed");
        }
        catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }*/
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
            //System.out.println(e);
            return false;
        }
    }   
}

  
    
  

