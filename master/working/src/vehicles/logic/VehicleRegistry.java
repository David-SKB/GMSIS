package vehicles.logic;

import common.DBConnection;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Scanner;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class VehicleRegistry {
   DBConnection DBInstance = DBConnection.getInstance();
   private static VehicleRegistry curInstance = null;
   
  public static VehicleRegistry getInstance(){
    if(curInstance == null){
      curInstance = new VehicleRegistry();  
    }
    return curInstance;
  }
  
  public boolean checkExists(String reg){
   try{
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String query = "SELECT * FROM VEHICLE;";
     ResultSet rs = c.query(query);
      while( rs.next() ){
        String regCheck = rs.getString("REGISTRATION");
        if(reg.equals(regCheck)){
          return true;  
        }   
      }
      rs.close();
      c.closeConnection();
      return false;
   }
   catch( SQLException e){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);
   } 
   return false;
 }
 
 public boolean checkWarranty(String reg){
   try{
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String sql = "SELECT * FROM WARRANTY;";
     ResultSet rs = c.query(sql);
      while( rs.next() ){
       String regCheck = rs.getString("REGISTRATION");
        if(reg.equals(regCheck)){
          return true;  
        }
      }
     rs.close();
     c.closeConnection();
     return false;
   }
   catch(SQLException e){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);  
   }
   return false;
  }
 
  public void updateDetails(String oldReg,String newReg,int custID,String make,String model,int engine,String fuel,String colour,String MOT,boolean warranty,String last,int mile){
   try{
    DBConnection c = DBConnection.getInstance();
     c.connect();
     //query for update
     String query = "UPDATE VEHICLE " +
                    "SET REGISTRATION = '" + newReg + "'," +
                    "MAKE = '" + make +"'," +
                    "MODEL = '" + model +"'," +
                    "ENGINESIZE = " + engine +"," +
                    "FUELTYPE = '" + fuel +"'," +
                    "COLOUR = '" + colour +"'," +
                    "MOTDATE = '" + MOT +"'," +
                    "WARRANTY = '" + warranty +"'," +
                    "LASTSERVICE = '" + last +"'," +
                    "MILEAGE = " + mile + " " +
                    "WHERE REGISTRATION = '" + oldReg + "';";
     c.update(query);
     c.closeConnection();
   }
   catch(Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);    
   }   
  }
  
  
  public void addWarranty(String reg,String name,String address,String expiry){
   try{
     DBConnection c = DBConnection.getInstance();
      c.connect();
      //query
      String query = "INSERT INTO WARRANTY(REGISTRATION,NAME,ADDRESS,EXPIRYDATE)"+
                     "VALUES ( '" +
                             reg + "', '" +
                             name + "', '" +
                             address + "', '" +
                             expiry + "' );";
      c.update(query);
      c.closeConnection();
                       
   }
   catch(Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );   
   }   
  }
  
  public void deleteWarranty(String reg){
    try{
     DBConnection c = DBConnection.getInstance();
      c.connect();
      //SQL DELETE QUERY
      String query = "DELETE FROM WARRANTY WHERE REGISTRATION = '" + reg + "';";
       c.update(query);
       c.closeConnection();
    }
    catch(Exception e){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);   
    }
  }
  
  public void addCar(String reg,int custID,String type,String make,String model,double engine,String fuel,String colour,String MOT,boolean warranty,String Last,int mile){
     if(!checkExists(reg)){
      try{
      DBConnection c = DBConnection.getInstance();
       c.connect();
     
       String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,TYPE,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,WARRANTY,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
                            type + "', '" +
                            make + "', '" +
                            model + "', '" + 
                            engine + "', '" + 
                            fuel + "', '" +
                            colour + "', '" +
                            MOT + "', '" +
                            warranty + "', '" +
                            Last + "', '" +
                            mile + "' );";
        boolean test = c.update(query);
        c.closeConnection();
   }
   catch(Exception e){
   System.err.println(e.getClass().getName() + ": " + e.getMessage() );
   System.exit(0);   
  }
 }
}
     
  public void addVan(String reg,int custID,String type,String make,String model,double engine,String fuel,String colour,String MOT,boolean warranty,String Last,int mile){
   
      if(!checkExists(reg)){
       try{
       DBConnection c = DBConnection.getInstance();
        c.connect();
         String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,TYPE,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,WARRANTY,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
                            type + "', '" +
                            make + "', '" +
                            model + "', '" + 
                            engine + "', '" + 
                            fuel + "', '" +
                            colour + "', '" +
                            MOT + "', '" +
                            warranty + "', '" +
                            Last + "', '" +
                            mile + "' );";
         c.update(query);
         c.closeConnection();
         System.out.println("Van added successfully");
   }
   catch(Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);   
  } 
 }
}
  public void addTruck(String reg,int custID,String type,String make,String model,double engine,String fuel,String colour,String MOT,boolean warranty,String Last,int mile){
    
     if(!checkExists(reg)){
      try{
     DBConnection c = DBConnection.getInstance();
     c.connect();
     String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,TYPE,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,WARRANTY,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
                            type + "', '" +
                            make + "', '" +
                            model + "', '" + 
                            engine + "', '" + 
                            fuel + "', '" +
                            colour + "', '" +
                            MOT + "', '" +
                            warranty + "', '" +
                            Last + "', '" +
                            mile + "' );";
         c.update(query);
         c.closeConnection();
         System.out.println("Truck added successfully");
    }
    catch(Exception e){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);   
   }
  }
}
   
  public void delete(String reg){
     try{
      DBConnection c = DBConnection.getInstance();
      c.connect();
       String sql = "DELETE FROM VEHICLE WHERE REGISTRATION = '" + reg + "';";
        c.update(sql);
        c.closeConnection();
        if(checkWarranty(reg)){
          deleteWarranty(reg);   
        }
     }
   catch(Exception e){
      System.err.println(e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);   
     }   
    }
  
  public void changeMOT(String registration){
   DBConnection c = DBConnection.getInstance();
   c.connect();
    Scanner input = new Scanner(System.in);
    System.out.println("ENTER THE NEW MOT DATE");
    String changed = input.nextLine();
    String query = "UPDATE VEHICLE SET MOTDATE = '" + changed + "' WHERE REGISTRATION = " + registration + ";";
    c.update(query);
    System.out.println("MOT date updated successfully");
    c.closeConnection();
    
  }
  public void changeLast(String registration){
   DBConnection c = DBConnection.getInstance();
   c.connect();
    Scanner input = new Scanner(System.in);
    System.out.println("ENTER THE NEW LAST SERVICE DATE");
    String changed = input.nextLine();
    String query = "UPDATE VEHICLE SET LASTSERVICE = '" + changed + "' WHERE REGISTRATION = " + registration + ";";
    c.update(query);
    System.out.println("Last service updated successfully");
    c.closeConnection();
  }
  public void changeMileage(String registration){
   DBConnection c = DBConnection.getInstance();
   c.connect();
    Scanner input = new Scanner(System.in);
    System.out.println("ENTER THE MOST RECENT MILEAGE");
    String changed = input.nextLine();
    int mile = Integer.parseInt(changed);
    String query = "UPDATE VEHICLE SET MILEAGE = " + mile + " WHERE REGISTRATION = " + registration + ";";
    c.update(query);
    System.out.println("MILEAGE UPDATED");
    c.closeConnection();   
  }
  public void changeColour(String registration){
   DBConnection c = DBConnection.getInstance();
   c.connect();
    Scanner input = new Scanner(System.in);
    System.out.println("ENTER THE NEW COLOUR");
    String changed = input.nextLine();
    String query = "UPDATE VEHICLE SET COLOUR = '" + changed + "' WHERE REGISTRATION = " + registration + ";";
    c.update(query);
    System.out.println("colour updated successfully");
    c.closeConnection();  
  }
  
  public ArrayList<Vehicle> searchBoth(String registration,String manufacturer){
   try{
    ArrayList<Vehicle>result = new ArrayList();
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String sql = "SELECT * FROM VEHICLE \n" +
                  "WHERE REGISTRATION = '" + registration + "'\n" +
                  "AND MAKE = '" + manufacturer + "';";
     ResultSet rs = c.query(sql);
     while( rs.next( )){
        String reg = rs.getString("REGISTRATION");
        int cID = rs.getInt("CUSTOMERID");
        String type = rs.getString("TYPE");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOTdate = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String Lastdate = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        Vehicle v = new Vehicle(reg,cID,type,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
        result.add(v);   
     }
     rs.close();
     c.closeConnection();
     return result;
   }
   catch(SQLException e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() ); 
     System.exit(0);
      return null;
   }
  }
  
  public Vehicle searchForEdit(String registration){
   try{
     DBConnection c = DBConnection.getInstance();
     c.connect();
     String sql = "SELECT * FROM VEHICLE WHERE REGISTRATION LIKE '" + registration + "';";
      ResultSet rs = c.query(sql);
      while( rs.next() ){
       if(registration.equals(rs.getString("REGISTRATION"))){
        String reg = rs.getString("REGISTRATION");
        String type = rs.getString("TYPE");
        int cID = rs.getInt("CUSTOMERID");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOTdate = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String Lastdate = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        Vehicle v = new Vehicle(reg,cID,type,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
        return v;
       }   
      }
    return null;
   }
   catch(SQLException e){
    return null;   
   }
  }
  
  public ArrayList<Vehicle> searchCustomerVehicles(int id){
   try{
    ArrayList<Vehicle>result = new ArrayList();
    DBConnection c = DBConnection.getInstance();
     c.connect();
     //query 
     String sql = "SELECT * FROM VEHICLE WHERE CUSTOMERID LIKE " + id +";";
      ResultSet rs = c.query(sql);
       while( rs.next() ){
        String reg = rs.getString("REGISTRATION");
        int cID = rs.getInt("CUSTOMERID");
        String type = rs.getString("TYPE");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOTdate = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String Lastdate = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        //CREATE VEHICLE OBJECT FROM SEARCH
        Vehicle v = new Vehicle(reg,cID,type,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
         result.add(v);
       }
       rs.close();
       c.closeConnection();
       return result;
   }
   catch(SQLException e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     return null;
   }   
  }
  
  public ArrayList<Vehicle> searchReg(String registration){
   try{
    ArrayList<Vehicle>result = new ArrayList();
    DBConnection c = DBConnection.getInstance();
     c.connect();
     //query
     String sql = "SELECT * FROM VEHICLE WHERE REGISTRATION LIKE '" + registration + "';";
      ResultSet rs = c.query(sql);
      while( rs.next() ){
        String reg = rs.getString("REGISTRATION");
        int cID = rs.getInt("CUSTOMERID");
        String type = rs.getString("TYPE");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOTdate = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String Lastdate = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        Vehicle v = new Vehicle(reg,cID,type,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
        result.add(v);
      }
      rs.close();
      c.closeConnection();
      return result;
   }
   catch(SQLException e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() ); 
     System.exit(0);
      return null;
   }
  }
  
  public ArrayList<Vehicle> searchManu(String manufacturer){
   try{
     ArrayList<Vehicle> result = new ArrayList();
     DBConnection c = DBConnection.getInstance();
     c.connect();
      String sql = "SELECT * FROM VEHICLE WHERE MAKE LIKE '" + manufacturer + "';";
       ResultSet rs = c.query(sql);
       while( rs.next() ){
        String reg = rs.getString("REGISTRATION");
        int cID = rs.getInt("CUSTOMERID");
        String type =rs.getString("TYPE");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOT = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String last = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        Vehicle v = new Vehicle(reg,cID,type,make,model,engine,fuel,colour,MOT,warranty,last,mile);
        result.add(v);  
       }
      rs.close();
      c.closeConnection();
      return result;
   }
   catch(SQLException e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() ); 
     System.exit(0);
     return null;
   }   
  }
  public ArrayList<Vehicle> getAllVehicles(){
   try{
    ArrayList<Vehicle>list = new ArrayList<>();
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String sql = "SELECT * FROM VEHICLE;";
      ResultSet rs = c.query(sql);
      int count = 0;
      while(rs.next()){
          
       String reg = rs.getString("REGISTRATION");
       int cID = count;
       String type = rs.getString("TYPE");
       String make = rs.getString("MAKE");
       String model = rs.getString("MODEL");
       int engine = rs.getInt("ENGINESIZE");
       String fuel = rs.getString("FUELTYPE");
       String colour = rs.getString("COLOUR");
       String MOT = rs.getString("MOTDATE");
       boolean warranty = rs.getBoolean("WARRANTY");
       String last = rs.getString("LASTSERVICE");
       int mile = rs.getInt("MILEAGE");
       
       Vehicle v = new Vehicle(reg,cID,type,make,model,engine,fuel,colour,MOT,warranty,last,mile);
       list.add(v);
       count++;
      }
     return list;
   }
   catch(SQLException e){
    return null;
   }
  }
}
