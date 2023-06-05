package GUI.orders;

import GUI.customers.Customer;
/**
 * The Order class represents an order with its properties and associated customer.
 */

public class Order {
    private int orderID;
    private String address;
    private boolean isShipped;
    private int customerID;
    private Customer customer;
    private int productID;

    /**
     * Constructs an Order object with the specified properties.
     *
     * @param orderID    The ID of the order.
     * @param address    The address of the order.
     * @param isShipped  The shipping status of the order.
     * @param customerID The ID of the associated customer.
     * @param productId
     */
    public Order(int orderID, String address, boolean isShipped, int customerID, int productId) {
        this.orderID = orderID;
        this.address = address;
        this.isShipped = isShipped;
        this.customerID = customerID;
        this.productID = productId;
    }

    /**
     * Returns the ID of the order.
     *
     * @return The order ID.
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Sets the ID of the order.
     *
     * @param orderID The order ID to set.
     */

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * Returns the address of the order.
     *
     * @return The address of the order.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the order.
     *
     * @param address The address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the shipping status of the order.
     *
     * @return The shipping status of the order.
     */
    public boolean isShipped() {
        return isShipped;
    }

    /**
     * Sets the shipping status of the order.
     *
     * @param shipped The shipping status to set.
     */
    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    /**
     * Returns the ID of the associated customer.
     *
     * @return The customer ID.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the ID of the associated customer.
     *
     * @param customerID The customer ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Returns the associated customer.
     *
     * @return The customer associated with the order.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the associated customer.
     *
     * @param customer The customer to set.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
