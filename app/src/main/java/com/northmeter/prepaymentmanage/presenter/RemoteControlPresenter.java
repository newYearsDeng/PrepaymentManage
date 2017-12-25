package com.northmeter.prepaymentmanage.presenter;

import android.util.Log;

import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.model.NewIdBean;
import com.northmeter.prepaymentmanage.presenter.i.IRemoteControlPresenter;
import com.northmeter.prepaymentmanage.ui.i.IRemoteControlActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.net.ApiService;
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
 * Created by lht on 2016/11/24.
 */
public class RemoteControlPresenter implements IRemoteControlPresenter {
    private static final java.lang.String TAG = IRemoteControlPresenter.class.getSimpleName();
    private Subscription subscription;
    private IRemoteControlActivity activity;
    private String normal, count, accuracy;
    private Subscription[] subscriptions;
    private boolean over;
    public RemoteControlPresenter(IRemoteControlActivity activity) {
        this.activity = activity;
        String selectedBuilding = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDBUILDING, "");
        String selectedRoom = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDROOM, "");
        String selectedFloor = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDFLOOR, "");
        String selectedArea_ID = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDAREA_ID, "");
        String getSpId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String decryptId = AES.decrypt(getSpId, Contants.DECRYPT_ID_KEY);
        if(selectedArea_ID==null||selectedArea_ID.equals("")){
            toast("请选择建筑");
            activity.openRight();
            return;
        }

        String area;
        if(selectedRoom==null||selectedRoom.equals("")||selectedRoom.equals("null")){
            area=selectedBuilding+selectedFloor;
        }else{
            area=selectedBuilding+selectedRoom;
        }
        activity.showArea(selectedArea_ID,area,decryptId);
    }
    @Override
    public void getData(String type, String selectedArea_ID,String controlType ) {
        Log.i("LHT",TAG+" type "+type);
        Log.i("LHT",TAG+" selectedArea_ID "+selectedArea_ID);
        Log.i("LHT",TAG+" controlType "+controlType);
        Log.i("LHT","---------------------------------");
        activity.showLoading();
        subscription= RetrofitHelper.getApiService()
                .getMeterInfo(selectedArea_ID, type, controlType, "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<EquipmentBean, List<EquipmentBean.RESPONSEXMLBean>>() {
                    @Override
                    public List<EquipmentBean.RESPONSEXMLBean> call(EquipmentBean equipmentBean) {
                        String rescode = equipmentBean.getRESCODE();
                        String resmsg = equipmentBean.getRESMSG();
                        if (rescode.equals("1")) {
                            if (resmsg.equals("成功")) {
                                count = equipmentBean.getCOUNT();
                                accuracy = equipmentBean.getACCURACY();
                                normal = equipmentBean.getNORMAL();
                                return equipmentBean.getRESPONSEXML();
                            } else {
                                toast(resmsg);
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
                        //lvEs.setOnItemClickListener(listener);
                       // toast("加载完成");
                        activity.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.hideLoading();
                        if (e instanceof SocketTimeoutException) {
                            toast("网络连接超时，请检查网络");
                        } else if (e instanceof ConnectException) {
                            toast("无法连接到服务器，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen) {
                        activity.showData(count, accuracy, normal, responsexmlBeen);
                    }
                });
    }


    @Override
    public void rxUnsubscribe() {
        subscription.unsubscribe();
        if(subscriptions!=null){
            for (Subscription subscription1 : subscriptions) {
                subscription1.unsubscribe();
            }
        }

    }

    @Override
    public void goControlMeter(final List<String> datas, String ControlType, String OPNAME) {

        final ApiService apiService = RetrofitHelper.getApiService();
        subscriptions=new Subscription[datas.size()];

        for (int i=0;i<datas.size();i++) {
            if(i==datas.size()-1){
                over=true;
            }
            subscriptions[i]=apiService.getControlMeterNewId(datas.get(i),ControlType,OPNAME)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<NewIdBean, String>() {
                        @Override
                        public String call(NewIdBean newIdBean) {
                            final String rescode = newIdBean.getRESCODE();
                            final String resmsg = newIdBean.getRESMSG();

                            if (rescode.equals("1")) {
                                if (resmsg.equals("成功")) {
                                    return newIdBean.getRESPONSEXML().get(0).getNEWID();
                                } else {
                                    toast(resmsg);
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
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            if(over){
                               toast("命令发送完毕");
                                over=!over;
                                activity.cancleSelctedMode();
                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            if (e instanceof SocketTimeoutException) {
                                toast("网络连接超时，请检查网络");
                            } else if (e instanceof ConnectException) {
                                toast("无法连接到服务器，请检查网络");
                            }
                            activity.cancleSelctedMode();
                        }
                        @Override
                        public void onNext(String s) {
                          Log.i("LHT","执行" +s);
                        }
                    });
        }
    }


    public void toast(String  msg){
        ToastUtil.showShort(MyApplication.getContext(),msg);
    }

}
