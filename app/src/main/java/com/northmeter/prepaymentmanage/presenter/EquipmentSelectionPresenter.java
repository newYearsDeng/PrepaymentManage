package com.northmeter.prepaymentmanage.presenter;

import android.util.Log;

import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.presenter.i.IEquipmentSelectionPresenter;
import com.northmeter.prepaymentmanage.ui.i.IEquipmentSelection;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
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
 * Created by Administrator on 2016/11/16.
 */
public class EquipmentSelectionPresenter implements IEquipmentSelectionPresenter {
    private static final java.lang.String TAG = EquipmentSelectionPresenter.class.getSimpleName();
    private Subscription subscription;
    private IEquipmentSelection activity;
    private String normal, count, accuracy;

    public EquipmentSelectionPresenter( IEquipmentSelection activity) {
        this.activity = activity;
    }

    @Override
    public void getData(final int refreshType, String type, String selectedArea_ID, String controlType , int pageIndex, int pageSize) {
        Log.i("TAG","type "+type);
        Log.i("TAG","buildingId "+selectedArea_ID);
        Log.i("TAG","controlType "+controlType);
        Log.i("TAG","pageIndex "+pageIndex);
        Log.i("TAG","pageSize "+pageSize);
        Log.i("TAG","-----------------------");

        subscription=RetrofitHelper.getApiService()
                .getMeterInfo(selectedArea_ID, type,controlType, pageIndex+"", pageSize+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<EquipmentBean, List<EquipmentBean.RESPONSEXMLBean>>() {
                    @Override
                    public List<EquipmentBean.RESPONSEXMLBean> call(EquipmentBean equipmentBean) {
                        String rescode = equipmentBean.getRESCODE();
                        String resmsg = equipmentBean.getRESMSG();
                        if (rescode.equals("1")) {
                            count = equipmentBean.getCOUNT();
                            accuracy = equipmentBean.getACCURACY();
                            normal = equipmentBean.getNORMAL();
                            if (resmsg.equals("成功")) {
                                return equipmentBean.getRESPONSEXML();
                            } else {
                               // toast(resmsg);
                                return null;
                            }
                        } else if (rescode.equals("0")) {
                            ToastUtil.showShort(MyApplication.getContext(), resmsg);
                            return null;
                        } else {
                           toast("网络异常，请检查网络..");
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<List<EquipmentBean.RESPONSEXMLBean>>() {
                    @Override
                    public void onCompleted() {
                        activity.stopRefresh();
                        //activity.stopLoadMore();
                        //存储刷新时间
                        SharedPreferencesUtil.put(MyApplication.getContext(), "lastRefreshTime", activity.getLastRefreshTime());
                        //lvEs.setOnItemClickListener(listener);
                       // toast("加载完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.stopRefresh();
                       // activity.stopLoadMore();
                        if (e instanceof SocketTimeoutException) {
                            toast("网络连接超时，请检查网络");
                        } else if (e instanceof ConnectException) {
                            toast("无法连接到服务器，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen) {
                        //下拉刷新

                            activity.showNewData(refreshType,count, accuracy, normal, responsexmlBeen);
                          //加载更多


                    }
                });
    }

    @Override
    public String getSelectedAreaID() {
        String selectedArea_ID = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDAREA_ID, "");

        if (selectedArea_ID == null || selectedArea_ID.equals("")) {
            toast( "请选择建筑");
            activity.openRight();
            return "";

        } else {
            activity.startRefresh();
            return  selectedArea_ID;
        }
    }

    @Override
    public void rxUnsubscribe() {
        subscription.unsubscribe();

    }

    public void toast(String  msg){
        ToastUtil.showShort(MyApplication.getContext(),msg);
    }
}
