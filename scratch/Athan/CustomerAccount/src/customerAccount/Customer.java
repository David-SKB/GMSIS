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
public class Customer {
    private String fullName;
    private String address;
    private String postCode;
    private int phone;
    private String email;
    
    public Customer
        (String name, String address, String postC, int phone,
                String email){
        this.fullName = name;
        this.address = address;
        this.postCode = postC;
        this.phone = phone;
        this.email = email;
    }
}
