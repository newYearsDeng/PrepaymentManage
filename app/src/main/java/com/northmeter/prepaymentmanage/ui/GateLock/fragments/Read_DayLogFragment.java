package com.northmeter.prepaymentmanage.ui.GateLock.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseFragment;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.ui.GateLock.MainOfLogqueryActivity;
import com.northmeter.prepaymentmanage.ui.GateLock.adapter.Read_DayLogAdapter;
import com.northmeter.prepaymentmanage.ui.GateLock.i.IRead_DayLogShow;
import com.northmeter.prepaymentmanage.ui.GateLock.model.ReadDayLogBean;
import com.northmeter.prepaymentmanage.ui.GateLock.presenter.Read_DayLogPresenter;
import com.northmeter.prepaymentmanage.util.AES;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.PromptHelper;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by dyd on 2017/6/24.
 * 日志查询fragment
 */
public class Read_DayLogFragment extends BaseFragment implements XRefreshView.XRefreshViewListener,DialogInterface.OnDismissListener,IRead_DayLogShow {
    @BindView(R.id.tv_area_commonquery)
    TextView tvAreaCommonquery;
    @BindView(R.id.tva_commonquery)
    TextView tvaCommonquery;
    @BindView(R.id.tvb_commonquery)
    TextView tvbCommonquery;
    @BindView(R.id.tvc_commonquery)
    TextView tvcCommonquery;
    @BindView(R.id.xRefreshView_commonquery)
    XRefreshView xRefreshView;
    private static long lastRefreshTime;
    @BindView(R.id.lv_commonquery)
    ListView lvCommonquery;
    @BindView(R.id.tv_choosetime)
    TextView tvChoosetime;
    Unbinder unbinder;
    private Read_DayLogAdapter adapter;

    private Read_DayLogPresenter read_dayLogPresenter;

    private int pageIndex = 0;
    private int pageSize = 10;
    //定位的级别
    private String buildId=null;
    private String position=null;
    private String startDate=null;

    private  String power;//用户级别

    private String[] titles = new String[]{"建筑地址","操作时间","操作方式"};


    public static Read_DayLogFragment newInstance(String power) {
        Read_DayLogFragment fragment = new Read_DayLogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("power", power);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_daylog_read;
    }

    @Override
    protected void startGetArgument(Bundle savedInstanceState) {
        try{
            EventBus.getDefault().register(this);
            read_dayLogPresenter = new Read_DayLogPresenter(Read_DayLogFragment.this);
            if (getArguments() != null) {
                power = getArguments().getString("power");
                if(power.equals("manager")){
                    read_dayLogPresenter.initialdata();
                }else{
                    startDate = new SimpleDateFormat("yyyy-MM").format(Calendar.getInstance().getTime());

                    String spDecrypeId_1 = (String) SharedPreferencesUtil.get(MyApplication.getContext(), Contants.SP_AES_ID_KEY, "");
                    //根据id取出buidingid
                    String spDecrypeBuildingId_1 = (String) SharedPreferencesUtil.get(MyApplication.getContext(), spDecrypeId_1, "");
                    buildId = AES.decrypt(spDecrypeBuildingId_1, Contants.SP_AES_BUILDING_KEY);
                    System.out.println("selectedArea_ID==="+buildId);
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        init_view();
    }

    private void init_view() {
        tvAreaCommonquery.setText("操作日志查询");
        tvaCommonquery.setText(titles[0]);
        tvbCommonquery.setText(titles[1]);// TODO: 2016/12/2/11:00 线路名称跟结算量对换位置了
        tvcCommonquery.setText(titles[2]);
        tvChoosetime.setText(startDate);

        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        // 设置时候可以自动刷新
        xRefreshView.setAutoRefresh(false);
        xRefreshView.setXRefreshViewListener(this);

        adapter = new Read_DayLogAdapter(new ArrayList<ReadDayLogBean.RESPONSEXMLBean>());
        lvCommonquery.setAdapter(adapter);

        xRefreshView.startRefresh();

    }


    @Override
    public void onRefresh() {
        pageIndex = 0;
        read_dayLogPresenter.getData(0,buildId,"月度",startDate,"门锁",++pageIndex,pageSize);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        read_dayLogPresenter.getData(1,buildId,"月度",startDate,"门锁",++pageIndex,pageSize);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_choosetime)
    public void onViewClicked() {
        PromptHelper.createTimePicker(getContext(), false, true, true, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                startDate = year + "-" + (monthOfYear+1);
                tvChoosetime.setText(startDate);
                xRefreshView.startRefresh();
            }
        }, this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    @Override
    public void showData(int xRefreshType,List<ReadDayLogBean.RESPONSEXMLBean> datas) {
        if(xRefreshType==0){
            lastRefreshTime = xRefreshView.getLastRefreshTime();
            adapter.update(datas);
        }else{
            adapter.add(datas);
        }
        lastRefreshTime=xRefreshView.getLastRefreshTime();
    }

    @Override
    public void stopRefresh() {
        xRefreshView.stopRefresh();
    }

    @Override
    public void showinitialdata(String startData, String postion, String area, String building) {
        this.buildId  = building;
        this.startDate = startData;
        this.position = postion;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onEvent(AlreadyBuilding info) {
        ((MainOfLogqueryActivity) getActivity()).closeDrawerRight();
        System.out.println("建筑  "+info.Area_ID);

        buildId = info.Area_ID;
        pageIndex = 1;
        xRefreshView.startRefresh();

    }
}
