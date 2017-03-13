package specialist.logic;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DisplayVehicle 
{ 
    private final SimpleStringProperty T4REGX;
    private final SimpleStringProperty T4MAKEX;
    private final SimpleStringProperty T4MODELX;
    private final SimpleStringProperty T4FUELX;
    private final SimpleIntegerProperty T4MILEAGEX;
    private final SimpleStringProperty T4COLORX;
    
    public DisplayVehicle(String t4reg, String t4make, String t4model, String t4fuel, int t4mileage, String t4color)
    {
        this.T4REGX = new SimpleStringProperty(t4reg);
        this.T4MAKEX = new SimpleStringProperty(t4make);
        this.T4MODELX = new SimpleStringProperty(t4model);
        this.T4FUELX = new SimpleStringProperty(t4fuel);
        this.T4MILEAGEX = new SimpleIntegerProperty(t4mileage);
        this.T4COLORX = new SimpleStringProperty(t4color);
    }
    
    public String getT4REGX()
    {
        return T4REGX.get();
    }
    
    public String getT4MAKEX()
    {
        return T4MAKEX.get();
    }
    
    public String getT4MODELX()
    {
        return T4MODELX.get();
    }
    
    public String getT4FUELX()
    {
        return T4FUELX.get();
    }
    
    public int getT4MILEAGEX()
    {
        return T4MILEAGEX.get();
    }
    
    public String getT4COLORX()
    {
        return T4COLORX.get();
    }
}
