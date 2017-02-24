import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

import javafx.stage.Stage;
/**
 *
 * @author athanasiosgkanos
 */
public class componentLoader extends Application{
    private Parent rootNode;
    
    public static void main(String[] args){
        Application.launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
       Parent parent = FXMLLoader.load(getClass().getResource("vehicle.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Hello World!");
        stage.setScene(scene);
        stage.show(); 
    }
    
    
}