package GUI.supplies;

import GUI.JavaConnector;

import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;

public class Vendor {
    private int vendorID;
    private String companyName;
    HashMap<Material, Double> materialPrice;

    public  Vendor (){}
    public Vendor(String companyName, HashMap <Material, Double> supply){
        this.vendorID = getLastVendorId() + 1;
        this.companyName = companyName;
        materialPrice = supply;
    }
    public int getLastVendorId(){
        int max =0;
        try {
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            String query = "Select vendorID from vendor";
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet result = statement.executeQuery();


            while (result.next()) {
                if(result.getInt("vendorID")> max){
                    max = result.getInt("vendorID");
                }
            }

        } catch (NumberFormatException numF1) {

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return max;
    }

    public int getVendorID() {
        return vendorID;
    }

    public String getCompanyName() {
        return companyName;
    }


    public void addVendorToDatabase() {

        try {
            Iterator <Material> iterator = materialPrice.keySet().iterator();
            JavaConnector javaConnector = new JavaConnector();
            Connection con = javaConnector.getConnection();
            while (iterator.hasNext()) {
                Material mat = iterator.next();
                String query = "Insert Into vendor (vendorID, companyName, rawMaterial, price) values (" +
                        getVendorID() + ",'" + getCompanyName() + "' , '"+  mat.materialName +"' , "+ materialPrice.get(mat) + " ); ";
                PreparedStatement statement = con.prepareStatement(query);
                statement.executeUpdate();
            }
        }catch(SQLException numF1) {
            numF1.printStackTrace();
        }catch(Exception e1) {
            e1.printStackTrace();
        }


    }




}
