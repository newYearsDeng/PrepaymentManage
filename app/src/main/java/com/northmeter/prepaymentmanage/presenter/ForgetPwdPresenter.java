package com.northmeter.prepaymentmanage.presenter;

import android.text.TextUtils;

import com.northmeter.prepaymentmanage.model.ForgetPwdProblemBean;
import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.presenter.i.IForgetPwdPresenter;
import com.northmeter.prepaymentmanage.ui.i.IForgetPwdActivity;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/23 14:24
 * @des 忘记密码的presenter
 */
public class ForgetPwdPresenter implements IForgetPwdPresenter {
    private final IForgetPwdActivity mForgetView;
    private Subscription mProblemSubscribe;
    private Subscription mConfirmSubscribe;

    public ForgetPwdPresenter(IForgetPwdActivity iForgetPwdActivity) {
        mForgetView = iForgetPwdActivity;
    }

    @Override
    public void getProblem(String phone) {
        mForgetView.setProblemClickable(false);
        mProblemSubscribe = RetrofitHelper
                .getApiService()
                .getProblem(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ForgetPwdProblemBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mForgetView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mForgetView.showToast("无法连接网络，请检查网络");
                        }
                        mForgetView.setProblemClickable(true);
                    }

                    @Override
                    public void onNext(ForgetPwdProblemBean forgetPwdProblemBean) {
                        String rescode = forgetPwdProblemBean.getRESCODE();
                        String resmsg = forgetPwdProblemBean.getRESMSG();
                        List<ForgetPwdProblemBean.RESPONSEXMLBean> responsexml = forgetPwdProblemBean.getRESPONSEXML();
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                //得到密保问题
                                String problem1 = responsexml.get(0).getPasswordProblem1();
                                String problem2 = responsexml.get(0).getPasswordProblem2();
                                String problem3 = responsexml.get(0).getPasswordProblem3();
                                LoggerUtil.d(problem1 + "---" + problem2 + "---" + problem3);

                                //创建密保问题集合
                                ArrayList<String> problemArr = new ArrayList<>();
                                if (!"null".equals(problem1) && !TextUtils.isEmpty(problem1)) {
                                    problemArr.add(problem1);
                                }
                                if (!"null".equals(problem2) && !TextUtils.isEmpty(problem2)) {
                                    problemArr.add(problem2);
                                }
                                if (!"null".equals(problem3) && !TextUtils.isEmpty(problem3)) {
                                    problemArr.add(problem3);
                                }
                                //把问题添加进数组
                                LoggerUtil.d(problemArr.size() + "");
                                if (problemArr.size() == 0) {
                                    mForgetView.showToast("此手机号码尚未设置密保");
                                } else {
                                    //从集合取出问题放进数组
                                    String[] problems = new String[problemArr.size()];
                                    for (int i = 0; i < problemArr.size(); i++) {
                                        problems[i] = problemArr.get(i);
                                    }
                                    mForgetView.showProblemDialog(problems);
                                }
                            } else {
                                mForgetView.showToast(resmsg);
                            }
                        } else {
                            mForgetView.showToast("接口数据异常，无法获取到数据");
                        }
                        mForgetView.setProblemClickable(true);
                    }
                });
    }

    @Override
    public void confirmChangePwd(String phone, String confirmPwd, String problem, String answer) {
        mForgetView.showLoading();
        mConfirmSubscribe = RetrofitHelper
                .getApiService()
                .resetPassword(phone, confirmPwd, problem, answer)
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
                            mForgetView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mForgetView.showToast("无法连接网络，请检查网络");
                        }
                        mForgetView.hideLoading();
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();

                        if ("1".equals(rescode)) {
                            if ("密码重置成功".equals(resmsg)) {
                                mForgetView.showToast("密码重置成功");
                                mForgetView.finishView();
                            } else if("密保答案不正确".equals(resmsg)){
                                mForgetView.showToast("密保答案不正确");
                            }else {
                                mForgetView.showToast("重设密码失败");
                            }
                        } else {
                            mForgetView.showToast("获取接口数据异常");
                        }
                        mForgetView.hideLoading();
                    }
                });
    }

    @Override
    public void unRxsubscribe() {
        if (mProblemSubscribe != null) {
            mProblemSubscribe.unsubscribe();
        }
        if (mConfirmSubscribe != null) {
            mConfirmSubscribe.unsubscribe();
        }
    }
}
