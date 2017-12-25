package com.northmeter.prepaymentmanage.ui.GateLock.model;

/**
 * Created by dyd on 2017/6/27.
 */
public class OpenDoor_Model {

    public String lockName;
    public String state;

    public String getLockName() {
        return lockName;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
