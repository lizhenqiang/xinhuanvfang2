package com.bigdata.xinhuanufang.mine;

import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.DetailActivityAdapterAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.bean.Mine_Recharge_jinshoutaoBean;
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
 * @author asus
 * 明细Activity
 */
public class DetailActivity extends BaseActivity{
	private ListView detailLV; //明细LV
	private List<Mine_Recharge_jinshoutaoBean> dataList;
	private DetailActivityAdapterAdapter detailactivityadapteradapter; //明细Adapter

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dataList=(List<Mine_Recharge_jinshoutaoBean>) msg.obj;
					DataAdapter(dataList);
					break;

				default:
					break;
			}
		};
	};
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_detail;
	}

	protected void DataAdapter(List<Mine_Recharge_jinshoutaoBean> dataList) {
		// TODO Auto-generated method stub
		System.out.println(dataList.size());
		detailactivityadapteradapter = new DetailActivityAdapterAdapter(DetailActivity.this, dataList);
		detailLV.setAdapter(detailactivityadapteradapter);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setGone();
		setTitle("明细");
		setBack();
		detailLV = (ListView) findViewById(R.id.lv_ad_detail);
		dataList=new ArrayList<Mine_Recharge_jinshoutaoBean>();
		//获取金手套充值记录
		getNetWorkData();
	}
	private void getNetWorkData() {
		//http://115.28.69.240/boxing/app/gloves_list.php?user_id=1
		x.http().get(new RequestParams(Config.ip+Config.app+"/gloves_list.php?user_id="+Config.userID), new CommonCallback<String>() {
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
					JSONArray json1=json.getJSONArray("gloves");
					for (int i = 0; i < json1.length(); i++) {
						JSONObject json2=json1.getJSONObject(i);
						String gloves_id=json2.getString("gloves_id");
						String gloves_num=json2.getString("gloves_num");
						String gloves_date=json2.getString("gloves_date");
						dataList.add(new Mine_Recharge_jinshoutaoBean(gloves_id, gloves_num, gloves_date));
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
