/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author athanasiosgkanos
 */
public class ComponentLoader extends Application{
    public static void main(String[] args){
        Application.launch(ComponentLoader.class, (java.lang.String[])null);
    }
    
    @Override
    public void start(Stage primaryStage){
        try{
            StackPane page = (StackPane) FXMLLoader.load(ComponentLoader.class.getResource("Interface.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("GM-SIS");
            primaryStage.show();
        }catch(Exception ex){
            Logger.getLogger(ComponentLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
