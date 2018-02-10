package com.northmeter.prepaymentmanage.ui.air_conditioner.presenter;

import android.util.Log;

import com.northmeter.prepaymentmanage.model.EleUseInfoBean;
import com.northmeter.prepaymentmanage.model.NewIdBean;
import com.northmeter.prepaymentmanage.model.ReadingState;
import com.northmeter.prepaymentmanage.ui.air_conditioner.i.IAirConditionerControlPresenter;
import com.northmeter.prepaymentmanage.ui.air_conditioner.i.IAirConditionerControlSHow;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.net.ApiService;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

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
 * Created by dyd on 2018/2/1.
 */
public class AirConditionerControlPresenter implements IAirConditionerControlPresenter{
    private String TAG = "AirConditionerControlPresenter";
    private Subscription mEleSubscribe;
    private ApiService apiService;
    private Subscription mNewIdSubscribe;
    private Subscription mStateSubscribe;
    private Subscription mTimeSubscribe;
    private long startTime;
    private long timeLimit = 1 * 60 * 1000;
    private final IAirConditionerControlSHow iAirConditionerControlSHow;

    public AirConditionerControlPresenter(IAirConditionerControlSHow iAirConditionerControlSHow){
        this.iAirConditionerControlSHow = iAirConditionerControlSHow;
        apiService = RetrofitHelper
                .getApiService();
    }

    @Override
    public void sendControlData(String comaddress, String operatortype) {

    }

    @Override
    public void getData(String meterType, String comaddress) {
        apiService = RetrofitHelper
                .getApiService();
        iAirConditionerControlSHow.showLoading();

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
                                iAirConditionerControlSHow.showToast("获取数据失败，请重试");
                                iAirConditionerControlSHow.hideLoading();
                                return null;
                            }
                        } else {
                            iAirConditionerControlSHow.showToast("接口数据异常，无法获取到数据");
                            iAirConditionerControlSHow.hideLoading();
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
                            iAirConditionerControlSHow.showToast("网络连接超时，请检查网络");
                        } else {
                            iAirConditionerControlSHow.showToast("数据出错");
                        }
                        iAirConditionerControlSHow.hideLoading();
                    }

                    @Override
                    public void onNext(EleUseInfoBean.RESPONSEXMLBean responsexmlBean) {
                        String yffye = responsexmlBean.getYFFYE();
                        String bzdye = responsexmlBean.getBZDYE();
                        String dbzt = responsexmlBean.getDBZT();
                        String zydl = responsexmlBean.getZYDL();
                        String zye = responsexmlBean.getZYE();
                        String updatedtime = responsexmlBean.getUPDATEDTIME();
                        Log.i(TAG,"yffye"+yffye);
                        Log.i(TAG,"bzdye"+bzdye);
                        Log.i(TAG,"dbzt"+dbzt);
                        Log.i(TAG,"zydl"+zydl);
                        Log.i(TAG,"zye"+zye);
                        Log.i(TAG,"yffye"+yffye);
                        Log.i(TAG,"updatedtime"+updatedtime);


                        iAirConditionerControlSHow.showData(yffye, bzdye, zye, zydl, dbzt, updatedtime);
                        iAirConditionerControlSHow.hideLoading();
                    }
                });

    }

    /**
     * @param comadress
     * @param metertype
     * 发送灯控开关命令
     */
    public void sendLightsControl(final String comadress, final String metertype) {
        try{
            Log.i(TAG, "comadress " + comadress);
            Log.i(TAG, "meterType " + metertype);
            Log.i(TAG, "-------------------");
            startTime = System.currentTimeMillis();
            Log.i(TAG, "startTime " + startTime);
            iAirConditionerControlSHow.showLoading();
            mNewIdSubscribe = apiService.getRemotelylockNewId(comadress, metertype)
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
                            iAirConditionerControlSHow.hideLoading();
                            iAirConditionerControlSHow.showMeterState(0, "");
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

                            getReadingState(newid);
                        }
                    });
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void getReadingState(final String newid) {
        Log.i(TAG, "newid " + newid);
        iAirConditionerControlSHow.showMeterState(1, "任务正在执行中，请等待...");
        mStateSubscribe = apiService.getRemotelylockStatus(newid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadingState>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iAirConditionerControlSHow.hideLoading();
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
                                iAirConditionerControlSHow.hideLoading();
                                iAirConditionerControlSHow.showMeterState(2, "");
                                break;
                            case "1":
                                toast("执行成功");
                                iAirConditionerControlSHow.hideLoading();
                                iAirConditionerControlSHow.showMeterState(1, "");
                                //getData(meterType, comaddress);
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
                                                Log.i(TAG, "endTime " + endtime);
                                                if ((endtime - startTime) <= timeLimit) {
                                                    getReadingState(newid);
                                                } else {
                                                    iAirConditionerControlSHow.hideLoading();
                                                    iAirConditionerControlSHow.getStateTimeout();
                                                }
                                            }
                                        });
                                break;
                        }
                    }
                });
    }

    public void toast(String  msg){
        ToastUtil.showShort(MyApplication.getContext(),msg);
    }


    @Override
    public void rxUnsubscribe() {
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




    /**
     * @param comadress
     * @param metertype
     */
    //执行点抄任务
    public void readingMeter(final String comadress, final String metertype) {
        Log.i(TAG, "---------点钞------------");
        Log.i(TAG, "comadress " + comadress);
        Log.i(TAG, "meterType " + metertype);
        Log.i(TAG, "-------------------");
        startTime = System.currentTimeMillis();
        Log.i(TAG, "startTime " + startTime);
        iAirConditionerControlSHow.showLoading();
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
                        iAirConditionerControlSHow.hideLoading();
                        iAirConditionerControlSHow.showMeterState(0, "");
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
        Log.i(TAG, "newid " + newid);
        iAirConditionerControlSHow.showMeterState(1, "任务正在执行中，请等待...");
        mStateSubscribe = apiService.getReadingState(newid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReadingState>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        iAirConditionerControlSHow.hideLoading();
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
                                iAirConditionerControlSHow.hideLoading();
                                iAirConditionerControlSHow.showMeterState(2, "抄表");
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
                                                    iAirConditionerControlSHow.hideLoading();
                                                    iAirConditionerControlSHow.getStateTimeout();
                                                }
                                            }
                                        });
                                break;
                        }
                    }
                });
    }



}


