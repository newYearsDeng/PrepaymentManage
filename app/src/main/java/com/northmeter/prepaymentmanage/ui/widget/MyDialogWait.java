package com.northmeter.prepaymentmanage.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;

/**
 * Created by lht on 2016/11/28.
 * des:waitDialot
 */
public class MyDialogWait extends Dialog {
    private TextView tv_yes;
    private TextView tv_no;
    public  MyDialog_interface listenrer;
    public MyDialogWait(Context context,int theme) {
        super(context,theme);

    }
    public void init(MyDialog_interface listenrer){
        this.listenrer=listenrer;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_wait);
        initView();
    }

    private void initView() {
        tv_yes= (TextView) findViewById(R.id.dialog_wait_text_yes);
        tv_no= (TextView) findViewById(R.id.dialoh_wait_text_no);
        tv_yes.setOnClickListener(listenrer.yes());
        tv_no.setOnClickListener(listenrer.no());
    }


}
