package com.northmeter.prepaymentmanage.presenter.i;

/**
 * @author zz
 * @time 2016/11/11 9:39
 * @des 注册presenter的接口
 */
public interface ILoginPresenter {
    void confirmLogin(String name,String password);
    void dbConfirmLogin();
    /**
     * 取消rx的注册
     */
    void rxUnsubscribe();
}
