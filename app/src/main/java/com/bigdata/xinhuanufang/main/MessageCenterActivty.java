package com.bigdata.xinhuanufang.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MessageCenterAdapter;
import com.bigdata.xinhuanufang.adapter.MessageCenterAdapter.onMessageCenteItemClick;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.bean.jpush;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/*
 * 消息中心Activity
 * */
public class MessageCenterActivty extends BaseActivity {
	private ListView messageCenterLV; // 消息中心ListViw
	private MessageCenterAdapter messageCenterAdapter; // 消息中心ListViewAdapter
	private String[] messageTime = { "昨天 下午 12:30", "今天  上午11:30",
			"前天  中午12:00" }; // 消息时间
	private List<jpush> datalist;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 10:
					datalist= (List<jpush>) msg.obj;
					jpush_wushuju.setVisibility(View.GONE);
					showmessagelist(datalist);
					break;
				case 20:
					//没有数据
					jpush_wushuju.setVisibility(View.VISIBLE);
					break;
			}
		}
	};
	private TextView jpush_wushuju;

	private void showmessagelist(List<jpush> datalist) {
		messageCenterAdapter = new MessageCenterAdapter(this, datalist);
		messageCenterLV.setAdapter(messageCenterAdapter);
		messageCenterAdapter
				.setOnMessageItemClick(new onMessageCenteItemClick() {

					@Override
					public void onMessageClick(int po) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(MessageCenterActivty.this,
								MessageDetailActivity.class);
						startActivity(intent);
					}
				});
	}

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_messagecenter;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("消息中心"); // 设置标题栏文本
		super.setGone(); // 设置标题栏右侧文字不可见
		/*
		 * 对返回按钮添加点击事件
		 */
		super.setBack();
		datalist=new ArrayList<>();
		getdatanetwork();
		/*
		 * 为ListView添加适配
		 */
		messageCenterLV = (ListView) findViewById(R.id.lv_messageC_messageCenter);

		/*
		 * 点击查看详情跳转至消息中心
		 */
		jpush_wushuju = (TextView) findViewById(R.id.jpush_wushuju);
		jpush_wushuju.setVisibility(View.GONE);

	}

	private void getdatanetwork() {
		//http://115.28.69.240/boxing/app/jpush_list.php?user_id=100004
		RequestParams params=new RequestParams(Config.ip+Config.app+"/jpush_list.php");
		params.addBodyParameter("user_id",Config.userID);
		x.http().post(params, new Callback.CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				try {
					Message msg=Message.obtain();
					JSONObject json=new JSONObject(s);
					String code=json.getString("code");
					if (code.equals("1")) {
						JSONArray array=json.getJSONArray("jpush");
						for (int i = 0; i < array.length(); i++) {
							JSONObject js=array.getJSONObject(i);
							String jpush_id=js.getString("jpush_id");
							String jpush_userid=js.getString("jpush_userid");
							String jpush_content=js.getString("jpush_content");
							String jpush_date=js.getString("jpush_date");
							datalist.add(new jpush(jpush_id,jpush_userid,jpush_content,jpush_date));
						}

						msg.what=10;
						msg.obj=datalist;
						handler.sendMessage(msg);
					}else if (code.equals("0")) {
						msg.what=20;
						handler.sendMessage(msg);
					}

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
}
