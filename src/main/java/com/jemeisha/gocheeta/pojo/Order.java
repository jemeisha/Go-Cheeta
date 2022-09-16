package com.jemeisha.gocheeta.pojo;

import com.jemeisha.gocheeta.database.DBUtil;

public class Order {
    private int orderID;
    private String username;
    private String vehicleNo;
    private int driverID;
    private int pickup;
    private int destination;
    private double total;
    private int bookingState;
    private Driver driver;

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public int getPickup() {
        return pickup;
    }

    public void setPickup(int pickup) {
        this.pickup = pickup;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getBookingState() {
        return bookingState;
    }

    public void setBookingState(int bookingState) {
        this.bookingState = bookingState;
    }

    public void loadDriver(){
        DBUtil db= DBUtil.getSingletonInstance();
        this.driver = db.getDriverById(this.driverID);
    }
    public void loadCustomer(){
        DBUtil db= DBUtil.getSingletonInstance();
        this.customer = db.getCustomerByUsername(this.username);
    }
}
