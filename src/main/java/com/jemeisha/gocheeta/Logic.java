package com.jemeisha.gocheeta;

import com.jemeisha.gocheeta.database.DBUtil;
import com.jemeisha.gocheeta.errors.DistanceNotFound;
import com.jemeisha.gocheeta.errors.NoDriversAvailable;
import com.jemeisha.gocheeta.errors.OrderAlreadyExist;
import com.jemeisha.gocheeta.errors.OrderCannotBeFound;
import com.jemeisha.gocheeta.pojo.Customer;
import com.jemeisha.gocheeta.pojo.Driver;
import com.jemeisha.gocheeta.pojo.Order;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebService
public class Logic {
    @WebMethod
//    @WebResult(targetNamespace = "go_cheeta")
    public String sayHello(){
        return "Hello";
    }
    @WebMethod
    @WebResult(targetNamespace = "go_cheeta")
    public Customer register(String username, String password, String cusFirstName, String cusLastName, String cusMobNo) throws NoSuchAlgorithmException {
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

    public static String loginDriver(int driverId, String password) throws NoSuchAlgorithmException {

        DBUtil db = DBUtil.getSingletonInstance();
        Driver driver = db.getDriverById(driverId);

        if (driver != null) {
            String newPassword = Util.hashMD5(password);
            if (newPassword.equals(driver.getPassword())) {

                return "JWT";
            } else {
                return null;
            }
        } else {

            return null;

        }
    }

    public static String loginAdmin(String username, String password) {

        final String ADMIN_USERNAME = "admin";
        final String ADMIN_PASSWORD = "pass";

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {

            return "JWT";
        } else {
            return null;
        }

    }

    public static Order bookARide(String username, int startingLocation, int endingLocation) throws SQLException, ClassNotFoundException, OrderAlreadyExist, NoDriversAvailable, DistanceNotFound {

        DBUtil db = DBUtil.getSingletonInstance();

        ArrayList<Order> ongoingOrders = db.getOrdersByUsername(username, true);
        if (ongoingOrders.size() > 0) {
            //customer has ongoing order, decline order
            throw new OrderAlreadyExist();
        } else {
            ArrayList<Driver> availableDrivers = db.getAvailableDrivers(startingLocation);
            if (availableDrivers.size() > 0) {
                double distance = db.getDistance(startingLocation, endingLocation);
                if (distance < 0) {
                    throw new DistanceNotFound();
                }
                double total = Util.calculatePrice(distance);
                Driver driver = availableDrivers.get(0);
                String vehicleNo = db.getVehicleByDriverId(driver.getDriverId()).getVehicleNo();
                int orderID = db.createOrder(username, vehicleNo, driver.getDriverId(), startingLocation, endingLocation, total, 0);

                Order order = new Order();
                order.setOrderID(orderID);
                order.setUsername(username);
                order.setVehicleNo(vehicleNo);
                order.setDriverID(driver.getDriverId());
                order.setPickup(startingLocation);
                order.setDestination(endingLocation);
                order.setTotal(total);
                order.setBookingState(0);

                return order;
            } else {
                throw new NoDriversAvailable();

                // no drivers available, decline order
            }
        }


    }

    public static boolean changeOrderStatus(int orderId, int state) throws SQLException, ClassNotFoundException, OrderCannotBeFound {
        DBUtil db = DBUtil.getSingletonInstance();
        Order order = db.getOrderByOrderId(orderId);

        if (order != null) {
            return db.updateOrderStatusById(orderId, state);

        } else {
            //order does not exist.Throw error
            throw new OrderCannotBeFound();

        }


    }

}
