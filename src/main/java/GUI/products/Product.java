package GUI.products;

public class Product{

    int inventoryLevel;
    int productID;
    String productName;
    double price;
    String rawMaterial;

    public Product(int productID, String productName, double price, String rawMaterial, int inventoryLevel){
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.rawMaterial = rawMaterial;
        this.inventoryLevel = inventoryLevel;
    }
}
