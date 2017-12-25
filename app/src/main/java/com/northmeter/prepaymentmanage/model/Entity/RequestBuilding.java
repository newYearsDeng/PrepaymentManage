package com.northmeter.prepaymentmanage.model.Entity;

/**
 * Created by Lht
 * on 2016/12/19.
 * des:
 */
public class RequestBuilding {

    private String Area_ID;
    private String Area_NAME;

    public String getArea_ID() {
        return Area_ID;
    }

    public void setArea_ID(String Area_ID) {
        this.Area_ID = Area_ID;
    }

    public String getArea_NAME() {
        return Area_NAME;
    }

    public void setArea_NAME(String Area_NAME) {
        this.Area_NAME = Area_NAME;
    }

    @Override
    public String toString() {
        return "RESPONSEXMLBean{" +
                "Area_ID='" + Area_ID + '\'' +
                ", Area_NAME='" + Area_NAME + '\'' +
                '}';
    }
}
