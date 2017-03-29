
package customers.logic;

import diagrep.logic.DiagRepairBooking;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerBill {
    
    private final DiagRepairBooking DRP;
    private final SimpleFloatProperty cost;
    private final SimpleStringProperty bookingID;
    private final SimpleStringProperty bookingDate;
    private final SimpleStringProperty VehID;
    private final SimpleStringProperty bookingType;
    private final SimpleStringProperty bill;
    
    public CustomerBill(DiagRepairBooking DRP, float cost, String bill){
        this.DRP = DRP;
        this.bookingID = new SimpleStringProperty(DRP.getId());
        this.bookingDate = new SimpleStringProperty(DRP.getBookdate());
        this.VehID = new SimpleStringProperty(DRP.getVehreg());
        this.bookingType = new SimpleStringProperty(DRP.getType());
        this.cost = new SimpleFloatProperty(cost);
        this.bill = new SimpleStringProperty(bill);
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
    
    public String getBill(){
        return bill.get();
    }
    
    public void setBill(String status){
        bill.set(status);
    }
    
}
    
