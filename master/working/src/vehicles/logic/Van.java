/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicles.logic;

import java.util.Date;

/**
 *
 * @author joshuascott
 */
public class Van extends Vehicle{
 public Van(String reg,int custID,String type,String mdl,String mke,double eSize,String fType,String clr,String MOT,boolean warr,
    String lastSer,int mile){
   super(reg,custID,type,mdl,mke,eSize,fType,clr,MOT,warr,lastSer,mile);  
  }   
}
