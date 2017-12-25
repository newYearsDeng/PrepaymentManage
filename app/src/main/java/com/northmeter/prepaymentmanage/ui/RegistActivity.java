package com.northmeter.prepaymentmanage.ui;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.RegistPresenter;
import com.northmeter.prepaymentmanage.ui.i.IRegistActivity;
import com.northmeter.prepaymentmanage.ui.widget.TimerPickerDialog;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author zz
 * @time 2016/11/07 9:30
 * @des 用户注册
 */
public class RegistActivity extends BaseActivity implements IRegistActivity {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_regist_into_school_date)
    TextView mTvIntoSchoolDate;
    @BindView(R.id.tv_regist_into_house)
    TextView mTvIntoHouse;
    @BindView(R.id.et_regist_phone)
    EditText mEtPhoneNum;
    @BindView(R.id.et_regist_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_regist_student_number)
    EditText mEtStudentNumber;
    @BindView(R.id.et_regist_input_password)
    EditText mEtInputPassword;
    @BindView(R.id.et_regist_confirm_password)
    EditText mEtConfirmPassword;
    @BindView(R.id.tv_regist_problem_one)
    TextView mTvProblemOne;
    @BindView(R.id.et_regist_answer_one)
    EditText mEtAnswerOne;
    @BindView(R.id.tv_regist_problem_two)
    TextView mTvProblemTwo;
    @BindView(R.id.et_regist_answer_two)
    EditText mEtAnswerTwo;
    @BindView(R.id.tv_regist_problem_three)
    TextView mTvProblemThree;
    @BindView(R.id.et_regist_answer_three)
    EditText mEtAnswerThree;
    @BindView(R.id.spin_kit_regist_loading)
    SpinKitView mSpinKitRegistLoading;
    @BindView(R.id.btn_regist_confirm)
    Button mBtnRegistConfirm;
    private RegistPresenter mPresenter;
    private int mCheckItemOne = 0;//第一个问题被选中的条目
    private int mCheckItemTwo = 0;//第二个问题被选中的条目
    private int mCheckItemThree = 0;//第三个问题被选中的条目
    private HashMap<Integer, Integer> mCheckProblem = new HashMap<>();//通过键值对保存密保问题
    private StringBuffer buildingName = new StringBuffer();
    private String mLastSonAreaId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("用户注册");
        mPresenter = new RegistPresenter(this);
    }




    @OnClick({R.id.ll_back_titlebar,
            R.id.btn_regist_confirm,
            R.id.tv_regist_into_school_date,
            R.id.tv_regist_into_house,
            R.id.rl_regist_problem_one_bg,
            R.id.rl_regist_problem_two_bg,
            R.id.rl_regist_problem_three_bg
    })
    public void onClick(View view) {


        //得到输入框的信息
        String phone = mEtPhoneNum.getText().toString().trim();
        String userName = mEtUserName.getText().toString().trim();
        String intoSchoolDate = mTvIntoSchoolDate.getText().toString().trim();
        String studentNum = mEtStudentNumber.getText().toString().trim();
//        String intoHouse = mTvIntoHouse.getText().toString().trim();
        String inputPwd = mEtInputPassword.getText().toString().trim();
        String confirmPwd = mEtConfirmPassword.getText().toString().trim();
        String problemOne = mTvProblemOne.getText().toString().trim();
        String answerOne = mEtAnswerOne.getText().toString().trim();
        String problemTwo = mTvProblemTwo.getText().toString().trim();
        String answerTwo = mEtAnswerTwo.getText().toString().trim();
        String problemThree = mTvProblemThree.getText().toString().trim();
        String answerThree = mEtAnswerThree.getText().toString().trim();

        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                //返回
                finish();
                break;
            case R.id.btn_regist_confirm:
                //注册按钮
                //宿舍显示是名字，传的是id
                mPresenter.confirmRegist(phone, userName, intoSchoolDate, studentNum, mLastSonAreaId, inputPwd, confirmPwd,
                        problemOne, answerOne, problemTwo, answerTwo, problemThree, answerThree);
                break;
            case R.id.tv_regist_into_school_date:
                //得到日历的实例
                Calendar calendar = Calendar.getInstance();
                //显示时间选择器的dialog
                new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                mTvIntoSchoolDate.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                            }
                        },
                        calendar.get(Calendar.YEAR),//设置默认年（当前）
                        calendar.get(Calendar.MONTH),//设置默认月（当前）
                        calendar.get(Calendar.DAY_OF_MONTH))//设置默认日（当前）
                        .show();
                break;
            case R.id.tv_regist_into_house:
                String house = mTvIntoHouse.getText().toString().trim();
                LoggerUtil.d(house);

                //点击就清空Stringbuffer
                if (buildingName.length() > 0) {
                    buildingName.delete(0, buildingName.length());
                }
                //获取校区信息
                mPresenter.getSchoolBuilding();
                break;
            case R.id.rl_regist_problem_one_bg:
                setProblemText(mTvProblemOne);
                break;
            case R.id.rl_regist_problem_two_bg:
                setProblemText(mTvProblemTwo);
                break;
            case R.id.rl_regist_problem_three_bg:
                setProblemText(mTvProblemThree);
                break;
        }
    }

    /**
     * 把选中的问题显示在textview上
     *
     * @param problemText
     */
    public void setProblemText(final TextView problemText) {

        final String[] problemArr = new String[]{"您配偶的生日是？",
                "您母亲的姓名是？", "您学号(或工号)是？", "您母亲的生日是？",
                "您高中班主任的名字是？", "您父亲的姓名是？", "您小学班主任的名字是？",
                "您配偶的姓名是？", "您初中班主任的名字是？", "对您影响最大的人是？"};

        //设置默认选中的问题条目为0
        int defaultCheckItem = 0;
        if (problemText == mTvProblemOne) {
            //如果点击的是第一个问题，就选第一个问题被选择的条目
            defaultCheckItem = mCheckItemOne;
        } else if (problemText == mTvProblemTwo) {
            //如果点击的是第二个问题，就选第二个问题被选择的条目
            defaultCheckItem = mCheckItemTwo;
        } else if (problemText == mTvProblemThree) {
            //如果点击的是第三个问题，就选第三个问题被选择的条目
            defaultCheckItem = mCheckItemThree;
        }

        //设置密保问题多选条目dialog
        new AlertDialog.Builder(this).setSingleChoiceItems(problemArr, defaultCheckItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //判断密保问题是否在集合中存在
                if (!mCheckProblem.containsValue(which)) {
                    //问题序号做键，密保问题集合序号做值；给各自选中的密保问题条目赋值
                    if (problemText == mTvProblemOne) {
                        mCheckProblem.put(1, which);
                        mCheckItemOne = mCheckProblem.get(1);
                    } else if (problemText == mTvProblemTwo) {
                        mCheckProblem.put(2, which);
                        mCheckItemTwo = mCheckProblem.get(2);
                    } else if (problemText == mTvProblemThree) {
                        mCheckProblem.put(3, which);
                        mCheckItemThree = mCheckProblem.get(3);
                    }

                    problemText.setText(problemArr[which]);
                    problemText.setTextColor(Color.parseColor("#000000"));

                    dialog.dismiss();
                } else {
                    ToastUtil.showShort(MyApplication.getContext(), "该问题已被选择");
                }

            }
        }).show();
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showProblemDialog() {

        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this)
                .setTitleText("温馨提示")
                .setContentText("你没有设置任何密保问题，请至少设置一个密保问题，方便以后找回密码")
                .setConfirmText("确定")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
        sweetAlertDialog.show();

    }

    @Override
    public void finishView() {
        finish();
    }

    @Override
    public void showSchoolBuildingDialog(final String[] areaIds, final String[] areaNames) {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(areaNames, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //继续查询building的子建筑信息
                        mPresenter.getSchoolSonBuilding(areaIds[which]);
                        //拼接校区信息
                        buildingName.append(areaNames[which]);
                        dialog.dismiss();
                    }
                }).show();

    }

    @Override
    public void showBuildingName(String area_id) {
//        mBuildingDialog.dismiss();
        mTvIntoHouse.setText(buildingName.toString());
        mLastSonAreaId = area_id;
    }

    @Override
    public void showLoading() {
        mSpinKitRegistLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKitRegistLoading.setVisibility(View.GONE);
    }

    @Override
    public void btnClickable(boolean clickable) {
        mBtnRegistConfirm.setClickable(clickable);
    }

    @Override
    public void buildingClickable(boolean clickable) {
        mTvIntoHouse.setClickable(clickable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.RxUnsubscribe();
    }
}
