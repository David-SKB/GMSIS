package user.gui;

import common.Authentication;
import common.Main;
import customers.gui.CustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author athanasiosgkanos
 */
public class InterfaceController {
    
    @FXML
    private TabPane InterfaceTabPane;
    @FXML
    private Tab adminTab;
    @FXML
    private CustomerController customerController = new CustomerController();
    @FXML
    private AnchorPane custCon;
    
    public void tabSwitch(){
        this.InterfaceTabPane.getTabs().remove(adminTab);
    }
    
   public void custTest(){
        this.customerController.updateAchorPane(custCon);
    }
    
    public void logoutCustomer(ActionEvent evt){
        Main.stage.close();
        Authentication authenticate = new Authentication();
        authenticate.start(new Stage()); 
    }
}
