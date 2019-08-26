package com.northmeter.prepaymentmanage.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.DateAdapter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.BillsPresenter;
import com.northmeter.prepaymentmanage.ui.i.IBillsActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.recyclerview.DividerItemDecoration;
import com.northmeter.prepaymentmanage.util.recyclerview.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/08 10:37
 * @des 水/电 费账单
 */
public class BillsAcvtivity extends BaseActivity implements IBillsActivity {
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_bills_date)
    TextView mTvBillsDate;
    @BindView(R.id.tv_bills_month_start_meter_floor)
    TextView mTvMonthStartMeterFloor;
    @BindView(R.id.tv_bills_month_end_meter_floor)
    TextView mTvMonthEndMeterFloor;
    @BindView(R.id.tv_bills_total_use)
    TextView mTvTotalUse;
    @BindView(R.id.tv_bills_money)
    TextView mTvMoney;
    @BindView(R.id.spin_kit_bills_loading)
    SpinKitView mSpinKitBillsLoading;
    @BindView(R.id.tv_tv_bills_total_use_name)
    TextView mTvBillsTotalUseName;
    @BindView(R.id.tv_bills_month_start_meter_floor_name)
    TextView tvBillsMonthStartMeterFloorName;
    @BindView(R.id.tv_bills_month_end_meter_floor_name)
    TextView tvBillsMonthEndMeterFloorName;
    private BillsPresenter mPresenter;
    private ArrayList<String> mDates;
    private String mComaddress;
    private String mMeterType;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_bills;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mComaddress = getIntent().getStringExtra(Contants.DEVICES_QUERY_COMADDRESS_INTENT_EXTRA);
        mMeterType = getIntent().getStringExtra(Contants.DEVICES_QUERY_METER_TYPE_INTENT_EXTRA);

        mTvTitleTitlebar.setText(mMeterType + "费账单");

        if (mMeterType.equals("电")) {
            tvBillsMonthStartMeterFloorName.setText("期初表底(kwh)");
            tvBillsMonthEndMeterFloorName.setText("期末表底(kwh)");
            mTvBillsTotalUseName.setText("结算用量(kwh)");
        }

        initDate();


        mPresenter = new BillsPresenter(this);
        mPresenter.getMonthBill(mComaddress, mTvBillsDate.getText().toString().trim());
    }

    private void initDate() {
        //初始化时间
        Calendar calendar = Calendar.getInstance();
        //当前的年月
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        if (month == 0) {
            mTvBillsDate.setText(year-1 + "-" + 12);
        } else {
            mTvBillsDate.setText(year + "-" + month);
        }
        mDates = new ArrayList<>();
        for (int i = 12; i > 0; i--) {
            //如果月份小于0了就向下少一年
            if (month <= 0) {
                month = 12;
                year = --year;
            }
            if (month < 10) {
                //小与10月在前面加个0
                mDates.add(year + "-0" + month--);
            } else {
                mDates.add(year + "-" + month--);
            }
        }


    }


    @OnClick({R.id.ll_back_titlebar, R.id.tv_bills_date})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                mPresenter.rxUnsubscribe();
                break;
            case R.id.tv_bills_date:

                RecyclerView recyclerView = new RecyclerView(this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
                recyclerView.setLayoutManager(linearLayoutManager);

                recyclerView.setAdapter(new DateAdapter(mDates));
                recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), linearLayoutManager.getOrientation()));


                //设置popupwindow
                final PopupWindow popupWindow = new PopupWindow(recyclerView,
                        mTvBillsDate.getWidth(), mTvBillsDate.getHeight() * 5, true);
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
                        mPresenter.getMonthBill(mComaddress, mTvBillsDate.getText().toString().trim());
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                }));
                break;
        }
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showDataToTextView(String qcbd, String qmbd, String jsdata, String jsmoney) {
        if ("电".equals(mMeterType)) {
//            qcbd = qcbd + "㎥";
//            qmbd = qmbd + "㎥";
//            jsdata = jsdata + "㎥";

        } else {
//            qcbd = qcbd + "kWh";
//            qmbd = qmbd + "kWh";
//            jsdata = jsdata + "kWh";
        }
        mTvMonthStartMeterFloor.setText(qcbd);
        mTvMonthEndMeterFloor.setText(qmbd);
        mTvTotalUse.setText(jsdata);
        jsmoney = jsmoney.substring(0, jsmoney.length() - 2);
        mTvMoney.setText(jsmoney);
    }

    @Override
    public void showEmptyData() {
        mTvMonthStartMeterFloor.setText("");
        mTvMonthEndMeterFloor.setText("");
        mTvTotalUse.setText("");
        mTvMoney.setText("");
    }

    @Override
    public void showLoading() {
        mSpinKitBillsLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKitBillsLoading.setVisibility(View.GONE);
    }



}
