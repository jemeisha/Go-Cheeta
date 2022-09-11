package com.jemeisha.gocheeta.database;

import com.jemeisha.gocheeta.pojo.*;
import com.jemeisha.gocheeta.pojo.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/go_cheeta";
    static final String USER = "root";
    static final String PASS = "example";
    static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    private static DBUtil SINGLETON_INSTANCE= new DBUtil();
    private DBUtil(){

    }

    public static DBUtil getSingletonInstance(){

        return SINGLETON_INSTANCE;
    }


    /*Customer Functions-------------------*/

    public boolean createCustomer(
            String username,
            String password,
            String cusFirstName,
            String cusLastName,
            String cusMobNo


    ) {
        int rowsAffected = 0;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `customer` VALUES (?,?,?,?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, cusFirstName);
            ps.setString(4, cusLastName);
            ps.setString(5, cusMobNo);
            rowsAffected = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rowsAffected > 0;


    }

    public Customer getCustomerByUsername(String username) {

        Customer customer = new Customer();
        boolean customerFound = false;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `customer` WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            customerFound = rs.next();
            if (customerFound) {
                customer.setUsername(rs.getString("username"));
                customer.setPassword(rs.getString("password"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (customerFound) {
            return customer;
        } else {
            return null;

        }


    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customerList = new ArrayList<>();
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {
                Customer customer = new Customer();

                customer.setCusFistName(resultSet.getString("username"));
                customer.setCusLastName(resultSet.getString("password"));
                customer.setCusFistName(resultSet.getString("first_name"));
                customer.setCusLastName(resultSet.getString("last_name"));
                customer.setCusMobNo(resultSet.getString("mobile_no"));
                customerList.add(customer);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return customerList;
    }

    public ArrayList<Branch> getBranches() {
        ArrayList<Branch> branchList = new ArrayList<>();
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM branch");
            while (resultSet.next()) {
                Branch branch = new Branch();
                branch.setBranchId(resultSet.getInt("branch_id"));
                branch.setBranchName(resultSet.getString("branch_name"));
                branch.setPhoneNo(resultSet.getString("phoneNo"));
                branchList.add(branch);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return branchList;
    }

    public Driver getDriverById(int driverId) {

        Driver driver = new Driver();
        boolean driverFound = false;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `driver` WHERE driver_id=?");
            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            driverFound = rs.next();
            if (driverFound) {
                driver.setDriverId(rs.getInt("driver_id"));
                driver.setPassword(rs.getString("password"));
                driver.setDriverFirstName(rs.getString("first_name"));
                driver.setDriverLastName(rs.getString("last_name"));
                driver.setDriverNic(rs.getString("NIC"));
                driver.setDriverMobile(rs.getString("mobile"));
                driver.setBranchId(rs.getInt("branch_id"));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (driverFound) {
            return driver;
        } else {
            return null;

        }

    }

    public ArrayList<Driver> getAllDrivers() {
        ArrayList<Driver> driverList = new ArrayList<>();
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM driver");
            while (resultSet.next()) {
                Driver driver = new Driver();

                driver.setDriverId(resultSet.getInt("driver_id"));
                driver.setPassword(resultSet.getString("password"));
                driver.setDriverFirstName(resultSet.getString("first_name"));
                driver.setDriverLastName(resultSet.getString("last_name"));
                driver.setDriverNic(resultSet.getString("NIC"));
                driver.setDriverMobile(resultSet.getString("mobile"));
                driver.setBranchId(resultSet.getInt("branch_id"));
                driverList.add(driver);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return driverList;
    }

    public ArrayList<Driver> getAvailableDrivers(int branchId){
        ArrayList<Driver> driverList = new ArrayList<>();
        int rowsAffected = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM driver WHERE branch_id=? AND driver_id NOT IN (SELECT driver_id FROM `order` WHERE booking_state <=? )");
            ps.setInt(1, branchId);
            ps.setInt(2, 2);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Driver driver = new Driver();

                driver.setDriverId(resultSet.getInt("driver_id"));
                driver.setPassword(resultSet.getString("password"));
                driver.setDriverFirstName(resultSet.getString("first_name"));
                driver.setDriverLastName(resultSet.getString("last_name"));
                driver.setDriverNic(resultSet.getString("NIC"));
                driver.setDriverMobile(resultSet.getString("mobile"));
                driver.setBranchId(resultSet.getInt("branch_id"));
                driverList.add(driver);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return driverList;

    }
    public Vehicle getVehicleById(String vehicleId) {

        Vehicle vehicle = new Vehicle();
        boolean vehicleFound = false;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `vehicle` WHERE vehicle_no=?");
            ps.setString(1, vehicleId);
            ResultSet rs = ps.executeQuery();

            vehicleFound = rs.next();
            if (vehicleFound) {
                vehicle.setVehicleNo(rs.getString("vehicle_no"));
                vehicle.setDriverId(rs.getInt("driver_id"));
                vehicle.setVehicleType(rs.getInt("vehicle_category"));
                vehicle.setNoOfSeats(rs.getInt("noOfSeats"));
                vehicle.setVehicleColour(rs.getString("colour"));

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (vehicleFound) {
            return vehicle;
        } else {
            return null;

        }

    }

    public ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM vehicle");
            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle();

                vehicle.setVehicleNo(resultSet.getString("vehicle_no"));
                vehicle.setDriverId(resultSet.getInt("driver_id"));
                vehicle.setVehicleType(resultSet.getInt("vehicle_category"));
                vehicle.setNoOfSeats(resultSet.getInt("noOfSeats"));
                vehicle.setVehicleColour(resultSet.getString("colour"));
                vehicleList.add(vehicle);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicleList;
    }


    public boolean createVehicle(
            String vehicleNo,
//          String driverId,
            String vehicleType,
            int noOfSeats,
            String vehicleColour

    ) {
        int rowsAffected = 0;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `vehicle` VALUES (?,?,?,?,?)");

            ps.setString(1, vehicleNo);
            //ps.setString(2, driverId);
            ps.setString(3, vehicleType);
            ps.setInt(4, noOfSeats);
            ps.setString(5, vehicleColour);
            rowsAffected = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return rowsAffected > 0;


    }

    public Vehicle getVehicleByDriverId(int driverId) {

        Vehicle vehicle = new Vehicle();
        boolean vehicleFound = false;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `vehicle` WHERE driver_id=?");
            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            vehicleFound = rs.next();
            if (vehicleFound) {
                vehicle.setVehicleNo(rs.getString("vehicle_no"));
                vehicle.setDriverId(rs.getInt("driver_id"));
                vehicle.setVehicleType(rs.getInt("vehicle_category"));
                vehicle.setNoOfSeats(rs.getInt("noOfSeats"));
                vehicle.setVehicleColour(rs.getString("colour"));

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (vehicleFound) {
            return vehicle;
        } else {
            return null;
        }
    }
    public ArrayList<Order> getOrdersByUsername(String username,boolean ongoing){
        ArrayList<Order> orderList = new ArrayList<>();
        int rowsAffected = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `order` WHERE username=? AND booking_state<=?");
            ps.setString(1, username);
            ps.setInt(2, ongoing?2:3);//ternary operator
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.setOrderID(resultSet.getInt("order_id"));
                order.setUsername(resultSet.getString("username"));
                order.setVehicleNo(resultSet.getString("vehicle_no"));
                order.setDriverID(resultSet.getInt("driver_id"));
                order.setPickup(resultSet.getInt("pickup"));
                order.setDestination(resultSet.getInt("destination"));
                order.setTotal(resultSet.getInt("total"));
                order.setBookingState(resultSet.getInt("booking_state"));

                orderList.add(order);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return orderList;

    }

    public int createOrder(
            String username,
            String vehicleNo,
            int driverID,
            int pickup,
            int destination,
            double total,
            int bookingState


    ) throws SQLException, ClassNotFoundException {
        int rowsAffected = 0;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `order`(`username`,`vehicle_no`,`driver_id`,`pickup`,`destination`,`total`,`booking_state`) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, vehicleNo);
            ps.setInt(3, driverID);
            ps.setInt(4, pickup);
            ps.setInt(5, destination);
            ps.setDouble(6, total);
            ps.setInt(7, bookingState);

            rowsAffected = ps.executeUpdate();
             //get the auto generated id
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return (generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public Order getOrderByOrderId(int orderId) {

        Order order= new Order();
        boolean orderFound = false;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `order` WHERE order_id=?");
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            orderFound = rs.next();
            if (orderFound) {
                order.setOrderID(rs.getInt("order_id"));
                order.setUsername(rs.getString("username"));
                order.setDriverID(rs.getInt("driver_id"));
                order.setPickup(rs.getInt("pickup"));
                order.setDestination(rs.getInt("destination"));
                order.setTotal(rs.getInt("total"));
                order.setBookingState(rs.getInt("booking_state"));

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        if (orderFound) {
            return order;
        } else {
            return null;
        }
    }

    public boolean updateOrderStatusById(int orderId,int status) throws SQLException, ClassNotFoundException {

        int rowsAffected = 0;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("UPDATE `order` SET booking_state=? WHERE order_id=?");

            ps.setInt(1, status);
            ps.setInt(2, orderId);


            rowsAffected = ps.executeUpdate();
            //get the auto generated id
          return rowsAffected>0;

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }



    }

    public ArrayList<Order> getOrdersByDriverId(int driverId,boolean ongoing){
        ArrayList<Order> orderList = new ArrayList<>();
        int rowsAffected = 0;
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `order` WHERE driver_id=? AND booking_state<=?");
            ps.setInt(1, driverId);
            ps.setInt(2, ongoing?2:3);//ternary operator
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                Order order = new Order();

                order.setOrderID(resultSet.getInt("order_id"));
                order.setUsername(resultSet.getString("username"));
                order.setVehicleNo(resultSet.getString("vehicle_no"));
                order.setDriverID(resultSet.getInt("driver_id"));
                order.setPickup(resultSet.getInt("pickup"));
                order.setDestination(resultSet.getInt("destination"));
                order.setTotal(resultSet.getInt("total"));
                order.setBookingState(resultSet.getInt("booking_state"));

                orderList.add(order);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return orderList;

    }

    public double getDistance(int locationOne,int locationTwo){

        if(locationTwo<locationOne){

            int temp= locationOne;
            locationOne=locationTwo;
            locationTwo=temp;
        }
        boolean distanceFound = false;

        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `distance` WHERE distance_one=? AND distance_two=?");
            ps.setInt(1, locationOne);
            ps.setInt(2, locationTwo);
            ResultSet rs = ps.executeQuery();

            distanceFound = rs.next();
            if (distanceFound) {
                return rs.getDouble("distance");

            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return -1; //distance not found


    }

    public int createCategory(
            String name

    ) throws SQLException, ClassNotFoundException {
        int rowsAffected = 0;
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `category`(`name`) VALUES (?)",Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, name);

            rowsAffected = ps.executeUpdate();
            //get the auto generated id
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return (generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categoryList = new ArrayList<>();
        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM category");
            while (resultSet.next()) {
                Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setName(resultSet.getString("name"));

                categoryList.add(category);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return categoryList;
    }

    public ArrayList<Vehicle> getVehiclesByCategory(int category) {
        ArrayList<Vehicle> vehicleList = new ArrayList<>();


        boolean vehicleFound = false;
        try {


            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM `vehicle` WHERE vehicle_category=?");

            ps.setInt(1,category);
            ResultSet resultSet = ps.executeQuery();


            while (resultSet.next()) {
                Vehicle vehicle = new Vehicle();

                vehicle.setVehicleNo(resultSet.getString("vehicle_no"));
                vehicle.setDriverId(resultSet.getInt("driver_id"));
                vehicle.setVehicleType(resultSet.getInt("vehicle_category"));
                vehicle.setNoOfSeats(resultSet.getInt("noOfSeats"));
                vehicle.setVehicleColour(resultSet.getString("colour"));
                vehicleList.add(vehicle);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicleList;
    }

    public double getTotalSales(){


        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT COALESCE(SUM(`total`),0) AS `sum` FROM `order` WHERE booking_state=3");

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("sum");

        } catch (Exception e) {
            System.out.println(e);
        }
        return -1; //distance not found


    }

    public double getSalesByBranch(int branchId){


        try {
            Class.forName(CLASS_NAME);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("SELECT COALESCE(SUM(`total`),0) AS `sum` FROM `order` WHERE pickup=? AND booking_state=3");
            ps.setInt(1, branchId);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getDouble("sum");

        } catch (Exception e) {
            System.out.println(e);
        }
        return -1; //distance not found


    }

    //getCustomerByUsername
    //createCustomer
    //getAllCustomers

    //getAllBranches
    //getBranchById

    //getDistance


    //getDriverById
    //getAllDrivers
    //getAvailableDrivers

    //getVehicleById
    //getAllVehicles
    //createVehicle
    //updateVehicleById-------------update driver id column.....
    //getVehicleByDriverId

    //createOrder-------------
    //updateOrderStatusById-----------------
    //getOrdersByUsername
    //getOrdersByDriverId--------------.......

    //-------Admin---------
     //createCategory
     //getAllCategories
     //getVehiclesByCategory
     //getSalesByBranch
     //getTotalSales
     //getAllOrders---------..........


    //
}
