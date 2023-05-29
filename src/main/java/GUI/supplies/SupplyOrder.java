package GUI.supplies;

import GUI.Date;
import GUI.JavaConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Random;


public class SupplyOrder{
    Vendor vendor;
    private int supplyOrderID;
    private String arrivalDate;
    private String orderDate;

    Material material;
    ArrayList<Material> materials;

    public SupplyOrder (){

    }
    public SupplyOrder(String orderPlaced, String arrivalDate,Vendor vendor){
        this.vendor = vendor;
        this.orderDate = orderPlaced;
        this.arrivalDate = arrivalDate;
    }
    public SupplyOrder(String orderPlaced, String arrivalDate, ArrayList<Material> materials, Vendor vendor){


        this.vendor = vendor;
        this.materials = materials;
        this.arrivalDate = arrivalDate;
        this.orderDate = orderPlaced;
        supplyOrderID = getLastOrderId() +1;
    }
    public void createAnInvoice(){

    }
    public int getLastOrderId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select supplyOrderID from supplyOrder";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();



            while(result.next()){

                if(result.getInt("supplyOrderID")> max){
                    max = result.getInt("supplyOrderID");
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }
    public int getVendorIDwithLowestPrice(String material){
        double minPrice = 0;
        int vendorID = 0;
        material.toUpperCase();
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select price from vendor where rawMaterial =' " + material + "';";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if (minPrice > result.getDouble("price")) {

                    vendorID = result.getInt("vendorID");
                    System.out.println(result.getInt("vendorID"));
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return vendorID;

    }
    public double getMaterialPrice(String material) {
        double minPrice = 0;

        material.toUpperCase();
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select price from vendor where rawMaterial =' " + material + "';";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            int vendorID;
            if (result.next()) {
                if (minPrice > result.getDouble("price")) {
                    minPrice = result.getDouble("price");
                    vendorID = result.getInt("vendorID");
                    System.out.println(result.getDouble("price"));
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return minPrice;
    }
    public void makeSupplyOrder(String materialName, int vendorId, int quantity){
        materialName.toUpperCase();
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select * from vendor where rawMaterial ='" + materialName + "' AND vendorID =" + vendorId +";";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                Vendor newVendor = new Vendor(result.getInt("vendorID"), result.getString("companyName"),
                        result.getString("rawMaterial"), result.getDouble("price"));
                createSupplyOrderInDataBase(newVendor, materialName, result.getDouble("price"), quantity, "05/23/2023", "05/30/2023");
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void createSupplyOrderInDataBase(Vendor v, String material, double price,  int quantity, String placed, String arrival){
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Insert into supplyorder (supplyOrderID,vendorID,rawMaterial,price,quantity, orderPlaced,arrivalDate)" +
                    "values (" + (getLastOrderId()+1) +","+ v.getVendorID() +",'"+ material+"'," + price+","+quantity+",'"+placed+"',"+"'"+arrival+"');" ;

            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();


        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }



    public int getVendorID() {
        return vendor.getVendorID();
    }


    public String getCompanyName() {
        return vendor.getCompanyName();
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public String getArrivalDate() {
        return arrivalDate.toString();
    }

    public String getOrderDate() {
        return orderDate.toString();
    }


}
