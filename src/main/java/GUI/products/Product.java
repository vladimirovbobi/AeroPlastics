package GUI.products;

import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * The type Product.
 */
public class Product{

    private int inventoryLevel;
    private int productID;
    private String productName;
    private double price;
    private String rawMaterial;

    /**
     * Instantiates a new Product.
     *
     * @param productID      the product id
     * @param productName    the product name
     * @param price          the price
     * @param rawMaterial    the raw material
     * @param inventoryLevel the inventory level
     */
    public Product(int productID, String productName, double price, String rawMaterial, int inventoryLevel){
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.rawMaterial = rawMaterial;
        this.inventoryLevel = inventoryLevel;
    }

    /**
     * Gets inventory level.
     *
     * @return the inventory level
     */
    public int getInventoryLevel() {
        return inventoryLevel;
    }

    /**
     * Gets product id.
     *
     * @return the product id
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Gets product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets raw material.
     *
     * @return the raw material
     */
    public String getRawMaterial() {
        return rawMaterial;
    }

    /**
     * Sets inventory level.
     *
     * @param inventoryLevel the inventory level
     */
    public void setInventoryLevel(int inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
    }

    /**
     * Product is in stock and shipped boolean.
     *
     * @param productID the product id
     * @param quantity  the quantity
     * @return the boolean
     */
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

    /**
     * Reduce product by.
     *
     * @param product             the product
     * @param numberOfUnitsBought the number of units bought
     */
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

    /**
     * Get material used for product string.
     *
     * @param productID the product id
     * @return the string
     */
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
