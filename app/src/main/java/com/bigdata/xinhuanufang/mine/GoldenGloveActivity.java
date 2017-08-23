package com.bigdata.xinhuanufang.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GoldenGloveAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.PopWindow;
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
 * 金手套交易Activity
 */
public class GoldenGloveActivity extends BaseActivity implements OnClickListener{
	private ListView goldenGloveLV;
	private List<Mine_Recharge_jinshoutaoBean> dataList;
	private GoldenGloveAdapter goldenGloveAdapter;
	private String[] num={"1","2"};

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
	private TextView jinshoutao_shuliang;

	@Override
	public int getView() {
		return R.layout.activity_goldenglove;
	}
	protected void DataAdapter(List<Mine_Recharge_jinshoutaoBean> dataList) {
		goldenGloveAdapter = new GoldenGloveAdapter(GoldenGloveActivity.this, dataList);
		goldenGloveLV.setAdapter(goldenGloveAdapter);
	}
	// 初始化组件方法
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("金手套交易");// 设置title的标题
//		super.setTextRight("筛选");
		super.setTextRight("");

		super.tv_itt_save.setOnClickListener(this);
		setBack();
		Intent intent=getIntent();
		String tv_fragMine_myGloveNum=intent.getStringExtra("tv_fragMine_myGloveNum");
		goldenGloveLV = (ListView) findViewById(R.id.lv_agg_goldenGlove);
		jinshoutao_shuliang = (TextView) findViewById(R.id.jinshoutao_shuliang);
		jinshoutao_shuliang.setText(tv_fragMine_myGloveNum);
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_itt_save:
				//筛选
				PopWindow popWindow = new PopWindow(this);
				popWindow.showPopupWindow(findViewById(R.id.tv_itt_save));

				break;

			default:
				break;
		}
	}
}
