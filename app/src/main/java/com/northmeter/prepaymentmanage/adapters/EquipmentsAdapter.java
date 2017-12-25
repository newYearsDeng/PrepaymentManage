package com.northmeter.prepaymentmanage.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.EquipmentBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class EquipmentsAdapter extends BaseAdapter{
    private List<EquipmentBean.RESPONSEXMLBean> equipments;
    private String type;

    public EquipmentsAdapter(List<EquipmentBean.RESPONSEXMLBean> equipments,String type) {
        this.equipments = equipments;
        this.type=type;
    }

    @Override
    public int getCount() {
        return equipments.size();
    }

    @Override
    public Object getItem(int i) {
        return equipments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void update( List<EquipmentBean.RESPONSEXMLBean> newData){
        Log.i("LHT","newData" +newData);
       /* if(newData.size()==0||newData==null){

        }*/
        equipments.clear();
        if(newData!=null){
            equipments.addAll(newData);
        }
        notifyAdapter();
    }

    private  synchronized void  notifyAdapter() {
        this.notifyDataSetChanged();
    }

    public void add(List<EquipmentBean.RESPONSEXMLBean> newData){
        if(!equipments.contains(newData)){
            if(newData!=null){
                equipments.addAll(newData);
            }
        }
        notifyAdapter();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listview_item_equipment,viewGroup,false);
            vh.tv_balance= (TextView) view.findViewById(R.id.tv_balance_lv_item_eq);
            vh.tv_routeName= (TextView) view.findViewById(R.id.tv_routeName_lv_item_eq);
            vh.tv_communicationStatus= (TextView) view.findViewById(R.id.tv_communicationStatus_lv_item_eq);
            vh.tv_electricState= (TextView) view.findViewById(R.id.tv_electricState_lv_item_eq);
            vh.iv= (ImageView) view.findViewById(R.id.iv_head_lv_item_eq);
            view.setTag(vh);

            //对于listView ,注意添加这一行 ，即可在item上使用高度
            AutoUtils.autoSize(view);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        setUI(i,vh);
        return view;
    }

    private void setUI(int i, ViewHolder vh) {

        EquipmentBean.RESPONSEXMLBean equipment = equipments.get(i);
        if(type.equals("水")){
            vh.iv.setImageResource(R.drawable.icon_device_choice_water);
        }else if(type.equals("门锁")){
            vh.iv.setImageResource(R.drawable.icon_device_choice_doorlock);
        }
        if(equipment!=null){
            vh.tv_routeName.setText(equipment.getBuildingName());
            vh.tv_balance.setText(equipment.getZYE());
            String bjzt = equipment.getBJZT();
            vh.tv_electricState.setText(bjzt);
            if(bjzt.equals("阀关")||bjzt.equals("跳闸")){
                vh.tv_electricState.setTextColor(Color.RED);
            }else{
                vh.tv_electricState.setTextColor(Color.parseColor("#3E3A39"));
            }
            String txzt = equipment.getTXZT();
            vh.tv_communicationStatus.setText(txzt);
            if(txzt.equals("通讯异常")){
                vh.tv_communicationStatus.setTextColor(Color.RED);
            }else{
                vh.tv_communicationStatus.setTextColor(Color.parseColor("#3E3A39"));
            }
        }

    }


    private class ViewHolder{
        private TextView tv_routeName;
        private TextView  tv_balance;
        private TextView tv_electricState;
        private TextView  tv_communicationStatus;
        private ImageView iv;
    }
}
