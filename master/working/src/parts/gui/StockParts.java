package parts.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jr308
 */
public class StockParts extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("viewDeliveries.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();  
        //test
    }
    
    public static void main(String[] args){
        System.out.println("hi");
        launch(args);
    }
    
}
