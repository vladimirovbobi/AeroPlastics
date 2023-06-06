package GUI;

import GUI.customers.Customer;
import GUI.products.Product;
import GUI.orders.Order;
import GUI.supplies.Material;
import GUI.supplies.Vendor;
import GUI.supplies.resupply.Cart;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Java connector.
 */
public class JavaConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/aeroplastics";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@Ziemlupr2072";

    /**
     * Gets connection.
     *
     * @return the connection
     * @throws SQLException the sql exception
     */
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    /**
     * Prepare statement prepared statement.
     *
     * @param query the query
     * @return the prepared statement
     * @throws SQLException the sql exception
     */
    public PreparedStatement prepareStatement(String query) throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement(query);
    }

    /**
     * Connection to material table to display on supply window.
     *
     * @return material table display on supply window.
     */
    public static List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve materials data
            String query = "SELECT inventoryLevel, materialID, materialName, moldTemperature, plasticDensity FROM materials";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //Process the result set
            while (resultSet.next()) {
                int inventoryLevel = resultSet.getInt("inventoryLevel");
                int materialID = resultSet.getInt("materialID");
                String materialName = resultSet.getString("materialName");
                int moldTemperature = resultSet.getInt("moldTemperature");
                int plasticDensity = resultSet.getInt("plasticDensity");

                //Create a Material object and add it to the list
                Material material = new Material(inventoryLevel, materialID, materialName, moldTemperature, plasticDensity);
                materials.add(material);
            }

            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }

    /**
     * Connection to customer table to display on customer window.
     *
     * @return customer table display on customer window.
     */
    public static List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve customer data
            String query = "SELECT customerID, firstName, lastName, affiliation FROM customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String company = resultSet.getString("affiliation");

                // Create a Customer object and add it to the list
                Customer customer = new Customer(customerID, firstName, lastName, company);
                customers.add(customer);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Gets all vendors.
     *
     * @return the all vendors
     */
    public static List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve materials data
            String query = "SELECT vendorID,companyName, rawMaterial,productQuantity, price FROM vendor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //Process the result set
            while (resultSet.next()) {

                int vendorID = resultSet.getInt("vendorID");
                String vendorName = resultSet.getString("companyName");
                int productQuantity = resultSet.getInt("productQuantity");
                String rawMaterial = resultSet.getString("rawMaterial");
                double price  = resultSet.getDouble("price");

                //Create a Vendor object and add it to the list
                Vendor vendor = new Vendor( vendorName,vendorID, rawMaterial, productQuantity, price);
                vendors.add(vendor);
            }

            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    /**
     * Get cart list.
     *
     * @return the list
     */
    public static List<Vendor> getCart(){
        List<Vendor> vendors = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            String query2 = "SELECT * FROM cart";
            Statement statement2 = connection.createStatement();
            ResultSet resultSet2 = statement2.executeQuery(query2);

            ArrayList<Integer> productIDs = new ArrayList<>();
            ArrayList<Integer> productQuantity = new ArrayList<>();
            ArrayList<Integer> vendorIDs = new ArrayList<>();
            ArrayList<String> rawMaterials = new ArrayList<>();
            while (resultSet2.next()) {

                productQuantity.add(resultSet2.getInt("productQuantity"));
                productIDs.add(resultSet2.getInt("productID"));
                vendorIDs.add(resultSet2.getInt("vendorID"));
                rawMaterials.add(resultSet2.getString("rawMaterial"));
            }

            ResultSet resultSet = null;
            PreparedStatement statement = null;
            int quantity = 0;
            for (int i = 0; i <= vendorIDs.size(); i++) {
                // Execute a SQL query to retrieve materials data

                if (i == vendorIDs.size()) {
                    Cart cart = Cart.getInstance();
                    cart.updateAmount();

                    Vendor vendor = new Vendor("Cart Total: ",cart.getCartID(), cart.getAmount(), quantity);
                    vendors.add(vendor);
                } else {
                    quantity += productQuantity.get(i);
                    String query = "SELECT * FROM vendor WHERE vendorID = ? AND rawMaterial = ?";
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, vendorIDs.get(i));
                    statement.setString(2, rawMaterials.get(i));
                    resultSet = statement.executeQuery();
                    //Process the result set
                    while (resultSet.next()) {

                        String rawMaterial = resultSet.getString("rawMaterial");
                        String vendorName = resultSet.getString("companyName");
                        double price = resultSet.getDouble("price");
                        //Create a Vendor object and add it to the list
                        Vendor vendor = new Vendor(vendorName,vendorIDs.get(i), rawMaterial, productQuantity.get(i), price);
                        vendors.add(vendor);
                    }
                }
            }
            //Close the database connection and resources
             connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    /**
     * Search customers by name or id list.
     *
     * @param name the name
     * @return the list
     */
    public static List<Customer> searchCustomersByNameOrID(String name) {
        List<Customer> customers = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);

            // Execute a SQL query to retrieve customer data
            String query = "SELECT * FROM customer WHERE firstName = ? OR lastName = ? OR customerID = ? OR affiliation = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, name);
            try {
                statement.setInt(3, Integer.parseInt(name));
            } catch (NumberFormatException e) {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setString(4,name);
            ResultSet resultSet = statement.executeQuery();

            //Process the result set
            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String affiliation = resultSet.getString("affiliation");

                //Create a Customer object and add it to the list
                Customer customer = new Customer(customerID,firstName,lastName,affiliation);
                customers.add(customer);
            }

            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Search orders by name or id list.
     *
     * @param name the name
     * @return the list
     */
    public static List<Order> searchOrdersByNameOrID(String name){
        List<Order> orders = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve order data
            String query = "SELECT * FROM orders WHERE orderID = ? OR customerID = ? OR customerID IN " +
                    "(SELECT customerID FROM customer WHERE firstName = ? OR lastName = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            int orderID;
            try {
                orderID = Integer.parseInt(name);
            } catch (NumberFormatException e) {
                orderID = -1;
            }
            statement.setInt(1, orderID);
            statement.setString(2, name);
            statement.setString(3, name);
            statement.setString(4, name);

            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            while (resultSet.next()) {
                int orderIDResult = resultSet.getInt("orderID");
                String address = resultSet.getString("address");
                boolean isShipped = resultSet.getBoolean("isShipped");
                int customerID = resultSet.getInt("customerID");
                int productID = resultSet.getInt("productID");
                int quantity = resultSet.getInt("quantity");
                String arrivalDate = resultSet.getString("arrivalDate");
                String orderDate = resultSet.getString("orderDate");

                // Create an Order object and add it to the list
                Order order = new Order(orderIDResult, address, isShipped, customerID, productID, quantity,orderDate,arrivalDate );
                orders.add(order);
            }

            // Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Search vendor material by name list.
     *
     * @param name the name
     * @return the list
     */
    public static List<Vendor> searchVendorMaterialByName(String name){
            name =name.toUpperCase();

            List<Vendor> vendors = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);

            // Execute a SQL query to retrieve customer data
            String query = "SELECT * FROM vendor WHERE vendorID = ? OR companyName = ? OR rawMaterial = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            try {
                statement.setInt(1, Integer.parseInt(name));
            } catch (NumberFormatException e) {
                statement.setNull(1, Types.INTEGER);
            }
            statement.setString(2,name);
            statement.setString(3,name);
            ResultSet resultSet = statement.executeQuery();


            //Process the result set
                while (resultSet.next()) {

                    int vendorID = resultSet.getInt("vendorID");
                    String vendorName = resultSet.getString("companyName");
                    int productQuantity = resultSet.getInt("productQuantity");
                    String rawMaterial = resultSet.getString("rawMaterial");
                    double price  = resultSet.getDouble("price");

                    //Create a Vendor object and add it to the list
                    Vendor vendor = new Vendor( vendorName,vendorID, rawMaterial, productQuantity, price);
                    vendors.add(vendor);
                }

                //Close the database connection and resources
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return vendors;
        }

    /**
     * Search materials by name list.
     *
     * @param name the name
     * @return the list
     */
    public static List<Material> searchMaterialsByName(String name) {
        List<Material> materials = new ArrayList<>();

            try {
                //Establish a connection to the database
                Connection connection = DriverManager.getConnection(DB_URL,USERNAME, PASSWORD);

                // Execute a SQL query to retrieve customer data
                String query = "SELECT * FROM materials WHERE materialID = ? OR materialName = ?";
                PreparedStatement statement = connection.prepareStatement(query);

                try {
                    statement.setInt(1, Integer.parseInt(name));
                } catch (NumberFormatException e) {
                    statement.setNull(1, Types.INTEGER);
                }
                statement.setString(2,name);
                ResultSet resultSet = statement.executeQuery();

            //Process the result set
            while (resultSet.next()) {
                int inventoryLevel = resultSet.getInt("inventoryLevel");
                int materialID = resultSet.getInt("materialID");
                String materialName = resultSet.getString("materialName");
                int moldTemperature = resultSet.getInt("moldTemperature");
                int plasticDensity = resultSet.getInt("plasticDensity");

                //Create a Material object and add it to the list
                Material material = new Material(inventoryLevel, materialID, materialName, moldTemperature, plasticDensity);
                materials.add(material);
            }

            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materials;
    }

    /**
     * Gets supply orders.
     *
     * @return the supply orders
     */
    public static List<Vendor> getSupplyOrders() {
        List<Vendor> vendors = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve materials data
            String query = "SELECT * FROM supplyOrder";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            int totalQuantity = 0;
            int supplyOrderID = 0;
            //Process the result set
            while (resultSet.next()) {
                if(resultSet.getInt("supplyOrderID") != supplyOrderID){
                    supplyOrderID = resultSet.getInt("supplyOrderID");
                    totalQuantity = 0;
                }

                int vendorID = resultSet.getInt("vendorID");
                int productQuantity = resultSet.getInt("quantity");
                String rawMaterial = resultSet.getString("rawMaterial");
                double price  = resultSet.getDouble("price");
                if(rawMaterial.equals("Total")){
                    Vendor vendor = new Vendor("Cart", supplyOrderID,rawMaterial, totalQuantity, price);
                    vendors.add(vendor);
                }else {
                    totalQuantity += productQuantity;
                    //Create a Vendor object and add it to the list
                    Vendor vendor = new Vendor(Vendor.getVendorByID(vendorID), vendorID, rawMaterial, productQuantity, price);
                    vendors.add(vendor);
                }
            }

            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendors;
    }

    /**
     * Connection to product table to display on product window.
     *
     * @return product table display on product window.
     */
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            //Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve materials data
            String query = "SELECT productID, productName, price, rawMaterial, inventoryLevel FROM products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            //Process the result set
            while (resultSet.next()) {
                int quantity = resultSet.getInt("inventoryLevel");
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                double price = resultSet.getDouble("price");
                String rawMaterial = resultSet.getString("rawMaterial");

                //Create a Material object and add it to the list
                Product product = new Product(productID, productName, price, rawMaterial, quantity);
                products.add(product);
            }

            //Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Search product by name or id product.
     *
     * @param name the name
     * @param id   the id
     * @return the product
     */
    public static Product searchProductByNameOrID(String name, int id) {
        Product product = null;

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Prepare the SQL query based on whether the search term is a name or ID
            String sqlQuery;
            PreparedStatement statement;
            if (id != 0) {
                sqlQuery = "SELECT productID, productName, price, rawMaterial, inventoryLevel FROM products " +
                        "WHERE productID = ?";
                statement = connection.prepareStatement(sqlQuery);
                statement.setInt(1, id);
            } else {
                sqlQuery = "SELECT productID, productName, price, rawMaterial, inventoryLevel FROM products " +
                        "WHERE productName = ?";
                statement = connection.prepareStatement(sqlQuery);
                statement.setString(1, name);
            }

            ResultSet resultSet = statement.executeQuery();

            // Process the result set
            if (resultSet.next()) {
                int inventoryLevel = resultSet.getInt("inventoryLevel");
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                double price = resultSet.getDouble("price");
                String rawMaterial = resultSet.getString("rawMaterial");

                // Create a Product object
                product = new Product(productID, productName, price, rawMaterial, inventoryLevel);
            }

            // Close the database connection and resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    /**
     * Sets inventory level.
     *
     * @param productID         the product id
     * @param newInventoryLevel the new inventory level
     */
    public static void setInventoryLevel(int productID, int newInventoryLevel) {
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Prepare the SQL query
            String query = "UPDATE products SET inventoryLevel = ? WHERE productID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newInventoryLevel);
            statement.setInt(2, productID);

            // Execute the update
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory level updated successfully.");
            } else {
                System.out.println("Failed to update inventory level.");
            }

            // Close the database connection and resources
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connection to order table to display on order window.
     *
     * @return order table display on order window.
     */
    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "SELECT * FROM orders";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int orderID = resultSet.getInt("orderID");
                String address = resultSet.getString("address");
                boolean isShipped = resultSet.getBoolean("isShipped");
                int customerID = resultSet.getInt("customerID");
                int productID = resultSet.getInt("productID");
                int quantity = resultSet.getInt("quantity");
                String orderDate = resultSet.getString("orderDate");
                String arrivalDate = resultSet.getString("arrivalDate");

                Order order = new Order(orderID, address, isShipped, customerID, productID,quantity,orderDate,arrivalDate);
                orders.add(order);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

}
