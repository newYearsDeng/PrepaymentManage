package com.northmeter.prepaymentmanage.ui.i;

/**
 * @author zz
 * @time 2016/11/23 13:56
 * @des 忘记密码view的接口
 */
public interface IForgetPwdActivity {
    void showToast(String toastStr);
    void setProblemClickable(boolean cilckable);
    void showLoading();
    void hideLoading();
    void showProblemDialog(String[] problemArr);
    void finishView();
}
