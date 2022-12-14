package com.jemeisha.gocheeta.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Category")
public class Category {

    private int categoryId;
    private String name;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
