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

public class Part {
    private String name;
    private String description;
    private int id;
    private Date installationDate;
    private Date warrantyExpiry;
    
    public Part(String n, String d, Date i, Date w){
        name = n;
        description = d;
        installationDate = i;
        warrantyExpiry = w;
    }
    
}
