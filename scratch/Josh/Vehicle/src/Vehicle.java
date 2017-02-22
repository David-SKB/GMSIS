/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vehicle{
  private  SimpleStringProperty registration;
  private final SimpleIntegerProperty customerID;
  private final SimpleStringProperty model;
  private final SimpleStringProperty make;
  private final SimpleIntegerProperty engineSize;
  private final SimpleStringProperty fuelType;
  private  SimpleStringProperty colour;
  private  SimpleStringProperty MOTRenewal;
  private  SimpleBooleanProperty warranty;
  private  SimpleStringProperty lastService;
  private SimpleIntegerProperty currentMile;
  
  public Vehicle(String reg,SimpleIntegerProperty custID,String mdl,String mke,SimpleIntegerProperty eSize,String fType,String clr,String MOT,SimpleBooleanProperty warr,
    String lastSer,SimpleIntegerProperty mile){
    this.registration = new SimpleStringProperty(reg);
    this.customerID = custID;
    this.model = new SimpleStringProperty(mdl);
    this.make = new SimpleStringProperty(mke);
    this.engineSize = eSize;
    this.fuelType = new SimpleStringProperty(fType);
    this.colour = new SimpleStringProperty(clr);
    this.MOTRenewal = new SimpleStringProperty(MOT);
    this.warranty = warr;
    this.lastService = new SimpleStringProperty(lastSer);
    this.currentMile = mile;
  }
  //reg getters and setters
  public String getReg(){
    return registration.get();
  }
  public void setReg(SimpleStringProperty registration){
   this.registration = registration;   
  }
  public int getId(){
   return customerID.get();
  }
  public String getModel(){
   return model.get();   
  }
  public String getMake(){
   return make.get();   
  }
  public int getEngine(){
   return engineSize.get();    
  }
  public String getFuel(){
   return fuelType.get();   
  }
  //colour
  public String getColour(){
   return colour.get();
  }
  public void setColour(SimpleStringProperty colour){
   this.colour = colour;  
  }
  public String getMOt(){
   return MOTRenewal.get();
  }
  public void setMot(SimpleStringProperty MOTRenewal){
   this.MOTRenewal = MOTRenewal;   
  }
  public boolean getWarranty(){
   return warranty.get();   
  }
  public void setWarranty(SimpleBooleanProperty warranty){
   if(warranty.equals(true)){
       this.warranty.set(false);  
   }
   else{
       this.warranty.set(true);   
   }
  }
  public String getLast(){
    return lastService.get();   
  }
  public void setLast(SimpleStringProperty lastService){
    this.lastService = lastService;
  }
  public int getMile(){
   return currentMile.get();
  }
  public void setMile(SimpleIntegerProperty currentMile){
   this.currentMile = currentMile;   
  }
}