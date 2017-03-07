/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.logic;

import common.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author athanasiosgkanos
 */
public class UserRegistry {
    DBConnection DBInstance = DBConnection.getInstance();
    private static UserRegistry URInstance = null;
    
    private UserRegistry(){    
    }
    
    public static UserRegistry getInstance(){
        if(URInstance == null){
            URInstance = new UserRegistry();
        }
        return URInstance;
    }
    
    public boolean addUser(int IDNo, String password, String sName, String fName, double rate){
        boolean success;
        DBInstance.connect();       
        String query = "INSERT INTO USERS (ID, PASSWORD, SURNAME, FIRSTNAME, HRATE, SYSADM) " + 
                           "VALUES ( " + 
                            IDNo + ", '" +
                            password + "', '" +
                            sName + "', '" +
                            fName + "', " +
                            rate + ", '" +
                            false + "');";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
    
    public boolean addAdmin(int IDNo, String password, String sName, String fName){
        boolean success;
        DBInstance.connect();       
        String query = "INSERT INTO USERS (ID, PASSWORD, SURNAME, FIRSTNAME, HRATE, SYSADM) " + 
                           "VALUES ( " + 
                            IDNo + ", '" +
                            password + "', '" +
                            sName + "', '" +
                            fName + "', " +
                            null + ", '" +
                            true + "');";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
    
    public boolean editUser(int IDNo, String password, String sName, String fName, double rate, boolean sysAdmin, int IDNoOld){
        boolean success;
        DBInstance.connect();
        String query  = "UPDATE USERS \n" + 
                        "SET " +
                        "ID = " + IDNo + ", " +
                        "PASSWORD = '" + password + "'," +
                        "SURNAME = '" + sName + "', " +
                        "FIRSTNAME = '" + fName + "', " +
                        "HRATE = " + rate + ", " +
                        "SYSADM = '" + sysAdmin + "'\n" +
                        "WHERE ID = " + IDNoOld + ";";
        success = DBInstance.update(query);
        return success;
    }
    
    public boolean editAdmin(int IDNo, String password, String sName, String fName, boolean sysAdmin, int IDNoOld){
        boolean success;
        DBInstance.connect();
        String query  = "UPDATE USERS \n" + 
                        "SET " +
                        "ID = " + IDNo + ", " +
                        "PASSWORD = '" + password + "'," +
                        "SURNAME = '" + sName + "', " +
                        "FIRSTNAME = '" + fName + "', " +
                        "HRATE = " + null + ", " +
                        "SYSADM = '" + sysAdmin + "'\n" +
                        "WHERE ID = " + IDNoOld + ";";
        success = DBInstance.update(query);
        return success;
    }
    
    public boolean deleteUser(int IDNo){
        boolean success;
        DBInstance.connect();
        String query = "DELETE FROM USERS\n" +
                       "WHERE ID = " + IDNo +  "; ";
        success = DBInstance.update(query);
        return success;
    }
    
    public ArrayList<Employee> getActiveUsers(){
        try{
            ArrayList<Employee> activeUsers = new ArrayList<Employee>();
            DBInstance.connect();
            String query = "SELECT * FROM USERS;";
            ResultSet rs = DBInstance.query(query);
            
            while(rs.next()){
                int ID = rs.getInt("ID");
                String pass = rs.getString("PASSWORD");
                String sName = rs.getString("SURNAME");
                String fName = rs.getString("FIRSTNAME");
                String sysAdm = rs.getString("SYSADM");
                if(sysAdm.equalsIgnoreCase("TRUE")){
                    activeUsers.add(new SysAdmin(ID,pass,sName,fName,true));
                }else{
                    double rate = rs.getDouble("HRATE");
                    activeUsers.add(new Mechanic(ID,pass,sName,fName,rate,false));
                }

            }
            return activeUsers;
        }catch(SQLException e){
            return null;
        }
    }
}
