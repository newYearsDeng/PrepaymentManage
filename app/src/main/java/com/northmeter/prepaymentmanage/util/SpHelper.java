package com.northmeter.prepaymentmanage.util;

import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class SpHelper {
    public static AlreadyBuilding getFromSp(){
        String school= (String) SharedPreferencesUtil.get(MyApplication.getContext(),Contants.SELECTEDSCHOOL,"");
        String buildingId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDAREA_ID, "");
        String selectedBuilding = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDBUILDING, "");
        String selectedRoom = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDROOM, "");
        String selectedFloor = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDFLOOR, "");

        return  new AlreadyBuilding(buildingId,school,selectedBuilding,selectedFloor,selectedRoom);
    };
}
