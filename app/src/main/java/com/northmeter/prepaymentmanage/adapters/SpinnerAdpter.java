package com.northmeter.prepaymentmanage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by lht on 2016/11/11.
 */
public class SpinnerAdpter extends BaseAdapter {
    private List<String> data;

    public SpinnerAdpter(List<String> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void update(List<String> newData ){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_spinner_item,viewGroup,false);
            vh.tv= (TextView) view.findViewById(R.id.tv_spinner);
            AutoUtils.autoSize(view);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.tv.setText(data.get(i));
        return view;
    }

    class ViewHolder {
        TextView tv;
    }
}
