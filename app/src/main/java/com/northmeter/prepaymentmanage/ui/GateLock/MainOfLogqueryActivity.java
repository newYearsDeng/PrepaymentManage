package com.northmeter.prepaymentmanage.ui.GateLock;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.EmptyFragmentPagerAdapter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.ui.GateLock.fragments.Read_DayLogFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Read_DayQueryFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Read_MonthQueryFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Use_DayQueryFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Use_MonthQueryFragment;
import com.northmeter.prepaymentmanage.ui.widget.NoScrollViewPager;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dyd on 2017/6/24.
 * 日志查询
 */
public class MainOfLogqueryActivity extends BaseActivity{
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.iv_title_right_titilebar)
    ImageView ivTitleTitleBar;
    @BindView(R.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R.id.tl_empty)
    TabLayout tlEmpty;
    @BindView(R.id.vp_empty)
    NoScrollViewPager vpEmpty;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;


    private List<Fragment> fragments = new ArrayList<>();
    private String[] mTitles;
    private EmptyFragmentPagerAdapter adapter;

    private String type,power;
    private String operationType;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_mainof_logquery;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        power = getIntent().getStringExtra("power");
        //关闭滑动
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        if(power.equals("manager")){
            String selectedArea_ID = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDAREA_ID, "");
            //判断是否进行选择建筑。。
            if (selectedArea_ID == null || selectedArea_ID.equals("")) {
                drawerlayout.openDrawer(Gravity.RIGHT);
                // ToastUtil.showShort(MyApplication.getContext(), "请选择建筑");
            }
            ivTitleTitleBar.setVisibility(View.VISIBLE);
        }

        String title = "记录查询";


        fragments.add(Read_DayLogFragment.newInstance(power));
        //fragments.add(Read_MonthQueryFragment.newInstance(type));

        tvTitleTitlebar.setText(title);

        mTitles = new String[]{""};
        adapter = new EmptyFragmentPagerAdapter(getSupportFragmentManager(), fragments, mTitles);
        vpEmpty.setAdapter(adapter);
        tlEmpty.setupWithViewPager(vpEmpty);
        vpEmpty.setOffscreenPageLimit(2);
    }

    @OnClick({R.id.ll_back_titlebar, R.id.iv_title_right_titilebar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.iv_title_right_titilebar:
                drawerlayout.openDrawer(Gravity.RIGHT);
                break;
        }
    }

    /* @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
     public void onEvent(AlreadyBuilding info) {
         dl.closeDrawer(Gravity.RIGHT);
     }*/
    public void closeDrawerRight(){
        drawerlayout.closeDrawer(Gravity.RIGHT);
    }

    //监听返回键
    public void onBackPressed() {
        if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerlayout.closeDrawer(Gravity.RIGHT);
            return;
        }
        super.onBackPressed();

    }
}
