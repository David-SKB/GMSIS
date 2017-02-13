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
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;

import javafx.stage.Stage;
/**
 *
 * @author athanasiosgkanos
 */
public class ComponentLoader extends Application{
    private Parent rootNode;
    
    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void init() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Interface.fxml"));
        rootNode = fxmlLoader.load();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(rootNode));
        stage.show();
    }
    
    
}
