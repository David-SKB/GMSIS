/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.logic;

import customers.logic.Customer;
import diagrep.logic.DiagRepairBooking;
import java.math.BigDecimal;
import vehicles.logic.Vehicle;

/**
 *
 * @author jr308
 */
public class UsedPart {
    private Part part;
    private DiagRepairBooking booking;
    private Customer customer;
    private Vehicle vehicle;
    public UsedPart(Part p, DiagRepairBooking bId, Customer cId, Vehicle vId) {
        part = p;
        booking = bId;
        customer = cId;
        vehicle = vId;
    }
    
    public String getName()
    {
        return part.getName();
    }
    
    public String getDescription()
    {
        return part.getDescription();
    }
    
    public String getCost()
    {
        return part.getCost();
    }
    public String getRepairID()
    {
        return booking.getId();
    }
    
    public String getVehicleRegistration()
    {
        return vehicle.getRegistration();
    }
    
     public String getCustomerID()
    {
        return booking.getCust();
    }
    
    public String getCustomerFirstName()
    {
        return customer.getFirstname();
    }
    
    public String getCustomerSurname()
    {
        return customer.getSurname();
    }
    
    public String getDate()
    {
        return booking.getBookdate();
    }
}
