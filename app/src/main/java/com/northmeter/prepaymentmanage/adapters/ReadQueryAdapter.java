package com.northmeter.prepaymentmanage.adapters;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.BuildMeterReadData;
import com.northmeter.prepaymentmanage.model.BuildMeterUseData;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Lht
 * on 2016/12/5.
 * des:
 */
public class ReadQueryAdapter extends BaseAdapter {
    private List<BuildMeterReadData.RESPONSEXMLBean> infoList;

    /**
     * @param infoList
     */
    public ReadQueryAdapter(List<BuildMeterReadData.RESPONSEXMLBean> infoList) {
        this.infoList = infoList;

    }

    public interface onClickQueryAdapterListener {
        void click(BuildMeterUseData.RESPONSEXMLBean bean);
    }

    private onClickQueryAdapterListener listener;

    public void setOnClickQueryListener(onClickQueryAdapterListener listener) {
        this.listener = listener;
    }
    public void clear(){
        infoList.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoList.get(i);
    }



    public void update(List<BuildMeterReadData.RESPONSEXMLBean> newData) {
         infoList.clear();
        if(newData!=null){
            infoList.addAll(newData);
        }
        notifyDataSetChanged();

    }
    public void add(List<BuildMeterReadData.RESPONSEXMLBean> newData){
        if(infoList.contains(newData)){
            return;
        }
        infoList.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder vh = null;
        if (view == null) {
            vh = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listview_item_cquery, viewGroup, false);
            vh.tva = (TextView) view.findViewById(R.id.tv1_cq);
            vh.tvb = (TextView) view.findViewById(R.id.tv2_cq);
            vh.tvc = (TextView) view.findViewById(R.id.tv3_cq);
            view.setTag(vh);
            AutoUtils.autoSize(view);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        setUI(vh, i);
        return view;
    }


    private void setUI(ViewHolder vh, int position) {
            final BuildMeterReadData.RESPONSEXMLBean bean = infoList.get(position);
            vh.tva.setText(bean.getDataItemValueTime());
            vh.tvb.setText(bean.getBuildingName());
            vh.tvc.setText(bean.getTotalactiveTotal());
    }

    private class ViewHolder {
        private TextView tva;
        private TextView tvb;
        private TextView tvc;
    }
}
