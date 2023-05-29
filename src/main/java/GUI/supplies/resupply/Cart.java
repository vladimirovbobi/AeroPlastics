package GUI.supplies.resupply;

import GUI.Date;
import GUI.JavaConnector;
import GUI.supplies.Material;
import GUI.supplies.Vendor;

import java.sql.*;

public class Cart {
    private static Cart cart;
    private double amount;
    private String orderDate,arrivalDate;
    private int cartID;
    private Cart(){
        cartID = 1;
        amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(5);
    }
    public static Cart getInstance(){
        if(cart == null) {
            return new Cart();
        }
        return cart;
    }
    public void resetCart(){
        cartID = 1;
        amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(5);
    }
//this method takes Material or getMaterial from the ProductToMaterialAdapter

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
    public Cart createCart() throws SQLException {

        JavaConnector javaConnector = new JavaConnector();
        Connection con = javaConnector.getConnection();

        String query = "Insert Into Cart (cartID, orderPlaced, arrivalDate)" +
                "values (1," + ", '" + Date.todaysDate() + "', '" + Date.changeTodaysDateByDays(5) + "',";
        PreparedStatement statement = con.prepareStatement(query);
        statement.executeUpdate();
        return getInstance();


    }
    public void addToCart(int vendorID, String materialName,int quantity){
        try {
            materialName.toUpperCase();
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();

            addToAmount(Vendor.getPriceByVendorID(vendorID,materialName)*quantity);

            String query = "Insert Into Cart (amount, productID,quantity)" +
                        "values ("+ cart.amount + Material.getIDForMaterialName(materialName) + "," +quantity+ ");";
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();


        }catch(NumberFormatException numF1) {

        }catch(Exception e1) {
            e1.printStackTrace();
        }
    }
    public void addToAmount(double money){
        this.amount += money;
    }

    public double getAmount() {
        return amount;
    }
}
