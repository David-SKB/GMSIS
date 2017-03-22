
package customers.logic;

import diagrep.logic.Booking;
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
        this.bookingID = new SimpleStringProperty(((Booking)DRP).getBookingID());
        this.bookingDate = new SimpleStringProperty(((Booking)DRP).getBookingDate());
        this.VehID = new SimpleStringProperty(DRP.getVechID());
        this.bookingType = new SimpleStringProperty(((Booking)DRP).getBookingType());
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
    
