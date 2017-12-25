package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/18 16:28
 * @des 在/离 线设备显示的presenter
 */
public interface ILinesEquipmentPresenter {
    void getDevicesInfo(String linesStr);
    void rxUnsubscribe();
}
