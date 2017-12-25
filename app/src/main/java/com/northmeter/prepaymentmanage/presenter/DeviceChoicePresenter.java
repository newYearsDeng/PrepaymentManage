package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.LookChumBean;
import com.northmeter.prepaymentmanage.model.UserMeterBean;
import com.northmeter.prepaymentmanage.presenter.i.IDeviceChoicePresenter;
import com.northmeter.prepaymentmanage.ui.i.IDeviceChoiceActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/16 16:33
 * @des 设备选择的presenter
 */
public class DeviceChoicePresenter implements IDeviceChoicePresenter {
    private final IDeviceChoiceActivity mDeviceChoiceView;
    private Subscription mMeterSubscribe;
    private Subscription mLookChumSubscribe;


    public DeviceChoicePresenter(IDeviceChoiceActivity iDeviceChoiceActivity) {
        mDeviceChoiceView = iDeviceChoiceActivity;
    }

    @Override
    public void getMeters() {
        //从sp取出loginname
        String getSpId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String loginName = AES.decrypt(getSpId, Contants.DECRYPT_ID_KEY);
        LoggerUtil.d(loginName);

        mDeviceChoiceView.showLoading();
        mMeterSubscribe = RetrofitHelper
                .getApiService()
                .getUserMeter(loginName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserMeterBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mDeviceChoiceView.showToast("网络连接超时，请检查网络");
                        } else {
                            mDeviceChoiceView.showToast("数据出错");
                        }
                        mDeviceChoiceView.hideLoading();
                    }

                    @Override
                    public void onNext(UserMeterBean userMeterBean) {
                        String rescode = userMeterBean.getRESCODE();
                        String resmsg = userMeterBean.getRESMSG();
                        LoggerUtil.d(rescode + "---" + resmsg);
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                List<UserMeterBean.RESPONSEXMLBean> responsexml = userMeterBean.getRESPONSEXML();
                                mDeviceChoiceView.showDevices(responsexml);
                            } else {
                                mDeviceChoiceView.showToast("找不到该用户的表计信息");
                            }
                        } else {
                            mDeviceChoiceView.showToast("接口数据异常，无法获取到数据");
                        }
                        mDeviceChoiceView.hideLoading();
                    }
                });
    }

    @Override
    public void lookChum() {
        //从sp里面取出loginid
        String spDecrypeId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        //根据id取出buidingid
        String spDecrypeBuildingId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spDecrypeId, "");
        String buidingId = AES.decrypt(spDecrypeBuildingId, Contants.SP_AES_BUILDING_KEY);
        LoggerUtil.d("building------" + buidingId);

        mDeviceChoiceView.showLoading();
        mDeviceChoiceView.setClickable(false);
        mLookChumSubscribe = RetrofitHelper
                .getApiService()
                .getChumsName(buidingId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<LookChumBean, List<LookChumBean.RESPONSEXMLBean>>() {
                    @Override
                    public List<LookChumBean.RESPONSEXMLBean> call(LookChumBean lookChumBean) {
                        //该房间尚未住人
                        String rescode = lookChumBean.getRESCODE();
                        String resmsg = lookChumBean.getRESMSG();
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                List<LookChumBean.RESPONSEXMLBean> responsexml = lookChumBean.getRESPONSEXML();
                                return responsexml;
                            } else {
                                mDeviceChoiceView.showToast("该房间尚未住人");
                                mDeviceChoiceView.hideLoading();
                                mDeviceChoiceView.setClickable(true);
                                return null;
                            }
                        } else {
                            mDeviceChoiceView.showToast("接口数据异常，无法获取到数据");
                            mDeviceChoiceView.hideLoading();
                            mDeviceChoiceView.setClickable(true);
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<List<LookChumBean.RESPONSEXMLBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mDeviceChoiceView.showToast("网络连接超时，请检查网络");
                        } else {
                            mDeviceChoiceView.showToast("数据出错");
                        }
                        mDeviceChoiceView.hideLoading();
                        mDeviceChoiceView.setClickable(true);
                    }

                    @Override
                    public void onNext(List<LookChumBean.RESPONSEXMLBean> responsexmlBeen) {

                        mDeviceChoiceView.showLookChum(responsexmlBeen);

                        mDeviceChoiceView.hideLoading();
                        mDeviceChoiceView.setClickable(true);
                    }
                });
    }


    @Override
    public void rxUnsubscribe() {
        if (mMeterSubscribe != null) {
            mMeterSubscribe.unsubscribe();
        }
        if (mLookChumSubscribe != null) {
            mLookChumSubscribe.unsubscribe();
        }
    }
}
