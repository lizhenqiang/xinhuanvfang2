package com.bigdata.xinhuanufang.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.TopNewsMoreAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.LoadListViewnews;
import com.bigdata.xinhuanufang.game.bean.TopMore;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 更多赛事新闻
 *
 * @author weiyu$
 */
public class MoreMatch extends BaseActivity implements OnItemClickListener, LoadListViewnews.ILoadListener {
    //	private String[] finghter = { "青格勒1", "青格勒2", "青格勒1", "青格勒2", "青格勒1",
//			"青格勒2" }; // 头条个数
    //http://115.28.69.240/boxing/app/news_list1.php?status=0&page=1
// 记录要加载的第几页数据
    private int count = 1;

    private List<TopMore> finghter = new ArrayList<TopMore>();
    private LoadListViewnews topNewsMoreLV;// 头条资讯listView
    private TopNewsMoreAdapter topNewsMoreAdapter;// 头条资讯Adapter
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    finghter = (List<TopMore>) msg.obj;
                    topNewsMoreAdapter = new TopNewsMoreAdapter(MoreMatch.this,
                            finghter);

                    topNewsMoreLV.setAdapter(topNewsMoreAdapter);
                    break;
                case 120:
                    if (finghter.size()==0) {

                        xuanshou_quanbu_wu.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }

        ;

    };
    private Button xuanshou_quanbu_wu;

    @Override
    public int getView() {
        // TODO Auto-generated method stub
        return R.layout.activity_topnews;
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        setTitle("赛事新闻");
        setGone();
        setBack();
        xuanshou_quanbu_wu = (Button) findViewById(R.id.xuanshou_quanbu_wu);
        xuanshou_quanbu_wu.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        topNewsMoreLV = (LoadListViewnews) findViewById(R.id.lv_more_topnew);
        ////http://115.28.69.240/boxing/app/news_list1.php?status=0&page=1
        topNewsMoreLV.setOnItemClickListener(this);
        topNewsMoreLV.setInterface(this);
        initNewDatas(count);
    }




    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        Bundle bundle = new Bundle();
        bundle.putString("news_id", finghter.get(arg2).getId());
        Intent intent = new Intent(this, Information.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void onLoad() {
        // TODO Auto-generated method stub
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                count++;
                initNewDatas(count);// 得到新数据
                topNewsMoreAdapter.notifyDataSetChanged();// 刷新ListView;
                topNewsMoreLV.loadCompleted();
            }
        }, 2000);
    }

    private void initNewDatas(int count) {
        //http://115.28.69.240/boxing/app/news_list1.php?status=1&page=1
        x.http().get(new RequestParams(Config.ip + Config.app + "/news_list1.php?status=1&page=" + count), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    Message msg= Message.obtain();

                        JSONObject json = new JSONObject(s);
                    String code=json.getString("code");
                    if (code.equals("1")) {
                        JSONArray json1 = json.getJSONArray("list");
                        for (int i = 0; i < json1.length(); i++) {
                            JSONObject js = json1.getJSONObject(i);
                            String id = js.getString("news_id");
                            String news_pic = js.getString("news_pic");
                            String news_title = js.getString("news_title");
                            String news_title2 = js.getString("news_title2");
                            String news_date = js.getString("news_date");
                            TopMore topmore = new TopMore(id, news_pic, news_title, news_title2, news_date);
                            finghter.add(topmore);
                        }
                        msg.obj = finghter;
                        msg.what = 100;
                        handler.sendMessage(msg);
                    }else if (code.equals("0")) {
                        msg.what=120;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
