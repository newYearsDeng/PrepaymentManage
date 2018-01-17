package com.northmeter.prepaymentmanage.ui.light.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.ui.light.i.IUserLightControlShow;
import com.northmeter.prepaymentmanage.ui.light.presenter.UserLightControlPresenter;
import com.northmeter.prepaymentmanage.ui.widget.MyDialogWait;
import com.northmeter.prepaymentmanage.ui.widget.MyDialog_interface;
import com.northmeter.prepaymentmanage.util.Contants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dyd on 2017/12/29.
 */
public class UserLightControlActivity extends BaseActivity implements IUserLightControlShow {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.image_light_control_all)
    CheckBox imageLightControlAll;
    @BindView(R.id.image_light_control_1)
    CheckBox imageLightControl1;
    @BindView(R.id.image_light_control_2)
    CheckBox imageLightControl2;
    @BindView(R.id.image_light_control_3)
    CheckBox imageLightControl3;
    @BindView(R.id.image_light_control_4)
    CheckBox imageLightControl4;
    @BindView(R.id.spin_kit_devices_query_loading)
    SpinKitView spinKitTopupLoading;

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
    @BindView(R.id.tv_devices_query_update_time)
    TextView mTvUpdateTime;
    @BindView(R.id.rl_layout_devices_query_prepayment_balance)
    RelativeLayout mRlLayout;
    @BindView(R.id.tv_title_right_titlebar)
    TextView tv_title_right_titlebar;

    private String mMeterType;
    private String mComaddress;

    private UserLightControlPresenter userLightControlPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_light_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("灯控管理");
        tv_title_right_titlebar.setVisibility(View.VISIBLE);
        tv_title_right_titlebar.setText("抄表");
        mMeterType = getIntent().getStringExtra(Contants.DEVICES_QUERY_METER_TYPE_INTENT_EXTRA).substring(0, 1);
        mComaddress = getIntent().getStringExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA);
        userLightControlPresenter = new UserLightControlPresenter(this);
        userLightControlPresenter.getData(mMeterType,mComaddress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_back_titlebar, R.id.image_light_control_all, R.id.image_light_control_1,
            R.id.image_light_control_2, R.id.image_light_control_3, R.id.image_light_control_4,
            R.id.tv_title_right_titlebar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.tv_title_right_titlebar:
                userLightControlPresenter.readingMeter(mComaddress,mMeterType);
                break;
            case R.id.image_light_control_all:

                break;
            case R.id.image_light_control_1:
                break;
            case R.id.image_light_control_2:
                break;
            case R.id.image_light_control_3:
                break;
            case R.id.image_light_control_4:
                break;
        }
    }

    @Override
    public void showData(String yffye, String bzye, String zye, String zyl, String bzt, String updateTime) {
        if ("".equals(bzye)) {
            bzye = "0.00";
        }
        mTvTotalBalance.setText(zye);
        mRlLayout.setVisibility(View.VISIBLE);
        mTvBuzhuBalance.setText(bzye);
        mTvPrepaymentBalance.setText(yffye);
        mTvTotalUse.setText(zyl);
        mTvState.setText(bzt);
        mTvUpdateTime.setText(updateTime);
    }

    @Override
    public void showToast(String toastStr) {

    }

    @Override
    public void showLoading() {
        spinKitTopupLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if (spinKitTopupLoading == null) {
            return;
        }
        spinKitTopupLoading.setVisibility(View.GONE);

    }

    @Override
    public void showMeterState(int state, String msg) {

    }

    @Override
    public void getStateTimeout() {
        final MyDialogWait myDialogWait = new MyDialogWait(this, R.style.MyDialog1);
        myDialogWait.init(new MyDialog_interface() {
            @Override
            protected void onMyno() {
                myDialogWait.dismiss();
                finish();
            }

            @Override
            protected void onMyyes() {
                myDialogWait.dismiss();
            }
        });
        myDialogWait.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        userLightControlPresenter.rxUnsubscribe();
    }
}
