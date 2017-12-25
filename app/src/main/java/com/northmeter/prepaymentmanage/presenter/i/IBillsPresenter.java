package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/14 17:38
 * @des 水/电 账单presenter的接口
 */
public interface IBillsPresenter {
    void getMonthBill(String comaddress, String yearAndMonth);

    void rxUnsubscribe();
}
