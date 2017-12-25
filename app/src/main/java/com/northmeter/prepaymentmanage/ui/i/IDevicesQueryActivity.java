package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/11 17:29
 * @des 水/电 表查询
 */
public interface IDevicesQueryActivity {
    /**
     * 数据显示
     * @param yffye
     * @param bzye
     * @param zye
     * @param zyl
     * @param bzt
     * @param updateTime
     * @param yffye  (lht 2016.11.15)
     */
    void showData(String yffye,String bzye, String zye, String zyl, String bzt, String updateTime);

    void showToast(String toastStr);

    void showLoading();
    void hideLoading();

    void showMeterState(int state ,String msg);

    void getStateTimeout();

}
