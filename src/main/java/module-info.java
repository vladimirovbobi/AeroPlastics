module com.GUI.aeroplastics {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    //initiate javafx.fxml;
    opens GUI.login to javafx.fxml;
    opens GUI.menu to javafx.fxml;
    opens GUI.orders to javafx.fxml;
        opens GUI.orders.addOrder to javafx.fxml;
        opens GUI.orders.removeOrder to javafx.fxml;
    opens GUI.customers to javafx.fxml;
        opens GUI.customers.addCustomer to javafx.fxml;
        opens GUI.customers.removeCustomer to javafx.fxml;
    opens GUI.products to javafx.fxml;
    opens GUI.supplies to javafx.fxml;
        opens GUI.supplies.removeSupply;
      //  opens GUI.supplies.addSupply;
        opens GUI.supplies.resupply;


    //export sheet
    exports GUI.login;
    exports GUI.menu;
    exports GUI.orders;
        exports GUI.orders.addOrder;
        exports GUI.orders.removeOrder;
    exports GUI.customers;
        exports GUI.customers.addCustomer;
        exports GUI.customers.removeCustomer;
    exports GUI.products;
    exports GUI.supplies;
        exports GUI.supplies.removeSupply;
        //exports GUI.supplies.addSupply;
        exports GUI.supplies.resupply;
}