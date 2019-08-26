package com.northmeter.prepaymentmanage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.LookChumBean;
import com.northmeter.prepaymentmanage.ui.DeviceChoiceActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author zz
 * @time 2016/12/02 8:44
 * @des ${TODO}
 */
public class LookChumAdater extends RecyclerView.Adapter<LookChumAdater.LookChumViewholder> {

    private Context mActivity;
    private final List<LookChumBean.RESPONSEXMLBean> mData;

    public LookChumAdater(Context context, List<LookChumBean.RESPONSEXMLBean> responsexmlBeen) {
        this.mActivity = context;
        this.mData = responsexmlBeen;
    }

    @Override
    public LookChumViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.item_look_chum, parent, false);
        return new LookChumViewholder(view);
    }

    @Override
    public void onBindViewHolder(LookChumViewholder holder, int position) {
        holder.mTvLookChum.setText(mData.get(position).getRoommateName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class LookChumViewholder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_look_chum)
        TextView mTvLookChum;

        public LookChumViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            AutoUtils.autoSize(itemView);
        }
    }
}
