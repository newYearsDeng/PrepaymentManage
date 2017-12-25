package com.northmeter.prepaymentmanage.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.FeedbackPresenter;
import com.northmeter.prepaymentmanage.ui.i.IFeedbackActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/17 10:24
 * @des 意见反馈的view
 */
public class FeedbackActivity extends BaseActivity implements IFeedbackActivity {

    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_title_right_titlebar)
    TextView mTvTitleRightTitlebar;
    @BindView(R.id.et_feedback_content)
    EditText mEtContent;
    @BindView(R.id.tv_feedback_text_number)
    TextView mTvTextNumber;
    private FeedbackPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("意见反馈");
        mTvTitleRightTitlebar.setText("提交");

        mPresenter = new FeedbackPresenter(this);

        mTvTextNumber.setText("0/100");
        //动态获取edittext的数量
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = mEtContent.getText().length();
                mTvTextNumber.setText(TextUtils.isEmpty(s.toString()) ? "0/100" : length + "/100");
            }
        });
    }
    @OnClick({R.id.ll_back_titlebar, R.id.tv_title_right_titlebar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.tv_title_right_titlebar:
                String content = mEtContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showShort(MyApplication.getContext(), "请输入您宝贵的意见和建议");
                    return;
                }

                mPresenter.setFeedbackContent(content);
                break;
        }
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void commitSucceed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxUnsubscribe();
    }
}
