package com.northmeter.prepaymentmanage.ui.i;

import com.northmeter.prepaymentmanage.model.TopUp;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/15 10:24
 * @des 充值流水View的接口
 */
public interface ITopupWater {
    void showToast(String toastStr);

    void showDataToLv(List<TopUp.RESPONSEXMLBean> responsexml);

    void showLoading();
    void hideLoading();
}
