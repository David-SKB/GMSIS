/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.logic;

/**
 *
 * @author JR
 */
import java.math.BigDecimal;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Part {
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty cost;
    private final SimpleIntegerProperty stocklevel;
    
    public Part(int i, String n, String d, BigDecimal c, int s){
        id = new SimpleIntegerProperty(i);
        name = new SimpleStringProperty(n);
        description = new SimpleStringProperty(d);
        cost = new SimpleStringProperty(c.toString());
        stocklevel = new SimpleIntegerProperty(s);
    }
    
    public Part(String n, String d, BigDecimal c, int s){
        id = new SimpleIntegerProperty(0);
        name = new SimpleStringProperty(n);
        description = new SimpleStringProperty(d);
        cost = new SimpleStringProperty(c.toString());
        stocklevel = new SimpleIntegerProperty(s);
    }
    //test
    public int getID(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public String getDescription(){
        return description.get();
    }
    public String getCost(){
        return cost.get();
    }
    public int getStocklevel(){
        return stocklevel.get();
    }
}
