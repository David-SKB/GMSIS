/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joshuascott
 */
public class Test {
    public static void main(String[] args) {
     VehicleRegistry vr = new VehicleRegistry();
      vr.addCar(1,1,"VW","Golf",2000,"Petrol","red",null,null,20000);
       if(vr.checkExists(1)){
        System.out.println("car was found therefore was successfully added"); 
       }
    }  
}