package com.bigdata.xinhuanufang.game;

import android.app.ActionBar.LayoutParams;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GameReviewAdapter;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
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

import static com.bigdata.xinhuanufang.R.id.lv_fgr_gameReview;

/*
 * 赛事回看Fragment
 * */
public class GameReviewFragment extends Fragment implements OnClickListener, MyScrollView.OnScrollListener {
	private ListViewForScrollView gameReviewLV; // 赛事回看ListView
	private GameReviewAdapter gameReviewAdapter;// 赛事回看adapter
	private String[] finghterOne = { "青格勒1", "青格勒2", "青格勒3", "青格勒4", "青格勒5",
			"青格勒6" };
	private LinearLayout weightLL; // 体重公斤级LinearLayout
	private LinearLayout zhanWuJiLL; // 战无极LinearLayout
	private LinearLayout clubLL; // LinearLayout

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
	private Iterator<Button> it; // 集合遍历器


	private View WeightContentView; // 体重公斤级
	private View clubContentView; // 俱乐部
	private View zhanwujiContentView; // 战无极

	private Button game_nan_zhongliangji;// 体重公斤级
	private Button game_nan_qingliangji;// 体重公斤级
	private Button game_nan_ciqingliangji;// 体重公斤级
	private Button game_nv_zhongliangji;// 体重公斤级
	private Button game_nv_qingliangji;// 体重公斤级
	private Button game_nv_ciqingliangji;// 体重公斤级

	private Button game_zhanwuji_quanwang;// 战无极
	private Button game_zhanwuji_quanjineng1;// 战无极
	private Button game_zhanwuji_quanjineng2;// 战无极
	private Button game_saifadou_quanwang;// 战无极
	private Button game_saifadou_quanjineng1;// 战无极
	private Button game_saifadou_quanjineng2;// 战无极
	private int weightvideo_id = 0;
	private int areavideo_id = 0;
	private int zhanwuji = 0;
	private int page = 1;
	private String video_liveguessid = "0";
	private int num=1;
	// 存储搜索列表数据的集合
	private List<GameDetailsInfo> list;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					list = (List<GameDetailsInfo>) msg.obj;
					// 将数据加载得listview上
					showListView(list);
					break;
				case 4:
					pb.setVisibility(View.GONE);
					getNetWorkDatas("0", "0", "0", "1");
					Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					pb_saishikuikan_jiazaigengduo.setVisibility(View.INVISIBLE);
					num++;
					getNetWorkData(weightvideo_id+"", areavideo_id+"", zhanwuji+"", num+"");
					Toast.makeText(getActivity(), "加载完成", Toast.LENGTH_SHORT).show();
				default:
					break;
			}
		};

	};
	private Button game_gongfushijiebei;
	private boolean isweight = true;
	private boolean iszhanwuji=true;
	private PopupWindow popupWindow;
	private TextView tizhong_gongjinji;
	private TextView saiqu_club;
	private TextView zhanwuji_saifadou;
	private MyScrollView saishihuikan_refresh;
	private ProgressBar pb;
	private ProgressBar pb_saishikuikan_jiazaigengduo;
	private boolean isdhuaxin=false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_gamereview, null);
	}

	protected void showListView(final List<GameDetailsInfo> list) {
		// TODO Auto-generated method stub
		// 赛事回看界面的搜索
		gameReviewAdapter = new GameReviewAdapter(getActivity(), list);
		gameReviewAdapter.notifyDataSetChanged();
		gameReviewLV.setAdapter(gameReviewAdapter);
		if (list.size() != 0) {
			video_liveguessid = list.get(0).getVideo_liveguessid();
		}
		gameReviewLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("context", null);
				bundle.putString("weightvideo_id", list.get(position).getVideo_id());
				String s=list.get(position).getVideo_liveguessid();
				bundle.putString("video_liveguessid", list.get(position).getVideo_liveguessid());
				bundle.putInt("pos", position);
				bundle.putInt("tag", 1);
				bundle.putInt("page", num);
				bundle.putString("Player_namea",list.get(position).getPlayer_namea());
				bundle.putString("Player_nameb",list.get(position).getPlayer_nameb());
				bundle.putString("Player_heada",list.get(position).getPlayer_heada());
				bundle.putString("Player_headb",list.get(position).getPlayer_headb());
				bundle.putString("Player_success",list.get(position).getPlayer_success());
				bundle.putString("Video_time",list.get(position).getVideo_time());
				bundle.putString("Video_content",list.get(position).getVideo_content());
				bundle.putString("Video_pic",list.get(position).getVideo_pic());
				bundle.putString("Video_id",list.get(position).getVideo_id());
				bundle.putString("Is_coll",list.get(position).getIs_coll());
				bundle.putString("video_liveguessid",list.get(position).getVideo_liveguessid());
				Intent intent=new Intent(getActivity(),ReviewDetailsActivity.class);
				intent.putExtras(bundle);
//				startActivity(intent);
				startActivityForResult(intent,282);
			}
		});

//		gameReviewAdapter.setOnGameReviewItemClick(new onGameReviewItemClick() {
//
//			@Override
//			public void onGameReviewClick(int pos) {
//				// 赛事回看的条目点击监听
//				Intent ReviewDetailsintent = new Intent(getActivity(),
//						ReviewDetailsActivity.class);
//				// 想下一个页面传递点击条目所对应的
//				ReviewDetailsintent.putExtra("weightvideo_id", weightvideo_id);
//				ReviewDetailsintent.putExtra("areavideo_id", areavideo_id);
//				ReviewDetailsintent.putExtra("zhanwuji", zhanwuji);
//				ReviewDetailsintent.putExtra("pos", pos);
//				ReviewDetailsintent.putExtra("page", page);
//				ReviewDetailsintent.putExtra("video_liveguessid",
//						video_liveguessid);
//				ReviewDetailsintent.putExtra("tag", 1);
//				startActivity(ReviewDetailsintent);
//			}
//		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==282) {
			getNetWorkDatas("0", "0", "0", "1");
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		localList = new ArrayList<Button>();

		list = new ArrayList<GameDetailsInfo>();
		gameReviewLV = (ListViewForScrollView) getView()
				.findViewById(lv_fgr_gameReview);
		weightLL = (LinearLayout) getView().findViewById(R.id.ll_fgr_weight);
		weightLL.setOnClickListener(this);
		zhanWuJiLL = (LinearLayout) getView()
				.findViewById(R.id.ll_fgr_zhanWuJi);
		zhanWuJiLL.setOnClickListener(this);
		clubLL = (LinearLayout) getView().findViewById(R.id.ll_fgr_club);
		clubLL.setOnClickListener(this);
		tizhong_gongjinji = (TextView) getView().findViewById(R.id.tizhong_gongjinji);
		saiqu_club = (TextView) getView().findViewById(R.id.saiqu_club);
		zhanwuji_saifadou = (TextView) getView().findViewById(R.id.zhanwuji_saifadou);
		saishihuikan_refresh = (MyScrollView) getView().findViewById(R.id.saishihuikan_refresh);
		saishihuikan_refresh.setOnScrollListener(this);
		pb = (ProgressBar) getView().findViewById(R.id.pb_saishihuikan);
		pb_saishikuikan_jiazaigengduo = (ProgressBar) getView().findViewById(R.id.pb_saishikuikan_jiazaigengduo);
		pb_saishikuikan_jiazaigengduo.setVisibility(View.INVISIBLE);
		getNetWorkData("0", "0", "0", "1");
	}

	/**
	 * 根据不同的点击获取网络数据
	 */
	public void getNetWorkData(String weightvideo_id, String areavideo_id,
							   String zhanwuji, String page) {
		// http://115.28.69.240/boxing/app/video_list.php?weightvideo_id=2&areavideo_id=1&page=1&user_id=1
		// http://115.28.69.240/boxing/app/video_list.php?weightvideo_id=2&areavideo_id=1&page=1

		System.out.println("集合清空,进行二次获取数据");
		if (weightvideo_id.isEmpty()) {
			weightvideo_id = "0";
		}
		if (zhanwuji.isEmpty()) {
			zhanwuji = "0";
		}
		if (page.isEmpty()) {
			page = "1";
		}
		// if (weightvideo_id.isEmpty()) {
		// weightvideo_id="2";
		// }
		list.clear();
		Log.e("TAG", "赛事回看"+Config.ip + Config.app + "/video_list."
				+ "php?weightvideo_id=" + weightvideo_id
				+ "&areavideo_id="+ areavideo_id+"&savelife_id" + zhanwuji + "&page=" + page+ "&user_id=" + Config.userID);
		x.http().get(
				new RequestParams(Config.ip + Config.app + "/video_list."
						+ "php?weightvideo_id=" + weightvideo_id
						+ "&areavideo_id=" + areavideo_id+"&savelife_id=" +zhanwuji+ "&page=" + page+ "&user_id=" + Config.userID),
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
							System.out.println("拿到的数据"+arg0);
							JSONObject json = new JSONObject(arg0);
							JSONArray js = json.getJSONArray("video");
							for (int i = 0; i < js.length(); i++) {
								JSONObject array = js.getJSONObject(i);
								String video_id = array.getString("video_id");
								String video_playeruserid = array
										.getString("video_playeruserid");
								String video_playeruserid2 = array
										.getString("video_playeruserid2");
								String video_time = array
										.getString("video_time");
								String video_title = array
										.getString("video_title");
								String video_success = array
										.getString("video_success");
								String video_pic = array.getString("video_pic");
								String video_content = array
										.getString("video_content");
								String video_liveguessid = array
										.getString("video_liveguessid");
								String player_namea = array
										.getString("player_namea");
								String player_heada = array
										.getString("player_heada");
								String player_nameb = array
										.getString("player_nameb");
								String player_headb = array
										.getString("player_headb");
								String player_success = array
										.getString("player_success");
								String is_coll = array.getString("is_coll");
								list.add(new GameDetailsInfo(video_id,
										video_playeruserid,
										video_playeruserid2, video_time,
										video_title, video_success, video_pic,
										video_content, video_liveguessid,
										player_namea, player_heada,
										player_nameb, player_headb,
										player_success, is_coll));

							}
							Message msg = Message.obtain();
							msg.what = 110;
							msg.obj = list;
							handler.sendMessage(msg);
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

	public void getNetWorkDatas(String weightvideo_id, String areavideo_id,
							   String zhanwuji, String page) {
		// http://115.28.69.240/boxing/app/video_list.php?weightvideo_id=2&areavideo_id=1&page=1&user_id=1
		// http://115.28.69.240/boxing/app/video_list.php?weightvideo_id=2&areavideo_id=1&page=1
		list.clear();
		System.out.println("集合清空,进行二次获取数据");
		if (weightvideo_id.isEmpty()) {
			weightvideo_id = "0";
		}
		if (zhanwuji.isEmpty()) {
			zhanwuji = "0";
		}
		if (page.isEmpty()) {
			page = "1";
		}
		// if (weightvideo_id.isEmpty()) {
		// weightvideo_id="2";
		// }

		x.http().get(
				new RequestParams(Config.ip + Config.app + "/video_list."
						+ "php?weightvideo_id=" + weightvideo_id
						+ "&areavideo_id=" + zhanwuji + "&page=" + page+ "&user_id=" + Config.userID),
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
							System.out.println("拿到的数据"+arg0);
							JSONObject json = new JSONObject(arg0);
							JSONArray js = json.getJSONArray("video");
							for (int i = 0; i < js.length(); i++) {
								JSONObject array = js.getJSONObject(i);
								String video_id = array.getString("video_id");
								String video_playeruserid = array
										.getString("video_playeruserid");
								String video_playeruserid2 = array
										.getString("video_playeruserid2");
								String video_time = array
										.getString("video_time");
								String video_title = array
										.getString("video_title");
								String video_success = array
										.getString("video_success");
								String video_pic = array.getString("video_pic");
								String video_content = array
										.getString("video_content");
								String video_liveguessid = array
										.getString("video_liveguessid");
								String player_namea = array
										.getString("player_namea");
								String player_heada = array
										.getString("player_heada");
								String player_nameb = array
										.getString("player_nameb");
								String player_headb = array
										.getString("player_headb");
								String player_success = array
										.getString("player_success");
								String is_coll = array.getString("is_coll");
								list.add(new GameDetailsInfo(video_id,
										video_playeruserid,
										video_playeruserid2, video_time,
										video_title, video_success, video_pic,
										video_content, video_liveguessid,
										player_namea, player_heada,
										player_nameb, player_headb,
										player_success, is_coll));

							}
							Message msg = Message.obtain();
							msg.what = 110;
							msg.obj = list;
							handler.sendMessage(msg);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.ll_fgr_weight:
				showPopupWindow(v, "weight");
				break;
			case R.id.ll_fgr_zhanWuJi:
				showPopupWindow(v, "zhanwuji");
				break;
			case R.id.ll_fgr_club:
				showPopupWindow(v, "club");
				break;
			case R.id.btn_pClub_beijing:
				// 赛区
				areavideo_id = 1;
				saiqu_club.setText("北京赛区");
				isCheckArea(btn_pClub_beijing);

				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_shanghai:

				areavideo_id = 3;
				saiqu_club.setText("西安赛区");
				isCheckArea(btn_pClub_shanghai);
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_hainan:

				areavideo_id = 4;
				saiqu_club.setText("济南赛区");
				isCheckArea(btn_pClub_hainan);
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_heilongjiang:

				areavideo_id = 5;
				saiqu_club.setText("石家庄赛区");
				isCheckArea(btn_pClub_heilongjiang);
				popupWindow.dismiss();
				// getNetWorkData(String.valueOf(weightvideo_id),
				// String.valueOf(areavideo_id), String.valueOf(zhanwuji),
				// String.valueOf(page));
//				getNetWorkData("2", "2", "1", "1");
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_guangzhou:
				saiqu_club.setText("广州赛区");
				areavideo_id = 6;

				isCheckArea(btn_pClub_guangzhou);
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_shanxi:

				areavideo_id = 7;
				saiqu_club.setText("成都赛区");
				isCheckArea(btn_pClub_shanxi);
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_shandong:

				areavideo_id = 7;
				saiqu_club.setText("山东赛区");
				popupWindow.dismiss();
				isCheckArea(btn_pClub_shandong);
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_nanjing:

				areavideo_id = 8;
				saiqu_club.setText("南京赛区");
				isCheckArea(btn_pClub_nanjing);
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.btn_pClub_xian:

				areavideo_id = 3;
				saiqu_club.setText("西安赛区");
				isCheckArea(btn_pClub_xian);
				popupWindow.dismiss();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_nan_zhongliangji:

//				if (isweight) {
					weightvideo_id = 1;
					tizhong_gongjinji.setText("男:~60KG");

				isCheckWeight(game_nan_zhongliangji);
					isweight=false;
//				}else{
//					weightvideo_id = 0;
//					tizhong_gongjinji.setText("体重公斤级");
//					game_nan_zhongliangji.setBackgroundColor(Color.WHITE);
//					game_nan_zhongliangji.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_nan_qingliangji:

//				if (isweight) {
					weightvideo_id = 2;
					tizhong_gongjinji.setText("男:61~90KG");
				isCheckWeight(game_nan_qingliangji);
					isweight=false;
//				}else{
//					weightvideo_id = 0;
//					tizhong_gongjinji.setText("体重公斤级");
//					game_nan_qingliangji.setBackgroundColor(Color.WHITE);
//					game_nan_qingliangji.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_nan_ciqingliangji:

//				if (isweight) {
					weightvideo_id = 3;
					tizhong_gongjinji.setText("男:91KG~");
				isCheckWeight(game_nan_ciqingliangji);
					isweight=false;
//				}else{
//					weightvideo_id = 0;
//					tizhong_gongjinji.setText("体重公斤级");
//					game_nan_ciqingliangji.setBackgroundColor(Color.WHITE);
//					game_nan_ciqingliangji.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_nv_zhongliangji:

//				if (isweight) {
					weightvideo_id = 4;
					tizhong_gongjinji.setText("女:~48KG");
				isCheckWeight(game_nv_zhongliangji);
					isweight=false;
//				}else{
//					weightvideo_id = 0;
//					tizhong_gongjinji.setText("体重公斤级");
//					game_nv_zhongliangji.setBackgroundColor(Color.WHITE);
//					game_nv_zhongliangji.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_nv_qingliangji:

//				if (isweight) {
					weightvideo_id = 5;
					tizhong_gongjinji.setText("女:49~60KG");
				isCheckWeight(game_nv_qingliangji);
					isweight=false;

//				}else{
//					weightvideo_id = 0;
//					tizhong_gongjinji.setText("体重公斤级");
//					game_nv_qingliangji.setBackgroundColor(Color.WHITE);
//					game_nv_qingliangji.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_nv_ciqingliangji:

//				if (isweight) {
					weightvideo_id = 6;
					tizhong_gongjinji.setText("女:61KG~");
				    isCheckWeight(game_nv_ciqingliangji);
					isweight=false;
//				}else{
//					weightvideo_id = 0;
//					tizhong_gongjinji.setText("体重公斤级");
//					game_nv_ciqingliangji.setBackgroundColor(Color.WHITE);
//					game_nv_ciqingliangji.setSelected(false);
					isweight=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_zhanwuji_quanwang:

//				if (iszhanwuji) {
					zhanwuji = 1;
					zhanwuji_saifadou.setText("我就是拳王");
				isCheckZhanwuji(game_zhanwuji_quanwang);
					iszhanwuji=false;
//				}else{
//					zhanwuji = 0;
//					zhanwuji_saifadou.setText("战无极/赛法斗");
//					game_zhanwuji_quanwang.setBackgroundColor(Color.WHITE);
//					game_zhanwuji_quanwang.setSelected(false);
					iszhanwuji=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_zhanwuji_quanjineng1:

//				if (iszhanwuji) {
					zhanwuji = 2;
					zhanwuji_saifadou.setText("院校大奖赛");
				isCheckZhanwuji(game_zhanwuji_quanjineng1);
					iszhanwuji=false;
//				}else{
//					zhanwuji = 0;
//					zhanwuji_saifadou.setText("战无极/赛法斗");
//					game_zhanwuji_quanjineng1.setBackgroundColor(Color.WHITE);
//					game_zhanwuji_quanjineng1.setSelected(false);
					iszhanwuji=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;

			case R.id.game_zhanwuji_quanjineng2:

//				if (iszhanwuji) {
					zhanwuji = 3;
					zhanwuji_saifadou.setText("拳王统一战");
				isCheckZhanwuji(game_zhanwuji_quanjineng2);
					iszhanwuji=false;
//				}else{
//					zhanwuji = 0;
//					zhanwuji_saifadou.setText("战无极/赛法斗");
//					game_zhanwuji_quanjineng2.setBackgroundColor(Color.WHITE);
//					game_zhanwuji_quanjineng2.setSelected(false);
					iszhanwuji=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_saifadou_quanwang:

//				if (iszhanwuji) {
					zhanwuji = 4;
					zhanwuji_saifadou.setText("俱乐部联赛");
				isCheckZhanwuji(game_saifadou_quanwang);
					iszhanwuji=false;
//				}else{
//					zhanwuji = 0;
//					zhanwuji_saifadou.setText("战无极/赛法斗");
//					game_saifadou_quanwang.setBackgroundColor(Color.WHITE);
//					game_saifadou_quanwang.setSelected(false);
					iszhanwuji=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_saifadou_quanjineng1:

//				if (iszhanwuji) {
					zhanwuji = 5;
					zhanwuji_saifadou.setText("功夫世界杯");
				isCheckZhanwuji(game_saifadou_quanjineng1);
					iszhanwuji=false;
//				}else{
//					zhanwuji = 0;
//					zhanwuji_saifadou.setText("战无极/赛法斗");
//					game_saifadou_quanjineng1.setBackgroundColor(Color.WHITE);
//					game_saifadou_quanjineng1.setSelected(false);
					iszhanwuji=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
				break;
			case R.id.game_saifadou_quanjineng2:

//				if (iszhanwuji) {
					zhanwuji = 6;
					zhanwuji_saifadou.setText("国际赛法斗");
				isCheckZhanwuji(game_saifadou_quanjineng2);
					iszhanwuji=false;
//				}else{
//					zhanwuji = 0;
//					zhanwuji_saifadou.setText("战无极/赛法斗");
//					game_saifadou_quanjineng2.setBackgroundColor(Color.WHITE);
//					game_saifadou_quanjineng2.setSelected(false);
					iszhanwuji=true;
//				}
				popupWindow.dismiss();
				list.clear();
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(page));
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
			// 体重公斤级的布局
			if (WeightContentView == null) {
				WeightContentView = LayoutInflater.from(getActivity()).inflate(
						R.layout.popu_gameweight, null);
				initWeightData(WeightContentView);
			}else {
				initWeightData(WeightContentView);
			}
			popupWindow = new PopupWindow(WeightContentView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		} else if (str == "zhanwuji") {
			// 这里的布局进行了复用,发生状态不会保留,
			if (zhanwujiContentView == null) {
				zhanwujiContentView = LayoutInflater.from(getActivity())
						.inflate(R.layout.popu_zhanwuji, null);
				initZhanWuJiData(zhanwujiContentView);
			}else {
				initZhanWuJiData(zhanwujiContentView);
			}
			popupWindow = new PopupWindow(zhanwujiContentView,
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		} else if (str == "club") {
			if (clubContentView == null) {
				clubContentView = LayoutInflater.from(getActivity()).inflate(
						R.layout.popu_club, null);

				initData(clubContentView);
			}else {
				initData(clubContentView);
			}
			popupWindow = new PopupWindow(clubContentView,
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

	private void initZhanWuJiData(View zhanwujiContentView) {
		game_zhanwuji_quanwang = (Button) zhanwujiContentView
				.findViewById(R.id.game_zhanwuji_quanwang);
		game_zhanwuji_quanjineng1 = (Button) zhanwujiContentView
				.findViewById(R.id.game_zhanwuji_quanjineng1);
		game_zhanwuji_quanjineng2 = (Button) zhanwujiContentView
				.findViewById(R.id.game_zhanwuji_quanjineng2);
		game_saifadou_quanwang = (Button) zhanwujiContentView
				.findViewById(R.id.game_saifadou_quanwang);
		game_saifadou_quanjineng1 = (Button) zhanwujiContentView
				.findViewById(R.id.game_saifadou_quanjineng1);
		game_saifadou_quanjineng2 = (Button) zhanwujiContentView
				.findViewById(R.id.game_saifadou_quanjineng2);

		localList.clear();
		localList.add(game_zhanwuji_quanwang);
		localList.add(game_zhanwuji_quanjineng1);
		localList.add(game_zhanwuji_quanjineng2);
		localList.add(game_saifadou_quanwang);
		localList.add(game_saifadou_quanjineng1);
		localList.add(game_saifadou_quanjineng2);

		game_zhanwuji_quanwang.setOnClickListener(this);
		game_zhanwuji_quanjineng1.setOnClickListener(this);
		game_zhanwuji_quanjineng2.setOnClickListener(this);
		game_saifadou_quanwang.setOnClickListener(this);
		game_saifadou_quanjineng1.setOnClickListener(this);
		game_saifadou_quanjineng2.setOnClickListener(this);

	}

	private void initWeightData(View WeightContentView) {
		game_nan_zhongliangji = (Button) WeightContentView
				.findViewById(R.id.game_nan_zhongliangji);
		game_nan_qingliangji = (Button) WeightContentView
				.findViewById(R.id.game_nan_qingliangji);
		game_nan_ciqingliangji = (Button) WeightContentView
				.findViewById(R.id.game_nan_ciqingliangji);
		game_nv_zhongliangji = (Button) WeightContentView
				.findViewById(R.id.game_nv_zhongliangji);
		game_nv_qingliangji = (Button) WeightContentView
				.findViewById(R.id.game_nv_qingliangji);
		game_nv_ciqingliangji = (Button) WeightContentView
				.findViewById(R.id.game_nv_ciqingliangji);
		localList.clear();
		localList.add(game_nan_zhongliangji);
		localList.add(game_nan_qingliangji);
		localList.add(game_nan_ciqingliangji);
		localList.add(game_nv_zhongliangji);
		localList.add(game_nv_qingliangji);
		localList.add(game_nv_ciqingliangji);
		game_nan_zhongliangji.setOnClickListener(this);
		game_nan_qingliangji.setOnClickListener(this);
		game_nan_ciqingliangji.setOnClickListener(this);
		game_nv_zhongliangji.setOnClickListener(this);
		game_nv_qingliangji.setOnClickListener(this);
		game_nv_ciqingliangji.setOnClickListener(this);

	}

	public void isCheckZhan(TextView localBtn) {
		it = localList.iterator();
		while (it.hasNext()) {
			TextView btn = it.next();
			if (btn.getId() == localBtn.getId()) {
				localBtn.setBackgroundColor(Color.RED);
			} else {
				btn.setBackgroundColor(Color.WHITE);
			}
		}
	}

	/**
	 * popupwindow里面的textview是否被选中
	 *
	 * @param localBtn
	 */
//	TextView record;

	public void isChecks(TextView localBtn) {
		it = localList.iterator();
		while (it.hasNext()) {
			TextView btn = it.next();
			if (btn.getId() == localBtn.getId()) {
				localBtn.setBackgroundColor(Color.RED);
//				record = localBtn;
			} else {
				btn.setBackgroundColor(Color.WHITE);
			}
		}
	}

	/*
	 * popuWindow中Button是否被选中
	 */
	public void isCheckWeight(Button localBtn) {

		it = localList.iterator();
		while (it.hasNext()) {

			Button btn = it.next();
			if (btn.getId() == localBtn.getId()) {

				if(!localBtn.isSelected()) {

					localBtn.setTextColor(0xffffffff);
					localBtn.setSelected(true);

				}else {

					tizhong_gongjinji.setText("体重公斤级");
					weightvideo_id=0;
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
	public void isCheckZhanwuji(Button localBtn) {
		it = localList.iterator();
		while (it.hasNext()) {
			Button btn = it.next();
			if (btn.getId() == localBtn.getId()) {
				if(!localBtn.isSelected()) {
					localBtn.setTextColor(0xffffffff);
					localBtn.setSelected(true);

				}else {
					zhanwuji_saifadou.setText("战无极/赛法斗");
					zhanwuji=0;
					btn.setSelected(false);
					btn.setTextColor(0xff333333);

				}

			} else {
				btn.setSelected(false);
				btn.setTextColor(0xff333333);
			}
		}
	}
	public void isCheckArea(Button localBtn) {
		it = localList.iterator();
		while (it.hasNext()) {
			Button btn = it.next();
			if (btn.getId() == localBtn.getId()) {
				if(!localBtn.isSelected()) {
					localBtn.setTextColor(0xffffffff);
					localBtn.setSelected(true);

				}else {
					saiqu_club.setText("赛区/俱乐部");
					areavideo_id=0;
					btn.setSelected(false);
					btn.setTextColor(0xff333333);

				}

			} else {
				btn.setSelected(false);
				btn.setTextColor(0xff333333);
			}
		}
	}

	/*
	 * 初始化popuWindow中数据
	 */
	public void initData(View clubContentView) {
		btn_pClub_beijing = (Button) clubContentView
				.findViewById(R.id.btn_pClub_beijing);
		btn_pClub_shanghai = (Button) clubContentView
				.findViewById(R.id.btn_pClub_shanghai);
		btn_pClub_hainan = (Button) clubContentView
				.findViewById(R.id.btn_pClub_hainan);
		btn_pClub_heilongjiang = (Button) clubContentView
				.findViewById(R.id.btn_pClub_heilongjiang);
		btn_pClub_guangzhou = (Button) clubContentView
				.findViewById(R.id.btn_pClub_guangzhou);
		btn_pClub_shanxi = (Button) clubContentView
				.findViewById(R.id.btn_pClub_shanxi);
		btn_pClub_shandong = (Button) clubContentView
				.findViewById(R.id.btn_pClub_shandong);
		btn_pClub_nanjing = (Button) clubContentView
				.findViewById(R.id.btn_pClub_nanjing);
		btn_pClub_xian = (Button) clubContentView
				.findViewById(R.id.btn_pClub_xian);


		btn_pClub_shandong.setVisibility(View.GONE);
		btn_pClub_nanjing.setVisibility(View.GONE);
		btn_pClub_xian.setVisibility(View.GONE);
		localList.clear();
		localList.add(btn_pClub_beijing);
		localList.add(btn_pClub_shanghai);
		localList.add(btn_pClub_hainan);
		localList.add(btn_pClub_heilongjiang);
		localList.add(btn_pClub_guangzhou);
		localList.add(btn_pClub_shanxi);
		localList.add(btn_pClub_shandong);
		localList.add(btn_pClub_nanjing);
		localList.add(btn_pClub_xian);
		btn_pClub_shanghai.setOnClickListener(this);
		btn_pClub_beijing.setOnClickListener(this);
		btn_pClub_hainan.setOnClickListener(this);
		btn_pClub_heilongjiang.setOnClickListener(this);
		btn_pClub_guangzhou.setOnClickListener(this);
		btn_pClub_shanxi.setOnClickListener(this);
		btn_pClub_shandong.setOnClickListener(this);
		btn_pClub_nanjing.setOnClickListener(this);
		btn_pClub_xian.setOnClickListener(this);
	}

	@Override
	public void onScroll(int scrollY) {
		//System.out.println("scrollY" + scrollY) ;
		if (scrollY == 11111111) {
//            if (ll_tab.getVisibility()== View.GONE) {
//                return;
//            }
			if (saishihuikan_refresh.getScrollY()!= 0) {
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
			pb_saishikuikan_jiazaigengduo.setVisibility(View.VISIBLE);

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
