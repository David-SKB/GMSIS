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

    public void getOutstanding(String date)
    {
        //String SQL = "SELECT TYPE, ITEMID, PARTNAME, RETURNDATE FROM SPECIALISTREPAIRS, REPAIRPARTS WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'PART';";
        //String SQL = "SELECT TYPE, ITEMID, MAKE + ' ' + MODEL, RETURNDATE FROM SPECIALISTREPAIRS, VEHICLE WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'VEHICLE';";
        String SQL = "SELECT x, y FROM (SELECT TYPE, ITEMID, PARTNAME, RETURNDATE FROM SPECIALISTREPAIRS, REPAIRPARTS WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'PART') as x, (SELECT TYPE, ITEMID, MAKE + ' ' + MODEL, RETURNDATE FROM SPECIALISTREPAIRS, VEHICLE WHERE RETURNDATE >= Convert(datetime, '" + date + "') AND TYPE = 'VEHICLE') as y ORDER BY RETURNDATE ASC;";
        //hopefully that retrieves everything, just need to output to a table
    }
    
    public void getRecords()
    {
        //req 12
    }
    
    public void deleteBooking()
    {
        //req 13
    }
    
    public void searchReg()
    {
        //req 11 / 13
    }
    
    public void searchName()
    {
        //req 13
    }
}
