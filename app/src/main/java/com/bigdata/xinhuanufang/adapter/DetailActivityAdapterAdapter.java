package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.Mine_Recharge_jinshoutaoBean;
import com.bigdata.xinhuanufang.utils.configUtils;

import java.util.List;

/**
 * @author asus
 * 金手套充值明细Adapter
 */
public class DetailActivityAdapterAdapter extends BaseAdapter{
	private Context context;
	private List<Mine_Recharge_jinshoutaoBean> dataList;
	private LayoutInflater mInflater;

	public DetailActivityAdapterAdapter(Context context, List<Mine_Recharge_jinshoutaoBean> dataList) {
		this.context = context;
		this.dataList = dataList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.goldenglove_listview_item, null);
			holder=new ViewHolder();
			holder.jinshoutao_pay_num=(TextView) convertView.findViewById(R.id.jinshoutao_pay_num);
			holder.jinshoutao_pay_date=(TextView) convertView.findViewById(R.id.jinshoutao_pay_data);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.jinshoutao_pay_num.setText(dataList.get(position).getGloves_num());
		String strTime = configUtils.getStrTimess(dataList.get(position).getGloves_date());
		holder.jinshoutao_pay_date.setText(strTime);

		return convertView;

	}
	
	class ViewHolder{
		TextView jinshoutao_pay_date;
		TextView jinshoutao_pay_num;
	}
}
