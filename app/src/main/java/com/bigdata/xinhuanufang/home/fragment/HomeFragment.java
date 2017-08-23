package com.bigdata.xinhuanufang.home.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseFragment;

import com.bigdata.xinhuanufang.bean.List1;
import com.bigdata.xinhuanufang.home.adapter.HomeRecycleAdapter;

import com.bigdata.xinhuanufang.home.bean.BannerBean;
import com.bigdata.xinhuanufang.home.bean.NewsBean;
import com.bigdata.xinhuanufang.home.bean.liveListBean;
import com.bigdata.xinhuanufang.main.GussingActivity;
import com.bigdata.xinhuanufang.main.LuckDraw;
import com.bigdata.xinhuanufang.main.MessageCenterActivty;
import com.bigdata.xinhuanufang.main.SuperDreamActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * Created by lzq on 2017/5/13.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.iv_titlebar_message)
    ImageView iv_Massage;
    @Bind(R.id.rv_home)
    PullToRefreshRecyclerView rvHome;
    @Bind(R.id.tv_home_superdream)
    TextView tvHomeSuperdream;
    @Bind(R.id.tv_home_guess)
    TextView tvHomeGuess;
    @Bind(R.id.tv_home_game)
    TextView tvHomeGame;
    @Bind(R.id.ll_home_tilte)
    LinearLayout llHomeTilte;



    private List<liveListBean.LiveBean> liveBeanList = new ArrayList<>();
    private List<BannerBean.LayerBean> layerBeanList = new ArrayList<>();
    private List<NewsBean.List1Bean> list1BeenList = new ArrayList<>();
    private List<NewsBean.List2Bean> list2BeanList = new ArrayList<>();


    private String liveUrl = Config.ip + Config.app
            + "/live_list.php?user_id=" + Config.userID;
    private HomeRecycleAdapter adapter;
    private String bannerUrl = Config.ip + Config.app + "/layer_list.php?status=0";
    private  String newsUrl =Config.ip+Config.app+"/news_list.php";

    @Override
    public View initView() {


        View view = View.inflate(mContext, R.layout.fragment_home, null);


        ButterKnife.bind(this, view);




        return view;
    }

    public void initData(){




        getBannerDatas();

        initListener();

    }

    private void setAdapter() {

        adapter = new HomeRecycleAdapter(mContext,liveBeanList,layerBeanList,list1BeenList,list2BeanList);

        rvHome.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);


        rvHome.setLayoutManager(manager);
    }

    private void getBannerDatas() {
        OkHttpUtils
                .get()
                .url(bannerUrl)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "联网失败", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String response, int id) {

                        progressBannerDatas(response);


                    }
                });
    }

    private void progressBannerDatas(String response) {

        BannerBean bannerBean =  new Gson().fromJson(response,BannerBean.class);
        layerBeanList = bannerBean.getLayer();
        getLiveDatas();
    }

        private void initListener() {

            //设置是否开启上拉加载
            rvHome.setLoadingMoreEnabled(false);
            //设置是否开启下拉刷新
            rvHome.setPullRefreshEnabled(true);
            //设置是否显示上次刷新的时间
            rvHome.displayLastRefreshTime(true);
            //设置刷新回调
            rvHome.setPullToRefreshListener(new PullToRefreshListener() {
                @Override
                public void onRefresh() {
                    rvHome.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            rvHome.setRefreshComplete();
                            //模拟没有数据的情况

                            getBannerDatas();

                        }
                    }, 3000);
                }

                @Override
                public void onLoadMore() {
                }
            });


        iv_Massage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent messageCenterIntent = new Intent(getActivity(),
                        MessageCenterActivty.class);
                startActivity(messageCenterIntent);

            }
        });
        tvHomeSuperdream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainToSintent = new Intent(getActivity(),
                        SuperDreamActivity.class);
                startActivity(MainToSintent);

            }
        });
        tvHomeGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gussingIntent = new Intent(getActivity(),
                        GussingActivity.class);
                startActivity(gussingIntent);
            }
        });
        tvHomeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //抽奖
                Intent luckdraw = new Intent(getActivity(),
                        LuckDraw.class);
                startActivity(luckdraw);
            }
        });

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rvHome.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        int firstVisibleItemPosition = ((LinearLayoutManager) (rvHome.getLayoutManager())).findFirstVisibleItemPosition();
                        Log.e("TAG", "firstVisibleItemPosition="+firstVisibleItemPosition);
                        if(firstVisibleItemPosition == 1){
                            llHomeTilte.setVisibility(View.GONE);//第一个  吸顶文本的消失隐藏
                        }else{
                            llHomeTilte.setVisibility(View.GONE);
                        }

                    }
                });
            }*/
           rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
               @Override
               public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                   super.onScrollStateChanged(recyclerView, newState);
               }

               @Override
               public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                   super.onScrolled(recyclerView, dx, dy);
               }
           });
        rvHome.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e("STATE", "STATE=="+rvHome.refreshHeader.getRefreshState());

                int firstVisibleItemPosition = ((LinearLayoutManager) (recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
                Log.e("STATE", "firstVisibleItemPosition="+firstVisibleItemPosition);

                    if(firstVisibleItemPosition == 1){
                        llHomeTilte.setVisibility(View.GONE);//第一个  吸顶文本的消失隐藏
                    }else if(firstVisibleItemPosition==0) {
                        llHomeTilte.setVisibility(View.GONE);
                    }else {
                        llHomeTilte.setVisibility(View.VISIBLE);

                    }



                // Get the sticky information from the topmost view of the screen.
               /*View stickyInfoView = recyclerView.findChildViewUnder(
                        tvText.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tvText.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                // Get the sticky view's translationY by the first view below the sticky's height.
                 View transInfoView = recyclerView.findChildViewUnder(
                        tvText.getMeasuredWidth() / 2, tvText.getMeasuredHeight() + 1);*/


            }
        });



    }

    public void getLiveDatas() {


        OkHttpUtils
                .get()
                .url(liveUrl)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "联网失败", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String response, int id) {

                        progressLiveDatas(response);


                    }
                });
    }





    //解析比赛
    private void progressLiveDatas(String response) {



        liveListBean liveListBean =  new Gson().fromJson(response,liveListBean.class);
        liveBeanList = liveListBean.getLive();

        //matchesDataBeans = matchesBean.getData();

        getNewsDatas();




    }

    private void getNewsDatas() {
        OkHttpUtils
                .get()
                .url(newsUrl)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(mContext, "联网失败", Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onResponse(String response, int id) {

                        progressNewsDatas(response);


                    }
                });
    }

    private void progressNewsDatas(String response) {
        NewsBean newsBean =  new Gson().fromJson(response,NewsBean.class);
        list1BeenList = newsBean.getList1();
        list2BeanList = newsBean.getList2();
        setAdapter();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



    }








}

