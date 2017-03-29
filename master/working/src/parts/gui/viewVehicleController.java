/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import vehicles.logic.Vehicle;

/**
 *
 * @author jr308
 */
public class viewVehicleController implements Initializable {
    @FXML
    private TextArea vReg, vMake,vModel, vEngine, vFuel, vColour,
            vMOT, vWarranty, vLast, vMile, vType;
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    public void loadVehicleData(Vehicle v){
        vReg.setText(v.getRegistration());
        vMake.setText(v.getMake());
        vModel.setText(v.getModel());
        vEngine.setText(String.valueOf(v.getEngineSize()));
        vFuel.setText(v.getFuelType());
        vColour.setText(v.getColour());
        
        
    }
}