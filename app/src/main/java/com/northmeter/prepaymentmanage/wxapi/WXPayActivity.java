package com.northmeter.prepaymentmanage.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 微信支付
 */
public class WXPayActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.button_back)
    ImageView buttonBack;
    @BindView(R.id.text_charge_num)
    TextView textChargeNum;
    @BindView(R.id.bt_submit_order)
    Button btSubmitOrder;
    private Button submitButton;
    private Button confirmButton;
    private TextView textView;
    private EditText charge_number;//充值金额
    private StringBuffer sb;
    private Map<String, String> resultunifiedorder;
    private PayReq req;
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    private String telNum;
    private boolean charge_flag = false;//是否可进行充值
    private String chargeNum = null;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wxpay;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String textMoney = intent.getStringExtra("Money");
        textChargeNum.setText("¥"+textMoney);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button_back, R.id.bt_submit_order})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                finish();
                break;
            case R.id.bt_submit_order:
                ToastUtil.showShort(this, "支付后台正在升级，请稍后再试");
                break;
        }
    }
}
