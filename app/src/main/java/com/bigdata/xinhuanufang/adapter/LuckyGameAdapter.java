package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.bean.MineChouJiangGame;
import com.bigdata.xinhuanufang.utils.configUtils;

import java.util.List;

public class LuckyGameAdapter extends BaseAdapter{
	private Context context;
	private List<MineChouJiangGame> datalist;
	private LayoutInflater mInflater;

	public LuckyGameAdapter(Context context, List<MineChouJiangGame> datalist) {
		this.context = context;
		this.datalist = datalist;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return datalist.size();
	}

	@Override
	public Object getItem(int arg0) {
		return datalist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.luckygame_listview_item, null);
			holder=new ViewHolder();
			holder.choujiang_jinshoutao_number= (TextView) convertView.findViewById(R.id.choujiang_jinshoutao_number);
			holder.choujiang_jinshoutao_data= (TextView) convertView.findViewById(R.id.choujiang_jinshoutao_data);
			convertView.setTag(holder);
		}else{
			holder= (ViewHolder) convertView.getTag();
		}
		holder.choujiang_jinshoutao_number.setText(datalist.get(position).getGame_gloves());
		String strTimess = configUtils.getStrTimess(datalist.get(position).getGame_date5());
		holder.choujiang_jinshoutao_data.setText(strTimess);

		return convertView;

	}

	class ViewHolder{
		TextView choujiang_jinshoutao_data;
		TextView choujiang_jinshoutao_number;
	}
}
