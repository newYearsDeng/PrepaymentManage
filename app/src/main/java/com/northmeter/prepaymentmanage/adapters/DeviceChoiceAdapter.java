package com.northmeter.prepaymentmanage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.UserMeterBean;
import com.northmeter.prepaymentmanage.ui.DeviceChoiceActivity;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zz
 * @time 2016/11/16 17:04
 * @des 设备选择的adapter
 */
public class DeviceChoiceAdapter extends RecyclerView.Adapter<DeviceChoiceAdapter.DeviceChoiceViewholder> {

    private final List<UserMeterBean.RESPONSEXMLBean> mData;
    private Context mAcivity;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public DeviceChoiceAdapter(Context context, List<UserMeterBean.RESPONSEXMLBean> responsexml) {
        mAcivity = context;
        mData = responsexml;
    }

    @Override
    public DeviceChoiceViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mAcivity).inflate(R.layout.item_device_choice, parent, false);
        return new DeviceChoiceViewholder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceChoiceViewholder holder, int position) {
        String metertype = mData.get(position).getMETERTYPE();
        switch(metertype){
            case "水":
                Picasso.with(MyApplication.getContext()).load(R.drawable.icon_device_choice_water).into(holder.mIvMeterType);
                break;
            case "电":
                Picasso.with(MyApplication.getContext()).load(R.drawable.icon_device_choice_electricity).into(holder.mIvMeterType);
                break;
            default:
                Picasso.with(MyApplication.getContext()).load(R.drawable.icon_device_choice_electricity).into(holder.mIvMeterType);
                break;
        }
//        Picasso.with(MyApplication.getContext())
//                .load("水".equals(metertype) ? R.drawable.icon_device_choice_water : R.drawable.icon_device_choice_electricity)
//                .into(holder.mIvMeterType);

        holder.mTvMeterName.setText("设备" + (position + 1) + "   " + mData.get(position).getCOMADDRESS());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class DeviceChoiceViewholder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_item_device_choice_meter_type)
        ImageView mIvMeterType;
        @BindView(R.id.tv_item_device_choice_meter_name)
        TextView mTvMeterName;

        public DeviceChoiceViewholder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
