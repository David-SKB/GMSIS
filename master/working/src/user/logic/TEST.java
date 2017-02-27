/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.logic;

import common.DBConnection;

/**
 *
 * @author athanasiosgkanos
 */
public class TEST {
    public static void main(String[] args){
        DBConnection DBInstance = DBConnection.getInstance();
        UserRegistry UR = UserRegistry.getInstance();
        boolean admin = UR.addAdmin(12345, "TESTUSER", "ADMIN", "ADMIN");
        boolean user = UR.addUser(54321, "TESTUSER", "MECHANIC", "MECHANIC", 7.20);
        System.out.println(admin + " " + user);
    }
}
