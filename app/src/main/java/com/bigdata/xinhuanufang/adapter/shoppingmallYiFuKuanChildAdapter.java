package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.shoppingbuyInfo;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.List;

public class shoppingmallYiFuKuanChildAdapter extends BaseAdapter {
	private List<shoppingbuyInfo> dataList;// 比赛数据
	private Context context;
	private LayoutInflater mInflater;
	private int index;

	public shoppingmallYiFuKuanChildAdapter(
			List<shoppingbuyInfo> dataList,int index, Context context) {
		this.dataList = dataList;
		this.index=index;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.get(index).getCart().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(index).getCart().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			
			convertView = mInflater.inflate(
					R.layout.shopping_mall_yizhifu_listview_item_child, null);
			holder = new ViewHolder();
			holder.iv_shoping_yizhifu_info_pic = (ImageView) convertView
					.findViewById(R.id.iv_shoping_yizhifu_info_pic);
			holder.shopping_name = (TextView) convertView
					.findViewById(R.id.shopping_name);
			holder.shopping_price = (TextView) convertView
					.findViewById(R.id.shopping_price);
			holder.shopping_num = (TextView) convertView
					.findViewById(R.id.shopping_num);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.shopping_name.setText(dataList.get(index).getCart().get(position).getShop().get(0)
				.getShop_title());
		holder.shopping_price.setText(dataList.get(index).getCart().get(position).getCart_price());
		holder.shopping_num.setText("×" + dataList.get(index).getCart().get(position).getCart_num());
		x.image()
				.bind(holder.iv_shoping_yizhifu_info_pic,
						Config.ip
								+ dataList.get(index).getCart().get(position).getShop().get(0)
										.getShop_pic());
		return convertView;
	}

	class ViewHolder {
		ImageView iv_shoping_yizhifu_info_pic;
		TextView shopping_name;
		TextView shopping_price;
		TextView shopping_num;
	}

}
