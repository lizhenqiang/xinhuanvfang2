package com.bigdata.xinhuanufang.mine;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.LuckyGameAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.bean.MineChouJiangGame;
import com.bigdata.xinhuanufang.custom.PopWindow;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author asus
 *         我的抽奖游戏Activity
 */
public class LuckyGameActivity extends BaseActivity implements OnClickListener {
    private ListView luckyGameLV;
    private LuckyGameAdapter luckyGameAdapter;
    private String[] num = {"1", "2", "3"};
    private String status = "1";
    private String page = "1";
    private List<MineChouJiangGame> datalist;
    private Handler  handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    datalist= (List<MineChouJiangGame>) msg.obj;
                    //设置数据
                    setAdapterData(datalist);
                    break;
            }
        }
    };
    private TextView data_refresh_time;

    private void setAdapterData(List<MineChouJiangGame> datalist) {
        luckyGameAdapter = new LuckyGameAdapter(LuckyGameActivity.this, datalist);
        luckyGameLV.setAdapter(luckyGameAdapter);
    }

    @Override
    public int getView() {
        return R.layout.activity_luckygame;
    }

    @Override
    public void initView() {
        super.initView();
        super.setTitle("我的抽奖游戏");
        super.setTextRight("筛选");
        setBack();
        super.tv_itt_save.setOnClickListener(this);
        //隐藏筛选功能,目前不支持该功能
        tv_itt_save.setVisibility(View.GONE);
        data_refresh_time = (TextView) findViewById(R.id.data_refresh_time);
        luckyGameLV = (ListView) findViewById(R.id.lv_alg_records);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate=new Date(System.currentTimeMillis());//获取当前时间
        String str= formatter.format(curDate);
        data_refresh_time.setText(str);
        datalist=new ArrayList<>();
        //获取网络数据
        getNetWorkData();
    }

    private void getNetWorkData() {
        //http://115.28.69.240/boxing/app/game_list.php?user_id=1&status=1&page=1
        x.http().get(new RequestParams(Config.ip + Config.app + "/game_list.php?user_id=" + Config.userID + "&status=" + status + "&page=" + page), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json=new JSONObject(s);
                    JSONArray list=json.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject js=list.getJSONObject(i);
                        String game_id=js.getString("game_id");
                        String game_gloves=js.getString("game_gloves");
                        String game_date5=js.getString("game_date5");
                        datalist.add(new MineChouJiangGame(game_id,game_gloves,game_date5));
                    }
                    Message msg=Message.obtain();
                    msg.what=100;
                    msg.obj=datalist;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_itt_save:
                //筛选
                PopWindow popWindow = new PopWindow(this);
                popWindow.showPopupWindow(findViewById(R.id.tv_itt_save));

                break;

            default:
                break;
        }

    }
}
