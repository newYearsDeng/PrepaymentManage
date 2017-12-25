package com.northmeter.prepaymentmanage.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
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
 * Created by lht on 2016/11/7.
 */
public class QueryAdapter extends BaseAdapter {
    private List<Object> infoList;
    private boolean showMore;
    private int type;

    /**
     * @param infoList
     * @param type     0抄表数据  1.用水/电数据
     * @param showMore
     */
    public QueryAdapter(List<Object> infoList, int type, boolean showMore) {
        this.infoList = infoList;
        this.type = type;
        this.showMore = showMore;
    }

    public interface onClickQueryAdapterListener {
        void click(BuildMeterUseData.RESPONSEXMLBean bean);
    }

    private onClickQueryAdapterListener listener;

    public void setOnClickQueryListener(onClickQueryAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoList.get(i);
    }

    public void clear() {
        infoList.clear();
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
            vh.tvd = (TextView) view.findViewById(R.id.tv4_cq);
            view.setTag(vh);
            AutoUtils.autoSize(view);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        setUI(vh, i);
        return view;
    }

    private void setUI(ViewHolder vh, int position) {
        if (type == 0) {
            final BuildMeterReadData.RESPONSEXMLBean bean = (BuildMeterReadData.RESPONSEXMLBean) infoList.get(position);
            vh.tva.setText(bean.getDataItemValueTime());
            vh.tvb.setText(bean.getBuildingName());
            vh.tvc.setText(bean.getTotalactiveTotal());
        } else {
            final BuildMeterUseData.RESPONSEXMLBean bean = (BuildMeterUseData.RESPONSEXMLBean) infoList.get(position);
            vh.tva.setText(bean.getDataItemValueTime());
            vh.tvd.setText(bean.getTotalactiveTotal());//TODO 2016/12/2/11:00 线路名称跟结算量对换位置了
            vh.tvc.setText(bean.getCTotalactiveTotalMoney());
            vh.tvd.setVisibility(View.VISIBLE);
            vh.tvb.setText(bean.getBuildingName());//
            if (showMore) {
                Drawable drawable = MyApplication.getContext().getResources().getDrawable(R.drawable.icon_more);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                vh.tvc.setCompoundDrawables(null, null, drawable, null);
                vh.tvc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.click(bean);
                    }
                });
            }
        }
    }

    private class ViewHolder {
        private TextView tva;
        private TextView tvb;
        private TextView tvc;
        private TextView tvd;
    }


}
