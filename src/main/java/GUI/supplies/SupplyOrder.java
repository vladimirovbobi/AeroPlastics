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
     * Constructor
     * @param orderPlaced The Date the order is placed
     * @param arrivalDate The Date the order arrives
     * @param vendor Vendor Object
     */
    public SupplyOrder(String orderPlaced, String arrivalDate,Vendor vendor){
        this.vendor = vendor;
        this.orderDate = orderPlaced;
        this.arrivalDate = arrivalDate;
    }

    /**
     * Constructor
     * @param orderPlaced Date the order is placed
     * @param arrivalDate Date the order will arrive
     * @param materials  The name of the material
     * @param vendor Vendor Object
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
     * @return largest ID
     */
    public static int getLastOrderId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select supplyOrderID from supplyOrder";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {

                if (result.getInt("supplyOrderID") > max) {
                    max = result.getInt("supplyOrderID");
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }

    /**
     * Unused, can be integrated into automatic ordering in the future
     * @param material the name of the material
     * @return vendorID with the lowest price
     */
    public int getVendorIDwithLowestPrice(String material){
        double minPrice = 0;
        int vendorID = 0;
        material =  material.toUpperCase();
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

        }catch (Exception e1) {
            e1.printStackTrace();
        }
        return vendorID;

    }

    /**
     * Creates order in the supply order table (every product is recorded), also the total is added to the table
     * Resets and deletes the cart
     * @throws SQLException My SQL Exception
     */
    public static void submitOrder() throws SQLException {
        Cart cart = Cart.getInstance();
        JavaConnector connector = new JavaConnector();
        Connection connection = connector.getConnection();
        String query = "SELECT * FROM cart;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<String> rawMaterials = new ArrayList<>();
        ArrayList<String> orderDate = new ArrayList<>();
        ArrayList<String> arrivalDate = new ArrayList<>();
        ArrayList<Integer> vendorID = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();
        ArrayList<Double> price = new ArrayList<>();

        while (resultSet.next()) {
            rawMaterials.add(resultSet.getString("rawMaterial"));
            orderDate.add(resultSet.getString("orderDate"));
            arrivalDate.add(resultSet.getString("arrivalDate"));
            vendorID.add(resultSet.getInt("vendorID"));
            quantities.add(resultSet.getInt("productQuantity"));
            price.add(resultSet.getDouble("price"));
        }
        int quantity = 0;
        if(!vendorID.isEmpty()) {
            // Add total for every cart
            rawMaterials.add("Total");
            double total = 0.0;

            for (int i = 0; i < quantities.size(); i++) {
                quantity += quantities.get(i);
                total += quantities.get(i) * price.get(i);
            }
            double number = total;
            BigDecimal bd = new BigDecimal(Double.toString(number));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            total = bd.doubleValue();
            price.add(total);
        }else{
            return;
        }
        // Update vendor table with the new productQuantity and materials table with inventoryLevel
        for (int i = 0; i < quantities.size(); i++) {
            // Subtract productQuantity from vendor table
            String updateVendorQuery = "UPDATE vendor SET productQuantity = productQuantity - ? WHERE vendorID = ? AND rawMaterial = ?";
            PreparedStatement updateVendorStatement = connection.prepareStatement(updateVendorQuery);
            updateVendorStatement.setInt(1, quantities.get(i));
            updateVendorStatement.setInt(2, vendorID.get(i));
            updateVendorStatement.setString(3, rawMaterials.get(i));
            updateVendorStatement.executeUpdate();

            // Add productQuantity to materials table
            String updateMaterialsQuery = "UPDATE materials SET inventoryLevel = inventoryLevel + ? WHERE materialName = ?";
            PreparedStatement updateMaterialsStatement = connection.prepareStatement(updateMaterialsQuery);
            updateMaterialsStatement.setInt(1, quantities.get(i));
            updateMaterialsStatement.setString(2, rawMaterials.get(i));
            updateMaterialsStatement.executeUpdate();

            // Insert into supplyOrder table
            query = "INSERT INTO supplyOrder (supplyOrderID, vendorID, rawMaterial, price, quantity, orderPlaced, arrivalDate) VALUES ("
                    + getLastOrderId() + "," + vendorID.get(i) + ",'" + rawMaterials.get(i) + "'," + price.get(i) + "," + quantities.get(i) + ",'"
                    + orderDate.get(i) + "','" + arrivalDate.get(i) + "');";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        }

        // Insert a new entry in the supplyOrder table with the total quantity
        query = "INSERT INTO supplyOrder (supplyOrderID, rawMaterial, price, quantity) VALUES(" + cart.getCartID() + ",'"
                + rawMaterials.get(rawMaterials.size() - 1) + "'," + price.get(rawMaterials.size() - 1) + ", " + quantity + ");";
        cart.deleteCart();
        statement = connection.prepareStatement(query);
        statement.executeUpdate();

        connection.close();
    }
}
