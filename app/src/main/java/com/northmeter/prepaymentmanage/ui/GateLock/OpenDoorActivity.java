package com.northmeter.prepaymentmanage.ui.GateLock;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.northmeter.prepaymentmanage.R;
import com.northmeter.prepaymentmanage.base.BaseActivity;
import com.northmeter.prepaymentmanage.ui.GateLock.adapter.OpenDoorRVAdapter;
import com.northmeter.prepaymentmanage.ui.GateLock.model.OpenDoor_Model;
import com.northmeter.prepaymentmanage.util.Contants;
import com.northmeter.prepaymentmanage.util.MyApplication;
import com.northmeter.prepaymentmanage.util.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dyd on 2017/6/27.
 */
public class OpenDoorActivity extends BaseActivity {
    @BindView(R.id.tv_title_titlebar)
    TextView tvTitleTitlebar;
    @BindView(R.id.ll_back_titlebar)
    LinearLayout llBackTitlebar;
    @BindView(R.id.recycler_opendoor)
    RecyclerView recyclerOpendoor;
    private List<OpenDoor_Model> models;
    private String type;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_door;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        type = getIntent().getStringExtra(Contants.METERTYPE);
        tvTitleTitlebar.setText("远程开锁");

        //设置recyclerview管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyApplication.getContext());
        recyclerOpendoor.setLayoutManager(linearLayoutManager);
        recyclerOpendoor.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), linearLayoutManager.getOrientation()));


        models = new ArrayList<OpenDoor_Model>();
        String [] name = new String[]{"大门","客厅","卧室一","卧室二","卧室三"};
        for(int i = 0 ;i<5;i++){
            OpenDoor_Model model = new OpenDoor_Model();
            model.setLockName(name[i]);
            models.add(model);
        }

        OpenDoorRVAdapter adapter = new OpenDoorRVAdapter(models);
        recyclerOpendoor.setAdapter(adapter);
        adapter.setOnMyClickListener(new OpenDoorRVAdapter.OnMyClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                System.out.println(models.get(position).getLockName());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ll_back_titlebar)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back_titlebar:
                finish();
                break;
        }
    }
}
