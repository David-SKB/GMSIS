/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import database.DatabaseCreation;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author athanasiosgkanos
 */
public class Main extends Application{
    private Parent rootNode;
    public static Stage stage;
    public static void main(String[] args){
        Application.launch(args);
    }
    
    @Override
    public void init() {
        //only needed if db is kept outside or jar
        File f = new File("src/database/gmsisdb.db");
        if(!f.exists() && !f.isDirectory()) { 
            DatabaseCreation.createDB();
        }
    }
    

    @Override
    public void start(Stage stage) throws Exception {

        Authentication authenticate = new Authentication();
        authenticate.start(new Stage());
        this.stage = stage;
    }
}
