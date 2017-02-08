/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author athanasiosgkanos
 */
import javafx.event.EventHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class CustomerController 
    implements Initializable {

    @FXML
    private Button getActiveCustomers;
    
    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources){
        assert getActiveCustomers != null : "fx:id=\"myButton\" was not injected: check your FXML file 'Interface.fxml'.";
        
         getActiveCustomers.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("That was easy, wasn't it?");
            }
        });
    }
    
}