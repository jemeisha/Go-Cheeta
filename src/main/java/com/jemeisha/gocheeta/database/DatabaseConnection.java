package com.jemeisha.gocheeta.database;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.*;

@WebListener
public class DatabaseConnection implements ServletContextListener {

    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/go_cheeta";
    static final String USER = "root";
    static final String PASS = "example";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("database connection");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement statement= conn.createStatement();
             //test
            ResultSet resultSet= statement.executeQuery("SELECT * FROM branch");

            resultSet.next();

            System.out.println(resultSet.getInt("branch_id"));
            System.out.println(resultSet.getString("branch_name"));
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
