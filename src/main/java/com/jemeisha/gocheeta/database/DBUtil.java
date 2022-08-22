package com.jemeisha.gocheeta.database;

import com.jemeisha.gocheeta.pojo.Branch;
import com.jemeisha.gocheeta.pojo.Customer;

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
                //TODO
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

    public List<Branch> getStudents() {
        List<Branch> branches = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM branch");
            while (resultSet.next()) {
                Branch branch = new Branch();
                branch.setBranchId(resultSet.getInt("branch_id"));
                branch.setBranchName(resultSet.getString("branch_name"));
                branch.setPhoneNo(resultSet.getString("phoneNo"));
                branches.add(branch);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return branches;
    }

    //getCustomerByUsername
    //createCustomer
    //getAllCustomers

    //getAllBranches
    //getBranchById

    //getDistance

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
