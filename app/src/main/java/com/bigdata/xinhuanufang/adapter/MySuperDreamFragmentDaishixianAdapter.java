package com.bigdata.xinhuanufang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.bean.mysuperdreamdaishixian;
import com.bigdata.xinhuanufang.game.bean.AddJinShouTao;
import com.bigdata.xinhuanufang.mine.RechargeJinShouTao;
import com.bigdata.xinhuanufang.mine.RechargeJinShouTao.payType;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MySuperDreamFragmentDaishixianAdapter extends BaseAdapter {
	private List<mysuperdreamdaishixian> dataList;// 比赛数据
	private List<AddJinShouTao> jinshoutaoList;
	private Context context;
	private LayoutInflater mInflater;
	private String state = null;
	private int num = 10;// 假设等于10
	private int index = -1;;
	private TextView jinshoutao_count;
	private int gloves;

	public MySuperDreamFragmentDaishixianAdapter(Context context,
												 List<mysuperdreamdaishixian> dataList, String state) {
		this.context = context;
		this.dataList = dataList;
		this.state = state;
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
	public View getView( int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.mysuperdreamfragment_yishixian_item, null);
			holder = new ViewHolder();
			// holder.super_dream_checkbox=(CheckBox)
			// convertView.findViewById(R.id.super_dream_checkbox);
			holder.super_dream_image = (ImageView) convertView
					.findViewById(R.id.super_dream_image);
			holder.super_dream_title = (TextView) convertView
					.findViewById(R.id.super_dream_title);
			holder.super_dream_progress = (TextView) convertView
					.findViewById(R.id.super_dream_progress);
			holder.jinshoutao_zengjia = (ImageButton) convertView
					.findViewById(R.id.jinshoutao_zengjia);
			holder.jinshoutao_count = (TextView) convertView
					.findViewById(R.id.jinshoutao_count);
			holder.jinshoutao_jianshao = (ImageButton) convertView
					.findViewById(R.id.jinshoutao_jianshao);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		jinshoutaoList = new ArrayList<AddJinShouTao>();
		// 数据
		holder.super_dream_title.setText(dataList.get(position)
				.getThink_title());
		String progress=dataList.get(position).getThink_percent();
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < progress.length(); i++) {
			if (progress.charAt(i)=='.') {
				sb.append(progress.charAt(i));
				if (progress.length()>(i+1)) {
					sb.append(progress.charAt(i+1));

				}
				if (progress.length()>(i+2)) {
					sb.append(progress.charAt(i+2));

				}
				break;
			}else{
				sb.append(progress.charAt(i));
			}
		}

		holder.super_dream_progress.setText("当前进度:"+sb.toString()+" %");
		x.image().bind(holder.super_dream_image,
				Config.ip + dataList.get(position).getThink_pic());
		if (state.equals("全选")) {
			holder.super_dream_checkbox.setChecked(true);
		}
		if (state.equals("不全选")) {
			holder.super_dream_checkbox.setChecked(false);
		}
		// holder.super_dream_checkbox.setTag(position);
		// holder.super_dream_checkbox.setOnCheckedChangeListener(new
		// CheckBoxChangeListener());

		// 对增加进行监听,如果点击的话就弹出对对话框
		// 在弹出对话框的时候就调用-->我的超级梦想-增加金手套请求数据
		// 在dialog里面点击增加,每点击一次就调用提交按钮
			holder.jinshoutao_count.setText(dataList.get(position).getThinkjoin_gloves());
		String a=dataList.toString();
		holder.jinshoutao_zengjia.setId(position);
		holder.jinshoutao_zengjia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(final View v) {
				//点击加号之后弹出dialog
				final RechargeJinShouTao editNameDialog = new RechargeJinShouTao();
				editNameDialog.setData(jinshoutaoList,dataList,v.getId());
				editNameDialog.show(((Activity) context)
						.getFragmentManager(), "PayDialog");
				editNameDialog.setPay(new payType() {

					@Override
					public void setPayType(final int type) {
						// TODO Auto-generated method
						// stub
						//进行提交超级梦想,每点击一次,提交一次梦想
						int old_gloves=Integer.parseInt(dataList.get(v.getId()).getThinkjoin_gloves());

						Config.type_old_gloves= String.valueOf(type-old_gloves);


						//http://115.28.69.240/boxing/app/think_submit.php?user_id=100003&data=
//						if (Integer.parseInt(dataList.get(v.getId()).getThinkjoin_gloves())<=gloves) {
//							Toast.makeText(context, "金手套数量再不能少了", Toast.LENGTH_SHORT).show();
//							return;
//						}
//						if (Integer.parseInt(dataList.get(v.getId()).getThinkjoin_gloves())<Integer.parseInt(dataList.get(v.getId()).getThink_price())) {
//							Toast.makeText(context, "金手套数量", Toast.LENGTH_SHORT).show();
//							return;
//						}

						//提交超级梦想的按钮点击后开始请求
						//先进行金手套的增加请求,然后在提交
						// http://http://115.28.69.240/boxing/app/think_add2.php?user_id=1&think_id=2&gloves=5500&price=1000
						String think_id=dataList.get(v.getId()).getThink_id();
						String glovess= String.valueOf(type);
						String price=dataList.get(v.getId()).getThink_price();
						System.out.println("提交梦想的数据"+Config.ip + Config.app
								+ "/think_add2.php?user_id=" + Config.userID
								+ "&think_id="
								+ think_id
								+ "&gloves=" + glovess + "&price="
								+ price);
						x.http().get(
								new RequestParams(Config.ip + Config.app
										+ "/think_add2.php?user_id=" + Config.userID
										+ "&think_id="
										+ think_id
										+ "&gloves=" + glovess + "&price="
										+ price),
								new CommonCallback<String>() {

									private String thinkjoin_bili;
									private int fanhui_jinshoutao;
									private int jinshoutao_chajia;
									private int touru_jinshoutao;

									@Override
									public void onCancelled(CancelledException arg0) {
									}

									@Override
									public void onError(Throwable arg0, boolean arg1) {
									}

									@Override
									public void onFinished() {
									}

									@Override
									public void onSuccess(String arg0) {

										try {
											JSONObject json = new JSONObject(arg0);
												String think_id = json
														.getString("think_id");
												String think_percent = json
														.getString("think_percent");
												String thinkjoin_gloves = json
														.getString("thinkjoin_gloves");
												Config.thinkjoin_gloves=thinkjoin_gloves;
												String user_id = json.getString("user_id");
											thinkjoin_bili = json
                                                    .getString("thinkjoin_bili");
												dataList.get(v.getId()).setThink_percent(think_percent);
												Config.percent=think_percent;
//												refresh.refreshing();
												jinshoutaoList.add(new AddJinShouTao(
														think_id, think_percent,
														thinkjoin_gloves, user_id,
														thinkjoin_bili));
												notifyDataSetChanged();
												// 增加金手套的请求成功开始提交
											int aa=dataList.size();
											gloves = Integer.parseInt(dataList.get(v.getId()).getThinkjoin_gloves());
												//超级梦想的提交
												JSONArray array=new JSONArray();
												JSONObject object = new JSONObject();
											int dreamprice=type;
												try {
													//type是点击提交需要投入的金手套数量
													//当投入的金手套的数量大于梦想价格的时候
													//对金手套的数据格式进行判断
													String price=dataList.get(v.getId()).getThink_price();
													StringBuffer sb=new StringBuffer();
													boolean isxiaoshu=true;
													for (int i = 0; i < price.length(); i++) {
														if (price.charAt(i)=='.') {
															sb.append('0');
															sb.append(price.charAt(i));
															isxiaoshu=false;
														}else{
															if ((price.length()-1)==i) {
																if (isxiaoshu) {
																	sb.append('0');
																}
															}
															sb.append(price.charAt(i));
														}
													}
													String a=sb.toString();
													dataList.get(v.getId()).setThink_price(sb.toString());
													if (type>Integer.parseInt(sb.toString())) {
														//如果说用户要投入的金手套的数量超过了改梦想的价值,就投入该梦想的所对应的金手套
														//算出投入的金手套和梦想的价格的差多少
														//梦想的价格
														dreamprice=Integer.parseInt(sb.toString());
														//需要返回的金手套的价格
//														jinshoutao_chajia = type-dreamprice;
														//算出本次需要投入的金手套
														touru_jinshoutao = dreamprice-gloves;
														//算出需要返回账户的数量
														fanhui_jinshoutao = type-dreamprice;
														Toast.makeText(context, "本次投入"+ touru_jinshoutao +"个金手套,余下"+ fanhui_jinshoutao +"个金手套将返回到你的账户中", Toast.LENGTH_SHORT).show();
													}else if (type==Integer.parseInt(sb.toString())) {
														///这是投入的金手套和梦想的价格一致的情况
														//算出本兮投入的金手套的数量
														touru_jinshoutao = dreamprice-gloves;
														Toast.makeText(context, "本次投入"+touru_jinshoutao+"个金手套", Toast.LENGTH_SHORT).show();
														dreamprice=type;
													}else if (type<Integer.parseInt(sb.toString())) {
														///这是投入的金手套小鱼梦想的价格的情况
														//算出本兮金手套的投入数量
														gloves=Integer.parseInt(dataList.get(v.getId()).getThinkjoin_gloves());
														touru_jinshoutao = type-gloves;
														dreamprice=type;
														Toast.makeText(context, "本次投入"+touru_jinshoutao+"个金手套", Toast.LENGTH_SHORT).show();
													}
//													Toast.makeText(context, "本次投入"+(dreamprice-gloves)+"个", Toast.LENGTH_SHORT).show();
													object.put("think_id", dataList.get(v.getId()).getThink_id());
													object.put("thinkjoin_id", dataList.get(v.getId()).getThinkjoin_id());
													object.put("think_percent", dataList.get(v.getId()).getThink_percent());
													object.put("think_thisgloves", Config.type_old_gloves);
													object.put("thinkjoin_gloves", dreamprice);
													object.put("thinkjoin_bili", thinkjoin_bili);

												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												String a=dataList.get(v.getId()).getThink_percent();
												array.put(object);
												System.out.println("提交梦想的数据"+array.toString());

												x.http().get(new RequestParams(Config.ip + Config.app + "/think_submit.php?user_id=" + Config.userID + "&data=" + array), new CommonCallback<String>() {
													@Override
													public void onSuccess(String s) {
														try {
															editNameDialog.dismiss();
															JSONObject json=new JSONObject(s);
//															if (type<=Integer.parseInt(dataList.get(v.getId()).getThink_price())) {

																dataList.get(v.getId()).setThinkjoin_gloves(type+"");
//															}else{
//																dataList.get(v.getId()).setThinkjoin_gloves(Config.type_old_gloves+"");
//															}
//															notifyDataSetChanged();
															refresh.refreshing();
															//返回1
														} catch (JSONException e) {
															e.printStackTrace();
														}
													}

													@Override
													public void onError(Throwable throwable, boolean b) {

													}

													@Override
													public void onCancelled(CancelledException e) {

													}

													@Override
													public void onFinished() {

													}
												});



										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

									}
								});



					}
				});



			}

		});
		holder.jinshoutao_jianshao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "金手套减少", Toast.LENGTH_SHORT).show();
				RechargeJinShouTao editNameDialog = RechargeJinShouTao
						.getInstance();
				editNameDialog.show(((Activity) context).getFragmentManager(),
						"PayDialog");
				editNameDialog.setPay(new payType() {

					@Override
					public void setPayType(int type) {
						// TODO Auto-generated method stub
						num = type;

						System.out.println("金手套的数量" + type);
						notifyDataSetChanged();
					}
				});

			}

		});
		return convertView;
	}


	class ViewHolder {
		CheckBox super_dream_checkbox;
		ImageView super_dream_image;
		TextView super_dream_title;// 标题
		TextView super_dream_progress;
		ImageButton jinshoutao_zengjia;
		TextView jinshoutao_count;
		ImageButton jinshoutao_jianshao;
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
	private refreshdata refresh;
	public void setrefreshdata(refreshdata refresh) {
		this.refresh = refresh;
	}
	 public interface refreshdata{
		 void refreshing();
	 }



}
