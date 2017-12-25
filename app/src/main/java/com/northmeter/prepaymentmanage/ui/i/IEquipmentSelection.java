package com.northmeter.prepaymentmanage.ui.i;

import com.northmeter.prepaymentmanage.model.EquipmentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public interface IEquipmentSelection {
    /**
     * @param count
     * @param accuracy
     * @param normal
     * @param responsexmlBeen
     */
    void  showNewData(int refreshType, String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen);

   // void showMoreData(String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen);

    /**
     * 停止刷新
     */
    void stopRefresh();



    /**
     * 获取上次加载时间
     */
    long getLastRefreshTime();

    void startRefresh();

    void openRight();
}
