package common;

/**
 *
 * @author David O
 */
public class Authenticate
{   
    public static boolean check(String username, String password)
    {
        //need conn to db
        //passes username to db to check if it exists and retrieve password
        String SQL = "SELECT Password FROM Users WHERE Username regexp '[[:<:]]" + username + "[[:>:]]';";
        //ResultSet rs = statement.executeQuery(SQL);
        String result = "";
        //while(rs.next())
        {
            //result = rs.getString("Password");//retrieves the password
        }
        
        if (result.equals(password))
        {
            return true;
        }
        return false;
    }
    
    public static boolean isAdmin(String username)
    {
        String SQL = "SELECT Level FROM Users WHERE Username regexp '[[:<:]]" + username + "[[:>:]]';";
        //ResultSet rs = statement.executeQuery(SQL);
        String result = "";
        //while(rs.next())
        {
            //result = rs.getString("Level");//retrieves the users level
        }
        
        if (result.equals("Admin"))//or w/e it will be identified as in the db
        {
            return true;
        }
        return false;
    }
}

