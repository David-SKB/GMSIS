package common;

import javafx.geometry.Insets;
import javafx.application.Application;
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

/**
 *
 * @author David O
 */
public class Authentication
{
    protected static String authorised;

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
        Text teamTitle = new Text("SE Group 9");
        Text scenetitle = new Text("GM-SIS Login");
        teamTitle.setFont(Font.font("Palatino", FontWeight.SEMI_BOLD, 25));
        scenetitle.setFont(Font.font("Palatino", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);
        
        //grid.setGridLinesVisible(true);//Shows gridlines (debug)
        
        //End Labels & Fields
        
        //Button
        Button btn = new Button("Login");
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
                    if (checkUsernamePassword(username, password) == true)
                    {
                        if (isAdmin(username) == true)
                        {
                            //Login to admin version of system
                            actiontarget.setText("Admin Logged in");
                            authorised = "Admin";
                        }
                        else
                        {
                            //Login to regular version of site
                            actiontarget.setText("User Logged in");
                            authorised = "Admin";
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
        
        String SQL = "SELECT USERNAME,PASSWORD FROM AUTHENTICATION WHERE USERNAME = '" + username + "' AND PASSWORD = '" + password + "';";
        //String SQL = "SELECT PASSWORD FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);
        String result = "";
        try
        {
            while(rs.next())
            {
                //System.out.println("wag1 fam");
                System.out.println(rs.getString("USERNAME"));
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
        return false;
    }
    
    public static boolean isAdmin(String username){
        DBConnection c = DBConnection.getInstance();
        c.connect();
        String SQL = "SELECT SYSADM FROM USERS WHERE USERNAME = 'username';";
        //String SQL = "SELECT SYSADM FROM AUTHENTICATION WHERE USERNAME regexp '[[:<:]]" + username + "[[:>:]]';";
        ResultSet rs = c.query(SQL);
        Boolean result = false;

        try
        {
            while(rs.next())
            {
                result = rs.getBoolean("SYSADM");//retrieves the users level
            }
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        c.closeConnection();
        return result;
    }  
}
