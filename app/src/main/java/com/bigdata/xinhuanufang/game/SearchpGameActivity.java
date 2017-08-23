package com.bigdata.xinhuanufang.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.SearchGameAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.LoadListViewnews;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class SearchpGameActivity extends BaseActivity implements OnItemClickListener,  LoadListViewnews.ILoadListener {
	//赛事回看的搜索
	//listview展示数据的首选项
	private List<GameDetailsInfo> list;
	// 头条资讯listView
	private LoadListViewnews topNewsMoreLV;
	// 自定义的头条资讯adapter
	private SearchGameAdapter searchGameAdapter;
	//底部加载更多数据
	View footer;
	//记录要加载的第几页数据
	private int count=1;
	//搜索内容
	private String context;

	private SetPageListener pagerlistener;
	private String player_id;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					xuanshou_quanbu_wu.setVisibility(View.GONE);
					list = (List<GameDetailsInfo>) msg.obj;
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
	protected void showListView(List<GameDetailsInfo> list) {
		// 查找到list控件

		topNewsMoreLV.setInterface(this);
		//在底部加载一个j
		searchGameAdapter = new SearchGameAdapter(SearchpGameActivity.this,
				list);
		topNewsMoreLV.setAdapter(searchGameAdapter);
		topNewsMoreLV.setOnItemClickListener(this);

	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		Intent intent=getIntent();
		context=intent.getStringExtra("context");
		int stateus=intent.getIntExtra("loock_all", -1);
		player_id=intent.getStringExtra("player_id");
		setTitle("比赛视频");
		setGone();
		setBack();
		xuanshou_quanbu_wu = (Button) findViewById(R.id.xuanshou_quanbu_wu);
		xuanshou_quanbu_wu.setVisibility(View.GONE);
		list=new ArrayList<GameDetailsInfo>();
		topNewsMoreLV = (LoadListViewnews) findViewById(R.id.lv_more_topnew);

		if (stateus==2) {
			getLockAllData(stateus);
		}else if (!context.equals("")&&context!=null) {
			initNetwork(context);
		}

	}
	private void getLockAllData(int stateus) {
		//http://115.28.69.240/boxing/app/video_playerlist.php?player_id=1&status=1
		list.clear();
		String s=Config.ip + Config.app + "/video_playerlist.php?player_id="+player_id+"&status="+stateus;
		System.out.println("获取到的数据"+Config.ip + Config.app + "/video_playerlist.php?player_id="+player_id+"&status="+stateus);
		x.http().get(
				new RequestParams(Config.ip + Config.app + "/video_playerlist.php?player_id="+player_id+"&status="+stateus),
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


							JSONArray js = json.getJSONArray("video_playerlist");
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
	public void initData() {
		//http://115.28.69.240/boxing/app/news_search.php?keywords=%E7%83%AD%E7%83%AD
//		RequestParams params=new RequestParams(Config.ip+Config.app+"/news_search.php?");
//		params.addBodyParameter("keywords", context);



	}
	private void initNetwork(String content) {
		// http://115.28.69.240/boxing/app/video_search.php?keywords=%E7%94%B7%E5%AD%90
		//http://115.28.69.240/boxing/app/video_search.php?user_id=1&keywords=%E7%94%B7%E5%AD%90
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/video_search.php?user_id="+Config.userID+"&keywords=" + context),
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
								String is_coll=array.getString("is_coll");
								list.add(new GameDetailsInfo(video_id,
										video_playeruserid,
										video_playeruserid2, video_time,
										video_title, video_success, video_pic,
										video_content, video_liveguessid,
										player_namea, player_heada,
										player_nameb, player_headb,
										player_success,is_coll));
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
//		Intent intent=new Intent(this,ReviewDetailsActivity.class);
		/**
		 * ReviewDetailsintent.putExtra("weightvideo_id", weightvideo_id);
		 ReviewDetailsintent.putExtra("areavideo_id", areavideo_id);
		 ReviewDetailsintent.putExtra("zhanwuji", zhanwuji);
		 ReviewDetailsintent.putExtra("pos", pos);
		 ReviewDetailsintent.putExtra("page", page);
		 ReviewDetailsintent.putExtra("video_liveguessid",
		 video_liveguessid);
		 ReviewDetailsintent.putExtra("tag", 1);
		 */
//		Bundle bundle=new Bundle();
//		bundle.putString("weightvideo_id",list.get(position).getVideo_id());
//		bundle.putString("areavideo_id","");
//		bundle.putString("zhanwuji","");
//		bundle.putString("pos",position+"");
//		bundle.putString("page","");
//		bundle.putString("tag",1+"");
//		bundle.putString("video_liveguessid",list.get(position).getVideo_liveguessid());
//		intent.putExtra("weightvideo_id",list.get(position).getVideo_id());
//		intent.putExtra("areavideo_id", "");
//		intent.putExtra("zhanwuji", "");
//		intent.putExtra("pos", position);
//		intent.putExtra("page", "");
//		intent.putExtra("video_liveguessid",
//				list.get(position).getVideo_liveguessid());
//		intent.putExtra("tag", 1);
//		intent.putExtra(bundle);
//		startActivity(intent);
		Bundle bundle=new Bundle();
		bundle.putString("context", context);
		bundle.putString("weightvideo_id", list.get(position).getVideo_id());
		bundle.putString("video_liveguessid", list.get(position).getVideo_liveguessid());
		bundle.putInt("position", position);
		bundle.putInt("tag", 2);
		bundle.putString("player_id",player_id);
		bundle.putString("state","2");
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
		Intent intent=new Intent(this,ReviewDetailsActivity.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				count++;
				getLockAllData(2);//得到新数据
				searchGameAdapter.notifyDataSetChanged();//刷新ListView;
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
	

	

