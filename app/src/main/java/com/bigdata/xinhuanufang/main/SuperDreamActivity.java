package com.bigdata.xinhuanufang.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.SuperDreamGridViewAdapter;
import com.bigdata.xinhuanufang.adapter.ViewpagerAdapter;
import com.bigdata.xinhuanufang.game.bean.LuckUserBean;
import com.bigdata.xinhuanufang.game.bean.MainPrices;
import com.bigdata.xinhuanufang.game.bean.SuperDreamGridBean;
import com.bigdata.xinhuanufang.game.bean.SuperDreamGridPicBean;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.MyGridViews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超级梦想Activity
 * 17/03/01
 * */
public class SuperDreamActivity extends Activity implements OnClickListener {
	private List<MainPrices> pricesList;
	private ImageView iv_superD_back; // 返回ImageView
	private ImageView iv_superD_dreamList; // 梦想名单ImageView
	private List<LuckUserBean> luckList;
	private MyGridViews gview; // 超级梦想GridView
	private List<SuperDreamGridBean> gridDataList;
	private ViewPager vp_SuperD; // 超级梦想ViewPager
	private List<Map<String, Object>> data_list;
	private SuperDreamGridViewAdapter superdreamgridviewadapter; // GrideView适配器
	private static final int SHOW_NEXT_PAGE = 0; // 显示下一页
	private int[] imageResIds = { R.drawable.back_1, R.drawable.ic_launcher,
			R.drawable.icon, }; // Viewpager要显示的图片
	private int[] icon = { R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon, R.drawable.icon, R.drawable.icon }; // GrideView要显示的图片
	private String[] iconName = { "京东购物卡100元", "京东购物卡200元", "京东购物卡300元",
			"京东购物卡400元", "京东购物卡300元", "京东购物卡400元" }; // GrideView图片详情

	private TextView luck_name;
	private TextView luck_context;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_NEXT_PAGE:
					// 跳转下一页
					showNextPage();
					break;
				case 2:
					pricesList = (List<MainPrices>) msg.obj;
					PricesDataAdapter(pricesList);
					break;
				case 3:
					luckList = (List<LuckUserBean>) msg.obj;
					System.out.println(luckList.get(0).getUser_username()
							+ luckList.get(0).getThink_title());
					luck_name.setText(luckList.get(0).getUser_username());
					luck_context.setText(luckList.get(0).getThink_title());
					break;
				case 4:
					gridDataList = (List<SuperDreamGridBean>) msg.obj;
					GridDataAdapter(gridDataList);
					break;
			}
		}
	};
	private String biaoji="-1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_superdreamer);

		// 初始化组件方法
		initView();

		// 为ViewPager设置Adapter
		handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000); // 3秒后显示下一页

		data_list = new ArrayList<Map<String, Object>>(); // 新建LIST
		getData(); // 获取数据

		pricesList = new ArrayList<MainPrices>();
		Intent intent=getIntent();
		try {
			biaoji = intent.getStringExtra("biaoji");
			if (!biaoji.equals("-1")) {
				Config.isbiaoshi=biaoji;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		// 获取轮播图资源
		getPrices();
		luckList = new ArrayList<LuckUserBean>();
		// 获取小喇叭通知
		getLuckUser();
		gridDataList=new ArrayList<SuperDreamGridBean>();
		// 获取grid的数据列表
		getGridData();
	}

	protected void GridDataAdapter( List<SuperDreamGridBean> gridDataList) {
		// 配置适配器
		superdreamgridviewadapter = new SuperDreamGridViewAdapter(gridDataList,
				this);
		gview.setAdapter(superdreamgridviewadapter);
		superdreamgridviewadapter.setrefreshdata(new SuperDreamGridViewAdapter.refreshdata() {
			@Override
			public void refreshing() {
				getGridData();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode==20) {
//			Toast.makeText(this, "刷新数据", Toast.LENGTH_SHORT).show();
			getGridData();
		}
	}


	private void getGridData() {
		gridDataList.clear();
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/think_list.php?user_id=" + Config.userID),
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
								String think_shopid = json2
										.getString("think_shopid");
								String think_attrid = json2
										.getString("think_attrid");
								String think_percent = json2
										.getString("think_percent");
								String think_title = json2
										.getString("think_title");
								String think_pic = json2.getString("think_pic");
								String think_price = json2
										.getString("think_price");
								JSONArray json3 = json2.getJSONArray("pic");
								List<SuperDreamGridPicBean> pic = new ArrayList<SuperDreamGridPicBean>();
								for (int j = 0; j < json3.length(); j++) {
									JSONObject json4 = json3.getJSONObject(j);
									String shoppic_id = json4
											.getString("shoppic_id");
									String shoppic_shopid = json4
											.getString("shoppic_shopid");
									String shoppic_pic = json4
											.getString("shoppic_pic");
									pic.add(new SuperDreamGridPicBean(
											shoppic_id, shoppic_shopid,
											shoppic_pic));
								}
								String is_add = json2.getString("is_add");
								gridDataList.add(new SuperDreamGridBean(
										think_id, think_shopid, think_attrid,
										think_percent, think_title, think_pic,
										think_price, pic, is_add));
							}
							Message msg = Message.obtain();
							msg.what = 4;
							msg.obj = gridDataList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	protected void PricesDataAdapter(List<MainPrices> pricesList2) {
		// TODO Auto-generated method stub
		vp_SuperD.setAdapter(new ViewpagerAdapter(pricesList2,SuperDreamActivity.this)); //
	}

	private void getPrices() {
		// http://115.28.69.240/boxing/app/layer_list.php?status=0
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/layer_list.php?status=1"),
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
							JSONArray json1 = json.getJSONArray("layer");
							for (int i = 0; i < json1.length(); i++) {
								JSONObject json2 = json1.getJSONObject(i);
								String layer_id = json2.getString("layer_id");
								String layer_flag = json2
										.getString("layer_flag");
								String layer_pic = json2.getString("layer_pic");
								String layer_status = json2
										.getString("layer_status");
								pricesList.add(new MainPrices(layer_id,
										layer_flag, layer_pic, layer_status));
							}
							Message msg = Message.obtain();
							msg.what = 2;
							msg.obj = pricesList;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < icon.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icon[i]);
			map.put("text", iconName[i]);
			data_list.add(map);
		}
		return data_list;
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_superD_dreamList = (ImageView) findViewById(R.id.iv_superD_list);
		vp_SuperD = (ViewPager) findViewById(R.id.vp_superD);
		gview = (MyGridViews) findViewById(R.id.gv_asd_dreamProgress);
		iv_superD_back = (ImageView) findViewById(R.id.iv_superD_back);
		iv_superD_back.setOnClickListener(this);
		iv_superD_dreamList.setOnClickListener(this);

		// 通知:
		luck_name = (TextView) findViewById(R.id.luck_name);
		luck_context = (TextView) findViewById(R.id.luck_context);

	}

	private void getLuckUser() {
		// http://115.28.69.240/boxing/app/think_say.php
		x.http().get(
				new RequestParams(Config.ip + Config.app + "/think_say.php"),
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
							JSONObject json1 = json.getJSONObject("think");
							String think_userid = json1
									.getString("think_userid");
							String think_title = json1.getString("think_title");
							String user_username = json1
									.getString("user_username");
							luckList.add(new LuckUserBean(think_userid,
									think_title, user_username));
							Message msg = Message.obtain();
							msg.what = 3;
							msg.obj = luckList;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}

	/*
	 * 显示下一页
	 */
	public void showNextPage() {
		int currentItem = vp_SuperD.getCurrentItem();
		vp_SuperD.setCurrentItem(currentItem + 1);
		handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.iv_superD_back:
				finish();
				break;
			case R.id.iv_superD_list:
				//实现梦想名单
				Intent dreamListIntent = new Intent(this, DreamListActivity.class);
				startActivity(dreamListIntent);
				break;
			default:
				break;
		}
	}

}
