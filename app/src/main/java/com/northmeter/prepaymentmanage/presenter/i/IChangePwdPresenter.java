package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/17 15:22
 * @des 修改密码presenter的接口
 */
public interface IChangePwdPresenter {
//    void setUserChangePwd(String oldPwd, String confirmPwd);
//    void setManangeChangePwd(String oldPwd, String confirmPwd);

    void setChangePwd(String userType, String oldPwd, String confirmPwd);
    void rxUnsubscribe();
}
