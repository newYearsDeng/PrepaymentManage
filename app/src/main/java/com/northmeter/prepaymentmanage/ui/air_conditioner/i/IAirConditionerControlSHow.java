package com.northmeter.prepaymentmanage.ui.air_conditioner.i;

/**
 * Created by dyd on 2018/2/1.
 */
public interface IAirConditionerControlSHow {

    void showData(String yffye, String bzye, String zye, String zyl, String bzt, String updateTime);

    void showToast(String toastStr);

    void showLoading();

    void hideLoading();

    void showMeterState(int state, String msg);

    void getStateTimeout();
}
