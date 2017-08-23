package com.bigdata.xinhuanufang.main;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.DreamListAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.bean.ShiXianDreamMingDan;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 实现梦想名单
 * */
public class DreamListActivity extends BaseActivity implements OnClickListener {
	private List<ShiXianDreamMingDan> dreamList;
	private int page = 1;
	private ListViewForScrollView lv_dreamL_dreamList; // 往期梦想揭晓ListView
	private DreamListAdapter dreamListAdapter; // 梦想揭晓ListViewAdapter

	private ImageView iv_itt_back;
	private TextView tv_adl_dataRefresh;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					dreamList=(List<ShiXianDreamMingDan>) msg.obj;
					DataAdapter(dreamList);
					break;

				default:
					break;
			}
		};
	};

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_dreamlist;
	}

	protected void DataAdapter(List<ShiXianDreamMingDan> dreamList) {
		// TODO Auto-generated method stub
		dreamListAdapter = new DreamListAdapter(DreamListActivity.this, dreamList);
		lv_dreamL_dreamList.setAdapter(dreamListAdapter);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("实现梦想名单");
		super.setGone(); // 去掉标题栏右侧文本
		/*
		 * 为返回键添加适配
		 */
		super.setBack();
		/*
		 * 为ListView添加适配
		 */
		lv_dreamL_dreamList = (ListViewForScrollView) findViewById(R.id.lv_dreamL_dreamList);
		tv_adl_dataRefresh=(TextView) findViewById(R.id.tv_adl_dataRefresh);

		iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
		// 实例化集合
		dreamList = new ArrayList<ShiXianDreamMingDan>();
		// 获取网络数据
		getNetWork();
		//获取系统时间
		SimpleDateFormat formatter= new  SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		Date curDate=new Date(System.currentTimeMillis());//获取当前时间
		String str = formatter.format(curDate);
		tv_adl_dataRefresh.setText(str);

	}

	private void getNetWork() {
		// http://115.28.69.240/boxing/app/think_before.php?page=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/think_before.php?page=" + page),
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
							JSONArray json1 = json.getJSONArray("think");
							for (int i = 0; i < json1.length(); i++) {
								JSONObject json2 = json1.getJSONObject(i);
								String think_id = json2.getString("think_id");
								String think_userid = json2
										.getString("think_userid");
								String think_title = json2
										.getString("think_title");
								String think_pic = json2.getString("think_pic");
								String user_username = json2
										.getString("user_username");
								String count = json2.getString("count");
								dreamList.add(new ShiXianDreamMingDan(think_id,
										think_userid, think_title, think_pic,
										user_username, count));
							}
							Message msg=Message.obtain();
							msg.what=0;
							msg.obj=dreamList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.iv_itt_back:
				finish();
				break;
			default:
				break;
		}
	}
}
