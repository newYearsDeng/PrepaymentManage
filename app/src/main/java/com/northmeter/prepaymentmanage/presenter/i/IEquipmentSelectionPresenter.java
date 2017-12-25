package com.northmeter.prepaymentmanage.presenter.i;

/**
 * Created by Administrator on 2016/11/16.
 */
public interface IEquipmentSelectionPresenter {
    /**
     * 加载数据
     * @param refreshType 下拉刷新/加载更多
     * @param selectedArea_ID
     * @param pageSize
     * @param pageIndex
     */
    void getData(int refreshType,String type ,String selectedArea_ID,String controlType, int  pageSize, int pageIndex);

    String  getSelectedAreaID();
    /**
     * 取消订阅
     */
    void rxUnsubscribe();
}
