package com.bigdata.xinhuanufang.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lzq on 2016/11/23.
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    public Context mContext ;
    public abstract void initView();




    public BaseViewHolder(View itemView , Context context) {
        super(itemView);
        this.mContext = context;
        initView();

    }
    public abstract   void setData();


}
