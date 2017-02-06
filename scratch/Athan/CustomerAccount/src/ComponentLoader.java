/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author athanasiosgkanos
 */
public class ComponentLoader {
    public static void main(String[] args){
        ArrayList<Customer> customerList;
        CustomerRegistry CRInstance = CustomerRegistry.getInstance();
        CRInstance.addCustomer("Thanasis Gkanos", "2 Cobham Road", "N22 6RP", "07450496395", "thanasisg.nirvana@hotmail.com", "Individual");
        CRInstance.addCustomer("Axilleas Gkanos", "2 Cobham Road", "N22 6RP", "07450498395", "thanasis3g.nirvana@hotmail.com", "Individual");
        customerList = CRInstance.getActiveCustomers();
        Customer cs1 = customerList.get(0);
        Customer cs2 = customerList.get(1);
        System.out.println(cs1.getName());
        System.out.println(cs2.getName());
        
    }
}
