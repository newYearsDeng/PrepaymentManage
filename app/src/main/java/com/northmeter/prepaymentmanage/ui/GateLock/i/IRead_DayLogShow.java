package com.northmeter.prepaymentmanage.ui.GateLock.i;


import com.northmeter.prepaymentmanage.ui.GateLock.model.ReadDayLogBean;

import java.util.List;

/**
 * Created by dyd on 2017/7/1.
 */
public interface IRead_DayLogShow {

    void showData(int xRefreshType,List<ReadDayLogBean.RESPONSEXMLBean> datas);
    void stopRefresh();
    void showinitialdata(String startData,String postion,String area,String building);
}
