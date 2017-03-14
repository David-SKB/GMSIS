/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import parts.logic.PartRegistry;
import common.DBConnection;
import parts.logic.Part;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
//
/**
 * FXML Controller class
 *
 * @author jr308
 */
public class StockPartsController implements Initializable {
    
    private PartRegistry partR = PartRegistry.getInstance();
    
    private final ObservableList<Part> oPartList = FXCollections.observableArrayList();
    //Stock Parts
    @FXML
    private AnchorPane stockParts;
    
    @FXML
    private AnchorPane usedParts;
    
    @FXML 
    private AnchorPane repairs;
   
    private Part selectedPart;
    
    //stock parts gui
    @FXML
    private TableView<Part> stockTable;
    @FXML
    private TableColumn nameCol, descriptionCol, costCol,                          //FXML TableColumn. Columns form the TableView element.
                        stockCol;
    @FXML
    private TextField quantityTextField, searchTextField;
    @FXML
    private TextArea partNameTextArea, partDescriptionTextArea,
            partCostTextArea, partStockLevelTextArea;
    
    

   @Override
   public void initialize(URL url, ResourceBundle rb) {
        System.out.println("test");
        //partR.addPart("axis", "circular", 100);
        System.out.println("test2");
        stockParts.setVisible(true);
        usedParts.setVisible(false);
        repairs.setVisible(false);
        setupRowListeners();
        loadAllParts();
    }   
    
    //STOCK METHODS
    //loads oList into stock table
    public void loadStockParts(){//ActionEvent event){
        
        //System.out.println("test3");
        //loadAllParts();
        stockTable.setEditable(true);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("description"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("cost"));
        stockCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("stocklevel"));
        stockTable.setItems(oPartList);
        //System.out.println("test3");
    }
    
    public void loadAllParts(){//ActionEvent event){
        System.out.println("test4");
        oPartList.clear();
        ArrayList<Part> partlist = partR.getStockParts();
        System.out.println(partlist == null);
        System.out.println(partlist == null);
        if(partlist != null)
        {
            System.out.println("inside if");
            for(int i = 0; i < partlist.size(); i++)
            {
                System.out.println("inside for");
                oPartList.add(partlist.get(i));
            }
        }
        loadStockParts();
    }
    
    public void updateStockLevel(ActionEvent event){
        System.out.println("in update stock level");
        Part selectedPart = stockTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedPart.getName());  
        partR.updateStock(selectedPart.getName(), Integer.parseInt(quantityTextField.getText()));
        loadAllParts();
    }
    
   public void addStockPart(ActionEvent event){
        String name = partNameTextArea.getText();
        String description = partDescriptionTextArea.getText();
        int cost = Integer.parseInt(partCostTextArea.getText());
        //String stocklevel = partStockLevelTextArea.getText();
        partR.addPart(name, description, cost);
        loadAllParts();
    }
    
    public void searchParts(ActionEvent event){
        oPartList.clear();
        ArrayList<Part> partlist = partR.searchStockParts(searchTextField.getText(),"NAME");
        System.out.println(partlist == null);
        if(partlist != null)
        {
            System.out.println("inside if");
            for(int i = 0; i < partlist.size(); i++)
            {
                System.out.println("inside for");
                oPartList.add(partlist.get(i));
            }
        }
        loadStockParts();
    }
    
    private void setupRowListeners() {
        stockTable.setRowFactory( table ->{
            TableRow<Part> row = new TableRow<>();

            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && (!row.isEmpty()) ) {
                    selectedPart = stockTable.getSelectionModel().getSelectedItem();
                    loadPartIntoFields();
                }
            });
            return row;
        });
    }
    
    public void loadPartIntoFields()
    {
        partNameTextArea.setText(selectedPart.getName());
        partDescriptionTextArea.setText(selectedPart.getDescription());
        partCostTextArea.setText(selectedPart.getCost());
        partStockLevelTextArea.setText(selectedPart.getStocklevel());
    }
    
    //USED PARTS METHODS
    public void loadUsedParts(){//ActionEvent event){
        
        System.out.println("test3");
        loadAllParts();
        stockTable.setEditable(true);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("name"));
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("description"));
        costCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("cost"));
        stockCol.setCellValueFactory(
                new PropertyValueFactory<Part, String>("stocklevel"));
        stockTable.setItems(oPartList);
        
    }
    
    //REPAIRS METHODS
    
    
    //methods for changing anchor
    public void viewUsedPartsAnchor(ActionEvent event){
        repairs.setVisible(false);
        stockParts.setVisible(false);
        usedParts.setVisible(true);
    }
    public void viewStockPartsAnchor(ActionEvent event){
        repairs.setVisible(false);
        usedParts.setVisible(false);
        stockParts.setVisible(true);
    }
    public void viewRepairsAnchor(ActionEvent event){
        stockParts.setVisible(false);
        usedParts.setVisible(false);
        repairs.setVisible(true);
    }
    
}
//AnchorPane pane = FXMLLoader.load(getClass().getResource("mainFXML.fxml"));
//rootPane.getChildren().setAll(pane);
