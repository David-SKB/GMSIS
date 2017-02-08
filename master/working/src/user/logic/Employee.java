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
public class Employee {
        
    private int IDNumber;
    private String surname;
    private String firstName;
    
    public Employee(int IDNo, String sName, String fName){
        this.IDNumber = IDNo;
        this.surname = sName;
        this.firstName = fName;
    }
    
    public String getFirstName(){
        return firstName;
    }
}
