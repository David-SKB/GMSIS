package specialist.logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OutstandingMain
{
    
    private final SimpleStringProperty T5REGNOX;
    private final SimpleStringProperty T5EXPDELX;
    private final SimpleStringProperty T5EXPRETX;
    private final SimpleDoubleProperty T5COSTX;
    private final SimpleStringProperty T5SPCNAMEX;
    
    public OutstandingMain(String t1reg, String t5expdel, String t5expret, double t5cost, String t5type, String t5spcid)
    {
        this.T5REGNOX = new SimpleStringProperty(t1reg);
        this.T5EXPDELX = new SimpleStringProperty(t5expdel);
        this.T5EXPRETX = new SimpleStringProperty(t5expret);
        this.T5COSTX = new SimpleDoubleProperty(t5cost);
        this.T5SPCNAMEX = new SimpleStringProperty(t5spcid);
    }
    
    public String getT5REGNOX()
    {
        return T5REGNOX.get();
    }
    
    public String getT5EXPDELX()
    {
        return T5EXPDELX.get();
    }
    
    public String getT5EXPRETX()
    {
        return T5EXPRETX.get();
    }
    
    public double getT5COSTX()
    {
        return T5COSTX.get();
    }
    
    public String getT5SPCNAMEX()
    {
        return T5SPCNAMEX.get();
    }
}
