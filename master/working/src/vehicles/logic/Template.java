/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.logic;

/**
 *
 * @author joshuascott
 */
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Template {
  private final SimpleStringProperty make;
  private final SimpleStringProperty model;
  private final SimpleDoubleProperty engineSize;
  private final SimpleStringProperty fuelType;

  public Template(String make,String model,Double engineSize,String fuelType){
   this.make = new SimpleStringProperty(make);
   this.model = new SimpleStringProperty(model);
   this.engineSize = new SimpleDoubleProperty(engineSize);
   this.fuelType = new SimpleStringProperty(fuelType);   
  } 

  @Override
  public String toString(){
   String result = this.getMake() + " " + this.getModel() + " " + this.getEngineSize() + " " + this.getFuelType(); 
    return result;   
  }
 
 public String getMake(){
  return make.get();    
 }
 public String getModel(){
  return model.get();   
}
 public double getEngineSize(){
  return engineSize.get();    
 }
 public String getFuelType(){
  return fuelType.get();   
 }
}
