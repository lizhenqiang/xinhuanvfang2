package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.CircleImageView;

import java.io.File;
import java.util.List;

public class SuggestImageAdapter extends BaseAdapter {
	private Context context;
	private List<String> fighter;
	private LayoutInflater mInflater;
	

	public SuggestImageAdapter(Context context, List<String> fighter) {
		this.context = context;
		this.fighter = fighter;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fighter.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return fighter.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;
		if (convertView==null) {
			convertView = mInflater.inflate(
					R.layout.suggest_image_list, parent,false);
			holder=new ViewHolder();
			holder.list_image=(CircleImageView) convertView.findViewById(R.id.list_image);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.list_image.setImageURI(Uri.fromFile(new File(fighter.get(position))));
		return convertView;
	}
	
	class ViewHolder{
		CircleImageView list_image;
	}

}
