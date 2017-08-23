package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.TopMore;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopNewsMoreAdapter extends BaseAdapter{
	private Context context;
	private List<TopMore> fighter;
	private LayoutInflater mInflater;
	
	public TopNewsMoreAdapter(Context context, List<TopMore> fighter) {
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
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.topnews_listview_item, null);
			holder = new ViewHolder();
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_tnli_fighterOne);
			holder.tv_title2=(TextView) convertView.findViewById(R.id.tv_grli_fighterTwo);
			holder.iv_pivture=(ImageView) convertView.findViewById(R.id.iv_topN_fightPhone);
			holder.tv_date=(TextView) convertView.findViewById(R.id.tv_grli_time);
			convertView.setTag(holder);
			
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		//时间格式转换
		String strTime = configUtils.getStrTimes(fighter.get(arg0).getNews_date());
			holder.tv_title.setText(fighter.get(arg0).getNews_title());
			holder.tv_title2.setText(fighter.get(arg0).getNews_title2());
			holder.tv_date.setText(strTime);
			Picasso.with(context)
			.load(Config.ip+fighter.get(arg0).getNews_pic())
			.into(holder.iv_pivture);
		
		return convertView;
	}
	class ViewHolder{
		TextView tv_title;
		TextView tv_title2;
		ImageView iv_pivture;
		TextView tv_date;
	}
}
