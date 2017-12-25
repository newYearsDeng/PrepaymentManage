package com.northmeter.prepaymentmanage.ui.i;

import android.widget.BaseAdapter;

import com.northmeter.prepaymentmanage.adapters.QueryAdapter;
import com.northmeter.prepaymentmanage.model.BuildMeterReadData;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public interface IQueryFragment {
    //返回请求数据
    void showDayDatas(int xRefreshType,List<?> meterDatas);
    //初始化变量
    void showinitialdata(String startData,String postion,String area,String building,String[] titles,BaseAdapter cAdpter);
   //初始化刷新控件
    void initXRefreshView();

   //停止刷新
    void stopRefresh();
   //选择建筑后，更改UI
    void setTvChooseTimeArea(String time,String area,String position,String building);
    //加载数据
    void LoadingData();

}
