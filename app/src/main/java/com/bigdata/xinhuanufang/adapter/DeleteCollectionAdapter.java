package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bigdata.xinhuanufang.R;

/**
 * @author asus 删除我的收藏adapter
 */
public class DeleteCollectionAdapter extends BaseAdapter {

	private Context context;
	private String[] fighterOne;
	private LayoutInflater mInflater;

	public DeleteCollectionAdapter(Context context, String[] fighterOne) {
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
			convertView = mInflater.inflate(
					R.layout.deletecollection_listview_item, null);
		}

		return convertView;

	}

}
