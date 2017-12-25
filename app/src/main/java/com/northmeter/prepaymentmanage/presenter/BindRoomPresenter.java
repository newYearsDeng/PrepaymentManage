package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.Entity.HttpResult;
import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.presenter.i.IBindRoomPresenter;
import com.northmeter.prepaymentmanage.ui.i.IBindRoomActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/18 10:16
 * @des 绑定房间的presenter
 */
public class BindRoomPresenter implements IBindRoomPresenter {
    private final IBindRoomActivity mBindRoomView;
    private String[] areaIds;
    private String[] areaNames;
    private Subscription mSchoolSubscribe;
    private Subscription mSchoolSonSubscribe;

    public BindRoomPresenter(IBindRoomActivity iBindRoomActivity) {
        mBindRoomView = iBindRoomActivity;
    }

    @Override
    public void confirmBindRoom(String roomBuildingId) {
        String spId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String decryptId = AES.decrypt(spId, Contants.DECRYPT_ID_KEY);
        mBindRoomView.showLoading();
        RetrofitHelper.getApiService()
                .againBindRoom(decryptId, roomBuildingId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RequestResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mBindRoomView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mBindRoomView.showToast("无法连接网络，请检查网络是否正常");
                        }
                        mBindRoomView.hideLoading();
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        if ("1".equals(rescode) && "成功".equals(resmsg)) {
                            mBindRoomView.showToast("绑定成功");
                            mBindRoomView.bindSucceed();
                            mBindRoomView.hideLoading();
                        } else {
                            mBindRoomView.showToast("接口数据异常，无法绑定");
                            mBindRoomView.hideLoading();
                            return;
                        }
                    }
                });

    }

    @Override
    public void getSchool() {
        mBindRoomView.buildingClickable(false);
        mSchoolSubscribe = RetrofitHelper
                .getApiService()
                .getAreaInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<RequestBuilding>>>() {
                    @Override
                    public void onCompleted() {
                        //将建筑的数据置空
                        areaIds = null;
                        areaNames = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mBindRoomView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mBindRoomView.showToast("无法连接网络，请检查网络是否正常");
                        }
                        mBindRoomView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(HttpResult<List<RequestBuilding>> requestResult) {
                        String  rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("fu--->" + rescode + "---" + resmsg);
                        if ("1".equals(rescode) && "成功".equals(resmsg)) {
                            int size = requestResult.getRESPONSEXML().size();
                            //根据得到的数据创建数组长度
                            areaIds = new String[size];
                            areaNames = new String[size];
                            //循环添加数据
                            for (int i = 0; i < size; i++) {
                                String area_id = requestResult.getRESPONSEXML().get(i).getArea_ID();
                                String area_name = requestResult.getRESPONSEXML().get(i).getArea_NAME();
                                areaIds[i] = area_id;
                                areaNames[i] = area_name;
                            }
                            mBindRoomView.showSchool(areaIds, areaNames);
                            mBindRoomView.buildingClickable(true);
                        } else {
                            mBindRoomView.showToast("接口数据异常，无法获取到数据");
                            mBindRoomView.buildingClickable(true);
                            return;
                        }
                    }
                });
               /* .subscribe(new Subscriber<RequestResult>() {
                    @Override
                    public void onCompleted() {
                        //将建筑的数据置空
                        areaIds = null;
                        areaNames = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mBindRoomView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mBindRoomView.showToast("无法连接网络，请检查网络是否正常");
                        }
                        mBindRoomView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("fu--->" + rescode + "---" + resmsg);
                        if ("1".equals(rescode) && "成功".equals(resmsg)) {
                            int size = requestResult.getRESPONSEXML().size();
                            //根据得到的数据创建数组长度
                            areaIds = new String[size];
                            areaNames = new String[size];
                            //循环添加数据
                            for (int i = 0; i < size; i++) {
                                String area_id = requestResult.getRESPONSEXML().get(i).getArea_ID();
                                String area_name = requestResult.getRESPONSEXML().get(i).getArea_NAME();
                                areaIds[i] = area_id;
                                areaNames[i] = area_name;
                            }
                            mBindRoomView.showSchool(areaIds, areaNames);
                            mBindRoomView.buildingClickable(true);
                        } else {
                            mBindRoomView.showToast("接口数据异常，无法获取到数据");
                            mBindRoomView.buildingClickable(true);
                            return;
                        }
                    }
                });*/
    }

    @Override
    public void getSchoolSon(final String sonType, final String area_id) {
        mBindRoomView.buildingClickable(false);
        mSchoolSonSubscribe = RetrofitHelper
                .getApiService()
                .getSonInfo(area_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<RequestBuilding>>>() {
                    @Override
                    public void onCompleted() {
                        //将建筑的数据置空
                        areaIds = null;
                        areaNames = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mBindRoomView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mBindRoomView.showToast("无法连接网络，请检查网络是否正常");
                        }
                        mBindRoomView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(HttpResult<List<RequestBuilding>> requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("son--->" + rescode + "---" + resmsg);
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                //数据成功
                                int size = requestResult.getRESPONSEXML().size();
                                //根据得到的数据创建数组长度
                                areaIds = new String[size];
                                areaNames = new String[size];
                                //循环添加数据
                                for (int i = 0; i < size; i++) {
                                    String area_id = requestResult.getRESPONSEXML().get(i).getArea_ID();
                                    String area_name = requestResult.getRESPONSEXML().get(i).getArea_NAME();
                                    areaIds[i] = area_id;
                                    areaNames[i] = area_name;
                                }
                                if ("building".equals(sonType)) {
                                    mBindRoomView.showBuilding(areaIds, areaNames);
                                } else if ("floor".equals(sonType)) {
                                    mBindRoomView.showFloor(areaIds, areaNames);
                                } else if ("room".equals(sonType)) {
                                    mBindRoomView.showRoom(areaIds, areaNames);
                                }
                                mBindRoomView.buildingClickable(true);
                            } else if ("缺少参数".equals(resmsg)) {
                                mBindRoomView.showToast("请从上到下按顺序选择房间");
                                mBindRoomView.buildingClickable(true);
                                return;
                            } else if ("无下级子信息".equals(resmsg)) {
                                //到建筑信息的底部了、把最底层的buildingid传回去
                                mBindRoomView.noHaveDownInfo(area_id);
                                mBindRoomView.showToast("没有再下一级的信息了");
                                mBindRoomView.buildingClickable(true);
                                return;
                            } else {
                                mBindRoomView.showToast("数据有误，请重试");
                                mBindRoomView.buildingClickable(true);
                                return;
                            }
                        } else {
                            mBindRoomView.showToast("获取失败，请重试");
                            mBindRoomView.buildingClickable(true);
                            return;
                        }
                    }
                });
                /*.subscribe(new Subscriber<RequestResult>() {
                    @Override
                    public void onCompleted() {
                        //将建筑的数据置空
                        areaIds = null;
                        areaNames = null;
                    }

                    @Override
                    public void onError(Throwable e) {
                        LoggerUtil.d(e.toString());
                        if (e instanceof SocketTimeoutException) {
                            mBindRoomView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mBindRoomView.showToast("无法连接网络，请检查网络是否正常");
                        }
                        mBindRoomView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("son--->" + rescode + "---" + resmsg);
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                //数据成功
                                int size = requestResult.getRESPONSEXML().size();
                                //根据得到的数据创建数组长度
                                areaIds = new String[size];
                                areaNames = new String[size];
                                //循环添加数据
                                for (int i = 0; i < size; i++) {
                                    String area_id = requestResult.getRESPONSEXML().get(i).getArea_ID();
                                    String area_name = requestResult.getRESPONSEXML().get(i).getArea_NAME();
                                    areaIds[i] = area_id;
                                    areaNames[i] = area_name;
                                }
                                if ("building".equals(sonType)) {
                                    mBindRoomView.showBuilding(areaIds, areaNames);
                                } else if ("floor".equals(sonType)) {
                                    mBindRoomView.showFloor(areaIds, areaNames);
                                } else if ("room".equals(sonType)) {
                                    mBindRoomView.showRoom(areaIds, areaNames);
                                }
                                mBindRoomView.buildingClickable(true);
                            } else if ("缺少参数".equals(resmsg)) {
                                mBindRoomView.showToast("请从上到下按顺序选择房间");
                                mBindRoomView.buildingClickable(true);
                                return;
                            } else if ("无下级子信息".equals(resmsg)) {
                                //到建筑信息的底部了、把最底层的buildingid传回去
                                mBindRoomView.noHaveDownInfo(area_id);
                                mBindRoomView.showToast("没有再下一级的信息了");
                                mBindRoomView.buildingClickable(true);
                                return;
                            } else {
                                mBindRoomView.showToast("数据有误，请重试");
                                mBindRoomView.buildingClickable(true);
                                return;
                            }
                        } else {
                            mBindRoomView.showToast("获取失败，请重试");
                            mBindRoomView.buildingClickable(true);
                            return;
                        }
                    }
                });*/
    }

    @Override
    public void rxUnsubscribe() {
        if (mSchoolSubscribe != null) {
            mSchoolSubscribe.unsubscribe();
        }
        if (mSchoolSonSubscribe != null) {
            mSchoolSonSubscribe.unsubscribe();
        }
    }
}
