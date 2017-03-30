/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.logic;

import diagrep.logic.DiagRepairBooking;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author joshuascott
 */
public class VehicleBill {
  
    private final DiagRepairBooking drb;
    private final SimpleFloatProperty cost;
    private final SimpleStringProperty bookingID;
    private final SimpleStringProperty bookingDate;
    private final SimpleStringProperty VehID;
    private final SimpleStringProperty bookingType;
    
    public VehicleBill(DiagRepairBooking drb, float cost){
     this.drb = drb;
     this.bookingID = new SimpleStringProperty(drb.getId());
     this.bookingDate = new SimpleStringProperty(drb.getBookdate());
     this.VehID = new SimpleStringProperty(drb.getVehreg());
     this.bookingType = new SimpleStringProperty(drb.getType());
     this.cost = new SimpleFloatProperty(cost);
    }
    
    public DiagRepairBooking getDRB(){
      return this.drb;   
    }
    public float getCost(){
     return cost.get();
    }
    public String getBookingID(){
     return bookingID.get();
    }
    public String getBookingDate(){
     return bookingDate.get();
    }
    public String getVehID(){
     return VehID.get();
    }
    public String getBookingType(){
     return bookingType.get();
    }
}
