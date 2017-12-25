package com.northmeter.prepaymentmanage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.ui.CollectEquipmentActivity;
import com.northmeter.prepaymentmanage.util.LoggerUtil;
import com.zhy.autolayout.utils.AutoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zz
 * @time 2016/11/04 14:56
 * @des 采集设备recycerview的adapter
 */
public class CollectEquipmentRVAdapter extends RecyclerView.Adapter<CollectEquipmentRVAdapter.CollectEquipmentViewHolder> {
    private final CollectEquipmentActivity mActivity;
    private final String[] equipments;


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CollectEquipmentRVAdapter(CollectEquipmentActivity collectEquipmentActivity, String[] equipments) {
        mActivity = collectEquipmentActivity;
        this.equipments = equipments;
    }

    @Override
    public CollectEquipmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_collect_equipment, parent, false);
        return new CollectEquipmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CollectEquipmentViewHolder holder, int position) {
        holder.mTvName.setText(equipments[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return equipments.length;
    }

    public class CollectEquipmentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_equipment_lines_name)
        TextView mTvName;

        public CollectEquipmentViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
