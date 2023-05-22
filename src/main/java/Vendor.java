import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        JavaConnector connector = new JavaConnector();
        String query = "Select * From materials";
        Connection connection = connector.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        System.out.println(result.getInt("materialID"));

    }


}
