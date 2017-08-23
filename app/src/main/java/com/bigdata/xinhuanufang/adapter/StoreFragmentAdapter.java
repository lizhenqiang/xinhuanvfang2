package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.HashMap;
import java.util.List;

public class StoreFragmentAdapter extends BaseAdapter {
	private List<HashMap<String, String>> list ;
	public LayoutInflater inflater = null;
	public Context mContext;

	public StoreFragmentAdapter(Context mContext2, List<HashMap<String, String>> list2) {
		this.list = list2;
		this.mContext=mContext2;
		inflater = LayoutInflater.from(mContext2);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
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
			convertView = inflater.inflate(
					R.layout.activity_liveuseing_listview_item, parent,false);
			holder = new ViewHolder();
			holder.shop_title = (TextView) convertView
					.findViewById(R.id.shop_title);
			holder.shop_pic = (ImageView) convertView
					.findViewById(R.id.shop_pic);
			holder.shop_price = (TextView) convertView
					.findViewById(R.id.shop_price);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String shop_id = list.get(position).get("shop_id");
		String shop_title = list.get(position).get("shop_title");
		String shop_price = list.get(position).get("shop_price");
		String shop_pic = list.get(position).get("shop_pic");
		 holder.shop_title.setText(shop_title);
		 holder.shop_price.setText("¥"+shop_price);
		 x.image().bind(holder.shop_pic, shop_pic);
		 System.out.println(Config.ip+shop_pic);
		
		return convertView;
	}

	class ViewHolder {
		TextView shop_title;
		ImageView shop_pic;
		TextView shop_price;
	}
}
