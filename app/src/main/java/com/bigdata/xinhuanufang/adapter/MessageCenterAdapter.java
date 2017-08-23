package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.bean.jpush;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.configUtils;

import java.util.List;

public class MessageCenterAdapter extends BaseAdapter {
	private Context context;
	private List<jpush> datalist;
	private LayoutInflater mInflater;
	private onMessageCenteItemClick messageCenteItemClick;

	public void setOnMessageItemClick(
			onMessageCenteItemClick messageCenteItemClick) {
		this.messageCenteItemClick = messageCenteItemClick;
	}

	//点击事件的回调接口
	public interface onMessageCenteItemClick {
		void onMessageClick(int pos);
	}

	public MessageCenterAdapter(Context context, List<jpush> datalist) {
		this.context = context;
		this.datalist = datalist;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datalist.size();
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
	public View getView(final int position, View convertView, ViewGroup paret) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater
					.inflate(
							com.bigdata.xinhuanufang.R.layout.messagecenter_listview_item,
							null);
			holder.tv_mclv_time= (TextView) convertView.findViewById(R.id.tv_mclv_time);
			holder.tv_mcli_messageKey= (TextView) convertView.findViewById(R.id.tv_mcli_messageKey);
			holder.tv_mcli_clientName= (TextView) convertView.findViewById(R.id.tv_mcli_clientName);

			holder.checkDetailLayout = (RelativeLayout) convertView
					.findViewById(R.id.rl_mcli_checkDetails);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String s=datalist.get(position).getJpush_date();
		String time= configUtils.getStrTimes(datalist.get(position).getJpush_date());
		holder.tv_mclv_time.setText(time);
		holder.tv_mcli_messageKey.setText(datalist.get(position).getJpush_content());
		holder.tv_mcli_clientName.setText(Config.USER_USERNAME);
		holder.checkDetailLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				messageCenteItemClick.onMessageClick(position);
			}
		});
		return convertView;
	}

	class ViewHolder {
		public RelativeLayout checkDetailLayout; // 查看消息详情布局
		TextView tv_mclv_time;
		TextView tv_mcli_messageKey;
		TextView tv_mcli_clientName;
	}

}
