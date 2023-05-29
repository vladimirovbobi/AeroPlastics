package GUI;

import GUI.customers.CustomerApplication;
import GUI.menu.MenuApplication;
import GUI.orders.OrdersApplication;
import GUI.products.ProductApplication;
import GUI.supplies.SupplyApplication;
import GUI.supplies.removeSupply.RemoveSupplyApplication;
import GUI.supplies.resupply.ResupplyApplication;
import GUI.supplies.resupply.resupplyPop.ResupplyPopApplication;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Enables and disables stages, so only the necessary window is shown on the screen.
 */
public class ViewModel {
    private Stage currentStage;

    /**
     * Sets current stage.
     *
     * @param stage the stage
     */
    public void setCurrentStage(Stage stage) {
        this.currentStage = stage;
    }
    public void closeCurrentStage(){this.currentStage.close();}
    /**
     * Show customers window.
     *
     * @throws IOException the io exception
     */
    public void showCustomersWindow() throws IOException {
        CustomerApplication customerApp = new CustomerApplication();
        Stage stage = new Stage();
        customerApp.start(stage);
        currentStage.close(); //close the current window
    }
    public void showResupplyPopWindow() throws IOException {
        ResupplyPopApplication resupplyPopApp = new ResupplyPopApplication();
        Stage stage = new Stage();
        resupplyPopApp.start(stage);
    }
    public void showRemoveSupplyOrderPopWindow() throws IOException {
        RemoveSupplyApplication removePopApp = new RemoveSupplyApplication();
        Stage stage = new Stage();
        removePopApp.start(stage);
    }
    /**
     * Show order window.
     *
     * @throws IOException the io exception
     */
    public void showOrderWindow() throws IOException {
        OrdersApplication orderApp = new OrdersApplication();
        Stage stage = new Stage();
        orderApp.start(stage);
        currentStage.close(); //close the current window
    }

    /**
     * Show product window.
     *
     * @throws IOException the io exception
     */
    public void showProductWindow() throws IOException {
        ProductApplication productApp = new ProductApplication();
        Stage stage = new Stage();
        productApp.start(stage);
        currentStage.close(); //close the current window
    }

    /**
     * Show supply window.
     *
     * @throws IOException the io exception
     */
    public void showSupplyWindow() throws IOException {
        SupplyApplication supplyApp = new SupplyApplication();
        Stage stage = new Stage();
        supplyApp.start(stage);
        currentStage.close(); //close the current window
    }

    /**
     * Show menu window.
     *
     * @throws IOException the io exception
     */
    public void showMenuWindow() throws IOException {
        MenuApplication menuApp = new MenuApplication();
        Stage stage = new Stage();
        menuApp.start(stage);
        currentStage.close(); //close the current window
    }
    public void showSupplyOrderWindow() throws IOException {
        ResupplyApplication resupplyApp = new ResupplyApplication();
        Stage stage = new Stage();
        resupplyApp.start(stage);
        currentStage.close(); //close the current window
    }
}
