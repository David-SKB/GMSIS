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
    private String password;
    private String surname;
    private String firstName;
    private boolean sysAdmin;
    
    public Employee(int IDNo, String password, String sName, String fName, boolean sysAdmin){
        this.IDNumber = IDNo;
        this.password = password;
        this.surname = sName;
        this.firstName = fName;
        this.sysAdmin = sysAdmin;
    }
    
    public String getFirstName(){
        return firstName;
    }
}
