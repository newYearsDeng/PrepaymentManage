package com.northmeter.prepaymentmanage.ui.air_conditioner.i;

/**
 * Created by dyd on 2018/2/1.
 */
public interface IAirConditionerControlPresenter {
    void sendControlData(String comaddress, String operatortype);
    void getData(String meterType, String comaddress);
    /**
     * 取消rx注册
     */
    void rxUnsubscribe();
}

