package com.bigdata.xinhuanufang.mine;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MySuperDreamFragmentDaishixianAdapter;
import com.bigdata.xinhuanufang.adapter.MySuperDreamFragmentDaishixianAdapter.onCheckBoxChangeListener;
import com.bigdata.xinhuanufang.bean.mysuperdreamdaishixian;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的超级梦想待实现
 *
 * @author weiyu$
 */
public class MySuperDreamFragmentDaishixian extends Fragment implements OnClickListener {
    private ListView my_super_dream_daishixian_listview;
    private List<mysuperdreamdaishixian> dataList;
    private MySuperDreamFragmentDaishixianAdapter mysuperdreamfragmentdaishixianadapter;
    private TextView mine_superdream_daishixian_delete;
    private TextView mine_superdream_daishixian_commit;
    private TextView mine_superdream_daishixian_count;
    private String think_id;
    //全选
    private CheckBox mine_superdream_daishixian_quanxuan;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    dataList = (List<mysuperdreamdaishixian>) msg.obj;
                    DataShowAdapter(dataList);
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_super_dream,
                container, false);
        initView(view);
        return view;
    }

    protected void DataShowAdapter(
            final List<mysuperdreamdaishixian> dataList) {
        mysuperdreamfragmentdaishixianadapter = new MySuperDreamFragmentDaishixianAdapter(getActivity(), dataList, "");
        my_super_dream_daishixian_listview.setAdapter(mysuperdreamfragmentdaishixianadapter);
        //监听选中了那些条目
        mysuperdreamfragmentdaishixianadapter.setOnBoxChangeListener(new onCheckBoxChangeListener() {

            @Override
            public void onChanged(int position, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    think_id = dataList.get(position).getThink_id();
                    // 如果是选中的就发起删除收藏的请求
                    // http://115.28.69.240/boxing/app/my_collcannel.php?user_id=2&video_id=10

                }
            }
        });
        mysuperdreamfragmentdaishixianadapter.setrefreshdata(new MySuperDreamFragmentDaishixianAdapter.refreshdata() {
            @Override
            public void refreshing() {
                // 获取网络数据
                getNetWorkData();
            }
        });
    }


    private void initView(View view) {
        my_super_dream_daishixian_listview = (ListView) view
                .findViewById(R.id.my_super_dream_daishixian_listview);
        //全选
        mine_superdream_daishixian_quanxuan = (CheckBox) view.findViewById(R.id.mine_superdream_daishixian_quanxuan);
        ////删除订单
        mine_superdream_daishixian_delete = (TextView) view.findViewById(R.id.mine_superdream_daishixian_delete);
        //提交
        mine_superdream_daishixian_commit = (TextView) view.findViewById(R.id.mine_superdream_daishixian_commit);
        //通过checkbox选中数据变化的控件
        mine_superdream_daishixian_count = (TextView) view.findViewById(R.id.mine_superdream_daishixian_count);

        mine_superdream_daishixian_quanxuan.setOnClickListener(this);


        dataList = new ArrayList<mysuperdreamdaishixian>();
        // 获取网络数据
        getNetWorkData();
    }

    private void getNetWorkData() {
        // http://115.28.69.240/boxing/app/think_list.php?user_id=1
        //http://115.28.69.240/boxing/app/think_mylist.php?user_id=1
        dataList.clear();
        String asd=Config.ip + Config.app
                + "/think_mylist.php?user_id=" + Config.userID;
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/think_mylist.php?user_id=" + Config.userID),
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
                            JSONArray js=json.getJSONArray("think");
                            for (int i = 0; i < js.length(); i++) {
                                JSONObject json1=js.getJSONObject(i);
                                String think_id = json1.getString("think_id");
                                String think_percent = json1.getString("think_percent");
                                String think_title = json1.getString("think_title");
                                String think_pic = json1.getString("think_pic");
                                String think_price = json1.getString("think_price");
                                String thinkjoin_gloves = json1.getString("thinkjoin_gloves");
                                String thinkjoin_bili = json1.getString("thinkjoin_bili");
                                String thinkjoin_id = json1.getString("thinkjoin_id");
                                dataList.add(new mysuperdreamdaishixian(think_id, thinkjoin_id, think_percent, think_title, think_pic, think_price, thinkjoin_gloves, thinkjoin_bili));
                            }


                            Message msg = Message.obtain();
                            msg.what = 100;
                            msg.obj = dataList;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_superdream_daishixian_quanxuan:
                if (mine_superdream_daishixian_quanxuan.isChecked()) {
                    //全选的话,就拿到数据集合中所有的商品信息,进行拼接
                    mysuperdreamfragmentdaishixianadapter = new MySuperDreamFragmentDaishixianAdapter(getActivity(), dataList, "全选");
                    my_super_dream_daishixian_listview.setAdapter(mysuperdreamfragmentdaishixianadapter);
                } else if (!mine_superdream_daishixian_quanxuan.isChecked()) {
                    mysuperdreamfragmentdaishixianadapter = new MySuperDreamFragmentDaishixianAdapter(getActivity(), dataList, "不全选");
                    my_super_dream_daishixian_listview.setAdapter(mysuperdreamfragmentdaishixianadapter);
                }
                break;

            default:
                break;
        }
    }
}
