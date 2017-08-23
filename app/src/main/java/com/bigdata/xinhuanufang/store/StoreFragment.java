package com.bigdata.xinhuanufang.store;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.StoreFragmentAdapter;
import com.bigdata.xinhuanufang.adapter.ViewpagerAdapter;
import com.bigdata.xinhuanufang.game.bean.LiveUseing_shoping;
import com.bigdata.xinhuanufang.game.bean.MainPrices;
import com.bigdata.xinhuanufang.main.SuperDreamActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.MyGridView;

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

/*
 * 商城 Fragement
 *
 * 17/02/28
 * */
public class StoreFragment extends Fragment implements OnClickListener,
		OnItemClickListener {
	private List<MainPrices> pricesList;
	private List<LiveUseing_shoping> finghter = new ArrayList<LiveUseing_shoping>();
	private int[] imageResIds = { R.drawable.zhanji, R.drawable.ic_launcher,
			R.drawable.icon, }; // Viewpager要显示的图片
	private static final int SHOW_NEXT_PAGE = 0; // 显示下一页
	private ViewPager storeViewPager; // 商城的ViewPager

	private int[] icon = { R.drawable.icon, R.drawable.icon, R.drawable.icon,
			R.drawable.icon }; // GrideView要显示的图片
	private String[] iconName = { "中意拳王对抗赛84公斤级", "中意拳王对抗赛85公斤级",
			"中意拳王对抗赛86公斤级", "中意拳王对抗赛87公斤级时钟" }; // grideView图片下的文字描述
	private MyGridView goodsGV; // 显示商品的gridView
	private List<Map<String, Object>> data_list; // 用于存放 图片和响应文字的集合
	private StoreFragmentAdapter sim_adapter; // GrideView适配器

	private ImageView shoppingCarIV; // 购物车ImageView
	private TextView shoppingCarTV; // 购物车TextView
	private ImageView backIV; // 返回键ImageView

	private ImageView store_live; // 生活用品
	private ImageView store_electron; // 电子产品
	private ImageView luick_ware; // 抽奖商品
	private ImageView store_cjmx;

	private Button all_shop; // 全部商品
	private Button big_wear; // 超级梦想

	private View all_shop_isshow;
	private View big_wear_isshow;
	private int page = 1;
	public List<HashMap<String, String>> list ;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_NEXT_PAGE:
					showNextPage();
					break;
				case 110:
					list=(List<HashMap<String, String>>) msg.obj;
					sim_adapter=new StoreFragmentAdapter(getActivity(), list);
					goodsGV.setAdapter(sim_adapter);				//setList(list);
					break;
				case 2:
					pricesList = (List<MainPrices>) msg.obj;
					PricesDataAdapter(pricesList);
					break;
			}
		}
	};
	public List<HashMap<String, String>> getList() {
		return list;
	}

	protected void PricesDataAdapter(List<MainPrices> pricesList2) {
		// TODO Auto-generated method stub
		storeViewPager.setAdapter(new ViewpagerAdapter(pricesList2,getActivity()));
	}

	public void setList(List<HashMap<String, String>> list) {
		this.list = list;
	}

	protected void showListView(List<HashMap<String, String>> list2) {
		// 查找到list控件
//		topNewsMoreLV.setInterface(this);
//		// 在底部加载一个j
//		topNewsMoreAdapter = new LiveUseingAdapter(LiveAppliance.this, finghter);
//		topNewsMoreLV.setAdapter(topNewsMoreAdapter);
//		topNewsMoreLV.setOnItemClickListener(this);

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_store, container, false);

		initView(view);

		handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000); // 3秒后显示下一页

		/*
		 * 配置GridView的相关操作
		 */
		data_list = new ArrayList<Map<String, Object>>(); // 新建LIST
		// 获取数据
		// 新建适配器
		String[] from = { "image", "text" };
		int[] to = { R.id.iv_mgi_gamePhoto, R.id.tv_mgi_title };
//		sim_adapter = new SimpleAdapter(getActivity(), data_list,
//				R.layout.store_gridview_item, from, to);
		list = new ArrayList<HashMap<String, String>>();
		// 配置适配器




//		sim_adapter=new StoreFragmentAdapter(getActivity(), list);
//		goodsGV.setAdapter(sim_adapter);

			getData(Config.ip + Config.app
					+ "/shop_list.php?type=" + Config.AllShoping
					+ "&page=" + page);




		pricesList=new ArrayList<MainPrices>();
		// 获取轮播图资源
		getPrices();

		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub


		store_cjmx = (ImageView) view.findViewById(R.id.store_cjmx);
		// 生活用品
		store_live = (ImageView) view.findViewById(R.id.store_live);
		// 电子产品
		store_electron = (ImageView) view.findViewById(R.id.store_electron);
		// 抽奖商品
		luick_ware = (ImageView) view.findViewById(R.id.luick_ware);
		storeViewPager = (ViewPager) view.findViewById(R.id.vp_fragS_store);
		goodsGV = (MyGridView) view.findViewById(R.id.gv_fragS_goods);

		goodsGV.setOnItemClickListener(this);
		backIV = (ImageView) view.findViewById(R.id.iv_itb_back);
		backIV.setVisibility(View.GONE);
		shoppingCarTV = (TextView) view.findViewById(R.id.tv_itb_title);
		shoppingCarTV.setText("商城");
		shoppingCarIV = (ImageView) view.findViewById(R.id.iv_itb_list);
		shoppingCarIV.setImageResource(R.drawable.gouwuche);


		// 查找全部商品列表
		//all_shop = (Button) view.findViewById(R.id.all_shop);


		shoppingCarIV.setOnClickListener(this);

		store_live.setOnClickListener(this);
		store_electron.setOnClickListener(this);
		luick_ware.setOnClickListener(this);

		store_cjmx.setOnClickListener(this);

	}

	/*
	 * 显示下一页
	 */
	public void showNextPage() {
		int currentItem = storeViewPager.getCurrentItem();
		storeViewPager.setCurrentItem(currentItem + 1);
		handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000);
	}

	/*
	 * 将图片和对应的文本组装到Map集合中
	 */
	private void getData(String url) {
		list.clear();
		x.http().get(
				new RequestParams(url), new CommonCallback<String>() {

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
						// TODO Auto-generated method stub
						try {
							JSONObject json = new JSONObject(arg0);
							String code = json.getString("code");
							JSONArray shop_list = json
									.getJSONArray("shop_list");
							for (int i = 0; i < shop_list.length(); i++) {
								HashMap<String, String> map = new HashMap<String, String>();
								JSONObject js = shop_list.getJSONObject(i);
								map.put("shop_id", js.getString("shop_id"));
								map.put("shop_title", js.getString("shop_title"));
								map.put("shop_price", js.getString("shop_price"));
								map.put("shop_pic", Config.ip+js.getString("shop_pic"));
//								map.put("is_wish", js.getString("is_wish"));
								list.add(map);
							}
							//sim_adapter.notifyDataSetChanged();
							Message msg = Message.obtain();
							msg.what = 110;
							msg.obj = list;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.iv_itb_list:
				startActivity(ShoppingCarActivity.class);
				break;
			case R.id.store_live:
				startActivity(LiveAppliance.class);
				break;
			case R.id.store_electron:
				startActivity(StoreElectron.class);
				break;
			case R.id.luick_ware:
				startActivity(LuickWare.class);
				break;

			case R.id.store_cjmx:
//				big_wear_isshow.setVisibility(View.VISIBLE);
//				all_shop_isshow.setVisibility(View.GONE);
				// 超级梦想
//				getData(Config.ip + Config.app
//						+ "/shop_list.php?type=" + Config.LiveUseing
//						+ "&page=" + page);
//				System.out.println("显示超级梦想数据");
				//跳转到首页的超级梦想
				Intent intent=new Intent(getActivity(), SuperDreamActivity.class);
				intent.putExtra("bioaji","shopping");
				startActivity(intent);
				break;

			default:
				break;
		}
	}

	public void startActivity(Class<?> cls) {
		Intent intent = new Intent(getActivity(), cls);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
							long id) {
		// 对条目的监听,
		Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
		String shop_id=list.get(position).get("shop_id");
		intent.putExtra("shop_id", shop_id);
		startActivity(intent);
	}

	private void getPrices() {
		// http://115.28.69.240/boxing/app/layer_list.php?status=0
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/layer_list.php?status=2"),
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
}
