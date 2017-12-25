package com.northmeter.prepaymentmanage.model;

import java.io.Serializable;

/**
  *
  *@author lht
  *@time   2016/11/4 9:49
  *@des  电表监控信息
  */

public class Equipment implements Serializable{
    //线路名称
    private String routeName;
    //余额
    private String balance;
    //电表状态
    private String electricState;
    //通讯状态
    private String communicationStatus;

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getElectricState() {
        return electricState;
    }

    public void setElectricState(String electricState) {
        this.electricState = electricState;
    }

    public String getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(String communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public Equipment() {
    }

    public Equipment(String routeName, String balance, String electricState, String communicationStatus) {

        this.routeName = routeName;
        this.balance = balance;
        this.electricState = electricState;
        this.communicationStatus = communicationStatus;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "routeName='" + routeName + '\'' +
                ", balance='" + balance + '\'' +
                ", electricState='" + electricState + '\'' +
                ", communicationStatus='" + communicationStatus + '\'' +
                '}';
    }
}

