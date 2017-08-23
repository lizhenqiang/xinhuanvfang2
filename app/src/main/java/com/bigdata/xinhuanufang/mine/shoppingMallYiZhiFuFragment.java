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
import com.bigdata.xinhuanufang.adapter.shoppingmallYiFuKuanAdapter;
import com.bigdata.xinhuanufang.game.bean.shoppingbuyInfo;
import com.bigdata.xinhuanufang.game.bean.shoppingmall_yizhifu_address;
import com.bigdata.xinhuanufang.game.bean.shoppingmall_yizhifu_cart;
import com.bigdata.xinhuanufang.game.bean.shoppingmall_yizhifu_cart_shop;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class shoppingMallYiZhiFuFragment extends Fragment {
	private ListView shopping_mall_yizhifu_listview;
	private List<shoppingbuyInfo> dataList;
	private shoppingmallYiFuKuanAdapter shoppingmallyifukuanadapter;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 100:
				dataList=(List<shoppingbuyInfo>) msg.obj;
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
		View view = inflater.inflate(
				R.layout.fragment_shoppingmall_yizhifu_list, container, false);
		dataList = new ArrayList<shoppingbuyInfo>();
		initView(view);
		return view;
	}

	protected void DataAdapter(List<shoppingbuyInfo> dataList) {
		shoppingmallyifukuanadapter=new shoppingmallYiFuKuanAdapter(dataList, getActivity());
		shopping_mall_yizhifu_listview.setAdapter(shoppingmallyifukuanadapter);
	}

	private void initView(View view) {
		shopping_mall_yizhifu_listview = (ListView) view
				.findViewById(R.id.shopping_mall_yizhifu_listview);
		// ��ȡ��������
		getNetWorkData();
	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/shop_success.php?user_id=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/shop_success.php?user_id=" + Config.userID),
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
							JSONArray json1 = json.getJSONArray("orders");
							for (int i = 0; i < json1.length(); i++) {
								JSONObject json2 = json1.getJSONObject(i);
								String orders_id = json2.getString("orders_id");
								String orders_outtradeno = json2
										.getString("orders_outtradeno");
								String orders_money = json2
										.getString("orders_money");
								String orders_message = json2
										.getString("orders_message");
								String orders_addressid = json2
										.getString("orders_addressid");
								String orders_date = json2
										.getString("orders_date");
								JSONArray json3 = json2.getJSONArray("cart");
								List<shoppingmall_yizhifu_cart> cart = new ArrayList<shoppingmall_yizhifu_cart>();
								// ���ﳵ��Ʒ����
								for (int j = 0; j < json3.length(); j++) {
									JSONObject json4 = json3.getJSONObject(j);
									String cart_id = json4.getString("cart_id");
									String cart_shopid = json4
											.getString("cart_shopid");
									String cart_num = json4
											.getString("cart_num");
									String cart_price = json4
											.getString("cart_price");
									String attrs = json4.getString("attrs");
									JSONObject json5 = json4
											.getJSONObject("shop");
									List<shoppingmall_yizhifu_cart_shop> shop = new ArrayList<shoppingmall_yizhifu_cart_shop>();
										
										String shop_title = json5
												.getString("shop_title");
										String shop_pic = json5
												.getString("shop_pic");
										shop.add(new shoppingmall_yizhifu_cart_shop(
												shop_title, shop_pic));
									cart.add(new shoppingmall_yizhifu_cart(
											cart_id, cart_shopid, cart_num,
											cart_price, attrs, shop));
								}
								// �ջ���ַ����
								JSONObject json7 = json2.getJSONObject("address");
								List<shoppingmall_yizhifu_address> address = new ArrayList<shoppingmall_yizhifu_address>();
									String address_name = json7
											.getString("address_name");
									String address_tel = json7
											.getString("address_tel");
									String address_content = json7
											.getString("address_content");
									address.add(new shoppingmall_yizhifu_address(
											address_name, address_tel,
											address_content));
								dataList.add(new shoppingbuyInfo(orders_id,
										orders_outtradeno, orders_money,
										orders_message, orders_addressid,
										orders_date, cart, address));
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
