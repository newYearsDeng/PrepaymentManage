package com.northmeter.prepaymentmanage.ui.GateLock.i;

/**
 * Created by dyd on 2017/6/30.
 */
public interface I_openDoorDetail {
    void sendControlData(String comaddress,String operatortype);
    /**
     * 取消rx注册
     */
    void rxUnsubscribe();
}
