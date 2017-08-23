package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.SuperDreamFinish;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class MySuperDreanFragmentYishixianAdapter extends BaseAdapter {
	private List<SuperDreamFinish> dataList;// 比赛数据
	private Context context;
	private LayoutInflater mInflater;

	public MySuperDreanFragmentYishixianAdapter(
			List<SuperDreamFinish> dataList, Context context) {
		this.dataList = dataList;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.superdream_listview_item,
					null);
			holder = new ViewHolder();
			holder.tv_sdli_dreamPublicTime = (TextView) convertView
					.findViewById(R.id.tv_sdli_dreamPublicTime);
			holder.iv_sdli_dreamPhoto = (ImageView) convertView
					.findViewById(R.id.iv_sdli_dreamPhoto);
			holder.tv_sdli_dreamGoodsDetails = (TextView) convertView
					.findViewById(R.id.tv_sdli_dreamGoodsDetails);
			holder.tv_sdli_luckyHumman = (TextView) convertView
					.findViewById(R.id.tv_sdli_luckyHumman);
			holder.tv_sdli_joinNum = (TextView) convertView
					.findViewById(R.id.tv_sdli_joinNum);
			holder.super_dream_finish_delete = (Button) convertView
					.findViewById(R.id.super_dream_finish_delete);
			holder.super_dream_result = (TextView) convertView
					.findViewById(R.id.super_dream_result);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_sdli_dreamPublicTime.setText("第"
				+ dataList.get(position).getThink_id() + "期");
		holder.tv_sdli_dreamGoodsDetails.setText(dataList.get(position)
				.getThink_title());
		holder.tv_sdli_luckyHumman.setText(dataList.get(position)
				.getThinkjoin_gloves());
		holder.tv_sdli_joinNum.setText(dataList.get(position).getCount()+"人次");
		x.image().bind(holder.iv_sdli_dreamPhoto,
				Config.ip + dataList.get(position).getThink_pic());
		if (dataList.get(position).getFlag().equals("1")) {
			// 正在揭晓
			holder.super_dream_result.setText("正在揭晓");
			holder.super_dream_result.setTextColor(Color.RED);
		}
		if (dataList.get(position).getFlag().equals("2")) {
			// 梦想实现
			holder.super_dream_result.setText("梦想实现");
			holder.super_dream_result.setTextColor(Color.RED);
		}
		if (dataList.get(position).getFlag().equals("3")) {
			// 梦想落空
			holder.super_dream_result.setText("梦想落空");
			holder.super_dream_result.setTextColor(Color.RED);
		}
		System.out.println("删除已实现的地址"+Config.ip
										+ Config.app
										+ "/think_del2.php?thinkjoin_id="
										+ dataList.get(position)
												.getThinkjoin_id());
		holder.super_dream_finish_delete.setText("删除梦想记录");
		holder.super_dream_finish_delete
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// http://115.28.69.240/boxing/app/think_del2.php?thinkjoin_id=5
						// http://115.28.69.240/boxing/app/think_del2.php?thinkjoin_id=5
						x.http().get(
								new RequestParams(Config.ip
										+ Config.app
										+ "/think_del2.php?thinkjoin_id="
										+ dataList.get(position)
												.getThinkjoin_id()),
								new CommonCallback<String>() {
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
											System.out.println("code的值"+code);
											if (code.equals("0")) {
												Toast.makeText(context, "删除失败,请检查网络", Toast.LENGTH_SHORT).show();
											}
											if (code.equals("1")) {
												Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										dataList.remove(position);
										notifyDataSetChanged();
										
									}
								});
					}
				});

		return convertView;
	}

	class ViewHolder {
		TextView tv_sdli_dreamPublicTime;// 商品的期数
		ImageView iv_sdli_dreamPhoto;// 商品的图片
		TextView tv_sdli_dreamGoodsDetails;// 商品的名称
		TextView tv_sdli_luckyHumman;// 花费的金手套的个数
		TextView tv_sdli_joinNum;// 本期参与人数
		Button super_dream_finish_delete;// 删除订单的按钮
		TextView super_dream_result;// 梦想的结果
	}

}
