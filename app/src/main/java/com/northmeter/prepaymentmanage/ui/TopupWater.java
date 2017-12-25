package com.northmeter.prepaymentmanage.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.DateAdapter;
import com.northmeter.prepaymentmanage.adapters.TopUpAdpter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.TopUp;
import com.northmeter.prepaymentmanage.presenter.TopupWaterPresenter;
import com.northmeter.prepaymentmanage.ui.i.ITopupWater;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.recyclerview.DividerItemDecoration;
import com.northmeter.prepaymentmanage.util.recyclerview.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @time 2016/11/8 14:10
 * @des 充值流水
 */
public class TopupWater extends BaseActivity implements ITopupWater {

    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.lv_topup_water)
    ListView lvTopupWater;
    @BindView(R.id.spin_kit_topup_loading)
    SpinKitView mSpinKitTopupLoading;
    @BindView(R.id.tv_area_commonquery)
    TextView tvAreaCommonquery;
    @BindView(R.id.tv_choosetime)
    TextView mTvBillsDate;
    private TopupWaterPresenter mPresenter;
    private List<String> mDates = new ArrayList<>();
    private String comaddress;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topup_water;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitleTitlebar.setText("交易流水");
        tvAreaCommonquery.setText("时间");
        comaddress = getIntent().getStringExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA);
        //得到当前系统的日期
        int year = Calendar.getInstance().get(Calendar.YEAR);
        mTvBillsDate.setText(year+"");

        for (int i = 0; i < 5; i++) {
            mDates.add(year - i + "");
        }

        mPresenter = new TopupWaterPresenter(this);
        mPresenter.getChargeRecord(comaddress, year + "");
    }

    @OnClick({R.id.ll_back_titlebar, R.id.tv_title_titlebar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                mPresenter.rxUnsubscribe();
                break;
            case R.id.tv_title_titlebar:
                break;
        }
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showDataToLv(List<TopUp.RESPONSEXMLBean> responsexml) {
        lvTopupWater.setAdapter(new TopUpAdpter(responsexml));
    }

    @Override
    public void showLoading() {
        mSpinKitTopupLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKitTopupLoading.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_choosetime)
    public void onClick() {
        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new DateAdapter(mDates));
        recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), linearLayoutManager.getOrientation()));


        //设置popupwindow
        final PopupWindow popupWindow = new PopupWindow(recyclerView,
                mTvBillsDate.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eeeeef")));
        //popupwindow在日期下面弹出
        popupWindow.showAsDropDown(mTvBillsDate);
        //recyclerview的点击事件
        recyclerView.addOnItemTouchListener(new RecyclerViewClickListener(MyApplication.getContext()
                , recyclerView, new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mTvBillsDate.setText(mDates.get(position));
                popupWindow.dismiss();
                mPresenter.getChargeRecord(comaddress, mTvBillsDate.getText().toString().trim());
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
}
