module login.aeroplastics {
    requires javafx.controls;
    requires javafx.fxml;

    //initiate javafx.fxml;
    opens login to javafx.fxml;
    opens menu to javafx.fxml;
    opens orders to javafx.fxml;

    //export sheet
    exports login;
    exports menu;
    exports orders;
}