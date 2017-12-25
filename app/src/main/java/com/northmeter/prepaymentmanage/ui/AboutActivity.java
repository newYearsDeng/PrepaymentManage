package com.northmeter.prepaymentmanage.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.util.MyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/17 10:24
 * @des 关于的view
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_about_version)
    TextView mTvAboutVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("关于");
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            mTvAboutVersion.setText("版本号：" + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ll_back_titlebar)
    public void onClick() {
        finish();
    }
}
