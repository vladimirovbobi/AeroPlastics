package GUI.products;

import GUI.JavaConnector;
import GUI.ViewModel;
import GUI.products.addProduct.AddProductApplication;
import GUI.products.removeProduct.RemoveProductApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static GUI.JavaConnector.searchProductByNameOrID;

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
    @FXML
    private Button menuButton;
    @FXML
    private Button addProductButton;
    @FXML
    private Button removeProductButton;
    @FXML
    private Button viewAllButton;
    @FXML
    private Button singleViewButton;
    @FXML
    private TextField productTextField;

    private ViewModel viewModel;
    public void setViewModel(ViewModel viewModel){
        this.viewModel = viewModel;
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

    /**
     * Method that initializes table in supply window.
     */
    public void initialize() {
        // Initialize table columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("inventoryLevel"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        rawMaterialColumn.setCellValueFactory(new PropertyValueFactory<>("rawMaterial"));
    }

    /**
     * Method that handles menu button in product page.
     * Redirects to MenuApplication.
     */
    @FXML
    private void handleMenuButtonClick() throws IOException {
        viewModel.showMenuWindow();
    }

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
     * Method that handles view all button in products page.
     * Displays table in GUI.
     */
    @FXML
    private void handleViewAllButtonClick() throws IOException {
        populateProductsTable();
    }

    /**
     * Method that handles single view button in products page.
     * Displays table in GUI with product search.
     */
    @FXML
    private void handleSingleViewButtonClick() throws IOException {
        String searchTerm = productTextField.getText().trim(); // Get the search term from the textField

        if (!searchTerm.isEmpty()) {
            // Check if the search term is a valid integer
            int productID = 0;
            try {
                productID = Integer.parseInt(searchTerm);
            } catch (NumberFormatException e) {
                // Ignore the exception if the search term is not a valid integer
            }

            // Retrieve the product based on the search term (name or ID)
            Product product = searchProductByNameOrID(searchTerm, productID);

            // Clear existing data from the table
            displayTable.getItems().clear();

            if (product != null) {
                // Add the product to the table
                displayTable.getItems().add(product);
            } else {
                // Show a dialog if the product is not found
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Not Found");
                alert.setHeaderText(null);
                alert.setContentText("The product was not found.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleProductionRequestButtonCLick() throws IOException {
        String searchQuery = productTextField.getText(); // Assuming productTextField is the TextField where you enter the name or ID

        // Determine if the search query is a name or ID
        int productID = 0;
        String productName = null;
        try {
            productID = Integer.parseInt(searchQuery);
        } catch (NumberFormatException e) {
            productName = searchQuery;
        }

        // Retrieve the product based on the search query (name or ID)
        Product product = searchProductByNameOrID(productName, productID);

        if (product != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Production Request");
            dialog.setHeaderText("Enter the quantity to increase:");
            dialog.setContentText("Quantity:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(quantityStr -> {
                try {
                    int quantity = Integer.parseInt(quantityStr);

                    // Update the product quantity by adding the entered quantity to the existing quantity
                    int newQuantity = product.getInventoryLevel() + quantity;

                    // Call the setInventoryLevel method from the JavaConnector class
                    JavaConnector.setInventoryLevel(product.getProductID(), newQuantity);

                    // Show a confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Quantity Increased");
                    alert.setHeaderText(null);
                    alert.setContentText("The quantity has been increased successfully.");
                    alert.showAndWait();
                } catch (NumberFormatException e) {
                    // Show an error dialog if the quantity is not a valid number
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Quantity");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a valid quantity.");
                    alert.showAndWait();
                }
            });
        } else {
            // Show a dialog if the product is not found
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The product was not found.");
            alert.showAndWait();
        }
    }

    public void handleProduceMoreButtonClick(ActionEvent actionEvent) {
    }
}
