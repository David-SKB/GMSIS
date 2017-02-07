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
public class Mechanic extends Employee{
    
    private int hRate;
    
    public Mechanic(int IDNo,String sName, String fName, int rate){
        super(IDNo,sName,fName);
        this.hRate = rate;
    }
}
