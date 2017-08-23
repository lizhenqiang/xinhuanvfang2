package com.bigdata.xinhuanufang.main;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GameGussingAdapter;
import com.bigdata.xinhuanufang.game.bean.GameJiaYouJinCaiBiSai;
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
 * 比赛竞猜Fragment
 *
 * 17/03/02
 * */
public class GameGussingFragment extends Fragment {
	private ListView gameGussingLV;// 比赛竞猜ListView
	private GameGussingAdapter gameGussingAdapter;
	private String[] finghterOne = { "青格勒1", "青格勒2", "青格勒3", "青格勒4", "青格勒5",
			"青格勒6" }; // 比赛人员
	private String[] finghterTwo = { "费朗西丝1", "费朗西丝2", "费朗西丝3", "费朗西丝4",
			"费朗西丝5", "费朗西丝6" }; // 比赛人员
	private int[] icon = { R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon, R.drawable.icon, }; // ListView要显示的图片

	private List<GameJiaYouJinCaiBiSai> dataList;
	private boolean isrefresh=false;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dataList=(List<GameJiaYouJinCaiBiSai>) msg.obj;
					DataAdapter(dataList);
					break;

				default:
					break;
			}
		};
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gamegussing, container,
				false);
		dataList = new ArrayList<GameJiaYouJinCaiBiSai>();
		initView(view);


		// 获取网络数据
		getNetWorkData();
		DataAdapter(dataList);
		return view;
	}

	protected void DataAdapter(List<GameJiaYouJinCaiBiSai> dataList2) {
		gameGussingAdapter = new GameGussingAdapter(getActivity(), dataList2);
		gameGussingLV.setAdapter(gameGussingAdapter);

	}

	@Override
	public void onResume() {
		super.onPause();
		if (isrefresh) {
			getNetWorkData();
			isrefresh=false;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		isrefresh=true;
	}

	private void getNetWorkData() {
		dataList.clear();
		// http://115.28.69.240/boxing/app/liveguess_list.php?user_id=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/liveguess_list.php?user_id=" + Config.userID),

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
							JSONArray json1 = json.getJSONArray("liveguess");
							for (int i = 0; i < json1.length(); i++) {
								JSONObject json2 = json1.getJSONObject(i);
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
								dataList.add(new GameJiaYouJinCaiBiSai(
										liveguess_id, liveguess_pic,
										liveguess_time, liveguess_title,
										liveguess_playera, liveguess_playerb,
										liveguess_content, playera_head,
										playera_name, playerb_head,
										playerb_name, joina, joinb, suma, sumb,
										concern));
							}
							Message msg=Message.obtain();
							msg.what=0;
							msg.obj=dataList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	// 初始化组件方法
	private void initView(View view) {
		gameGussingLV = (ListView) view
				.findViewById(R.id.lv_fgg_gameGus);

	}
}
