package com.bigdata.xinhuanufang.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseViewHolder;

import com.bigdata.xinhuanufang.game.Information;
import com.bigdata.xinhuanufang.game.MoreMatch;
import com.bigdata.xinhuanufang.game.TopNewsActivity;
import com.bigdata.xinhuanufang.home.bean.BannerBean;
import com.bigdata.xinhuanufang.home.bean.NewsBean;
import com.bigdata.xinhuanufang.home.bean.liveListBean;

import com.bigdata.xinhuanufang.main.GameGussingDetailActivity;
import com.bigdata.xinhuanufang.main.GussingActivity;
import com.bigdata.xinhuanufang.main.LuckDraw;
import com.bigdata.xinhuanufang.main.SuperDreamActivity;
import com.bigdata.xinhuanufang.main.videoplayer.LivePlayerActivity;
import com.bigdata.xinhuanufang.main.videoplayer.VideoConfig;
import com.bigdata.xinhuanufang.utils.Config;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzq on 2017/5/15.
 */
public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    public static final  int VIEWPAGER = 0;
    public static final  int ACTIVITY = 1;
    public static final int LIVE = 2;
    public  static final  int NEWS =3;


    private HomeGuessRecyclerViewAdapter liveAdapter;

    private final Context mContext;
    public int currentType = VIEWPAGER;

    //private List<GuessBean.DataBean.ComingBean> guessDataBeans;
   // private List<MatchesBean.DataBean> matchesDataBeans;
    private List<liveListBean.LiveBean> liveBeanList = new ArrayList<>();
    private List<BannerBean.LayerBean> layerBeanList = new ArrayList<>();
    private List<String> bannerUrlList = new ArrayList<>();


    private List<NewsBean.List1Bean> list1BeenList = new ArrayList<>();
    private List<NewsBean.List2Bean> list2BeanList = new ArrayList<>();
    //List<ViewPagerBean.DataBean> dataBeens;
    List<Integer> dataBeens = new ArrayList<>();
    private   GridLayoutManager manager;
    private List<Integer> guessDataBeans = new ArrayList<>();
    public HomeRecycleAdapter(Context mContext, List<liveListBean.LiveBean> liveBeanList, List<BannerBean.LayerBean> layerBeanList, List<NewsBean.List1Bean> list1BeanList,List<NewsBean.List2Bean> list2BeanList) {
        this.mContext=mContext;

        this.liveBeanList=liveBeanList;
        this.layerBeanList=layerBeanList;
        this.list1BeenList = list1BeanList;
        this.list2BeanList = list2BeanList;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==VIEWPAGER) {
            return  new ViewpagerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_viewpager,parent,false),mContext);
        } else if (viewType == ACTIVITY) {
            Log.e("TAG", "TYPE==ACTIVITY");
            return new ActivityViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_ll_activity,parent,false), mContext);
        }  else if (viewType == LIVE) {
            Log.e("TAG", "TYPE==LIVE");
            return new LiveViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_rv_live,parent,false), mContext);
        }
        else if (viewType == NEWS) {
            Log.e("TAG", "TYPE==NEWS");
            return new NewsHolder(LayoutInflater.from(mContext).inflate(R.layout.home_news,parent,false), mContext);
        }

        return null;
        
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEWPAGER) {
            ViewpagerViewHolder viewpagerViewHolder = (ViewpagerViewHolder)holder;
            viewpagerViewHolder.setData();

        }else if(getItemViewType(position)==ACTIVITY) {
            ActivityViewHolder viewHolder = (ActivityViewHolder)holder;
            viewHolder.setData();
        }else if(getItemViewType(position)==LIVE) {
            LiveViewHolder viewHolder = (LiveViewHolder)holder;
            viewHolder.setData();

        }else if(getItemViewType(position)==NEWS) {
            NewsHolder viewHolder = (NewsHolder) holder;
            viewHolder.setData();
        }


    }

    public int getItemViewType(int position) {

        if(position==0) {
            currentType = VIEWPAGER;
        }else if(position==1) {
            currentType = ACTIVITY;
        }else if(position==2) {
            currentType = LIVE;
        }else if(position==3) {
            currentType = NEWS;
        }
        return currentType;
    }
    @Override
    public int getItemCount() {

        return liveBeanList==null?4:liveBeanList.size()+2;
    }



    private class ViewpagerViewHolder extends BaseViewHolder {


        private Banner banner;


        @Override
        public void initView() {
            banner = (Banner) itemView.findViewById(R.id.home_banner);
        }


        public ViewpagerViewHolder(View itemView, Context mContext) {
            super(itemView,mContext);
        }



        public void setData() {



          bindBannerData();
        }

//加载数据
        private void bindBannerData() {
            bannerUrlList.clear();
            for (int i=0;i<layerBeanList.size();i++){
                String url = Config.ip+layerBeanList.get(i).getLayer_pic();
                Log.e("TAG", "url"+url);
                bannerUrlList.add(url);
            }

            if(bannerUrlList.size()>0) {

                //2.设置Banner显示图片
                //设置banner样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                //设置图片加载器
                banner.setImageLoader(new GlideImageLoader());
                //设置图片url集合:imageUrl
                banner.setImages(bannerUrlList);
                //设homeBanner置banner动画效果
                banner.setBannerAnimation(Transformer.Accordion);
                //设置自动轮播，默认为true
                banner.isAutoPlay(true);
                //设置轮播时间
                banner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                banner.setIndicatorGravity(BannerConfig.RIGHT);
                //banner设置方法全部调用完毕时最后调用、
                banner.setOnBannerClickListener(new OnBannerClickListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        Toast.makeText(mContext, "position = "+(position-1), Toast.LENGTH_SHORT).show();
                    }
                });
                banner.start();
            }else {
                Log.e("TAG", "集合为空");
            }
        }


        public class GlideImageLoader extends ImageLoader {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {

                Log.e("TAG", "path"+path);
                //Picasso 加载图片简单用法
                Glide.with(context).load((String) path).into(imageView);




            }
        }
    }




    private class NewsHolder extends BaseViewHolder {

        private boolean TAG;
        private TextView toutiao;
        private TextView saishi;
        private TextView gengduo1;
        private TextView gengduo2;
        private ListView listView;
        @Override
        public void initView() {
            toutiao = (TextView) itemView.findViewById(R.id.tv_toutiao);
            saishi = (TextView) itemView.findViewById(R.id.tv_saishi);
            gengduo1 = (TextView) itemView.findViewById(R.id.tv_gengduo1);
            gengduo2 = (TextView)itemView.findViewById(R.id.tv_gengduo2);
            listView = (ListView) itemView.findViewById(R.id.lv_xinwen);
        }

        public NewsHolder(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        public void setData() {


            TAG=true;
            toutiao.setTextColor(Color.RED);
            listView.setAdapter(new NewsListViewAdapter(mContext,list1BeenList));
            toutiao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listView.setAdapter(new NewsListViewAdapter(mContext,list1BeenList));
                    toutiao.setTextColor(Color.RED);
                    saishi.setTextColor(Color.BLACK);
                    TAG=true;
                }
            });


            saishi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listView.setAdapter(new NewsListViewAdapter2(mContext,list2BeanList));
                    toutiao.setTextColor(Color.BLACK);
                    saishi.setTextColor(Color.RED);
                    TAG=false;
                }
            });
            gengduo1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent TopNewsintent = new Intent(mContext,
                            TopNewsActivity.class);
                    ((Activity)mContext).startActivity(TopNewsintent);
                }
            });
            gengduo2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent matchmore = new Intent(mContext, MoreMatch.class);
                    ((Activity)mContext).startActivity(matchmore);
                }
            });




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                   if((TAG)) {
                       Bundle bundle=new Bundle();
                       bundle.putString("news_id", list1BeenList.get(i).getNews_id());
                       Intent intent=new Intent(mContext,Information.class);
                       intent.putExtras(bundle);
                       ((Activity)mContext).startActivity(intent);
                   }else{
                       Bundle bundle=new Bundle();
                       bundle.putString("news_id", list2BeanList.get(i).getNews_id());
                       Intent intent=new Intent(mContext,Information.class);
                       intent.putExtras(bundle);
                       ((Activity)mContext).startActivity(intent);
                   }


                }
            });



        }





    }

    private class ActivityViewHolder extends BaseViewHolder {
        private TextView dream;
        private TextView guess;
        private TextView game;
        public TextView text;
        @Override
        public void initView() {
            dream = (TextView) itemView.findViewById(R.id.tv_home_superdream);
            guess = (TextView) itemView.findViewById(R.id.tv_home_guess);
            game = (TextView) itemView.findViewById(R.id.tv_home_game);
            text = (TextView) itemView.findViewById(R.id.tv_text);
        }

        public ActivityViewHolder(View itemView, Context mContext) {
            super(itemView,mContext);
        }

        @Override
        public void setData() {
            dream.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,
                            SuperDreamActivity.class);
                    ((Activity)mContext).startActivity(intent);
                }
            });
            guess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,
                            GussingActivity.class);
                    ((Activity)mContext).startActivity(intent);
                }
            });
            game.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext,
                            LuckDraw.class);
                    ((Activity)mContext).startActivity(intent);
                }
            });


        }
    }


    private class LiveViewHolder extends BaseViewHolder {

        private RecyclerView rv_home_live;

        public LiveViewHolder(View itemView, Context context) {
            super(itemView, context);
        }

        @Override
        public void initView() {

            rv_home_live = (RecyclerView) itemView.findViewById(R.id.rv_home_live);


        }



        @Override
        public void setData() {
            liveAdapter = new HomeGuessRecyclerViewAdapter(liveBeanList,mContext);
            rv_home_live.setAdapter(liveAdapter);

            if(liveBeanList==null) {
                rv_home_live.setVisibility(View.GONE);
            }

            else {
                rv_home_live.setVisibility(View.VISIBLE);
                 if(liveBeanList.get(0).getLive_status().equals("1")) {
                    manager = new GridLayoutManager(mContext,2);
                    manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return position<1?2:1;
                        }
                    });
                }else {
                    manager = new GridLayoutManager(mContext,2);

                }
            }

            rv_home_live.setLayoutManager(manager);

        }

        public  void bindData(final int position){


           
        }





        }

    }







