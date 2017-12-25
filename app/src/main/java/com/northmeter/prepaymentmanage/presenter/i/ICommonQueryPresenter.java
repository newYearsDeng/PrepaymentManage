package com.northmeter.prepaymentmanage.presenter.i;

import android.os.Bundle;

import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public interface ICommonQueryPresenter {
    /**
     *
     * @param MeterType  表计类型
     * @param DateType    日期类型
     * @param Building    建筑
     * @param startData   开始时间
     * @param pageIndex   页码
     * @param pageSize    页数
     */

    void getData(int xRefreshType,String MeterType,String DateType,String Building,String startData,int pageIndex,int pageSize);

    /**
     * 取消rx的注册
     */
    void rxUnsubscribe();

    void toast(String msg);

    void initialdata(String meterType);

    void getNowTimeArea(String dateType,AlreadyBuilding info);

}
