package com.northmeter.prepaymentmanage.presenter;

import android.text.TextUtils;

import com.northmeter.prepaymentmanage.model.BillsBean;
import com.northmeter.prepaymentmanage.presenter.i.IBillsPresenter;
import com.northmeter.prepaymentmanage.ui.i.IBillsActivity;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/14 17:38
 * @des 水/电 账单的presenter
 */
public class BillsPresenter implements IBillsPresenter {

    private final IBillsActivity mBillView;
    private Subscription mBillSubscribe;

    public BillsPresenter(IBillsActivity iBillsActivity) {
        mBillView = iBillsActivity;
    }

    @Override
    public void getMonthBill(String comaddress, String yearAndMonth) {
        mBillView.showLoading();
        mBillSubscribe = RetrofitHelper
                .getApiService()
                .getMonthUseData(comaddress, yearAndMonth)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<BillsBean, Observable<BillsBean.RESPONSEXMLBean>>() {
                    @Override
                    public Observable<BillsBean.RESPONSEXMLBean> call(BillsBean billsBean) {
                        String rescode = billsBean.getRESCODE();
                        String resmsg = billsBean.getRESMSG();
//                        LoggerUtil.d(rescode + "---" + resmsg);
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                return Observable.from(billsBean.getRESPONSEXML());
                            } else {
                                mBillView.showToast("没有该月的用能数据");
                                mBillView.showEmptyData();
                                mBillView.hideLoading();
                                return null;
                            }
                        } else {
                            mBillView.showToast("接口数据异常，无法获取到数据");
                            mBillView.hideLoading();
                            return null;
                        }

                    }
                }).subscribe(new Subscriber<BillsBean.RESPONSEXMLBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mBillView.showToast("网络连接超时，请检查网络后重试");
                        }else if (e instanceof ConnectException){
                            mBillView.showToast("无法连接网络，请检查");
                        }
                        mBillView.hideLoading();
                        mBillView.showEmptyData();
                    }

                    @Override
                    public void onNext(BillsBean.RESPONSEXMLBean responsexmlBean) {
                        String qcbd = responsexmlBean.getQCBD();
                        String qmbd = responsexmlBean.getQMBD();
                        String jsdata = responsexmlBean.getJSDATA();
                        String jsmoney = responsexmlBean.getJSMONEY();
                        LoggerUtil.d(qcbd + "---" + qmbd + "---" + jsdata + "---" + jsmoney);
                        if (!TextUtils.isEmpty(qcbd) && !TextUtils.isEmpty(qmbd)
                                && !TextUtils.isEmpty(jsdata) && !TextUtils.isEmpty(jsmoney)) {
                            mBillView.showDataToTextView(qcbd, qmbd, jsdata, jsmoney);
                        }
                        mBillView.hideLoading();
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (mBillSubscribe != null) {
            mBillSubscribe.unsubscribe();
        }
    }
}
