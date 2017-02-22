/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scratch.David_Aelmans;

import java.util.Date;

/**
 *
 * @author dja30
 */
public class DiagRepairBooking extends Booking {
    int CustID;
    int VechID;
    int milage;
    int EmpID;
    
    public DiagRepairBooking(int BID, Date date, int length, int CID, int VID, int miles, int EID){
        super(BID, date, length);
        CustID = CID;
        VechID = VID;
        milage = miles;
        EmpID = EID;
    }
    
    public int getCustID(){
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
