package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/18 10:15
 * @des 绑定房间presenter的接口
 */
public interface IBindRoomPresenter {
    void confirmBindRoom(String roomBuildingId);

    void getSchool();

    void getSchoolSon(String sonType, String area_id);

    void rxUnsubscribe();
}
