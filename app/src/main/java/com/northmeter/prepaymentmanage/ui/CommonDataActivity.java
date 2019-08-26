package com.northmeter.prepaymentmanage.ui;

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
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.ui.fragments.Read_DayQueryFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Read_MonthQueryFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Use_DayQueryFragment;
import com.northmeter.prepaymentmanage.ui.fragments.Use_MonthQueryFragment;
import com.northmeter.prepaymentmanage.ui.widget.NoScrollViewPager;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonDataActivity extends BaseActivity {
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
    @BindView(R.id.dl)
    DrawerLayout dl;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] mTitles;
    private EmptyFragmentPagerAdapter adapter;

    private String type;
    private String operationType;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        //EventBus.getDefault().register(this);

        type = getIntent().getStringExtra(Contants.METERTYPE);
        operationType = getIntent().getStringExtra(Contants.OPERATION_TYPE);

        initData();
    }


    private void initData() {
        //关闭滑动
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        String selectedArea_ID = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SELECTEDAREA_ID, "");
        //判断是否进行选择建筑。。
        if (selectedArea_ID == null || selectedArea_ID.equals("")) {
            dl.openDrawer(Gravity.RIGHT);
           // ToastUtil.showShort(MyApplication.getContext(), "请选择建筑");
        }

        String title = "";
        if (operationType.equals(Contants.READMETER)) {
            if (type.equals("水")) {
                title = "抄表(水)数据";
            } else {
                title = "抄表(电)数据";
            }
            fragments.add(Read_DayQueryFragment.newInstance(type));
            fragments.add(Read_MonthQueryFragment.newInstance(type));
        } else {
            if (type.equals("水")) {
                title = "用水数据";
            } else {
                title = "用电数据";
            }
            fragments.add(Use_DayQueryFragment.newInstance(type));
            fragments.add(Use_MonthQueryFragment.newInstance(type));

        }
        tvTitleTitlebar.setText(title);
        ivTitleTitleBar.setVisibility(View.VISIBLE);
        mTitles = getResources().getStringArray(R.array.empty_titles);
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
                dl.openDrawer(Gravity.RIGHT);
                break;
        }
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onEvent(AlreadyBuilding info) {
        dl.closeDrawer(Gravity.RIGHT);
    }*/
    public void closeDrawerRight(){
        dl.closeDrawer(Gravity.RIGHT);
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
