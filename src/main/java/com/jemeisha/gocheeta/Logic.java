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
    public final double SAME_CITY_PRICE = 200;

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
//        System.out.println("driver test - "+driver);
        if (driver != null) {
            String newPassword = Util.hashMD5(password);
//            System.out.println("new test - "+newPassword);
//            System.out.println("pass test - "+driver.getPassword());

            if (newPassword.equals(driver.getPassword())) {
                System.out.println("1");
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
    public Order bookARide(String username, int startingLocation, int endingLocation) throws Exception {

        DBUtil db = DBUtil.getSingletonInstance();

        ArrayList<Order> ongoingOrders = db.getOrdersByUsername(username, true);
        if (ongoingOrders.size() > 0) {
            //customer has ongoing order, decline order
            throw new OrderAlreadyExist();
        } else {
            ArrayList<Driver> availableDrivers = db.getAvailableDrivers(startingLocation);
            if (availableDrivers.size() > 0) {
                double total = 0;
                if (startingLocation == endingLocation) {
                    total = SAME_CITY_PRICE;
                }else{
                    double distance = db.getDistance(startingLocation, endingLocation);
                    if (distance < 0) {
                        throw new DistanceNotFound();
                    }
                    total = Util.calculatePrice(distance);
                }

                Driver driver = availableDrivers.get(0);
                String vehicleNo = driver.getVehicleNo();
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

    @WebMethod
    public boolean changeOrderStatus(int orderId, int state) throws Exception {
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
            DBUtil db = DBUtil.getSingletonInstance();
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
        for (int x = 0; x < drivers.size(); x++) {
            Driver d = drivers.get(x);
            d.loadVehicle();
            d.getVehicle().loadCategory();
        }
        return drivers.toArray(new Driver[0]);
    }

    @WebMethod
    public Driver[] getAllDriversWithVehicles() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Driver> drivers = db.getAllDrivers();
        for (int x = 0; x < drivers.size(); x++) {

            Driver d = drivers.get(x);
            d.loadVehicle();
            d.getVehicle().loadCategory();
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
        for (int x = 0; x < orders.size(); x++) {
            Order o = orders.get(x);
            o.loadDriver();
            o.loadBranches();
            o.getDriver().loadVehicle();
            o.getDriver().getVehicle().loadCategory();
        }
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

    @WebMethod
    public Order getCustomerOngoingBooking(String username) {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Order> os = db.getOrdersByUsername(username, true);
        if (os.size() == 0) {
            return null;
        }
        Order o = os.get(0);
        o.loadDriver();
        o.loadBranches();
        o.getDriver().loadVehicle();
        o.getDriver().getVehicle().loadCategory();
        return o;
    }

    @WebMethod
    public Order[] getCustomerOrderHistory(String username) {
        DBUtil db = DBUtil.getSingletonInstance();
        Order[] os = db.getOrdersByUsername(username, false).toArray(new Order[0]);
        ArrayList<Order> filtered = new ArrayList<>();
        for (int x = 0; x < os.length; x++) {
            Order o = os[x];
            if (o.getBookingState() == 3) {
                o.loadDriver();
                o.loadBranches();
                o.getDriver().loadVehicle();
                o.getDriver().getVehicle().loadCategory();
                filtered.add(o);
            }
        }
        return filtered.toArray(new Order[0]);
    }

    @WebMethod
    public Branch getBranchById(int branchId) {
        DBUtil db = DBUtil.getSingletonInstance();
        return db.getBranchById(branchId);
    }

    @WebMethod
    public Order getDriverOngoingBooking(String driverId) {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Order> os = db.getOrdersByDriverId(Integer.parseInt(driverId), true);
        if (os.size() == 0) {
            return null;
        }
        Order o = os.get(0);
        o.loadDriver();
        o.loadBranches();
        o.loadCustomer();
        o.getDriver().loadVehicle();
        o.getDriver().getVehicle().loadCategory();
        return o;
    }

    @WebMethod
    public Order[] getDriverOrderHistory(String driverId) {
        DBUtil db = DBUtil.getSingletonInstance();
        Order[] os = db.getOrdersByDriverId(Integer.parseInt(driverId), false).toArray(new Order[0]);
        ArrayList<Order> filtered = new ArrayList<>();
        for (int x = 0; x < os.length; x++) {
            Order o = os[x];
            if (o.getBookingState() == 3) {
                o.loadDriver();
                o.loadBranches();
                o.loadCustomer();
                o.getDriver().loadVehicle();
                o.getDriver().getVehicle().loadCategory();
                filtered.add(o);
            }
        }
        return filtered.toArray(new Order[0]);
    }


    @WebMethod
    public Driver getLoggedInDriver(String jwt) {
        DBUtil db = DBUtil.getSingletonInstance();
//        return db.getDriverById(1);
        DecodedJWT decodedJWT = Util.verifyToken(jwt, DRIVER_AUDIENCE);
        if (decodedJWT != null) {
            return db.getDriverById(Integer.parseInt(decodedJWT.getSubject()));
        } else {
            return null;
        }
        //return decodedJWT!=null; --------samething as if

    }

    @WebMethod
    public double getTotalSalesInfo() {
        return DBUtil.getSingletonInstance().getTotalSales();
    }

    @WebMethod
    public SalesInfo[] getTotalSalesByBranch() {
        DBUtil db = DBUtil.getSingletonInstance();
        ArrayList<Branch> branches = db.getBranches();
        ArrayList<SalesInfo> branchSales = new ArrayList<>();
        for (int x = 0; x < branches.size(); x++) {
            Branch b = branches.get(x);
            SalesInfo si = new SalesInfo();
            si.setBranchName(b.getBranchName());
            si.setSales(db.getSalesByBranch(b.getBranchId()));
            branchSales.add(si);
        }
        return branchSales.toArray(new SalesInfo[0]);
    }

    @WebMethod
    public Vehicle registerDriver(String fname, String lname, String nic, String mobno, String pass, String branchId, String vno, String vCatId, String noseats, String colour) throws NoSuchAlgorithmException {

        String md5Password = Util.hashMD5(pass);

        DBUtil db = DBUtil.getSingletonInstance();

        Vehicle v = new Vehicle();
        Driver d = new Driver();

        d.setDriverFirstName(fname);
        d.setDriverLastName(lname);
        d.setDriverNic(nic);
        d.setDriverMobile(mobno);
//        d.setPassword(pass);
        d.setBranchId(Integer.parseInt(branchId));
        d.setVehicleNo(vno);

        v.setVehicleType(Integer.parseInt(vCatId));
        v.setNoOfSeats(Integer.parseInt(noseats));
        v.setVehicleColour(colour);

        db.createVehicle(vno, vCatId, Integer.parseInt(noseats), colour);
        db.createDriver(md5Password, fname, lname, nic, mobno, branchId, vno);

        return v;

    }

    @WebMethod
    public Driver getDriverbyId(int driverId) {
        DBUtil db = DBUtil.getSingletonInstance();
        Driver d = db.getDriverById(driverId);
        d.loadVehicle();
        d.getVehicle().loadCategory();
        return d;
//        DecodedJWT decodedJWT = Util.verifyToken(jwt, DRIVER_AUDIENCE);
//        if (decodedJWT != null) {
//            DBUtil db = DBUtil.getSingletonInstance();
//            return db.getDriverById(decodedJWT.getSubject());
//        } else {
//            return null;
//        }
        //return decodedJWT!=null; --------samething as if
    }

    @WebMethod
    public boolean updateDriver(String oldVehicleNo, String driverId, String fname, String lname, String nic, String mobno, String pass, String branchId, String vno, String vCatId, String noseats, String colour) throws Exception {

        String md5Password = Util.hashMD5(pass);
        DBUtil db = DBUtil.getSingletonInstance();

        Vehicle v = new Vehicle();
        Driver d = new Driver();

        d.setDriverFirstName(fname);
        d.setDriverLastName(lname);
        d.setDriverNic(nic);
        d.setDriverMobile(mobno);
//        d.setPassword(pass);
        d.setBranchId(Integer.parseInt(branchId));
        d.setVehicleNo(vno);

        v.setVehicleType(Integer.parseInt(vCatId));
        v.setNoOfSeats(Integer.parseInt(noseats));
        v.setVehicleColour(colour);

        boolean vehicleUpdated = db.updateVehicle(vno, Integer.parseInt(vCatId), Integer.parseInt(noseats), colour, oldVehicleNo);
        if (!vehicleUpdated) {
            throw new Exception("Unable to update vehicle");
        }
        boolean driverUpdated = db.updateDriver(md5Password, fname, lname, nic, mobno, vno, Integer.parseInt(driverId));
        if (!driverUpdated) {
            throw new Exception("Unable to update driver");
        }

        return true;
    }
    @WebMethod
    public double getTripPricing(int pickup, int dest) throws Exception{
        DBUtil db = DBUtil.getSingletonInstance();
        double total = 0;
        if(pickup == dest){
            total = SAME_CITY_PRICE;
        }else{
            double distance = db.getDistance(pickup, dest);
            if (distance < 0) {
                throw new DistanceNotFound();
            }
            total = Util.calculatePrice(distance);
        }
        return total;
    }
}
