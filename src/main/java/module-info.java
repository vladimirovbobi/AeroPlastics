module com.example.aeroplastics {
    requires javafx.controls;
    requires javafx.fxml;

    //initiate javafx.fxml;
    opens GUI.example to javafx.fxml;
    opens GUI.login to javafx.fxml;
    opens GUI.menu to javafx.fxml;
    opens GUI.orders to javafx.fxml;

    //export sheet
    exports GUI.example;
    exports GUI.login;
    exports GUI.menu;
    exports GUI.orders;
}