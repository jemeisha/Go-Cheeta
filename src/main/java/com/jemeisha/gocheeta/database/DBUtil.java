package com.jemeisha.gocheeta.database;

import com.jemeisha.gocheeta.pojo.Branch;
import com.jemeisha.gocheeta.pojo.Customer;
import com.jemeisha.gocheeta.pojo.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/go_cheeta";
    static final String USER = "root";
    static final String PASS = "example";
    static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";


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

    public ArrayList<Branch> getStudents() {
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
                driver.setDriverFirstName(rs.getString("first_name"));
                driver.setDriverLastName(rs.getString("last_name"));
                driver.setDriverNic(rs.getString("NIC"));
                driver.setDriverMobile(rs.getString("mobile"));
                //branch id here and in Driver class
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
    //getCustomerByUsername
    //createCustomer
    //getAllCustomers

    //getAllBranches
    //getBranchById
//--------------TODO
    //getDistance
//------------------------

    //getDriverById
    //getAllDrivers
    //changeDriverStatus
    //assignDriverToOrder
    //getAvailableDrivers

    //getVehicleById
    //getAllVehicles
    //createVehicle
    //updateVehicleById

    //createOrder
    //updateOrderStatusById
    //cancelOrder
}
