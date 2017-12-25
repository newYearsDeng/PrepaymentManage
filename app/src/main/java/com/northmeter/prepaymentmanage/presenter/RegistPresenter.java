package com.northmeter.prepaymentmanage.presenter;

import android.text.TextUtils;

import com.northmeter.prepaymentmanage.model.Entity.HttpResult;
import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.presenter.i.IRegistPresenter;
import com.northmeter.prepaymentmanage.ui.i.IRegistActivity;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;

import java.net.SocketTimeoutException;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/11 9:39
 * @des 注册的presenter
 */
public class RegistPresenter implements IRegistPresenter {
    private final IRegistActivity mRegistView;

    private String[] areaIds;
    private String[] areaNames;
    Subscription mRegistSubscribe;
    Subscription mFuSubscribe;
    Subscription mZiSubscribe;

    public RegistPresenter(IRegistActivity iRegistActivity) {
        mRegistView = iRegistActivity;
    }

    @Override
    public void confirmRegist(String phone, String userName, String intoSchoolDate,
                              String studentNum, String buildingID, String inputPwd,
                              String confirmPwd, String problemOne, String answerOne,
                              String problemTwo, String answerTwo, String problemThree,
                              String answerThree) {
        //正则验证是否是手机格式
        String telRegex = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(14[5,7]))\\d{8}$";
        if (TextUtils.isEmpty(phone)) {
            mRegistView.showToast("手机号码不能为空");
            return;
        } else if (!phone.matches(telRegex)) {
            mRegistView.showToast("手机号码不正确，请检查");
            return;
        } else if (TextUtils.isEmpty(userName)) {
            mRegistView.showToast("用户姓名不能为空");
            return;
        } else if (TextUtils.isEmpty(intoSchoolDate)) {
            mRegistView.showToast("入校时间不能为空");
            return;
        }
//        else if (TextUtils.isEmpty(studentNum)) {
//            mRegist Activity.showToast("学号不能为空");
//            return;
//        }
        else if (TextUtils.isEmpty(buildingID)) {
            mRegistView.showToast("宿舍不能为空");
            return;
        } else if (TextUtils.isEmpty(inputPwd) || TextUtils.isEmpty(confirmPwd)) {
            mRegistView.showToast("密码不能为空");
            return;

        } else if(inputPwd.length()>15||inputPwd.length()<6){
            mRegistView.showToast("密码必须为6~15位");
            return;

        }else if (!confirmPwd.equals(inputPwd)) {
            mRegistView.showToast("两次密码不一致");
            return;
        } else if (TextUtils.isEmpty(problemOne) && TextUtils.isEmpty(problemTwo) && TextUtils.isEmpty(problemThree)) {
            mRegistView.showProblemDialog();
            return;
        } else if ((!TextUtils.isEmpty(problemOne) && TextUtils.isEmpty(answerOne)) ||
                (!TextUtils.isEmpty(problemTwo) && TextUtils.isEmpty(answerTwo)) ||
                (!TextUtils.isEmpty(problemThree) && TextUtils.isEmpty(answerThree))) {
            mRegistView.showToast("密保答案不能为空");
            return;
        }

        //加载动画
        mRegistView.showLoading();
        mRegistView.btnClickable(false);

        mRegistSubscribe = RetrofitHelper
                .getApiService()
                .postRegist(phone, userName, confirmPwd, intoSchoolDate, buildingID, problemOne, answerOne, problemTwo, answerTwo, problemThree, answerThree)
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
                            mRegistView.showToast("网络连接超时，请检查网络");
                        } else {
                            mRegistView.showToast("数据出错");
                        }
                        mRegistView.hideLoading();
                        mRegistView.btnClickable(true);
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {

                        List<RequestResult.RESPONSEXMLBean> responsexml = requestResult.getRESPONSEXML();
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
//                        LoggerUtil.d("注册：" + rescode + "---" + resmsg + "---" + responsexml);
                        if ("0".equals(rescode)) {
                            //返回值为0
                            if ("接口异常".equals(resmsg)) {
                                mRegistView.showToast("接口数据异常，请稍后重试");
                                mRegistView.hideLoading();
                                mRegistView.btnClickable(true);
                                return;
                            } else {
                                mRegistView.showToast("数据获取失败，请稍后重试");
                                mRegistView.hideLoading();
                                mRegistView.btnClickable(true);
                                return;
                            }
                        } else {
                            //返回值为1
                            if ("成功".equals(resmsg)) {
                                mRegistView.showToast("注册成功");
                                mRegistView.finishView();
                                mRegistView.hideLoading();
                                mRegistView.btnClickable(true);
                            } else if ("手机号码已经注册过".equals(resmsg)) {
                                mRegistView.showToast("手机号码已经注册过");
                                mRegistView.hideLoading();
                                mRegistView.btnClickable(true);
                                return;
                            } else {
                                mRegistView.showToast("注册失败");
                                mRegistView.hideLoading();
                                mRegistView.btnClickable(true);
                                return;
                            }
                        }

                    }
                });


    }

    @Override
    public void getSchoolBuilding() {
        mRegistView.buildingClickable(false);
        mFuSubscribe = RetrofitHelper
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
                            mRegistView.showToast("网络连接超时，请检查网络");
                        } else {
                            mRegistView.showToast("数据出错");
                        }
                        mRegistView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(HttpResult<List<RequestBuilding>> requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("fu" + rescode + "---" + resmsg);
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
                            mRegistView.showSchoolBuildingDialog(areaIds, areaNames);
                        } else {
                            mRegistView.showToast("获取失败，请重试");
//                            mRegist Activity.showBuildingName ();
                            mRegistView.buildingClickable(true);
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
                            mRegistView.showToast("网络连接超时，请检查网络");
                        } else {
                            mRegistView.showToast("数据出错");
                        }
                        mRegistView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("fu" + rescode + "---" + resmsg);
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
                            mRegistView.showSchoolBuildingDialog(areaIds, areaNames);
                        } else {
                            mRegistView.showToast("获取失败，请重试");
//                            mRegist Activity.showBuildingName ();
                            mRegistView.buildingClickable(true);
                            return;
                        }
                    }
                });*/
    }

    @Override
    public void getSchoolSonBuilding(final String area_id) {
        mZiSubscribe = RetrofitHelper
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
                            mRegistView.showToast("网络连接超时，请检查网络");
                        } else {
                            mRegistView.showToast("数据出错");
                        }
                        mRegistView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(HttpResult<List<RequestBuilding>> requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("zi" + rescode + "---" + resmsg);
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
                                mRegistView.showSchoolBuildingDialog(areaIds, areaNames);
                                mRegistView.buildingClickable(true);

                            } else if ("无下级子信息".equals(resmsg)) {
                                //到建筑信息的底部了、把最底层的buildingid传回去
                                mRegistView.showBuildingName(area_id);
                                mRegistView.buildingClickable(true);

                            } else {
                                mRegistView.showToast("数据有误，请重试");
//                                mRegist Activity.showBuildingName ();
                                mRegistView.buildingClickable(true);

                                return;
                            }
                        } else {
                            mRegistView.showToast("获取失败，请重试");
//                            mRegist Activity.showBuildingName ();
                            mRegistView.buildingClickable(true);

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
                            mRegistView.showToast("网络连接超时，请检查网络");
                        } else {
                            mRegistView.showToast("数据出错");
                        }
                        mRegistView.buildingClickable(true);
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        LoggerUtil.d("zi" + rescode + "---" + resmsg);
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
                                mRegistView.showSchoolBuildingDialog(areaIds, areaNames);
                                mRegistView.buildingClickable(true);

                            } else if ("无下级子信息".equals(resmsg)) {
                                //到建筑信息的底部了、把最底层的buildingid传回去
                                mRegistView.showBuildingName(area_id);
                                mRegistView.buildingClickable(true);

                            } else {
                                mRegistView.showToast("数据有误，请重试");
//                                mRegist Activity.showBuildingName ();
                                mRegistView.buildingClickable(true);

                                return;
                            }
                        } else {
                            mRegistView.showToast("获取失败，请重试");
//                            mRegist Activity.showBuildingName ();
                            mRegistView.buildingClickable(true);

                            return;
                        }
                    }
                });*/
    }

    @Override
    public void RxUnsubscribe() {
        if (mRegistSubscribe != null) {
            mRegistSubscribe.unsubscribe();
        }
        if (mFuSubscribe != null) {
            mFuSubscribe.unsubscribe();
        }
        if (mZiSubscribe != null) {
            mZiSubscribe.unsubscribe();
        }
    }

}
