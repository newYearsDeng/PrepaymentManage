package com.northmeter.prepaymentmanage.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.BindRoomPresenter;
import com.northmeter.prepaymentmanage.ui.i.IBindRoomActivity;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/17 10:24
 * @des 绑定房间的view
 */
public class BindRoomActivity extends BaseActivity implements IBindRoomActivity {

    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_bind_room_school)
    TextView mTvSchool;
    @BindView(R.id.tv_bind_room_building)
    TextView mTvBuilding;
    @BindView(R.id.tv_bind_room_floor)
    TextView mTvFloor;
    @BindView(R.id.tv_bind_room_room)
    TextView mTvRoom;
    @BindView(R.id.spin_kit_bind_room_loading)
    SpinKitView mSpinKitLoading;
    @BindView(R.id.rl_bind_room_school)
    RelativeLayout mRlSchool;
    @BindView(R.id.rl_bind_room_building)
    RelativeLayout mRlBuilding;
    @BindView(R.id.rl_bind_room_floor)
    RelativeLayout mRlFloor;
    @BindView(R.id.rl_bind_room_room)
    RelativeLayout mRlRoom;
    private BindRoomPresenter mPresenter;
    private String mSchoolBuildingId = "";
    private String mBuildingBuildingId = "";
    private String mFloorBuildingId = "";
    private String mRoomBuildingId = "";
    private int selectItem = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_room;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("绑定房间");

        mPresenter = new BindRoomPresenter(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.ll_back_titlebar,
            R.id.rl_bind_room_school,
            R.id.rl_bind_room_building,
            R.id.rl_bind_room_floor,
            R.id.rl_bind_room_room,
            R.id.btn_bind_room_confirm,
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                mPresenter.rxUnsubscribe();
                break;
            case R.id.rl_bind_room_school:
                mPresenter.getSchool();
                break;
            case R.id.rl_bind_room_building:
                mPresenter.getSchoolSon("building", mSchoolBuildingId);
                break;
            case R.id.rl_bind_room_floor:
                mPresenter.getSchoolSon("floor", mBuildingBuildingId);
                break;
            case R.id.rl_bind_room_room:
                mPresenter.getSchoolSon("room", mFloorBuildingId);
                break;
            case R.id.btn_bind_room_confirm:
                String school = mTvSchool.getText().toString().trim();
                String building = mTvBuilding.getText().toString().trim();
                String floor = mTvFloor.getText().toString().trim();
                String room = mTvRoom.getText().toString().trim();
                if (!TextUtils.isEmpty(mRoomBuildingId)) {
                    mPresenter.confirmBindRoom(mRoomBuildingId);
                } else {
                    ToastUtil.showShort(MyApplication.getContext(), "未选择完房间信息");
                }
                break;
        }
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showSchool(String[] areaIds, String[] areaNames) {
        setDialog(mTvSchool, areaIds, areaNames);
    }

    @Override
    public void showBuilding(String[] areaIds, String[] areaNames) {
        setDialog(mTvBuilding, areaIds, areaNames);
    }

    @Override
    public void showFloor(String[] areaIds, String[] areaNames) {
        setDialog(mTvFloor, areaIds, areaNames);
    }

    @Override
    public void showRoom(String[] areaIds, String[] areaNames) {
        setDialog(mTvRoom, areaIds, areaNames);
    }

    @Override
    public void noHaveDownInfo(String area_id) {
        mRoomBuildingId = area_id;
    }

    @Override
    public void bindSucceed() {
        //把buildingid存到sp里面
        String encryptBuildingId = AES.encrypt(mRoomBuildingId, Contants.SP_AES_BUILDING_KEY);
        String spId = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
        SharedPreferencesUtil.put(MyApplication.getContext(), spId, encryptBuildingId);
        startActivity(new Intent(MyApplication.getContext(), DeviceChoiceActivity.class));
        finish();
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
    public void buildingClickable(boolean clickable) {
        mRlSchool.setClickable(clickable);
        mRlBuilding.setClickable(clickable);
        mRlFloor.setClickable(clickable);
        mRlRoom.setClickable(clickable);
    }

    private void setDialog(final TextView textView, final String[] areaIds, final String[] areaNames) {
        //点击前的string
        final String beforeStr = textView.getText().toString();
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setSingleChoiceItems(areaNames, selectItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        textView.setText(areaNames[which]);
                        //点击后的string
                        String afterStr = textView.getText().toString();
                        if (!beforeStr.equals(afterStr)) {
                            //文本发生了变化，清空下一级的文本信息
                            if (textView == mTvSchool) {
                                mTvBuilding.setText("");
                                mTvFloor.setText("");
                                mTvRoom.setText("");
                            } else if (textView == mTvBuilding) {
                                mTvFloor.setText("");
                                mTvRoom.setText("");
                            } else if (textView == mTvFloor) {
                                mTvRoom.setText("");
                            } else if (textView == mTvRoom) {
                            }
                        }

                        //根据不同的类型来保存buildingid
                        if (textView == mTvSchool) {

                            mSchoolBuildingId = areaIds[which];
                        } else if (textView == mTvBuilding) {
                            mBuildingBuildingId = areaIds[which];
                        } else if (textView == mTvFloor) {
                            mFloorBuildingId = areaIds[which];
                        } else if (textView == mTvRoom) {
                            mRoomBuildingId = areaIds[which];
                        }
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }


}
