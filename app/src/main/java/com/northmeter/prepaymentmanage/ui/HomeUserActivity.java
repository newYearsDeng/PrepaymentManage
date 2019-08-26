package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.ui.light.activity.UserLightChoiceActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/11 16:48
 * @des 用户首页
 */
public class HomeUserActivity extends BaseActivity {
    @BindView(R.id.iv_manage_home_lock)
    ImageView ivManageHomeLock;
    private long exitTime = 0;
    @BindView(R.id.iv_home_setting)
    ImageView mIvSetting;
    @BindView(R.id.drawerlayout_user)
    DrawerLayout mDrawerlayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private String buildingID;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //从sp里面取出loginid
        String spDecrypeId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        //根据id取出buidingid
        String spDecrypeBuildingId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spDecrypeId, "");
        buildingID = AES.decrypt(spDecrypeBuildingId, Contants.SP_AES_BUILDING_KEY);
        LoggerUtil.d("" + buildingID);
    }


    @OnClick({R.id.iv_userhome_water_ele,
            R.id.iv_userhome_lighting,
            R.id.iv_userhome_air_condition,
            R.id.iv_home_setting,
            R.id.iv_manage_home_lock})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_userhome_water_ele:
                if (TextUtils.isEmpty(buildingID)) {
                    //如果buidingid为空就表示没绑定房间
                    startActivity(new Intent(MyApplication.getContext(), BindRoomActivity.class));
                } else {
                    Intent intent = new Intent(MyApplication.getContext(), DeviceChoiceActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_manage_home_lock://门锁
                if (TextUtils.isEmpty(buildingID)) {
                    //如果buidingid为空就表示没绑定房间
                    startActivity(new Intent(MyApplication.getContext(), BindRoomActivity.class));
                } else {
                    Intent intent = new Intent(MyApplication.getContext(),GateLockFirstActivity.class);
                    intent.putExtra(Contants.METERTYPE, "门锁");
                    intent.putExtra("power", "user");
                    startActivity(intent);
                }
                break;
            case R.id.iv_userhome_lighting://用户照明管理
                ToastUtil.showShort(MyApplication.getContext(), "功能暂未开放");
//                if (TextUtils.isEmpty(buildingID)) {
//                    //如果buidingid为空就表示没绑定房间
//                    startActivity(new Intent(MyApplication.getContext(), BindRoomActivity.class));
//                } else {
//                    Intent intent = new Intent(MyApplication.getContext(), UserLightChoiceActivity.class);
//                    startActivity(intent);
//                }
                break;
            case R.id.iv_userhome_air_condition://用户空调管理
                ToastUtil.showShort(MyApplication.getContext(), "功能暂未开放");
                break;
            case R.id.iv_home_setting:
//                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.drawer_open, R.string.drawer_close);
//                toggle.syncState();
//                mDrawerlayout.addDrawerListener(toggle);
                //打开左侧菜单
                mDrawerlayout.openDrawer(Gravity.LEFT);
//                mNavigationView.setItemIconTintList(null);
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
