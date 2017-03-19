package specialist.logic;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.util.Callback;

/**
 *
 * @author do302
 * initialise instance with: 
 * private static ErrorChecks EC = ErrorChecks.getInstance();
 */
public class ErrorChecks 
{
    private static ErrorChecks instance = null;
    LocalDate Selection = null;
    private ErrorChecks(){    
    }
    
    public static ErrorChecks getInstance()
    {
        if(instance == null)
        {
            instance = new ErrorChecks();
        }
        return instance;
    }
    
    /**
    * Converts a DatePicker Object to String
    */
    public String toString(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        return sqlDate.toString();
    }
    
    /**
    * Converts a DatePicker Object to a Date object (java.sql.Date object not java.util.Date)
    */
    public Date toDate(DatePicker DatePickerObject)
    {
        java.sql.Date sqlDate = java.sql.Date.valueOf(DatePickerObject.getValue());
        return sqlDate;
    }
    
    /**
    *
    * Converts a LocalDate Object to String
    */
    public LocalDate StringtoLDate(String value)
    {
        final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate newDate = LocalDate.parse(value, DATE_FORMAT);
        return newDate;
    }
    
    /**
    *
    * Call with TextField object to Restrict input to numbers (allows for decimals)
    */
    public void SetNumberRestriction(TextField field)//Restrics input to numbers only
    {
        DecimalFormat format = new DecimalFormat( "#.0" );
        field.setTextFormatter( new TextFormatter<>(c ->
        {
        if (c.getControlNewText().isEmpty())
        {
            return c;
        }
        ParsePosition parsePosition = new ParsePosition( 0 );
        Object object = format.parse( c.getControlNewText(), parsePosition );
        if ( object == null || parsePosition.getIndex() < c.getControlNewText().length() )
        {
            return null;
        }
        else
        {
            return c;
        }
        }));
    }

    /**
    *
    * returns false if passed String is not Alphanumberic (letters and numbers)
    */
    public boolean isAlphanumeric(String word) 
    {
        //returns false if not alphanumberic
        return word.matches("[a-zA-Z]+");
    }
    
    /**
    *
    * returns false if not max 2 decimal places (use for checking money validity)
    */
    public boolean DecimalPlaces(double num) 
    {
        String numstr = Double.toString(num);
        String[] strarray = numstr.split("[.]");
        if (strarray.length == 2)
        {
            if (strarray[1].length() > 2)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
    *
    * returns false if String isn't a plate number (in other words if it's length is > 7 and contains special characters like @#*)
    */
    public boolean isPlate(String word) 
    {
        if (word.matches("[A-Za-z0-9]+"))
        {
            if(word.length() <= 7)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
    *
    * Displays a given message to a given JavaFX Text object for 2 seconds
    */
    public void TimedMsg(Text TextObject, String msg)
    {
        TextObject.setText(msg);
        Timer timer = new Timer();
            timer.schedule( 
            new java.util.TimerTask() 
            {
                public void run() 
                {
                    TextObject.setText("");
                    timer.cancel();// Terminate the thread
                }
            }, 2000
            );
    }
    
    /**
    *
    * Disables dates on a DatePicker object that are before given LocalDate Object 
    * (Can use LocalDate.now() for current day)
    * Note: StringToLdate changes a string to LocalDate
    */
    public void DisableDatesBefore(DatePicker dp, LocalDate LD)
    {
        Selection = LD;
        dp.setDayCellFactory(DCF);
    }
    
    public Callback< DatePicker, DateCell > DCF = (final DatePicker myDP) -> new DateCell()
    {
        @Override
        public void updateItem(LocalDate item , boolean empty)
        {
            // Must call super
            super.updateItem(item , empty);
            
            // disable all past dates + colours them light red
            if (item.isBefore(Selection))
            {
                this.setDisable (true)                        ;
                this.setStyle(" -fx-background-color: #FFD3D3; ") ;
            }
        }
    };
}
