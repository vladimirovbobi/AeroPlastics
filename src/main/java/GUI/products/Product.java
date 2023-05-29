package GUI.products;

public class Product {
    private int productID;
    private String productName;
    private double price;
    private String rawMaterial;

    private int inventoryLevel;
    public Product(int productID, String productName, double price, String rawMaterial, int inventoryLevel){
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.rawMaterial = rawMaterial;
        this.inventoryLevel = inventoryLevel;
    }

    public int getProductID() {
        return productID;
    }

    public int getInventoryLevel() {
        return inventoryLevel;
    }

    public double getPrice() {
        return price;
    }

    public String getProductName() {
        return productName;
    }

    public String getRawMaterial() {
        return rawMaterial;
    }
}

