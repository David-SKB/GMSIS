/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.logic;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author athanasiosgkanos
 */
public class Employee {
        
    private final SimpleIntegerProperty IDNumber;
    private final SimpleStringProperty password;
    private final SimpleStringProperty surname;
    private final SimpleStringProperty firstName;
    private final SimpleBooleanProperty sysAdmin;
    
    public Employee(int IDNo, String password, String sName, String fName, boolean sysAdmin){
        this.IDNumber = new SimpleIntegerProperty(IDNo);
        this.password = new SimpleStringProperty(password);
        this.surname = new SimpleStringProperty(sName);
        this.firstName = new SimpleStringProperty(fName);
        this.sysAdmin = new SimpleBooleanProperty(sysAdmin);
    }
    
    public int getIDNumber(){
        return IDNumber.get();
    }
    
    public String getPassword(){
        return password.get();
    }
    
    public String getSurname(){
        return surname.get();
    }
    
    public String getFirstname(){
        return firstName.get();
    }
    
    public boolean getSysAdmin(){
        return sysAdmin.get();
    }
}
