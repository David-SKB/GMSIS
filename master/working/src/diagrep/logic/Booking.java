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
public class Booking {
    private final SimpleStringProperty bookingID;
    private final SimpleStringProperty bookingDate;
    private final SimpleStringProperty bookingLength;
    private final SimpleStringProperty bookingType;
    
    
    public Booking(String ID, String date, String length, String type){
        bookingID = new SimpleStringProperty(ID);
        bookingDate = new SimpleStringProperty(date);
        bookingLength = new SimpleStringProperty(length);
        bookingType = new SimpleStringProperty(type);
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
    
    public String getBookingType(){
        return bookingType.get();
    }
}
