package GUI.supplies;

import GUI.JavaConnector;

import java.sql.*;
import java.util.HashMap;

public class Vendor {
    private int vendorID;
    private String companyName;
    HashMap<Material, Double> materialPriceSheet;

    public Vendor(int vendorID, String companyName){
        this.vendorID = vendorID;
        this.companyName = companyName;
        materialPriceSheet = new HashMap<>();
    }

    public void addMaterial (Material mat, double price){
        materialPriceSheet.put(mat, price);
    }
    public void addHashMapMaterials(HashMap<Material,Double> mats){
        materialPriceSheet.putAll(mats);
    }

    public int getVendorID() {
        return vendorID;
    }

    public String getCompanyName() {
        return companyName;
    }


    public void addToDatabase() throws SQLException {

        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select * From materials";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                System.out.println(result.getString(2));
            }
        }catch(NumberFormatException numF1) {

        }catch(Exception e1) {
            e1.printStackTrace();
        }


    }


}
