package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author David
 */
public class dbCon 
{
    public static void connect()
    {
        Connection conn = null;
        try 
        {
            conn = DriverManager.getConnection("jdbc:sqlite:data.db");
        }

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        finally 
        {
            try 
            {
                if (conn != null) 
                {
                    conn.close();
                }
            } 
            catch (SQLException ex) 
            {
                System.out.println(ex.getMessage());
            }
        }
    }
}
