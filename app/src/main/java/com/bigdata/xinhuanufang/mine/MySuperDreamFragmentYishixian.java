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
import com.bigdata.xinhuanufang.adapter.MySuperDreanFragmentYishixianAdapter;
import com.bigdata.xinhuanufang.game.bean.SuperDreamFinish;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MySuperDreamFragmentYishixian extends Fragment {
	private ListView my_super_dream_daishixian_listview;
	private List<SuperDreamFinish> dataList;
	private MySuperDreanFragmentYishixianAdapter mysuperdreanfragmentyishixianadapter;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				dataList=(List<SuperDreamFinish>) msg.obj;
				DataAdapterShow(dataList);
				break;

			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_super_dream,
				container, false);
		initView(view);
		return view;
	}

	protected void DataAdapterShow(List<SuperDreamFinish> dataList) {
		mysuperdreanfragmentyishixianadapter=new MySuperDreanFragmentYishixianAdapter(dataList, getActivity());
		my_super_dream_daishixian_listview.setAdapter(mysuperdreanfragmentyishixianadapter);
	}

	private void initView(View view) {
		my_super_dream_daishixian_listview = (ListView) view
				.findViewById(R.id.my_super_dream_daishixian_listview);
		dataList = new ArrayList<SuperDreamFinish>();
		// ��ȡ��������
		getNetWorkData();
	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/think_list2.php?user_id=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/think_list2.php?user_id=" + Config.userID),
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
							JSONArray json2 = json.getJSONArray("think");
							for (int i = 0; i < json2.length(); i++) {
								JSONObject json3 = json2.getJSONObject(i);
								String think_id = json3.getString("think_id");
								String thinkjoin_id = json3
										.getString("thinkjoin_id");
								String think_title = json3
										.getString("think_title");
								String think_pic = json3.getString("think_pic");
								String thinkjoin_gloves = json3
										.getString("thinkjoin_gloves");
								String count = json3.getString("count");
								String flag = json3.getString("flag");
								dataList.add(new SuperDreamFinish(think_id,
										thinkjoin_id, think_title, think_pic,
										thinkjoin_gloves, count, flag));
							}
							Message msg=Message.obtain();
							msg.what=100;
							msg.obj=dataList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}
}
