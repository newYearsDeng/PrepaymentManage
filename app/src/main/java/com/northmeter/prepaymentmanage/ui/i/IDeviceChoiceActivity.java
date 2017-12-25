package com.northmeter.prepaymentmanage.ui.i;

import com.northmeter.prepaymentmanage.model.LookChumBean;
import com.northmeter.prepaymentmanage.model.UserMeterBean;

import java.util.List;

/**
 * @author zz
 * @time 2016/11/16 16:04
 * @des 设备选择View的接口
 */
public interface IDeviceChoiceActivity {
    void showToast(String toastStr);
    void showDevices(List<UserMeterBean.RESPONSEXMLBean> responsexml);

    void showLookChum(List<LookChumBean.RESPONSEXMLBean> responsexmlBeen);

    void setClickable(boolean clickable);
    void showLoading();
    void hideLoading();
}
