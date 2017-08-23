package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.List;

public class SearchGameAdapter extends BaseAdapter{
	private Context context;
	private List<GameDetailsInfo> list;
	private LayoutInflater mInflater;
	
	public SearchGameAdapter(Context context, List<GameDetailsInfo> list) {
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
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
					R.layout.gamereview_listview_item, null);
			holder = new ViewHolder();
			holder.tv_grli_fighterOne=(TextView) convertView.findViewById(R.id.tv_grli_fighterOne);
			holder.tv_grli_fighterTwo=(TextView) convertView.findViewById(R.id.tv_grli_fighterTwo);
			holder.iv_grli_fightPhone=(ImageView) convertView.findViewById(R.id.iv_grli_fightPhone);
			holder.tv_grli_gameRuler=(TextView) convertView.findViewById(R.id.tv_grli_gameRuler);
			holder.tv_grli_time=(TextView) convertView.findViewById(R.id.tv_grli_time);
			holder.tv_grli_winHuman=(TextView) convertView.findViewById(R.id.tv_grli_winHuman);
			convertView.setTag(holder);
			
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		
			holder.tv_grli_fighterOne.setText(list.get(arg0).getPlayer_namea());
			holder.tv_grli_fighterTwo.setText(list.get(arg0).getPlayer_nameb());
			holder.tv_grli_gameRuler.setText(list.get(arg0).getVideo_title());
			holder.tv_grli_time.setText(list.get(arg0).getVideo_time());
			holder.tv_grli_winHuman.setText(list.get(arg0).getPlayer_success());
			x.image().bind(holder.iv_grli_fightPhone, Config.ip+list.get(arg0).getVideo_pic());
		
		return convertView;
	}
	class ViewHolder{
		TextView tv_grli_fighterOne;//选手1
		TextView tv_grli_fighterTwo;//选手2
		ImageView iv_grli_fightPhone;//图片
		TextView tv_grli_gameRuler;//标题
		TextView tv_grli_time;//时间
		TextView tv_grli_winHuman;//获胜者
	}
}
