package GUI;

import GUI.supplies.Material;

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
}
