/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;
import customers.logic.Customer;
import diagrep.logic.DiagRepairBooking;
import java.util.*;
import vehicles.logic.Vehicle;
/**
 *
 * @author jr308
 */
public class RepairWrapper {
    
    private Customer customer;
    private Vehicle vehicle;
    private DiagRepairBooking booking;
    
    public RepairWrapper(Customer c, Vehicle v, DiagRepairBooking b)
    {
        customer = c;
        vehicle = v;
        booking = b;
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
