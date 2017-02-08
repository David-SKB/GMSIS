/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.*;
import user.logic.Mechanic;
import user.logic.UserRegistry;

/**
 *
 * @author athanasiosgkanos
 */
public class Main {
    public static void main(String[] args){
        UserRegistry ur = UserRegistry.getInstance();
        ur.addUser(12345,"password","Athan","Gkanos", 6.80);
        ArrayList<Mechanic> userList = new ArrayList<Mechanic>();
        userList = ur.getActiveUsers();
        Mechanic tempMech = userList.get(0);
        System.out.println(tempMech.getFirstName());
    }
}
