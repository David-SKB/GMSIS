/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author athanasiosgkanos
 */
public class Test {
    public static void main(String[] args){
        CustomerRegistry cr = CustomerRegistry.getInstance();
        boolean result = cr.addCustomer("Asder", "der", "2C", "12", "0745049394", "thanasisg.nirvana@hotmail.com", "Individual");
        System.out.println(result);
    
    }
}
