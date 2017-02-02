/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

import java.util.Date;

/**
 *
 * @author joshuascott
 */
public class Van extends Vehicle{
 public Van(String reg,String mdl,String mke,int eSize,String fType,String clr,Date MOT,boolean warr,
    Date lastSer,int mile){
   super(reg,mdl,mke,eSize,fType,clr,MOT,warr,lastSer,mile);  
  }   
}
