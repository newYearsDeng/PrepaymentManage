package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/02 14:06
 * @des 管理员首页
 */
public class HomeManageActivity extends BaseActivity {
    @BindView(R.id.iv_manage_home_lock)
    ImageView ivManageHomeLock;
    private long exitTime;

    @BindView(R.id.navigation_view_manage)
    NavigationView mNavigationView;
    @BindView(R.id.drawerlayout_manage)
    DrawerLayout mDrawerlayout;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @OnClick({
            R.id.iv_manage_home_head_menu,
            R.id.iv_manage_home_water,
            R.id.iv_manage_home_electricity,
            R.id.iv_manage_home_equipment,
            R.id.iv_manage_home_lock,
            R.id.iv_home_setting,
            R.id.iv_manage_home_light,
            R.id.iv_manage_home_air_condition})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.iv_manage_home_head_menu:
                break;
            case R.id.iv_manage_home_water:
                Intent intent = new Intent(this, Management.class);
                intent.putExtra(Contants.METERTYPE, "水");
                startActivity(intent);
                break;
            case R.id.iv_manage_home_electricity:
                Intent intent1 = new Intent(this, Management.class);
                intent1.putExtra(Contants.METERTYPE, "电");
                startActivity(intent1);
                break;
            case R.id.iv_manage_home_equipment:
                //采集设备
                startActivity(new Intent(MyApplication.getContext(), CollectEquipmentActivity.class));
                break;
            case R.id.iv_manage_home_lock:
                //门锁
                Intent intent2 = new Intent(MyApplication.getContext(),GateLockFirstActivity.class);
                intent2.putExtra(Contants.METERTYPE, "门锁");
                intent2.putExtra("power", "manager");
                startActivity(intent2);
                break;
            case R.id.iv_manage_home_light://照明
                Intent intent3 = new Intent(MyApplication.getContext(),EquipmentSelection.class);
                intent3.putExtra(Contants.METERTYPE, "灯控");
                intent3.putExtra("power", "manager");
                startActivity(intent3);
                break;
            case R.id.iv_manage_home_air_condition://分体空调
                Intent intent4 = new Intent(MyApplication.getContext(),EquipmentSelection.class);
                intent4.putExtra(Contants.METERTYPE, "空调");
                intent4.putExtra("power", "manager");
                startActivity(intent4);
                break;
            case R.id.iv_home_setting:
                //打开左侧菜单
                mDrawerlayout.openDrawer(Gravity.LEFT);
                //设置menu条目点击事件
                mNavigationView.setNavigationItemSelectedListener(new NavagationitemSelecteImpl(this, mDrawerlayout));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onStop() {
        super.onStop();
        mDrawerlayout.closeDrawers();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
