package com.jemeisha.gocheeta.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="BookingInformation")
public class BookingInfomation {
    private int totalBookings;
    private int ongoingBookings;

    public int getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(int totalBookings) {
        this.totalBookings = totalBookings;
    }

    public int getOngoingBookings() {
        return ongoingBookings;
    }

    public void setOngoingBookings(int ongoingBookings) {
        this.ongoingBookings = ongoingBookings;
    }
}
