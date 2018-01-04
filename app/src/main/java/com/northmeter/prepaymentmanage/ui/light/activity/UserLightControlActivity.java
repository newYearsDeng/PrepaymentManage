package com.northmeter.prepaymentmanage.ui.light.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.ui.light.i.IUserLightControlShow;
import com.northmeter.prepaymentmanage.ui.light.presenter.UserLightControlPresenter;
import com.northmeter.prepaymentmanage.ui.widget.MyDialogWait;
import com.northmeter.prepaymentmanage.ui.widget.MyDialog_interface;

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
    @BindView(R.id.spin_kit_topup_loading)
    SpinKitView spinKitTopupLoading;

    private UserLightControlPresenter userLightControlPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_light_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("照明管理");
        userLightControlPresenter = new UserLightControlPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_back_titlebar, R.id.image_light_control_all, R.id.image_light_control_1, R.id.image_light_control_2, R.id.image_light_control_3, R.id.image_light_control_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
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
                //presenter.readingMeter(comaddress, type);
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
