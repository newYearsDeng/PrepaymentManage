package com.northmeter.prepaymentmanage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.CollectEquipmentRVAdapter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.presenter.CollectEquipmentPresenter;
import com.northmeter.prepaymentmanage.ui.i.ICollectEquipmentActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.recyclerview.DividerItemDecoration;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/04 14:50
 * @des 采集设备的activity
 */
public class CollectEquipmentActivity extends BaseActivity implements ICollectEquipmentActivity {
    @BindView(R.id.rv_collect_equipment)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.tv_collect_equipment_devices_sum)
    TextView mTvDevicesSum;
    @BindView(R.id.tv_collect_equipment_online_sum)
    TextView mTvOnlineSum;
    @BindView(R.id.tv_collect_equipment_unline_sum)
    TextView mTvUnlineSum;
    @BindView(R.id.tv_collect_equipment_online_rate)
    TextView mTvOnlineRate;
    @BindView(R.id.spin_kit_collect_equipment_loading)
    SpinKitView mSpinKittLoading;


    private String[] equipments = {"在线设备查看", "离线设备查看"};
    private CollectEquipmentPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect_equipment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvTitleTitlebar.setText("采集设备");

        mPresenter = new CollectEquipmentPresenter(this);
        mPresenter.getDevicesNumber();


    }



    @OnClick({R.id.ll_back_titlebar, R.id.tv_title_right_titlebar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                mPresenter.rxUnsubscribe();
                break;

        }
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showDevices(String sum, String onLineSUM) {
        //设置recyclerview管理器
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
        mRecyclerView.setLayoutManager(manager);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), manager.getOrientation()));
        //设置adapter
        CollectEquipmentRVAdapter adapter = new CollectEquipmentRVAdapter(this, equipments);
        mRecyclerView.setAdapter(adapter);
        //设置点击事件
        adapter.setOnItemClickListener(new CollectEquipmentRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MyApplication.getContext(), LinesEquipmentActivity.class);
                intent.putExtra(Contants.EQUIPMENT_INTENT_EXTRA, equipments[position]);
                startActivity(intent);
            }
        });

        mTvDevicesSum.setText(sum);
        mTvOnlineSum.setText(onLineSUM);
        int devicesSum = Integer.parseInt(sum);
        int onLineSum = Integer.parseInt(onLineSUM);
        String unline = String.valueOf(devicesSum - onLineSum);
        mTvUnlineSum.setText(unline);

        if (devicesSum == 0) {
            mTvOnlineRate.setText("0%");
        } else {
            //设置在线率的百分比
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(0);
            float baifen = (float) onLineSum / devicesSum;
            String onLineRate = nt.format(baifen);
            mTvOnlineRate.setText(onLineRate);
        }
    }

    @Override
    public void showEmptyView() {
        mTvDevicesSum.setText("0");
        mTvUnlineSum.setText("0");
        mTvOnlineSum.setText("0");
        mTvOnlineRate.setText("0");
    }

    @Override
    public void showLoading() {
        mSpinKittLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mSpinKittLoading.setVisibility(View.GONE);
    }
}
