package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.MineBreakEggRecordBean;
import com.bigdata.xinhuanufang.utils.configUtils;

import java.util.List;
/**
 * 砸金蛋
 * @author weiyu$
 *
 */
public class BreakEggFragmentRecordAdapter extends BaseAdapter{

	private Context context;
	List<MineBreakEggRecordBean> dataList;
	private LayoutInflater mInflater;
	public BreakEggFragmentRecordAdapter(Context context,List<MineBreakEggRecordBean> dataList){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fragment_mine_break_egg, null);
			holder=new ViewHolder();
			holder.tv_dli_break_eggTime=(TextView) convertView.findViewById(R.id.tv_dli_break_eggTime);
			holder.tv_dli_break_GoodsDetails=(TextView) convertView.findViewById(R.id.tv_dli_break_GoodsDetails);
			holder.tv_dli_luckyshopping=(TextView) convertView.findViewById(R.id.tv_dli_luckyshopping);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		String strtime= configUtils.getStrTimess(dataList.get(position).getEgg2_date());
		holder.tv_dli_break_eggTime.setText(strtime);
		holder.tv_dli_luckyshopping.setText("获得奖品:"+dataList.get(position).getEgg2_num());
		return convertView;
	}

	class ViewHolder{
		TextView tv_dli_break_eggTime;
		TextView tv_dli_break_GoodsDetails;
		TextView tv_dli_luckyshopping;
	}
}
