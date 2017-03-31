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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import parts.logic.Part;
import parts.logic.PartRegistry;
import specialist.logic.ErrorChecks;

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
    private ErrorChecks EC = ErrorChecks.getInstance();
    
    public void initialize(URL url, ResourceBundle rb) {
        EC.SetWordSpaceRestriction(partNameTextArea);
        EC.SetWordSpaceRestriction(partDescriptionTextArea);
    }
    public void loadPart(Part p){
        id = p.getId();
        //System.out.println(id);
        partNameTextArea.setText(p.getName());
        partDescriptionTextArea.setText(p.getDescription());
        partCostTextArea.setText(p.getCost());
        partStockLevelTextArea.setText(String.valueOf(p.getStocklevel()));
    }
    
    public void editPart(ActionEvent event){
        if(!editValidation())
            return;
        partR.editStockPart(id,partNameTextArea.getText(),partDescriptionTextArea.getText(),new BigDecimal(partCostTextArea.getText()),Integer.parseInt(partStockLevelTextArea.getText()));
        Stage stage = (Stage)editButton.getScene().getWindow();
        stage.close();
    }
    public boolean editValidation(){
        boolean flag = true;
        String errors = "You have inputted the following information incorrectly:\n";
        if(partNameTextArea.getText().trim().isEmpty())
        {
            errors += "Part Name is required\n";
            flag = false;
        }
        if(partDescriptionTextArea.getText().trim().isEmpty())
        {
            errors += "Part Description is required\n";
            flag = false;
        }
        if(partCostTextArea.getText().trim().isEmpty())
        {
            errors += "Part Cost is required\n";
            flag = false;
        }
        else
        {
            try{
                int n = Integer.parseInt(partCostTextArea.getText().trim());
                if(n<1){
                    errors += "Quantity must be an integer greater than 0\n";
                    flag = false;
                }
            }catch(NumberFormatException e){
                errors += "Part Cost must be a decimal number\n";
                flag = false;
            }
        }
        if(partStockLevelTextArea.getText().trim().isEmpty())
        {
            errors += "Quantity is required\n";
            flag = false;
        }
        else
        {
            try{
                Integer.parseInt(partStockLevelTextArea.getText().trim());
            }catch(NumberFormatException e){
                errors += "Quantity must be an integer\n";
                flag = false;
            }
        }
        if(!flag)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText(errors);
            alert.showAndWait();
            return flag;
        }
        return flag;
    }
}
