/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

import java.util.Date;
public class Vehicle{
  final private String registration;
  final private String model;
  final private String make;
  final private int engineSize;
  final private String fuelType;
  private String colour;
  private Date MOTRenewal;
  private boolean warranty;
  private Date lastService;
  private int currentMile;
  public Vehicle(String reg,String mdl,String mke,int eSize,String fType,String clr,Date MOT,boolean warr,
    Date lastSer,int mile){
    registration = reg;
    model = mdl;
    make = mke;
    engineSize = eSize;
    fuelType = fType;
    colour = clr;
    MOTRenewal = MOT;
    warranty = warr;
    lastService = lastSer;
    currentMile = mile;
  }
}