package GUI.supplies.resupply;

import GUI.Date;
import GUI.JavaConnector;
import GUI.supplies.Material;
import GUI.supplies.Vendor;
import javafx.scene.shape.DrawMode;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;

public class Cart {
    private static Cart cart;
    private static double amount;
    private String orderDate,arrivalDate;
    private int cartID;
    public int numberOfItems =1 ;
    private Cart(){
        cartID = getLastCartId() + 1;
        this.amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(5);
    }
    public static Cart getInstance(){
        if(cart == null) {
            cart =  new Cart();
        }
        return cart;
    }
    public void resetCart(){
        cartID = 1;
        this.amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(5);
    }
//this method takes Material or getMaterial from the ProductToMaterialAdapter
    public int getLastCartId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select supplyOrderID from supplyorder";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
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
    public void addToAmount(double money){
        amount += money;
    }

    public double getAmount() {
        return amount;
    }

    public static void setAmount(double money) {
        amount = money;
    }

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

    public int getCartID() {
        return cartID;
    }
}
