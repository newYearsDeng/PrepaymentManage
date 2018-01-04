package com.northmeter.prepaymentmanage.ui.light.i;

/**
 * Created by dyd on 2017/6/30.
 */
public interface IUserLightControlShow {

    void showData(String yffye, String bzye, String zye, String zyl, String bzt, String updateTime);

    void showToast(String toastStr);

    void showLoading();

    void hideLoading();

    void showMeterState(int state, String msg);

    void getStateTimeout();
}
