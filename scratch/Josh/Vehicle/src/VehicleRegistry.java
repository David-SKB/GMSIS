
import Database.DBConnection;
import java.util.Date;
import java.sql.*;
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
  
  public boolean checkExists(int reg){
   try{
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String query = "SELECT * FROM VEHICLE;";
     ResultSet rs = c.query(query);
      while( rs.next() ){
        int regCheck = rs.getInt("REGISTRATION");
        System.out.println(regCheck);
        if(reg == (regCheck)){
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
  
  public void addCar(int reg,int custID,String make,String model,int engine,String fuel,String colour,Date MOT,Date Last,int mile){
    
     if(!checkExists(reg)){
      try{
      DBConnection c = DBConnection.getInstance();
       c.connect();
     
       String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
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
   
  public void addVan(int reg,int custID,String make,String model,int engine,String fuel,String colour,Date MOT,Date Last,int mile){
   
      if(!checkExists(reg)){
       try{
       DBConnection c = DBConnection.getInstance();
        c.connect();
         String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
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
         System.out.println("Van added successfully");
   }
   catch(Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);   
  } 
 }
}
  public void addTruck(int reg,int custID,String make,String model,int engine,String fuel,String colour,Date MOT,Date Last,int mile){
    
     if(!checkExists(reg)){
      try{
     DBConnection c = DBConnection.getInstance();
     c.connect();
     String query = "INSERT INTO VEHICLE(REGISTRATION,CUSTOMERID,MAKE,MODEL,ENGINESIZE,FUELTYPE,COLOUR,MOTDATE,LASTSERVICE,MILEAGE)"+
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
         System.out.println("Van added successfully");
    }
    catch(Exception e){
     System.err.println(e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);   
   }
  }
}
  public void delete(int reg){
   //check exists in db first
  if(checkExists(reg)){
   int dialogButton = JOptionPane.YES_NO_OPTION;
   int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to delete vehicle?","Warning",dialogButton);
   //check for yes/no from popup
    if(dialogResult == JOptionPane.YES_OPTION){
     try{
      DBConnection c = DBConnection.getInstance();
      c.connect();
       String sql = "DELETE FROM VEHICLE WHERE REGISTRATION = " + reg + ";";
        c.update(sql);
        c.closeConnection();
        System.out.println("Deletion successfull");
     }
   catch(Exception e){
      System.err.println(e.getClass().getName() + ": " + e.getMessage() );
        System.exit(0);   
     }   
    }
    else{
     return;   
    }
 }
}  
  public void edit(int reg){
   if(checkExists(reg)){
    try{
     DBConnection c = DBConnection.getInstance();
      c.connect();
      Scanner input = new Scanner(System.in);
       System.out.println("What field would you like to change?");
       String str = input.nextLine();
       
    } 
   catch( Exception e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);   
   }
  }
 }
  
  public void searchReg(int registration){
   try{
    Statement stmt = null;
    DBConnection c = DBConnection.getInstance();
     c.connect();
     String sql = "SELECT * FROM VEHICLE WHERE REGISTRATION LIKE '" + registration + "';";
      ResultSet rs = stmt.executeQuery(sql);
      while( rs.next() ){
        int reg = rs.getInt("REGISTRATION");
        int cID = rs.getInt("CUSTOMERID");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        Date MOT = rs.getDate("MOTDATE");
        Date last = rs.getDate("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        
        System.out.println("Registration = " + reg);
        System.out.println("Customer ID = " + cID);
        System.out.println("Make = " + make);
        System.out.println("Model = " + model);
        System.out.println("EngineSize = " + engine);
        System.out.println("FuelType = " + fuel);
        System.out.println("Colour = " + colour);
        System.out.println("MOT Date = " + MOT);
        System.out.println("Last service = " + last);
        System.out.println("Current Mileage = " + mile);
        System.out.println();
      }
      rs.close();
      stmt.close();
      c.closeConnection();
   }
   catch(SQLException e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() ); 
     System.exit(0);
   }
  }
  
  public void searchManu(String manufacturer){
   try{
     Statement stmt = null;
     DBConnection c = DBConnection.getInstance();
     c.connect();
      String sql = "SELECT * FROM VEHICLE WHERE MAKE LIKE '" + manufacturer + "';";
       ResultSet rs = stmt.executeQuery(sql);
       while( rs.next() ){
        int reg = rs.getInt("REGISTRATION");
        int cID = rs.getInt("CUSTOMERID");
        String make = rs.getString("MAKE");
        String model = rs.getString("MODEL");
        int engine = rs.getInt("ENGINESIZE");
        String fuel = rs.getString("FUELTYPE");
        String colour = rs.getString("COLOUR");
        Date MOT = rs.getDate("MOTDATE");
        Date last = rs.getDate("LASTSERVICE");
        int mile = rs.getInt("MILEAGE");
        
        System.out.println("Registration = " + reg);
        System.out.println("Customer ID = " + cID);
        System.out.println("Make = " + make);
        System.out.println("Model = " + model);
        System.out.println("EngineSize = " + engine);
        System.out.println("FuelType = " + fuel);
        System.out.println("Colour = " + colour);
        System.out.println("MOT Date = " + MOT);
        System.out.println("Last service = " + last);
        System.out.println("Current Mileage = " + mile);
        System.out.println();   
       }
      rs.close();
      stmt.close();
      c.closeConnection();
   }
   catch(SQLException e){
    System.err.println(e.getClass().getName() + ": " + e.getMessage() ); 
     System.exit(0);
   }   
  }
}
