package com.northmeter.prepaymentmanage.ui.i;

import android.widget.BaseAdapter;

import com.northmeter.prepaymentmanage.model.BuildMeterUseData;

import java.util.List;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public interface IUseQueryListFragment {
    //返回请求数据
    void showDayDatas(int xRefreshType,List<BuildMeterUseData.RESPONSEXMLBean> meterDatas);
    //初始化变量



    //停止刷新
    void stopRefresh();

}
