public class Repairs
{
    protected String CustomerID, contract, itemID, deliveryDate, returnDate;
    protected int SPCID, cost;
    public Repairs(int cost)
    {
        //
    }

    public boolean addSPC(int SPCID, String name, String address, String telephone, String email)
    {
        String SQL = "INSERT INTO CENTRES VALUES ('" + SPCID + "', '" + name + "', '" + address + "', '" + telephone + "', '" + email + "');";
        return true;
    }
    
    public boolean editSPC(int SPCID, String name, String address, String telephone, String email)
    {
        String SQL = "UPDATE CENTRES SET NAME = '" + name + "', ADDRESS = '" + address + "', TELEPHONE = '" + telephone + "', EMAIL = '" + email + "' WHERE SPCID = '" + SPCID + "';";
        return true;
    }
    
    public boolean deleteSPC(int SPCID)
    {
        String SQL = "DELETE * FROM CENTRES WHERE SPCID = '" + SPCID + "';";
        return true;
    }
    
    public void getOutstanding()
    {
        
    }
    
    public void getRecords()
    {
        
    }
    
    public void deleteBooking()
    {
        
    }
    
    public void searchReg()
    {
        
    }
    
    public void searchName()
    {
        
    }
}
