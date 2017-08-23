package com.bigdata.xinhuanufang.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;

public class LoadListViewnews extends ListView implements AbsListView.OnScrollListener {
    private int lastVisibleItem; //最后一个可见项
    private int totalItems; //总的item
    private View footer; //底部View+头部View;
    private boolean isLoading = false;//是否正在加载
    private ILoadListener iListener;//自定义的一个加载接口。暴露给MainActivity让它实现具体加载操作。可以根据需求不同而改写。
    private int page=1;

    public LoadListViewnews(Context context) {
        this(context,null);

    }

    public LoadListViewnews(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.listViewStyle);

    }

    public LoadListViewnews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initViews(context);


    }
    /**
     * 加载布局
     * @param context
     */
    private void initViews(Context context) {
        //获得footer+header布局文件
        LayoutInflater inflater =LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.footer,null);

        footer.findViewById(R.id.ll_footer).setVisibility(GONE);//初始化时设置footer不可见
        this.addFooterView(footer);
        this.setOnScrollListener(this);//设置滚动监听


    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(lastVisibleItem==totalItems&&scrollState ==SCROLL_STATE_IDLE){
            //如果不是在加载
            if(!isLoading){
                page+=1;
                //iListener.setPage(page);
                footer.findViewById(R.id.ll_footer).setVisibility(View.VISIBLE);
                iListener.onLoad();
                isLoading =true;

            }
        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.lastVisibleItem=firstVisibleItem+visibleItemCount;
        this.totalItems = totalItemCount;

    }


    /**
     * 加载更多数据的回调接口
     */
    public interface ILoadListener {
        public void onLoad();
        // public void setPage();
    }
    //上拉加载完毕
    public void loadCompleted(){
        isLoading =false;
        footer.findViewById(R.id.ll_footer).setVisibility(GONE);
    }


    public void setInterface(ILoadListener iListener){

        this.iListener=iListener;
    }

//    @Override
//    public int setpage() {
//        // TODO Auto-generated method stub
//        return page;
//    }
}
