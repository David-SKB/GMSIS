package common;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import user.gui.InterfaceController;
/**
 *
 * @author David O
 */
public class Authentication
{
    
    private static String tempName;
    private static String tempLastName;
    
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("GM-SIS Login Page");
        primaryStage.getIcons().add(new Image("/user/gui/icon2.png"));
        //GridCode
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #fff4e8;");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        //Labels & Fields
        Text scenetitle = new Text("GM-SIS Login");
        scenetitle.setFont(Font.font("Palatino", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        userName.setFont(Font.font("Palatino", FontWeight.NORMAL, 20));
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        pw.setFont(Font.font("Palatino", FontWeight.NORMAL, 20));
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);        
        
        //End Labels & Fields
        
        //Button
        Button btn = new Button("Login");
        btn.setFont(Font.font("Palatino", FontWeight.NORMAL, 20));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        //End Button
        
        //Text position
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        //End Text position
        
        //Button action
        btn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent e) 
            {
                if ((userTextField.getText() != null && !userTextField.getText().isEmpty()) && (pwBox.getText() != null && !pwBox.getText().isEmpty())) 
                {                
                    String username = userTextField.getText();
                    String password = pwBox.getText();
                    if (checkUsernamePassword(username, password))
                    {
                       if (isAdmin(username))
                        {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/user/gui/Interface.fxml"));
                                Parent root = (Parent)fxmlLoader.load();
                                Scene scene = new Scene(root);
                                Stage stage = Main.stage;
                                stage.setScene(scene);
                                stage.centerOnScreen();
                                stage.setTitle("GM-SIS Home");
                                stage.getIcons().add(new Image("/user/gui/icon2.png"));
                                primaryStage.close();
                                InterfaceController IC = fxmlLoader.<InterfaceController>getController();
                                IC.showWelcome(tempName + " " + tempLastName);
                                stage.show();   
                            } catch (IOException ex) {
                                Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {                                
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/user/gui/Interface.fxml"));
                                Parent root = (Parent)fxmlLoader.load();
                                Scene scene = new Scene(root);
                                Stage stage = Main.stage;
                                stage.setScene(scene);
                                stage.centerOnScreen();
                                stage.setTitle("GM-SIS Home");
                                stage.getIcons().add(new Image("/user/gui/icon2.png"));
                                primaryStage.close();
                                InterfaceController IC = fxmlLoader.<InterfaceController>getController();
                                IC.tabSwitch();
                                IC.showWelcome(tempName + " " + tempLastName);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    else
                    {
                        actiontarget.setText("Incorrect username/password");
                    }
                } 
                else 
                {
                    actiontarget.setText("Enter username and password");
                }
            }
        });
        //End Button action
        
        Scene scene = new Scene(grid, 700, 400);
        primaryStage.setScene(scene);
        //End Gridcode
        
        primaryStage.show();
    }
    
    public boolean checkUsernamePassword(String username, String password){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        
        String SQL = "SELECT * FROM USERS WHERE ID = '" + username + "' AND PASSWORD = '" + password + "';";        
        ResultSet rs = c.query(SQL);
        try{
            tempName = rs.getString("FIRSTNAME");
            tempLastName = rs.getString("SURNAME");
            return rs.isBeforeFirst();
        }catch(SQLException e){
            return false;
        }
    }
    
    public boolean isAdmin(String username){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        String SQL = "SELECT SYSADM FROM USERS WHERE ID = " + username + ";";       
        ResultSet rs = c.query(SQL);
        try{
            String check = rs.getString("SYSADM");
            c.closeConnection();
            return check.equalsIgnoreCase("TRUE");
        }catch(Exception e){
            c.closeConnection();
            return false;
        }
    }  
}
