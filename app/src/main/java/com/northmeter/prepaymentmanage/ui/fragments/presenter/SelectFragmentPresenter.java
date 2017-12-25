package com.northmeter.prepaymentmanage.ui.fragments.presenter;

import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;
import com.northmeter.prepaymentmanage.util.net.LoadDataListener;
import com.northmeter.prepaymentmanage.ui.fragments.model.SelectFragmentModel;
import com.northmeter.prepaymentmanage.ui.fragments.view.SelectBuildingView;
import com.northmeter.prepaymentmanage.util.Contants;

import java.util.List;

/**
 * Created by Lht
 * on 2016/12/19.
 * des:
 */
public class SelectFragmentPresenter {
    private SelectBuildingView<List<RequestBuilding>> view;
    private SelectFragmentModel model;

    public SelectFragmentPresenter(SelectBuildingView view) {
        this.view = view;
        model = new SelectFragmentModel();
    }

    public void getAreaInfo() {
        view.showProgress();
        model.getAreaInfo(new LoadDataListener<List<RequestBuilding>>() {

            @Override
            public void LoadSucess(List<RequestBuilding> data) {
                view.showData("school",data);
            }

            @Override
            public void LoadFail(String e) {
                view.toast(e);
                view.hideProgress();
            }
        });
    }

    public void getSonAreaInfo(String areaId, final String key) {
       /* Log.i("LHT","areaId "+areaId);
        Log.i("LHT","key "+key);
        Log.i("LHT","---------------");*/

        model.getSonInfo(areaId, key, new LoadDataListener<List<RequestBuilding>>() {


            @Override
            public void LoadSucess(List<RequestBuilding> data) {
                if (key.equals(Contants.SELECTEDROOM)) {
                    view.hideProgress();
                }
                view.showData(key,data);
            }

            @Override
            public void LoadFail(String e) {
                view.toast(e);
                view.hideProgress();
            }
        });
    }
}
