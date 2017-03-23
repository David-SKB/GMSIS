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

/**
 *
 * @author jr308
 */



public class ViewDeliveriesController implements Initializable {
    @FXML
    private TableView<Delivery> deliveriesTable;
    @FXML
    private TableColumn deliveriesPartNameCol, deliveriesQuantityCol, deliveriesDateCol;
    private final ObservableList<Delivery> oDeliveryList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDeliveriesList();
        loadDeliveriesTable();
    }
    public void loadDeliveriesList(){
        System.out.println("test4");
        oDeliveryList.clear();
        ArrayList<Delivery> deliveryList = new ArrayList<Delivery>();

        if (deliveryList != null) {
            System.out.println("inside if");
            for (int i = 0; i < deliveryList.size(); i++) {
                System.out.println("inside for");
                oDeliveryList.add(deliveryList.get(i));
            }
        }

    }
    
    public void loadDeliveriesTable(){
        deliveriesTable.setEditable(true);
        deliveriesPartNameCol.setCellValueFactory(
                new PropertyValueFactory<Delivery, String>("part"));
        deliveriesQuantityCol.setCellValueFactory(
                new PropertyValueFactory<Delivery, Integer>("quantity"));
        deliveriesDateCol.setCellValueFactory(
                new PropertyValueFactory<Delivery, String>("date"));
        deliveriesTable.setItems(oDeliveryList);
    }
}
