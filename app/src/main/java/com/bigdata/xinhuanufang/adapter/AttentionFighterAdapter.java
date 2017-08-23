package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.CircleImageView;
import com.bigdata.xinhuanufang.game.bean.GameGuanZhuFighter;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.List;


/**
 * @author asus
 * 我的关注-选手Adapter
 */
public class AttentionFighterAdapter extends BaseAdapter{
	private Context context;
	private List<GameGuanZhuFighter> listData;
	private LayoutInflater mInflater;
	private OnFighterMessageClick onFighterMessageClick;
	public void setOnFighterItemClick(
			OnFighterMessageClick onFighterMessageClick) {
		this.onFighterMessageClick = onFighterMessageClick;
	}

	// 选手信息的item回调接口
	public interface OnFighterMessageClick {
		void onFighterClick(int pos);
	}

	public AttentionFighterAdapter(Context context, List<GameGuanZhuFighter> listData) {
		this.context = context;
		this.listData = listData;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return listData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.attentionfighter_listview_item,
					null);
			holder=new ViewHolder();
			holder.iv_fai_fightPhone=(CircleImageView) convertView.findViewById(R.id.iv_fai_fightPhone);
			holder.tv_fai_fighterOne=(TextView) convertView.findViewById(R.id.tv_fai_fighterOne);
			holder.tv_fai_age=(TextView) convertView.findViewById(R.id.tv_fai_age);
			holder.tv_fai_country=(TextView) convertView.findViewById(R.id.tv_fai_country);
			holder.tv_fai_level=(TextView) convertView.findViewById(R.id.tv_fai_level);
			holder.tv_fai_honor=(TextView) convertView.findViewById(R.id.tv_fai_honor);
			holder.ll_fai_item= (LinearLayout) convertView.findViewById(R.id.ll_fai_item);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		String honors = null;
		holder.tv_fai_fighterOne.setText(listData.get(position).getPlayer_name());
		holder.tv_fai_age.setText(listData.get(position).getPlayer_age());
		holder.tv_fai_country.setText(listData.get(position).getPlayer_area());
		holder.tv_fai_level.setText(listData.get(position).getPlayer_level());

		for (int i = 0; i < listData.get(position).getHonors().size(); i++) {
			honors=listData.get(position).getHonors().get(i).getHonors_content()+"  ";
		}
		holder.tv_fai_honor.setText(honors);
		x.image().bind(holder.iv_fai_fightPhone, Config.ip+listData.get(position).getPlayer_head());
		holder.ll_fai_item.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onFighterMessageClick.onFighterClick(position);
			}
		});
		return convertView;

	}

	class ViewHolder{
		CircleImageView iv_fai_fightPhone;//图片
		TextView tv_fai_fighterOne;//姓名
		TextView tv_fai_age;//年龄
		TextView tv_fai_country;//国籍
		TextView tv_fai_level;//级别
		TextView tv_fai_honor;//荣誉
		LinearLayout ll_fai_item;
	}
}
