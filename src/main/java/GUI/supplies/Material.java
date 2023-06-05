package GUI.supplies;

import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Material {
    int inventoryLevel;
    int materialID;
    String materialName;
    int moldTemperature;
    int plasticDensity;

    /**
     * Constructor
     * @param inventoryLevel inventory level
     * @param materialID material ID
     * @param materialName material name
     * @param moldTemperature mold temperature
     * @param plasticDensity plastic density
     */

    public Material(int inventoryLevel, int materialID, String materialName, int moldTemperature, int plasticDensity){
        this.materialID = materialID;
        this.materialName = materialName;
        this.moldTemperature = moldTemperature;
        this.plasticDensity = plasticDensity;
        this.inventoryLevel = inventoryLevel;
    }

    /**
     * Constructor
     * @param materialID material ID
     * @param materialName material name
     * @param inventoryLevel inventory level
     */
    public Material (int materialID, String materialName, int inventoryLevel){
        this.materialName = materialName;
        this.materialID = materialID;
        this.inventoryLevel = inventoryLevel;

    }

    /**
     * Retrieves the material's ID stored in the database
     * @param name name
     * @return material
     */
    public static int getIDForMaterialName(String name){
           int i = 0 ;
            try {

                JavaConnector javaConnector = new JavaConnector();
                Connection con = javaConnector.getConnection();
                String query = "SELECT materialID FROM materials WHERE materialName = ?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setString(1, name);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt("materialID");
                }
                i = resultSet.getInt("materialID");

            }catch(Exception e1) {
                e1.printStackTrace();
            }
            return i;

    }

    public int getMaterialID() {
        return materialID;
    }

    public int getMoldTemperature() {
        return moldTemperature;
    }

    public int getPlasticDensity() {
        return plasticDensity;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getInventoryLevel() {
        return inventoryLevel;
    }
}
