package com.northmeter.prepaymentmanage.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lht
 * on 2016/12/16.
 * des:
 */
public class MyDialog_pay extends Dialog {
    @BindView(R.id.tv1_dialog_pay)
    TextView tv1DialogPay;
    @BindView(R.id.tv2_dialog_pay)
    TextView tv2DialogPay;
    @BindView(R.id.tv3_dialog_pay)
    TextView tv3DialogPay;
    @BindView(R.id.tv4_dialog_pay)
    TextView tv4DialogPay;
    @BindView(R.id.tv5_dialog_pay)
    TextView tv5DialogPay;
    @BindView(R.id.tv6_dialog_pay)
    TextView tv6DialogPay;
    private List<String> values = Arrays.asList("10","20","30","50","100","-1");
    private OnValueChooseListener listener;
    public MyDialog_pay(Context context, int themeResId,OnValueChooseListener listener ) {
        super(context, themeResId);
        this.listener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv1_dialog_pay, R.id.tv2_dialog_pay, R.id.tv3_dialog_pay, R.id.tv4_dialog_pay, R.id.tv5_dialog_pay, R.id.tv6_dialog_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1_dialog_pay:
                action(0);
                break;
            case R.id.tv2_dialog_pay:
                action(1);
                break;
            case R.id.tv3_dialog_pay:
                action(2);
            case R.id.tv4_dialog_pay:
                action(3);
                break;
            case R.id.tv5_dialog_pay:
                action(4);
                break;
            case R.id.tv6_dialog_pay:
                action(5);
                break;
        }
    }

    public void action(int position){
        listener.chooseVaules(values.get(position));
        dismiss();
    }
   public interface  OnValueChooseListener{
       void chooseVaules(String values);
   }
}
