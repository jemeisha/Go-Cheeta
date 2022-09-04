package com.jemeisha.gocheeta;

import com.jemeisha.gocheeta.database.DBUtil;
import com.jemeisha.gocheeta.pojo.Customer;
import com.jemeisha.gocheeta.pojo.Driver;
import com.jemeisha.gocheeta.pojo.Order;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Logic {

    public static Customer register(String username, String password, String cusFirstName, String cusLastName, String cusMobNo) throws NoSuchAlgorithmException {
        String md5Password = Util.hashMD5(password);
        Customer customer = new Customer();
        customer.setUsername(username);
        customer.setPassword(md5Password);
        customer.setCusFistName(cusFirstName);
        customer.setCusLastName(cusLastName);
        customer.setCusMobNo(cusMobNo);


        DBUtil db = DBUtil.getSingletonInstance();

        db.createCustomer(username, md5Password, cusFirstName, cusLastName, cusMobNo);

        return customer;

    }

    public static String login(String username, String password) throws NoSuchAlgorithmException {

        DBUtil db = DBUtil.getSingletonInstance();
        Customer customer = db.getCustomerByUsername(username);

        if (customer != null) {
            String newPassword = Util.hashMD5(password);
            if (newPassword.equals(customer.getPassword())) {

                return "JWT";
            } else {
                return null;
            }
        } else {

            return null;

        }
    }

    public static void bookARide(String username, int startingLocation, int endingLocation) throws SQLException, ClassNotFoundException {

        DBUtil db = DBUtil.getSingletonInstance();

        ArrayList<Order> ongoingOrders = db.getOrdersByUsername(username, true);
        if (ongoingOrders.size() > 0) {
            //customer has ongoing order, decline order
        } else {
            ArrayList<Driver> availableDrivers = db.getAvailableDrivers(startingLocation);
            if (availableDrivers.size() > 0) {
                Driver driver = availableDrivers.get(0);
                String vehicleNo = db.getVehicleByDriverId(driver.getDriverId()).getVehicleNo();
                int orderID = db.createOrder(username, vehicleNo, driver.getDriverId(), startingLocation, endingLocation, 1000, 0);

            Order order= new Order();
            order.setOrderID(orderID);
            order.setUsername(username);
            order.setVehicleNo(vehicleNo);
            order.setDriverID(driver.getDriverId());
            order.setPickup(startingLocation);
            order.setDestination(endingLocation);
            order.setTotal(1000);
            order.setBookingState(0);
            } else {

                // no drivers available, decline order
            }
        }


    }
}
