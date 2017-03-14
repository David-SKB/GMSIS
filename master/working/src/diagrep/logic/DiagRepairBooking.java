/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.logic;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author dja30
 */
public class DiagRepairBooking extends Booking {
    private final SimpleStringProperty CustID;
    private final SimpleStringProperty VechID;
    private final SimpleStringProperty milage;
    private final SimpleStringProperty EmpID;
    
    public DiagRepairBooking(String BID, String date, String length, String CID, String VID, String miles, String EID){
        super(BID, date, length,"Diagnosis and Repair");
        CustID = new SimpleStringProperty(CID);
        VechID = new SimpleStringProperty(VID);
        milage = new SimpleStringProperty(miles);
        EmpID = new SimpleStringProperty(EID);
    }
    
    public String getCustID(){
        return CustID.get();
    }
    
    public String getVechID(){
        return VechID.get();
    }
    
    public String getMilage(){
        return milage.get();
    }
        
    public String getEmpID(){
        return EmpID.get();
    }
    
}
