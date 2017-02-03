package common;

import Database.DBConnection;
import java.sql.ResultSet;

/**
 *
 * @author David O
 */
public class Authenticate
{   
    public static boolean check(String username, String password)
    {
        DBConnection c = DBConnection.getInstance();
        c.connect();
        String SQL = "SELECT PASSWORD FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);
        String result = "";
        try
        {
            while(rs.next())
            {
                result = rs.getString("PASSWORD");//retrieves the password
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
    
    public static boolean isAdmin(String username)
    {
        DBConnection c = DBConnection.getInstance();
        c.connect();
        String SQL = "SELECT SYSADM FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);
        Boolean result = false;
        try
        {
            while(rs.next())
            {
                result = rs.getBoolean("SYSADM");//retrieves the users level
            }
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
        if (result.equals("True"))
        {
            return true;
        }
        return false;
    }
}

