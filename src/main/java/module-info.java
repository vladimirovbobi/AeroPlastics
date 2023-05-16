module com.GUI.aeroplastics {
    requires javafx.controls;
    requires javafx.fxml;

    //initiate javafx.fxml;
    opens GUI.example to javafx.fxml;
    opens GUI.login to javafx.fxml;
    opens GUI.menu to javafx.fxml;
    opens GUI.orders to javafx.fxml;
    opens GUI.customers to javafx.fxml;
    opens GUI.products to javafx.fxml;
    opens GUI.supplies to javafx.fxml;

    //export sheet
    exports GUI.example;
    exports GUI.login;
    exports GUI.menu;
    exports GUI.orders;
    exports GUI.customers;
    exports GUI.products;
    exports GUI.supplies;
}