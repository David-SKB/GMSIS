/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers.gui;

import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

/**
 *
 * @author athanasiosgkanos
 */
public class CustomerVehiclesController {
    @FXML
    private TextField firstNameTF, lastNameTF, addressTF,cTypeTF,          
                      pCodeTF, phoneTF, emailTF;   
    /*@FXML
    private TableColumn registrationTC,makeTC,modelTC,engSizeTC,
                        fuelTypeTC,colourTC,lastServiceTC,mileageTC;*/
   // private final ObservableList<Vehicle> obsListData = FXCollections.observableArrayList();                   
    
    CustomerRegistry CR = CustomerRegistry.getInstance();
    private Customer tempCust;
    
    public void setUser(int ID){
        this.tempCust = CR.searchCustomerByID(String.valueOf(ID));
        setUserContactDetails();
       // setUserVehicleDetails();
    }

    
    private void setUserContactDetails(){
        firstNameTF.setText(tempCust.getFirstname());
        lastNameTF.setText(tempCust.getSurname());
        addressTF.setText(tempCust.getAddress());
        pCodeTF.setText(tempCust.getPostCode());
        phoneTF.setText(tempCust.getPhone());
        emailTF.setText(tempCust.getEmail());
        cTypeTF.setText(tempCust.getCustomerType());
    }
    
    /*private void setUserVehicleDetails(){
        loadData(obsListData);
    }*/
    
    /*private void loadData(ObservableList<Vehicle> dataList){
        ArrayList<Vehicle> vhAList = ;
        dataList.removeAll(dataList);
        if(vhAList != null){
            for(Vehicle v : vhAList){
               dataList.add(v);
            }
        }
    }*/
}
