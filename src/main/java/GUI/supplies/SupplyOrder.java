package GUI.supplies;

import GUI.JavaConnector;
import GUI.supplies.resupply.Cart;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;


public class SupplyOrder{
    Vendor vendor;
    private int supplyOrderID;
    private String arrivalDate;
    private String orderDate;

    Material material;
    ArrayList<Material> materials;

    public SupplyOrder (){

    }

    /**
     * Contructor
     * @param orderPlaced
     * @param arrivalDate
     * @param vendor
     */
    public SupplyOrder(String orderPlaced, String arrivalDate,Vendor vendor){
        this.vendor = vendor;
        this.orderDate = orderPlaced;
        this.arrivalDate = arrivalDate;
    }

    /**
     * Contructor
     * @param orderPlaced
     * @param arrivalDate
     * @param materials
     * @param vendor
     */
    public SupplyOrder(String orderPlaced, String arrivalDate, ArrayList<Material> materials, Vendor vendor){


        this.vendor = vendor;
        this.materials = materials;
        this.arrivalDate = arrivalDate;
        this.orderDate = orderPlaced;
        supplyOrderID = getLastOrderId() +1;
    }

    /**
     * Goes through the supply orders in the table and returns the biggest ID
     * @return biggest ID
     */
    public static int getLastOrderId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select supplyOrderID from supplyOrder";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();



            while(result.next()){

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

    /**
     * Unused, can be integrated into automatic ordering in the future
     * @param material
     * @return
     */
    public int getVendorIDwithLowestPrice(String material){
        double minPrice = 0;
        int vendorID = 0;
        material.toUpperCase();
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select price from vendor where rawMaterial =' " + material + "';";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if (minPrice > result.getDouble("price")) {

                    vendorID = result.getInt("vendorID");
                    System.out.println(result.getInt("vendorID"));
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return vendorID;

    }

    /**
     * Creates order in the supply order table (every product is recorded), also the total is added to the table
     * Resets and deletes the cart
     * @throws SQLException
     */
    public static void submitOrder() throws SQLException {
        Cart cart = Cart.getInstance();
        JavaConnector connector = new JavaConnector();
        Connection connection = connector.getConnection();
        String query = "Select * from cart;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<String> rawMaterials  = new ArrayList<>();
        ArrayList<String> orderDate = new ArrayList<>();
        ArrayList<String> arrivalDate = new ArrayList<>();
        ArrayList<Integer> vendorID = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<Double>  price = new ArrayList<>();

        while (resultSet.next()){
            rawMaterials.add(resultSet.getString("rawMaterial"));
            orderDate.add(resultSet.getString("orderDate"));
            arrivalDate.add(resultSet.getString("arrivalDate"));
            vendorID.add(resultSet.getInt("vendorID"));
            quantities.add(resultSet.getInt("productQuantity"));
            price.add(resultSet.getDouble("price"));
        }
        //add total for every cart
        rawMaterials.add("Total");
        double total = 0.0;
        int quantity = 0;
        for(int i = 0 ; i < quantities.size(); i++){
            quantity += quantities.get(i);
            total += quantities.get(i) * price.get(i);
        }
        double number = total;
        BigDecimal bd = new BigDecimal(Double.toString(number));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        total = bd.doubleValue();
        price.add(total);


        for(int i = 0 ; i < quantities.size(); i++) {

            query = "Insert into supplyOrder (supplyOrderID, vendorID, rawMaterial,price,quantity, orderPlaced,arrivalDate) values ("
                    + cart.getCartID() + "," + vendorID.get(i) +",'"+rawMaterials.get(i)+"',"+ price.get(i)+"," + quantities.get(i) + ",'"
                    + orderDate.get(i) + "','" + arrivalDate.get(i)+ "');";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }
        query = "Insert into supplyOrder(supplyOrderID, rawMaterial, price, quantity) values("+ cart.getCartID() +",'"+ rawMaterials.get(rawMaterials.size()-1)
                +"'," +price.get(rawMaterials.size()-1)+", " + quantity +");";

        cart.deleteCart();
        statement = connection.prepareStatement(query);
        statement.executeUpdate();

        connection.close();
    }


}
