/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.logic;

/**
 *
 * @author athanasiosgkanos
 */
public class SysAdmin extends Employee{
    
    public SysAdmin(int IDNo, String password, String sName, String fName, boolean sysAdmin){
        super(IDNo, password, sName, fName, sysAdmin);
    }
}
