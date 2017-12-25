package com.northmeter.prepaymentmanage.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
  *@author lht
  *@time   2016/11/9 14:15
  *@des   用电 或用水管理界面
  */
public class Management extends BaseActivity {
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.rl_dbjk_electricity)
    RelativeLayout rlDbjkElectricity;
    @BindView(R.id.rl_cbsjcx_elctricity)
    RelativeLayout rlCbsjcxElctricity;
    @BindView(R.id.rl_ydsjcx_elcricity)
    RelativeLayout rlYdsjcxElcricity;
    @BindView(R.id.rl_dbyckz_electricity)
    RelativeLayout rlDbyckzElectricity;
    @BindView(R.id.tv_sbjk)
    TextView tvSbjk;
    @BindView(R.id.tv_cbsjcx)
    TextView tvCbsjcx;
    @BindView(R.id.tv_ydsjcx)
    TextView tvYdsjcx;
    @BindView(R.id.tv_dbyckz)
    TextView tvDbyckz;
    private String type;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_electricitymanagement;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
       type=getIntent().getStringExtra(Contants.METERTYPE);
        if (type.equals("水")) {
            tvTitleTitlebar.setText("用水管理");
            tvSbjk.setText("水表监控");
            tvDbyckz.setText("水表远程控制");
            tvYdsjcx.setText("用水数据查询");
        } else if (type.equals("电")) {
            tvTitleTitlebar.setText("用电管理");
        }


    }


    @OnClick({R.id.ll_back_titlebar, R.id.rl_dbjk_electricity, R.id.rl_cbsjcx_elctricity, R.id.rl_ydsjcx_elcricity, R.id.rl_dbyckz_electricity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                this.finish();
                break;
            case R.id.rl_dbjk_electricity:
                Intent intent1 = new Intent(this, EquipmentSelection.class);
                intent1.putExtra("power","manager");
                intent1.putExtra(Contants.METERTYPE,type);
                startActivity(intent1);
                break;
            case R.id.rl_cbsjcx_elctricity:
                Intent intent2=new Intent(this,CommonDataActivity.class);
                intent2.putExtra(Contants.METERTYPE,type);
                intent2.putExtra(Contants.OPERATION_TYPE,Contants.READMETER);
                startActivity(intent2);
                break;
            case R.id.rl_ydsjcx_elcricity:
                Intent intent3=new Intent(this,CommonDataActivity.class);
                intent3.putExtra(Contants.METERTYPE,type);
                intent3.putExtra(Contants.OPERATION_TYPE,Contants.USEMETER);
                startActivity(intent3);

                break;
            case R.id.rl_dbyckz_electricity:
                Intent intent=new Intent(this,RemoteControl.class);
                intent.putExtra(Contants.METERTYPE,type);
                startActivity(intent);
                break;
        }
    }




}
