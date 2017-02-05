/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.*;

/**
 *
 * @author athanasiosgkanos
 */
public class ComponentLoader {
    public static void main(String[] args){
        CustomerRegistry CRInstance = CustomerRegistry.getInstance();
        CRInstance.addCustomer("Thanasis Gkanos", "2 Cobham Road", "N22 6RP", "07450496395", "thanasisg.nirvana@hotmail.com", "Individual");
    }
}
