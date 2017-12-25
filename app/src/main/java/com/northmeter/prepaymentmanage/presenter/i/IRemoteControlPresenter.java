package com.northmeter.prepaymentmanage.presenter.i;

import com.northmeter.prepaymentmanage.model.EquipmentBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/24.
 */
public interface IRemoteControlPresenter {

    void getData(String type, String selectedArea_ID,String controlType );

    void rxUnsubscribe();

    void goControlMeter(List<String> datas,String ControlType,String OPNAME );

}
