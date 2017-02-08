package common;


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
    
    public boolean update(String query){
        
        try {
            stmt = c.createStatement();
            String sql = query;
            stmt.executeUpdate(sql);
            
            return true;
        } catch ( Exception e ) {
            return false;
        }
        //System.out.println("Table created successfully");
    }
    
    public static boolean check(String username, String password){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        
        String SQL = "SELECT USERNAME,PASSWORD FROM AUTHENTICATION WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "';";
        //String SQL = "SELECT PASSWORD FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);
        String result = "";
        try
        {
            while(rs.next())
            {
                System.out.println("wag1 fam");
                System.out.println(rs.getString("USERNAME"));
                System.out.println(rs.getString("PASSWORD"));
                //result = rs.getString("PASSWORD");//retrieves the password
                return true;
            }
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    
        }
        if (result.equals(password))
        {
            return true;
        }
        return false;
    }
    
    public static boolean isAdmin(String username){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        //String SQL = "SELECT SYSADM FROM AUTHENTICATION WHERE USERNAME = 'username';";
        //String SQL = "SELECT SYSADM FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        //ResultSet rs = c.query(SQL);
        Boolean result = false;
        /*try
        {
            while(rs.next())
            {
                result = rs.getBoolean("SYSADM");//retrieves the users level
            }
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }*/
        
        return true;
    }
}

  
    
  

