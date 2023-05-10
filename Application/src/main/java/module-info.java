module login.aeroplastics {
    requires javafx.controls;
    requires javafx.fxml;

    //initiate javafx.fxml;
    opens login to javafx.fxml;
    opens customer to javafx.fxml;

    //export sheet
    exports login;
    exports customer;
}