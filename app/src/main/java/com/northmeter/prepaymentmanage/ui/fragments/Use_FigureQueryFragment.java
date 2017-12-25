package com.northmeter.prepaymentmanage.ui.fragments;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseFragment;
import com.northmeter.prepaymentmanage.model.BuildMeterUseData;
import com.northmeter.prepaymentmanage.model.EventBus.ChooseInfo;
import com.northmeter.prepaymentmanage.presenter.Use_Query_ListPresenter;
import com.northmeter.prepaymentmanage.ui.i.IUseQueryListFragment;
import com.northmeter.prepaymentmanage.util.Contants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class Use_FigureQueryFragment extends BaseFragment implements IUseQueryListFragment {
    @BindView(R.id.linechart__useTotal)
    LineChart mLcUseTotal;
    @BindView(R.id.linechart_moneyTotal)
    LineChart mLcMoneyTotal;
    private String meterType;
    private String dateType;
    private String startDate;
    private String buildingId;
    private int pageSize = 31;
    private int pageIndex = 1;
    private Use_Query_ListPresenter presenter;
    private int mWidth;
    private int mHeight;
    private String unit;//单位

    public static Use_FigureQueryFragment newInstance(String meterType, String dateType, String startDate, String buildingId) {
        Use_FigureQueryFragment fragment = new Use_FigureQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Contants.METERTYPE, meterType);
        bundle.putString(Contants.DAY_TYPE, dateType);
        bundle.putString(Contants.STARTDATE, startDate);
        bundle.putString(Contants.SELECTEDAREA_ID, buildingId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_commonquery_use_figure;
    }

    @Override
    protected void startGetArgument(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        final Bundle arguments = getArguments();
        if (arguments != null) {
            meterType = arguments.getString(Contants.METERTYPE);
            dateType = arguments.getString(Contants.DAY_TYPE);
            startDate = arguments.getString(Contants.STARTDATE);
            buildingId = arguments.getString(Contants.SELECTEDAREA_ID);
        }
    }

    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        if (meterType.equals("水")) {
            unit = "m³";
        }else{
            unit="kwh";
        }
        //测量图表的宽高
        WindowManager wm = getActivity().getWindowManager();
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        mWidth = point.x;
        mHeight = point.y;

        presenter = new Use_Query_ListPresenter(this);
        if (!buildingId.equals("")) {
            presenter.getData(0,buildingId, dateType, startDate, meterType, pageIndex, pageSize);
        }

    }

    /**
     * 生成一个数据
     *
     * @param
     * @return
     */
    private LineData getLineData(List<String> xValues, List<String> totalDatas, int color, String name) {

        //y轴数据
        ArrayList<Entry> yValues = new ArrayList<>();
        for (int i = 0; i < xValues.size(); i++) {
            final Float val = Float.valueOf(totalDatas.get(i));
           // Log.i("LHT","Y "+(float)(Math.round(val*100))/100);
            yValues.add(new Entry((float)(Math.round(val*100))/100, i));

        }

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, name);
        // mLineDataSet.setFillAlpha(110);
        // mLineDataSet.setFillColor(Color.RED);

        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(3f);// 显示的圆形大小
        lineDataSet.setColor(color);// 显示颜色
        lineDataSet.setCircleColor(color);// 圆形的颜色
        lineDataSet.setHighLightColor(color); // 高亮的线的颜色
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setDrawCircleHole(false);

        // 改变折线样式，用曲线。
//        lineDataSet.setDrawCubic(true);//平滑的曲线
        // 曲线的平滑度，值越大越平滑。
        lineDataSet.setCubicIntensity(0.2f);

        // 填充曲线下方的区域，红色，半透明。
//        lineDataSet.setDrawFilled(true);
//        lineDataSet.setFillAlpha(128);
//        lineDataSet.setFillColor(Color.RED);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet); // add the datasets

        // create a data object with the datasets
        LineData lineData = new LineData(xValues, lineDataSets);

        return lineData;
    }

    @Override
    public void showDayDatas(int type,List<BuildMeterUseData.RESPONSEXMLBean> meterDatas) {
        if (meterDatas == null) {
            mLcUseTotal.setVisibility(View.INVISIBLE);
            mLcMoneyTotal.setVisibility(View.INVISIBLE);
            return;
        }
        mLcUseTotal.setVisibility(View.VISIBLE);
        mLcMoneyTotal.setVisibility(View.VISIBLE);
        List<String> xVaues = new ArrayList<>();
        List<String> yTotal = new ArrayList<>();
        List<String> yMoney = new ArrayList<>();
        for (int i = meterDatas.size() - 1; i >= 0; i--) {
            final BuildMeterUseData.RESPONSEXMLBean bean = meterDatas.get(i);
            xVaues.add(bean.getDataItemValueTime().split(" ")[0]);
            final String totalactiveTotal = bean.getTotalactiveTotal();
            yTotal.add(totalactiveTotal.equals("") ? "0.00" : totalactiveTotal);
            final String cTotalactiveTotalMoney = bean.getCTotalactiveTotalMoney();
            yMoney.add(cTotalactiveTotalMoney.equals("") ? "0.00" : cTotalactiveTotalMoney);
        }

        LineData mLineData = getLineData(xVaues, yTotal, Color.parseColor("#f7931e"), "用" + meterType + "量");
        showChart(mLcUseTotal, mLineData, xVaues, unit);

        LineData mLineData1 = getLineData(xVaues, yMoney, Color.parseColor("#00BCD4"), "用" + meterType + "金额");
        showChart(mLcMoneyTotal, mLineData1, xVaues, "元");
    }

    private void showChart(LineChart lineChart, LineData lineData, List<String> xValues, String unit) {

        lineChart.setDrawBorders(false);  //是否在折线图上添加边框
        // no description text
        lineChart.setDescription("");// 数据描述
        if (xValues.size() == 0) {

            lineChart.setDescription("没有数据可供显示");// 数据描述
            lineChart.setDescriptionPosition((float) (mWidth / (1.5)), (float) (mHeight / 4.5));
            lineChart.setDescriptionTextSize(16);
        } else {
            lineChart.setDescription("单位：" + unit);
            lineChart.setDescriptionTextSize(14);
        }
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
//        lineChart.setNoDataTextDescription("You need to provide data for the chart.");


        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        lineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        lineChart.getXAxis().setGridColor(getResources().getColor(R.color.transparent));
        lineChart.getXAxis().setSpaceBetweenLabels(1);

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        lineChart.setDragEnabled(true);// 是否可以拖拽
        lineChart.setScaleEnabled(true);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//

//        lineChart.setBackgroundColor(Color.WHITE);// 设置背景

        // add data
        lineData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return new DecimalFormat("0.00").format(value);
            }
        });
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
        // modify the legend ...
//         mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(12f);// 样式大小
        mLegend.setTextColor(Color.BLACK);// 颜色
//      mLegend.setTypeface(mTf);// 字体
        mLegend.setTextSize(14f);

        lineChart.animateX(2500); // 立即执行的动画,x轴



    }


    @Override
    public void stopRefresh() {


    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getSelectArear(ChooseInfo info) {
        if (info.positioin.equals("floor")) {
            return;
        }
        if (dateType.equals(info.dateType)) {
            this.buildingId = info.buildingID;
            this.startDate = info.time;
            presenter.getData(0,buildingId, dateType, startDate, meterType, pageIndex, pageSize);
        }
    }


}
