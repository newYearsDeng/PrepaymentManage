package com.northmeter.prepaymentmanage.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.ForgetPwdPresenter;
import com.northmeter.prepaymentmanage.ui.i.IForgetPwdActivity;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/07 14:53
 * @des 忘记密码view
 */
public class ForgetPwdActivity extends BaseActivity implements IForgetPwdActivity {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_title_right_titlebar)
    TextView mTvTitleRight;
    @BindView(R.id.et_forget_pwd_phone_num)
    EditText mEtPhoneNum;
    @BindView(R.id.tv_forget_pwd_problem)
    TextView mTvProblem;
    @BindView(R.id.et_forget_pwd_answer)
    EditText mEtAnswer;
    @BindView(R.id.et_forget_pwd_input_password)
    EditText mEtInputPassword;
    @BindView(R.id.et_forget_pwd_confirm_password)
    EditText mEtConfirmPassword;
    @BindView(R.id.rl_forget_pwd_problem_bg)
    RelativeLayout mRlProblem;
    @BindView(R.id.spin_kit_forget_pwd_loading)
    SpinKitView mSpinKitLoading;
    private ForgetPwdPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("找回密码");
        mTvTitleRight.setText("完成");

        mPresenter = new ForgetPwdPresenter(this);

        //监听手机的文本变化
        mEtPhoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTvProblem.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }




    @OnClick({R.id.ll_back_titlebar, R.id.tv_title_right_titlebar, R.id.rl_forget_pwd_problem_bg})
    public void onClick(View view) {
        String phone = mEtPhoneNum.getText().toString();
        String problem = mTvProblem.getText().toString();
        String answer = mEtAnswer.getText().toString();
        String inputPwd = mEtInputPassword.getText().toString();
        String confirmPwd = mEtConfirmPassword.getText().toString();
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        //正则验证是否是手机格式
       // String telRegex = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(14[5,7]))\\d{8}$";
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.tv_title_right_titlebar:
                //完成确认修改
                if (TextUtils.isEmpty(phone)) {
                    showToast("请先输入手机号码");
                    return;
                } else if (!phone.matches(telRegex)||phone.length()!=11) {
                    showToast("手机号码不正确，请检查");
                    return;
                } else if (TextUtils.isEmpty(problem)) {
                    showToast("选择密保问题");
                    return;
                } else if (TextUtils.isEmpty(answer)) {
                    showToast("请输入密保答案");
                    return;
                } else if (TextUtils.isEmpty(inputPwd) || TextUtils.isEmpty(confirmPwd)) {
                    showToast("请输入密码");
                    return;
                } else if (!inputPwd.equals(confirmPwd)) {
                    showToast("两次输入的密码不一致");
                    return;
                }else if(inputPwd.length()<6||inputPwd.length()>15){
                    showToast("密码必须是6~15位");
                    return;
                }

                mPresenter.confirmChangePwd(phone,confirmPwd,problem,answer);

                break;
            case R.id.rl_forget_pwd_problem_bg:
                if (TextUtils.isEmpty(phone)) {
                    showToast("请先输入手机号码");
                    return;
                } else if (!phone.matches(telRegex)) {
                    showToast("手机号码不正确，请检查");
                    return;
                }
                //选择密保问题
                mPresenter.getProblem(phone);
                break;
        }
    }


    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void setProblemClickable(boolean cilckable) {
        mRlProblem.setClickable(cilckable);
    }

    @Override
    public void showLoading() {
        mSpinKitLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKitLoading.setVisibility(View.GONE);
    }

    @Override
    public void showProblemDialog(final String[] problemArr) {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(problemArr, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTvProblem.setText(problemArr[which]);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unRxsubscribe();
    }
}
