public class Material {
    int materialID;
    String materialName;
    int moldTemperature;
    int plasticDensity;
    public Material(int materialID, String materialName, int moldTemperature, int plasticDensity){
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
}
