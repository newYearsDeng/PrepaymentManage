package com.northmeter.prepaymentmanage.ui.light.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.DeviceChoiceAdapter;
import com.northmeter.prepaymentmanage.adapters.LookChumAdater;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.LookChumBean;
import com.northmeter.prepaymentmanage.model.UserMeterBean;
import com.northmeter.prepaymentmanage.presenter.ChangeRoomPresenter;
import com.northmeter.prepaymentmanage.presenter.DeviceChoicePresenter;
import com.northmeter.prepaymentmanage.ui.DevicesQueryActivity;
import com.northmeter.prepaymentmanage.ui.i.IChangeRoom;
import com.northmeter.prepaymentmanage.ui.i.IDeviceChoiceActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.recyclerview.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author dyd
 * @time 2017/12/26
 * @des 用户绑定的房间中的灯控设备选择
 */
public class UserLightChoiceActivity extends BaseActivity implements IDeviceChoiceActivity, IChangeRoom {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.recycerview_device_choice)
    RecyclerView mRecycerview;
    @BindView(R.id.spin_kit_deivce_choice_loading)
    SpinKitView mSpinKitLoading;
    @BindView(R.id.btn_device_choice_look_chum)
    Button mBtnLookChum;
    private DeviceChoicePresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_choice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("设备选择");
        mPresenter = new DeviceChoicePresenter(this);
        mPresenter.getMeters();
    }

    @Override
    public void showDevices(final List<UserMeterBean.RESPONSEXMLBean> responsexml) {
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
        mRecycerview.setLayoutManager(manager);
        DeviceChoiceAdapter adapter = new DeviceChoiceAdapter(UserLightChoiceActivity.this, responsexml);
        mRecycerview.setAdapter(adapter);
        mRecycerview.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), manager.getOrientation()));

        adapter.setOnItemClickListener(new DeviceChoiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String metertype = responsexml.get(position).getMETERTYPE();
                String comaddress = responsexml.get(position).getCOMADDRESS();
                LoggerUtil.d(comaddress);
                Intent intent = new Intent(MyApplication.getContext(), DevicesQueryActivity.class);
                intent.putExtra(Contants.DEVICES_QUERY_METER_TYPE_INTENT_EXTRA, metertype);
                intent.putExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA, comaddress);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLookChum(List<LookChumBean.RESPONSEXMLBean> responsexmlBeen) {
        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new LookChumAdater(this, responsexmlBeen));
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), linearLayoutManager.getOrientation()));

        new AlertDialog.Builder(this)
                .setTitle("住在该房间的同学有：")
                .setView(recyclerView)
                .show();
    }

    @Override
    public void setClickable(boolean clickable) {
        mBtnLookChum.setClickable(clickable);
    }

    @Override
    public void showLoading() {
        mSpinKitLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKitLoading.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_back_titlebar, R.id.btn_device_choice_look_chum, R.id.btn_device_choice_unbind_room})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                mPresenter.rxUnsubscribe();
                break;
            case R.id.btn_device_choice_look_chum:
                //查看室友
                mPresenter.lookChum();
                break;
            case R.id.btn_device_choice_unbind_room:
                String spId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
                String spBuildingId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spId, "");
                String decryptBuildingId = AES.decrypt(spBuildingId, Contants.SP_AES_BUILDING_KEY);
                if (TextUtils.isEmpty(decryptBuildingId)) {
                    ToastUtil.showShort(MyApplication.getContext(), "该账户没有绑定房间");
                } else {
                    new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("是否解除已经绑定的房间")
                            .setConfirmText("确定")
                            .setCancelText("取消")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog dialog) {
                                    ChangeRoomPresenter presenter = new ChangeRoomPresenter(UserLightChoiceActivity.this);
                                    presenter.unbindRoom();
                                    dialog.dismiss();


                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                }
                break;
        }
    }

    @Override
    public void unbindSucceed() {
        //解绑成功就把sp里面的buildingid清空
        String spId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String encryptBuildingId = AES.encrypt("", Contants.SP_AES_BUILDING_KEY);
        SharedPreferencesUtil.put(MyApplication.getContext(), spId, encryptBuildingId);

      /*  Intent intent = new Intent(MyApplication.getContext(), HomeUserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        finish();
    }


    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }
}
