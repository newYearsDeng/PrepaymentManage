package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.EquipmentsAdapter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.presenter.EquipmentSelectionPresenter;
import com.northmeter.prepaymentmanage.presenter.i.IEquipmentSelectionPresenter;
import com.northmeter.prepaymentmanage.ui.GateLock.OpenDoorDetailActivity;
import com.northmeter.prepaymentmanage.ui.i.IEquipmentSelection;
import com.northmeter.prepaymentmanage.ui.light.activity.ManagerLightControlActivity;
import com.northmeter.prepaymentmanage.ui.light.activity.UserLightControlActivity;
import com.northmeter.prepaymentmanage.ui.widget.CustomFooterView;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EquipmentSelection extends BaseActivity implements XRefreshView.XRefreshViewListener, AbsListView.OnScrollListener, IEquipmentSelection, AdapterView.OnItemClickListener {
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;

    @BindView(R.id.lv_es)
    ListView lvEs;
    @BindView(R.id.xRefreshView)
    XRefreshView xRefreshView;
    @BindView(R.id.dl)
    DrawerLayout dl;
    @BindView(R.id.tv_showMessage_es)
    TextView tv_showMessage;
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.iv_title_right_titilebar)
    ImageView ivRightTitlebar;

    private String type,power;
    private EquipmentsAdapter adapter;
    //上次刷新时间
    public static long lastRefreshTime;
    private String selectedArea_ID;
    private int pageSize = 10;
    private int pageIndex = 1;
    private EquipmentSelectionPresenter presenter;
    private int scrolleState;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_equipment_selection;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        EventBus.getDefault().register(this);
        presenter = new EquipmentSelectionPresenter(this);

        type = getIntent().getStringExtra(Contants.METERTYPE);
        power = getIntent().getStringExtra("power");

        if(type.equals("门锁")||type.equals("灯控")){
            tvTitleTitlebar.setText(type + "设备");
        }else{
            tvTitleTitlebar.setText("用" + type + "设备");
        }

        if(power.equals("manager")){
            ivRightTitlebar.setVisibility(View.VISIBLE);
            selectedArea_ID = presenter.getSelectedAreaID();
        }else{
            String spDecrypeId_1 = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
            //根据id取出buidingid
            String spDecrypeBuildingId_1 = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spDecrypeId_1, "");
            selectedArea_ID = AES.decrypt(spDecrypeBuildingId_1, Contants.SP_AES_BUILDING_KEY);
            System.out.println("selectedArea_ID==="+selectedArea_ID);
        }

        //初始化刷新控件
        initXrefreshview();

        adapter = new EquipmentsAdapter(new ArrayList<EquipmentBean.RESPONSEXMLBean>(), type);
        lvEs.setAdapter(adapter);
        lvEs.setOnItemClickListener(this);


        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


    }

    private void initXrefreshview() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);

        // 设置上次刷新的时间
        lastRefreshTime = (long) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.LASTREFRESH_TIME, 0L);

        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(this);
        xRefreshView.setOnAbsListViewScrollListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xRefreshView != null) {
            xRefreshView.stopRefresh();
            xRefreshView.stopLoadMore(true);
        }
    }

    @Override
    public void onRefresh() {
        if (selectedArea_ID.equals("null") || selectedArea_ID.equals("")) {
            // ToastUtil.showShort(this, "请先选择建筑");
            xRefreshView.stopRefresh();
            return;
        }
        pageIndex=1;
        presenter.getData(0, type, selectedArea_ID, "", pageIndex, pageSize);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        if (selectedArea_ID.equals("null") || selectedArea_ID.equals("")) {
            // ToastUtil.showShort(this, "请先选择建筑");
            xRefreshView.stopLoadMore(true);
            return;
        }
        pageIndex=pageIndex+1;
        presenter.getData(1, type, selectedArea_ID, "", pageIndex, pageSize);

    }

    @Override
    public void onRelease(float direction) {
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        scrolleState=scrollState;
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止时
               // tv_showMessage.setVisibility(View.GONE);
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
               // tv_showMessage.setVisibility(View.VISIBLE);// 手指做了抛的动作

            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: // 正在滚动。当屏幕滚动且用户使用的触碰
               // tv_showMessage.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        if(scrolleState==AbsListView.OnScrollListener.SCROLL_STATE_IDLE&&((visibleItemCount+firstVisibleItem)==totalItemCount)){
            tv_showMessage.setVisibility(View.VISIBLE);
        }else{
            tv_showMessage.setVisibility(View.GONE);
        }


    }


    @OnClick({R.id.ll_back_titlebar, R.id.iv_title_right_titilebar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.iv_title_right_titilebar:
                dl.openDrawer(Gravity.RIGHT);
                break;
        }
    }

 /*   @Override
    public void showData(String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> responsexmlBeen) {
        String msg = "";
        if (responsexmlBeen == null) {
            msg = "该建筑下没有设备信息";
        } else {
            msg = "电表总数: " + count + "  通讯正常数:  " + normal + "  通讯正常率: " + accuracy;
        }
        tv_showMessage.setText(msg);
        if (!datas.contains(responsexmlBeen)) {
            datas.addAll(responsexmlBeen);
        }
        adapter.notifyDataSetChanged();
    }*/

   /* @Override
    public void showNewData(String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> newdatas) {
        Log.i("LHT","下拉加载 "+newdatas.size());
        String msg = "";
        msg = "电表总数: " + count + "  通讯正常数:  " + normal + "  通讯正常率: " + accuracy;
        tv_showMessage.setText(msg);
        adapter.update(newdatas);
        //Log.i("LHT","new "+datas.size());
        // adapter.notifyDataSetChanged();
        *//*datas.clear();
        datas.addAll(newdatas);
        adapter.notifyDataSetChanged();*//*
    }

    @Override
    public void showMoreData(String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> newdatas) {
        String msg = "";
        msg = "电表总数: " + count + "  通讯正常数:  " + normal + "  通讯正常率: " + accuracy;
        tv_showMessage.setText(msg);
        *//*if (!datas.contains(newdatas)) {
            datas.addAll(newdatas);
        }
        Log.i("LHT","more  "+datas.size());
         adapter.notifyDataSetChanged();*//*
        adapter.add(newdatas);
    }*/

    @Override
    public void showNewData(int refreshType, String count, String accuracy, String normal, List<EquipmentBean.RESPONSEXMLBean> newdatas) {
        String msg = "";
        msg = "表计总数: " + count + "  通讯正常数:  " + normal + "  通讯正常率: " + accuracy;
        tv_showMessage.setText(msg);
         //下拉
        if(refreshType==0){
           // Log.i("LHT","下");
            adapter.update(newdatas);
            //上拉
        }else{
          //  Log.i("LHT","上");
            adapter.add(newdatas);
        }
    }

    @Override
    public void stopRefresh() {
        if (xRefreshView == null) {
            return;
        }
        xRefreshView.stopRefresh();
        xRefreshView.stopLoadMore(true);

    }

    @Override
    public long getLastRefreshTime() {
        return xRefreshView.getLastRefreshTime();
    }

    @Override
    public void startRefresh() {

        xRefreshView.startRefresh();
    }

    @Override
    public void openRight() {
        dl.openDrawer(Gravity.RIGHT);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        EquipmentBean.RESPONSEXMLBean equipment = (EquipmentBean.RESPONSEXMLBean) adapter.getItem(i);
        if (equipment == null) {
            return;
        }

        switch(type){
            case "门锁":
                Intent intent_0 = new Intent(EquipmentSelection.this, OpenDoorDetailActivity.class);
                intent_0.putExtra("equipment", equipment);
                intent_0.putExtra(Contants.METERTYPE, type);
                startActivity(intent_0);
                break;
            case "灯控":
                Intent intent_1 = new Intent(EquipmentSelection.this, ManagerLightControlActivity.class);
                intent_1.putExtra("equipment", equipment);
                intent_1.putExtra(Contants.METERTYPE, type);
                startActivity(intent_1);
                break;
            default:
                Intent intent_2 = new Intent(EquipmentSelection.this, EquipmentDetail.class);
                intent_2.putExtra("equipment", equipment);
                intent_2.putExtra(Contants.METERTYPE, type);
                startActivity(intent_2);
                break;
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onEvent(AlreadyBuilding info) {
        dl.closeDrawer(Gravity.RIGHT);
        selectedArea_ID = info.Area_ID;
        pageIndex = 1;
        startRefresh();

    }

    //监听返回键
    public void onBackPressed() {
        if (dl.isDrawerOpen(Gravity.RIGHT)) {
            dl.closeDrawer(Gravity.RIGHT);
            return;
        }
        super.onBackPressed();
    }


}
