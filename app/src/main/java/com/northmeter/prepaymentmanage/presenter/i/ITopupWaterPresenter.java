package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/15 10:27
 * @des 充值流水presenter的接口
 */
public interface ITopupWaterPresenter {
    void getChargeRecord(String comaddress, String year);

    void rxUnsubscribe();
}
