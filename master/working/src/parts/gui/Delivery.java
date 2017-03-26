/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parts.gui;

import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import parts.logic.Part;

/**
 *
 * @author jr308
 */
public class Delivery {
    private SimpleStringProperty part;
    private SimpleIntegerProperty quantity;
    private SimpleStringProperty date;
    
    public Delivery(String p, int q, String d){
        part = new SimpleStringProperty(p);
        quantity = new SimpleIntegerProperty(q);
        date = new SimpleStringProperty(d);
    }
    
    public String getPart(){
        return part.get();
    }
    
    public int getQuantity(){
        return quantity.get();
    }
    
    public String getDate(){
        return date.get();
    }
}
