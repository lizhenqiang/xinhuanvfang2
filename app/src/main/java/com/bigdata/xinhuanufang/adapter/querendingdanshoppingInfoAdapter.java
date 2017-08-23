package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.NetworkDataCar;
import com.bigdata.xinhuanufang.utils.Config;
import com.squareup.picasso.Picasso;

import java.util.List;

public class querendingdanshoppingInfoAdapter extends BaseAdapter{
	private Context context;
	private List<NetworkDataCar> shopingCarData;
	private LayoutInflater mInflater;
	private String[] str;

	public querendingdanshoppingInfoAdapter(Context context, List<NetworkDataCar> shopingCarData,String[] str) {
		this.context = context;
		this.shopingCarData = shopingCarData;
		this.str = str;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return str.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return shopingCarData.get(0).getCart().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.shopping_info_queren_list, null);
			holder = new ViewHolder();
			holder.tv_sli_productName=(TextView) convertView.findViewById(R.id.tv_sli_productName);
			holder.iv_sli_productPhoto=(ImageView) convertView.findViewById(R.id.iv_sli_productPhoto);
			holder.tv_sli_price=(TextView) convertView.findViewById(R.id.tv_sli_price);
			holder.enter_shoping_color_info=(TextView) convertView.findViewById(R.id.enter_shoping_color_info);
			convertView.setTag(holder);
			
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		int length = str.length;
		String s=shopingCarData.get(0).getCart().get(position).getCart_id();
		String a=str[position];
		if (str[position].equals(shopingCarData.get(0).getCart().get(position).getCart_id())) {
			int pos=position;
			String ss=shopingCarData.get(0).getCart().get(position).getCart_id();
			String aa=str[position];
			holder.tv_sli_productName.setText(shopingCarData.get(0).getCart().get(position).getShop_title());
			holder.tv_sli_price.setText("¥"+shopingCarData.get(0).getCart().get(position).getCart_price());
			holder.enter_shoping_color_info.setText(shopingCarData.get(0).getCart().get(position).getCart_attr());
			Picasso.with(context)
					.load(Config.ip+shopingCarData.get(0).getCart().get(position).getShop_pic())
					.into(holder.iv_sli_productPhoto);
		}


		
		return convertView;
	}
	class ViewHolder{
		ImageView iv_sli_productPhoto;//图片
		TextView tv_sli_productName;//名称
		TextView enter_shoping_color_info;//属性
		TextView tv_sli_price;//价格
	}
}
