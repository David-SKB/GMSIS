/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diagrep.logic;
import javafx.beans.property.SimpleStringProperty;

public class DiagRepairBooking
{
    private String id;
    private String bookdate;
    private String starttime;
    private String duration;
    private String type;
    private String cust;
    private String vehreg;
    private String mileage;
    private String emp;
    
    public DiagRepairBooking (String id, String bookdate, String starttime, String duration, String type, String cust, String vehreg, String mileage, String emp)
    {
        this.id = id;
        this.bookdate = bookdate;
        this.starttime = starttime;
        this.duration = duration;
        this.type = type;
        this.cust = cust;
        this.vehreg = vehreg;
        this.mileage = mileage;
        this.emp = emp;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the bookdate
     */
    public String getBookdate() {
        return bookdate;
    }

    /**
     * @return the starttime
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @return the cust
     */
    public String getCust() {
        return cust;
    }

    /**
     * @return the vehreg
     */
    public String getVehreg() {
        return vehreg;
    }

    /**
     * @return the mileage
     */
    public String getMileage() {
        return mileage;
    }

    /**
     * @return the emp
     */
    public String getEmp() {
        return emp;
    }
    
    
}