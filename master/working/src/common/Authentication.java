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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
/**
 *
 * @author David O
 */
public class Authentication
{
    
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("GM-SIS Login Page");
        
        //GridCode
        GridPane grid = new GridPane();
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
        
        //grid.setGridLinesVisible(true);//Shows gridlines (debug)
        
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
                    //actiontarget.setText("Username: "+ userTextField.getText() + " Password: " + pwBox.getText());//shows username and pass (debug)
                    String username = userTextField.getText();
                    String password = pwBox.getText();
                    if (checkUsernamePassword(username, password))
                    {
                       if (isAdmin(username))
                        {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/user/gui/Interface.fxml"));
                                Scene scene = new Scene(root);
                                Stage stage = Main.stage;
                                stage.setScene(scene);
                                stage.centerOnScreen();
                                stage.setTitle("GM-SIS Home");
                                primaryStage.close();
                                stage.show();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else
                        {
                                
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("/user/gui/Interface.fxml"));
                                Scene scene = new Scene(root);
                                Stage stage = Main.stage;
                                stage.setScene(scene);
                                stage.centerOnScreen();
                                stage.setTitle("GM-SIS Home");
                                primaryStage.close();
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
    
    public static boolean checkUsernamePassword(String username, String password){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        
        String SQL = "SELECT ID,PASSWORD FROM USERS WHERE ID = '" + username + "' AND PASSWORD = '" + password + "';";
        //String SQL = "SELECT PASSWORD FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);
        if(rs != null){
            return true;
        }else{
            return false;
        }
        /*try
        {
            while(rs.next())
            {
                //System.out.println("wag1 fam");
                System.out.println(rs.getString("ID"));
                System.out.println(rs.getString("PASSWORD"));
                //result = rs.getString("PASSWORD");//retrieves the password
                return true;
            }
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    
        }
        if (result.equals(password))
        {
            return true;
        }
        return false;*/
    }
    
    public static boolean isAdmin(String username){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        String SQL = "SELECT SYSADM FROM USERS WHERE ID = " + username + ";";
        //String SQL = "SELECT SYSADM FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);

        try{
            String check = rs.getString("SYSADM");
            c.closeConnection();
            if(check.equals("TRUE")){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            c.closeConnection();
            return false;
        }
        //Boolean result = false;

        /*try
        {
            while(rs.next())
            {
                result = rs.getBoolean("SYSADM");//retrieves the users level
            }
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }*/

        //return result;
    }  
}
