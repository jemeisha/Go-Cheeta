package com.jemeisha.gocheeta;

import com.jemeisha.gocheeta.database.DBUtil;
import com.jemeisha.gocheeta.pojo.Customer;

import java.security.NoSuchAlgorithmException;

public class Logic {

    public static Customer register (String username,String password,String cusFirstName, String cusLastName,String cusMobNo) throws NoSuchAlgorithmException {
        String md5Password= Util.hashMD5(password);
        Customer customer= new Customer();
        customer.setUsername(username);
        customer.setPassword(md5Password);
        customer.setCusFistName(cusFirstName);
        customer.setCusLastName(cusLastName);
        customer.setCusMobNo(cusMobNo);


       DBUtil db=  DBUtil.getSingletonInstance();

       db.createCustomer(username,md5Password,cusFirstName,cusLastName,cusMobNo);

        return customer;

    }

    public static String login (String username, String password) throws NoSuchAlgorithmException {

        DBUtil db=  DBUtil.getSingletonInstance();
        Customer customer= db.getCustomerByUsername(username);

        if (customer!= null){
            String newPassword= Util.hashMD5(password);
            if (newPassword.equals(customer.getPassword())){

                return "JWT";
            }else{
                return null;


            }


        }else{

          return null;

        }


    }
}
