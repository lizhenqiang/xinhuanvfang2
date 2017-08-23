package com.bigdata.xinhuanufang.mine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.AttentionFighterAdapter;
import com.bigdata.xinhuanufang.game.FighterDetailsActivity;
import com.bigdata.xinhuanufang.game.bean.GameGuanZhuFighter;
import com.bigdata.xinhuanufang.game.bean.GameGuanZhuFighterHonors;
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
 * @author asus 我的关注--选手Fragment
 */
public class MyAttentionFighterFragment extends Fragment {
	private String[] num = { "1", "2", "3", "4" }; // 用于控制ListView的数量
	private ListView attentionFighterLV; // 选手ListView
	private List<GameGuanZhuFighter> listData;// 数据集合
	private int page = 1;
	private AttentionFighterAdapter attentionFighterAdapter; // 选手Adapter
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 100:
					listData=(List<GameGuanZhuFighter>) msg.obj;
					DataAdapter(listData);
					break;

				default:
					break;
			}
		};
	};
	private String honors_content;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_attentionfighter,
				container, false);

		initView(view);


		return view;
	}

	protected void DataAdapter(final List<GameGuanZhuFighter> listData) {
		attentionFighterAdapter = new AttentionFighterAdapter(getActivity(),
				listData);
		attentionFighterLV.setAdapter(attentionFighterAdapter);
		attentionFighterAdapter.setOnFighterItemClick(new AttentionFighterAdapter.OnFighterMessageClick() {

			@Override
			public void onFighterClick(int pos) {
				Intent intent=new Intent(getActivity(), FighterDetailsActivity.class);
				intent.putExtra("player_id",listData.get(pos).getPlayer_id());
				intent.putExtra("player_head",listData.get(pos).getPlayer_head());
				intent.putExtra("player_sex",listData.get(pos).getPlayer_sex());
				intent.putExtra("player_name",listData.get(pos).getPlayer_name());
				intent.putExtra("player_age",listData.get(pos).getPlayer_age());
				intent.putExtra("player_area",listData.get(pos).getPlayer_area());
				intent.putExtra("player_level",listData.get(pos).getPlayer_level());
				intent.putExtra("drew",listData.get(pos).getDrew());
				intent.putExtra("win",listData.get(pos).getWin());
				intent.putExtra("ko",listData.get(pos).getKo());
				intent.putExtra("player_height",listData.get(pos).getPlayer_height());
				intent.putExtra("player_weight",listData.get(pos).getPlayer_weightid());
				intent.putExtra("player_cm",listData.get(pos).getPlayer_cm());
				intent.putExtra("player_special",listData.get(pos).getPlayer_special());
				intent.putExtra("player_group",listData.get(pos).getPlayer_group());
				StringBuffer sb=new StringBuffer();
				for (int i = 0; i < listData.get(pos).getHonors().size(); i++) {
					sb.append(" "+listData.get(pos).getHonors().get(i).getHonors_content());
					honors_content = listData.get(pos).getHonors().get(i).getHonors_content();
				}
				intent.putExtra("honors_content",honors_content);
				intent.putExtra("content",sb.toString());
				intent.putExtra("is_concern","1");
				startActivity(intent);
			}
		});

	}

	private void initView(View view) {
		attentionFighterLV = (ListView) view
				.findViewById(R.id.lv_fragAG_fighter);
		listData = new ArrayList<GameGuanZhuFighter>();
		// 获取网络数据
		getNetWork();
	}

	private void getNetWork() {
		// http://115.28.69.240/boxing/app/my_concern2.php?user_id=1&page=1
		// http://115.28.69.240/boxing/app/my_concern.php?user_id=1&page=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/my_concern2.php?user_id=" + Config.userID
						+ "&page=" + page), new CommonCallback<String>() {
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
							JSONArray js = json.getJSONArray("player");
							for (int i = 0; i < js.length(); i++) {
								JSONObject json1 = js.getJSONObject(i);
								String player_id = json1.getString("player_id");
								String is_delete = json1.getString("is_delete");
								String player_head = json1
										.getString("player_head");
								String player_name = json1
										.getString("player_name");
								String player_ageid = json1
										.getString("player_ageid");
								String player_areaid = json1
										.getString("player_areaid");
								String player_weightid = json1
										.getString("player_weightid");
								String player_age = json1
										.getString("player_age");
								String player_sex = json1
										.getString("player_sex");
								String player_area = json1
										.getString("player_area");
								String player_level = json1
										.getString("player_level");
								String player_height = json1
										.getString("player_height");
								String player_cm = json1.getString("player_cm");
								String player_special = json1
										.getString("player_special");
								String player_group = json1
										.getString("player_group");
								String player_date = json1
										.getString("player_date");
								String win = json1.getString("win");
								String drew = json1.getString("drew");
								String ko = json1.getString("ko");
								JSONArray json2 = json1.getJSONArray("honors");
								List<GameGuanZhuFighterHonors> honors = new ArrayList<GameGuanZhuFighterHonors>();
								for (int j = 0; j < json2.length(); j++) {
									JSONObject json3 = json2.getJSONObject(j);
									String honors_id = json3
											.getString("honors_id");
									String honors_content = json3
											.getString("honors_content");
									honors.add(new GameGuanZhuFighterHonors(
											honors_id, honors_content));
								}
								listData.add(new GameGuanZhuFighter(player_id,
										is_delete, player_head, player_name,
										player_ageid, player_areaid,
										player_weightid, player_age,
										player_sex, player_area, player_level,
										player_height, player_cm,
										player_special, player_group,
										player_date, honors, win, drew, ko));
							}
							Message msg=Message.obtain();
							msg.what=100;
							msg.obj=listData;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
	}
}
