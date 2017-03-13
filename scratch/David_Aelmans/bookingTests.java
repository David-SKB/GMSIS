package scratch.David_Aelmans;

/**
 *
 * @author dja30
 */
public class bookingTests {

    public static void main(String args[]) {
        BookingRegistry BR = new BookingRegistry();
        if (BR.editBooking(1, "EdiDate",1,1,1,1,1)){
            System.out.print("Success!");
        }
    }
}
