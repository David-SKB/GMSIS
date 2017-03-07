import vehicle.*;
import Database.DBConnection;
import java.util.Date;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Scanner;


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
  
  public void addCar(String reg,int custID,String make,String model,int engine,String fuel,String colour,String MOT,boolean warranty,String Last,int mile){
    
     if(!checkExists(reg)){
      try{
      DBConnection c = DBConnection.getInstance();
       c.connect();
     
       String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,WARRANTY,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
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
        System.out.println("Car added successfully");
   }
   catch(Exception e){
   System.err.println(e.getClass().getName() + ": " + e.getMessage() );
   System.exit(0);   
  }
 }
}
  
  public void addTemplate(){
   try{
     DBConnection c = DBConnection.getInstance();
     c.connect();
     //car 1
     String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car2
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car3
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car4
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car5
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car6
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car7
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car8
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car9
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car10
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car11
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car12
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car13
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car14
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car15
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car16
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car17
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car18
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car19
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     //car20
     query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
                    "VALUES (000,000,TEMPLATE,TEMPLATE,000,TEMPLATE,TEMPLATE,01/02/03,01/02/04,000)";
     c.update(query);
     c.closeConnection();
    }
    catch( Exception e ){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);  
    }
  }
   
  public void addVan(String reg,int custID,String make,String model,int engine,String fuel,String colour,String MOT,boolean warranty,String Last,int mile){
   
      if(!checkExists(reg)){
       try{
       DBConnection c = DBConnection.getInstance();
        c.connect();
         String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,WARRANTY,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
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
  public void addTruck(String reg,int custID,String make,String model,int engine,String fuel,String colour,String MOT,boolean warranty,String Last,int mile){
    
     if(!checkExists(reg)){
      try{
     DBConnection c = DBConnection.getInstance();
     c.connect();
     String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,WARRANTY,LASTSERVICE,MILEAGE)"+
                    "VALUES ( '" + 
                            reg + "', '" +
                            custID + "', '" +
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
     }
   catch(Exception e){
      System.err.println(e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);   
     }   
    }
  
  public void edit(String reg){
   if(checkExists(reg)){
    try{
      Scanner input = new Scanner(System.in);
       System.out.println("ENTER THE FIELD YOU WOULD LIKE TO CHANGE");
       String field = input.nextLine();
       //switch
       switch(field){
           case "MOT":
               changeMOT(reg);
               break;
           case "LAST":
               //field = "LAST";
               changeLast(reg);
               break;
           case "COLOUR":
               //field = "COLOUR";
               changeColour(reg);
               break;
           case "MILEAGE":
               //field = "MILEAGE";
               changeMileage(reg);
               break;
           default:
               System.out.println("Sorry you cannot change that field,try again");
       }
    } 
   catch( Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);   
   }
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
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOTdate = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String Lastdate = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        Vehicle v = new Vehicle(reg,cID,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
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
        Vehicle v = new Vehicle(reg,cID,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
        return v;
       }   
      }
    return null;
   }
   catch(SQLException e){
    return null;   
   }
  }
  
  public ArrayList<Vehicle> searchReg(String registration){
   try{
    ArrayList<Vehicle>result = new ArrayList();
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String sql = "SELECT * FROM VEHICLE WHERE REGISTRATION LIKE '" + registration + "';";
      ResultSet rs = c.query(sql);
      while( rs.next() ){
        String reg = rs.getString("REGISTRATION");
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
        Vehicle v = new Vehicle(reg,cID,make,model,engine,fuel,colour,MOTdate,warranty,Lastdate,mile);
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
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        String MOT = rs.getString("MOTDATE");
        boolean warranty = rs.getBoolean("WARRANTY");
        String last = rs.getString("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        Vehicle v = new Vehicle(reg,cID,make,model,engine,fuel,colour,MOT,warranty,last,mile);
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
       String make = rs.getString("MAKE");
       String model = rs.getString("MODEL");
       int engine = rs.getInt("ENGINESIZE");
       String fuel = rs.getString("FUELTYPE");
       String colour = rs.getString("COLOUR");
       String MOT = rs.getString("MOTDATE");
       boolean warranty = rs.getBoolean("WARRANTY");
       String last = rs.getString("LASTSERVICE");
       int mile = rs.getInt("MILEAGE");
       
       Vehicle v = new Vehicle(reg,cID,make,model,engine,fuel,colour,MOT,warranty,last,mile);
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
