package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.yuanjiaoImage;
import com.bigdata.xinhuanufang.game.bean.SuperDreamGridBean;
import com.bigdata.xinhuanufang.main.AddDreamActivity;
import com.bigdata.xinhuanufang.main.SuperDreamActivity;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class SuperDreamGridViewAdapter extends BaseAdapter {
	private List<SuperDreamGridBean> list;
	public LayoutInflater inflater = null;
	public Context mContext;

	public SuperDreamGridViewAdapter(List<SuperDreamGridBean> list,
			Context mContext) {
		this.list = list;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
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
	public View getView( int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.superdream_gridview_item,
					null);
			holder = new ViewHolder();
			holder.iv_sdgi_dreamPhoto = (yuanjiaoImage) convertView
					.findViewById(R.id.iv_sdgi_dreamPhoto);
			holder.tv_sdgi_dreamTitle = (TextView) convertView
					.findViewById(R.id.tv_sdgi_dreamTitle);
			holder.super_dream_progress_baifenbi = (TextView) convertView
					.findViewById(R.id.super_dream_progress_baifenbi);
			holder.superdream_progress = (ProgressBar) convertView
					.findViewById(R.id.superdream_progress);
			holder.btn_add_dream = (Button) convertView
					.findViewById(R.id.btn_add_dream);
			if (list.get(position).getIs_add().equals("0")) {
				//加入梦想
				holder.btn_add_dream.setText("加入梦想");
			}else if (list.get(position).getIs_add().equals("1")) {
				//加入梦想
				holder.btn_add_dream.setText("已加入梦想");
			}
			holder.chaojimengxiang_paraent= (LinearLayout) convertView.findViewById(R.id.chaojimengxiang_paraent);
			holder.chaojimengxiang_paraent.setBackgroundColor(Color.WHITE);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_sdgi_dreamTitle.setText(list.get(position).getThink_title()
				+ list.get(position).getThink_price());
		String baifenbi=list.get(position)
				.getThink_percent();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < baifenbi.length(); i++) {
			if (baifenbi.charAt(i)=='.') {
				sb.append(baifenbi.charAt(i));
				if (baifenbi.length()>(i+1)) {
					sb.append(baifenbi.charAt(i+1));

				}
				if (baifenbi.length()>(i+2)) {
					sb.append(baifenbi.charAt(i+2));

				}
				break;
			}else{
				sb.append(baifenbi.charAt(i));
			}
		}
		holder.super_dream_progress_baifenbi.setText(sb.toString()+"%");
		holder.tv_sdgi_dreamTitle.setText(list.get(position).getThink_title());
//			holder.superdream_progress.setProgress(Integer.parseInt(list.get(
//					position).getThink_percent()));
			holder.superdream_progress.setProgress((int) (Double.parseDouble(list.get(
					position).getThink_percent())));

		System.out.println("加载图片的地址:" + Config.ip
				+ list.get(position).getThink_pic());
		x.image().bind(holder.iv_sdgi_dreamPhoto,
				Config.ip + list.get(position).getThink_pic());
		holder.chaojimengxiang_paraent.setId(position);
		holder.chaojimengxiang_paraent.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(mContext, AddDreamActivity.class);
				intent.putExtra("think_id", list.get(v.getId()).getThink_id());
				intent.putExtra("think_shopid", list.get(v.getId()).getThink_shopid());
				intent.putExtra("think_title",list.get(v.getId()).getThink_title());
				intent.putExtra("think_price",list.get(v.getId()).getThink_price());
				intent.putExtra("think_percent",list.get(v.getId()).getThink_percent());
				intent.putExtra("think_pic",list.get(v.getId()).getThink_pic());
				intent.putExtra("think_attrid",list.get(v.getId()).getThink_attrid());
				intent.putExtra("is_add",list.get(v.getId()).getIs_add());
//				mContext.startActivity(intent);
				((SuperDreamActivity)mContext).startActivityForResult(intent,20);
			}
		});
		holder.btn_add_dream.setId(position);
		holder.btn_add_dream.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View v) {
//				System.out.println("点击了添加梦想的按钮"+Config.ip + Config.app
//								+ "/think_add.php?user_id=" + Config.userID
//								+ "&think_id="
//								+ list.get(position).getThink_id());
				// http://115.28.69.240/boxing/app/think_add.php?user_id=1&think_id=1
				if (list.get(v.getId()).getIs_add().equals("1")) {
					Toast.makeText(mContext, "已经加入过了", Toast.LENGTH_SHORT).show();
				}else if (list.get(v.getId()).getIs_add().equals("0")) {

				x.http().get(
						new RequestParams(Config.ip + Config.app
								+ "/think_add.php?user_id=" + Config.userID
								+ "&think_id="
								+ list.get(v.getId()).getThink_id()), new Callback.CommonCallback<String>() {
									@Override
									public void onCancelled(
											CancelledException arg0) {
									}
									@Override
									public void onError(Throwable arg0,
											boolean arg1) {
									}
									@Override
									public void onFinished() {
									}
									@Override
									public void onSuccess(String arg0) {
										try {
											JSONObject json=new JSONObject(arg0);
											String code=json.getString("code");
											if (code.equals("1")) {
												Toast.makeText(mContext, "加入成功", Toast.LENGTH_SHORT).show();
												//加入成功后跳转到梦想详情
												list.get(v.getId()).setIs_add("1");
												notifyDataSetChanged();
											}
											refre.refreshing();
											Intent intent=new Intent(mContext, AddDreamActivity.class);
											intent.putExtra("think_id", list.get(v.getId()).getThink_id());
											intent.putExtra("think_shopid", list.get(v.getId()).getThink_shopid());
											intent.putExtra("think_title",list.get(v.getId()).getThink_title());
											intent.putExtra("think_price",list.get(v.getId()).getThink_price());
											intent.putExtra("think_percent",list.get(v.getId()).getThink_percent());
											intent.putExtra("think_pic",list.get(v.getId()).getThink_pic());
											intent.putExtra("is_add","1");
											mContext.startActivity(intent);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								});
				}
			}
		});

		return convertView;
	}



	class ViewHolder {
		yuanjiaoImage iv_sdgi_dreamPhoto;
		TextView tv_sdgi_dreamTitle;
		TextView super_dream_progress_baifenbi;
		ProgressBar superdream_progress;
		Button btn_add_dream;//加入梦想
		LinearLayout chaojimengxiang_paraent;
	}
	private refreshdata refre;
	public void setrefreshdata(refreshdata refre){
		this.refre=refre;
	}

	public interface  refreshdata{
		void refreshing();
	}
}
