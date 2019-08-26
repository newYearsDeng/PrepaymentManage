package com.northmeter.prepaymentmanage.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.northmeter.prepaymentmanage.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Lht
 * @time 2017/1/3 16:29
 * @des:
 */
public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {


    private final List<String> mData;

    public DateAdapter(List<String> dates) {
        this.mData = dates;
    }

    @Override
    public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_pop, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DateViewHolder holder, int position) {
        holder.mTvPop.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_pop_date)
        TextView mTvPop;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            AutoUtils.autoSize(itemView);
        }
    }
}
