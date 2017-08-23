package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

import java.util.LinkedHashMap;
import java.util.List;

public class MyCollectionAdapter extends BaseAdapter{

	private Context context;
	private List<GameDetailsInfo> dataList;
	private LayoutInflater mInflater;
	private String state;
	private LinkedHashMap<Integer, Boolean> isSelected;
	public MyCollectionAdapter(Context context, List<GameDetailsInfo> dataList,String state) {
		this.context = context;
		this.dataList = dataList;
		mInflater = LayoutInflater.from(context);
		isSelected = new LinkedHashMap();
		this.state=state;
		initData();
	}

	private void initData() {
		for (int i = 0; i < dataList.size(); i++) {
			getIsSelected().put(i, false);
		}
	}
	public LinkedHashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(LinkedHashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
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
			convertView = mInflater.inflate(
					R.layout.mycollection_listview_item, null);
			holder=new ViewHolder();
			holder.tv_grli_fighterOne=(TextView) convertView.findViewById(R.id.tv_grli_fighterOne);
			holder.tv_grli_fighterTwo=(TextView) convertView.findViewById(R.id.tv_grli_fighterTwo);
			holder.tv_grli_gameRuler=(TextView) convertView.findViewById(R.id.tv_grli_gameRuler);
			holder.tv_grli_time=(TextView) convertView.findViewById(R.id.tv_grli_time);
			holder.iv_grli_fightPhone=(ImageView) convertView.findViewById(R.id.iv_grli_fightPhone);
			holder.share_checkbox=(CheckBox) convertView.findViewById(R.id.share_checkbox);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_grli_fighterOne.setText(dataList.get(position).getPlayer_namea());
		holder.tv_grli_fighterTwo.setText(dataList.get(position).getPlayer_nameb());
		holder.tv_grli_gameRuler.setText(dataList.get(position).getVideo_title());
		holder.tv_grli_time.setText(dataList.get(position).getVideo_time());
		x.image().bind(holder.iv_grli_fightPhone, Config.ip+dataList.get(position).getVideo_pic());
		if (state.equals("完成")) {
			holder.share_checkbox.setVisibility(View.INVISIBLE);
		}else if (state.equals("删除")) {
			holder.share_checkbox.setVisibility(View.VISIBLE);
		}else if (state.equals("全选")) {
			holder.share_checkbox.setVisibility(View.VISIBLE);
			holder.share_checkbox.setChecked(true);
		}else if (state.equals("不全选")) {
			holder.share_checkbox.setVisibility(View.VISIBLE);
			holder.share_checkbox.setChecked(false);
		}
		holder.share_checkbox.setTag(position);
		holder.share_checkbox.setOnCheckedChangeListener(new CheckBoxChangeListener());
		return convertView;

	}
	class ViewHolder{
		ImageView iv_grli_fightPhone;//图片
		TextView tv_grli_fighterOne;//选手1
		TextView tv_grli_fighterTwo;//选手2
		TextView tv_grli_gameRuler;//标题
		TextView tv_grli_time;//时间
		CheckBox share_checkbox;
	}
	private class CheckBoxChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int position = (Integer) buttonView.getTag();
			getIsSelected().put(position, isChecked);
			if (changeListener != null) {
				changeListener.onChanged(position, isChecked);
				System.out.println("选定的position"+position);
			}

		}
	}
	/**
	 * 选中按钮监听回调
	 */
	private onCheckBoxChangeListener changeListener;

	public void setOnBoxChangeListener(onCheckBoxChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	public interface onCheckBoxChangeListener {
		void onChanged(int position, boolean isChecked);
	}

}
