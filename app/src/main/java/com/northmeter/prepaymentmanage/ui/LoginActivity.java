package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.LoginPresenter;
import com.northmeter.prepaymentmanage.ui.i.ILoginActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author zz
 * @time 2016/11/02 14:46
 * @des 登录界面
 */
public class LoginActivity extends BaseActivity implements ILoginActivity, TextWatcher {


    @BindView(R.id.cb_login_rempass)
    CheckBox mCbRememberPwd;
    @BindView(R.id.et_login_id)
    EditText mEtId;
    @BindView(R.id.et_login_password)
    EditText mEtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.spin_kit_login_loading)
    SpinKitView mSpinKitLoading;
    @BindView(R.id.iv_delete)
    ImageView deleteName;

    private long exitTime = 0;

    //    private String editTextIdKey = "login_edit_text_id_key";
    private String editTextPwdKey = "login_edit_text_pwd_key";
    //    private String spAesIdKey = AES.encrypt("user_id", "login_sp_id_key");
    private String spAesPwdKey = AES.encrypt("user_pwd", "login_sp_pwd_key");
    private LoginPresenter mPresenter;
    private String mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mPresenter = new LoginPresenter(this);

        //从sharepreference里面取出账号密码
        String getSpId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        String decryptId = AES.decrypt(getSpId, Contants.DECRYPT_ID_KEY);
        String getSpPwd = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spAesPwdKey, "");
        String decryptPwd = AES.decrypt(getSpPwd, editTextPwdKey);
        //把取出的账号密码填写到edittext
        mEtId.setText(decryptId);
        mEtPwd.setText(decryptPwd);
        //如果密码框有数据就默认选中记住密码
        if (!TextUtils.isEmpty(decryptPwd)) {
            mCbRememberPwd.setChecked(true);
        }
        if(!TextUtils.isEmpty(decryptId)){
            deleteName.setVisibility(View.VISIBLE);
        }
        // LoggerUtil.d("sp的：" + decryptId + "---" + decryptPwd);
        mEtId.addTextChangedListener(this);
    }


    @OnClick({R.id.ll_login_remember_pwd, R.id.btn_login, R.id.ll_login_forget_pwd, R.id.iv_login_regist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_login_remember_pwd:
                //记住密码
                mCbRememberPwd.setChecked(mCbRememberPwd.isChecked() ? false : true);
                break;
            case R.id.ll_login_forget_pwd:
                //忘记密码
                startActivity(new Intent(MyApplication.getContext(), ForgetPwdActivity.class));
                break;
            case R.id.btn_login:
                //登录按钮
                //获取输入框账号和密码
                mId = mEtId.getText().toString();
                String password = mEtPwd.getText().toString();

                mPresenter.confirmLogin(mId, password);

                break;
            case R.id.iv_login_regist:
                //新用户注册
                startActivity(new Intent(MyApplication.getContext(), RegistActivity.class));
                break;
        }
    }


    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public boolean isCheck() {
        return mCbRememberPwd.isChecked();
    }

    @Override
    public void putNameToSp(String name) {
        SharedPreferencesUtil.put(MyApplication.getContext(),
                Contants.SP_AES_ID_KEY, AES.encrypt(name, Contants.DECRYPT_ID_KEY));

    }

    @Override
    public void putPwdToSp(String password) {
        SharedPreferencesUtil.put(MyApplication.getContext(),
                spAesPwdKey, AES.encrypt(password, editTextPwdKey));

    }

    @Override
    public void clearPwdToSp() {
        SharedPreferencesUtil.remove(MyApplication.getContext(), spAesPwdKey);
    }

    @Override
    public void startHomeView(String oper_type, String buildingID) {

        if ("查询员" .equals(oper_type)) {
            LoggerUtil.d(mId);
            //普通用户
            //把buildingid存到sp里面
            String encryptBuildingId = AES.encrypt(buildingID, Contants.SP_AES_BUILDING_KEY);
            String encryptLoginId = AES.encrypt(mId, Contants.DECRYPT_ID_KEY);
            SharedPreferencesUtil.put(MyApplication.getContext(), encryptLoginId, encryptBuildingId);
            Intent intent = new Intent(MyApplication.getContext(), HomeUserActivity.class);
            startActivity(intent);
        } else {
            //管理员
            startActivity(new Intent(MyApplication.getContext(), HomeManageActivity.class));
        }
        finish();
        mPresenter.rxUnsubscribe();
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
    public void btnClickable(boolean btnClickable) {
        mBtnLogin.setClickable(btnClickable);
    }

    @Override
    public void setJpushTag(String id) {

        //设置别名
        JPushInterface.setAlias(this, id, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("LHT", "设置别名成功 " + s);

            }
        });

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showShort(getApplicationContext(), "再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.rxUnsubscribe();
        mSpinKitLoading.setVisibility(View.GONE);
        mBtnLogin.setClickable(true);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.length() != 0) {
            deleteName.setVisibility(View.VISIBLE);
        } else {
            deleteName.setVisibility(View.GONE);
        }
    }
    @OnClick(R.id.iv_delete)
    public void onClick() {
        mEtId.setText("");
        mEtPwd.setText("");
    }
}
