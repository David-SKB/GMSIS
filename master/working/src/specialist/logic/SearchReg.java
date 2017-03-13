package specialist.logic;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class SearchReg extends SearchMain
{
    private final SimpleIntegerProperty T1IDX;
    private final SimpleStringProperty T1REGX;
    private final SimpleStringProperty T1SPCX;
    private final SimpleStringProperty T1EXPDELX;
    private final SimpleStringProperty T1EXPRETX;
    private final SimpleDoubleProperty T1COSTX;

    public SearchReg(int t1id, String t1Reg, String t1spc, String t1expdel, String t1expret, double t1cost)
    {
        super(t1id, t1Reg, t1spc, t1expdel, t1expret, t1cost);
        this.T1IDX = new SimpleIntegerProperty(t1id);
        this.T1REGX = new SimpleStringProperty(t1Reg);
        this.T1SPCX = new SimpleStringProperty(t1spc);
        this.T1EXPDELX = new SimpleStringProperty(t1expdel);
        this.T1EXPRETX = new SimpleStringProperty(t1expret);
        this.T1COSTX = new SimpleDoubleProperty(t1cost);
    }
    
    public int getT1IDX()
    {
        return T1IDX.get();
    }
    
    public String getT1REGX()
    {
        return T1REGX.get();
    }
    
    public String getT1SPCX()
    {
        return T1SPCX.get();
    }
    
    public String getT1EXPDELX()
    {
        return T1EXPDELX.get();
    }
    
    public String getT1EXPRETX()
    {
        return T1EXPRETX.get();
    }
    
    public double getT1COSTX()
    {
        return T1COSTX.get();
    }
}
