package David_Aelmans;

/**
 *
 * @author dja30
 */
public class bookingTests {

    public static void main(String args[]) {
        BookingRegistry BR = new BookingRegistry();
        if(BR.deleteBooking(1)){
            System.out.println("YAY");
        }
    }
}
