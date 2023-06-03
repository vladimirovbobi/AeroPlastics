package GUI.supplies.resupply;

import GUI.Date;
import GUI.JavaConnector;
import GUI.supplies.Material;
import GUI.supplies.SupplyController;
import GUI.supplies.SupplyOrder;
import GUI.supplies.Vendor;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;

public class Cart {
    private static Cart cart;
    private static double amount;
    private String orderDate,arrivalDate;
    private int cartID;


    /**
     * Private constructor
     */
    private Cart(){
        cartID = getLastCartId() + 1;
        this.amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(5);
    }

    /**
     * Using the Singleton Pattern, it creates an instance only once and retrieves the same object every time
     * @return cart
     */
    public static Cart getInstance(){
        if(cart == null) {
            cart =  new Cart();
        }
        return cart;
    }

    /**
     * Set default values
     */
    public void resetCart(){
        cartID = 1;
        this.amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(5);
    }

    /**
     * Goes through the supply orders in the table and returns the biggest ID
     * @return biggest ID
     */
    public int getLastCartId(){
        return SupplyOrder.getLastOrderId();
    }

    /**
     * Removes everything from the cart and resets the variables in the instance
     */
    public void deleteCart(){
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Delete from Cart";
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();
            cart.resetCart();

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Adds new items to table "cart" , by finding the price from a vendor by their ID
     * @param vendorID
     * @param materialName
     * @param quantity
     */
    public void addToCart(int vendorID, String materialName,int quantity){
        try {
            materialName.toUpperCase();
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();

            addToAmount(Vendor.getPriceByVendorID(vendorID,materialName)*quantity);
            double price = Vendor.getPriceByVendorID(vendorID,materialName);
            if(price > 0) {
                String query = "Insert Into Cart (cartID,price, orderDate, arrivalDate,productID,productQuantity,vendorID,rawMaterial)" +
                        "values ("+Cart.getInstance().cartID + "," + Vendor.getPriceByVendorID(vendorID, materialName) + ", '" + Date.todaysDate() + "', '" + Date.changeTodaysDateByDays(5) + "',"
                        + Material.getIDForMaterialName(materialName) + "," + quantity + "," + vendorID + ",'" + materialName.toUpperCase() + "');";
                PreparedStatement statement = con.prepareStatement(query);
                statement.executeUpdate();
            }


        }catch(NumberFormatException numF1) {

        }catch(Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Adds to the current total
     * @param money
     */
    public void addToAmount(double money){
        amount += money;
    }

    /**
     * Retrieves the current total
     * @return
     */

    public double getAmount() {
        return amount;
    }

    /**
     * Sets the current total
     * @param money
     */
    public static void setAmount(double money) {
        amount = money;
    }

    /**
     * Gets the prices and quantities for all the items in the cart, and multiplies them to update the total for the current cart
     * @throws SQLException
     */
    public static void updateAmount() throws SQLException {
        JavaConnector javaConnector = new JavaConnector();
        Connection con = javaConnector.getConnection();

        String query = "Select * from cart;";
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery(query);
        ArrayList<Double> price = new ArrayList<>();
        ArrayList<Integer> quantity = new ArrayList<>();
        while(resultSet.next()){
            price.add(resultSet.getDouble("price"));
            quantity.add(resultSet.getInt("productQuantity"));
        }
        double total=0.0;
        for(int i= 0; i< price.size(); i++) {
            total += price.get(i)*quantity.get(i);
        }
        double number = total;
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        total = bd.doubleValue();
        Cart.setAmount(total);
    }

    /**
     * Retrieves cartID
     * @return cartID
     */
    public int getCartID() {
        return cartID;
    }
}
