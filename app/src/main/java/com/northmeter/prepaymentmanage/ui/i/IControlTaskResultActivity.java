package com.northmeter.prepaymentmanage.ui.i;

import com.northmeter.prepaymentmanage.model.ControlTaskBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */
public interface IControlTaskResultActivity {
    void showData(List<ControlTaskBean.RESPONSEXMLBean> datas);
    void stopRefresh();
}
