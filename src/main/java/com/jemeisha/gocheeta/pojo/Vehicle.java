package com.jemeisha.gocheeta.pojo;

import com.jemeisha.gocheeta.database.DBUtil;

public class Vehicle {

    private String vehicleNo;
//    private int driverId;
    private int vehicleType;
    private int noOfSeats;
    private String vehicleColour;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
//    public int getDriverId() {
//        return driverId;
//    }

//    public void setDriverId(int driverId) {
//        this.driverId = driverId;
//    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getVehicleColour() {
        return vehicleColour;
    }

    public void setVehicleColour(String vehicleColour) {
        this.vehicleColour = vehicleColour;
    }

    public void loadCategory(){
        this.category = DBUtil.getSingletonInstance().getCategoryById(this.vehicleType);
    }
}
