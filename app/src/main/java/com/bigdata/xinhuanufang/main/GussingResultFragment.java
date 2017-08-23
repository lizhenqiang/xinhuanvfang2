package com.bigdata.xinhuanufang.main;

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
import com.bigdata.xinhuanufang.adapter.GussingResultAdapter;
import com.bigdata.xinhuanufang.adapter.GussingResultAdapter.OnGussingResultItemClick;
import com.bigdata.xinhuanufang.game.bean.GameJiaYouJinCaiResult;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/*
 * 竞猜结果Fragment
 *
 * 17/03/02
 * */
public class GussingResultFragment extends Fragment {
	private List<GameJiaYouJinCaiResult> dataList;
	private ListView lv_fgr_gussingResult; // 竞猜结果ListView
	private GussingResultAdapter gussingResultAdapter; // 竞猜结果Adapter
	private String[] fighterOne = { "青格勒", "青格勒", "青格勒", "青格勒", "青格勒", "青格勒",
			"青格勒" }; // 拳击选手一号
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					dataList=(List<GameJiaYouJinCaiResult>) msg.obj;
					DataAdapter(dataList);
					break;

				default:
					break;
			}
		};
	};


	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_gussingresult,
				container, false);
		initView(view);
		dataList = new ArrayList<GameJiaYouJinCaiResult>();
		// 获取数据
		getNetWorkData();
		return view;
	}

	protected void DataAdapter(final List<GameJiaYouJinCaiResult> dataList) {
		gussingResultAdapter = new GussingResultAdapter(getActivity(),
				dataList);
		lv_fgr_gussingResult.setAdapter(gussingResultAdapter);
		gussingResultAdapter
				.setOnGussingResultItemClick(new OnGussingResultItemClick() {

					@Override
					public void onGussingResultClick(int pos) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getActivity(),
								GussingResulDetailActivity.class);
						intent.putExtra("liveguess_id", dataList.get(pos).getLiveguess_id());
						intent.putExtra("liveguess_pic", dataList.get(pos).getLiveguess_pic());
						intent.putExtra("liveguess_title", dataList.get(pos).getLiveguess_title());
						intent.putExtra("liveguess_time2", dataList.get(pos).getLiveguess_time2());
						intent.putExtra("liveguess_content", dataList.get(pos).getLiveguess_content());
						intent.putExtra("liveguess_playera", dataList.get(pos).getLiveguess_playera());
						intent.putExtra("liveguess_playerb", dataList.get(pos).getLiveguess_playerb());
						intent.putExtra("liveguess_playerid", dataList.get(pos).getLiveguess_playerid());
						intent.putExtra("guess_id", dataList.get(pos).getGuess_id());
						intent.putExtra("guess_playerid", dataList.get(pos).getGuess_playerid());
						intent.putExtra("guess_isprize", dataList.get(pos).getGuess_isprize());
						intent.putExtra("guess_num", dataList.get(pos).getGuess_num());
						intent.putExtra("playera_head", dataList.get(pos).getPlayera_head());
						intent.putExtra("playera_name", dataList.get(pos).getPlayera_name());
						intent.putExtra("playerb_head", dataList.get(pos).getPlayerb_head());
						intent.putExtra("playerb_name", dataList.get(pos).getPlayerb_name());
						intent.putExtra("joina", dataList.get(pos).getJoina());
						intent.putExtra("joinb", dataList.get(pos).getJoinb());
						intent.putExtra("suma", dataList.get(pos).getSuma());
						intent.putExtra("sumb", dataList.get(pos).getSumb());
						intent.putExtra("sucess", dataList.get(pos).getSucess());
						startActivity(intent);
					}
				});
	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/liveguessresult_list.php?user_id=1
		x.http()
				.get(new RequestParams(Config.ip + Config.app
								+ "/liveguessresult_list.php?user_id=" + Config.userID),
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
									JSONArray json1 = json
											.getJSONArray("guess");
									for (int i = 0; i < json1.length(); i++) {
										JSONObject json2 = json1
												.getJSONObject(i);
										String liveguess_id = json2
												.getString("liveguess_id");
										String liveguess_pic = json2
												.getString("liveguess_pic");
										String liveguess_title = json2
												.getString("liveguess_title");
										String liveguess_time2 = json2
												.getString("liveguess_time2");
										String liveguess_content = json2
												.getString("liveguess_content");
										String liveguess_playera = json2
												.getString("liveguess_playera");
										String liveguess_playerb = json2
												.getString("liveguess_playerb");
										String liveguess_playerid = json2
												.getString("liveguess_playerid");
										String guess_id = json2
												.getString("guess_id");
										String guess_playerid = json2
												.getString("guess_playerid");
										String guess_isprize = json2
												.getString("guess_isprize");
										String guess_num = json2
												.getString("guess_num");
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
										String sucess = json2
												.getString("sucess");
										dataList.add(new GameJiaYouJinCaiResult(
												liveguess_id, liveguess_pic,
												liveguess_title,
												liveguess_time2,
												liveguess_content,
												liveguess_playera,
												liveguess_playerb,
												liveguess_playerid, guess_id,
												guess_playerid, guess_isprize,
												guess_num, playera_head,
												playera_name, playerb_head,
												playerb_name, joina, joinb,
												suma, sumb, sucess));
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

	private void initView(View view) {
		// TODO Auto-generated method stub
		lv_fgr_gussingResult = (ListView) view
				.findViewById(R.id.lv_fgr_gussingResult);


	}
}
