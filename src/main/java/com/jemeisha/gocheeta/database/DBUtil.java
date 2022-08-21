package com.jemeisha.gocheeta.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DBUtil {
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/go_cheeta";
    static final String USER = "root";
    static final String PASS = "example";


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
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `customer` VALUES (?,?,?,?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.setString(3,cusFirstName);
            ps.setString(4,cusLastName);
            ps.setString(5,cusMobNo);
            rowsAffected = ps.executeUpdate();
        } catch(Exception e) {
            System.out.println(e);
        }
        return rowsAffected > 0;


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
