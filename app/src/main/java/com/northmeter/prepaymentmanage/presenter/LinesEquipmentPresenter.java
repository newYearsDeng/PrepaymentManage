package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.LinesDevicesBean;
import com.northmeter.prepaymentmanage.presenter.i.ILinesEquipmentPresenter;
import com.northmeter.prepaymentmanage.ui.i.ILinesEquipmentActivity;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/18 16:29
 * @des 在/离 线设备显示的presenter
 */
public class LinesEquipmentPresenter implements ILinesEquipmentPresenter {
    private final ILinesEquipmentActivity mLinesView;
    private Subscription mSubscribe;

    public LinesEquipmentPresenter(ILinesEquipmentActivity iLinesEquipmentActivity) {
        mLinesView = iLinesEquipmentActivity;
    }

    @Override
    public void getDevicesInfo(String linesStr) {
        mLinesView.showLoading();
        mSubscribe = RetrofitHelper
                .getApiService()
                .getLinesDevicesInfo(linesStr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LinesDevicesBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mLinesView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mLinesView.showToast("无法连接网络，请检查");
                        }
                        mLinesView.hideLoading();
                    }

                    @Override
                    public void onNext(LinesDevicesBean linesDevicesBean) {
                        String rescode = linesDevicesBean.getRESCODE();
                        String resmsg = linesDevicesBean.getRESMSG();
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                List<LinesDevicesBean.RESPONSEXMLBean> responsexml = linesDevicesBean.getRESPONSEXML();
                                if (responsexml != null) {
                                    mLinesView.showDevices(responsexml);
                                    mLinesView.hideLoading();
                                } else {
                                    mLinesView.showToast("没有数据");
                                    mLinesView.hideLoading();
                                    return;
                                }
                            } else {
                                mLinesView.showToast("获取数据失败");
                                mLinesView.hideLoading();
                                return;
                            }
                        } else {
                            mLinesView.showToast("接口数据异常，无法获取到数据");
                            mLinesView.hideLoading();
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
