package com.bigdata.xinhuanufang.mine;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.AttentionGameAdapter;
import com.bigdata.xinhuanufang.game.bean.GameGuanZhu;
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
 * @author asus 我的关注--比赛Fragment
 */
public class MyAttentionGameFragment extends Fragment {
	private String[] num = { "1", "2", "3", "4" }; // 用于控制ListView的数量
	private ListView attentionGameLV; // 比赛ListView
	private List<GameGuanZhu> listData;// 比赛数据
	private int page = 1;// 当前的页数
	private AttentionGameAdapter attentionGameAdapter; // 比赛Adapter

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 100:
					listData=(List<GameGuanZhu>) msg.obj;
					DataAdapter(listData);
					break;

				default:
					break;
			}
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_attentiongame,
				container, false);
		listData = new ArrayList<GameGuanZhu>();
		initView(view);


		return view;
	}

	protected void DataAdapter(final List<GameGuanZhu> listData) {
		// TODO Auto-generated method stub
		attentionGameAdapter = new AttentionGameAdapter(getActivity(), listData);
		attentionGameLV.setAdapter(attentionGameAdapter);
//		attentionGameLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Intent intent=new Intent(getActivity(), ReviewDetailsActivity.class);
//				Bundle bundle=new Bundle();
//				bundle.putInt("tag",3);
//				bundle.putString("video_liveguessid",listData.get(position).getConcern_liveguessid());
//				intent.putExtra("context",bundle);
//				startActivity(intent);
//			}
//		});
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		attentionGameLV = (ListView) view.findViewById(R.id.lv_fragAG_game);
		// 加载网络数据
		getNetWork();
	}

	private void getNetWork() {
		// http://115.28.69.240/boxing/app/my_concern.php?user_id=1&page=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/my_concern.php?user_id=" + Config.userID + "&page="
						+ page), new CommonCallback<String>() {
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
							JSONArray js = json.getJSONArray("concern");
							for (int i = 0; i < js.length(); i++) {
								JSONObject json1 = js.getJSONObject(i);
								String concern_id = json1
										.getString("concern_id");
								String concern_userid = json1
										.getString("concern_userid");
								String concern_liveguessid = json1
										.getString("concern_liveguessid");
								String concern_pic = json1
										.getString("concern_pic");
								String concern_playernamea = json1
										.getString("concern_playernamea");
								String concern_playerheada = json1
										.getString("concern_playerheada");
								String concern_playerglovesa = json1
										.getString("concern_playerglovesa");
								String concern_playernameb = json1
										.getString("concern_playernameb");
								String concern_playerheadb = json1
										.getString("concern_playerheadb");
								String concern_playerglovesb = json1
										.getString("concern_playerglovesb");
								String concern_title = json1
										.getString("concern_title");
								String concern_time = json1
										.getString("concern_time");
								String concern_content = json1
										.getString("concern_content");
								listData.add(new GameGuanZhu(concern_id,
										concern_userid, concern_liveguessid,
										concern_pic, concern_playernamea,
										concern_playerheada,
										concern_playerglovesa,
										concern_playernameb,
										concern_playerheadb,
										concern_playerglovesb, concern_title,
										concern_time, concern_content));
								Message msg=Message.obtain();
								msg.what=100;
								msg.obj=listData;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}
