package com.northmeter.prepaymentmanage.presenter;

import android.util.Log;

import com.northmeter.prepaymentmanage.model.BuildMeterUseData;
import com.northmeter.prepaymentmanage.presenter.i.ICommonQueryListPresenter;
import com.northmeter.prepaymentmanage.ui.i.IUseQueryListFragment;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class Use_Query_ListPresenter implements ICommonQueryListPresenter {
    private Subscription subscription;
    private IUseQueryListFragment fragment;


    public Use_Query_ListPresenter(IUseQueryListFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void getData(final int xRefreshType, String building, final String dateType, String startDate, String meterType, int pageIndex, int pageSize) {
        Log.i("LHT", "buildingId " + building);
        Log.i("LHT", "dateType " + dateType);
        Log.i("LHT", "startDate " + startDate);
        Log.i("LHT", "meterType " + meterType);
        Log.i("LHT", "pageIndex " + pageIndex);
        Log.i("LHT", "pageSize " + pageSize);
        Log.i("LHT", "----------------------------------------------- ");
        subscription = RetrofitHelper.getApiService()
                .getBuildMeterUseData(building, dateType, startDate, meterType, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BuildMeterUseData, List<BuildMeterUseData.RESPONSEXMLBean>>() {
                    @Override
                    public List<BuildMeterUseData.RESPONSEXMLBean> call(BuildMeterUseData buildMeteruseData) {
                        String rescode = buildMeteruseData.getRESCODE();
                        String resmsg = buildMeteruseData.getRESMSG();
                       /* Log.i("LHT", dateType + "-->" + rescode + "  " + resmsg);
                        Log.i("LHT", "--------------------------");*/
                        if (rescode.equals("1")) {
                            if (resmsg.equals("成功")) {
                                return buildMeteruseData.getRESPONSEXML();
                            } else {
                                toast("已加载所有数据");
                                return null;
                            }
                        } else if (rescode.equals("0")) {
                            toast("数据访问失败");
                            return null;
                        } else {
                            toast("网络连接失败，请检查网络");
                            return null;
                        }
                    }
                }).subscribe(new Subscriber<List<BuildMeterUseData.RESPONSEXMLBean>>() {
                    @Override
                    public void onCompleted() {
                        fragment.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.stopRefresh();
                        if (e instanceof SocketTimeoutException) {
                            toast("网络连接超时，请检查网络");
                        } else if (e instanceof ConnectException) {
                            toast("无法连接到服务器，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(List<BuildMeterUseData.RESPONSEXMLBean> responsexmlBeen) {
                        fragment.showDayDatas(xRefreshType,responsexmlBeen);
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    private void toast(String msg) {
        ToastUtil.showShort(MyApplication.getContext(), msg);
    }
}
