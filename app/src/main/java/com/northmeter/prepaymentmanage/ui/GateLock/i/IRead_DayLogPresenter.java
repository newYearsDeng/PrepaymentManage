package com.northmeter.prepaymentmanage.ui.GateLock.i;

/**
 * Created by dyd on 2017/7/1.
 */
public interface IRead_DayLogPresenter {
    void  getData(int xRefreshType,String buildingId,String DateType,String StartDate,String METERTYPE,int PageIndex,int PageSize);
    void rxUnsubscribe();

    void initialdata();
}
