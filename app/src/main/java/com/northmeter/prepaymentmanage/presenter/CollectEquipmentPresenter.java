package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.DevicesStateBean;
import com.northmeter.prepaymentmanage.presenter.i.ICollectEquipmentPresenter;
import com.northmeter.prepaymentmanage.ui.i.ICollectEquipmentActivity;
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
 * @time 2016/11/18 15:56
 * @des 采集设备的presenter
 */
public class CollectEquipmentPresenter implements ICollectEquipmentPresenter{
    private final ICollectEquipmentActivity mView;
    private Subscription mSubscribe;

    public CollectEquipmentPresenter(ICollectEquipmentActivity iCollectEquipmentActivity) {
        mView= iCollectEquipmentActivity;
    }

    @Override
    public void getDevicesNumber() {
        mView.showLoading();
        mSubscribe = RetrofitHelper
                .getApiService()
                .getDevicesState()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<DevicesStateBean, Observable<DevicesStateBean.RESPONSEXMLBean>>() {
                    @Override
                    public Observable<DevicesStateBean.RESPONSEXMLBean> call(DevicesStateBean devicesStateBean) {
                        String rescode = devicesStateBean.getRESCODE();
                        String resmsg = devicesStateBean.getRESMSG();
                        if ("1".equals(rescode) && "成功".equals(resmsg)) {
                            return Observable.from(devicesStateBean.getRESPONSEXML());
                        } else {
                            mView.showToast("接口数据异常，获取设备信息失败");
                            mView.hideLoading();
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<DevicesStateBean.RESPONSEXMLBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mView.showToast("无法连接网络，请检查");
                        }
                        mView.showEmptyView();
                        mView.hideLoading();
                    }

                    @Override
                    public void onNext(DevicesStateBean.RESPONSEXMLBean responsexmlBean) {
                        String onLineSUM = responsexmlBean.getOnLineSUM();
                        String sum = responsexmlBean.getSUM();
                        mView.showDevices(sum, onLineSUM);
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void rxUnsubscribe() {
        if(mSubscribe != null){
            mSubscribe.unsubscribe();
        }
    }
}
