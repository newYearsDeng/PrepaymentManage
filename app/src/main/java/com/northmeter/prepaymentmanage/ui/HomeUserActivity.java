package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }


    @OnClick({R.id.iv_userhome_water_ele,
            R.id.iv_userhome_lighting,
            R.id.iv_userhome_air_condition,
            R.id.iv_home_setting,
            R.id.iv_manage_home_lock})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_userhome_water_ele:
                //从sp里面取出loginid
                String spDecrypeId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
                //根据id取出buidingid
                String spDecrypeBuildingId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spDecrypeId, "");
                String buidingId = AES.decrypt(spDecrypeBuildingId, Contants.SP_AES_BUILDING_KEY);
                LoggerUtil.d("" + buidingId);

                if (TextUtils.isEmpty(buidingId)) {
                    //如果buidingid为空就表示没绑定房间
                    startActivity(new Intent(MyApplication.getContext(), BindRoomActivity.class));
                } else {
                    Intent intent = new Intent(MyApplication.getContext(), DeviceChoiceActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.iv_manage_home_lock://门锁
                String spDecrypeId_1 = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
                //根据id取出buidingid
                String spDecrypeBuildingId_1 = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spDecrypeId_1, "");
                String buidingId_1 = AES.decrypt(spDecrypeBuildingId_1, Contants.SP_AES_BUILDING_KEY);
                LoggerUtil.d("" + buidingId_1);

                if (TextUtils.isEmpty(buidingId_1)) {
                    //如果buidingid为空就表示没绑定房间
                    startActivity(new Intent(MyApplication.getContext(), BindRoomActivity.class));
                } else {
                    Intent intent2 = new Intent(MyApplication.getContext(),GateLockFirstActivity.class);
                    intent2.putExtra(Contants.METERTYPE, "门锁");
                    intent2.putExtra("power", "user");
                    startActivity(intent2);
                }


                break;
            case R.id.iv_userhome_lighting://用户照明管理
                ToastUtil.showShort(MyApplication.getContext(), "功能暂未开放");
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
