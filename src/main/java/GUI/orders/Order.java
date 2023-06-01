package GUI.orders;

import GUI.customers.Customer;

public class Order {
    private int orderID;
    private int cartID;
    private String address;
    private boolean isShipped;
    private int customerID;
    private Customer customer;

    public Order(int orderID, int cartID, String address, boolean isShipped, int customerID) {
        this.orderID = orderID;
        this.cartID = cartID;
        this.address = address;
        this.isShipped = isShipped;
        this.customerID = customerID;
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isShipped() {
        return isShipped;
    }

    public void setShipped(boolean shipped) {
        isShipped = shipped;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
