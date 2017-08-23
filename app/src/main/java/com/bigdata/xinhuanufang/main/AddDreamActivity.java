package com.bigdata.xinhuanufang.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.SuperViewpagerAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.bean.SuperDreamGridBean;
import com.bigdata.xinhuanufang.game.bean.SuperDreamGridPicBean;
import com.bigdata.xinhuanufang.mine.MySuperDreamActivity;
import com.bigdata.xinhuanufang.store.ConfirmOrderActivity;
import com.bigdata.xinhuanufang.to.SuperDreamRule;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiyu$ on 2017/4/10.
 * 梦想商品详情的activity
 */

public class AddDreamActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager dream_details_viewpager;
    private WebView dream_details_webview;
    private Button main_join_dream;
    private String think_id;
    private TextView super_dream_title;
    private TextView super_dream_price_money;
    private Button super_now_buy;
    private ProgressBar super_dream_main_progress;
    private List<SuperDreamGridBean> gridDataList;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4:
                    gridDataList = (List<SuperDreamGridBean>) msg.obj;
                    setData(gridDataList);
                    break;
                case 0:
                    // 跳转下一页
                    showNextPage();
                    break;
            }
        }
    };
    private List<SuperDreamGridPicBean> pic;
    private TextView is_add_dream;
    private TextView dream_jiexiao_progress;
    private String think_title;
    private String think_price;
    private String think_percent;
    private String think_pic;
    private String is_adds;
    private String think_shopid;
    private String think_attrid;
    private TextView activs_rule;
    private TextView super_dream_qishu;

    /*
     * 显示下一页
	 */
    public void showNextPage() {
        int currentItem = dream_details_viewpager.getCurrentItem();
        dream_details_viewpager.setCurrentItem(currentItem + 1);
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public int getView() {
        return R.layout.dream_details;
    }

    @Override
    public void initView() {
        super.initView();
        super.setTitle("梦想商品详情"); // 设置标题栏文本
        super.setGone(); // 设置标题栏右侧文字不可见
        /*
		 * 对返回按钮添加点击事件
		 */
        super.setBack();
        Intent intent = getIntent();
        think_id = intent.getStringExtra("think_id");
        think_shopid = intent.getStringExtra("think_shopid");
        think_title = intent.getStringExtra("think_title");
        think_price = intent.getStringExtra("think_price");
        think_percent = intent.getStringExtra("think_percent");
        think_pic = intent.getStringExtra("think_pic");
        think_attrid = intent.getStringExtra("think_attrid");
        is_adds = intent.getStringExtra("is_add");
        //再次获取超级梦想的网络数据
        gridDataList = new ArrayList<>();
        super_dream_qishu = (TextView) findViewById(R.id.super_dream_qishu);
        super_dream_qishu.setText("第"+think_id+"期");
        GETsuperDream();
        //商品名称
        super_dream_title = (TextView) findViewById(R.id.super_dream_title);
        super_dream_title.setText(think_title);
        //商品一口价的价格
        super_dream_price_money = (TextView) findViewById(R.id.super_dream_price_money);
        super_dream_price_money.setText("¥" + think_price);
        //直接购买
        super_now_buy = (Button) findViewById(R.id.super_now_buy);
        super_now_buy.setOnClickListener(this);
        //梦想进度
        dream_jiexiao_progress = (TextView) findViewById(R.id.dream_jiexiao_progress);
        //加入梦想的进度
        super_dream_main_progress = (ProgressBar) findViewById(R.id.super_dream_main_progress);
//        super_dream_progress.setProgress(Integer.parseInt(think_percent));
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < think_percent.length(); i++) {
            if (think_percent.charAt(i)=='.') {
                sb.append(think_percent.charAt(i));
                if (think_percent.length()>(i+1)) {
                    sb.append(think_percent.charAt(i+1));

                }
                if (think_percent.length()>(i+2)) {
                    sb.append(think_percent.charAt(i+2));

                }
                break;
            }else{
                sb.append(think_percent.charAt(i));
            }
        }
        double asd=Double.parseDouble(sb.toString());
        super_dream_main_progress.setProgress((int)(Double.parseDouble(sb.toString())));
        NumberFormat nFromat = NumberFormat.getPercentInstance();
        String rates = nFromat.format(Double.parseDouble(sb.toString()));
        dream_jiexiao_progress.setText("揭晓进度:"+asd+" %");
        //加入梦想
        main_join_dream = (Button) findViewById(R.id.main_join_dream);
        //轮播图
        dream_details_viewpager = (ViewPager) findViewById(R.id.dream_details_viewpager);
        // 为ViewPager设置Adapter
        handler.sendEmptyMessageDelayed(0, 3000); // 3秒后显示下一页
        //http://115.28.69.240/boxing/app/shop_content.php?shop_id=1
        dream_details_webview = (WebView) findViewById(R.id.dream_details_webview);
        //这里显示的是假数据
        dream_details_webview.loadUrl(Config.ip + Config.app
                + "/shop_content.php?shop_id=" + think_shopid);
        //http://115.28.69.240/boxing/app/think_add.php?user_id=1&think_id=1&gloves=333&price=333
        //进行加入梦想的网络操作
        main_join_dream.setOnClickListener(this);
        //是否已经添加进梦想
        is_add_dream = (TextView) findViewById(R.id.is_add_dream);
        is_add_dream.setTextColor(Color.RED);
        if (is_adds.equals("0")) {
            is_add_dream.setText("你还没有参与本次超级梦想哦");
        }else if (is_adds.equals("1")) {
            is_add_dream.setText("你已经添加过该梦想");
        }
        //活动规则
        activs_rule = (TextView) findViewById(R.id.activs_rule);
        activs_rule.setOnClickListener(this);
    }

    private void GETsuperDream() {
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/think_list.php?user_id=" + Config.userID),
                new Callback.CommonCallback<String>() {
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
                            JSONArray json1 = json.getJSONArray("think");
                            for (int i = 0; i < json1.length(); i++) {
                                JSONObject json2 = json1.getJSONObject(i);
                                String think_id = json2.getString("think_id");
                                String think_shopid = json2
                                        .getString("think_shopid");
                                String think_attrid = json2
                                        .getString("think_attrid");
                                String think_percent = json2
                                        .getString("think_percent");
                                String think_title = json2
                                        .getString("think_title");
                                String think_pic = json2.getString("think_pic");
                                String think_price = json2
                                        .getString("think_price");
                                JSONArray json3 = json2.getJSONArray("pic");
                                pic = new ArrayList<SuperDreamGridPicBean>();
                                for (int j = 0; j < json3.length(); j++) {
                                    JSONObject json4 = json3.getJSONObject(j);
                                    String shoppic_id = json4
                                            .getString("shoppic_id");
                                    String shoppic_shopid = json4
                                            .getString("shoppic_shopid");
                                    String shoppic_pic = json4
                                            .getString("shoppic_pic");
                                    pic.add(new SuperDreamGridPicBean(
                                            shoppic_id, shoppic_shopid,
                                            shoppic_pic));
                                }
                                String is_add = json2.getString("is_add");

                                gridDataList.add(new SuperDreamGridBean(
                                        think_id, think_shopid, think_attrid,
                                        think_percent, think_title, think_pic,
                                        think_price, pic, is_add));
                            }
                            Message msg = Message.obtain();
                            msg.what = 4;
                            msg.obj = gridDataList;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void setData(List<SuperDreamGridBean> data) {

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getPic().size()==0) {
//                x.image().bind(dream_details_viewpager,Config.ip+data.get(i).getThink_pic());
            }else {
                if (data.get(i).getThink_id().equals(think_id)) {
                    for (int j = 0; j < data.get(i).getPic().size(); j++) {
                        dream_details_viewpager.setAdapter(new SuperViewpagerAdapter(AddDreamActivity.this,data, think_id, i)); //
                    }

                }
            }
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_join_dream:
                addSuperDream();
                break;
            case  R.id.super_now_buy:
                //直接购买,跳转到确认订单
                /**
                 * bundle.putString("shopingID", shopingID);
                 * bundle.putString("shopingPrice", shopingPrice);
                 * bundle.putString("shopingTitle", shopingTitle);
                 * bundle.putString("shopingPic", shopingPic);
                 */
                Intent intent=new Intent(AddDreamActivity.this, ConfirmOrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("biaoji","mengxiang");
                bundle.putString("shopingID",think_shopid);
                bundle.putString("shopingPrice",think_price);
                bundle.putString("shopingTitle",think_title);
                bundle.putString("shopingPic",think_pic);
                bundle.putString("think_attrid",think_attrid);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.activs_rule:
                //查看活动规则
                Intent superdream_rule=new Intent(AddDreamActivity.this, SuperDreamRule.class);
                startActivity(superdream_rule);
                break;
        }
    }

    private void addSuperDream() {
        x.http().get(new RequestParams(Config.ip + Config.app
                        + "/think_add.php?user_id=" + Config.userID
                        + "&think_id="
                        + think_id), new Callback.CommonCallback<String>() {
                    @Override
                    public void onCancelled(
                            CancelledException arg0) {
                    }

                    @Override
                    public void onError(Throwable arg0,
                                        boolean arg1) {
                    }

                    @Override
                    public void onFinished() {
                    }

                    @Override
                    public void onSuccess(String arg0) {
                        try {
                            JSONObject json = new JSONObject(arg0);
                            String code = json.getString("code");
                            if (code.equals("1")) {
                                Toast.makeText(mContext, "加入成功", Toast.LENGTH_SHORT).show();
                                //加入成功后跳转到梦想详情
                                is_add_dream.setText("你已经添加过该梦想");
                                Intent intent=new Intent(AddDreamActivity.this, MySuperDreamActivity.class);
                                startActivity(intent);
                                setResult(20);

                            }else  if (code.equals("0")) {
                                Toast.makeText(mContext, "加入失败", Toast.LENGTH_SHORT).show();
                                //加入成功后跳转到梦想详情

                            }else  if (code.equals("3")) {
                                Toast.makeText(mContext, "已经加入过了", Toast.LENGTH_SHORT).show();
                                //加入成功后跳转到梦想详情
                                Intent intent=new Intent(AddDreamActivity.this, MySuperDreamActivity.class);
                                startActivity(intent);
                                setResult(20);
                            }
                            setResult(20);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });
    }
}
