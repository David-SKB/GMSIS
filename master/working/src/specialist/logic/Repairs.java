package specialist.logic;
import common.DBConnection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Repairs
{
    private static Repairs RInstance = null;
    private DBConnection DBC = DBConnection.getInstance();

    public Repairs()
    {
        
    }
    
    public boolean addVehicle(String RegNo, int SPCID, Date ExpDel, Date ExpRet, double Cost)
    {
        boolean success;
        DBC.connect();       
        String SQL = "INSERT INTO REPAIRVEHICLE (REGNO, SPCID, DELIVERYDATE, RETURNDATE, COST) " + 
                           "VALUES ( '" + 
                            RegNo + "', '" +
                            SPCID + "', '" +
                            ExpDel + "', '" +
                            ExpRet + "', '" +
                            Cost + "' );";
        success = DBC.update(SQL);
        DBC.closeConnection();
        System.out.println(SQL);//view sql output
        return success;
    }

    public boolean addPart(String Name, String Desc, int ID, int SPCID, Date ExpDel, Date ExpRet, double Cost)
    {
        boolean success;
        DBC.connect();       
        String SQL = "INSERT INTO REPAIRPARTS (PARTNAME, DESC, PARTID, SPCID, DELIVERYDATE, RETURNDATE, COST) " + 
                           "VALUES ( '" + 
                            Name + "', '" +
                            Desc + "', '" +
                            ID + "', '" +
                            SPCID + "', '" +
                            ExpDel + "', '" +
                            ExpRet + "', '" +
                            Cost + "' );";
        success = DBC.update(SQL);
        DBC.closeConnection();
        return success;
    }
    
    public boolean editVehicle(String RegNo, int SPCID, Date ExpDel, Date ExpRet, double Cost, int RepairID)
    {
        System.out.println("entered2");
        boolean success;
        DBC.connect();       
        String SQL ="UPDATE REPAIRVEHICLE \n" + 
                    "SET " +
                    "REGNO = '" + RegNo + "', " +
                    "SPCID = '" + SPCID + "', " +
                    "DELIVERYDATE = '" + ExpDel + "', " +
                    "RETURNDATE = '" + ExpRet + "', " +
                    "COST = '" + Cost + "' \n" + 
                    "WHERE ID = '" + RepairID +  "';";
        //System.out.println(SQL);//view sql output
        success = DBC.update(SQL);
        DBC.closeConnection();
        
        return success;
    }
    
    public ObservableList<ListSPC> getSPCList() throws SQLException
    {
        ObservableList<ListSPC> SPCList = FXCollections.observableArrayList();
        DBC.connect();
        String SQL = "SELECT SPCID, NAME FROM CENTRES ";
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
        {
            SPCList.add(new ListSPC(rs.getInt("SPCID"), rs.getString("NAME")));
        }
        DBC.closeConnection();
        return SPCList;
    }
    
    public ObservableList<String> getSPCListCombo() throws SQLException
    {
        ObservableList<String> SPCList = FXCollections.observableArrayList();
        DBC.connect();
        String SQL = "SELECT NAME FROM CENTRES ";
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
        {
            SPCList.add(rs.getString("NAME"));
        }
        DBC.closeConnection();
        return SPCList;
    }
    //RETURN LIST OF ALL SPC'S
    public ObservableList<SPC> getAllSPC() throws SQLException
    {
        ObservableList<SPC> SPCList = FXCollections.observableArrayList();
        DBC.connect();
        String SQL = "SELECT * FROM CENTRES ";
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
        {
            SPCList.add(new SPC(rs.getInt("ID"), rs.getString("NAME"), rs.getString("ADDRESS"), rs.getString("TELEPHONE"), rs.getString("EMAIL")));
        }
        DBC.closeConnection();
        return SPCList;
    }
    
    public int getSPCID(String Name) throws SQLException
    {
        ObservableList<ListSPC> SPCList = FXCollections.observableArrayList();
        DBC.connect();
        String SQL = "SELECT SPCID FROM CENTRES WHERE NAME = '" + Name + "';";
        ResultSet rs = DBC.query(SQL);
        int ID = 0;
        if(rs !=null)
        {
            ID = rs.getInt("SPCID");
        }
        DBC.closeConnection();
        return ID;
    }
    
    public ObservableList<DisplayVehicle> getVehicle(String regNo) throws SQLException
    {
       ObservableList<DisplayVehicle> vehicleInfo = FXCollections.observableArrayList();
        DBC.connect();
        //String SQL = "SELECT ID, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST, TYPE FROM REPAIRVEHICLE, CENTRES WHERE REGNO LIKE '%" + RegSearch.getText() + "%' AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";
        String SQL = "SELECT REGISTRATION, MAKE, MODEL, FUELTYPE, MILEAGE, COLOUR FROM VEHICLE WHERE REGISTRATION = '" + regNo + "';";
        //System.out.println(SQL);
        ResultSet rs = DBC.query(SQL);
        if(rs != null)
        {
            //System.out.println(rs.getString("MILEAGE"));
            vehicleInfo.add(new DisplayVehicle( rs.getString("REGISTRATION"), rs.getString("MAKE"), rs.getString("MODEL"), rs.getString("FUELTYPE"), rs.getInt("MILEAGE") , rs.getString("COLOUR") ));
        }
        DBC.closeConnection();
        return vehicleInfo;
    }
    
    public ObservableList<SearchMain> searchReg(String regNo) throws SQLException
    {
       ObservableList<SearchMain> resultList = FXCollections.observableArrayList();
            DBC.connect();
            String SQL = "SELECT ID, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST FROM REPAIRVEHICLE, CENTRES WHERE REGNO LIKE '%" + regNo + "%' AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";
            //System.out.println(SQL);
            ResultSet rs = DBC.query(SQL);
            while(rs.next())
            {
                resultList.add(new SearchReg( rs.getInt("ID"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST") ));
            }
            DBC.closeConnection();
        return resultList;
    }
    
    public ObservableList<SearchMain> searchName(String fname, String lname) throws SQLException
    {
       ObservableList<SearchMain> resultList = FXCollections.observableArrayList();
            DBC.connect();
            String SQL = "SELECT REPAIRVEHICLE.ID, FIRSTNAME, SURNAME, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST FROM REPAIRVEHICLE, CENTRES, CUSTOMER, VEHICLE WHERE FIRSTNAME LIKE '%" + fname + "%' AND CUSTOMER.SURNAME LIKE '%" + lname + "%' AND VEHICLE.CUSTOMERID = CUSTOMER.ID AND REPAIRVEHICLE.REGNO = VEHICLE.REGISTRATION AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";
            //System.out.println(SQL);
            ResultSet rs = DBC.query(SQL);
            while(rs.next())
            {
                resultList.add(new SearchName( rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("SURNAME"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST")));
            }
            DBC.closeConnection();
        return resultList;
    }
    
    public ObservableList<SearchMain> searchSPC(int SPCID) throws SQLException
    {
       ObservableList<SearchMain> resultList = FXCollections.observableArrayList();
            DBC.connect();
            //String SQL = "SELECT ID, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST, TYPE FROM REPAIRVEHICLE, CENTRES WHERE REPAIRVEHICLE.SPCID = " + SPCID + " AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";
            String SQL = "SELECT REPAIRVEHICLE.ID, FIRSTNAME, SURNAME, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST FROM REPAIRVEHICLE, CENTRES, CUSTOMER, VEHICLE WHERE REPAIRVEHICLE.SPCID = " + SPCID + " AND VEHICLE.CUSTOMERID = CUSTOMER.ID AND REPAIRVEHICLE.REGNO = VEHICLE.REGISTRATION AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";

            //System.out.println(SQL);
            ResultSet rs = DBC.query(SQL);
            while(rs.next())
            {
                //resultList.add(new SearchReg( rs.getInt("ID"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST"), "Vehicle" ));
                resultList.add(new SearchName( rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("SURNAME"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST")));
            }
            if (resultList == null)
            {
                System.out.println("empty g");
            }
            DBC.closeConnection();
        return resultList;
    }
    
    public void getOutstanding(String date)
    {
        //String SQL = "SELECT TYPE, ITEMID, PARTNAME, RETURNDATE FROM SPECIALISTREPAIRS, REPAIRPARTS WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'PART';";
        //String SQL = "SELECT TYPE, ITEMID, MAKE + ' ' + MODEL, RETURNDATE FROM SPECIALISTREPAIRS, VEHICLE WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'VEHICLE';";
        String SQL = "SELECT x, y FROM (SELECT TYPE, ITEMID, PARTNAME, RETURNDATE FROM SPECIALISTREPAIRS, REPAIRPARTS WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'PART') as x, (SELECT TYPE, ITEMID, MAKE + ' ' + MODEL, RETURNDATE FROM SPECIALISTREPAIRS, VEHICLE WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'VEHICLE') as y ORDER BY RETURNDATE ASC;";
        //hopefully that retrieves everything, just need to output to a table
    }
    
    public boolean deleteVehicleRepair(int RepairID)
    {
        String SQL = "DELETE FROM REPAIRVEHICLE WHERE ID = " + RepairID + ";";
        return DBC.update(SQL);
    }

    public static Repairs getInstance()
    {
        if(RInstance == null)
        {
            RInstance = new Repairs();
        }
        return RInstance;
    }
    
    //Error Checks 
    public boolean isAlphanumeric(String word) 
    {
        //returns false if not alphanumberic
        return word.matches("[a-zA-Z]+");
    }
    
    public boolean isPlate(String word) 
    {
        //returns false if containts special characters
        if (word.matches("[A-Za-z0-9]+"))
        {
            if(word.length() <= 7)
            {
                return true;
            }
        }
        return false;

    }
    
    
}
