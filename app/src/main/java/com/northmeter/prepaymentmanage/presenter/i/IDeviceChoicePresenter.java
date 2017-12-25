package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/14 14:13
 * @des 设备选择presenter的接口
 */
public interface IDeviceChoicePresenter {
    /**
     * 得到用户所拥有的的表计信息
     */
    void getMeters();

    void lookChum();

    void rxUnsubscribe();
}
