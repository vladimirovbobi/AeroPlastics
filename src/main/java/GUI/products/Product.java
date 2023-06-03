package GUI.products;

public class Product{

    private int inventoryLevel;
    private int productID;
    private String productName;
    private double price;
    private String rawMaterial;

    public Product(int productID, String productName, double price, String rawMaterial, int inventoryLevel){
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.rawMaterial = rawMaterial;
        this.inventoryLevel = inventoryLevel;
    }

    public int getInventoryLevel() {
        return inventoryLevel;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getRawMaterial() {
        return rawMaterial;
    }
}
