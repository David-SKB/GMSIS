/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerAccount;

/**
 *
 * @author athanasiosgkanos
 */
public class Individual extends Customer{
    public Individual
        (String name, String address, String postCode, int phone, String email){
        super(name,address,postCode,phone,email);
    }
}
