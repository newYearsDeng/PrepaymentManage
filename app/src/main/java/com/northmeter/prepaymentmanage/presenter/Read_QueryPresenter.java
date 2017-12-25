package com.northmeter.prepaymentmanage.presenter;

import android.util.Log;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.ReadQueryAdapter;
import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.presenter.i.ICommonQueryPresenter;
import com.northmeter.prepaymentmanage.ui.i.IQueryFragment;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SpHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
 * Created by lht on 2016/11/17.
 * des:查询Presenter
 */
public class Read_QueryPresenter implements ICommonQueryPresenter {
    private IQueryFragment fragment;
    private String dateType;
    private String[] titles = new String[5];
    private ReadQueryAdapter cAdpter;
    private Calendar calendar;
    private String selectedBuilding, selectedFloor, selectedRoom;
    private String meterType, buildingId, startData, area;
    private Subscription subscription;
    //默认定位到楼层
    private String postion = "floor";

    public Read_QueryPresenter(IQueryFragment fragment, String dateType) {

        this.fragment = fragment;
        this.dateType = dateType;
    }


    public void getData(final int xRefreshType, String buildingId, final String dateType, String startDate, String meterType, int pageIndex, int pageSize) {

        Log.i("LHT", "building " + buildingId);
        Log.i("LHT", "dateType " + dateType);
        Log.i("LHT", "startDate " + startDate);
        Log.i("LHT", "meterType " + meterType);
        Log.i("LHT", "pageIndex " + pageIndex);
        Log.i("LHT", "pageSize " + pageSize);
        Log.i("LHT", "-------------------------------------------");


        // fragment.startRefresh();
      subscription = RetrofitHelper.getApiService()
                .getBuildMeterReadData(buildingId, dateType, startDate, meterType, pageIndex, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<BuildMeterReadData, List<BuildMeterReadData.RESPONSEXMLBean>>() {
                    @Override
                    public List<BuildMeterReadData.RESPONSEXMLBean> call(BuildMeterReadData buildMeterReadData) {

                        String rescode = buildMeterReadData.getRESCODE();
                        String resmsg = buildMeterReadData.getRESMSG();
                        Log.i("LHT", dateType + "-->" + rescode + "  " + resmsg);
                        Log.i("LHT", "--------------------------");
                        if (rescode.equals("1")) {
                            if (resmsg.equals("成功")) {
                                return buildMeterReadData.getRESPONSEXML();

                            } else {
                                toast("已加载全部");
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
                }).subscribe(new Subscriber<List<BuildMeterReadData.RESPONSEXMLBean>>() {
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
                    public void onNext(List<BuildMeterReadData.RESPONSEXMLBean> responsexmlBeen) {

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

    @Override
    public void toast(String msg) {
        ToastUtil.showShort(MyApplication.getContext(), msg);
    }

    @Override
    public void initialdata(String meterType) {

       this.meterType=meterType;

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
        chooseTime(postion, dateType);

        titles = MyApplication.getContext().getResources().getStringArray(R.array.commonfragment_title_0);
        cAdpter = new ReadQueryAdapter(new ArrayList<BuildMeterReadData.RESPONSEXMLBean>());

        fragment.showinitialdata(startData, postion, area, buildingId, titles, cAdpter);
    }

    private void chooseTime(String postion, String dateType) {
        switch (postion) {
            case "floor":
                switch (dateType) {
                    case "月度":
                            startData = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
                        break;
                    case "日度":
                            //startData = year + "-" + month + "-" + day;
                            startData = new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime());

                        break;
                }
                break;
            case "room":
                switch (dateType) {
                    case "月度":
                        startData = calendar.get(Calendar.YEAR) + "";
                        break;
                    case "日度":
                        startData = new SimpleDateFormat("yyyy-MM").format(calendar.getTime());
                        break;
                }
                break;
        }
    }

    @Override
    public void getNowTimeArea(String dateType, AlreadyBuilding info) {
        if (info.room == null || info.room.equals("")) {
            area = info.building + info.floor;
            postion = "floor";
        } else {
            area = info.building + info.room;
            postion = "room";
        }

        chooseTime(postion, dateType);
        fragment.setTvChooseTimeArea(startData, area, postion, info.Area_ID);

    }


}
