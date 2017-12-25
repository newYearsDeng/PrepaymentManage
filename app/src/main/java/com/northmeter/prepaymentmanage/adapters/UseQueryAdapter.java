package com.northmeter.prepaymentmanage.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
public class UseQueryAdapter extends BaseAdapter {
    private List<BuildMeterUseData.RESPONSEXMLBean> infoList;
    private boolean showMore;
    private String meterType;

    /**
     * @param infoList
     */
    public UseQueryAdapter(List<BuildMeterUseData.RESPONSEXMLBean> infoList, boolean showMore, String meterType) {
        this.infoList = infoList;
        this.showMore = showMore;
        this.meterType = meterType;
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

    public void update(List<BuildMeterUseData.RESPONSEXMLBean> newData) {
        infoList.clear();
        if(newData!=null){
            infoList.addAll(newData);
        }
        notifyDataSetChanged();

    }
    public void add(List<BuildMeterUseData.RESPONSEXMLBean> newData){
        if (!infoList.contains(newData)) {
            infoList.addAll(newData);
        }
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listview_item_query_use, viewGroup, false);
            vh.tva = (TextView) view.findViewById(R.id.tv1_cq_use);
            vh.tvb = (TextView) view.findViewById(R.id.tv2_cq_use);
            vh.tvc = (TextView) view.findViewById(R.id.tv3_cq_use);
            vh.tvd = (TextView) view.findViewById(R.id.tv4_cq_use);
            vh.iv= (ImageView) view.findViewById(R.id.iv_cq_use);
            view.setTag(vh);
            AutoUtils.autoSize(view);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        setUI(vh, i);
        return view;
    }

    private void setUI(ViewHolder vh, int position) {
        final BuildMeterUseData.RESPONSEXMLBean bean = infoList.get(position);
        vh.tva.setText(bean.getDataItemValueTime());
        vh.tvd.setVisibility(View.VISIBLE);
        vh.tvb.setText(bean.getBuildingName());
        String unit = "m³";
        if (meterType.equals("电")) {
            unit = "kwh";
        }
        final String totalactiveTotal = bean.getTotalactiveTotal();
        if (!totalactiveTotal.equals("")) {
            vh.tvd.setText(totalactiveTotal + unit);//TODO 2016/12/2/11:00 线路名称跟结算量对换位置了
        } else {
            vh.tvd.setText(totalactiveTotal);
        }

        final String cTotalactiveTotalMoney = bean.getCTotalactiveTotalMoney();
         //Log.i("LHT","position "+position+"  money "+cTotalactiveTotalMoney);
        if (!cTotalactiveTotalMoney.equals("")) {
            vh.tvc.setText(cTotalactiveTotalMoney + "元");
            if(showMore){
                vh.iv.setVisibility(View.VISIBLE);
                vh.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.click(bean);
                }
            });
            }
        }else{
            vh.tvc.setText(cTotalactiveTotalMoney);
            vh.iv.setVisibility(View.GONE);
        }
       /* if (showMore){
            Log.i("LHT","showMore "+position);
            Drawable drawable = MyApplication.getContext().getResources().getDrawable(R.drawable.icon_more);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            vh.tvc.setCompoundDrawables(null, null, drawable, null);
            vh.tvc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.click(bean);
                }
            });
        }*/

    }

    private class ViewHolder {
        private TextView tva;
        private TextView tvb;
        private TextView tvc;
        private TextView tvd;
        private ImageView iv;
    }

}
