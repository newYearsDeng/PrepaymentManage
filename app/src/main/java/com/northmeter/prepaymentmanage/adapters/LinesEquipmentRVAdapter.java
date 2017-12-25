package com.northmeter.prepaymentmanage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.LinesDevicesBean;
import com.northmeter.prepaymentmanage.ui.CollectEquipmentActivity;
import com.northmeter.prepaymentmanage.ui.LinesEquipmentActivity;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zz
 * @time 2016/11/04 14:56
 * @des 在/离线设备recycerview的adapter
 */
public class LinesEquipmentRVAdapter extends RecyclerView.Adapter<LinesEquipmentRVAdapter.LinesEquipmentViewHolder> {
    private final LinesEquipmentActivity mActivity;
    private final List<LinesDevicesBean.RESPONSEXMLBean> mData;
    private final boolean isOnLines;


    public LinesEquipmentRVAdapter(LinesEquipmentActivity linesEquipmentActivity, List<LinesDevicesBean.RESPONSEXMLBean> data, boolean isOnLines) {
        mActivity = linesEquipmentActivity;
        mData = data;
        this.isOnLines = isOnLines;
    }

    @Override
    public LinesEquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_lines_equipment, parent, false);
        return new LinesEquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LinesEquipmentViewHolder holder, int position) {
        //设置数据
        holder.mTvFloorName.setText(mData.get(position).getConcentratorName());
        holder.mTvNumber.setText("设备编号："+mData.get(position).getConcentratorCode());
        holder.mTvIp.setText("IP："+mData.get(position).getIP());

        int iconIntId = isOnLines ? R.drawable.icon_on_line : R.drawable.icon_off_line;
        Picasso.with(MyApplication.getContext())
                .load(iconIntId)
                .into(holder.mIvLinesIcon);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
//            }
//        });
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    private OnItemClickListener mOnItemClickListener;
//
//    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
//        this.mOnItemClickListener = mOnItemClickListener;
//    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class LinesEquipmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_equipment_floor_name)
        TextView mTvFloorName;
        @BindView(R.id.iv_lines_icon)
        ImageView mIvLinesIcon;
        @BindView(R.id.tv_equipment_number)
        TextView mTvNumber;
        @BindView(R.id.tv_equipment_ip)
        TextView mTvIp;

        public LinesEquipmentViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
