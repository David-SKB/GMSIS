/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.logic;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Vehicle{
  private  SimpleStringProperty registration;
  private final SimpleIntegerProperty customerID;
  private final SimpleStringProperty type;
  private final SimpleStringProperty make;
  private final SimpleStringProperty model;
  private final SimpleDoubleProperty engineSize;
  private final SimpleStringProperty fuelType;
  private  SimpleStringProperty colour;
  private  SimpleStringProperty MOTRenewal;
  private  SimpleBooleanProperty warranty;
  private  SimpleStringProperty lastService;
  private SimpleIntegerProperty currentMile;
  
  public Vehicle(String reg,int custID,String Vtype,String mdl,String mke,double eSize,String fType,String clr,String MOT,boolean warr,
    String lastSer,int mile){
    this.registration = new SimpleStringProperty(reg);
    this.customerID = new SimpleIntegerProperty(custID);
    this.type = new SimpleStringProperty(Vtype);
    this.model = new SimpleStringProperty(mdl);
    this.make = new SimpleStringProperty(mke);
    this.engineSize = new SimpleDoubleProperty(eSize);
    this.fuelType = new SimpleStringProperty(fType);
    this.colour = new SimpleStringProperty(clr);
    this.MOTRenewal = new SimpleStringProperty(MOT);
    this.warranty = new SimpleBooleanProperty(warr);
    this.lastService = new SimpleStringProperty(lastSer);
    this.currentMile = new SimpleIntegerProperty(mile);
  }
  //reg getters and setters
  public String getRegistration(){
    return registration.get();
  }
  public void setRegistration(String reg){
   registration.set(reg);
  }
  public int getId(){
   return customerID.get();
  }
  public String getType(){
   return type.get();
  }
  public String getModel(){
   return model.get();   
  }
  public void setModel(String nmodel){
   model.set(nmodel);
  }
  public String getMake(){
   return make.get();   
  }
  public void setMake(String nmake){
   make.set(nmake);
  }
  public double getEngineSize(){
   return engineSize.get();    
  }
  public void setEngineSize(int nSize){
   engineSize.set(nSize);
  }
  public String getFuelType(){
   return fuelType.get();   
  }
  public void setFuelType(String nfuel){
   fuelType.set(nfuel);
  }
  //colour
  public String getColour(){
   return colour.get();
  }
  public void setColour(String ncolour){
   colour.set(ncolour);
  }
  public String getMOTRenewal(){
   return MOTRenewal.get();
  }
  public void setMOTRenewal(String nMOT){
   MOTRenewal.set(nMOT);
  }
  public boolean getWarranty(){
   return warranty.get();   
  }
  public void setWarranty(boolean nwarranty){
   warranty.set(nwarranty);
  }
  public String getLastService(){
    return lastService.get();   
  }
  public void setLastService(String nlastService){
    lastService.set(nlastService);
  }
  public int getCurrentMile(){
   return currentMile.get();
  }
  public void setCurrentMile(int ncurrentMile){
   currentMile.set(ncurrentMile);
  }
}