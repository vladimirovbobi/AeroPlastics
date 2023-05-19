package GUI.customers.addCustomer;
import GUI.customers.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import GUI.ViewModel;

public class AddCustomerController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField affiliationTextField;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;

    private ViewModel viewModel;


    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @FXML
    private void initialize() {
        addButton.setOnAction(event -> handleAddButtonClick());
        cancelButton.setOnAction(event -> handleCancelButtonClick());
    }

    @FXML
    private void handleAddButtonClick() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String affiliation = affiliationTextField.getText();

        // Create a new Customer object
        Customer newCustomer = new Customer(firstName, lastName, affiliation);



        // you can also close the Add Customer window after processing
        cancelButton.getScene().getWindow().hide();
    }

    @FXML
    private void handleCancelButtonClick() {
        cancelButton.getScene().getWindow().hide();
    }
}


