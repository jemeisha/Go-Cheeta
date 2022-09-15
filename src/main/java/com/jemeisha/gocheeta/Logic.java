package com.jemeisha.gocheeta;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.jemeisha.gocheeta.database.DBUtil;
import com.jemeisha.gocheeta.errors.DistanceNotFound;
import com.jemeisha.gocheeta.errors.NoDriversAvailable;
import com.jemeisha.gocheeta.errors.OrderAlreadyExist;
import com.jemeisha.gocheeta.errors.OrderCannotBeFound;
import com.jemeisha.gocheeta.pojo.*;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebService
public class Logic {

    public final String CUSTOMER_AUDIENCE = "customer";
    public final String ADMIN_AUDIENCE = "admin";
    public final String DRIVER_AUDIENCE = "driver";

    @WebMethod
//    @WebResult(targetNamespace = "go_cheeta")
    public String sayHello() {
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

    @WebMethod
    public String login(String username, String password) throws NoSuchAlgorithmException {

        DBUtil db = DBUtil.getSingletonInstance();
        Customer customer = db.getCustomerByUsername(username);

        if (customer != null) {
            String newPassword = Util.hashMD5(password);
            if (newPassword.equals(customer.getPassword())) {
                String jwt = Util.signJWT(username, CUSTOMER_AUDIENCE);
                return jwt;
            } else {
                return null;
            }
        } else {

            return null;

        }
    }

    @WebMethod
    public String loginDriver(String driverId, String password) throws NoSuchAlgorithmException {

        DBUtil db = DBUtil.getSingletonInstance();
        int dId = Integer.parseInt(driverId);
        Driver driver = db.getDriverById(dId);

        if (driver != null) {
            String newPassword = Util.hashMD5(password);
            if (newPassword.equals(driver.getPassword())) {
                String jwt = Util.signJWT(driverId, DRIVER_AUDIENCE);
                return jwt;
            } else {
                return null;
            }
        } else {

            return null;

        }
    }

    @WebMethod
    public String loginAdmin(String username, String password) {

        final String ADMIN_USERNAME = "admin";
        final String ADMIN_PASSWORD = "pass";

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {

            String jwt = Util.signJWT(username, ADMIN_AUDIENCE);
            return jwt;
        } else {
            return null;
        }

    }

    @WebMethod
    public Order bookARide(String username, int startingLocation, int endingLocation) throws Exception{

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

    @WebMethod
    public boolean isLoggedIn(String jwt) {

        DecodedJWT decodedJWT = Util.verifyToken(jwt, CUSTOMER_AUDIENCE);
        if (decodedJWT != null) {
            return true;
        } else {
            return true;
        }
        //return decodedJWT!=null; --------samething as if

    }

    @WebMethod
    public Customer getLoggedInUser(String jwt) {

        DecodedJWT decodedJWT = Util.verifyToken(jwt, CUSTOMER_AUDIENCE);
        if (decodedJWT != null) {
            DBUtil db=DBUtil.getSingletonInstance();
            return db.getCustomerByUsername(decodedJWT.getSubject());
        } else {
            return null;
        }
        //return decodedJWT!=null; --------samething as if

    }

    @WebMethod
    public boolean isDriverLoggedIn(String jwt) {

        DecodedJWT decodedJWT = Util.verifyToken(jwt, DRIVER_AUDIENCE);
        if (decodedJWT != null) {
            return true;
        } else {
            return true;
        }
        //return decodedJWT!=null; --------samething as if

    }

    @WebMethod
    public boolean isAdminLoggedIn(String jwt) {

        DecodedJWT decodedJWT = Util.verifyToken(jwt, ADMIN_AUDIENCE);
        if (decodedJWT != null) {
            return true;
        } else {
            return true;
        }
        //return decodedJWT!=null; --------samething as if

    }

    @WebMethod
    public Driver[] getAllDrivers() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Driver> drivers = db.getAllDrivers();
        return drivers.toArray(new Driver[0]);
    }

    @WebMethod
    public Driver[] getAllDriversWithVehicles() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Driver> drivers = db.getAllDrivers();
        for (int x = 0; x < drivers.size(); x++) {

            Driver d = drivers.get(x);
            d.loadVehicle();
        }
        return drivers.toArray(new Driver[0]);
    }

    @WebMethod
    public Customer[] getAllCustomers() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Customer> customers = db.getAllCustomers();
        return customers.toArray(new Customer[0]);
    }

    @WebMethod
    public BookingInfomation getBookingInfomation() {
        BookingInfomation bookingInfomation = new BookingInfomation();
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Order> ongoingBookings = db.getAllOrders(true);
        ArrayList<Order> totalBookings = db.getAllOrders(false);

        bookingInfomation.setOngoingBookings(ongoingBookings.size());
        bookingInfomation.setTotalBookings(totalBookings.size());

        return bookingInfomation;

    }

    @WebMethod
    public Order[] getAllOrders() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Order> orders = db.getAllOrders(false);
        return orders.toArray(new Order[0]);
    }

    @WebMethod
    public Branch[] getAllBranches() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Branch> branches = db.getAllBranches();
        return branches.toArray(new Branch[0]);
    }

    @WebMethod
    public Category[] getAllVehicleCategories() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Category> categories = db.getAllCategories();
        return categories.toArray(new Category[0]);
    }
}
