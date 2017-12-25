package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/18 10:14
 * @des 绑定房间view的接口
 */
public interface IBindRoomActivity {
    void showToast(String toastStr);

    void showSchool(String[] areaIds, String[] areaNames);
    void showBuilding(String[] areaIds, String[] areaNames);
    void showFloor(String[] areaIds, String[] areaNames);
    void showRoom(String[] areaIds, String[] areaNames);
    void noHaveDownInfo(String area_id);

    void bindSucceed();
    void showLoading();
    void hideLoading();
    void buildingClickable(boolean clickable);
}
