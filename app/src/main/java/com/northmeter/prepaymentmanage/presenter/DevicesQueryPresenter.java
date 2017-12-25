package com.northmeter.prepaymentmanage.presenter;

import android.util.Log;

import com.northmeter.prepaymentmanage.model.EleUseInfoBean;
import com.northmeter.prepaymentmanage.model.NewIdBean;
import com.northmeter.prepaymentmanage.model.ReadingState;
import com.northmeter.prepaymentmanage.model.WaterUseInfoBean;
import com.northmeter.prepaymentmanage.presenter.i.IDevicesQueryPresenter;
import com.northmeter.prepaymentmanage.ui.i.IDevicesQueryActivity;
import com.northmeter.prepaymentmanage.util.net.ApiService;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/11 17:31
 * @des 水/电 表设备查询的presenter
 */
public class DevicesQueryPresenter implements IDevicesQueryPresenter {
    private final IDevicesQueryActivity mDevicesQueryView;
    private Subscription mWaterSubscribe;
    private Subscription mEleSubscribe;
    private ApiService apiService;
    private Subscription mNewIdSubscribe;
    private Subscription mStateSubscribe;
    private Subscription mTimeSubscribe;
    private long startTime;
    private long timeLimit = 1 * 60 * 1000;

    public DevicesQueryPresenter(IDevicesQueryActivity iDevicesQueryActivity) {
        mDevicesQueryView = iDevicesQueryActivity;
    }

    @Override
    public void getData(String meterType, String comaddress) {
        apiService = RetrofitHelper
                .getApiService();
        mDevicesQueryView.showLoading();
        if ("水".equals(meterType)) {
            mWaterSubscribe = apiService.getWaterDevicesInfo(comaddress)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<WaterUseInfoBean, Observable<WaterUseInfoBean.RESPONSEXMLBean>>() {
                        @Override
                        public Observable<WaterUseInfoBean.RESPONSEXMLBean> call(WaterUseInfoBean waterUseInfoBean) {
                            String rescode = waterUseInfoBean.getRESCODE();
                            String resmsg = waterUseInfoBean.getRESMSG();
                            if ("1".equals(rescode)) {
                                if ("成功".equals(resmsg)) {
                                    return Observable.from(waterUseInfoBean.getRESPONSEXML());
                                } else {
                                    mDevicesQueryView.showToast("获取数据失败，请重试");
                                    mDevicesQueryView.hideLoading();
                                    return null;
                                }
                            } else {
                                mDevicesQueryView.showToast("接口数据异常，无法获取到数据");
                                mDevicesQueryView.hideLoading();
                                return null;
                            }
                        }
                    })
                    .subscribe(new Subscriber<WaterUseInfoBean.RESPONSEXMLBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LoggerUtil.d(e.toString());
                            if (e instanceof SocketTimeoutException) {
                                mDevicesQueryView.showToast("网络连接超时，请检查网络");
                            } else {
                                mDevicesQueryView.showToast("数据出错");
                            }
                            mDevicesQueryView.hideLoading();
                        }

                        @Override
                        public void onNext(WaterUseInfoBean.RESPONSEXMLBean responsexmlBean) {
                            String bzsye = responsexmlBean.getBZSYE();
                            String sbzt = responsexmlBean.getSBZT();
                            String zye = responsexmlBean.getZYE();
                            String zysl = responsexmlBean.getZYSL();
                            String updatedtime = responsexmlBean.getUPDATEDTIME();
                            mDevicesQueryView.showData("", bzsye, zye, zysl, sbzt, updatedtime);
                            mDevicesQueryView.hideLoading();
                        }
                    });
        } else {
            mEleSubscribe = apiService.getEleDevicesInfo(comaddress)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1<EleUseInfoBean, Observable<EleUseInfoBean.RESPONSEXMLBean>>() {
                        @Override
                        public Observable<EleUseInfoBean.RESPONSEXMLBean> call(EleUseInfoBean eleUseInfoBean) {
                            String rescode = eleUseInfoBean.getRESCODE();
                            String resmsg = eleUseInfoBean.getRESMSG();
                            if ("1".equals(rescode)) {
                                if ("成功".equals(resmsg)) {
                                    return Observable.from(eleUseInfoBean.getRESPONSEXML());
                                } else {
                                    mDevicesQueryView.showToast("获取数据失败，请重试");
                                    mDevicesQueryView.hideLoading();
                                    return null;
                                }
                            } else {
                                mDevicesQueryView.showToast("接口数据异常，无法获取到数据");
                                mDevicesQueryView.hideLoading();
                                return null;
                            }
                        }
                    })
                    .subscribe(new Subscriber<EleUseInfoBean.RESPONSEXMLBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LoggerUtil.d(e.toString());
                            if (e instanceof SocketTimeoutException) {
                                mDevicesQueryView.showToast("网络连接超时，请检查网络");
                            } else {
                                mDevicesQueryView.showToast("数据出错");
                            }
                            mDevicesQueryView.hideLoading();
                        }

                        @Override
                        public void onNext(EleUseInfoBean.RESPONSEXMLBean responsexmlBean) {
                            String yffye = responsexmlBean.getYFFYE();
                            String bzdye = responsexmlBean.getBZDYE();
                            String dbzt = responsexmlBean.getDBZT();
                            String zydl = responsexmlBean.getZYDL();
                            String zye = responsexmlBean.getZYE();
                            String updatedtime = responsexmlBean.getUPDATEDTIME();
                            mDevicesQueryView.showData(yffye, bzdye, zye, zydl, dbzt, updatedtime);
                            mDevicesQueryView.hideLoading();
                        }
                    });
        }

    }

    /**
     * @param comadress
     * @param metertype
     */
    //执行抄表任务
    public void readingMeter(final String comadress, final String metertype) {
        Log.i("LHT", "---------点钞------------");
        Log.i("LHT", "comadress " + comadress);
        Log.i("LHT", "meterType " + metertype);
        Log.i("LHT", "-------------------");
        startTime = System.currentTimeMillis();
        Log.i("LHT", "startTime " + startTime);
        mDevicesQueryView.showLoading();
        mNewIdSubscribe = apiService.getNewId(comadress, metertype)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<NewIdBean, NewIdBean.RESPONSEXMLBean>() {
                    @Override
                    public NewIdBean.RESPONSEXMLBean call(NewIdBean newIdBean) {
                        String rescode = newIdBean.getRESCODE();
                        String resmsg = newIdBean.getRESMSG();
                        if (rescode.equals("0")) {
                            toast("接口数据异常，无法获取到数据");
                            return null;
                        } else if (rescode.equals("1")) {
                            if (resmsg.equals("成功")) {
                                return newIdBean.getRESPONSEXML().get(0);
                            } else {
                                toast(resmsg);
                                return null;
                            }
                        } else {
                            toast("网络连接超时，请检查网络");
                            return null;
                        }
                    }
                }).subscribe(new Subscriber<NewIdBean.RESPONSEXMLBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mDevicesQueryView.hideLoading();
                        mDevicesQueryView.showMeterState(0, "");
                        if (e instanceof SocketTimeoutException) {
                            toast("网络连接超时，请检查网络");
                        } else if (e instanceof ConnectException) {
                            toast("无法连接到服务器，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(NewIdBean.RESPONSEXMLBean responsexmlBean) {
                        String newid = responsexmlBean.getNEWID();
                        if (newid == null || newid.equals("")) {
                            return;
                        }

                        getReadingState(newid, comadress, metertype);
                    }
                });
    }

    private void getReadingState(final String newid, final String comaddress, final String meterType) {
        Log.i("LHT", "newid " + newid);
        mDevicesQueryView.showMeterState(1, "任务正在执行中，请等待...");
        mStateSubscribe = apiService.getReadingState(newid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadingState>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mDevicesQueryView.hideLoading();
                        if (e instanceof SocketTimeoutException) {
                            toast("网络连接超时，请检查网络");
                        } else if (e instanceof ConnectException) {
                            toast("无法连接到服务器，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(ReadingState readingState) {
                        switch (readingState.getRESCODE()) {
                            case "0":
                                toast("执行失败");
                                mDevicesQueryView.hideLoading();
                                mDevicesQueryView.showMeterState(2, "抄表");
                                break;
                            case "1":
                                toast("执行成功");
                                getData(meterType, comaddress);
                                break;
                            case "2":
                                //toast("未执行");
                                //mDevicesQueryView.hideLoading();
                                mTimeSubscribe = Observable.timer(2, TimeUnit.SECONDS)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Action1<Long>() {
                                            @Override
                                            public void call(Long aLong) {
                                                final long endtime = System.currentTimeMillis();
                                                Log.i("Lht", "endTime " + endtime);
                                                if ((endtime - startTime) <= timeLimit) {
                                                    getReadingState(newid, comaddress, meterType);
                                                } else {
                                                    mDevicesQueryView.hideLoading();
                                                    mDevicesQueryView.getStateTimeout();
                                                }
                                            }
                                        });
                                break;
                        }
                    }
                });
    }

    ;

    /**
     * toast
     *
     * @param msg
     */
    public void toast(String msg) {
        ToastUtil.showShort(MyApplication.getContext(), msg);
    }

    @Override
    public void rxUnsubscribe() {
        if (mWaterSubscribe != null) {
            mWaterSubscribe.unsubscribe();
        }
        if (mEleSubscribe != null) {
            mEleSubscribe.unsubscribe();
        }
        if (mNewIdSubscribe != null) {
            mNewIdSubscribe.unsubscribe();
        }
        if (mStateSubscribe != null) {
            mStateSubscribe.unsubscribe();
        }
        if (mTimeSubscribe != null) {
            mTimeSubscribe.unsubscribe();
        }
    }


}
