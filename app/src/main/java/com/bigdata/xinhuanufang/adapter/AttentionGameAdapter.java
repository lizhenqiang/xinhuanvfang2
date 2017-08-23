package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.ReviewDetailsActivity;
import com.bigdata.xinhuanufang.game.bean.GameGuanZhu;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.List;


/**
 * @author asus
 * 我的关注-比赛Adapter
 */
public class AttentionGameAdapter extends BaseAdapter{
	private Context context;
	private List<GameGuanZhu> listData;
	private LayoutInflater mInflater;

	public AttentionGameAdapter(Context context, List<GameGuanZhu> listData) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.attentiongame_listview_item,
					null);
			holder=new ViewHolder();
			holder.iv_ali_fightPhone=(ImageView) convertView.findViewById(R.id.iv_ali_fightPhone);
			holder.tv_ali_fighterOne=(TextView) convertView.findViewById(R.id.tv_ali_fighterOne);
			holder.tv_ali_fighterTwo=(TextView) convertView.findViewById(R.id.tv_ali_fighterTwo);
			holder.tv_ali_gameRuler=(TextView) convertView.findViewById(R.id.tv_ali_gameRuler);
			holder.tv_ali_time=(TextView) convertView.findViewById(R.id.tv_ali_time);
			holder.ll_ali_item= (LinearLayout) convertView.findViewById(R.id.ll_ali_item);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_ali_fighterOne.setText(listData.get(position).getConcern_playernamea());
		holder.tv_ali_fighterTwo.setText(listData.get(position).getConcern_playernameb());
		holder.tv_ali_gameRuler.setText(listData.get(position).getConcern_title());
		holder.tv_ali_time.setText(listData.get(position).getConcern_time());
		x.image().bind(holder.iv_ali_fightPhone, Config.ip+listData.get(position).getConcern_pic());
		holder.ll_ali_item.setId(position);
		holder.ll_ali_item.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int pos=v.getId();
				Bundle bundle=new Bundle();
				bundle.putString("context", null);
				bundle.putInt("position", pos);
				bundle.putInt("tag",3);
				bundle.putString("video_liveguessid",listData.get(pos).getConcern_liveguessid());
				Intent intent=new Intent(context, ReviewDetailsActivity.class);

				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return convertView;

	}
	
	class ViewHolder{
		ImageView iv_ali_fightPhone;//图片
		TextView tv_ali_fighterOne;//选手1
		TextView tv_ali_fighterTwo;//选手2
		TextView tv_ali_gameRuler;//标题
		TextView tv_ali_time;//时间
		LinearLayout ll_ali_item;
	}
}
