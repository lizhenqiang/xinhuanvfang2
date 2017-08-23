package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.LiveUseing_shoping_List;
import com.bigdata.xinhuanufang.utils.Config;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LiveUseingAdapter extends BaseAdapter{
	private Context context;
	private List<LiveUseing_shoping_List> fighter;
	private LayoutInflater mInflater;
	
	public LiveUseingAdapter(Context context, List<LiveUseing_shoping_List> fighter) {
		this.context = context;
		this.fighter = fighter;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fighter.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return fighter.get(arg0);
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
					R.layout.activity_liveuseing_listview_item, null);
			holder = new ViewHolder();
			holder.shop_title=(TextView) convertView.findViewById(R.id.shop_title);
			holder.shop_pic=(ImageView) convertView.findViewById(R.id.shop_pic);
			holder.shop_price=(TextView) convertView.findViewById(R.id.shop_price);
			convertView.setTag(holder);
			
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		
			holder.shop_title.setText(fighter.get(position).getShop_title());
			holder.shop_price.setText("¥"+fighter.get(position).getShop_price());
			Picasso.with(context)
			.load(Config.ip+fighter.get(position).getShop_pic())
			.into(holder.shop_pic);
		
		return convertView;
	}
	class ViewHolder{
		TextView shop_title;
		ImageView shop_pic;
		TextView shop_price;
	}
}
