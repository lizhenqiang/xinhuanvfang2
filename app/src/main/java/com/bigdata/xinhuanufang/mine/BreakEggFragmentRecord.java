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
import com.bigdata.xinhuanufang.adapter.BreakEggFragmentRecordAdapter;
import com.bigdata.xinhuanufang.game.bean.MineBreakEggRecordBean;
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
 * 砸金蛋记录
 * @author weiyu$
 *
 */
public class BreakEggFragmentRecord extends Fragment{
	private ListView gameGussingLV;
	private List<MineBreakEggRecordBean> dataList;
	private BreakEggFragmentRecordAdapter breakeggfragmentrecordadapter;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dataList=(List<MineBreakEggRecordBean>) msg.obj;
					DataAdapter(dataList);
					break;

				default:
					break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_gamegussing, container,
				false);
		initView(view);
		return view;
	}

	protected void DataAdapter(List<MineBreakEggRecordBean> dataList) {
		breakeggfragmentrecordadapter=new BreakEggFragmentRecordAdapter(getActivity(), dataList);
		gameGussingLV.setAdapter(breakeggfragmentrecordadapter);
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		gameGussingLV = (ListView) view
				.findViewById(R.id.lv_fgg_gameGus);
		dataList=new ArrayList<MineBreakEggRecordBean>();
		getNetWorkData();

	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/egg_data.php?user_id=1
		x.http().get(new RequestParams(Config.ip+Config.app+"/egg_data.php?user_id="+Config.userID), new CommonCallback<String>() {
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
					JSONObject json=new JSONObject(arg0);
					JSONArray json1=json.getJSONArray("egg2");
					for (int i = 0; i < json1.length(); i++) {
						JSONObject json2=json1.getJSONObject(i);
						String egg2_id=json2.getString("egg2_id");
						String egg2_userid=json2.getString("egg2_userid");
						String egg2_num=json2.getString("egg2_num");
						String egg2_date=json2.getString("egg2_date");
						dataList.add(new MineBreakEggRecordBean(egg2_id, egg2_userid, egg2_num, egg2_date));
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
}
