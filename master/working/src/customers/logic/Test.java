/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers.logic;

/**
 *
 * @author athanasiosgkanos
 */
public class Test {
    public static void main(String[] args){
        CustomerRegistry CR = CustomerRegistry.getInstance();
        int ID = CR.getCustomerID("04561657468","qmul@qmul.com");
        System.out.println(ID); 
    }
}
