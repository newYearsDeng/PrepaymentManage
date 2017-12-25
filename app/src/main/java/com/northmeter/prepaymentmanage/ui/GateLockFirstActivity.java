package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.ui.GateLock.MainOfLogqueryActivity;
import com.northmeter.prepaymentmanage.ui.GateLock.OpenDoorActivity;
import com.northmeter.prepaymentmanage.util.Contants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dyd on 2017/6/23.
 */
public class GateLockFirstActivity extends BaseActivity {
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.rl_opendoor_warn)
    RelativeLayout rlOpendoorWarn;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.rl_logquery)
    RelativeLayout rlLogquery;
    @BindView(R.id.rl_share_key)
    RelativeLayout rlShareKey;
    @BindView(R.id.rl_open_door)
    RelativeLayout rlOpenDoor;
    @BindView(R.id.rl_device_message)
    RelativeLayout rlDeviceMessage;

    private String type,power;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gatelock_first;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        type = getIntent().getStringExtra(Contants.METERTYPE);
        power = getIntent().getStringExtra("power");
        tvTitleTitlebar.setText("门锁管理");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_back_titlebar, R.id.rl_opendoor_warn, R.id.rl_setting, R.id.rl_logquery, R.id.rl_share_key, R.id.rl_open_door, R.id.rl_device_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar://返回
                finish();
                break;
            case R.id.rl_opendoor_warn://开门提醒
                break;
            case R.id.rl_setting://高级设置
                break;
            case R.id.rl_logquery://记录查询
                Intent intent_1 = new Intent(GateLockFirstActivity.this, MainOfLogqueryActivity.class);
                intent_1.putExtra(Contants.METERTYPE, type);
                intent_1.putExtra("power",power);
                startActivity(intent_1);
                break;
            case R.id.rl_share_key://分享钥匙
                break;
            case R.id.rl_open_door://远程开门
                Intent intent_2 = new Intent(GateLockFirstActivity.this, EquipmentSelection.class);
                intent_2.putExtra(Contants.METERTYPE, type);
                intent_2.putExtra("power",power);
                startActivity(intent_2);
                break;
            case R.id.rl_device_message://设备信息
                break;
        }
    }
}
