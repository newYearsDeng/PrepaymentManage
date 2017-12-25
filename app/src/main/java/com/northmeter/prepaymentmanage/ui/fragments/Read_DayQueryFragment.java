package com.northmeter.prepaymentmanage.ui.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.ReadQueryAdapter;
import com.northmeter.prepaymentmanage.base.BaseFragment;
import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.presenter.Read_QueryPresenter;
import com.northmeter.prepaymentmanage.ui.CommonDataActivity;
import com.northmeter.prepaymentmanage.ui.i.IQueryFragment;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.PromptHelper;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Lht
 * on 2016/12/2.
 * des: 抄表数据查询（日查询）
 */
public class Read_DayQueryFragment extends BaseFragment implements IQueryFragment, XRefreshView.XRefreshViewListener, DialogInterface.OnDismissListener {
    @BindView(R.id.tv_area_commonquery)
    TextView tvAreaCommonquery;
    @BindView(R.id.tv_choosetime)
    TextView tvChoosetime;
    @BindView(R.id.tva_commonquery)
    TextView tvaCommonquery;
    @BindView(R.id.tvb_commonquery)
    TextView tvbCommonquery;
    @BindView(R.id.tvc_commonquery)
    TextView tvcCommonquery;
    @BindView(R.id.lv_commonquery)
    ListView lvCommonquery;
    @BindView(R.id.xRefreshView_commonquery)
    XRefreshView xRefreshView;
    private String meterType;

    private String[] titles;
    private ReadQueryAdapter cAdpter;
    //定位的级别
    private String buildingId;
    private String position;
    private String startDate;
    private int pageIndex = 0;
    private int pageSize = 10;
    private String area;
    public static long lastRefreshTime;

    private Read_QueryPresenter presenter;

    public Read_DayQueryFragment() {
    }

    public static Read_DayQueryFragment newInstance(String type) {
        Read_DayQueryFragment fragment = new Read_DayQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Contants.METERTYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_commonquery_read;
    }

    @Override
    protected void startGetArgument(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        presenter = new Read_QueryPresenter(this, "日度");

        if (getArguments() != null) {
            meterType = getArguments().getString(Contants.METERTYPE);
            presenter.initialdata(meterType);
        }

    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        //设置显示数据
        setView();
        LoadingData();
    }

    private void setView() {
        tvaCommonquery.setText(titles[0]);
        tvbCommonquery.setText(titles[1]);// TODO: 2016/12/2/11:00 线路名称跟结算量对换位置了
        tvcCommonquery.setText(titles[2]);
        tvChoosetime.setText(startDate);
        lvCommonquery.setAdapter(cAdpter);
        tvAreaCommonquery.setText(area);
        initXRefreshView();
    }

    @OnClick(R.id.tv_choosetime)
    public void onClick() {
        //定位到房间
        if (position.equals("room")) {
            PromptHelper.createTimePicker(getContext(), false, true, true, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    startDate = year + "-" + (monthOfYear+1);
                    tvChoosetime.setText(startDate);
                }
            }, this);
            //定位到楼层
        } else if (position.equals("floor")) {
            PromptHelper.createTimePicker(getContext(), true, true, true, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    startDate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                    tvChoosetime.setText(startDate);
                }
            }, this);
        }
    }


    @Override
    public void showDayDatas(int xRefreshType, List<?> meterDatas) {
        if(xRefreshType==0){
            lastRefreshTime = xRefreshView.getLastRefreshTime();
            cAdpter.update((List<BuildMeterReadData.RESPONSEXMLBean>) meterDatas);
        }else{
            cAdpter.add((List<BuildMeterReadData.RESPONSEXMLBean>) meterDatas);
        }
    }

    @Override
    public void showinitialdata(String startData, String postion, String area, String buildingId, String[] titles, BaseAdapter cAdpter) {
        this.startDate = startData;
        this.position = postion;
        this.area = area;
        this.buildingId = buildingId;
        this.titles = titles;
        this.cAdpter = (ReadQueryAdapter) cAdpter;
    }

    @Override
    public void initXRefreshView() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(this);
    }



    @Override
    public void stopRefresh() {
        if (xRefreshView != null) {
            xRefreshView.stopRefresh();
            xRefreshView.stopLoadMore(true);
        }
    }

    @Override
    public void setTvChooseTimeArea(String time, String area, String position, String building) {
        tvChoosetime.setText(time);
        tvAreaCommonquery.setText(area);
        this.position = position;
        this.buildingId = building;
        this.startDate = time;
        //重新加载
        againLoading();
    }

    private void againLoading() {
        pageIndex = 0;
        cAdpter.clear();
        LoadingData();
    }

    @Override
    public void LoadingData() {
        if (buildingId == null || buildingId.equals("") || buildingId.equals("null")) {
            //ToastUtil.showShort(getContext(), "查询时建筑不能为空");
            return;
        }
        xRefreshView.startRefresh();
    }

    @Override
    public void onRefresh() {
        pageIndex=0;
        presenter.getData(0,buildingId, "日度", startDate, meterType, ++pageIndex, pageSize);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        presenter.getData(1,buildingId, "日度", startDate, meterType, ++pageIndex, pageSize);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getSelectArear(AlreadyBuilding info) {
        ((CommonDataActivity) getActivity()).closeDrawerRight();

        presenter.getNowTimeArea("日度", info);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        againLoading();
    }


}
