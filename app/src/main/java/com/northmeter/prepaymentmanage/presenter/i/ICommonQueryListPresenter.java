package com.northmeter.prepaymentmanage.presenter.i;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public interface ICommonQueryListPresenter {

    void getData(int xRefreshType,String MeterType,String DateType,String Building,String startData,int pageIndex,int pageSize);

    /**
     * 取消rx的注册
     */
    void rxUnsubscribe();
}
