package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.presenter.i.IChangePwdPresenter;
import com.northmeter.prepaymentmanage.ui.i.IChangePwdActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.net.ApiService;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/17 15:22
 * @des 修改密码的presenter
 */
public class ChangePwdPresenter implements IChangePwdPresenter {


    private final IChangePwdActivity mChangePwdView;
    private Subscription mSubscribe;

    public ChangePwdPresenter(IChangePwdActivity iChangePwdActivity) {
        mChangePwdView = iChangePwdActivity;

    }


    @Override
    public void setChangePwd(String userType, String oldPwd, String confirmPwd) {
        //从sp取出loginname
        String getSpId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String loginName = AES.decrypt(getSpId, Contants.DECRYPT_ID_KEY);
        LoggerUtil.d(loginName);
        //根据用户类型来判断调用那个接口
        ApiService apiService = RetrofitHelper
                .getApiService();
        Observable<RequestResult> observable = Contants.USER_TYPE_USER.equals(userType) ?
                apiService.changeUserPwd(loginName, oldPwd, confirmPwd) : apiService.changeManagePwd(loginName, oldPwd, confirmPwd);
        mSubscribe = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RequestResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mChangePwdView.showToast("链接超时，请检查网络");
                        } else if (e instanceof ConnectException) {
                            mChangePwdView.showToast("访问网络出错，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                mChangePwdView.showToast("修改密码成功，请重新登录");
                                mChangePwdView.changeSucceed();
                            } else {
                                mChangePwdView.showToast("旧密码不正确，请重新输入");
                                return;
                            }
                        } else {
                            mChangePwdView.showToast("接口数据异常，无法修改密码");
                            return;
                        }
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
        }
    }
}
