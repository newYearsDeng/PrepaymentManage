package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/11 9:42
 * @des 登录view的接口
 */
public interface ILoginActivity {
    /**
     * 弹出toast
     * @param toastStr
     */
    void showToast(String toastStr);

    /**
     * 判断checkbox是否选中
     */
    boolean isCheck();
    /**
     * 保存账号
     */
    void putNameToSp(String name);
    /**
     * 保存密码
     */
    void putPwdToSp(String password);
    /**
     * 清空密码
     */
    void clearPwdToSp();
    /**
     * 进入主页面
     * @param oper_type
     * @param buildingID
     */
    void startHomeView(String oper_type, String buildingID);

    void showLoading();
    void hideLoading();
    void btnClickable(boolean btnClickable);

    void setJpushTag(String id);

}
