package com.northmeter.prepaymentmanage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.WXPayData;
import com.northmeter.prepaymentmanage.presenter.DevicesQueryPresenter;
import com.northmeter.prepaymentmanage.ui.i.IDevicesQueryActivity;
import com.northmeter.prepaymentmanage.ui.widget.MyDialog_pay;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.wxapi.WXPayActivity;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/08 9:39
 * @des 设备查询
 */
public class DevicesQueryActivity extends BaseActivity implements IDevicesQueryActivity {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_devices_query_prepayment_balance)
    TextView mTvPrepaymentBalance;
    @BindView(R.id.tv_devices_query_buzhu_balance)
    TextView mTvBuzhuBalance;
    @BindView(R.id.tv_devices_query_total_balance)
    TextView mTvTotalBalance;
    @BindView(R.id.tv_devices_query_total_use)
    TextView mTvTotalUse;
    @BindView(R.id.tv_devices_query_state)
    TextView mTvState;
    @BindView(R.id.btn_devices_query_bills)
    Button mBtnBills;
    @BindView(R.id.tv_devices_query_buzhu_title)
    TextView mTvBuzhuTitle;
    @BindView(R.id.tv_devices_query_total_use_title)
    TextView mTvTotalUseTitle;
    @BindView(R.id.tv_devices_query_state_title)
    TextView mTvStateTitle;
    @BindView(R.id.tv_devices_query_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.rl_layout_devices_query_prepayment_balance)
    RelativeLayout mRlLayout;
    @BindView(R.id.spin_kit_devices_query_loading)
    SpinKitView mSpinKitLoading;
    @BindView(R.id.et_devices_query_pay_values)
    EditText etDevicesQueryPayValues;
    @BindView(R.id.btn_devices_query_confirm_pay)
    Button btnDevicesQueryConfirmPay;
    private DevicesQueryPresenter mPresenter;
    private String mMeterType;
    private String mComaddress;
    private String values = "";

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_devices_query;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api= WXAPIFactory.createWXAPI(this,Contants.APP_ID_WX,true);
        // 将该app注册到微信
        api.registerApp(Contants.APP_ID_WX);

        mMeterType = getIntent().getStringExtra(Contants.DEVICES_QUERY_METER_TYPE_INTENT_EXTRA).substring(0, 1);
        mComaddress = getIntent().getStringExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA);
        if ("水".equals(mMeterType)) {
            mRlLayout.setVisibility(View.GONE);
        }

        mTvTitleTitlebar.setText(mMeterType + "表查询");
        mTvBuzhuTitle.setText("水".equals(mMeterType) ? "剩余补助" + mMeterType + "量(kwh)" : "补助" + mMeterType + "余额(元)");
        mTvTotalUseTitle.setText("水".equals(mMeterType) ? "总用" + mMeterType + "量(m³)" : "总用" + mMeterType + "量(kwh)");
        mTvStateTitle.setText(mMeterType + "表状态");
        mBtnBills.setText(mMeterType + "费账单");


        mPresenter = new DevicesQueryPresenter(this);
        mPresenter.getData(mMeterType, mComaddress);
    }


    @OnClick({R.id.ll_back_titlebar,
            R.id.btn_devices_query_recharge_record,
            R.id.btn_devices_query_bills,
            R.id.et_devices_query_pay_values,
            R.id.btn_devices_query_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                mPresenter.rxUnsubscribe();
                break;
            case R.id.btn_devices_query_recharge_record:
                //充值流水
                Intent topupIntent = new Intent(this, TopupWater.class);
                topupIntent.putExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA, mComaddress);
                startActivity(topupIntent);
                break;
            case R.id.btn_devices_query_bills:
                //用能账单
                Intent billsIntent = new Intent(MyApplication.getContext(), BillsAcvtivity.class);
                billsIntent.putExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA, mComaddress);
                billsIntent.putExtra(Contants.DEVICES_QUERY_METER_TYPE_INTENT_EXTRA, mMeterType);
                startActivity(billsIntent);
                break;
            case R.id.et_devices_query_pay_values:
                new MyDialog_pay(this, R.style.MyDialog1, new MyDialog_pay.OnValueChooseListener() {
                    @Override
                    public void chooseVaules(String data) {
                        values = data;

                        if (values.equals("-1")) {
                            etDevicesQueryPayValues.setOnClickListener(null);
                            values = "";
                            etDevicesQueryPayValues.setFocusable(true);
                            etDevicesQueryPayValues.requestFocus();
                            etDevicesQueryPayValues.setFocusableInTouchMode(true);

                        }
                        etDevicesQueryPayValues.setText(values);
                    }
                }).show();
                break;
            case R.id.btn_devices_query_confirm_pay:
                final String trim = etDevicesQueryPayValues.getText().toString().trim();
                if (trim.equals("") || trim == null) {
                    ToastUtil.showShort(this, "充值金额不能为空...");
                    return;
                }
                final Float valueOf = Float.valueOf(trim);
                if (valueOf >= 0) {
                    ToastUtil.showShort(this, "开始充值，请稍等...");
                    //开始微信支付

                    Intent intent = new Intent(MyApplication.getContext(), WXPayActivity.class);
                    intent.putExtra("Money",etDevicesQueryPayValues.getText().toString());
                    startActivity(intent);


                } else {
                    ToastUtil.showShort(this, "请输入正确金额...");
                }
                break;
        }
    }

    //核心支付方法
    private void sendPayReq(WXPayData info) {
        api = WXAPIFactory.createWXAPI(this, info.getAppid());
        PayReq req = new PayReq();
        req.appId = info.getAppid();
        req.partnerId = info.getPartnerid();
        req.prepayId = info.getPrepayid();//预支付id
        req.nonceStr = info.getNoncestr();//32位内的随机串，防重发
        req.timeStamp = String.valueOf(info.getTimestamp());//时间戳，为 1970 年 1 月 1 日 00:00 到请求发起时间的秒数
        req.packageValue = info.getPackage1();
        req.sign = info.getApp_signature();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    @Override
    public void showData(String yffye, String bzye, String zye, String zyl, String bzt, String updateTime) {
        if ("".equals(bzye)) {
            bzye = "0.00";
        }
        mTvTotalBalance.setText(zye);
        if ("水".equals(mMeterType)) {
            mTvBuzhuBalance.setText(bzye);
//            mTvTotalUse.setText(zyl + "㎥");
            mTvTotalUse.setText(zyl);
        } else {
            mRlLayout.setVisibility(View.VISIBLE);
            mTvBuzhuBalance.setText(bzye);
            mTvPrepaymentBalance.setText(yffye);
            mTvTotalUse.setText(zyl);
        }
        mTvState.setText(bzt);
        mTvUpdateTime.setText(updateTime);
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showLoading() {
        mSpinKitLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKitLoading.setVisibility(View.GONE);
    }

    @Override
    public void showMeterState(int state, String msg) {

    }

    @Override
    public void getStateTimeout() {

    }


}
