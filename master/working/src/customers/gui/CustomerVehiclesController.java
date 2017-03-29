
/***********************************************
 * @author athanasiosgkanos - ec15252
 **********************************************/

package customers.gui;

import common.Main;
import customers.logic.Customer;
import customers.logic.CustomerRegistry;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vehicles.gui.EditTabController;
import vehicles.logic.Vehicle;
import vehicles.logic.VehicleRegistry;


public class CustomerVehiclesController {
    @FXML
    private TableView<Vehicle> vehTV;
    @FXML
    private TextField firstNameTF, lastNameTF, addressTF,cTypeTF,          
                      pCodeTF, phoneTF, emailTF;   
    @FXML
    private TableColumn registrationTC,makeTC,modelTC,engSizeTC,
                        fuelTypeTC,colourTC,lastServiceTC,mileageTC;
    private final ObservableList<Vehicle> obsListData = FXCollections.observableArrayList();                   
    
    CustomerRegistry CR = CustomerRegistry.getInstance();
    VehicleRegistry VR = VehicleRegistry.getInstance();
    private Customer tempCust;
    private int custID;
    private Vehicle tempVeh;

    
    public void setUser(int ID){
        this.tempCust = CR.searchCustomerByID(String.valueOf(ID));
        this.custID = ID;
        setUserContactDetails();
        setUserVehicleDetails();
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
    
    private void setUserVehicleDetails(){
        loadData(obsListData);
        registrationTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("registration"));
        makeTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("make"));
        modelTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("model"));
        engSizeTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("engineSize"));
        fuelTypeTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("fuelType"));
        colourTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("colour"));
        lastServiceTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("lastService"));
        mileageTC.setCellValueFactory(
                new PropertyValueFactory<Vehicle, String>("currentMile"));
        vehTV.setItems(obsListData);
        
        vehTV.setRowFactory((TableView<Vehicle> tv) -> {
            TableRow<Vehicle> row = new TableRow<>();
            row.setOnMouseClicked(event2 -> {
                if (! row.isEmpty() && event2.getButton()== MouseButton.PRIMARY 
                                    && event2.getClickCount() == 2) {
                    tempVeh = row.getItem();
                    if(tempVeh != null){
                        loadVehicleRecord(tempVeh);
                    }
                }
            });
            return row;
        });
    }
    
    private void loadVehicleRecord(Vehicle v){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/vehicles/gui/EditTab.fxml")); 
            Parent root = (Parent)fxmlLoader.load();
            EditTabController etc = fxmlLoader.getController();
            etc.set(v);
                     Scene scene = new Scene(root);
                     Stage stage = new Stage();
                     stage.setScene(scene);
                     stage.centerOnScreen();
                     stage.setTitle("Vehicle Record");
                     stage.initModality(Modality.WINDOW_MODAL);
                     stage.initOwner(Main.stage);
                     stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
  
    
    private void loadData(ObservableList<Vehicle> dataList){
        ArrayList<Vehicle> vhAList = VR.searchCustomerVehicles(custID);
        dataList.removeAll(dataList);
        if(vhAList != null &&
           !vhAList.isEmpty()){
            for(Vehicle v : vhAList){
               dataList.add(v);
            }
        }
    }
}
