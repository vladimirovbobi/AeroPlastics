package GUI.supplies;

import GUI.Date;

import java.util.ArrayList;


public class SupplyOrder extends Vendor{
    private Date arrivalDate;
    private Date orderDate;
    private Vendor vendor;
    ArrayList<Material> materials;
    public SupplyOrder(Date placed, Date arrival, ArrayList<Material> materials, Vendor vendor){
        super(vendor.getVendorID(), vendor.getCompanyName());
        this.vendor = vendor;
        this.materials = materials;
        arrivalDate = arrival;
        orderDate = placed;
    }
    public void createAnInvoice(){

    }

    @Override
    public int getVendorID() {
        return super.getVendorID();
    }

    @Override
    public String getCompanyName() {
        return super.getCompanyName();
    }

    public ArrayList<Material> getMaterials() {
        return materials;
    }

    public String getArrivalDate() {
        return arrivalDate.toString();
    }

    public String getOrderDate() {
        return orderDate.toString();
    }


}
