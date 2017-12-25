package com.northmeter.prepaymentmanage.util;

import android.app.Application;
import android.content.Context;

import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.jpush.android.api.JPushInterface;

/**
 * @author zz
 * @time 2016/11/02 14:13
 * @des ${TODO}
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //拿到设备的物理高度进行适配
        AutoLayoutConifg.getInstance().useDeviceSize();

        //初始化推送
        JPushInterface.setDebugMode(false);//设置开启日志
        JPushInterface.init(this);
    }

    public static Context getContext(){
        return mContext;
    }
}
