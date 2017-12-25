package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.presenter.i.IFeedbackPresenter;
import com.northmeter.prepaymentmanage.ui.i.IFeedbackActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/17 17:16
 * @des 反馈意见的presenter
 */
public class FeedbackPresenter implements IFeedbackPresenter {
    private final IFeedbackActivity mFeedbackView;
    private Subscription mSubscribe;

    public FeedbackPresenter(IFeedbackActivity iFeedbackActivity) {
        mFeedbackView = iFeedbackActivity;
    }

    @Override
    public void setFeedbackContent(String content) {

        //从sp取出loginname
        String getSpId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String loginName = AES.decrypt(getSpId, Contants.DECRYPT_ID_KEY);
        LoggerUtil.d(loginName);

        mSubscribe = RetrofitHelper.getApiService()
                .setFeedbackContent(loginName, content)
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
                            mFeedbackView.showToast("连接超时，请重试");
                        } else if (e instanceof ConnectException) {
                            mFeedbackView.showToast("无法连接网络，请检查");
                        }
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        if ("1".equals(rescode)){
                            if ("建议提交成功".equals(resmsg)){
                                mFeedbackView.showToast("建议提交成功");
                                mFeedbackView.commitSucceed();
                            }else {
                                mFeedbackView.showToast("建议提交有误");
                                return;
                            }
                        }else {
                            mFeedbackView.showToast("接口数据异常，提交失败");
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
