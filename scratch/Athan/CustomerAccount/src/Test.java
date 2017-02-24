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
        boolean success = cr.editCustomer("Gkanos","Athanasios","2 Cobham Road","N22 6RP","07450496355","athanasiosgkanos@hotmail.com","Individual","07450496395","thanasisg.nirvana@hotmail.com");
        System.out.println(success);
    }
}
