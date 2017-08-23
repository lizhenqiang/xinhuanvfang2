package com.bigdata.xinhuanufang.main;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MainFragmentAdapter;
import com.bigdata.xinhuanufang.adapter.ViewpagerAdapter;
import com.bigdata.xinhuanufang.game.bean.MainPrices;
import com.bigdata.xinhuanufang.game.bean.liveListBean;
import com.bigdata.xinhuanufang.main.MainActivity.IOnFocusListener;
import com.bigdata.xinhuanufang.main.MyScrollView.OnScrollListener;
import com.bigdata.xinhuanufang.main.videoplayer.LivePlayerActivity;
import com.bigdata.xinhuanufang.main.videoplayer.VideoConfig;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 首页 Fragement
 *
 * 17/02/28
 * */
public class MainFragment extends Fragment implements OnClickListener,
        OnScrollListener, IOnFocusListener {
    private List<MainPrices> pricesList;

    private int[] imageResIds = {R.drawable.back_1, R.drawable.ic_launcher,
            R.drawable.icon,}; // Viewpager要显示的图片
    private int[] icon = {R.drawable.icon, R.drawable.icon, R.drawable.icon,
            R.drawable.icon, R.drawable.icon, R.drawable.icon,}; // GrideView要显示的图片
    private String[] iconName = {"中意拳王对抗赛84公斤级", "中意拳王对抗赛85公斤级",
            "中意拳王对抗赛86公斤级", "中意拳王对抗赛87公斤级时钟", "中意拳王对抗赛88公斤级", "中意拳王对抗赛89公斤级"};
    private static final int SHOW_NEXT_PAGE = 0; // 显示下一页
    private ViewPager viewPager;
    private MyGridView gview;
    private List<Map<String, Object>> data_list;
    private int searchLayoutTop;
    private int searchLayoutold;
    private LinearLayout superDreamLayout; // 超级梦想布局
    private LinearLayout headerSuperDreamLayout;
    private LinearLayout gussingLayout; // 加油竞猜布局
    private LinearLayout headerGussingLayout;
    private LinearLayout hint_header;
    private ImageView messageCenterIV; // 消息中心ImageView
    private LinearLayout ll_tab;
    private MyScrollView sv_fragM_scrollView; // scrollView
    private List<liveListBean> dataList;
    private MainFragmentAdapter mainfragmentadapter;
    private ProgressBar pb;
    /**
     * 悬浮框的参数
     */
    private static WindowManager.LayoutParams suspendLayoutParams;
    /**
     * 购买布局的高度
     */
    private int buyLayoutHeight;
    /**
     * myScrollView与其父类布局的顶部距离
     */
    private int myScrollViewTop;
    private LinearLayout ll_fm_choujiang;
    /**
     * 购买布局与其父类布局的顶部距离
     */
    private int buyLayoutTop;
    private RelativeLayout main_header;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_NEXT_PAGE:
                    showNextPage();
                    break;
                case 1:
                    dataList = (List<liveListBean>) msg.obj;
                    DataAdapter(dataList);
                    break;
                case 2:
                    pricesList = (List<MainPrices>) msg.obj;
                    PricesDataAdapter(pricesList);
                    break;
                case 4:
                    pb.setVisibility(View.GONE);
                    getNetWorkData();
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private GestureDetector gsDetector;
    private LinearLayout yincang_choujiang;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        initView(v);


        handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000); // 3秒后显示下一页

        data_list = new ArrayList<Map<String, Object>>(); // 新建LIST
        pricesList = new ArrayList<MainPrices>();
        getData(); // 获取数据

        // 新建适配器
        // 配置适配器

        dataList = new ArrayList<liveListBean>();
        // 获取网络数据
        getNetWorkData();
        //获取轮播图资源
        getPrices();

        return v;
    }

    protected void PricesDataAdapter(List<MainPrices> pricesList2) {
        // TODO Auto-generated method stub
        viewPager.setAdapter(new ViewpagerAdapter(pricesList2,getActivity()));
    }

    private void getPrices() {
        //http://115.28.69.240/boxing/app/layer_list.php?status=0
        x.http().get(new RequestParams(Config.ip + Config.app + "/layer_list.php?status=0"), new CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {
                try {
                    JSONObject json = new JSONObject(arg0);
                    JSONArray json1 = json.getJSONArray("layer");
                    for (int i = 0; i < json1.length(); i++) {
                        JSONObject json2 = json1.getJSONObject(i);
                        String layer_id = json2.getString("layer_id");
                        String layer_flag = json2.getString("layer_flag");
                        String layer_pic = json2.getString("layer_pic");
                        String layer_status = json2.getString("layer_status");
                        pricesList.add(new MainPrices(layer_id, layer_flag, layer_pic, layer_status));
                    }
                    Message msg = Message.obtain();
                    msg.what = 2;
                    msg.obj = pricesList;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    protected void DataAdapter(final List<liveListBean> dataList) {
//        for (int i = 0; i < dataList.size(); i++) {
//            if (dataList.get(i).getLive_id().equals("")) {
//                dataList.remove(i);
//            }
//        }
        mainfragmentadapter = new MainFragmentAdapter(getActivity(), dataList);
        gview.setAdapter(mainfragmentadapter);
        gview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //改变播放模式
                VideoConfig.mActivityType = VideoConfig.ACTIVITY_TYPE_LIVE_PLAY;
                if (dataList.get(arg2).getLive_status().equals("")) {
                    //跳转到比赛预告里面activity
                    Intent intent=new Intent(getActivity(), GameGussingDetailActivity.class);
                    intent.putExtra("liveguess_id",dataList.get(arg2).getLiveguess_id());
                    intent.putExtra("liveguess_pic",dataList.get(arg2).getLiveguess_pic());
                    intent.putExtra("liveguess_time",dataList.get(arg2).getLiveguess_time());
                    intent.putExtra("liveguess_title",dataList.get(arg2).getLiveguess_title());
                    intent.putExtra("liveguess_playera",dataList.get(arg2).getLiveguess_playera());
                    intent.putExtra("liveguess_playerb",dataList.get(arg2).getLiveguess_playerb());
                    intent.putExtra("liveguess_content",dataList.get(arg2).getLiveguess_content());
                    intent.putExtra("playera_head",dataList.get(arg2).getPlayera_head());
                    intent.putExtra("playera_name",dataList.get(arg2).getPlayera_name());
                    intent.putExtra("playerb_head",dataList.get(arg2).getPlayerb_head());
                    intent.putExtra("playerb_name",dataList.get(arg2).getPlayerb_name());
                    intent.putExtra("joina",dataList.get(arg2).getJoina());
                    intent.putExtra("joinb",dataList.get(arg2).getJoinb());
                    intent.putExtra("suma",dataList.get(arg2).getSuma());
                    intent.putExtra("sumb",dataList.get(arg2).getSumb());
                    intent.putExtra("concern",dataList.get(arg2).getConcern());
//                    startActivity(intent);
                    startActivityForResult(intent,88);
                }else  if (dataList.get(arg2).getLive_status().equals("1")) {
                    //正在直播
                    Intent intent = new Intent(getActivity(), LivePlayerActivity.class);
                    intent.putExtra("playUrl", dataList.get(arg2).getLive_downstream_address());
                    startActivity(intent);
                }else  if (dataList.get(arg2).getLive_status().equals("2")) {
                    //正在直播
                    Intent intent = new Intent(getActivity(), LivePlayerActivity.class);
                    intent.putExtra("playUrl", dataList.get(arg2).getLive_downstream_address());
                    startActivity(intent);
                }



            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==88) {
            getNetWorkData();
        }

    }

    private void getNetWorkData() {
        dataList.clear();
        // http://115.28.69.240/boxing/app/live_list.php?user_id=1
        System.out.println("直播数据的地址"+Config.ip + Config.app
                + "/live_list.php?user_id=" + Config.userID);
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/live_list.php?user_id=" + Config.userID),
                new CommonCallback<String>() {
                    @Override
                    public void onCancelled(CancelledException arg0) {
                    }

                    @Override
                    public void onError(Throwable arg0, boolean arg1) {
                    }

                    @Override
                    public void onFinished() {
                    }

                    @Override
                    public void onSuccess(String arg0) {
                        try {
                            JSONObject json = new JSONObject(arg0);
                            JSONArray json1 = json.getJSONArray("live");
                            for (int i = 0; i < json1.length(); i++) {
                                JSONObject json2 = json1.getJSONObject(i);
                                String live_id = json2.getString("live_id");
                                String live_playera = json2
                                        .getString("live_playera");
                                String live_playerb = json2
                                        .getString("live_playerb");
                                String live_status = json2
                                        .getString("live_status");
                                String live_pic = json2.getString("live_pic");
                                String live_count = json2
                                        .getString("live_count");
                                String live_title = json2
                                        .getString("live_title");
                                String live_downstream_address = json2
                                        .getString("live_downstream_address");
                                String liveguess_id = json2
                                        .getString("liveguess_id");
                                String liveguess_pic = json2
                                        .getString("liveguess_pic");
                                String liveguess_time = json2
                                        .getString("liveguess_time");
                                String liveguess_title = json2
                                        .getString("liveguess_title");
                                String liveguess_playera = json2
                                        .getString("liveguess_playera");
                                String liveguess_playerb = json2
                                        .getString("liveguess_playerb");
                                String liveguess_content = json2
                                        .getString("liveguess_content");
                                String playera_head = json2
                                        .getString("playera_head");
                                String playera_name = json2
                                        .getString("playera_name");
                                String playerb_head = json2
                                        .getString("playerb_head");
                                String playerb_name = json2
                                        .getString("playerb_name");
                                String joina = json2.getString("joina");
                                String joinb = json2.getString("joinb");
                                String suma = json2.getString("suma");
                                String sumb = json2.getString("sumb");
                                String concern = json2.getString("concern");
                                dataList.add(new liveListBean(live_id,
                                        live_playera, live_playerb,
                                        live_status, live_pic, live_count,
                                        live_title, live_downstream_address,
                                        liveguess_id, liveguess_pic,
                                        liveguess_time, liveguess_title,
                                        liveguess_playera, liveguess_playerb,
                                        liveguess_content, playera_head,
                                        playera_name, playerb_head,
                                        playerb_name, joina, joinb, suma, sumb,
                                        concern));
                            }
                            Message msg = Message.obtain();
                            msg.what = 1;
                            msg.obj = dataList;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
    }

    /*
     * 初始化组件方法
     */
    public void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        gview = (MyGridView) view.findViewById(R.id.gv_frgM_gView);
        superDreamLayout = (LinearLayout) view
                .findViewById(R.id.ll_fm_superDeam);
        headerSuperDreamLayout = (LinearLayout) view
                .findViewById(R.id.ll_fm_header_superDeam);
        gussingLayout = (LinearLayout) view.findViewById(R.id.ll_fm_gussing);
        headerGussingLayout = (LinearLayout) view
                .findViewById(R.id.ll_fm_header_gussing);
        yincang_choujiang = (LinearLayout) view.findViewById(R.id.yincang_choujiang);
        yincang_choujiang.setOnClickListener(this);
        messageCenterIV = (ImageView) view.findViewById(R.id.iv_fragM_msg);
        hint_header = (LinearLayout) view.findViewById(R.id.hint_header);
        // 抽奖
        ll_fm_choujiang = (LinearLayout) view
                .findViewById(R.id.ll_fm_choujiang);
        // 需要悬浮的控件
        ll_tab = (LinearLayout) view.findViewById(R.id.ll_tab);
        main_header = (RelativeLayout) view.findViewById(R.id.main_header);
        messageCenterIV.setOnClickListener(this);
        superDreamLayout.setOnClickListener(this);
        gussingLayout.setOnClickListener(this);
        sv_fragM_scrollView = (MyScrollView) view
                .findViewById(R.id.sv_fragM_scrollView);
        sv_fragM_scrollView.setOnScrollListener(this);
        headerSuperDreamLayout.setOnClickListener(this);
        headerGussingLayout.setOnClickListener(this);
        ll_fm_choujiang.setOnClickListener(this);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        gsDetector = new GestureDetector(getActivity(), new Mlistener());
//        gview.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Log.e("MainActivity", event.getX()+"");
//                return gsDetector.onTouchEvent(event);
//            }
//        });


    }


    private List<Map<String, Object>> getData() {
        // TODO Auto-generated method stub
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }

    /*
     * 显示下一页
     */
    public void showNextPage() {
        int currentItem = viewPager.getCurrentItem();
        viewPager.setCurrentItem(currentItem + 1);
        handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.ll_fm_header_superDeam:
            case R.id.ll_fm_superDeam:
                Intent MainToSintent = new Intent(getActivity(),
                        SuperDreamActivity.class);
                startActivity(MainToSintent);
                break;
            case R.id.ll_fm_header_gussing:
            case R.id.ll_fm_gussing:
                Intent gussingIntent = new Intent(getActivity(),
                        GussingActivity.class);
                startActivity(gussingIntent);
                break;
            case R.id.iv_fragM_msg:
                Intent messageCenterIntent = new Intent(getActivity(),
                        MessageCenterActivty.class);
                startActivity(messageCenterIntent);
                break;
            case R.id.yincang_choujiang:
            case R.id.ll_fm_choujiang:
                //抽奖
                Intent luckdraw = new Intent(getActivity(),
                        LuckDraw.class);
                startActivity(luckdraw);
                break;
            default:
                break;
        }
    }

    @Override
    public void onScroll(int scrollY) {
        // TODO Auto-generated method stub


        //控件显示的动画
        TranslateAnimation mShowAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF
                , -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAnim.setDuration(500);

        //控件隐藏的动画
        TranslateAnimation HiddenAmin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF
                , 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        HiddenAmin.setDuration(500);
        searchLayoutTop = ll_tab.getTop() - 1;
        if (scrollY >= searchLayoutTop) {
            ll_tab.setVisibility(View.GONE);
            hint_header.setVisibility(View.VISIBLE);

        }
        if (scrollY <= searchLayoutTop) {
            hint_header.setVisibility(View.GONE);
            ll_tab.setVisibility(View.VISIBLE);
        }
        if (scrollY ==11111111) {
            if (sv_fragM_scrollView.getScrollY()!= 0) {
                return;
            }
                pb.setVisibility(View.VISIBLE);
//                Animation animation = new ScaleAnimation(1f, 1f, 0, 1f);
//                animation.setDuration(300);
//                pb.startAnimation(animation);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message msg = new Message();
                            msg.what = 4;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();



        }

        // if(scrollY >= searchLayoutTop){
        // if (superDreamLayout.getParent()!=gview) {
        // ll_tab.removeView(superDreamLayout);
        // gview.addView(superDreamLayout);
        // }
        // }else{
        // if (superDreamLayout.getParent()!=ll_tab) {
        // gview.removeView(superDreamLayout);
        // ll_tab.addView(superDreamLayout);
        // }
        // }
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        if (hasFocus) {
            searchLayoutTop = main_header.getBottom();// 获取searchLayout的顶部位置

            searchLayoutold = ll_tab.getBottom();
        }
    }
    class Mlistener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (e2.getY() - e1.getY() > 0 && gview.getFirstVisiblePosition() == 0) {
                pb.setVisibility(View.VISIBLE);
                Animation animation = new ScaleAnimation(1f, 1f, 0, 1f);
                animation.setDuration(300);
                pb.startAnimation(animation);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            Message msg = new Message();
                            msg.what = 4;
                            handler.sendMessage(msg);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
            return false;
        }

    }

}


