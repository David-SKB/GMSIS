
package vehicle;
import Database.DBConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.*;
import java.util.Date;
import java.sql.PreparedStatement;

public class VehicleRegistry {
   DBConnection DBInstance = DBConnection.getInstance();
   private static VehicleRegistry curInstance = null;
   
  private VehicleRegistry(){
    VehicleRegistry vr = new VehicleRegistry();  
  }
  public static VehicleRegistry getInstance(){
    if(curInstance == null){
      curInstance = new VehicleRegistry();  
    }
    return curInstance;
  }
  public void addCar(int reg,int custID,String make,String model,int engine,String fuel,String colour,Date MOT,DATE Last,int mile){
   try{
      DBConnection c = DBConnection.getInstance();
       c.connect();
     
       String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOT,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
                            make + "', '" +
                            model + "', '" + 
                            engine + "', '" + 
                            fuel + "', '" +
                            colour + "', '" +
                            MOT + "', '" +
                            Last + "', '" +
                            mile + "' );";
        c.update(query);
        c.closeConnection();
   }
   catch(Exception e){
   System.err.println(e.getClass().getName() + ": " + e.getMessage() );
   System.exit(0);   
  }
 }
  public void addVan(int reg,int custID,String make,String model,int engine,String fuel,String colour,Date MOT,DATE Last,int mile){
   try{
       DBConnection c = DBConnection.getInstance();
        c.connect();
         String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOT,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
                            make + "', '" +
                            model + "', '" + 
                            engine + "', '" + 
                            fuel + "', '" +
                            colour + "', '" +
                            MOT + "', '" +
                            Last + "', '" +
                            mile + "' );";
         c.update(query);
         c.closeConnection();
   }
   catch(Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);   
  }   
  }
  public void addTruck(int reg,int custID,String make,String model,int engine,String fuel,String colour,Date MOT,DATE Last,int mile){
    try{
     DBConnection c = DBConnection.getInstance();
     c.connect();
     String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOT,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
                            make + "', '" +
                            model + "', '" + 
                            engine + "', '" + 
                            fuel + "', '" +
                            colour + "', '" +
                            MOT + "', '" +
                            Last + "', '" +
                            mile + "' );";
         c.update(query);
         c.closeConnection();
    }
    catch(Exception e){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);   
   }
  }
  public void delete(int reg){
  try{
   DBConnection c = DBConnection.getInstance();
   c.connect();
   String sql = "DELETE FROM VEHICLE WHERE REGISTRATION = " + reg + ";";
   c.update(sql);
   c.closeConnection();
  }
  catch(Exception e){
   System.err.println(e.getClass().getName() + ": " + e.getMessage() );
   System.exit(0);   
  }
 }
  
  public void edit(int reg){
      
  }
}
