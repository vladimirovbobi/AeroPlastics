package GUI.supplies;

import GUI.JavaConnector;
import javafx.scene.control.Alert;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vendor {
    public static boolean sortByPrice;
    public static boolean sortByName;

    private int vendorID;
    private String companyName;
    HashMap<Material, Double> materialPrice;

    private String rawMaterial;
    private double price;
    private int productQuantity;

    /**
     * Constructor
     */
    public  Vendor (){}

    /**
     * Constructor
     * @param vendorID Vendor ID number
     * @param companyName Vendor's company name
     * @param rawMaterial The name of the raw material
     * @param price The price for the item
     */
    public Vendor(int vendorID,String companyName,String rawMaterial,double price){
        this.vendorID = vendorID;
        this.companyName = companyName;
        this.rawMaterial = rawMaterial;
        this.price = price;
    }

    /**
     * Constructor
     * @param vendorID Vendor ID number
     * @param productQuantity The quantity bought from a vendor
     * @param rawMaterial The name of the raw material
     * @param price The price for the item
     */
    public Vendor(int vendorID,String rawMaterial,int productQuantity, double price){
        this.vendorID = vendorID;
        this.productQuantity = productQuantity;
        this.rawMaterial = rawMaterial;
        this.price = price;
    }

    /**
     * Constructor
     * @param vendorID Vendor ID number
     * @param companyName Vendor's company name
     * @param rawMaterial The name of the raw material
     * @param price The price for the item
     * @param quantity The quantity bought from a vendor
     */
    public Vendor(String companyName,int vendorID, String rawMaterial,int quantity,double price){
        this.vendorID = vendorID;
        this.companyName = companyName;
        this.rawMaterial = rawMaterial;
        this.price = price;
        this.productQuantity = quantity;
    }

    /**
     * Constructor
     * @param companyName The Vendor's company name
     * @param supply Hash Map with the items and price per item
     */

    public Vendor(String companyName, HashMap <Material, Double> supply){
        this.vendorID = getLastVendorId() + 1;
        this.companyName = companyName;
        materialPrice = supply;
    }

    /**
     * Constructor
     * @param companyName The vendor's company name
     * @param amount The price for the item
     * @param quantity The number of items bought
     */
    public Vendor(String companyName, double amount, int quantity) {
        this.companyName = companyName;
        this.price = amount;
        this.productQuantity = quantity;
    }

    public Vendor(String companyName, int cartID, double amount, int quantity) {
        this.companyName = companyName;
        this.price = amount;
        this.vendorID = cartID;
        this.productQuantity = quantity;
    }


    /**
     * Get the last VendorID
     * @return vendorID
     */
    public int getLastVendorId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select vendorID from vendor";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getInt("vendorID")> max){
                    max = result.getInt("vendorID");
                }
            }

        } catch (NumberFormatException numF1) {
            return 0;
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }

    /**
     * Retrieve vendorID
     * @return vendor ID number
     */
    public int getVendorID() {
        return vendorID;
    }

    /**
     * Retrieve the name of the company
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Retrieve product quantity
     * @return product quantity
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     * Retrieve Raw Material name
     * @return the name of the raw material
     */
    public String getRawMaterial() {
        return rawMaterial;
    }

    /**
     * Retrieve the price
     * @return price per item
     */
    public double getPrice() {
        return price;
    }

    /**
     * Check if a specific raw material is so sold by anyone
     * @param rawMaterial The name of the raw material
     * @return true/false
     */
    public static boolean checkAvailability (String rawMaterial){
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select rawMaterial from vendor";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if (result.getString("rawMaterial").toUpperCase().equals(rawMaterial.toUpperCase())) {
                    return true;
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * Retrieve company name from an ID
     * @param vendorID The Vendor ID number
     * @return The name of the company associated with the vendorID
     */
    public static String getVendorByID (int vendorID){
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select companyName,rawMaterial from vendor where vendorID = " + vendorID + ";";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                return result.getString("companyName");
            }
        }catch (Exception e1) {
            e1.printStackTrace();
        }

        return "No company found";

    }

    /**
     * Retrieve price for a specific item from a vendor with the vendor ID
     * @param vendorID The vendor's ID number
     * @param materialName The name of the material
     * @return The price corresponding to the vendorID and the name of the material
     */
    public static double getPriceByVendorID(int vendorID, String materialName) {
        try {
            materialName.toUpperCase();
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select price from vendor where vendorID = " + vendorID + " AND rawMaterial = '" + materialName + "';";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getDouble("price");
            }
            return result.getDouble("price");

        } catch (SQLException exception) {
            warningMaterialUnavailable();
            return 0 ;
        }

    }

    /**
     * Display a warning message when a material is unavailable
     */
    public static void warningMaterialUnavailable(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Material Unavailable");
        alert.showAndWait();
    }

    /**
     * Fetches and retrieves a list of materials and their vendors sorted by Price
     * @return List of Vendors
     */
    public static List<Vendor> getVendorsByPrice() {
        String sortWay;
        List<Vendor> vendors = new ArrayList<>();
        //sets the order for the vendors by price
        if (sortByPrice) {
            sortWay = "asc";
            sortByPrice = false;
        } else {
            sortWay = "desc";
            sortByPrice = true;
        }
        try {
            JavaConnector connector = new JavaConnector();
            //Establish a connection to the database
            Connection connection = connector.getConnection();

            // Execute a SQL query to retrieve materials data
            String query = "SELECT  vendorID, companyName, rawMaterial,productQuantity,price from vendor order by price " + sortWay + ";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //Process the result set
            while (resultSet.next()) {
                int vendorID = resultSet.getInt("vendorID");
                int productQuantity = resultSet.getInt("productQuantity");
                String companyName = resultSet.getString("companyName");
                String rawMaterial = resultSet.getString("rawMaterial");
                double price = resultSet.getDouble("price");

                //Create a Material object and add it to the list
                Vendor vendor = new Vendor( companyName,vendorID, rawMaterial, productQuantity, price);
                vendors.add(vendor);
            }


            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  vendors;
    }

    /**
     * Fetches List of Vendors sorted by Material
     * @return List of Vendors
     */
    public static List<Vendor> getVendorsByMaterial() {
        String sortWay;
        List<Vendor> vendors = new ArrayList<>();
        if (sortByName) {
            sortWay = "asc";
            sortByName = false;
        } else {
            sortWay = "desc";
            sortByName = true;
        }
        try {
            JavaConnector connector = new JavaConnector();
            //Establish a connection to the database
            Connection connection = connector.getConnection();

            // Execute a SQL query to retrieve materials data
            String query = "SELECT  vendorID, companyName, rawMaterial,productQuantity, price from vendor order by rawMaterial " + sortWay + ";";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //Process the result set
            while (resultSet.next()) {
                int vendorID = resultSet.getInt("vendorID");
                int productQuantity = resultSet.getInt("productQuantity");
                String companyName = resultSet.getString("companyName");
                String rawMaterial = resultSet.getString("rawMaterial");
                double price = resultSet.getDouble("price");

                //Create a Material object and add it to the list
                Vendor vendor = new Vendor( companyName,vendorID, rawMaterial, productQuantity, price);
                vendors.add(vendor);
            }


            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  vendors;
    }

}
