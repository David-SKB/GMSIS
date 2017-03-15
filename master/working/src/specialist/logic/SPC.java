package specialist.logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SPC
{
    private final SimpleIntegerProperty IDX;
    private final SimpleStringProperty NAMEX;
    private final SimpleStringProperty ADDRESSX;
    private final SimpleStringProperty TELEPHONEX;
    private final SimpleStringProperty EMAILX;

    public SPC(int id, String name, String address,  String telephone, String email)
    {
        this.IDX = new SimpleIntegerProperty(id);
        this.NAMEX = new SimpleStringProperty(name);
        this.ADDRESSX = new SimpleStringProperty(address);
        this.TELEPHONEX = new SimpleStringProperty(telephone);
        this.EMAILX = new SimpleStringProperty(email);
    }
    
    public int getIDX()
    {
        return IDX.get();
    }
    
    public String getNAMEX()
    {
        return NAMEX.get();
    }
    
    public String getADDRESSX()
    {
        return ADDRESSX.get();
    }
    
    public String getTELEPHONEX()
    {
        return TELEPHONEX.get();
    }
    
    public String getEMAILX()
    {
        return EMAILX.get();
    }
}
