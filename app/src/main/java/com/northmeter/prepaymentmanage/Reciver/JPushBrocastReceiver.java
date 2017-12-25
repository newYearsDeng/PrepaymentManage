package com.northmeter.prepaymentmanage.Reciver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * @author zz
 * @time 2016/6/3/0003 14:59
 * @des 接收推送的广播
 */
public class JPushBrocastReceiver extends BroadcastReceiver {
    private NotificationManager nm;
    private final static String TAG="JpReciver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
           Log.i(TAG,"JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i( TAG,"接受到推送下来的自定义消息"+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.i(TAG, "接受到推送下来的通知");
            receivingNotification(context,bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.i(TAG, "用户点击打开了通知");
            openNotification(context,bundle);
        } else {
            Log.i(TAG,"Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.i(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
       Log.i(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.i(TAG,"extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            //当前的url的值是由，极光推送的服务传递过来的，http://www.itheima.com
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("url");
            Log.i(TAG,"myValue "+myValue);

        } catch (Exception e) {
            Log.i(TAG,"Exception  --  >" + e.toString());
            return;
        }
        /**
         * 接收到消息后要点击要跳转的页面
         * 这里是跳转到另一个Activity进行页面信息的显示
         */
       // Intent mIntent = new Intent(context, FlashActivity.class);
//        mIntent.putExtra("url", myValue);
      //  mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
     //   context.startActivity(mIntent);
        //System.exit(0);
    }
}
