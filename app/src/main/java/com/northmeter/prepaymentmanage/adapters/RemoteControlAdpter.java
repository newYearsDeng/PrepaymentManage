package com.northmeter.prepaymentmanage.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public class RemoteControlAdpter extends BaseAdapter {

    private List<EquipmentBean.RESPONSEXMLBean> datas;
    private String type;

    public RemoteControlAdpter(String type,List<EquipmentBean.RESPONSEXMLBean> datas) {
        this.type=type;
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

    public void update(List<EquipmentBean.RESPONSEXMLBean> newData){
        datas.clear();
        datas.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(vh==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_gridview_item_remotecontrol,viewGroup,false);
            vh.iv_choose= (ImageView) view.findViewById(R.id.iv_checked_gv_item);
            vh.iv= (ImageView) view.findViewById(R.id.iv_icon_gv_item);
            vh.tv1= (TextView) view.findViewById(R.id.tv_room_gv_item);
            vh.tv2= (TextView) view.findViewById(R.id.tv_zt_gv_item);
            AutoUtils.autoSize(view);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        setUI(vh,i);

        return view;
    }

    private void setUI(final ViewHolder vh, int i) {
        final EquipmentBean.RESPONSEXMLBean bean = datas.get(i);
        final String zt = bean.getBJZT();
        final String room = bean.getBuildingName();
        vh.tv1.setText(room);
        if(bean.isChecked()){
            vh.iv_choose.setVisibility(View.VISIBLE);
        }else{
            vh.iv_choose.setVisibility(View.GONE);
        }
        if(type.equals("水")){
            if(zt.equals("阀开")){
                vh.iv.setImageResource(R.drawable.icon_watermeter_open);
                vh.tv2.setText("水表状态 ："+zt);
            }else{
                vh.iv.setImageResource(R.drawable.icon_watermeter_close);
                vh.tv2.setText("水表状态 ："+zt);
                vh.tv2.setTextColor(Color.RED);
            }
        }else if(type.equals("电")){
             if(zt.equals("跳闸")){
                 vh.iv.setImageResource(R.drawable.icon_electricmeter_close);
                 vh.tv2.setTextColor(Color.RED);
                 vh.tv2.setText("电表状态 ："+zt);
             }else{
                 vh.iv.setImageResource(R.drawable.icon_electricmeter_open);
                 vh.tv2.setText("电表状态 ："+zt);
             }
        }
    }
//去选全部选中状态
    public void cancleSelected(){
        for (EquipmentBean.RESPONSEXMLBean data : datas) {
             data.setChecked(false);
        }
        notifyDataSetChanged();
    }

    private class ViewHolder{
        private ImageView iv_choose;
        private ImageView iv;
        private TextView tv1;
        private TextView tv2;

    }
}
