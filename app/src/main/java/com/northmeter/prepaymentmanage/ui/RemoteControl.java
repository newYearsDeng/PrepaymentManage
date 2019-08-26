package com.northmeter.prepaymentmanage.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.PopListViewAdpter;
import com.northmeter.prepaymentmanage.adapters.RemoteControlAdpter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.presenter.RemoteControlPresenter;
import com.northmeter.prepaymentmanage.ui.i.IRemoteControlActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RemoteControl extends BaseActivity implements IRemoteControlActivity, AdapterView.OnItemClickListener {

    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.gv_remote_control)
    GridView gridView;
    @BindView(R.id.dl_remote_control)
    DrawerLayout dlRemoteControl;
    @BindView(R.id.iv_choose_building_remote_control)
    ImageView ivChooseBuildingRemoteControl;
    @BindView(R.id.iv_choose_menu_remote_control)
    ImageView ivChooseMenuRemoteControl;
    @BindView(R.id.spin_kit_topup_loading)
    SpinKitView loading;
    @BindView(R.id.ll_control_container)
    LinearLayout controlContainer;
    @BindView(R.id.tv1_control)
    TextView tv1Control;
    @BindView(R.id.tv2_control)
    TextView tv2Control;
    @BindView(R.id.tv3_control)
    TextView tv3Control;
    @BindView(R.id.tv4_control)
    TextView tv4Control;
    @BindView(R.id.tv_record_remote_control)
    TextView tv_record_control;
    private PopupWindow popWindow;
    private ListView pListView;
    private String type;
    private List<String> menus;
    private RemoteControlPresenter presenter;
    private RemoteControlAdpter adpter;
    private String buildingID;
    private boolean selectedMode;//选中，或者不选中状态
    private String [] orders;
    //登陆名
    private String loginName;
   //水表编号集合
    private List<String> comaddressList=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remote_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        type = getIntent().getStringExtra(Contants.METERTYPE);
        //关闭滑动
        dlRemoteControl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        //initListview
        pListView = new ListView(this);
        if (type.equals("水")) {
            menus = Arrays.asList("全部", "阀开", "阀关");
            tv1Control.setText("开阀");
            tv2Control.setEnabled(false);
            tv3Control.setEnabled(false);
            tv4Control.setText("关阀");
            orders=new String[]{"04","","","05"};
        } else if (type.equals("电")) {
            menus = Arrays.asList("全部", "保电", "合闸", "跳闸");
            tv1Control.setText("保电");
            tv2Control.setText("解除保电");
            tv3Control.setText("合闸");
            tv4Control.setText("跳闸");
            orders=new String[]{"00","01","02","03"};
        }
        pListView.setAdapter(new PopListViewAdpter(menus));
        pListView.setOnItemClickListener(this);

        presenter = new RemoteControlPresenter(this);

        adpter = new RemoteControlAdpter(type, new ArrayList<EquipmentBean.RESPONSEXMLBean>());
        gridView.setAdapter(adpter);

        gridView.setOnItemClickListener(new GrideViewItemClickListener());
        gridView.setOnItemLongClickListener(new GrideViewItemLongClickListener());

/*
        if (buildingID == null || buildingID.equals("")) {
            return;
        }
        presenter.getData(type, buildingID, "");*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (buildingID == null || buildingID.equals("")) {
            return;
        }
        presenter.getData(type, buildingID, "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 50)
    public void onEvent(AlreadyBuilding info) {
        dlRemoteControl.closeDrawer(Gravity.RIGHT);
        controlContainer.setVisibility(View.GONE);
        buildingID = info.Area_ID;
        if(info.room==null||info.room.equals("")){
            tvTitleTitlebar.setText(info.building+info.floor);
        }else{
            tvTitleTitlebar.setText(info.building+info.room);
        }
        presenter.getData(type, buildingID, "");
    }

    //监听返回键
    public void onBackPressed() {
        if (dlRemoteControl.isDrawerOpen(Gravity.RIGHT)) {
            dlRemoteControl.closeDrawer(Gravity.RIGHT);
            return;
        }
        if (selectedMode) {
            cancleSelecter();
            return;
        }
        super.onBackPressed();

    }

    private void cancleSelecter() {
        adpter.cancleSelected();
        controlContainer.setVisibility(View.GONE);
        selectedMode = false;
        return;
    }

    @OnClick({R.id.ll_back_titlebar, R.id.iv_choose_building_remote_control, R.id.iv_choose_menu_remote_control,R.id.tv1_control, R.id.tv2_control, R.id.tv3_control, R.id.tv4_control,R.id.tv_record_remote_control})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                if (selectedMode) {
                    selectedMode = false;
                    adpter.cancleSelected();
                    controlContainer.setVisibility(View.GONE);
                } else {
                    finish();
                }
                break;
            case R.id.iv_choose_building_remote_control:
                dlRemoteControl.openDrawer(Gravity.RIGHT);
                break;
            case R.id.iv_choose_menu_remote_control:
                if (popWindow == null) {
                    popWindow = new PopupWindow(pListView, 220, ViewGroup.LayoutParams.WRAP_CONTENT);
                    popWindow.setFocusable(true);
                    popWindow.setOutsideTouchable(true);
                    // popWindow.setBackgroundDrawable(ContextCompat.getDrawable(RemoteControl.this, R.drawable.icon_bounced));
                    popWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E8E8E8")));
                }
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                } else {
                    popWindow.showAsDropDown(ivChooseMenuRemoteControl, -10, 50);
                }
                break;
            case R.id.tv1_control:
               // presenter.goControlMeter(comaddressList,orders[0],loginName);
                createOrderDialog(orders[0],tv1Control.getText().toString());
                break;
            case R.id.tv2_control:
              //presenter.goControlMeter(comaddressList,orders[1],loginName);
                createOrderDialog(orders[1],tv2Control.getText().toString());
                break;
            case R.id.tv3_control:
               // presenter.goControlMeter(comaddressList,orders[2],loginName);
                createOrderDialog(orders[2],tv3Control.getText().toString());
                break;
            case R.id.tv4_control:
               // presenter.goControlMeter(comaddressList,orders[3],loginName);
                createOrderDialog(orders[3],tv4Control.getText().toString());
                break;
            case R.id.tv_record_remote_control:
                Intent intent=new Intent(this,ControlTaskReultActivity.class);
                intent.putExtra("buildingId",buildingID);
                intent.putExtra(Contants.METERTYPE,type);
                startActivity(intent);
                break;
        }
    }


    public  void createOrderDialog(final String order,String msg){
       /* final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("是否执行"+msg+"命令?")
                .setTitle("提示")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedMode = false;
                        adpter.cancleSelected();
                        controlContainer.setVisibility(View.GONE);


                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        presenter.goControlMeter(comaddressList,order,loginName);
                        comaddressList.clear();

                    }
                })
                .setCancelable(false)
                .show();*/
        new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                .setTitleText("是否执行"+msg+"命令？")
                .setConfirmText("确认")
                .setCancelText("取消")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        selectedMode = false;
                        adpter.cancleSelected();
                        controlContainer.setVisibility(View.GONE);
                        sweetAlertDialog.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        presenter.goControlMeter(comaddressList,order,loginName);
                        comaddressList.clear();
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }
    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if(loading==null){
            return;
        }
        loading.setVisibility(View.GONE);
    }

    @Override
    public void openRight() {
        dlRemoteControl.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void showArea(String AreaId, String Area,String LoginName) {
        tvTitleTitlebar.setText(Area);
        buildingID=AreaId;
        loginName=LoginName;
    }

    @Override
    public void cancleSelctedMode() {
        cancleSelecter();
    }

    @Override
    public void showData(String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen) {
        adpter.update(responsexmlBeen);

    }
    //pop 点击事件
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        popWindow.dismiss();
        controlContainer.setVisibility(View.GONE);
        final String control = menus.get(i);
        presenter.getData(type, buildingID, control);
    }




   //改变选项状态
    private void switchItemMode(View view, int i) {
        EquipmentBean.RESPONSEXMLBean bean = (EquipmentBean.RESPONSEXMLBean) adpter.getItem(i);
        bean.setChecked(!bean.isChecked());
        final String comaddress = bean.getCOMADDRESS();
        final View chooseView = view.findViewById(R.id.iv_checked_gv_item);
        if (bean.isChecked()) {
            chooseView.setVisibility(View.VISIBLE);
            comaddressList.add(comaddress);

        } else {
            chooseView.setVisibility(View.GONE);
            comaddressList.remove(comaddress);
        }
    }

    private class GrideViewItemLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            selectedMode = true;
            controlContainer.setVisibility(View.VISIBLE);
            switchItemMode(view, i);
            return true;
        }

    }

    private class GrideViewItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (selectedMode) {
                switchItemMode(view, i);
            }

        }
    }

}
