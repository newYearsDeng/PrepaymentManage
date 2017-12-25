package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/18 15:53
 * @des 采集设备view的接口
 */
public interface ICollectEquipmentActivity {
    void showToast(String toastStr);
    void showDevices(String sum, String onLineSUM);

    void showEmptyView();
    void showLoading();
    void hideLoading();
}
