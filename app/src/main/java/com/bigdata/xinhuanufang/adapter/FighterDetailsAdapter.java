package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bigdata.xinhuanufang.R;

/**
 * @author asus
 * 选手信息Adapter
 */
public class FighterDetailsAdapter extends BaseAdapter{
	private Context context;
	private String[] fighterOne;
	private LayoutInflater mInflater;

	public FighterDetailsAdapter(Context context, String[] fighterOne) {
		this.context = context;
		this.fighterOne = fighterOne;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fighterOne.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.fighterdetail_listview_item,
					null);
		}

		return convertView;

	}
}
