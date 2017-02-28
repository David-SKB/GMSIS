/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author athanasiosgkanos
 */
public class Main extends Application{
    private Parent rootNode;
    protected static Stage stage;
    
    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Authentication authenticate = new Authentication();
        authenticate.start(new Stage());
        this.stage = stage;
    }
}
