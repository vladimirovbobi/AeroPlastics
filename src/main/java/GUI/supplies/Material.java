package GUI.supplies;

import GUI.Date;
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

    public Material(int inventoryLevel, int materialID, String materialName, int moldTemperature, int plasticDensity){
        this.materialID = materialID;
        this.materialName = materialName;
        this.moldTemperature = moldTemperature;
        this.plasticDensity = plasticDensity;
        this.inventoryLevel = inventoryLevel;
    }
    public Material (int materialID, String materialName, int inventoryLevel){
        this.materialName = materialName;
        this.materialID = materialID;
        this.inventoryLevel = inventoryLevel;

    }
    public static int getIDForMaterialName(String name){
           int i = 0 ;
            try {

                JavaConnector javaConnector = new JavaConnector();
                Connection con = javaConnector.getConnection();
                String query = "Select materialID from materials where materialName = '" + name + "');";
                PreparedStatement statement = con.prepareStatement(query);
                ResultSet resultSet= statement.executeQuery(query);
                if(resultSet.next()){
                    return resultSet.getInt("materialID");
                }
                i  = resultSet.getInt("materialID");

            }catch(NumberFormatException numF1) {

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
