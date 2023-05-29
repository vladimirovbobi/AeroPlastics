package GUI.products;

import GUI.supplies.Material;

public class ProductToMaterialAdapter extends Material {
    Material material;
    public ProductToMaterialAdapter(Product product) {
        super(product.getProductID(), product.getProductName(), product.getInventoryLevel());
    }

    public Material getMaterial() {
        material = new Material(this.getMaterialID(), this.getMaterialName(),this.getInventoryLevel());
        return material;
    }
}
