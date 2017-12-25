package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/11 17:31
 * @des 水/电 表设备查询prensenter的接口
 */
public interface IDevicesQueryPresenter {
    void getData(String meterType, String comaddress);

    /**
     * 取消rx注册
     */
    void rxUnsubscribe();
}
