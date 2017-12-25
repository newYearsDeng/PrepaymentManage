package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.ControlTaskBean;
import com.northmeter.prepaymentmanage.presenter.i.IControlTaskResultPresenter;
import com.northmeter.prepaymentmanage.ui.i.IControlTaskResultActivity;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lht on 2016/11/25.
 */
public class ControlTaskResultPresenter implements IControlTaskResultPresenter {
    private Subscription subscription;
    private IControlTaskResultActivity activity;

    public ControlTaskResultPresenter(IControlTaskResultActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getData(String buildingId, String meterType) {
        subscription = RetrofitHelper.getApiService()
                .getControlTaskResult(buildingId, meterType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ControlTaskBean, List<ControlTaskBean.RESPONSEXMLBean>>() {
                    @Override
                    public List<ControlTaskBean.RESPONSEXMLBean> call(ControlTaskBean controlTaskBean) {
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
                .subscribe(new Subscriber<List<ControlTaskBean.RESPONSEXMLBean>>() {
                    @Override
                    public void onCompleted() {
                           activity.stopRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                                activity.stopRefresh();
                    }

                    @Override
                    public void onNext(List<ControlTaskBean.RESPONSEXMLBean> responsexmlBeen) {
                        activity.showData(responsexmlBeen);
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        subscription.unsubscribe();
    }

    public void toast(String msg){
        ToastUtil.showShort(MyApplication.getContext(),msg);
    }
}
