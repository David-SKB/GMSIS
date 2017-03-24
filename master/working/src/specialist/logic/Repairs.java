package specialist.logic;
import common.DBConnection;
import customers.logic.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class Repairs
{
    private static Repairs RInstance = null;
    private DBConnection DBC = DBConnection.getInstance();
    private static ErrorChecks EC = ErrorChecks.getInstance();
    public Repairs()
    {
        
    }
    
    public static Repairs getInstance()
    {
        if(RInstance == null)
        {
            RInstance = new Repairs();
        }
        return RInstance;
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
        //System.out.println(SQL);//view sql output
        return success;
    }

    public boolean addPart(String RegNo, int ID, int SPCID, Date ExpDel, Date ExpRet)
    {
        boolean success;
        DBC.connect();    
        String SQL = "INSERT INTO REPAIRPARTS (REGNO, PARTID, SPCID, DELIVERYDATE, RETURNDATE) " + 
                           "VALUES ( '" + 
                            RegNo + "', '" +
                            ID + "', '" +
                            SPCID + "', '" +
                            ExpDel + "', '" +
                            ExpRet + "' );";
        success = DBC.update(SQL);
        //System.out.println(SQL);
        DBC.closeConnection();
        return success;
    }
    
    public boolean editVehicle(String RegNo, int SPCID, Date ExpDel, Date ExpRet, double Cost, int RepairID)
    {
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
    
    public boolean editPart(String RegNo, int ID, int SPCID, Date ExpDel, Date ExpRet, int RepairID)
    {
        boolean success;
        DBC.connect();    
        String SQL = "UPDATE REPAIRPARTS \n" + 
                            "SET " +
                            "REGNO = '" + RegNo + "', " +
                            "PARTID = '" + ID + "', " +
                            "SPCID = '" + SPCID + "', " +
                            "DELIVERYDATE = '" + ExpDel + "', " +
                            "RETURNDATE = '" + ExpRet + "' \n" + 
                            "WHERE ID = '" + RepairID +  "';";
        success = DBC.update(SQL);
        //System.out.println(SQL);
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
    
    public ObservableList<String> getPartListCombo() throws SQLException
    {
        ObservableList<String> PartList = FXCollections.observableArrayList();
        DBC.connect();
        String SQL = "SELECT NAME FROM STOCKPARTS WHERE STOCK > 0;";
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
        {
            PartList.add(rs.getString("NAME"));
        }
        DBC.closeConnection();
        return PartList;
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
    
    public int getPartID(String Name) throws SQLException
    {
        DBC.connect();
        String SQL = "SELECT ID FROM STOCKPARTS WHERE NAME = '" + Name + "';";
        ResultSet rs = DBC.query(SQL);
        int ID = 0;
        if(rs !=null)
        {
            ID = rs.getInt("ID");
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
            vehicleInfo.add(new DisplayVehicle( rs.getString("REGISTRATION"), rs.getString("MAKE"), rs.getString("MODEL"), rs.getString("FUELTYPE"), rs.getInt("MILEAGE") , rs.getString("COLOUR") ));
        }
        DBC.closeConnection();
        return vehicleInfo;
    }
    
    public ObservableList<SearchMain> searchReg(String regNo) throws SQLException
    {
       ObservableList<SearchMain> resultList = FXCollections.observableArrayList();
            DBC.connect();
            String SQL = "SELECT REPAIRVEHICLE.ID, FIRSTNAME, SURNAME, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST FROM REPAIRVEHICLE, CENTRES, CUSTOMER, VEHICLE WHERE REGNO LIKE '%" + regNo + "%' AND VEHICLE.CUSTOMERID = CUSTOMER.ID AND REPAIRVEHICLE.REGNO = VEHICLE.REGISTRATION AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";
            //System.out.println(SQL);
            ResultSet rs = DBC.query(SQL);
            while(rs.next())
            {
                resultList.add(new SearchMain( rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("SURNAME"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST")));
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
                resultList.add(new SearchMain( rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("SURNAME"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST")));
            }
            DBC.closeConnection();
        return resultList;
    }
    
    public ObservableList<SearchMain> searchSPC(int SPCID) throws SQLException
    {
       ObservableList<SearchMain> resultList = FXCollections.observableArrayList();
            DBC.connect();
            String SQL = "SELECT REPAIRVEHICLE.ID, FIRSTNAME, SURNAME, REGNO, NAME, DELIVERYDATE, RETURNDATE, COST FROM REPAIRVEHICLE, CENTRES, CUSTOMER, VEHICLE WHERE REPAIRVEHICLE.SPCID = " + SPCID + " AND VEHICLE.CUSTOMERID = CUSTOMER.ID AND REPAIRVEHICLE.REGNO = VEHICLE.REGISTRATION AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";

            //System.out.println(SQL);
            ResultSet rs = DBC.query(SQL);
            while(rs.next())
            {
                resultList.add(new SearchMain( rs.getInt("ID"), rs.getString("FIRSTNAME"), rs.getString("SURNAME"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST")));
            }
            DBC.closeConnection();
        return resultList;
    }
    
    public ObservableList<RepairParts> getPartRepairs(String RegNo) throws SQLException
    {
        ObservableList<RepairParts> resultList = FXCollections.observableArrayList();
        DBC.connect(); 
        String SQL = "SELECT REPAIRPARTS.ID, REGNO, STOCKPARTS.NAME, CENTRES.NAME, DELIVERYDATE, RETURNDATE, COST FROM REPAIRPARTS, CENTRES, STOCKPARTS WHERE REGNO = '" + RegNo + "' AND REPAIRPARTS.SPCID = CENTRES.SPCID AND PARTID = STOCKPARTS.ID;";
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
            {
                resultList.add(new RepairParts(rs.getInt("ID"), rs.getString("REGNO"), rs.getString("NAME"), rs.getString(4), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE") , rs.getDouble("COST")));
            }

        DBC.closeConnection();
        return resultList;
    }
    
    /*public ObservableList<OutstandingMain> getOutstanding(int SPCID) throws SQLException
    {
        ObservableList<OutstandingMain> resultList = FXCollections.observableArrayList();
        
        //FOR VEHICLES
        String SQL = "SELECT REGNO, DELIVERYDATE, RETURNDATE, COST FROM REPAIRVEHICLE WHERE RETURNDATE >= Datetime('"+ LocalDate.now().toString() + "') AND SPCID = '" + SPCID + "';";
        DBC.connect(); 
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
            {
                resultList.add(new OutstandingMain(rs.getString("REGNO"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE"), rs.getDouble("COST"), "Vehicle"));
            }
        
        //FOR PARTS
        SQL = "SELECT PARTID, DELIVERYDATE, RETURNDATE, COST FROM REPAIRPARTS, STOCKPARTS WHERE RETURNDATE >= Datetime('"+ LocalDate.now().toString() + "') AND SPCID = '" + SPCID + "';";
        rs = DBC.query(SQL);
        while(rs.next())
            {
                resultList.add(new OutstandingMain(Integer.toString(rs.getInt("PARTID")), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE"), rs.getDouble("COST"), "Part"));
            }

        DBC.closeConnection();
        return resultList;
    }*/
    
    public ObservableList<OutstandingMain> getOutstanding() throws SQLException
    {
        ObservableList<OutstandingMain> resultList = FXCollections.observableArrayList();
        
        //FOR VEHICLES
        String SQL = "SELECT REGNO, DELIVERYDATE, RETURNDATE, COST, NAME FROM REPAIRVEHICLE, CENTRES WHERE RETURNDATE >= Datetime('"+ LocalDate.now().toString() + "') AND REPAIRVEHICLE.SPCID = CENTRES.SPCID;";
        //System.out.println(SQL);
        DBC.connect(); 
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
            {
                resultList.add(new OutstandingMain(rs.getString("REGNO"), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE"), rs.getDouble("COST"), "Vehicle", rs.getString("NAME")));
            }
        
        //FOR PARTS
        SQL = "SELECT PARTID, DELIVERYDATE, RETURNDATE, COST, CENTRES.NAME FROM REPAIRPARTS, STOCKPARTS, CENTRES WHERE RETURNDATE >= Datetime('"+ LocalDate.now().toString() + "') AND REPAIRPARTS.SPCID = CENTRES.SPCID;";
        //System.out.println(SQL);
        rs = DBC.query(SQL);
        while(rs.next())
            {
                resultList.add(new OutstandingMain(Integer.toString(rs.getInt("PARTID")), rs.getString("DELIVERYDATE"), rs.getString("RETURNDATE"), rs.getDouble("COST"), "Part", rs.getString("NAME")));
            }

        DBC.closeConnection();
        return resultList;
    }
    public boolean deleteVehicleRepair(int RepairID)
    {
        DBC.connect();
        String SQL = "DELETE FROM REPAIRVEHICLE WHERE ID = " + RepairID + ";";
        boolean success = DBC.update(SQL);
        DBC.closeConnection();
        return success;
    }
    
    public boolean findVehicle(String regNo)
    {
        DBC.connect();
        String SQL = "SELECT COUNT(REGISTRATION) FROM VEHICLE WHERE REGISTRATION =  '" + regNo + "';";
        System.out.println(SQL);
        int count = 0;
        ResultSet rs = DBC.query(SQL);
        System.out.println(rs.toString());
        try {
            if(rs.next())
            {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) 
        {
            //db error
        }
        DBC.closeConnection();
        if (count == 0)
        {
            return false;
        }
        return true;
    }
    
    public boolean deletePartRepair(int RepairID)
    {
        DBC.connect();
        String SQL = "DELETE FROM REPAIRPARTS WHERE ID = " + RepairID + ";";
        boolean success = DBC.update(SQL);
        DBC.closeConnection();
        return success;
    }
    
    //taken and modified from customer search with name
    public Customer searchCustomerWithReg(String RegNo)
    {
        DBC.connect();
        try
        {
            String query = "SELECT SURNAME, FIRSTNAME, ADDRESS, POSTCODE, PHONE, EMAIL, CUSTOMERTYPE  FROM CUSTOMER, VEHICLE WHERE REGISTRATION = '"+ RegNo + "' AND VEHICLE.CUSTOMERID = ID;";
            Customer searchedCustomers = null;
            ResultSet rs = DBC.query(query);
            if(rs !=null)
            {
                String sName = rs.getString("SURNAME");
                String fName = rs.getString("FIRSTNAME");
                String address = rs.getString("ADDRESS");
                String postCode = rs.getString("POSTCODE");
                String phone = rs.getString("PHONE");
                String email = rs.getString("EMAIL");
                String customerType = rs.getString("CUSTOMERTYPE");
                searchedCustomers = new Customer(sName,fName,address,postCode,phone,email,customerType);
            }
            DBC.closeConnection();
            return searchedCustomers;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    
    public ArrayList<String> getAllVehicles() throws SQLException
    {
        ArrayList<String> VehicleList = new ArrayList<>();
        DBC.connect();
        String SQL = "SELECT REGISTRATION FROM VEHICLE;";
        ResultSet rs = DBC.query(SQL);
        while(rs.next())
            {
                VehicleList.add(rs.getString("REGISTRATION"));
            }
        return VehicleList;
    }
    
    public boolean removeStock(int ID)
    {
        DBC.connect();
        String SQL = "UPDATE STOCKPARTS SET STOCK = STOCK-1 WHERE ID = " + ID + ";";
        boolean success = DBC.update(SQL);
        DBC.closeConnection();
        return success;
    }

}
