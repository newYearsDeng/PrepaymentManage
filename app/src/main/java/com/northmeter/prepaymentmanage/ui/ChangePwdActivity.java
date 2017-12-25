package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.ChangePwdPresenter;
import com.northmeter.prepaymentmanage.ui.i.IChangePwdActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/17 10:24
 * @des 密码修改的view
 */
public class ChangePwdActivity extends BaseActivity implements IChangePwdActivity {

    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.et_change_pwd_old_password)
    EditText mEtOldPassword;
    @BindView(R.id.et_change_pwd_input_password)
    EditText mEtInputPassword;
    @BindView(R.id.et_change_pwd_confirm_password)
    EditText mEtConfirmPassword;
    private ChangePwdPresenter mPresenter;
    private String mUserType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("修改密码");


        mPresenter = new ChangePwdPresenter(this);
        mUserType = getIntent().getStringExtra(Contants.USER_TYPE_INTENT_EXTRA);

    }



    @OnClick({R.id.ll_back_titlebar, R.id.btn_change_pwd_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
            case R.id.btn_change_pwd_confirm:

                String oldPwd = mEtOldPassword.getText().toString();
                String inputPwd = mEtInputPassword.getText().toString();
                String confirmPwd = mEtConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(oldPwd)){
                    ToastUtil.showShort(MyApplication.getContext(),"请输入旧密码");
                    return;
                }else if (TextUtils.isEmpty(inputPwd) || TextUtils.isEmpty(confirmPwd)){
                    ToastUtil.showShort(MyApplication.getContext(),"请输入新密码");
                    return;
                }else if (!inputPwd.equals(confirmPwd)){
                    ToastUtil.showShort(MyApplication.getContext(),"两次输入的新密码不一致");
                    return;
                }else if(inputPwd.length()<6||inputPwd.length()>15){
                    ToastUtil.showShort(MyApplication.getContext(),"密码必须是6~15位");
                    return;
                }

                if (Contants.USER_TYPE_USER.equals(mUserType)){
                    //普通用户
                    mPresenter.setChangePwd(mUserType,oldPwd,confirmPwd);
                }else {
                    //管理员
                    mPresenter.setChangePwd(mUserType,oldPwd,confirmPwd);
                }

                break;
        }
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(),toastStr);
    }

    @Override
    public void changeSucceed() {
        Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxUnsubscribe();
    }
}
