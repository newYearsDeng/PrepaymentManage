package com.northmeter.prepaymentmanage.ui.GateLock.presenter;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.ReadQueryAdapter;
import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.ControlTaskBean;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.ui.GateLock.i.IRead_DayLogPresenter;
import com.northmeter.prepaymentmanage.ui.GateLock.i.IRead_DayLogShow;
import com.northmeter.prepaymentmanage.ui.GateLock.model.ReadDayLogBean;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SpHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dyd on 2017/7/1.
 */
public class Read_DayLogPresenter implements IRead_DayLogPresenter {
    private String selectedBuilding, selectedFloor, selectedRoom;
    private String buildingId, startData, area;
    private String postion = "floor";
    private Calendar calendar;
    private Subscription subscription;
    private IRead_DayLogShow iRead_dayLogShow;

    public Read_DayLogPresenter(IRead_DayLogShow iRead_dayLogShow){
        this.iRead_dayLogShow = iRead_dayLogShow;
    }


    @Override
    public void getData(final int xRefreshType, String buildingId, String DateType, String StartDate, String METERTYPE, int PageIndex, int PageSize) {

        subscription = RetrofitHelper.getApiService()
                .getUnlockRecordBuilding(buildingId, DateType, StartDate, METERTYPE, PageIndex, PageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ReadDayLogBean, List<ReadDayLogBean.RESPONSEXMLBean>>() {
                    @Override
                    public List<ReadDayLogBean.RESPONSEXMLBean> call(ReadDayLogBean controlTaskBean) {
                        final String resmsg = controlTaskBean.getRESMSG();
                        final String rescode = controlTaskBean.getRESCODE();
                        if(rescode.equals("1")){
                            if(resmsg.equals("成功")){
                                return controlTaskBean.getRESPONSEXML();
                            }else{
                                toast(resmsg);
                                return null;
                            }
                        }else if(rescode.equals("0")){
                            toast("数据访问失败..");
                            return null;
                        }else{
                            toast("网络连接异常，请检查网络..");
                            return null;
                        }
                    }
                })
                .subscribe(new Subscriber<List<ReadDayLogBean.RESPONSEXMLBean>>() {
                    @Override
                    public void onCompleted() {
                        iRead_dayLogShow.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iRead_dayLogShow.stopRefresh();
                    }

                    @Override
                    public void onNext(List<ReadDayLogBean.RESPONSEXMLBean> responsexmlBeen) {
                        iRead_dayLogShow.showData(xRefreshType,responsexmlBeen);
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        subscription.unsubscribe();
    }

    @Override
    public void initialdata() {
        try{
            final AlreadyBuilding fromSp = SpHelper.getFromSp();
            selectedBuilding=fromSp.building;
            selectedFloor=fromSp.floor;
            selectedRoom=fromSp.room;
            buildingId=fromSp.Area_ID;

            calendar = Calendar.getInstance();



            if (selectedRoom != null && !selectedRoom.equals("null")) {
                postion = "room";

            }
            if (postion.equals("floor")) {
                area = selectedBuilding + selectedFloor;
            } else {
                area = selectedBuilding + selectedRoom;
            }
            //选择显示时间
            startData = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
            iRead_dayLogShow.showinitialdata(startData, postion, area, buildingId);
        }catch(Exception e){
            e.printStackTrace();
        }


    }



    public void toast(String msg){
        ToastUtil.showShort(MyApplication.getContext(),msg);
    }
}
