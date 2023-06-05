package GUI.products;

import GUI.JavaConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class Product{

    private int inventoryLevel;
    private int productID;
    private String productName;
    private double price;
    private String rawMaterial;

    public Product(int productID, String productName, double price, String rawMaterial, int inventoryLevel){
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.rawMaterial = rawMaterial;
        this.inventoryLevel = inventoryLevel;
    }

    public int getInventoryLevel() {
        return inventoryLevel;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getRawMaterial() {
        return rawMaterial;
    }

    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    public static boolean productIsInStockAndShipped(int productID, int quantity){
        try {
            int productInStock =0;
            String material ;
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT * FROM products WHERE productID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, productID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                productInStock =   resultSet.getInt("inventoryLevel");
            }
            //produce 1
            if (productInStock >= quantity){
                reduceProductBy(productID, quantity);
                return true;
            }else{
                return false;
            }
        }catch(Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }
    public static void reduceProductBy(int product, int numberOfUnitsBought){
        int quantity = 0 ;
        try {

            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT inventoryLevel FROM products WHERE productID =? ";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,product);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                quantity = resultSet.getInt("inventoryLevel");
            }
            //Reduce the quantity because a product is made
            quantity -= numberOfUnitsBought;
            query = "UPDATE products SET inventoryLevel = ? WHERE productID = ?;";
            statement = con.prepareStatement(query);
            statement.setInt(2, product);

            statement.setInt(1, quantity);

            statement.executeUpdate();

        }catch(Exception e1) {
            e1.printStackTrace();
        }
    }
    public static String getMaterialUsedForProduct(int productID){
        String material="" ;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT * FROM products WHERE productID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, productID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                material = resultSet.getString("rawMaterial");
            }
            return material;
        }catch(Exception e1) {
            e1.printStackTrace();
        }
        return material;
    }
}
