package com.northmeter.prepaymentmanage.ui.i;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public interface IUseQueryFragment {
    //选择建筑后，更改UI
    void setTvChooseTimeArea(String time,String area,String position,String buildingId);
    void setTimeAreaFromSp(String time,String area,String position,String buildingId);

    void setShowingFragment();
}
