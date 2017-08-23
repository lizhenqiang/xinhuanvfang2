package com.bigdata.xinhuanufang.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.TopNewsMoreAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.LoadListViewnews;
import com.bigdata.xinhuanufang.game.bean.TopMore;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 头条资讯
 */
public class TopNewsActivity extends BaseActivity implements OnItemClickListener, LoadListViewnews.ILoadListener {
	//新闻资讯的头条里面的更多
	//listview展示数据的首选项
	private List<TopMore> finghter=new ArrayList<TopMore>();
	// 头条资讯listView
	private LoadListViewnews topNewsMoreLV;
	// 自定义的头条资讯adapter
	private TopNewsMoreAdapter topNewsMoreAdapter;
	//底部加载更多数据
	View footer;
	//记录要加载的第几页数据
	private int count=1;

	private SetPageListener pagerlistener;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					finghter=(List<TopMore>) msg.obj;
					Log.e("nidaye", finghter.size() + "");
					//将数据加载得listview上
					showListView(finghter);
					break;
				case 120:
					if (finghter.size()==0) {

						xuanshou_quanbu_wu.setVisibility(View.VISIBLE);
					}
					break;
				default:
					break;
			}
		};

	};
	private Button xuanshou_quanbu_wu;


	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_topnews;
	}
	protected void showListView(List<TopMore> finghter) {
		// 查找到list控件
		topNewsMoreLV = (LoadListViewnews) findViewById(R.id.lv_more_topnew);
		topNewsMoreLV.setInterface(this);
		ProgressBar pb= (ProgressBar) findViewById(R.id.pb_toutiaozixun);
		//在底部加载一个j
		topNewsMoreAdapter = new TopNewsMoreAdapter(TopNewsActivity.this,
				finghter);
		topNewsMoreLV.setAdapter(topNewsMoreAdapter);
		topNewsMoreLV.setOnItemClickListener(this);

	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("头条资讯");
		setGone();
		setBack();
		xuanshou_quanbu_wu = (Button) findViewById(R.id.xuanshou_quanbu_wu);
		xuanshou_quanbu_wu.setVisibility(View.GONE);
	}
	@Override
	public void initData() {


		// TODO Auto-generated method stub
		////http://115.28.69.240/boxing/app/news_list1.php?status=0&page=1
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//"http://115.28.69.240/boxing/app/news_list1.php?status=0&page=2"
				/*byte[] buffer = Httptools.getData(Config.ip+Config.app+"/news_list1.php?status=0&page=1");
				String params=new String(buffer);
				String result = new String(buffer);
				if(TextUtils.isEmpty(result)){
					return;
				}else{
					try {
						JSONObject json = new JSONObject(result);
						JSONArray json1=json.getJSONArray("list");
						for (int i = 0; i < json1.length(); i++) {
							JSONObject js=json1.getJSONObject(i);
							String id=js.getString("news_id");
							String news_pic=js.getString("news_pic");
							String news_title=js.getString("news_title");
							String news_title2=js.getString("news_title2");
							String news_date=js.getString("news_date");
							finghter.add(new TopMore(id, news_pic, news_title, news_title2, news_date));
						}
						Message msg= Message.obtain();
						msg.what=110;
						msg.obj=finghter;
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}*/
				initNewDatas(count);

			}
		}).start();



	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Bundle bundle=new Bundle();
		bundle.putString("news_id", finghter.get(position).getId());
		Intent intent=new Intent(this,Information.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				count++;
				initNewDatas(count);//得到新数据
				topNewsMoreAdapter.notifyDataSetChanged();//刷新ListView;
				topNewsMoreLV.loadCompleted();
			}
		}, 2000);
	}
	protected void initNewDatas(int page) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//"http://115.28.69.240/boxing/app/news_list1.php?status=0&page=2"
        System.out.println("现在加载数据的路径"+Config.ip + Config.app + "/news_list1.php?status=0&page=" + page);
        x.http().get(new RequestParams(Config.ip + Config.app + "/news_list1.php?status=0&page=" + page), new Callback.CommonCallback<String>() {
				@Override
				public void onSuccess(String s) {
					try {
						Message msg= Message.obtain();
						JSONObject json = new JSONObject(s);
						String code=json.getString("code");
						if (code.equals("1")) {


						JSONArray json1=json.getJSONArray("list");
						for (int i = 0; i < json1.length(); i++) {
							JSONObject js=json1.getJSONObject(i);
							String id=js.getString("news_id");
							String news_pic=js.getString("news_pic");
							String news_title=js.getString("news_title");
							String news_title2=js.getString("news_title2");
							String news_date=js.getString("news_date");
							finghter.add(new TopMore(id, news_pic, news_title, news_title2, news_date));
						}

						msg.what=110;
						msg.obj=finghter;
						handler.sendMessage(msg);
						}else if (code.equals("0")) {
							msg.what=120;
							handler.sendMessage(msg);
						}
					} catch (Exception e) {
						// TODO: handle exception
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




	/**
	 * 获取加载第几页的接口回调
	 */
	public interface SetPageListener{
		public int setpage();
	}

}
	

	

