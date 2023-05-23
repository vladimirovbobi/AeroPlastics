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
    private int supplyOrderID;
    private Date arrivalDate;
    private Date orderDate;
    private Vendor vendor;
    ArrayList<Material> materials;

    public SupplyOrder (){

    }
    public SupplyOrder(Date placed, Date arrival, ArrayList<Material> materials, Vendor vendor){


        this.vendor = vendor;
        this.materials = materials;
        arrivalDate = arrival;
        orderDate = placed;
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



            do {

                if(result.getInt("supplyOrderID")> max){
                    max = result.getInt("supplyOrderID");
                }
            }while(result.next());

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println(max);
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


            if (result.next()) {
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
