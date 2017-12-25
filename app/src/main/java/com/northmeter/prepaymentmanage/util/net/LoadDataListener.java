package com.northmeter.prepaymentmanage.util.net;

import com.northmeter.prepaymentmanage.model.Entity.RequestBuilding;

import java.util.List;

/**
 * Created by Lht
 * on 2016/12/19.
 * des:
 */
public interface LoadDataListener<T> {

    void LoadSucess(T data);
    void LoadFail(String e);
}
