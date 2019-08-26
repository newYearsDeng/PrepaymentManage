package com.northmeter.prepaymentmanage.ui.GateLock.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.ui.GateLock.model.OpenDoor_Model;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dyd on 2017/6/27.
 * 远程开门的门锁列表adapter
 */
public class OpenDoorRVAdapter extends RecyclerView.Adapter<OpenDoorRVAdapter.OpenDoorViewHolder> {

    public interface OnMyClickListener{
        void onItemClick(View view, int position);
    }

    public OnMyClickListener onClickListener;

    public void setOnMyClickListener(OnMyClickListener onClickListener){
        this.onClickListener = onClickListener;
    }


    public List<OpenDoor_Model> models;

    public OpenDoorRVAdapter(List<OpenDoor_Model> models){
        this.models = models;
    }




    @Override
    public OpenDoorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_door, parent, false);
        OpenDoorViewHolder viewHolder = new OpenDoorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OpenDoorViewHolder holder, final int position) {
        holder.tvOpenDoorName.setText(models.get(position).getLockName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onItemClick(holder.itemView,holder.getLayoutPosition());
            }
        });

        holder.checkbox_door_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.checkbox_door_lock.isChecked()){
                    System.out.println("开门"+position);
                }else{
                    System.out.println("关门"+position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class OpenDoorViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_open_door_name)
        TextView tvOpenDoorName;

        @BindView(R.id.checkbox_door_lock)
        CheckBox checkbox_door_lock;

        public OpenDoorViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
