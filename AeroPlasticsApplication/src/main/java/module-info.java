module login.aeroplastics {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens login to javafx.fxml;
    exports login;
}