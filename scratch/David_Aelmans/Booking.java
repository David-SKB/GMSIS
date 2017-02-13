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
public class Booking {
    int bookingID;
    Date bookingDate;
    int bookingLength;
    
    
    public Booking(int ID, Date date, int length){
        bookingID = ID;
        bookingDate = date;
        bookingLength = length;
    }
    
}
