package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.liveListBean;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.xutils.x;

import java.util.List;

public class MainFragmentAdapter extends BaseAdapter {
	private List<liveListBean> list;
	public LayoutInflater inflater = null;
	public Context mContext;

	public MainFragmentAdapter(Context mContext2, List<liveListBean> list) {
		this.list = list;
		this.mContext = mContext2;
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
			convertView = inflater.inflate(R.layout.main_gridview_item, parent,
					false);
			holder = new ViewHolder();
			holder.tv_mgi_title = (TextView) convertView
					.findViewById(R.id.tv_mgi_title);
			holder.iv_mgi_gamePhoto = (ImageView) convertView
					.findViewById(R.id.iv_mgi_gamePhoto);
			holder.iv_mgvi_zhibozhong = (ImageView) convertView
					.findViewById(R.id.iv_mgvi_zhibozhong);
			holder.tv_mgi_hunmanNum = (TextView) convertView
					.findViewById(R.id.tv_mgi_hunmanNum);
			holder.imageView1= (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String a=list.get(position).getLive_status();
		if (list.get(position).getLive_status().equals("")) {
			//预告
			holder.tv_mgi_title.setText(list.get(position).getLiveguess_title());
			if (!list.get(position).getLiveguess_time().isEmpty()||!list.get(position).getLiveguess_time().equals("")) {
				String strTimess = configUtils.getStrTimess(list.get(position).getLiveguess_time());
				holder.tv_mgi_hunmanNum.setText(strTimess);
			}

			x.image().bind(holder.iv_mgi_gamePhoto, Config.ip+list.get(position).getLiveguess_pic());
			holder.iv_mgvi_zhibozhong.setImageResource(R.drawable.sy_weikaishi);
		}else if (list.get(position).getLive_status().equals("1")){

			holder.tv_mgi_title.setText(list.get(position).getLive_title());
			if (!list.get(position).getLiveguess_time().isEmpty()||!list.get(position).getLiveguess_time().equals("")) {
				String strTimess = configUtils.getStrTimess(list.get(position).getLiveguess_time());
				holder.tv_mgi_hunmanNum.setText(strTimess);
			}
			x.image().bind(holder.iv_mgi_gamePhoto, Config.ip + list.get(position).getLive_pic());
		}
		else if (list.get(position).getLive_status().equals("2")){
			holder.tv_mgi_title.setText(list.get(position).getLive_title());
			holder.tv_mgi_hunmanNum.setVisibility(View.GONE);
			x.image().bind(holder.iv_mgi_gamePhoto, Config.ip + list.get(position).getLive_pic());
//			holder.tv_mgi_title.setVisibility(View.GONE);
//			holder.tv_mgi_hunmanNum.setVisibility(View.GONE);
//			holder.iv_mgi_gamePhoto.setVisibility(View.GONE);
//			holder.imageView1.setVisibility(View.GONE);
		}

		return convertView;
	}

	class ViewHolder {
		TextView tv_mgi_title;
		ImageView iv_mgi_gamePhoto;
		ImageView iv_mgvi_zhibozhong;
		TextView tv_mgi_hunmanNum;
		ImageView imageView1;
	}
}
