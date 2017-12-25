package com.northmeter.prepaymentmanage.ui.i;

import com.northmeter.prepaymentmanage.model.LinesDevicesBean;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/18 16:27
 * @des 在/离 线设备显示view的接口
 */
public interface ILinesEquipmentActivity {
    void showToast(String toastStr);
    void showDevices(List<LinesDevicesBean.RESPONSEXMLBean> responsexml);

    void showLoading();
    void hideLoading();
}
