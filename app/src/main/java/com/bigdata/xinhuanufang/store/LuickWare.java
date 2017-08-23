package com.bigdata.xinhuanufang.store;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.LiveUseingAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.LoadListViewnews;
import com.bigdata.xinhuanufang.game.bean.LiveUseing_shoping_List;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LuickWare extends BaseActivity implements OnItemClickListener,
		 LoadListViewnews.ILoadListener {
	// 新闻资讯的头条里面的更多
	// listview展示数据的首选项
	private List<LiveUseing_shoping_List> shop_list_child = new ArrayList<LiveUseing_shoping_List>();
	// 头条资讯listView
	private LoadListViewnews topNewsMoreLV;
	// 自定义的头条资讯adapter
	private LiveUseingAdapter topNewsMoreAdapter;
	// 记录第几页
	private String page = "1";
	// 底部加载更多数据
	View footer;
	// 记录要加载的第几页数据
	private int count = 1;

	private SetPageListener pagerlistener;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					shop_list_child = (List<LiveUseing_shoping_List>) msg.obj;
					Log.e("nidaye", shop_list_child.size() + "");
					// 将数据加载得listview上
					showListView(shop_list_child);
					break;
				case 120:
					if (shop_list_child.size()==0) {

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

	protected void showListView(List<LiveUseing_shoping_List> shop_list_child) {
		// 查找到list控件
		topNewsMoreLV = (LoadListViewnews) findViewById(R.id.lv_more_topnew);
		topNewsMoreLV.setInterface(this);
		// 在底部加载一个j
		topNewsMoreAdapter = new LiveUseingAdapter(LuickWare.this, shop_list_child);
		topNewsMoreLV.setAdapter(topNewsMoreAdapter);
		topNewsMoreLV.setOnItemClickListener(this);

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("运动搏击");
		setGone();
		setBack();
		xuanshou_quanbu_wu = (Button) findViewById(R.id.xuanshou_quanbu_wu);
		xuanshou_quanbu_wu.setVisibility(View.GONE);
	}

	@Override
	public void initData() {

		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {

				initNewDatas(count);

			}
		}).start();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub
		String shop_id=shop_list_child.get(position).getShop_id();
		Bundle bundle = new Bundle();
		bundle.putString("shop_id", shop_id);
		Intent intent = new Intent(this, GoodsDetailsActivity.class);
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
				initNewDatas(count);// 得到新数据
				topNewsMoreAdapter.notifyDataSetChanged();// 刷新ListView;
				topNewsMoreLV.loadCompleted();
			}
		}, 2000);
	}

	protected void initNewDatas(int page) {
		// 从网络获取生活用品
		// http://115.28.69.240/boxing/app/shop_list.php?type=2&page=1
		System.out.println("电子产品"+Config.ip + Config.app
				+ "/shop_list.php?type=" + Config.LuickWare + "&page="
				+ page);
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/shop_list.php?type=" + Config.LuickWare + "&page="
						+ page), new CommonCallback<String>() {

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onFinished() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(String arg0) {
						try {

							Message msg = Message.obtain();
							JSONObject json = new JSONObject(arg0);
							String code = json.getString("code");
							if (code.equals("1")) {
							JSONArray shop_list = json
									.getJSONArray("shop_list");

							for (int i = 0; i < shop_list.length(); i++) {
								JSONObject js = shop_list.getJSONObject(i);

								String shop_id=js.getString("shop_id");
								String shop_title=js.getString("shop_title");
								String shop_price=js.getString("shop_price");
								String shop_pic=js.getString("shop_pic");
								shop_list_child.add(new LiveUseing_shoping_List(shop_id, shop_title, shop_price, shop_pic));
							}

							msg.what = 110;
							msg.obj = shop_list_child;
							handler.sendMessage(msg);}
							else if (code.equals("0")) {
								msg.what = 120;
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});



	}

	/**
	 * 获取加载第几页的接口回调
	 */
	public interface SetPageListener {
		public int setpage();
	}

}
