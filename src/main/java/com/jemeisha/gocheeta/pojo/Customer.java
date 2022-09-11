package com.jemeisha.gocheeta.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Customer")
public class Customer {
    private String username;
    private String password;
    private String cusFistName;
    private String cusLastName;
    private String cusMobNo;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCusFistName() {
        return cusFistName;
    }

    public void setCusFistName(String cusFistName) {
        this.cusFistName = cusFistName;
    }

    public String getCusLastName() {
        return cusLastName;
    }

    public void setCusLastName(String cusLastName) {
        this.cusLastName = cusLastName;
    }

    public String getCusMobNo() {
        return cusMobNo;
    }

    public void setCusMobNo(String cusMobNo) {
        this.cusMobNo = cusMobNo;
    }
}
