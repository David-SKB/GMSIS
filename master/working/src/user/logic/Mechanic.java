/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.logic;

import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author athanasiosgkanos
 */
public class Mechanic extends Employee{
    
    private final SimpleDoubleProperty hRate;
    
    public Mechanic(int IDNo, String password, String sName, String fName, double rate, boolean sysAdmin){
        super(IDNo, password, sName, fName,sysAdmin);
        this.hRate = new SimpleDoubleProperty(rate);
    }
    
    public double getHRate(){
        return hRate.get();
    }
}
