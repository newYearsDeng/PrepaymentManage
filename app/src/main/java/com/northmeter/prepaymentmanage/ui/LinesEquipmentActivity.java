package com.northmeter.prepaymentmanage.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.LinesEquipmentRVAdapter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.LinesDevicesBean;
import com.northmeter.prepaymentmanage.presenter.LinesEquipmentPresenter;
import com.northmeter.prepaymentmanage.ui.i.ILinesEquipmentActivity;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.northmeter.prepaymentmanage.util.recyclerview.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zz
 * @time 2016/11/04 15:52
 * @des 在/离线设备
 */

public class LinesEquipmentActivity extends BaseActivity implements ILinesEquipmentActivity {
    @BindView(R.id.rv_is_lines_equipment)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_title_titlebar)
    TextView mTvTitleTitlebar;
    @BindView(R.id.spin_kit_lines_devices_loading)
    SpinKitView mSpinKitLoading;

    private boolean isOnLines = true;
    private LinesEquipmentPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_is_lines_equipment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //通过传递过来的字符串 判断 在/离 线设备
        String stringExtra = getIntent().getStringExtra(Contants.EQUIPMENT_INTENT_EXTRA);
        String linesStr = stringExtra.substring(0, 2);
        mTvTitleTitlebar.setText(linesStr + "设备");
        if ("在线".equals(linesStr)) {
            isOnLines = true;
        } else {
            isOnLines = false;
        }

        mPresenter = new LinesEquipmentPresenter(this);
        mPresenter.getDevicesInfo(linesStr);
    }

    @OnClick(R.id.ll_back_titlebar)
    public void onClick() {
        finish();
    }

    @Override
    public void showToast(String toastStr) {
        ToastUtil.showShort(MyApplication.getContext(), toastStr);
    }

    @Override
    public void showDevices(List<LinesDevicesBean.RESPONSEXMLBean> responsexml) {

        //设置recyclerview管理器
        LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getContext());
        mRecyclerView.setLayoutManager(manager);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), manager.getOrientation()));
        //设置adapter
        LinesEquipmentRVAdapter adapter = new LinesEquipmentRVAdapter(this, responsexml, isOnLines);
        mRecyclerView.setAdapter(adapter);
        //设置点击事件
//        adapter.setOnItemClickListener(new LinesEquipmentRVAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                ToastUtil.showShort(MyApplication.getContext(), strings.get(position));
//            }
//        });
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.rxUnsubscribe();
    }
}
