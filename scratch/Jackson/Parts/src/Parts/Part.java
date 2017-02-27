/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parts;

/**
 *
 * @author JR
 */
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

public class Part {
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private int id;
    private final SimpleStringProperty cost;
    private final SimpleStringProperty stocklevel;
    
    public Part(String n, String d, String c, String s){
        name = new SimpleStringProperty(n);
        description = new SimpleStringProperty(d);
        cost = new SimpleStringProperty(c);
        stocklevel = new SimpleStringProperty(s);
    }
    //test
}
