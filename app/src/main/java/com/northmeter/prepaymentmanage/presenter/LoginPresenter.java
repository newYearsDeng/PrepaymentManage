package com.northmeter.prepaymentmanage.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.northmeter.prepaymentmanage.model.LoginBean;
import com.northmeter.prepaymentmanage.model.bdBean.BDLoginBean;
import com.northmeter.prepaymentmanage.presenter.i.ILoginPresenter;
import com.northmeter.prepaymentmanage.ui.i.ILoginActivity;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author admin
 * @time 2016/11/11 15:04
 * @des 登录的presenter
 */
public class LoginPresenter implements ILoginPresenter {
    private final ILoginActivity mLoginView;
    private Subscription mLoginSubscribe;

    public LoginPresenter(ILoginActivity iLoginActivity) {
        mLoginView = iLoginActivity;
    }

    @Override
    public void confirmLogin(final String name, String password) {

        //判空
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
            //判断是否保存密码
            if (mLoginView.isCheck()) {
                //对密码进行加密并存储
                mLoginView.putPwdToSp(password);
            } else {
                //清空密码
                mLoginView.clearPwdToSp();
            }
            //加载动画
            mLoginView.btnClickable(false);
            mLoginView.showLoading();

            mLoginSubscribe = RetrofitHelper
                    .getApiService()
                    .postLogin(name, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<LoginBean, Observable<LoginBean.RESPONSEXMLBean>>() {
                        @Override
                        public Observable<LoginBean.RESPONSEXMLBean> call(LoginBean loginBean) {

                            String rescode = loginBean.getRESCODE();
                            String resmsg = loginBean.getRESMSG();
                            Log.i("admin","loginBean "+loginBean.toString());
                            if ("0".equals(rescode)) {
                                //返回值为0
                                if ("接口异常".equals(resmsg)) {
                                    mLoginView.showToast("接口数据异常，请稍后重试");
                                    mLoginView.btnClickable(true);
                                    mLoginView.hideLoading();
                                } else {
                                    mLoginView.showToast("数据获取失败，请稍后重试");
                                    mLoginView.btnClickable(true);
                                    mLoginView.hideLoading();
                                }
                                return null;
                            } else {
                                //返回值为1
                                if ("成功".equals(resmsg)) {
                                    //成功才有list集合
                                    List<LoginBean.RESPONSEXMLBean> responsexml = loginBean.getRESPONSEXML();

                                    return Observable.from(responsexml);
                                } else if ("登录账号或密码不对!".equals(resmsg)) {
                                    mLoginView.showToast("账号或密码有误");
                                    mLoginView.btnClickable(true);
                                    mLoginView.hideLoading();
                                    return null;
                                } else {
                                     mLoginView.showToast("登录失败");
                                    mLoginView.btnClickable(true);
                                    mLoginView.hideLoading();
                                    return null;
                                }
                            }
                        }
                    })
                    .subscribe(new Subscriber<LoginBean.RESPONSEXMLBean>() {
                        @Override
                        public void onCompleted() {
                            LoggerUtil.d("completed");
                        }

                        @Override
                        public void onError(Throwable e) {

                            LoggerUtil.d(e.toString());
                            if (e instanceof SocketTimeoutException) {
                                mLoginView.showToast("网络连接超时，请检查网络");
                            } else if (e instanceof ConnectException) {
                                mLoginView.showToast("无法连接到服务器，请检查网络");
                            }
                            mLoginView.btnClickable(true);
                            mLoginView.hideLoading();
                        }

                        @Override
                        public void onNext(LoginBean.RESPONSEXMLBean responsexmlBean) {
                            String buildingID = responsexmlBean.getBuildingID();
                            String oper_type = responsexmlBean.getOPER_TYPE();
                            //查询员  超级管理员
                            mLoginView.startHomeView(oper_type, buildingID);

                            mLoginView.btnClickable(true);
                            mLoginView.hideLoading();
                            //测试
                            mLoginView.setJpushTag(name);
                        }
                    });
            //对账号进行加密、保存、跳转页面
            mLoginView.putNameToSp(name);
        } else {
            mLoginView.showToast("账号密码不能为空");
            return;
        }
    }

    @Override
    public void dbConfirmLogin(){
        mLoginSubscribe = RetrofitHelper.getApiService().getLogin("","","","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<BDLoginBean, Observable<BDLoginBean>>() {
                    @Override
                    public Observable<BDLoginBean> call(BDLoginBean bdLoginBean) {
                        return Observable.just(bdLoginBean);
                    }
                }).subscribe(new Subscriber<BDLoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BDLoginBean bdLoginBean) {

                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (mLoginSubscribe != null) {
            mLoginSubscribe.unsubscribe();
        }
    }
}
