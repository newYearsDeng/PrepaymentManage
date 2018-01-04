package com.northmeter.prepaymentmanage.ui.light.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dyd on 2017/12/29.
 */
public class UserLightControlActivity extends BaseActivity {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.image_light_control_all)
    CheckBox imageLightControlAll;
    @BindView(R.id.image_light_control_1)
    CheckBox imageLightControl1;
    @BindView(R.id.image_light_control_2)
    CheckBox imageLightControl2;
    @BindView(R.id.image_light_control_3)
    CheckBox imageLightControl3;
    @BindView(R.id.image_light_control_4)
    CheckBox imageLightControl4;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_light_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("照明管理");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_back_titlebar, R.id.image_light_control_all, R.id.image_light_control_1, R.id.image_light_control_2, R.id.image_light_control_3, R.id.image_light_control_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.image_light_control_all:
                break;
            case R.id.image_light_control_1:
                break;
            case R.id.image_light_control_2:
                break;
            case R.id.image_light_control_3:
                break;
            case R.id.image_light_control_4:
                break;
        }
    }
}
