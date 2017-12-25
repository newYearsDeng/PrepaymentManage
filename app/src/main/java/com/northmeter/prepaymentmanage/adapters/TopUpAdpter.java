package com.northmeter.prepaymentmanage.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.model.TopUp;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by lht on 2016/11/8.
 */
public class TopUpAdpter extends BaseAdapter {
    private List<TopUp.RESPONSEXMLBean> data;

    public TopUpAdpter(List<TopUp.RESPONSEXMLBean> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh = null;
        if (view == null) {
            vh = new VH();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item_topup, viewGroup, false);
            vh.tv_fs = (TextView) view.findViewById(R.id.tv_fs_lv_item_tu);
            vh.tv_lx = (TextView) view.findViewById(R.id.tv_lx_lv_item_tu);
            vh.tv_je = (TextView) view.findViewById(R.id.tv_je_lv_item_tu);
            vh.tv_time = (TextView) view.findViewById(R.id.tv_time_lv_item_up);
            view.setTag(vh);
            AutoUtils.autoSize(view);
        } else {
            vh = (VH) view.getTag();
        }
        setUI(vh, i);
        return view;
    }

    private void setUI(VH vh, int i) {
        TopUp.RESPONSEXMLBean bean = data.get(i);
        vh.tv_fs.setText(bean.getOptionName());
        String chargeRecordName = bean.getChargeRecordName();
        if (!chargeRecordName.equals("充值")) {
            vh.tv_lx.setTextColor(Color.RED);
        }

        vh.tv_lx.setText(chargeRecordName);
        String chargeAmount = bean.getChargeAmount();
        chargeAmount = chargeAmount.substring(0, chargeAmount.length() - 2);
        vh.tv_je.setText(chargeAmount);
        vh.tv_time.setText(bean.getChargeTime());
    }

    class VH {
        private TextView tv_fs;
        private TextView tv_lx;
        private TextView tv_je;
        private TextView tv_time;
    }
}
