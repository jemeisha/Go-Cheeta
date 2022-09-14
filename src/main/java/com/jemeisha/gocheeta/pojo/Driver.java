package com.jemeisha.gocheeta.pojo;

import com.jemeisha.gocheeta.database.DBUtil;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Driver")
public class Driver {

    private int driverId;
    @XmlTransient
    private String password;
    private String driverFirstName;
    private String driverLastName;
    private String driverNic;
    private String driverMobile;
    private int  branchId;
    private Vehicle vehicle;

    private String vehicleNo;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getDriverNic() {
        return driverNic;
    }

    public void setDriverNic(String driverNic) {
        this.driverNic = driverNic;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public void loadVehicle(){
        DBUtil db= DBUtil.getSingletonInstance();
        this.vehicle=db.getVehicleById(this.vehicleNo);
    }
}
