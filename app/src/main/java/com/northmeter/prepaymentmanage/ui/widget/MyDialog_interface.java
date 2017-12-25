package com.northmeter.prepaymentmanage.ui.widget;

import android.view.View;

/**
 * Created by Administrator on 2016/11/28.
 */
public abstract  class MyDialog_interface {
   public View.OnClickListener no(){
        View.OnClickListener onClickListener=new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onMyno();
           }
       };
       return onClickListener;
   }
    public View.OnClickListener yes(){
        View.OnClickListener onClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyyes();
            }
        };
        return onClickListener;
    }
    protected abstract void onMyno();

    protected abstract void onMyyes();
}
