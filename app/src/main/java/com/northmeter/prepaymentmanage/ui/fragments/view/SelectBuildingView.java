package com.northmeter.prepaymentmanage.ui.fragments.view;

import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;

import java.util.List;

/**
 * Created by Lht
 * on 2016/12/19.
 * des:
 */
public interface SelectBuildingView<T> {

    void showData(String position,T data);

    void showProgress();
    void hideProgress();

    void toast(String msag);
}
