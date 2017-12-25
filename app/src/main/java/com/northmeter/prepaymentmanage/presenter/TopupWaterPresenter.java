package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.TopUp;
import com.northmeter.prepaymentmanage.presenter.i.ITopupWaterPresenter;
import com.northmeter.prepaymentmanage.ui.i.ITopupWater;
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
 * @time 2016/11/15 10:26
 * @des 充值流水的presenter
 */
public class TopupWaterPresenter implements ITopupWaterPresenter {
    private final ITopupWater mTopupWaterView;
    private Subscription mRecordSubscribe;

    public TopupWaterPresenter(ITopupWater iTopupWater) {
        mTopupWaterView = iTopupWater;
    }

    @Override
    public void getChargeRecord(String comaddress, String year) {

        mTopupWaterView.showLoading();
        mRecordSubscribe = RetrofitHelper.getApiService()
                .getGetChargeRecord(comaddress, year)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TopUp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mTopupWaterView.showToast("访问网络超时，请检查网络");
                        }else if(e instanceof ConnectException){
                            mTopupWaterView.showToast("无法连接网络，请检查");
                        }
                        mTopupWaterView.hideLoading();
                    }

                    @Override
                    public void onNext(TopUp topUp) {
                        String rescode = topUp.getRESCODE();
                        String resmsg = topUp.getRESMSG();
//                        LoggerUtil.d(rescode+"---"+resmsg);
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                List<TopUp.RESPONSEXMLBean> responsexml = topUp.getRESPONSEXML();
                                mTopupWaterView.showDataToLv(responsexml);
                                mTopupWaterView.hideLoading();
                            }else {
                                mTopupWaterView.showToast("找不到该用户的充值记录");
                                mTopupWaterView.showDataToLv(new ArrayList<TopUp.RESPONSEXMLBean>());
                                mTopupWaterView.hideLoading();
                                return;
                            }
                        } else {
                            mTopupWaterView.showToast("接口数据异常，无法获取到数据");
                            mTopupWaterView.showDataToLv(new ArrayList<TopUp.RESPONSEXMLBean>());
                            mTopupWaterView.hideLoading();
                            return;
                        }
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        mRecordSubscribe.unsubscribe();
    }
}
