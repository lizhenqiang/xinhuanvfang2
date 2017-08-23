package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.yuanjiaoImage;
import com.bigdata.xinhuanufang.game.bean.ShiXianDreamMingDan;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.List;


public class DreamListAdapter extends BaseAdapter {
	private Context context;
	private List<ShiXianDreamMingDan> dreamList;
	private LayoutInflater mInflater;

	public DreamListAdapter(Context context, List<ShiXianDreamMingDan> dreamList) {
		this.context = context;
		this.dreamList = dreamList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dreamList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dreamList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.dreamlist_listview_item,
					null);
			holder.tv_dli_dreamPublicTime = (TextView) convertView.findViewById(R.id.tv_dli_dreamPublicTime);
			holder.tv_dli_dreamGoodsDetails = (TextView) convertView.findViewById(R.id.tv_dli_dreamGoodsDetails);
			holder.iv_dli_dreamPhoto = (yuanjiaoImage) convertView.findViewById(R.id.iv_dli_dreamPhoto);
			holder.tv_dli_luckyHumman = (TextView) convertView.findViewById(R.id.tv_dli_luckyHumman);
			holder.tv_dli_userID = (TextView) convertView.findViewById(R.id.tv_dli_userID);
			holder.tv_dli_joinNum = (TextView) convertView.findViewById(R.id.tv_dli_joinNum);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_dli_dreamPublicTime.setText(dreamList.get(position).getThink_id());
		holder.tv_dli_dreamGoodsDetails.setText(dreamList.get(position).getThink_title());
		holder.tv_dli_luckyHumman.setText(dreamList.get(position).getUser_username());
		holder.tv_dli_userID.setText(dreamList.get(position).getThink_userid());
		holder.tv_dli_joinNum.setText(dreamList.get(position).getCount());
		x.image().bind(holder.iv_dli_dreamPhoto, Config.ip+dreamList.get(position).getThink_pic());
		return convertView;
	}

	class ViewHolder {
		TextView tv_dli_dreamPublicTime;// 期数
		TextView tv_dli_dreamGoodsDetails;// 商品名称
		yuanjiaoImage iv_dli_dreamPhoto;// 图片
		TextView tv_dli_luckyHumman;// 幸运者
		TextView tv_dli_userID;// 用户id
		TextView tv_dli_joinNum;// 参与人数
	}

}
