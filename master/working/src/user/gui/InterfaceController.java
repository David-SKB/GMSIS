package user.gui;

import common.Authentication;
import common.Main;
import customers.gui.CustomerController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import specialist.gui.RepairsController;

/**
 *
 * @author athanasiosgkanos
 */
public class InterfaceController implements Initializable{
    
    @FXML
    private TabPane InterfaceTabPane;
    @FXML
    private Tab adminTab;
    @FXML
    private CustomerController customerController = new CustomerController();
    @FXML
    private AnchorPane customerAP;
    @FXML
    private AnchorPane SPCAP;
    @FXML
    private RepairsController RPC = new RepairsController();
    @FXML
    private AdminController adminController = new AdminController();
    @FXML
    private AnchorPane adminAP;
    @FXML
    private Label welcomeUser;

     public void spcUpdate(){
         this.RPC.updateAnchorPane(SPCAP);
     }
    
    public void showWelcome(String user){
        welcomeUser.setText("Welcome, " + user + "!");
    }
    
    public void tabSwitch(){
        this.InterfaceTabPane.getTabs().remove(adminTab);
    }
    
    public void adminUpdate(){
        this.adminController.updateAnchorPane(adminAP);
    }
    
    public void customerUpdate(){
        this.customerController.updateAchorPane(customerAP);
    }
    
    public void logoutCustomer(ActionEvent evt){
        Main.stage.close();
        Authentication authenticate = new Authentication();
        authenticate.start(new Stage()); 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.InterfaceTabPane.getSelectionModel().select(0);
    }
}
