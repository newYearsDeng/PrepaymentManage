package com.northmeter.prepaymentmanage.ui.GateLock.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.ui.GateLock.model.ReadDayLogBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by dyd on 2017/7/1.
 */
public class Read_DayLogAdapter extends BaseAdapter {
    private List<ReadDayLogBean.RESPONSEXMLBean> datas;

    public Read_DayLogAdapter(List<ReadDayLogBean.RESPONSEXMLBean> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(view==null){
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_four_listview,viewGroup,false);
            vh=new ViewHolder();
            vh.tv1= (TextView) view.findViewById(R.id.tv1_item_four);
            vh.tv2= (TextView) view.findViewById(R.id.tv2_item_four);
            vh.tv3= (TextView) view.findViewById(R.id.tv3_item_four);
            vh.tv4= (TextView) view.findViewById(R.id.tv4_item_four);
            vh.tv4.setVisibility(View.GONE);
            view.setTag(vh);
            AutoUtils.auto(view);
        }else{
            vh= (ViewHolder) view.getTag();

        }
        setUI(vh,i);
        return view;
    }

    private void setUI(ViewHolder vh, int i) {
        final ReadDayLogBean.RESPONSEXMLBean data = datas.get(i);
        vh.tv1.setText(data.getBuildingName());
        vh.tv2.setText(data.getUnlockTime());
        vh.tv3.setText(data.getUnlockWay());
    }

    private class ViewHolder{
        TextView tv1,tv2,tv3,tv4;
    }

    public void update(List<ReadDayLogBean.RESPONSEXMLBean> newDatas){
        datas.clear();
        datas.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void add(List<ReadDayLogBean.RESPONSEXMLBean> newData){
        if(datas.contains(newData)){
            return;
        }
        datas.addAll(newData);
        notifyDataSetChanged();
    }
}

