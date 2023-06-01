package GUI;

import GUI.customers.Customer;
import GUI.products.ProductApplication;
import GUI.supplies.Material;
import GUI.supplies.Vendor;
import GUI.supplies.resupply.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class JavaConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/aeroplastics";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Bob4oSirop4o";


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
     * Connection to material table to display on supply window.
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

    public static List<Customer> getAllCustomers() {

        List<Customer> customers = new ArrayList<>();

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            // Execute a SQL query to retrieve customer data
            String query = "SELECT customerID, firstName, lastName, company FROM customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String company = resultSet.getString("company");

                // Create a Customer object and add it to the list
                Customer customer = new Customer(firstName, lastName, company);
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
            for (int i = 0; i <= vendorIDs.size(); i++) {
                // Execute a SQL query to retrieve materials data

                if (i == vendorIDs.size()) {

                    Cart.updateAmount();
                    Vendor vendor = new Vendor("Total: ", Cart.getInstance().getAmount());
                    vendors.add(vendor);
                } else {
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

}
