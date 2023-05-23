package GUI.supplies;

import GUI.JavaConnector;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

public class Vendor {
    private int vendorID;
    private String companyName;
    HashMap<Material, Double> materialPrice;

    public  Vendor (){}
    public Vendor(String companyName, HashMap <Material, Double> supply){
        this.vendorID = getLastOrderId() + 1;
        this.companyName = companyName;
        materialPrice = supply;
    }
    public int getLastOrderId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select vendorID from vendor";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getInt("vendorID")> max){
                    max = result.getInt("supplyOrderID");
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }

    public int getVendorID() {
        return vendorID;
    }

    public String getCompanyName() {
        return companyName;
    }


    public void addVendorToDatabase() {

        try {
            Iterator <Material> iterator = materialPrice.keySet().iterator();
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            while (iterator.hasNext()) {
                Material mat = iterator.next();
                String query = "Insert Into vendor (vendorID, companyName, rawMaterial, price, orderPlaced, arrivalDate), values (" +
                        getVendorID() + "," + getCompanyName() + ", "+  mat.materialName +", "+ materialPrice.get(mat) +"); ";
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet result = statement.executeQuery();
            }
        }catch(SQLException numF1) {
            numF1.printStackTrace();
        }catch(Exception e1) {
            e1.printStackTrace();
        }


    }
    /*
    public static void main() {
        Material material = new Material(inventoryLevel, 1,"PLA",190,4);
        Material material2 = new Material(inventoryLevel, 2,"PTGE",240,5);
        Material material3 = new Material(inventoryLevel, 3,"FLK",308,7);
        Material material4 = new Material(inventoryLevel, 4,"TPK",120,1);
        HashMap<Material, Double> priceMat = new HashMap<>();
        priceMat.put(material,40.2);
        priceMat.put(material2,75.7);
        priceMat.put(material3,200.9);
        priceMat.put(material4,10.5);

        Vendor vendor = new Vendor("Supply Co", priceMat);
        vendor.addVendorToDatabase();
    }

*/
}
