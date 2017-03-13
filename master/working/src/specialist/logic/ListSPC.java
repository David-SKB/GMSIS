package specialist.logic;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ListSPC
{
    private final SimpleIntegerProperty T3IDX;
    private final SimpleStringProperty T3SPCX;
    
    public ListSPC(int t3id, String t3spc)
    {
        this.T3IDX = new SimpleIntegerProperty(t3id);
        this.T3SPCX = new SimpleStringProperty(t3spc);
    }
    
    public int getT3IDX()
    {
        return T3IDX.get();
    }
    
    public String getT3SPCX()
    {
        return T3SPCX.get();
    }
    
}
