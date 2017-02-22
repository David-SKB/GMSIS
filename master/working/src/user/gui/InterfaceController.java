package user.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author athanasiosgkanos
 */
public class InterfaceController {
    
    @FXML
    private TabPane InterfaceTabPane;
    @FXML
    private Tab adminTab;
    
    public void tabSwitch(){
        this.InterfaceTabPane.getTabs().remove(adminTab);
    }
}
