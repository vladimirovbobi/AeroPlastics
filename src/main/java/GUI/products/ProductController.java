package GUI.products;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.orders.addOrder.AddOrderApplication;
import GUI.products.addProduct.AddProductApplication;
import GUI.products.removeProduct.RemoveProductApplication;
import GUI.supplies.Material;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * The type Product controller.
 */
public class ProductController {

    @FXML
    private TableView<Product> displayTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, String> rawMaterialColumn;

    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
    }

    @FXML
    private Button menuButton;
    /**
     * Method that handles menu button in product page.
     * Redirects to MenuApplication.
     */
    @FXML
    private void handleMenuButtonClick() throws IOException {
        viewModel.showMenuWindow();
    }
    @FXML
    private Button addProductButton;
    /**
     * Method that handles add product button in product page.
     * Displays external window that has fields for adding a product.
     */
    @FXML
    private void handleAddProductButtonClick() throws IOException {
        AddProductApplication addProductApp = new AddProductApplication();
        Stage stage = new Stage();
        addProductApp.start(stage);
    }
    @FXML
    private Button removeProductButton;
    /**
     * Method that handles remove product button in product page.
     * Displays external window that has fields for removing a product.
     */
    @FXML
    private void handleRemoveProductButtonClick() throws IOException {
        RemoveProductApplication removeProductApp = new RemoveProductApplication();
        Stage stage = new Stage();
        removeProductApp.start(stage);
    }
    /**
     * Method that populates table in product window.
     */
    public void populateProductsTable() {
        // Retrieve materials data from the database (example code)
        List<Product> products = JavaConnector.getAllProducts(); // Replace with your actual code to fetch data from the database

        // Clear existing data from the table
        displayTable.getItems().clear();

        // Populate the table with materials data
        displayTable.getItems().addAll(products);
    }
}
