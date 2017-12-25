package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/11 9:42
 * @des 注册view的接口
 */
public interface IRegistActivity {
    /**
     * 弹出toast
     * @param toastStr
     */
    void showToast(String toastStr);

    /**
     * 弹出警告框
     */
    void showProblemDialog();
    /**
     * 关闭页面
     */
    void finishView();
    /**
     * 校区建筑选择
     * @param areaIds
     */
    void showSchoolBuildingDialog(String[] areaIds, String[] areaNames);
    /**
     * 隐藏dialog
     * @param area_id
     */
    void showBuildingName(String area_id);

    void showLoading();
    void hideLoading();
    void btnClickable(boolean clickable);
    void buildingClickable(boolean clickable);
}
