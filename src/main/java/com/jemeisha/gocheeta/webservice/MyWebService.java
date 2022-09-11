package com.jemeisha.gocheeta.webservice;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public class MyWebService {
    @WebMethod
    @WebResult(targetNamespace = "go_cheeta")
    public String sayHello(){
        return "Hello";
    }
}
