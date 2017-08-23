package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.ListViewNesting;
import com.bigdata.xinhuanufang.game.bean.shoppingbuyInfo;
import com.bigdata.xinhuanufang.mine.ShoppingOrderInfo;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import static android.R.id.list;

public class shoppingmallYiFuKuanAdapter extends BaseAdapter {
	private List<shoppingbuyInfo> dataList;// 比赛数据
	private Context context;
	private LayoutInflater mInflater;
	private int shoppingnum = 0;// 商品的数量
	private double shoppingprice = 0.00;// 商品的价格
	private double yunfei = 0.00;
	private shoppingmallYiFuKuanChildAdapter shoppingmallyifukuanchildadapter;

	public shoppingmallYiFuKuanAdapter(List<shoppingbuyInfo> dataList,
			Context context) {
		this.dataList = dataList;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.shopping_mall_yizhifu_listview_item, null);
			holder = new ViewHolder();
			holder.shopping_mall_bianhao = (TextView) convertView
					.findViewById(R.id.shopping_mall_bianhao);
			holder.shopping_mall_time = (TextView) convertView
					.findViewById(R.id.shopping_mall_time);
			holder.shopping_mall_info = (TextView) convertView
					.findViewById(R.id.shopping_mall_info);
			holder.shopping_mall_yizhifu_listview_child = (ListViewNesting) convertView
					.findViewById(R.id.shopping_mall_yizhifu_listview_child);
			holder.shopping_mall_yizhifu_delete = (Button) convertView
					.findViewById(R.id.shopping_mall_yizhifu_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.shopping_mall_bianhao.setText(dataList.get(position)
				.getOrders_outtradeno());
		//时间格式转换
		String strTime = configUtils.getStrTimess(dataList.get(position).getOrders_date());
		holder.shopping_mall_time.setText(strTime);
		// 商品总价
		shoppingprice = Double.parseDouble(dataList.get(position)
				.getOrders_money());
		// 商品数量
		shoppingnum = dataList.get(position).getCart().size();
		holder.shopping_mall_info.setText("共" + shoppingnum + "件商品" + " 合计:¥ "
				+ shoppingprice + "  (含运费¥" + yunfei + ")");
		// 删除订单按钮进行监听
		holder.shopping_mall_yizhifu_delete
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// http://115.28.69.240/boxing/app/shop_del.php?orders_id=3
						x.http()
								.get(new RequestParams(Config.ip + Config.app
										+ "/shop_del.php?orders_id="
										+ dataList.get(position).getOrders_id()),
										new CommonCallback<String>() {
											@Override
											public void onCancelled(
													CancelledException arg0) {
											}
											@Override
											public void onError(Throwable arg0,
													boolean arg1) {
											}
											@Override
											public void onFinished() {
											}
											@Override
											public void onSuccess(String arg0) {
												try {
													JSONObject json=new JSONObject(arg0);
													String code=json.getString("code");
													if (code.equals("1")) {
														Toast.makeText(context, "订单删除成功", 0).show();
														dataList.remove(position);
														notifyDataSetChanged();
													}
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												
											}
										});

					}
				});
		// 给子adapter设置适配器
		shoppingmallyifukuanchildadapter = new shoppingmallYiFuKuanChildAdapter(
				dataList, position, context);
		holder.shopping_mall_yizhifu_listview_child
				.setAdapter(shoppingmallyifukuanchildadapter);
		holder.shopping_mall_yizhifu_listview_child.setBackgroundColor(Color
				.parseColor("#f8f8f4"));
		holder.shopping_mall_yizhifu_listview_child
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						// 对条目的监听,
						Intent intent = new Intent(context,
								ShoppingOrderInfo.class);
						// 将所需要的数据全部传递过去
						// 收货人姓名
						intent.putExtra("shoping_address_name",
								dataList.get(position).getAddress().get(0)
										.getAddress_name());
						// 手机号
						intent.putExtra("shoping_address_tel",
								dataList.get(position).getAddress().get(0)
										.getAddress_tel());
						// 收货地址
						intent.putExtra("shoping_address_content", dataList
								.get(position).getAddress().get(0)
								.getAddress_content());
						// 订单编号
						intent.putExtra(
								"shoppingmall_details_shopping_bianhao",
								dataList.get(position).getOrders_outtradeno());
						// 商品图片
						intent.putExtra("shoppingmall_details_shopping_pic",
								dataList.get(position).getCart().get(arg2)
										.getShop().get(0).getShop_pic());
						// 商品名称
						intent.putExtra("shoppingmall_details_shopping_title",
								dataList.get(position).getCart().get(arg2)
										.getShop().get(0).getShop_title());
						// 商品属性
						intent.putExtra("shoppingmall_details_shopping_attrs",
								dataList.get(position).getCart().get(arg2)
										.getAttrs());
						// 商品价格
						intent.putExtra("shoppingmall_details_shopping_price",
								dataList.get(position).getCart().get(arg2)
										.getCart_price());
						// 商品数量
						intent.putExtra("shoppingmall_details_shopping_num",
								dataList.get(position).getCart().get(arg2)
										.getCart_num());
						// 实际付款金额
						intent.putExtra(
								"shoppingmall_details_shopping_money",
								Double.parseDouble(dataList.get(position)
										.getCart().get(arg2).getCart_price())
										* Double.parseDouble(dataList
												.get(position).getCart()
												.get(arg2).getCart_num()) + "");
						String shop_id = dataList.get(position).getCart()
								.get(arg2).getCart_id();
						intent.putExtra("shop_id", shop_id);
						context.startActivity(intent);
					}
				});

		return convertView;
	}

	class ViewHolder {
		TextView shopping_mall_bianhao;
		TextView shopping_mall_time;
		ListViewNesting shopping_mall_yizhifu_listview_child;
		TextView shopping_mall_info;
		Button shopping_mall_yizhifu_delete;
	}

}
