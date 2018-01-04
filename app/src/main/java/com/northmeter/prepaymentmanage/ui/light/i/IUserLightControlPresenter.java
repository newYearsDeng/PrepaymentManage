package com.northmeter.prepaymentmanage.ui.light.i;

/**
 * Created by dyd on 2017/6/30.
 */
public interface IUserLightControlPresenter {
    void sendControlData(String comaddress, String operatortype);
    /**
     * 取消rx注册
     */
    void rxUnsubscribe();
}
