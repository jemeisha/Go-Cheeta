//package com.jemeisha.gocheeta;
//
//import java.io.*;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import com.jemeisha.gocheeta.errors.DistanceNotFound;
//import com.jemeisha.gocheeta.errors.NoDriversAvailable;
//import com.jemeisha.gocheeta.errors.OrderAlreadyExist;
//import com.jemeisha.gocheeta.errors.OrderCannotBeFound;
//import com.jemeisha.gocheeta.pojo.Customer;
//import com.jemeisha.gocheeta.pojo.Driver;
//import com.jemeisha.gocheeta.pojo.Order;
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
//import com.jemeisha.gocheeta.database.DBUtil;
//
//@WebServlet(name = "helloServlet", value = "/hello-world")
//public class HelloServlet extends HttpServlet {
//    private String message;
//
//    public void init() {
//        message = "Hello World!";
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//       DBUtil db= DBUtil.getSingletonInstance();
//       //db.createCustomer("kithu","kithu123","Kithumini","Almeida","0765625193");
////        Customer customer= db.getCustomerByUsername("kithu");
////        System.out.println("username_ "+customer.getUsername());
////        ArrayList<Customer> customerList =db.getAllCustomers();
////        System.out.println("Length: "+customerList.size());
//
////        ArrayList<Driver> driverList =db.getAvailableDrivers(1);
////        System.out.println("Length: "+driverList.size());
//
////        try {
////            Logic.register("testUsername","testPassword","testFNAme","testLName","0762345183");
////        } catch (NoSuchAlgorithmException e) {
////          throw new RuntimeException(e);
////            System.out.println(e);
////        }
//
////        try {
////            String token= Logic.login("testUsernam","testPassword");
////            System.out.println("token "+ token);
////
////        } catch (NoSuchAlgorithmException e) {
//////            throw new RuntimeException(e);
////            System.out.println(e);
////        }
//
//        try {
//            int orderID= db.createOrder("helen","CAS3922",1,1,3,1000,1);
//            System.out.println("order id"+ orderID);
//        }catch (Exception e){
//            System.out.println(e);
//
//        }
//
////        try {
////            Order order=Logic.bookARide("helen",1,2);
////            Logic.changeOrderStatus(order.getOrderID(),2);
////            //System.out.println("token "+ token);
////
////
////        }
////        catch (SQLException e) {
////            throw new RuntimeException(e);
////        } catch (ClassNotFoundException e) {
////            throw new RuntimeException(e);
////        } catch (OrderAlreadyExist e) {
////            throw new RuntimeException(e);
////        } catch (NoDriversAvailable e) {
////            throw new RuntimeException(e);
////        } catch (OrderCannotBeFound e) {
////            throw new RuntimeException(e);
////        } catch (DistanceNotFound e) {
////            throw new RuntimeException(e);
////        }
//
//
////            ArrayList<Order> getOrdersByDriverId= db.getOrdersByDriverId(1,true);
////
////        System.out.println("length: "+getOrdersByDriverId.size());
////           // System.out.println(e);
//
//        response.setContentType("text/html");
//
//        // Hello
//        PrintWriter out = response.getWriter();
//        out.println("<html><body>");
//        out.println("<h1>" + message + "</h1>");
//        out.println("</body></html>");
//    }
//
//    public void destroy() {
//    }
//}