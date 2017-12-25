package com.northmeter.prepaymentmanage.ui.GateLock;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.ui.GateLock.i.IOpenDoorShow;
import com.northmeter.prepaymentmanage.ui.GateLock.presenter.OpenDoorDetailPresenter;
import com.northmeter.prepaymentmanage.ui.widget.MyDialogWait;
import com.northmeter.prepaymentmanage.ui.widget.MyDialog_interface;
import com.northmeter.prepaymentmanage.util.Contants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dyd on 2017/6/30.
 * 门锁控制
 */
public class OpenDoorDetailActivity extends BaseActivity implements IOpenDoorShow, View.OnClickListener {
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_build_name)
    TextView tvBuildName;
    @BindView(R.id.tv_door_state)
    TextView tvDoorState;
    @BindView(R.id.btn_door_open)
    Button btnDoorOpen;
    @BindView(R.id.btn_door_close)
    Button btnDoorClose;
    @BindView(R.id.spin_kit_topup_loading)
    SpinKitView spinKitTopupLoading;
    @BindView(R.id.tv_update_time_ed)
    TextView tvUpdateTimeEd;
    private OpenDoorDetailPresenter openDoorDetailPresenter;

    private EquipmentBean.RESPONSEXMLBean equipment;
    private String type;
    private String comaddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opendoor_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        equipment = (EquipmentBean.RESPONSEXMLBean) getIntent().getSerializableExtra("equipment");
        type = getIntent().getStringExtra(Contants.METERTYPE);
        tvTitleTitlebar.setText(equipment.getBuildingName() + "(" + type + ")");
        comaddress = equipment.getCOMADDRESS();
        //COMADDRESS='888888888888', BuildingName='130', ZYE='0.00', BJZT='', TXZT='通讯正常'}
        tvBuildName.setText(comaddress);
        tvDoorState.setText(equipment.getBuildingName());


        openDoorDetailPresenter = new OpenDoorDetailPresenter(this);
    }


    public void onClick(View view) {
    }


    @OnClick({R.id.ll_back_titlebar, R.id.btn_door_open, R.id.btn_door_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.btn_door_open://开锁
                openDoorDetailPresenter.sendDoorControl(comaddress, "01");
                break;
            case R.id.btn_door_close://关锁
                openDoorDetailPresenter.sendDoorControl(comaddress, "02");
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
        tvUpdateTimeEd.setText(msg);

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
        finish();
        openDoorDetailPresenter.rxUnsubscribe();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

