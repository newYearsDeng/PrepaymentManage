package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/23 14:24
 * @des 忘记密码presenter的接口
 */
public interface IForgetPwdPresenter {
    void getProblem(String phone);
    void confirmChangePwd(String phone, String confirmPwd, String problem, String answer);
    void unRxsubscribe();
}
