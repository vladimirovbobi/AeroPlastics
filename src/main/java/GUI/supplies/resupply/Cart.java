package GUI.supplies.resupply;

import GUI.Date;
import GUI.JavaConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cart {
    private double amount;
    private String orderDate,arrivalDate;
    private int cartID;
    public Cart(){
        cartID = getLastCartID() + 1;
        amount = 0.0;
        orderDate = Date.todaysDate();
        arrivalDate = Date.changeTodaysDateByDays(7);
    }


    public static void print() {
        Cart c = new Cart();
        System.out.println(c.orderDate);
        System.out.println(c.arrivalDate);
    }

    public int getLastCartID() {

        int max = 0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select cartID from cart";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if (result.getInt("cartID") > max) {
                    max = result.getInt("cartID");
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }
}
