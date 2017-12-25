package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 进场过渡
 */
public class WelcomeActivity extends BaseActivity {

    private Subscription mSubscribe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //隐藏状态栏
       // View decorView = getWindow().getDecorView();
      //  int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
      //  decorView.setSystemUiVisibility(option);

        //减少图片占用内存
      //Todo：规定观察者的的工作线程，否则跳转会出现端暂的黑屏
        mSubscribe = Observable
                .timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        startActivity(new Intent(MyApplication.getContext(), LoginActivity.class));
                        finish();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null) {
            mSubscribe.unsubscribe();
        }
    }


}
