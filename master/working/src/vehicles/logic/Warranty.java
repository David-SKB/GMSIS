/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.logic;
import javafx.beans.property.SimpleStringProperty;
/**
 *
 * @author joshuascott
 */
public class Warranty {
 private SimpleStringProperty name;
 private SimpleStringProperty address;
 private SimpleStringProperty expiry;
 
  public Warranty(String wname,String waddress,String wexpiry){
    this.name = new SimpleStringProperty(wname);
    this.address = new SimpleStringProperty(waddress);
    this.expiry = new SimpleStringProperty(wexpiry);
  }
  public String getName(){
   return name.get();
  }
  public String getAddress(){
   return address.get();
  }
  public String getExpiry(){
   return expiry.get();
  }
}
