package com.northmeter.prepaymentmanage.ui.fragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseFragment;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.model.EventBus.ChooseInfo;
import com.northmeter.prepaymentmanage.presenter.Use_QueryPresenter;
import com.northmeter.prepaymentmanage.ui.CommonDataActivity;
import com.northmeter.prepaymentmanage.ui.i.IUseQueryFragment;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.PromptHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lht
 * on 2016/12/2.
 * des:
 */
public class Use_DayQueryFragment extends BaseFragment implements IUseQueryFragment, DialogInterface.OnDismissListener {

    @BindView(R.id.tv_area_query_use)
    TextView tvAreaQueryUse;
    @BindView(R.id.tv_choosetime_query_use)
    TextView tvChoosetimeQueryUse;
    @BindView(R.id.tv_name_query_use)
    TextView tvNameQueryUse;
    @BindView(R.id.tv_switch_query_use)
    TextView tvSwitchQueryUse;
    @BindView(R.id.rl_contol_query_use)
    RelativeLayout rl_control;
    private String meterType;
    private boolean isFirst;
    private Use_QueryPresenter presenter;
    private Fragment[] fragments;
    private String position;
    private String startDate;
    private String buildingId;
    private Use_ListQueryFragment use_listQueryFragment;
    private Use_FigureQueryFragment use_figureQueryFragment;

    public Use_DayQueryFragment() {

    }

    public static Use_DayQueryFragment newInstance(String meterType) {
        Use_DayQueryFragment fragment = new Use_DayQueryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Contants.METERTYPE, meterType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_commonquery_use;
    }

    @Override
    protected void startGetArgument(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        if (getArguments() != null) {
            meterType = getArguments().getString(Contants.METERTYPE);

        }
    }
    @Override
    protected void finishCreateView(Bundle savedInstanceState) {
        presenter = new Use_QueryPresenter(this, "日度");
        use_listQueryFragment = Use_ListQueryFragment.newInstance(meterType,"日度",startDate,buildingId);
        use_figureQueryFragment = Use_FigureQueryFragment.newInstance(meterType,"日度",startDate,buildingId);
        fragments = new Fragment[]{use_listQueryFragment, use_figureQueryFragment};
        setShowingFragment();


    }

    @OnClick({R.id.tv_choosetime_query_use, R.id.tv_switch_query_use})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_choosetime_query_use:
                //定位到房间
                if (position.equals("room")) {
                    PromptHelper.createTimePicker(getContext(), false, true, true, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            startDate = year + "-" + (monthOfYear+1);
                            tvChoosetimeQueryUse.setText(startDate);

                        }
                    }, this);
                    //定位到楼层
                } else if (position.equals("floor")) {
                    PromptHelper.createTimePicker(getContext(), true, true, true, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            startDate = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
                            tvChoosetimeQueryUse.setText(startDate);
                        }
                    }, this);
                }
                break;
            case R.id.tv_switch_query_use:
               setShowingFragment();
                break;
        }
    }

    @Override
    public void setTvChooseTimeArea(String time, String area,String position,String buildingId) {
        updateUI(time, area, position, buildingId);
        EventBus.getDefault().post(new ChooseInfo("日度",time,buildingId,position));

    }

    private void updateUI(String time, String area, String position, String buildingId) {
        tvAreaQueryUse.setText(area);
        tvChoosetimeQueryUse.setText(time);
        this.position=position;
        this.buildingId=buildingId;
        this.startDate=time;
        if(position.equals("floor")){
            rl_control.setVisibility(View.GONE);
            FragmentTransaction ftc = getChildFragmentManager().beginTransaction();
            if(fragments==null){
                return;
            }
            if(fragments[1].isAdded()){
                ftc.hide(fragments[1]);
            }
            ftc.show(fragments[0]).commit();
           // ((CommonDataActivity) getActivity()).setViewPagerNoScoller(false);
            isFirst = true;
            tvSwitchQueryUse.setText("切换图表");

        }else{
            rl_control.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void setTimeAreaFromSp(String time, String area, String position, String buildingId) {
        updateUI(time, area, position, buildingId);

    }

    @Override
    public void setShowingFragment() {
        FragmentTransaction ftc = getChildFragmentManager().beginTransaction();
        if (isFirst) {
            ftc.hide(fragments[0]);
            if (!fragments[1].isAdded()) {
                fragments[1]= Use_FigureQueryFragment.newInstance(meterType,"日度",startDate,buildingId);
                ftc.add(R.id.fl_container, fragments[1]);
            }
            ftc.show(fragments[1]).commit();
           // ((CommonDataActivity) getActivity()).setViewPagerNoScoller(true);
            isFirst = false;
            tvSwitchQueryUse.setText("切换表格");
        } else {
            ftc.hide(fragments[1]);
            if (!fragments[0].isAdded()) {
                ftc.add(R.id.fl_container, fragments[0]);
            }
            ftc.show(fragments[0]).commit();
           // ((CommonDataActivity) getActivity()).setViewPagerNoScoller(false);
            isFirst = true;
            tvSwitchQueryUse.setText("切换图表");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getSelectArear(AlreadyBuilding info) {
        ((CommonDataActivity) getActivity()).closeDrawerRight();
        presenter.getNowTimeArea("日度", info);
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {
            // use_listQueryFragment.setData(startDate,buildingId);
        EventBus.getDefault().post(new ChooseInfo("日度",startDate,buildingId,position));
    }
}
