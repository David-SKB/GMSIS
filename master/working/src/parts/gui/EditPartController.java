/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import parts.logic.Part;
import parts.logic.PartRegistry;

/**
 *
 * @author jr308
 */
public class EditPartController implements Initializable {
    private PartRegistry partR = PartRegistry.getInstance();
    private int id;
    @FXML
    Button editButton;
    @FXML
    private TextArea partNameTextArea, partDescriptionTextArea,
            partCostTextArea, partStockLevelTextArea;
    
    public void initialize(URL url, ResourceBundle rb) {
        //loadDeliveriesTable();
    }
    public void loadPart(Part p){
        id = p.getId();
        System.out.println(id);
        partNameTextArea.setText(p.getName());
        partDescriptionTextArea.setText(p.getDescription());
        partCostTextArea.setText(p.getCost());
        partStockLevelTextArea.setText(String.valueOf(p.getStocklevel()));
    }
    
    public void editPart(ActionEvent event){
        partR.editStockPart(id,partNameTextArea.getText(),partDescriptionTextArea.getText(),new BigDecimal(partCostTextArea.getText()),Integer.parseInt(partStockLevelTextArea.getText()));
        Stage stage = (Stage)editButton.getScene().getWindow();
        stage.close();
    }
    
}
