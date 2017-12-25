package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.ui.i.IUseQueryFragment;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SpHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class Use_QueryPresenter {
    private IUseQueryFragment fragment;
    private String dateType;
    private Calendar calendar;
    private String selectedBuilding, selectedFloor, selectedRoom,buildingId;
    private String startData, area;
    //默认定位到楼层
    private String postion = "floor";

    public Use_QueryPresenter(IUseQueryFragment fragment, String dateType) {
        this.fragment = fragment;
        this.dateType = dateType;
        initialdata();
    }

    public void toast(String msg) {
        ToastUtil.showShort(MyApplication.getContext(), msg);
    }

    private void initialdata() {
        final AlreadyBuilding fromSp = SpHelper.getFromSp();
        selectedBuilding = fromSp.building;
        selectedFloor = fromSp.floor;
        selectedRoom = fromSp.room;
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
        fragment.setTimeAreaFromSp(startData, area,postion,buildingId);
    }

    private void chooseTime(String postion, String dateType) {
        switch (postion) {
            case "floor":
                switch (dateType) {
                    case "月度":
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.MONTH, -1);
                        String lastMonth = new SimpleDateFormat("yyyy-MM ").format(calendar.getTime());
                        startData = lastMonth;

                        break;
                    case "日度":
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.add(Calendar.DATE, -1);
                        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(calendar1.getTime());
                        startData = yesterday;
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


    public void getNowTimeArea(String dateType, AlreadyBuilding info) {
        if (info.room == null || info.room.equals("")) {
            area = info.building + info.floor;
            postion = "floor";
        } else {
            area = info.building + info.room;
            postion = "room";
        }

        chooseTime(postion, dateType);
        fragment.setTvChooseTimeArea(startData, area,postion,info.Area_ID);

    }

}
