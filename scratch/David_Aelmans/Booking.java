/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scratch.David_Aelmans;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author dja30
 */
public class Booking {
    private final SimpleStringProperty bookingID;
    private final SimpleStringProperty bookingDate;
    private final SimpleStringProperty bookingLength;
    
    
    public Booking(String ID, String date, String length){
        bookingID = new SimpleStringProperty(ID);
        bookingDate = new SimpleStringProperty(date);
        bookingLength = new SimpleStringProperty(length);
    }
    
    public String getBookingID(){
        return bookingID.get();
    }
    
    public String getBookingDate(){
        return bookingDate.get();
    }
    
    public String getBookingLength(){
        return bookingLength.get();
    }
}
