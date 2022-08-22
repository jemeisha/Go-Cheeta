package com.jemeisha.gocheeta;

import java.io.*;
import java.util.ArrayList;

import com.jemeisha.gocheeta.pojo.Customer;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.jemeisha.gocheeta.database.DBUtil;

@WebServlet(name = "helloServlet", value = "/hello-world")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       DBUtil db=new DBUtil();
       //db.createCustomer("kithu","kithu123","Kithumini","Almeida","0765625193");
//        Customer customer= db.getCustomerByUsername("kithu");
//        System.out.println("username_ "+customer.getUsername());
        ArrayList<Customer> customerList =db.getAllCustomers();
        System.out.println("Length: "+customerList.size());

        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}