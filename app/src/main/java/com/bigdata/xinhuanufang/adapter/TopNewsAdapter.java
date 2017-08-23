package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.NewsBean;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author asus
 * 头条资讯Adapter
 */
public class TopNewsAdapter extends BaseAdapter {
	private Context context;
	private List<NewsBean> list;
	private LayoutInflater mInflater;
	private final int type1=0;
	private final int type2=1;
	
	
	//头条资讯回调接口
	
	public TopNewsAdapter(Context context, List<NewsBean> list) {
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("集合大小"+list.size());
		return 8;
	}
	

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(0).getmList1().get(arg0);
	}
	
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int	position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		
		if (convertView == null) {
			
				convertView = mInflater.inflate(
						R.layout.topnews_listview_item, null);
				holder = new ViewHolder();
				holder.tv_title=(TextView) convertView.findViewById(R.id.tv_tnli_fighterOne);
				holder.tv_picture=(ImageView) convertView.findViewById(R.id.iv_topN_fightPhone);
				holder.tv_title2=(TextView) convertView.findViewById(R.id.tv_grli_fighterTwo);
				holder.tv_date=(TextView) convertView.findViewById(R.id.tv_grli_time);
				convertView.setTag(holder);
				
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		//时间格式转换
		String strTime = configUtils.getStrTimes((list.get(0).getmList1().get(position)).getNews_date());
		Picasso.with(context).load(Config.ip+ list.get(0).getmList1().get(position).getNews_pic()).into(holder.tv_picture);
//		x.image().bind(holder.tv_picture, Config.ip+ list.get(0).getmList1().get(position).getNews_pic());
		holder.tv_title.setText((list.get(0).getmList1().get(position)).getNews_title());
		holder.tv_date.setText(strTime);
		holder.tv_title2.setText((list.get(0).getmList1().get(position)).getNews_title2());

		return convertView;
	}
	class ViewHolder{
		TextView tv_title;
		ImageView tv_picture;
		TextView tv_title2;
		TextView tv_date;
	}
}
