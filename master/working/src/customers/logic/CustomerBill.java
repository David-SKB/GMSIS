
package customers.logic;

import diagrep.logic.DiagRepairBooking;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerBill {
    
    private DiagRepairBooking DRP;
    private SimpleFloatProperty bill;
    private final SimpleStringProperty bookingID;
    private final SimpleStringProperty bookingDate;
    private final SimpleStringProperty VehID;
    private final SimpleStringProperty bookingType;
    
    public CustomerBill(DiagRepairBooking DRP, float bill){
        this.DRP = DRP;
        this.bookingID = new SimpleStringProperty(DRP.getId());
        this.bookingDate = new SimpleStringProperty(DRP.getBookdate());
        this.VehID = new SimpleStringProperty(DRP.getVehreg());
        this.bookingType = new SimpleStringProperty(DRP.getType());
        this.bill = new SimpleFloatProperty(bill);
    }
    
    public float getBill(){
        return bill.get();
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
    
