package com.northmeter.prepaymentmanage.ui.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.ybq.android.spinkit.SpinKitView;
import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.adapters.SpinnerAdpter;
import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.model.EventBus.AlreadyBuilding;
import com.northmeter.prepaymentmanage.ui.fragments.presenter.SelectFragmentPresenter;
import com.northmeter.prepaymentmanage.ui.fragments.view.SelectBuildingView;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.SharedPreferencesUtil;
import com.northmeter.prepaymentmanage.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * created by lht on 2016/12/19
 * des:区域选择
 */
public class SelectBuildingFragment extends Fragment implements SelectBuildingView<List<RequestBuilding>> {
    @BindView(R.id.spinner_school)
    Spinner spinnerSchool;
    @BindView(R.id.spinner_building)
    Spinner spinnerBuilding;
    @BindView(R.id.spinner_floor)
    Spinner spinnerFloor;
    @BindView(R.id.btn_confirm_selectbuilding)
    Button btnConfirmSelectbuilding;
    @BindView(R.id.spinner_room)
    Spinner spinnerRoom;
    @BindView(R.id.spin_kit_bind_room_loading)
    SpinKitView spinKitBindRoomLoading;
    private String room_id;
    private String floor_id;
    private SelectFragmentPresenter presenter;
    private List<RequestBuilding> schoolArears = new ArrayList<>();
    private List<RequestBuilding> buildings = new ArrayList<>();
    private List<RequestBuilding> floors = new ArrayList<>();
    private List<RequestBuilding> rooms = new ArrayList<>();
    private AlreadyBuilding info = new AlreadyBuilding();
    private View rootView;
    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_select_building, container, false);
        }

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        presenter = new SelectFragmentPresenter(this);

        presenter.getAreaInfo();
        initListener();
    }


    private void initListener() {
        //校区选择----加载建筑
        spinnerSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 // Log.i("LHT", "获取建筑成功 ");
                final RequestBuilding bean = schoolArears.get(i);
                info.school = bean.getArea_NAME();
                presenter.getSonAreaInfo(bean.getArea_ID(), Contants.SELECTEDBUILDING);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//建筑选择----加载楼层
        spinnerBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               // Log.i("LHT", "获取楼层成功 ");
                final RequestBuilding bean = buildings.get(i);
                info.building = bean.getArea_NAME();
                String area_id = bean.getArea_ID();
                presenter.getSonAreaInfo(area_id, Contants.SELECTEDFLOOR);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//楼层选择 ---加载房间
        spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Log.i("LHT", "获取房间成功 ");
                final RequestBuilding bean = floors.get(i);
                floor_id = bean.getArea_ID();
                info.Area_ID = floor_id;
                info.floor = bean.getArea_NAME();
                String area_id = bean.getArea_ID();
                presenter.getSonAreaInfo(area_id, Contants.SELECTEDROOM);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                RequestBuilding bean = rooms.get(i);
                info.room = bean.getArea_NAME();
                room_id = bean.getArea_ID();
                //如果选择了空白
                if (room_id != null && !room_id.equals("")) {
                    info.Area_ID = room_id;
                    //等于上级的楼层id
                } else {
                    info.Area_ID = floor_id;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void bindSpinnerAdpdter(Spinner spinner, List<RequestBuilding> orgData) {
        if (orgData == null) {
            return;
        }
        List<String> data = new ArrayList<>();
        for (int i = 0; i < orgData.size(); i++) {
            final RequestBuilding bean = orgData.get(i);
            data.add(bean.getArea_NAME());
        }
        spinner.setDropDownVerticalOffset(spinner.getHeight());
        spinner.setAdapter(new SpinnerAdpter(data));
    }

    @OnClick(R.id.btn_confirm_selectbuilding)
    public void onClick() {
        if(info.school==null||info.building==null){
            return;
        }

        SharedPreferencesUtil.put(MyApplication.getContext(), Contants.SELECTEDSCHOOL, info.school);
        SharedPreferencesUtil.put(MyApplication.getContext(), Contants.SELECTEDAREA_ID, info.Area_ID);
        SharedPreferencesUtil.put(MyApplication.getContext(), Contants.SELECTEDBUILDING, info.building);
        SharedPreferencesUtil.put(MyApplication.getContext(), Contants.SELECTEDROOM, info.room + "");
        SharedPreferencesUtil.put(MyApplication.getContext(), Contants.SELECTEDFLOOR, info.floor + "");
        EventBus.getDefault().post(info);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void showData(String position, List<RequestBuilding> data) {
        switch (position) {
            case "school":
                schoolArears = data;
                bindSpinnerAdpdter(spinnerSchool, data);
                break;
            case Contants.SELECTEDBUILDING:
                buildings = data;
                bindSpinnerAdpdter(spinnerBuilding, data);
                break;
            case Contants.SELECTEDFLOOR:
                floors = data;
                bindSpinnerAdpdter(spinnerFloor, data);
                break;
            case Contants.SELECTEDROOM:
                rooms = data;
                rooms.add(0, new RequestBuilding());
                bindSpinnerAdpdter(spinnerRoom, rooms);
                break;

        }
    }

    @Override
    public void showProgress() {

        spinKitBindRoomLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        spinKitBindRoomLoading.setVisibility(View.GONE);

    }

    public void toast(String msg) {
        ToastUtil.showShort(getContext(), msg);
    }


}
