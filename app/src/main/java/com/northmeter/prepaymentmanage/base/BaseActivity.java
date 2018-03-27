package com.northmeter.prepaymentmanage.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 *@author  admin
 *@time    2016/12/20 17:56
 *@des:    BaseActivity
 */
public abstract class BaseActivity extends AutoLayoutActivity {
private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏
        //设置布局
        setContentView(getLayoutId());
        //初始化黄油控件
        unbinder = ButterKnife.bind(this);
        //注册事件总线

         //初始化控件
        initView(savedInstanceState);
    }


    protected abstract int getLayoutId();
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //事件总线反注册
        EventBus.getDefault().unregister(this);
        //取消绑定黄油刀
        unbinder.unbind();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);

    }

    @Override
    protected void onPause() {
        super.onPause();
       // JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
      // JPushInterface.onResume(this);
    }
}
