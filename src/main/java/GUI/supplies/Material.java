package GUI.supplies;

public class Material {
    int inventoryLevel;
    int materialID;
    String materialName;
    int moldTemperature;
    int plasticDensity;
    public Material(int inventoryLevel, int materialID, String materialName, int moldTemperature, int plasticDensity){
        this.inventoryLevel = inventoryLevel;
        this.materialID = materialID;
        this.materialName = materialName;
        this.moldTemperature = moldTemperature;
        this.plasticDensity = plasticDensity;
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
