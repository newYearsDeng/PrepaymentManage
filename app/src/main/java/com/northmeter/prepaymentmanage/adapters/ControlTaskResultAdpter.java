package com.northmeter.prepaymentmanage.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.ControlTaskBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/25.
 */
public class ControlTaskResultAdpter extends BaseAdapter {
    private List<ControlTaskBean.RESPONSEXMLBean> datas;

    public ControlTaskResultAdpter(List<ControlTaskBean.RESPONSEXMLBean> datas) {
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
    public void update(List<ControlTaskBean.RESPONSEXMLBean> newDatas){
        datas.clear();
        datas.addAll(newDatas);
        notifyDataSetChanged();
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
            view.setTag(vh);
            AutoUtils.auto(view);
        }else{
            vh= (ViewHolder) view.getTag();

        }
        setUI(vh,i);
        return view;
    }

    private void setUI(ViewHolder vh, int i) {
        final ControlTaskBean.RESPONSEXMLBean data = datas.get(i);
        vh.tv1.setText(data.getLINENAME());
        vh.tv2.setText(data.getControlTime());
        vh.tv3.setText(data.getControlType());
        final String result = data.getResult();
        vh.tv4.setText(result);
        if(result.equals("成功")){
            vh.tv4.setTextColor(Color.parseColor("#00bcd4"));
        }else{
            vh.tv4.setTextColor(Color.parseColor("#f4544d"));
        }
    }

    private class ViewHolder{
        TextView tv1,tv2,tv3,tv4;
    }
}
