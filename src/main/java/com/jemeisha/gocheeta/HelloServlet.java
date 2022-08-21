package com.jemeisha.gocheeta;

import java.io.*;

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
       db.createCustomer("kithu","kithu123","Kithumini","Almeida","0765625193");



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