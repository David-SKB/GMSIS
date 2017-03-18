package specialist.logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RepairParts 
{
    private final SimpleIntegerProperty T2IDX;
    private final SimpleStringProperty T2REGNOX;
    private final SimpleStringProperty T2NAMEX;
    private final SimpleStringProperty T2SPCX;
    private final SimpleStringProperty T2EXPDELX;
    private final SimpleStringProperty T2EXPRETX;
    private final SimpleDoubleProperty T2COSTX;
    
    public RepairParts(int t2id, String t2reg, String t2name, String t2spc, String t2expdel, String t2expret, double t2cost)
    {
        this.T2IDX = new SimpleIntegerProperty(t2id);
        this.T2REGNOX = new SimpleStringProperty(t2reg);
        this.T2NAMEX = new SimpleStringProperty(t2name);
        this.T2SPCX = new SimpleStringProperty(t2spc);
        this.T2EXPDELX = new SimpleStringProperty(t2expdel);
        this.T2EXPRETX = new SimpleStringProperty(t2expret);
        this.T2COSTX = new SimpleDoubleProperty(t2cost);
    }
    
    public int getT2IDX()
    {
        return T2IDX.get();
    }
    
    public String getT2REGNOX()
    {
        return T2REGNOX.get();
    }
    
    public String getT2NAMEX()
    {
        return T2NAMEX.get();
    }
    
    public String getT2SPCX()
    {
        return T2SPCX.get();
    }
    
    public String getT2EXPDELX()
    {
        return T2EXPDELX.get();
    }
    
    public String getT2EXPRETX()
    {
        return T2EXPRETX.get();
    }
    
    public double getT2COSTX()
    {
        return T2COSTX.get();
    }
}
