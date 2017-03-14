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
public class SPCRegistry {
    DBConnection DBInstance = DBConnection.getInstance();
    private static SPCRegistry SPCInstance = null;
    
    private SPCRegistry(){    
    }
    
    public static SPCRegistry getInstance(){
        if( SPCInstance == null){
            SPCInstance = new SPCRegistry();
        }
        return SPCInstance;
    }
    
   public boolean addSPC(String spcName, String spcAddress, String spcTel, String spcEmail){
        boolean success;
        DBInstance.connect();       
        String query = "INSERT INTO CENTRES (NAME,ADDRESS,TELEPHONE,EMAIL) " + 
                       "VALUES ( '" + 
                       spcName + "', '" +
                       spcAddress + "', '" +
                       spcTel + "', '" +
                       spcEmail + "');";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
   
    public boolean editSPC(String spcName, String spcAddress, String spcTel, String spcEmail,
                           String oldName, String oldAddr, String oldTel, String oldEmail){
        boolean success;
        DBInstance.connect();       
        String query = "UPDATE CENTRES \n" + 
                       "SET " +
                       "NAME = '" + spcName + "', " +
                       "ADDRESS = '" + spcAddress + "', " +
                       "TELEPHONE = '" + spcTel + "', " +
                       "EMAIL = '" + spcEmail + "'\n" + 
                       "WHERE NAME = '" + oldName +  "'\n" +
                       "AND ADDRESS = '" + oldAddr + "'\n" +
                       "AND TELEPHONE = '" + oldTel + "'\n" +
                       "AND EMAIL = '" + oldEmail + "';";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
   
    public boolean deleteSPC(String spcName, String spcAddress, String spcTel, String spcEmail){
        boolean success;
        DBInstance.connect();       
        String query = "DELETE FROM CENTRES\n" + 
                           "WHERE NAME = '" + spcName +  "'\n" +
                           "AND ADDRESS = '" + spcAddress + "'\n" +
                           "AND TELEPHONE = '" + spcTel + "'\n" +
                           "AND EMAIL = '" + spcEmail + "';";
        success = DBInstance.update(query);
        DBInstance.closeConnection();
        return success;
    }
    
    public ArrayList<Employee> getSPCs(){
        try{
            ArrayList<SPC> SPClist = new ArrayList<SPC>();
            DBInstance.connect();
            String query = "SELECT * FROM CENTRES;";
            ResultSet rs = DBInstance.query(query);
            
            while(rs.next()){
                String spcName = rs.getString("NAME");
                String spcAddress = rs.getString("ADDRESS");
                String spcTel = rs.getString("TELEPHONE");
                String spcEmail = rs.getString("EMAIL");
                SPClist.add(new SPC(spcName,spcAddress,spcTel,spcEmail));
            }
            return SPClist;
        }catch(SQLException e){
            return null;
        }
    }
    
}
