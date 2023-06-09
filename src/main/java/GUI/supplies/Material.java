package GUI.supplies;

import GUI.JavaConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * The type Material.
 */
public class Material {
    /**
     * The Inventory level.
     */
    int inventoryLevel;
    /**
     * The Material id.
     */
    int materialID;
    /**
     * The Material name.
     */
    String materialName;
    /**
     * The Mold temperature.
     */
    int moldTemperature;
    /**
     * The Plastic density.
     */
    int plasticDensity;

    /**
     * Constructor
     *
     * @param inventoryLevel  inventory level
     * @param materialID      material ID
     * @param materialName    material name
     * @param moldTemperature mold temperature
     * @param plasticDensity  plastic density
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
     *
     * @param materialID     material ID
     * @param materialName   material name
     * @param inventoryLevel inventory level
     */
    public Material (int materialID, String materialName, int inventoryLevel){
        this.materialName = materialName;
        this.materialID = materialID;
        this.inventoryLevel = inventoryLevel;

    }

    /**
     * Retrieves the material's ID stored in the database
     *
     * @param name name
     * @return material int
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

    /**
     * Reduce material by.
     *
     * @param name          the name
     * @param numberOfUnits the number of units
     */
    public static void reduceMaterialBy(String name, int numberOfUnits){
        int quantity = 0 ;
        try {

            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT inventoryLevel FROM materials WHERE materialName=? OR materialID = ?;";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1,name);
            try {
                statement.setInt(2, Integer.parseInt(name));
            }catch (NumberFormatException e){
                statement.setNull(2, Types.INTEGER);
            }
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                quantity = resultSet.getInt("inventoryLevel");
            }

            //Reduce the quantity because a product is made
            quantity -= 3 * numberOfUnits;
            query = "Update materials SET inventoryLevel = ? WHERE materialName = ? OR materialID = ? ";
            statement = con.prepareStatement(query);
            statement.setString(2, name);
            try {
                statement.setInt(3, Integer.parseInt(name));
            }catch (NumberFormatException e){
                statement.setNull(3, Types.INTEGER);
            }
            statement.setInt(1, quantity);
            statement.executeUpdate();

        }catch(Exception e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Is in stock to produce boolean.
     *
     * @param name                  the name
     * @param numberOfUnitsProduced the number of units produced
     * @return the boolean
     */
    public static boolean isInStockToProduce(String name,int numberOfUnitsProduced){
      int quantity = 0;
        try {

            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "SELECT inventoryLevel FROM materials WHERE materialName = ? OR materialID = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, name);
            try {
                statement.setInt(2, Integer.parseInt(name));
            }catch (NumberFormatException e){
                statement.setNull(2, Types.INTEGER);
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                quantity=   resultSet.getInt("inventoryLevel");
            }
            //produce 1 costs 3 units of material
            if (quantity >= 3*numberOfUnitsProduced){
                reduceMaterialBy(name, numberOfUnitsProduced);
                return true;
            }else{
                return false;
            }
        }catch(Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    /**
     * Gets material id.
     *
     * @return the material id
     */
    public int getMaterialID() {
        return materialID;
    }

    /**
     * Gets mold temperature.
     *
     * @return the mold temperature
     */
    public int getMoldTemperature() {
        return moldTemperature;
    }

    /**
     * Gets plastic density.
     *
     * @return the plastic density
     */
    public int getPlasticDensity() {
        return plasticDensity;
    }

    /**
     * Gets material name.
     *
     * @return the material name
     */
    public String getMaterialName() {
        return materialName;
    }

    /**
     * Gets inventory level.
     *
     * @return the inventory level
     */
    public int getInventoryLevel() {
        return inventoryLevel;
    }
}
