package com.northmeter.prepaymentmanage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class PopListViewAdpter extends BaseAdapter {
    private List<String> times;
    public PopListViewAdpter(List<String> times) {
       this.times=times;
    }

    @Override
    public int getCount() {
        return times.size();
    }

    @Override
    public Object getItem(int i) {
        return times.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listview_item_pop,viewGroup,false);
            vh.tv= (TextView) view.findViewById(R.id.tv_lv_item_pop);
            view.setTag(vh);
            AutoUtils.autoSize(view);
        }else{
            vh= (ViewHolder) view.getTag();
        }
            vh.tv.setText(times.get(i));
        return view;
    }
    private class ViewHolder{
        TextView tv;
    }
}
