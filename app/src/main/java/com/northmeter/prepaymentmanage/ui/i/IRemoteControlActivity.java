package com.northmeter.prepaymentmanage.ui.i;

import com.northmeter.prepaymentmanage.model.EquipmentBean;

import java.util.List;

/**
 * Created by lht on 2016/11/24.
 * des:RemoteControlPresenter
 */
public interface IRemoteControlActivity {
    void showLoading();
    void hideLoading();
    void  openRight();

    void showArea(String AreaId,String Area,String loginName);
    void cancleSelctedMode();

    void showData(String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen);
}
