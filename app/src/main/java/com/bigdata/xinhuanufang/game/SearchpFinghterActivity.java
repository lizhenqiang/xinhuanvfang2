package com.bigdata.xinhuanufang.game;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.FighterMessageAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.LoadListViewnews;
import com.bigdata.xinhuanufang.game.bean.GameFighterBean;
import com.bigdata.xinhuanufang.game.bean.GameFighterBeanHonors;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class SearchpFinghterActivity extends BaseActivity implements   LoadListViewnews.ILoadListener {
	//选手的搜索
	//listview展示数据的首选项
	private List<GameFighterBean> list;
	// 头条资讯listView
	private LoadListViewnews topNewsMoreLV;
	// 自定义的头条资讯adapter
	private FighterMessageAdapter fightermessageadapter;
	//底部加载更多数据
	View footer;
	//记录要加载的第几页数据
	private int count=1;
	//搜索内容
	private String context;

	private SetPageListener pagerlistener;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					xuanshou_quanbu_wu.setVisibility(View.GONE);
					list = (List<GameFighterBean>) msg.obj;
					// 将数据加载得listview上
					showListView(list);
					break;
				case 120:
					if (list.size()==0) {
						xuanshou_quanbu_wu.setVisibility(View.VISIBLE);
					}
					break;
				default:
					break;
			}
		};

	};
	private Button xuanshou_quanbu_wu;


	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_topnews;
	}
	protected void showListView(final List<GameFighterBean> list) {
		// 查找到list控件

		topNewsMoreLV.setInterface(this);
		//在底部加载一个j
		fightermessageadapter = new FighterMessageAdapter(SearchpFinghterActivity.this,
				list);
		topNewsMoreLV.setAdapter(fightermessageadapter);
		fightermessageadapter.setOnFighterItemClick(new FighterMessageAdapter.OnFighterMessageClick() {
			@Override
			public void onFighterClick(int pos) {
				Intent fighterDetailsintent = new Intent(SearchpFinghterActivity.this,
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
				for (int i = 0; i < list.get(pos).getHonors().size(); i++) {
					fighterDetailsintent.putExtra("honors_content", list.get(pos).getHonors().get(i).getHonors_content());
					content=content+list.get(pos).getHonors().get(i).getHonors_content();
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
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		String title=intent.getStringExtra("title");
		context=intent.getStringExtra("context");
		setTitle(title);
		setGone();
		setBack();
		list=new ArrayList<GameFighterBean>();
		topNewsMoreLV = (LoadListViewnews) findViewById(R.id.lv_more_topnew);
		xuanshou_quanbu_wu = (Button) findViewById(R.id.xuanshou_quanbu_wu);
		xuanshou_quanbu_wu.setVisibility(View.VISIBLE);
		initNetwork(context);

	}
	@Override
	public void initData() {
		//http://115.28.69.240/boxing/app/news_search.php?keywords=%E7%83%AD%E7%83%AD
//		RequestParams params=new RequestParams(Config.ip+Config.app+"/news_search.php?");
//		params.addBodyParameter("keywords", context);



	}
	private void initNetwork(String content) {
		// http://115.28.69.240/boxing/app/video_search.php?keywords=%E7%94%B7%E5%AD%90
		// http://115.28.69.240/boxing/app/player_search.php?user_id=1&keywords=%E9%82%B9
		// http://115.28.69.240/boxing/app/player_search.php?user_id=1&keywords=皱
		//http://115.28.69.240/boxing/app/player_search.php?user_id=1&keywords=%E7%9A%B1
//		try {
//			context=URLEncoder.encode(context,"utf-8");
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		System.out.println(Config.ip + Config.app
				+ "/player_search.php?user_id="+Config.userID+"&keywords=" + content);

		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/player_search.php?user_id="+Config.userID+"&keywords=" + context),
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
							String code=json.getString("code");
							if (code.equals("1")) {
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
									JSONArray json1 = array.getJSONArray("honors");
									List<GameFighterBeanHonors> honors = new ArrayList<GameFighterBeanHonors>();
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
									String win = array.getString("win");
									String drew = array.getString("drew");
									String ko = array.getString("ko");
									String is_concern = array
											.getString("is_concern");
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
								msg.what = 110;
								msg.obj = list;
								handler.sendMessage(msg);
							}else if (code.equals("0")) {
								Message msg = Message.obtain();
								msg.what = 120;
								msg.obj = "暂无数据";
								handler.sendMessage(msg);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				count=pagerlistener.setpage();
//				 initNewDatas(count);//得到新数据
				fightermessageadapter.notifyDataSetChanged();//刷新ListView;
				topNewsMoreLV.loadCompleted();
			}
		}, 2000);
	}

	/**
	 * 获取加载第几页的接口回调
	 */
	public interface SetPageListener{
		public int setpage();
	}

}
	

	

