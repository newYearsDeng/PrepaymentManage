package com.northmeter.prepaymentmanage.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.UseQueryAdapter;
import com.northmeter.prepaymentmanage.base.BaseFragment;
import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.BuildMeterUseData;
import com.northmeter.prepaymentmanage.model.EventBus.ChooseInfo;
import com.northmeter.prepaymentmanage.presenter.Use_Query_ListPresenter;
import com.northmeter.prepaymentmanage.ui.i.IUseQueryListFragment;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.ToastUtil;
import com.zhy.autolayout.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class Use_ListQueryFragment extends BaseFragment implements XRefreshView.XRefreshViewListener, IUseQueryListFragment, UseQueryAdapter.onClickQueryAdapterListener {

    @BindView(R.id.tva_use_list)
    TextView tvaUseList;
    @BindView(R.id.tvb_use_list)
    TextView tvbUseList;
    @BindView(R.id.tvc__use_list)
    TextView tvcUseList;
    @BindView(R.id.tvd_use_list)
    TextView tvdUseList;
    @BindView(R.id.lv_use_list)
    ListView lvUseList;
    @BindView(R.id.xRefreshView_use_list)
    XRefreshView xRefreshView;

    private Use_Query_ListPresenter presenter;
    public String meterType, dayType;
    public static long lastRefreshTime;
    private String startDate;
    private String buildingId;

    private int pageIndex = 0;
    private int pageSize = 10;

    private UseQueryAdapter adapter;


    public Use_ListQueryFragment() {
    }

    public static Use_ListQueryFragment newInstance(String type, String dayType, String startDate, String buildingId) {
        Use_ListQueryFragment fragment = new Use_ListQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Contants.METERTYPE, type);
        bundle.putString(Contants.DAY_TYPE, dayType);
        bundle.putString(Contants.SELECTEDAREA_ID, buildingId);
        bundle.putString(Contants.STARTDATE, startDate);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_commonquery_use_list;
    }

    @Override
    protected void startGetArgument(Bundle savedInstanceState) {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            meterType = arguments.getString(Contants.METERTYPE);
            dayType = arguments.getString(Contants.DAY_TYPE);
            buildingId = arguments.getString(Contants.SELECTEDAREA_ID);
            startDate = arguments.getString(Contants.STARTDATE);
        }
        presenter = new Use_Query_ListPresenter(this);
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        if (dayType.equals("日度")) {
            adapter = new UseQueryAdapter(new ArrayList<BuildMeterUseData.RESPONSEXMLBean>(), false, meterType);
        } else {
            adapter = new UseQueryAdapter(new ArrayList<BuildMeterUseData.RESPONSEXMLBean>(), true, meterType);
        }
        String titiles[] = getResources().getStringArray(R.array.commonfragment_title_1);
        tvaUseList.setText(titiles[0]);
        tvbUseList.setText(titiles[1]);
        tvcUseList.setText(titiles[2]);
        tvdUseList.setText(titiles[3]);
        initXRefreshView();

        lvUseList.setAdapter(adapter);
        adapter.setOnClickQueryListener(this);
        if (buildingId == null || buildingId.equals("") || buildingId.equals("null")) {
            return;
        }
        xRefreshView.startRefresh();
    }

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
    public void showDayDatas(int xRefreshType, List<BuildMeterUseData.RESPONSEXMLBean> meterDatas) {
        Log.i("LHT","xRefreshType "+xRefreshType+ " "+dayType);
        for (BuildMeterUseData.RESPONSEXMLBean meterData : meterDatas) {
            Log.i("LHT",meterData.toString());
        }
        Log.i("LHT","---------------------");
        if(xRefreshType==0){
            lastRefreshTime = xRefreshView.getLastRefreshTime();
            adapter.update(meterDatas);
        }else{
            adapter.add(meterDatas);
        }
    }

    @Override
    public void stopRefresh() {
        if (xRefreshView != null) {
            xRefreshView.stopRefresh();
            xRefreshView.stopLoadMore(true);
        }

    }


    @Override
    public void onRefresh() {
        if (buildingId.equals("null") || buildingId.equals("")) {
            ToastUtil.showShort(getContext(), "请先择建筑");
            xRefreshView.stopRefresh();
            return;
        }
        pageIndex = 0;
        presenter.getData(0,buildingId, dayType, startDate, meterType, ++pageIndex, pageSize);
    }

    @Override
    public void onLoadMore(boolean isSilence) {
        presenter.getData(1,buildingId, dayType, startDate, meterType, ++pageIndex, pageSize);
    }

    @Override
    public void onRelease(float direction) {

    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getSelectArear(ChooseInfo info) {
        if (!info.dateType.equals(dayType)) {
            return;
        }
        pageIndex = 0;
        startDate = info.time;
        buildingId = info.buildingID;
        adapter.clear();
        xRefreshView.startRefresh();
    }

    @Override
    public void click(BuildMeterUseData.RESPONSEXMLBean bean) {
        if (bean != null) {
            createDialog(bean);
        }

    }

    private void createDialog(BuildMeterUseData.RESPONSEXMLBean meter) {
        String unit = "(m³)";
        final Dialog dialog = new Dialog(getContext(), R.style.MyDialog1);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_dialog_moreinfo, null);
        ((ImageButton) view.findViewById(R.id.ib_cancle_dialog)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((TextView) view.findViewById(R.id.tv_routeName_dialog)).setText(meter.getBuildingName());
        ((TextView) view.findViewById(R.id.tv_jsy_dialog)).setText(meter.getDataItemValueTime());
        if (meterType.equals("电")) {
            unit = "(kwh)";
        }
        final TextView tv1 = (TextView) view.findViewById(R.id.tv_qcbd_dialog_name);
        tv1.setText(tv1.getText() + unit);
        final TextView tv2 = (TextView) view.findViewById(R.id.tv_qmbd_dialog_name);
        tv2.setText(tv2.getText() + unit);
        ((TextView) view.findViewById(R.id.tv_qcbd_dialog)).setText(meter.getSDataItemValue());
        ((TextView) view.findViewById(R.id.tv_qmbd_dialog)).setText(meter.getEDataItemValue());
        final TextView tv3 = (TextView) view.findViewById(R.id.tv_jsydl_dialog_name);
        tv3.setText(tv3.getText() + unit);
        ((TextView) view.findViewById(R.id.tv_jsydl_dialog)).setText(meter.getTotalactiveTotal());
        ((TextView) view.findViewById(R.id.tv_isje_dialog)).setText(meter.getCTotalactiveTotalMoney());
        AutoUtils.autoSize(view);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        params.width = (int) (displayMetrics.widthPixels / 1.5);
        params.height = (int) (displayMetrics.heightPixels / 2);
        dialog.getWindow().setAttributes(params);
    }
}
