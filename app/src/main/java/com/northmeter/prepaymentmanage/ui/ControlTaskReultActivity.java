package com.northmeter.prepaymentmanage.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.ControlTaskResultAdpter;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.model.ControlTaskBean;
import com.northmeter.prepaymentmanage.presenter.ControlTaskResultPresenter;
import com.northmeter.prepaymentmanage.ui.i.IControlTaskResultActivity;
import com.northmeter.prepaymentmanage.util.Contants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ControlTaskReultActivity extends BaseActivity implements XRefreshView.XRefreshViewListener ,IControlTaskResultActivity {


    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.tv_searchtime)
    TextView tvSearchtime;
    @BindView(R.id.lv_es)
    ListView lvEs;
    @BindView(R.id.xRefreshView)
    XRefreshView xRefreshView;
    private static long lastRefreshTime;
    private String buildingId;
    private String meterType;
    private ControlTaskResultAdpter adpter;
    private ControlTaskResultPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_control_task_reult;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        tvTitleTitlebar.setText("操作记录");
        initRefreshView();
        buildingId = getIntent().getStringExtra("buildingId");
        meterType = getIntent().getStringExtra(Contants.METERTYPE);
        Calendar calendar=Calendar.getInstance();
        tvSearchtime.setText( calendar.get(Calendar.YEAR)+"/"+( calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH));
        adpter=new ControlTaskResultAdpter(new ArrayList<ControlTaskBean.RESPONSEXMLBean>());
        lvEs.setAdapter(adpter);
        presenter=new ControlTaskResultPresenter(this);
        xRefreshView.startRefresh();

    }

    private void initRefreshView() {
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(false);

        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(this);

    }


    @OnClick(R.id.ll_back_titlebar)
    public void onClick() {
        finish();
    }

    @Override
    public void onRefresh() {
     presenter.getData(buildingId,meterType);
    }

    @Override
    public void onLoadMore(boolean isSilence) {

    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public void showData(List<ControlTaskBean.RESPONSEXMLBean> datas) {
        lastRefreshTime=xRefreshView.getLastRefreshTime();
        adpter.update(datas);
    }

    @Override
    public void stopRefresh() {
         xRefreshView.stopRefresh();
    }
}
