package GUI.supplies;

import GUI.JavaConnector;
import GUI.supplies.resupply.ResupplyController;
import GUI.supplies.resupply.resupplyPop.ResupplyPopController;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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


    public  Vendor (){}
    public Vendor(int vendorID,String companyName,String rawMaterial,double price){
        this.vendorID = vendorID;
        this.companyName = companyName;
        this.rawMaterial = rawMaterial;
        this.price = price;
    }
    public Vendor(String companyName,int vendorID, String rawMaterial,int quantity,double price){
        this.vendorID = vendorID;
        this.companyName = companyName;
        this.rawMaterial = rawMaterial;
        this.price = price;
        this.productQuantity = quantity;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public Vendor(String companyName, HashMap <Material, Double> supply){
        this.vendorID = getLastVendorId() + 1;
        this.companyName = companyName;
        materialPrice = supply;
    }

    public Vendor(String companyName, double amount, int quantity) {
        this.companyName = companyName;
        this.price = amount;
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

    public String getRawMaterial() {
        return rawMaterial;
    }

    public double getPrice() {
        return price;
    }
    public static boolean checkAvailability (String rawMaterial){
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select rawMaterial from vendor";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getString("rawMaterial").toUpperCase().equals(rawMaterial.toUpperCase())) {
                    return true;
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Material Unavailable");
            alert.showAndWait();
        }
        return 0;
    }

    /**
     * Fetches and retreves a list of materials and their vendors sorted by Price
     * @return List of Vendors
     */
    public static List<Vendor> getVendorsByPrice() {
        String sortWay;
        List<Vendor> vendors = new ArrayList<>();
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

    public void addVendorToDatabase() {

        try {
            Iterator <Material> iterator = materialPrice.keySet().iterator();
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            while (iterator.hasNext()) {
                Material mat = iterator.next();
                String query = "Insert Into vendor (vendorID, companyName, rawMaterial, price) values (" +
                        getVendorID() + ",'" + getCompanyName() + "' , '"+  mat.materialName +"' , "+ materialPrice.get(mat) + " ); ";
                PreparedStatement statement = con.prepareStatement(query);
                statement.executeUpdate();
            }
        }catch(SQLException numF1) {
            numF1.printStackTrace();
        }catch(Exception e1) {
            e1.printStackTrace();
        }


    }




}
