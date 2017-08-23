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
import com.bigdata.xinhuanufang.game.bean.MyShare;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.xutils.x;

import java.util.List;


public class MyShareAdapter extends BaseAdapter {
	private Context context;
	private List<MyShare> dataList;
	private LayoutInflater mInflater;
	private String isshow;


	public MyShareAdapter(Context context, List<MyShare> dataList, String isshow) {
		this.context = context;
		this.dataList = dataList;
		mInflater = LayoutInflater.from(context);
		this.isshow = isshow;
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
		ViewHolde holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.mycollection_listview_item, null);
			holder = new ViewHolde();
			holder.iv_grli_fightPhone = (ImageView) convertView
					.findViewById(R.id.iv_grli_fightPhone);
			holder.tv_grli_fighterOne = (TextView) convertView
					.findViewById(R.id.tv_grli_fighterOne);
			holder.tv_grli_fighterTwo = (TextView) convertView
					.findViewById(R.id.tv_grli_fighterTwo);
			holder.tv_grli_gameRuler = (TextView) convertView
					.findViewById(R.id.tv_grli_gameRuler);
			holder.tv_grli_time = (TextView) convertView
					.findViewById(R.id.tv_grli_time);
			holder.share_checkbox = (CheckBox) convertView
					.findViewById(R.id.share_checkbox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolde) convertView.getTag();
		}
		
		holder.tv_grli_fighterOne.setText(dataList.get(position)
				.getShare_playernamea());
		holder.tv_grli_fighterTwo.setText(dataList.get(position)
				.getShare_playernameb());
		holder.tv_grli_gameRuler.setText(dataList.get(position)
				.getShare_title());
		String strTime = configUtils.getStrTime(dataList.get(position).getShare_time());
		holder.tv_grli_time.setText(strTime);
		x.image().bind(holder.iv_grli_fightPhone,
				Config.ip + dataList.get(position).getShare_pic());
		if (isshow.equals("完成")) {
			holder.share_checkbox.setVisibility(View.INVISIBLE);
		} else if (isshow.equals("删除")) {
			holder.share_checkbox.setVisibility(View.VISIBLE);
		} else if (isshow.equals("全选")) {
			holder.share_checkbox.setVisibility(View.VISIBLE);
			holder.share_checkbox.setChecked(true);
		} else if (isshow.equals("不全选")) {
			holder.share_checkbox.setVisibility(View.VISIBLE);
			holder.share_checkbox.setChecked(false);
		}
		
		holder.share_checkbox.setTag(position);
		holder.share_checkbox
				.setOnCheckedChangeListener(new CheckBoxChangeListener());
		
		return convertView;

	}

	class ViewHolde {
		ImageView iv_grli_fightPhone;// 图片
		TextView tv_grli_fighterOne;// 选手1
		TextView tv_grli_fighterTwo;// 选手2
		TextView tv_grli_gameRuler;// 标题
		TextView tv_grli_time;// 时间
		CheckBox share_checkbox;
	}

	private class CheckBoxChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			int position = (Integer) buttonView.getTag();
			if (changeListener != null) {
				changeListener.onChanged(position, isChecked);
			}
			
		}
	}
	private  onCheckBoxChangeListener changeListener;
	/**
	 * 选中按钮监听回调
	 */

	public void setOnBoxChangeListener(onCheckBoxChangeListener changeListener) {
		this.changeListener = changeListener;
	}
/**
 * 获取索引和是否选中
 * @author weiyu$
 *
 */
	public interface onCheckBoxChangeListener {
		void onChanged(int position, boolean isChecked);
	}

}
