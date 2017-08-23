package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bigdata.xinhuanufang.game.bean.GameJiaYouJinCaiBiSai;

import java.util.List;

public class GuessingFragmentRecordAdapter extends BaseAdapter{

	private Context context;
	List<GameJiaYouJinCaiBiSai> dataList;
	private LayoutInflater mInflater;
	private boolean flag=true;
	public GuessingFragmentRecordAdapter(Context context,List<GameJiaYouJinCaiBiSai> dataList){
		this.context = context;
		this.dataList = dataList;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
