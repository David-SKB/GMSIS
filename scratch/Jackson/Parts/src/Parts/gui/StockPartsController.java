/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts.gui;

import Parts.PartRegistry;
import Database.DBConnection;
import Parts.Part;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author jr308
 */
public class StockPartsController implements Initializable {
    
    private PartRegistry partR = PartRegistry.getInstance();
    private TableView<Part> stockTable;
    private TableColumn nameCol, descriptionCol, costCol,                          //FXML TableColumn. Columns form the TableView element.
                        stockCol;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test");
        partR.addPart("wheel", "circular", 100);
        System.out.println("test2");
        loadStockParts();
    }   
        
    public void loadStockParts(){//ActionEvent event){
        System.out.println("test3");
        ArrayList<Part> partList = partR.getStockParts();
        ObservableList<Part> oPartList = FXCollections.observableArrayList(partList);
        stockTable.setEditable(true);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("NAME"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("DESCRIPTION"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("COST"));
        stockCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("STOCK"));
        stockTable.setItems(oPartList);
    }
    
}
