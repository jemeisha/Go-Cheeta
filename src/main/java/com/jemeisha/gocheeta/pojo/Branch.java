package com.jemeisha.gocheeta.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Branch")
public class Branch {
    private int branchId;
    private String branchName;
    private String phoneNo;

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
