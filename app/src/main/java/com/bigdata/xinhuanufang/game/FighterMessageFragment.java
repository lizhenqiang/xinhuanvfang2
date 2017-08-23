package com.bigdata.xinhuanufang.game;

import android.app.ActionBar.LayoutParams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.FighterMessageAdapter;
import com.bigdata.xinhuanufang.adapter.FighterMessageAdapter.OnFighterMessageClick;
import com.bigdata.xinhuanufang.game.bean.GameFighterBean;
import com.bigdata.xinhuanufang.game.bean.GameFighterBeanHonors;
import com.bigdata.xinhuanufang.main.MyScrollView;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 选手信息Fragment
 * */
public class FighterMessageFragment extends Fragment implements OnClickListener, MyScrollView.OnScrollListener {
	private ListViewForScrollView fighterMessageLV; // 选手信息ListView
	private FighterMessageAdapter fighterMessageAdapter;// 赛事回看adapter
	private String[] finghter = { "青格勒1", "青格勒2", "青格勒3", "青格勒4", "青格勒5",
			"青格勒6" };

	private LinearLayout WeightLL; // 体重公斤级LinearLayout
	private LinearLayout Eare; // 战无极LinearLayout
	private LinearLayout Age; // LinearLayout

	private Button btn_pClub_beijing; // 北京Button
	private Button btn_pClub_shanghai; // 上海Button
	private Button btn_pClub_hainan; // 海南Button
	private Button btn_pClub_heilongjiang; // 黑龙江Button
	private Button btn_pClub_guangzhou; // 广州Button
	private Button btn_pClub_shanxi; // 山西Button
	private Button btn_pClub_shandong; // 山东Button
	private Button btn_pClub_nanjing; // 南京Button
	private Button btn_pClub_xian; // 西安Button
	private List<Button> localList; // 存放地区的集合
	private List<Button> weightList;// 存放体重的
	private List<Button> ageList;// 存放体重的
	private Iterator<Button> it; // 集合遍历器
	// 体重
	private Button weight1; // 北京Button
	private Button weight2; // 上海Button
	private Button weight3; // 海南Button
	private Button weight4; // 黑龙江Button
	private Button weight5; // 广州Button
	private Button weight6; // 山西Button


	// 年龄
	private Button age1; // 北京Button
	private Button age2; // 上海Button
	private Button age3; // 海南Button


	private View WeightContentView; // 体重公斤级
	private View AreaContentView; // 国家
	private View AgeContentView; // 年龄

	// private List<String> weightList;
	// private List<String> areaList;
	// 存储搜索列表数据的集合
	private List<GameFighterBean> list;

	// 记录索引
	private int weight_id = 0;
	private int area_id = 0;
	private int age = 0;
	private int page = 1;
	private int num=1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					list = (List<GameFighterBean>) msg.obj;
					// 将数据加载得listview上
					System.out.println("集合的大小是" + list.size());
					showListView(list);
					break;
				case 4:
					pb.setVisibility(View.GONE);
//					getNetWorkData("0", "0", "0", "1");
					list.clear();
					getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
							String.valueOf(age), String.valueOf(page));
					Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					pb_jiazaigengduo.setVisibility(View.INVISIBLE);
					num++;
					getNetWorkData(weight_id+"", area_id+"", age+"", num+"");
					Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
			}
		};

	};
	private boolean isget=false;
	private boolean isage = true;
	private boolean isweight = true;
	private boolean isarea = true;
	private PopupWindow popupWindow;
	private TextView tizhong;
	private TextView guoji;
	private TextView nianlin;
	private ProgressBar pb;
	private MyScrollView xuanshouxinxi_refresh;
	private ProgressBar pb_jiazaigengduo;
	//是否加载更多

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_fightermesg, container,
				false);
		// 初始化组件方法
		initView(view);

		return view;
	}

	protected void showListView(final List<GameFighterBean> list) {
		// TODO Auto-generated method stub
		fighterMessageAdapter = new FighterMessageAdapter(getActivity(), list);
		fighterMessageLV.setAdapter(fighterMessageAdapter);
		fighterMessageAdapter
				.setOnFighterItemClick(new OnFighterMessageClick() {

					@Override
					public void onFighterClick(int pos) {
						// TODO Auto-generated method stub
						Intent fighterDetailsintent = new Intent(getActivity(),
								FighterDetailsActivity.class);
						//将所需要的数据传过去
						String content="";
						fighterDetailsintent.putExtra("player_id", list.get(pos).getPlayer_id());
						fighterDetailsintent.putExtra("player_head", list.get(pos).getPlayer_head());
						fighterDetailsintent.putExtra("player_name", list.get(pos).getPlayer_name());
						fighterDetailsintent.putExtra("player_sex", list.get(pos).getPlayer_sex());
						fighterDetailsintent.putExtra("player_age", list.get(pos).getPlayer_age());
						fighterDetailsintent.putExtra("player_area", list.get(pos).getPlayer_area());
						fighterDetailsintent.putExtra("player_level", list.get(pos).getPlayer_level());
						try {
							for (int i = 0; i < list.get(pos).getHonors().size(); i++) {
                                fighterDetailsintent.putExtra("honors_content", list.get(pos).getHonors().get(0).getHonors_content());
                                content=content+list.get(pos).getHonors().get(i).getHonors_content();
                            }
						} catch (Exception e) {
							e.printStackTrace();
						}
						fighterDetailsintent.putExtra("drew", list.get(pos).getDrew());
						fighterDetailsintent.putExtra("win", list.get(pos).getWin());
						fighterDetailsintent.putExtra("ko", list.get(pos).getKo());
						fighterDetailsintent.putExtra("player_height", list.get(pos).getPlayer_height());
						fighterDetailsintent.putExtra("player_weight", list.get(pos).getPlayer_weight());
						fighterDetailsintent.putExtra("player_cm", list.get(pos).getPlayer_cm());
						fighterDetailsintent.putExtra("player_special", list.get(pos).getPlayer_special());
						fighterDetailsintent.putExtra("player_group", list.get(pos).getPlayer_group());
						fighterDetailsintent.putExtra("player_special", list.get(pos).getPlayer_special());
						fighterDetailsintent.putExtra("content", content);
						fighterDetailsintent.putExtra("is_concern", list.get(pos).getIs_concern());
						startActivity(fighterDetailsintent);
					}
				});
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		localList = new ArrayList<Button>();
		weightList = new ArrayList<Button>();
		ageList = new ArrayList<Button>();
		list = new ArrayList<GameFighterBean>();
		pb_jiazaigengduo = (ProgressBar) view.findViewById(R.id.pb_jiazaigengduo);
		pb_jiazaigengduo.setVisibility(View.INVISIBLE);
		// 条目
		fighterMessageLV = (ListViewForScrollView) view
				.findViewById(R.id.lv_ffm_fighterMessage);
		// 体重
		WeightLL = (LinearLayout) view.findViewById(R.id.ll_ffm_weight);
		// 国家
		Eare = (LinearLayout) view.findViewById(R.id.ll_ffm_country);
		// 年龄
		Age = (LinearLayout) view.findViewById(R.id.ll_ffm_age);
		WeightLL.setOnClickListener(this);
		Eare.setOnClickListener(this);
		Age.setOnClickListener(this);
		pb = (ProgressBar) view.findViewById(R.id.pb_xuanshouxinxi);
		xuanshouxinxi_refresh = (MyScrollView) view.findViewById(R.id.xuanshouxinxi_refresh);
		xuanshouxinxi_refresh.setOnScrollListener(this);
		tizhong = (TextView) view.findViewById(R.id.tizhong);
		guoji = (TextView) view.findViewById(R.id.guoji);
		nianlin = (TextView) view.findViewById(R.id.nianlin);
	//加载更多

		// localList = new ArrayList<String>();
		// areaList = new ArrayList<String>();
		// ageList = new ArrayList<String>();


		// 获取列表信息
		// getDataInfo();
		getNetWorkData("0", "0", "0", "1");
	}

//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		super.onHiddenChanged(hidden);
//		getNetWorkData("1", "1", "1", "1");
//	}

	@Override
	public void onResume() {
		super.onResume();
		if (isget) {
			list.clear();
//			getNetWorkData("0", "0", "0", "1");
			page = 1;
			num=1;
			getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
					String.valueOf(age), String.valueOf(page));
		}

	}

	/**
	 * 根据不同的条件帅选点击获取网络数据
	 */
	public void getNetWorkData(String weight_id, String area_id, String age_id,
							   String page) {
		// http://115.28.69.240/boxing/app/video_list.php?weightvideo_id=2&areavideo_id=1&page=1

		System.out.println("集合清空,进行二次获取数据"+weight_id+area_id+age_id);
		if (weight_id.isEmpty()) {
			weight_id = "0";
		}
		if (area_id.isEmpty()) {
			area_id = "0";
		}
		if (page.isEmpty()) {
			page = "1";
		}
		if (age_id.isEmpty()) {
			age_id = "0";
		}
		// http://115.28.69.240/boxing/app/player_list.php?user_id=1&weight_id=1&area_id=1&age_id=1&page=1
		// http://115.28.69.240/boxing/app/player_list.php?user_id=1&weight_id=2&area_id=1&age_id=1&page=1
		System.out.println("数据地址:"+Config.ip + Config.app
				+ "/player_list.php?user_id=" + Config.userID
				+ "&weight_id=" + weight_id + "&area_id=" + area_id
				+ "&age_id=" + age_id + "&page=" + page);
		String a=Config.ip + Config.app
				+ "/player_list.php?user_id=" + Config.userID
				+ "&weight_id=" + weight_id + "&area_id=" + area_id
				+ "&age_id=" + age_id + "&page=" + page;
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/player_list.php?user_id=" + Config.userID
						+ "&weight_id=" + weight_id + "&area_id=" + area_id
						+ "&age_id=" + age_id + "&page=" + page),
				new CommonCallback<String>() {
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
							JSONArray js = json.getJSONArray("player");
							for (int i = 0; i < js.length(); i++) {
								JSONObject array = js.getJSONObject(i);
								String player_id = array.getString("player_id");
								String is_delete = array.getString("is_delete");
								String player_head = array
										.getString("player_head");
								String player_name = array
										.getString("player_name");
								String player_ageid = array
										.getString("player_ageid");
								String player_areaid = array
										.getString("player_areaid");
								String player_weightid = array
										.getString("player_weightid");
								String player_age = array
										.getString("player_age");
								String player_sex = array
										.getString("player_sex");
								String player_area = array
										.getString("player_area");
								String player_weight = array
										.getString("player_weight");
								String player_level = array
										.getString("player_level");
								String player_height = array
										.getString("player_height");
								String player_cm = array.getString("player_cm");
								String player_special = array
										.getString("player_special");
								String player_group = array
										.getString("player_group");
								String player_date = array
										.getString("player_date");
								List<GameFighterBeanHonors> honors = null;
								try {
									JSONArray json1 = array.getJSONArray("honors");
									honors = new ArrayList<GameFighterBeanHonors>();
									for (int j = 0; j < json1.length(); j++) {
                                        JSONObject json2 = json1.getJSONObject(j);
                                        String honors_id = json2
                                                .getString("honors_id");
                                        String honors_playerid = json2
                                                .getString("honors_playerid");
                                        String honors_content = json2
                                                .getString("honors_content");
                                        honors.add(new GameFighterBeanHonors(
                                                honors_id, honors_playerid,
                                                honors_content));
                                    }
								} catch (JSONException e) {
									e.printStackTrace();
								}
								String win = array.getString("win");
								String drew = array.getString("drew");
								String ko = array.getString("ko");
								String is_concern = array
										.getString("is_concern");
								System.out.println("选手是否被关注"+is_concern);
								list.add(new GameFighterBean(player_id,
										is_delete, player_head, player_name,
										player_ageid, player_areaid,
										player_weightid, player_age,
										player_sex, player_area, player_weight,
										player_level, player_height, player_cm,
										player_special, player_group,
										player_date, honors, win, drew, ko,
										is_concern));
							}
							Message msg = Message.obtain();
							System.out.println("集合时空的发生异常"+list.size());
							msg.what = 110;
							msg.obj = list;
							handler.sendMessage(msg);
							isget=true;
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							System.out.println("集合时空的发生异常");
							Message msg = Message.obtain();
							msg.what = 110;
							msg.obj = list;
							handler.sendMessage(msg);
						}
					}
				});

	}

	// private void getDataInfo() {
	//
	// // for (int i = 0; i < localList.size(); i++) {
	// // if (localList.get(i).isSelected()) {
	// // String name = (String) localList.get(i).getText();
	// // for (int j = 0; j < weightList.size(); j++) {
	// // weight = -1;
	// // if (localList.get(i).getText().equals(weightList.get(j))) {
	// //
	// // weight = j + 1;
	// // System.out.println("选中的id是" + weight);
	// // System.out.println(i);
	// // }
	// // }
	// // for (int j = 0; j < areaList.size(); j++) {
	// // area = -1;
	// // if (localList.get(i).getText().equals(areaList.get(j))) {
	// //
	// // area = j + 1;
	// // System.out.println("选中的id是" + area);
	// // }
	// // }
	// // for (int j = 0; j < ageList.size(); j++) {
	// // age = -1;
	// // if (localList.get(i).getText().equals(ageList.get(j))) {
	// //
	// // age = j + 1;
	// // System.out.println("选中的id是" + age);
	// // }
	// // }
	// // }
	// // }
	//
	// //
	// http://115.28.69.240/boxing/app/player_list.php?weight_id=1&area_id=1&age_id=1&page=1
	// x.http().get(
	// new RequestParams(Config.ip + Config.app
	// + "/player_list.php?weight_id=" + weight + "&area_id="
	// + area + "&age_id=" + age + "&page=" + page),
	// new CommonCallback<String>() {
	//
	// @Override
	// public void onCancelled(CancelledException arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onError(Throwable arg0, boolean arg1) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onFinished() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onSuccess(String arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	// });
	// }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.ll_ffm_weight:
				showPopupWindow(v, "weight");

				break;
			case R.id.ll_ffm_country:
				showPopupWindow(v, "country");
				break;
			case R.id.ll_ffm_age:
				showPopupWindow(v, "age");
				break;
			// 区域
			case R.id.btn_pClub_beijing:

//				if (isarea) {
					area_id = 1;
					guoji.setText("中国");
				isCheck(btn_pClub_beijing);
					isarea=false;
//				}else{
//					area_id = 0;
//					guoji.setText("国际(或地区)");
//					btn_pClub_beijing.setTextColor(0xff333333);
//					btn_pClub_beijing.setSelected(false);
					isarea=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_shanghai:

//				if (isarea) {
					area_id = 2;
					guoji.setText("欧洲");
				isCheck(btn_pClub_shanghai);
					isarea=false;
//				}else{
//					area_id = 0;
//					guoji.setText("国际(或地区)");
//					btn_pClub_shanghai.setTextColor(0xff333333);
//					btn_pClub_shanghai.setSelected(false);
					isarea=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_hainan:

//				if (isarea) {
					area_id = 3;
					guoji.setText("亚洲");
				isCheck(btn_pClub_hainan);
					isarea=false;
//				}else{
//					area_id = 0;
//					guoji.setText("国际(或地区)");
//					btn_pClub_hainan.setTextColor(0xff333333);
//					btn_pClub_hainan.setSelected(false);
					isarea=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_heilongjiang:

//				if (isarea) {
					area_id = 4;
					guoji.setText("美洲");
				isCheck(btn_pClub_heilongjiang);
					isarea=false;
//				}else{
//					area_id = 0;
//					guoji.setText("国际(或地区)");
//					btn_pClub_heilongjiang.setTextColor(0xff333333);
//					btn_pClub_heilongjiang.setSelected(false);
					isarea=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_guangzhou:

//				if (isarea) {
					area_id = 5;
					guoji.setText("非洲");
				isCheck(btn_pClub_guangzhou);
					isarea=false;
//				}else{
//					area_id = 0;
//					guoji.setText("国际(或地区)");
//					btn_pClub_guangzhou.setTextColor(0xff333333);
//					btn_pClub_guangzhou.setSelected(false);
					isarea=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_shanxi:

//				if (isarea) {
					area_id = 6;
					guoji.setText("其他");
				isCheck(btn_pClub_shanxi);
					isarea=false;
//				}else{
//					area_id = 0;
//					guoji.setText("国际(或地区)");
//					btn_pClub_shanxi.setTextColor(0xff333333);
//					btn_pClub_shanxi.setSelected(false);
					isarea=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_shandong:
				isCheck(btn_pClub_shandong);
				if (isarea) {
					area_id = 7;
					isarea=false;
				}else{
					area_id = 0;
					btn_pClub_shandong.setTextColor(0xff333333);
					btn_pClub_shandong.setSelected(false);
					isarea=true;
				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_nanjing:
				isCheck(btn_pClub_nanjing);
				if (isarea) {
					area_id = 8;
					isarea=false;
				}else{
					area_id = 0;
					btn_pClub_nanjing.setTextColor(0xff333333);
					btn_pClub_nanjing.setSelected(false);
					isarea=true;
				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.btn_pClub_xian:
				isCheck(btn_pClub_xian);
				if (isarea) {
					area_id = 9;
					isarea=false;
				}else{
					area_id = 0;
					btn_pClub_xian.setTextColor(0xff333333);
					btn_pClub_xian.setSelected(false);
					isarea=true;
				}
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			// 体重
			case R.id.weight1:

//				if (isweight) {
					weight_id = 1;
					tizhong.setText("男:~60KG");
				isCheckWeight(weight1);
					isweight=false;
//				}else{
//					weight_id = 0;
//					tizhong.setText("体重");
//					weight1.setTextColor(0xff333333);
//					weight1.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.weight2:

//				if (isweight) {
					weight_id = 2;
					tizhong.setText("男:61~90KG");
				isCheckWeight(weight2);
					isweight=false;
//				}else{
//					weight_id = 0;
//					tizhong.setText("体重");
//					weight2.setTextColor(0xff333333);
//					weight2.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.weight3:

//				if (isweight) {
					weight_id = 3;
					tizhong.setText("男:91KG~");
				isCheckWeight(weight3);
					isweight=false;
//				}else{
//					weight_id = 0;
//					tizhong.setText("体重");
//					weight3.setTextColor(0xff333333);
//					weight3.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.weight4:

//				if (isweight) {
					weight_id = 4;
					tizhong.setText("女:~48KG");
				isCheckWeight(weight4);
					isweight=false;
//				}else{
//					weight_id = 0;
//					tizhong.setText("体重");
//					weight4.setTextColor(0xff333333);
//					weight4.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.weight5:

//				if (isweight) {
					weight_id = 5;
					tizhong.setText("女:49~60KG");
				isCheckWeight(weight5);
					isweight=false;
//				}else{
//					weight_id = 0;
//					tizhong.setText("体重");
//					weight5.setTextColor(0xff333333);
//					weight5.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.weight6:

//				if (isweight) {
					weight_id = 6;
					tizhong.setText("女:61KG~");
				isCheckWeight(weight6);
					isweight=false;
//				}else{
//					weight_id = 0;
//					tizhong.setText("体重");
//					weight6.setTextColor(0xff333333);
//					weight6.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;

			// 年龄
			case R.id.age1:

//				if (isage) {
					age = 1;
					nianlin.setText("~19岁");
					isage=false;
				isCheckAge(age1);
//				}else{
//					age = 0;
//					nianlin.setText("年龄范围");
//					age1.setTextColor(0xff333333);
//					age1.setSelected(false);
					isage=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.age2:

//				if (isage) {
					age = 2;
					nianlin.setText("20~29岁");
				isCheckAge(age2);
					isage=false;
//				}else{
//					age = 0;
//					nianlin.setText("年龄范围");
//					age1.setTextColor(0xff333333);
//					age1.setSelected(false);
					isage=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;
			case R.id.age3:
				nianlin.setText("30岁~");
				age = 3;
				isCheckAge(age3);
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weight_id), String.valueOf(area_id),
						String.valueOf(age), String.valueOf(page));
				break;

			default:
				break;
		}
	}

	/*
	 * 显示popuWindow的方法
	 */
	private void showPopupWindow(View v, String str) {
		// TODO Auto-generated method stub
		popupWindow = null;
		if (str == "weight") {
			// if (popupWindow != null) {
			// popupWindow.dismiss();
			// }
			// 体重
			if (WeightContentView == null) {
				WeightContentView = LayoutInflater.from(getActivity()).inflate(
						R.layout.popu_game_details_weight, null);
				initWeightData(WeightContentView);

			}else {
				initWeightData(WeightContentView);
			}
			popupWindow = new PopupWindow(WeightContentView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		} else if (str == "country") {
			// if (popupWindow != null) {
			// popupWindow.dismiss();
			// }
			// 区域
			if (AreaContentView == null) {
				AreaContentView = LayoutInflater.from(getActivity()).inflate(
						R.layout.popu_club, null);
				initAreaData(AreaContentView);

			}else {
				initAreaData(AreaContentView);
			}
			popupWindow = new PopupWindow(AreaContentView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		} else if (str == "age") {
			// if (popupWindow != null) {
			// popupWindow.dismiss();
			// }
			// 年龄
			if (AgeContentView == null) {
				AgeContentView = LayoutInflater.from(getActivity()).inflate(
						R.layout.popu_game_details_age, null);
				initAgeData(AgeContentView);

			}else {
				initAgeData(AgeContentView);
			}
			popupWindow = new PopupWindow(AgeContentView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		}

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				return false;
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});

		// 如果不设置PopupWindow的背景
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.xingbiedi));

		// 设置好参数之后再show
		popupWindow.showAsDropDown(v);

	}







	/*
	 * popuWindow中Button是否被选中
	 */
	public void isCheck(Button localBtn) {
		it =localList .iterator();
		while (it.hasNext()) {

			Button btn = it.next();
			if (btn.getId() == localBtn.getId()) {

				if(!localBtn.isSelected()) {

					localBtn.setTextColor(0xffffffff);
					localBtn.setSelected(true);

				}else {

					guoji.setText("国籍（或地区）");
					area_id=0;
					btn.setSelected(false);
					btn.setTextColor(0xff333333);

				}

			} else {
				Log.e("TAG", "555555555555555555555555");
				btn.setSelected(false);
				btn.setTextColor(0xff333333);
			}
		}
	}

	/**
	 * 体重
	 *
	 * @param localBtn
	 */
	public void isCheckWeight(Button localBtn) {


			it = weightList.iterator();
			while (it.hasNext()) {

				Button btn = it.next();
				if (btn.getId() == localBtn.getId()) {

					if(!localBtn.isSelected()) {

						localBtn.setTextColor(0xffffffff);
						localBtn.setSelected(true);

					}else {

						tizhong.setText("体重公斤级");
						weight_id=0;
						btn.setSelected(false);
						btn.setTextColor(0xff333333);

					}

				} else {
					Log.e("TAG", "555555555555555555555555");
					btn.setSelected(false);
					btn.setTextColor(0xff333333);
				}
			}

	}

	/**
	 * 体重
	 *
	 * @param localBtn
	 */
	public void isCheckAge(Button localBtn) {
		it = ageList.iterator();
		while (it.hasNext()) {

			Button btn = it.next();
			if (btn.getId() == localBtn.getId()) {

				if(!localBtn.isSelected()) {

					localBtn.setTextColor(0xffffffff);
					localBtn.setSelected(true);

				}else {

					nianlin.setText("年龄范围");
					age=0;
					btn.setSelected(false);
					btn.setTextColor(0xff333333);

				}

			} else {
				Log.e("TAG", "555555555555555555555555");
				btn.setSelected(false);
				btn.setTextColor(0xff333333);
			}
		}
	}

	/**
	 * 清空button文字
	 *
	 * @param
	 */
	public void buttonClear() {
		it = localList.iterator();
		while (it.hasNext()) {
			Button btn = it.next();
			btn.setText(null);
		}
	}

	/*
	 * 初始化popuWindow中数据
	 */
	public void initWeightData(View WeightContentView) {
		weight1 = (Button) WeightContentView.findViewById(R.id.weight1);
		weight2 = (Button) WeightContentView.findViewById(R.id.weight2);
		weight3 = (Button) WeightContentView.findViewById(R.id.weight3);
		weight4 = (Button) WeightContentView.findViewById(R.id.weight4);
		weight5 = (Button) WeightContentView.findViewById(R.id.weight5);
		weight6 = (Button) WeightContentView.findViewById(R.id.weight6);


//		weight2.setTextColor(0xffffffff);
//		weight2.setSelected(true);
		weightList.clear();
		weightList.add(weight1);
		weightList.add(weight2);
		weightList.add(weight3);
		weightList.add(weight4);
		weightList.add(weight5);
		weightList.add(weight6);

		weight1.setOnClickListener(this);
		weight2.setOnClickListener(this);
		weight3.setOnClickListener(this);
		weight4.setOnClickListener(this);
		weight5.setOnClickListener(this);
		weight6.setOnClickListener(this);


	}

	private void initAreaData(View AreaContentView) {
		btn_pClub_beijing = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_beijing);
		btn_pClub_shanghai = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_shanghai);
		btn_pClub_hainan = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_hainan);
		btn_pClub_heilongjiang = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_heilongjiang);
		btn_pClub_guangzhou = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_guangzhou);
		btn_pClub_shanxi = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_shanxi);
		btn_pClub_shandong = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_shandong);
		btn_pClub_nanjing = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_nanjing);
		btn_pClub_xian = (Button) AreaContentView
				.findViewById(R.id.btn_pClub_xian);

		btn_pClub_beijing.setText("中国");
		btn_pClub_shanghai.setText("欧洲");
		btn_pClub_hainan.setText("亚洲");
		btn_pClub_heilongjiang.setText("美洲");
		btn_pClub_guangzhou.setText("非洲");
		btn_pClub_shanxi.setText("其他");
		btn_pClub_shandong.setVisibility(View.GONE);
		btn_pClub_nanjing.setVisibility(View.GONE);
		btn_pClub_xian.setVisibility(View.GONE);
//		btn_pClub_beijing.setTextColor(0xffffffff);
//		btn_pClub_beijing.setSelected(true);
		localList.clear();
		localList.add(btn_pClub_beijing);
		localList.add(btn_pClub_shanghai);
		localList.add(btn_pClub_hainan);
		localList.add(btn_pClub_heilongjiang);
		localList.add(btn_pClub_guangzhou);
		localList.add(btn_pClub_shanxi);
		/*localList.add(btn_pClub_shandong);
		localList.add(btn_pClub_nanjing);
		localList.add(btn_pClub_xian);*/
		btn_pClub_beijing.setOnClickListener(this);
		btn_pClub_shanghai.setOnClickListener(this);
		btn_pClub_hainan.setOnClickListener(this);
		btn_pClub_heilongjiang.setOnClickListener(this);
		btn_pClub_guangzhou.setOnClickListener(this);
		btn_pClub_shanxi.setOnClickListener(this);
		btn_pClub_shandong.setOnClickListener(this);
		btn_pClub_nanjing.setOnClickListener(this);
		btn_pClub_xian.setOnClickListener(this);

	}

	private void initAgeData(View AgeContentView) {
		age1 = (Button) AgeContentView.findViewById(R.id.age1);
		age2 = (Button) AgeContentView.findViewById(R.id.age2);
		age3 = (Button) AgeContentView.findViewById(R.id.age3);

//		age1.setTextColor(0xffffffff);
//		age1.setSelected(true);
		ageList.clear();
		ageList.add(age1);
		ageList.add(age2);
		ageList.add(age3);

		age1.setOnClickListener(this);
		age2.setOnClickListener(this);
		age3.setOnClickListener(this);


	}


	@Override
	public void onScroll(int scrollY) {
		//System.out.println("scrollY" + scrollY) ;
		System.out.println("显示的条目"+fighterMessageLV.getLastVisiblePosition());
		if (scrollY == 11111111) {
//            if (ll_tab.getVisibility()== View.GONE) {
//                return;
//            }
			if (xuanshouxinxi_refresh.getScrollY()!= 0) {
				return;
			}
			pb.setVisibility(View.VISIBLE);
//			Animation animation = new ScaleAnimation(1f, 1f, 0, 1f);
//			animation.setDuration(300);
//			pb.startAnimation(animation);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						Message msg = new Message();
						msg.what = 4;
						handler.sendMessage(msg);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
		if (scrollY == 22222222){


			pb_jiazaigengduo.setVisibility(View.VISIBLE);
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(2000);
						Message msg = new Message();
						msg.what = 5;
						handler.sendMessage(msg);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();

		}
	}
}
