package com.northmeter.prepaymentmanage.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.presenter.DevicesQueryPresenter;
import com.northmeter.prepaymentmanage.ui.i.IDevicesQueryActivity;
import com.northmeter.prepaymentmanage.ui.widget.MyDialogWait;
import com.northmeter.prepaymentmanage.ui.widget.MyDialog_interface;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;

public class EquipmentDetail extends BaseActivity implements IDevicesQueryActivity, View.OnClickListener {
    @BindView(R.id.tv_title_right_titlebar)
    TextView tvTitleRightTitlebar;
    @BindView(R.id.tv_bh_ed)
    TextView tvBhEd;
    @BindView(R.id.tv_dbbh_ed)
    TextView tvDbbhEd;
    @BindView(R.id.tv_dbzt_ed)
    TextView tvDbztEd;
    @BindView(R.id.tv_bzje_ed)
    TextView tvBzjeEd;
    @BindView(R.id.tv_yff_ed)
    TextView tvYffEd;
    @BindView(R.id.tv_zje_ed)
    TextView tvZjeEd;
    @BindView(R.id.tv_zydl_ed)
    TextView tvZydlEd;
    @BindView(R.id.tv_update_time_ed)
    TextView tvUpdateTimeEd;
    @BindView(R.id.btn_reading_ed)
    Button btnReading;
    @BindView(R.id.rl_yff_ed)
    LinearLayout rl_yff_ed;
    @BindView(R.id.spin_kit_topup_loading)
    SpinKitView spinKitTopupLoading;
    @BindView(R.id.tv_zt_ed)
    TextView tvZtEd;
    @BindView(R.id.tv_zsyl_ed0)
    TextView tvZsylEd0;
    private DevicesQueryPresenter presenter;
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.tv_line_ed)
    TextView line;
    @BindView(R.id.tv_bzje_name)
    TextView tv_bzje_name;
    private EquipmentBean.RESPONSEXMLBean equipment;
    private String type;
    private String comaddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_equipment_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        equipment = (EquipmentBean.RESPONSEXMLBean) getIntent().getSerializableExtra("equipment");
        type = getIntent().getStringExtra(Contants.METERTYPE);
        tvTitleTitlebar.setText(equipment.getBuildingName() + "(" + type + ")");
        comaddress = equipment.getCOMADDRESS();

        if (type.equals("水")) {
            rl_yff_ed.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            tvBhEd.setText("水表编号");
            tvZtEd.setText("水表状态");
            tvZsylEd0.setText("总用水量(m³)");
            tv_bzje_name.setText("补助余额(吨)");
        }
        presenter = new DevicesQueryPresenter(this);
        llBackTitlebar.setOnClickListener(this);
        btnReading.setOnClickListener(this);
        //加载数据
        presenter.getData(type, comaddress);
    }

    @Override
    public void showData(String yffye, String bzye, String zye, String zyl, String bzt, String updateTime) {
        tvDbbhEd.setText(comaddress);
        tvDbztEd.setText(bzt);
        tvZjeEd.setText(zye);
        tvBzjeEd.setText(bzye);
        tvYffEd.setText(yffye);
        if (type.equals("水")) {
            tvZydlEd.setText(zyl);
        } else {
            tvZydlEd.setText(zyl);
        }
        tvUpdateTimeEd.setText("数据更新时间:  " + updateTime);
        btnReading.setText("抄表");
        btnReading.setEnabled(true);
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
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

        if (btnReading == null) {
            return;
        }
        if (msg == null || state == 0) {
            btnReading.setEnabled(true);
            btnReading.setText(msg);
            return;
        }
        if (state == 2) {
            btnReading.setEnabled(true);
            btnReading.setText(msg);
        } else {
            btnReading.setEnabled(false);
            btnReading.setText(msg);
        }
    }

    @Override
    public void getStateTimeout() {
        if (btnReading == null) {
            return;
        }

        btnReading.setText("抄表");
        btnReading.setEnabled(true);

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
                presenter.readingMeter(comaddress, type);
            }
        });
        myDialogWait.show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                presenter.rxUnsubscribe();
                break;
            case R.id.btn_reading_ed:
                presenter.readingMeter(comaddress, type);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        finish();
        presenter.rxUnsubscribe();
        super.onDestroy();
    }


}
