/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import parts.logic.Part;
import parts.logic.PartRegistry;

/**
 *
 * @author jr308
 */



public class ViewDeliveriesController implements Initializable {
    PartRegistry partR = PartRegistry.getInstance();
    @FXML
    private TableView<Delivery> deliveryTable;
    @FXML
    private TableColumn deliveryPartNameCol, deliveryQuantityCol, deliveryDateCol;
    private final ObservableList<Delivery> oDeliveryList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDeliveriesList();
        loadDeliveriesTable();
    }
    public void loadDeliveriesList(){
        System.out.println("test4");
        oDeliveryList.clear();
        ArrayList<Delivery> deliveryList = partR.getDeliveries();

        if (deliveryList != null) {
            System.out.println("inside if");
            for (int i = 0; i < deliveryList.size(); i++) {
                System.out.println(deliveryList.get(i).getPart());
                oDeliveryList.add(deliveryList.get(i));
            }
        }

    }
    
    public void loadDeliveriesTable(){
        deliveryTable.setEditable(true);
        deliveryPartNameCol.setCellValueFactory(
                new PropertyValueFactory<Delivery, String>("part"));
        deliveryQuantityCol.setCellValueFactory(
                new PropertyValueFactory<Delivery, Integer>("quantity"));
        deliveryDateCol.setCellValueFactory(
                new PropertyValueFactory<Delivery, String>("date"));
        deliveryTable.setItems(oDeliveryList);
    }
}
