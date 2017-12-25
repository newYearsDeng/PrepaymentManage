package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/14 14:16
 * @des 普通用户view的接口
 */
public interface IHomeUserActivity {
    void showDialog(String[] comaddressArr, String[] meterTypeArr);

    void showToast(String toastStr);
}
