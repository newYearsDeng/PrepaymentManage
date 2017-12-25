package com.northmeter.prepaymentmanage.ui.fragments.model;

import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.util.net.HttpData;
import com.northmeter.prepaymentmanage.util.net.LoadDataListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Lht
 * on 2016/12/19.
 * des:
 */
public class SelectFragmentModel {
    public  void getAreaInfo(final LoadDataListener listener){
        HttpData.getInstance().getAreaInfo(new Subscriber<List<RequestBuilding>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.LoadFail(setToastMsg(e));
            }

            @Override
            public void onNext(List<RequestBuilding> requestBuildings) {
                   listener.LoadSucess(requestBuildings);
            }
        });
    }

    private String setToastMsg(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            return  "网络连接超时，请检查网络";
        } else if (e instanceof ConnectException) {
            return "无法连接到服务器，请检查网络";
        }
        return "未知错误 ，请联系开发人员";
    }



    public void  getSonInfo(String areaId, String key, final LoadDataListener listener){
         HttpData.getInstance().getSonInfo(areaId, key, new Subscriber<List<RequestBuilding>>() {
             @Override
             public void onCompleted() {

             }

             @Override
             public void onError(Throwable e) {
                    listener.LoadFail(setToastMsg(e));
             }

             @Override
             public void onNext(List<RequestBuilding> requestBuildings) {

                    listener.LoadSucess(requestBuildings);
             }
         });
    }
}
