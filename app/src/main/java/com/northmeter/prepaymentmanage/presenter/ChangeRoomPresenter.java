package com.northmeter.prepaymentmanage.presenter;

import com.northmeter.prepaymentmanage.model.RequestResult;
import com.northmeter.prepaymentmanage.presenter.i.IChangeRoomPresenter;
import com.northmeter.prepaymentmanage.ui.i.IChangeRoom;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.net.RetrofitHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zz
 * @time 2016/11/18 9:02
 * @des 改变绑定房间的presenter
 */
public class ChangeRoomPresenter implements IChangeRoomPresenter {
    private final IChangeRoom mChangeRoomView;
    private Subscription mSubscribe;

    public ChangeRoomPresenter(IChangeRoom iChangeRoom) {
        mChangeRoomView = iChangeRoom;
    }

    @Override
    public void unbindRoom() {
        //从sp取出loginname
        String getSpId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String loginName = AES.decrypt(getSpId, Contants.DECRYPT_ID_KEY);
        LoggerUtil.d(loginName);

        mSubscribe = RetrofitHelper
                .getApiService()
                .removeRoomBind(loginName)
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
                            mChangeRoomView.showToast("连接超时，请稍后重试");
                        } else if (e instanceof ConnectException) {
                            mChangeRoomView.showToast("无法连接网络，请检查网络");
                        }
                    }

                    @Override
                    public void onNext(RequestResult requestResult) {
                        String rescode = requestResult.getRESCODE();
                        String resmsg = requestResult.getRESMSG();
                        if ("1".equals(rescode)) {
                            if ("成功".equals(resmsg)) {
                                mChangeRoomView.showToast("解绑房间成功");
                                mChangeRoomView.unbindSucceed();
                            } else {
                                mChangeRoomView.showToast("解绑失败");
                                return;
                            }
                        } else {
                            mChangeRoomView.showToast("接口数据异常，无法解除绑定");
                            return;
                        }
                    }
                });
    }

    @Override
    public void rxUnsubscribe() {
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
        }
    }
}
