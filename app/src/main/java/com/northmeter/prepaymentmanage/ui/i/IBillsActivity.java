package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/14 17:35
 * @des 水/电 账单view的接口
 */
public interface IBillsActivity {
    void showToast(String toastStr);

    void showDataToTextView(String qcbd, String qmbd, String jsdata, String jsmoney);

    void showEmptyData();

    void showLoading();
    void hideLoading();
}
