package com.bigdata.xinhuanufang.game;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MathGameAdapter;
import com.bigdata.xinhuanufang.adapter.MessageBoardAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
import com.bigdata.xinhuanufang.game.bean.MessageBoardBean;
import com.bigdata.xinhuanufang.main.InterViewWebActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;
import com.bigdata.xinhuanufang.utils.configUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;



/**
 * 回看详情页面
 * */
public class ReviewDetailsActivity extends BaseActivity implements
		OnClickListener {
	private ListViewForScrollView mathGameLV;// 相关比赛ListView
	private MathGameAdapter mathGameAdapter;
	private String[] finghterOne = { "青格勒1", "青格勒2" }; // 比赛人员

	private ListViewForScrollView messageBoradLV;// 留言板ListView
	private MessageBoardAdapter messageBoardAdapter;
	private String[] messageHuman = { "李昂叔叔", "李昂叔叔" }; // 留言人数
	private ImageView collectionIV; // 收藏ImageView
	private ImageView video;
	private ImageView iv_reviewD_fighterPhotoOne;
	private TextView tv_reviewD_fighterPhotoOne;
	private ImageView iv_reviewD_fighterPhotoTwo;
	private TextView tv_reviewD_fighterPhotoTwo;
	private TextView tv_reviewD_successFighter;// 获胜者
	private TextView video_time;// 比赛时间
	private List<MessageBoardBean> messageBoard;
	private TextView video_content;
	private List<GameDetailsInfo> list;
	private Button btn_reviewD_send;
	private EditText te_reviewD_discuss;
	private String video_liveguessid;
	// 记录视频的id
	private String video_id;
	private int position = -1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					list = (List<GameDetailsInfo>) msg.obj;
					// 将数据加载得listview上
					int a=list.size();
					if (a==0) {
						return;
					}
					showListView(list);
					break;
				case 100:
					messageBoard = (List<MessageBoardBean>) msg.obj;
					if (messageBoard.size()==0) {
						return;
					}
					showMessageData(messageBoard);
					break;
				default:
					break;
			}
		};

	};
	private boolean flag;
	private ImageView video_bofang;
	private String video_liveguessids;

	/*
	 * 加载布局
	 */
	@Override
	public int getView() {
		return R.layout.activity_reviewdetails;
	}

	protected void showMessageData(List<MessageBoardBean> messageBoard) {
		messageBoradLV = (ListViewForScrollView) findViewById(R.id.lv_reviewD_messageBoard);
		messageBoardAdapter = new MessageBoardAdapter(
				ReviewDetailsActivity.this, messageBoard);
		messageBoradLV.setAdapter(messageBoardAdapter);
	}

	/*
	 * 界面初始化操作
	 */
	@Override
	public void initView() {
		super.initView();
		super.setTitle("回看详情");
		super.setImageVisible();
		super.setGone();
		super.setRightImage(R.drawable.shoucang_0);
		video = (ImageView) findViewById(R.id.videoView);
		video_bofang = (ImageView) findViewById(R.id.video_bofang);
		video_bofang.setOnClickListener(this);
		iv_reviewD_fighterPhotoOne = (ImageView) findViewById(R.id.iv_reviewD_fighterPhotoOne);
		iv_reviewD_fighterPhotoTwo = (ImageView) findViewById(R.id.iv_reviewD_fighterPhotoTwo);
		tv_reviewD_successFighter = (TextView) findViewById(R.id.tv_reviewD_successFighter);
		tv_reviewD_fighterPhotoOne = (TextView) findViewById(R.id.tv_reviewD_fighterPhotoOne);
		tv_reviewD_fighterPhotoTwo = (TextView) findViewById(R.id.tv_reviewD_fighterPhotoTwo);
		video_content = (TextView) findViewById(R.id.video_content);
		video_time = (TextView) findViewById(R.id.video_time);
//		if (!LibsChecker.checkVitamioLibs(this))
//			return;
		// Vitamio.isInitialized(this);

		// video.setVideoPath("http://115.28.69.240/boxing/app/video_youku.php?video_id=10");
		// video.start();
		// 返回键处理
		setBack();
		//
		// Intent ReviewToMintent = new Intent(ReviewDetailsActivity.this,
		// MainActivity.class);
		// Bundle bundle = new Bundle();
		// bundle.putString("name", "game");
		// ReviewToMintent.putExtras(bundle);
		// startActivity(ReviewToMintent);
		//
		// super.setBack(ReviewToMintent);

		Intent intent = getIntent();
		String content = intent.getStringExtra("context");
		// 获取网络数据
		int weightvideo_id = intent.getIntExtra("weightvideo_id", 0);
		int areavideo_id = intent.getIntExtra("areavideo_id", 0);
		int zhanwuji = intent.getIntExtra("zhanwuji", 1);
		int page = intent.getIntExtra("page", 1);
		int TAG = intent.getIntExtra("tag", -1);


		System.out.println("pos的值是" + position);
		video_liveguessid = intent.getStringExtra("video_liveguessid");
		list = new ArrayList<GameDetailsInfo>();

		initNetwork(content);
		if (TAG == 1) {
			// 赛事回看的条目过来的数据
			position = intent.getIntExtra("pos", -1);
			int Page = intent.getIntExtra("page", 1);
//			for (int i = 0; i < Page; i++) {
//
//				getNetWorkData(String.valueOf(weightvideo_id),
//						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
//						String.valueOf(i), video_liveguessid);
//				System.out.println("刷新"+i);
//			}
			String Player_namea=intent.getStringExtra("Player_namea");
			String Player_nameb=intent.getStringExtra("Player_nameb");
			String Player_heada=intent.getStringExtra("Player_heada");
			String Player_headb=intent.getStringExtra("Player_headb");
			String Player_success=intent.getStringExtra("Player_success");
			String Video_time=intent.getStringExtra("Video_time");
			String Video_content=intent.getStringExtra("Video_content");
			String Video_pic=intent.getStringExtra("Video_pic");
			String Video_id=intent.getStringExtra("Video_id");
			String Is_coll=intent.getStringExtra("Is_coll");
			video_liveguessids = intent.getStringExtra("video_liveguessid");
			getMessageBoardData(video_liveguessids);
			setResult(282);
			tv_reviewD_fighterPhotoOne
					.setText(Player_namea);
			tv_reviewD_fighterPhotoTwo
					.setText(Player_nameb);
			tv_reviewD_successFighter.setText(Player_success+ "胜");
			String strTime = configUtils.getStrTimes(Video_time);
			video_time.setText(strTime);
			video_time.setVisibility(View.GONE);
			video_content.setText(Video_content);

			//回看详情页面的图片加载
			x.image().bind(video, Config.ip + Video_pic);
			x.image().bind(iv_reviewD_fighterPhotoOne,
					Config.ip + Player_heada);
			x.image().bind(iv_reviewD_fighterPhotoTwo,
					Config.ip + Player_headb);
			video_id = Video_id;
			flag = false;
			if (Is_coll.equals("0")) {
				collectionIV.setImageResource(R.drawable.shoucang_0);
				flag=true;
			} else if (Is_coll.equals("1")) {
				collectionIV.setImageResource(R.drawable.shoucang_1);
				flag=false;
			}

		} else if (TAG == 2) {
			//选手相关过来的条目
//			for (int i = 0; i < page; i++) {
//				getNetWorkData(String.valueOf(weightvideo_id),
//						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
//						String.valueOf(i), video_liveguessid);
//			}
            //选手相关过来的数据
            /**
             *  bundle.putString("player_id",player_id);
             bundle.putString("state","1");
             */
			position = intent.getIntExtra("position", -1);
            String player_id=intent.getStringExtra("player_id");
            String state=intent.getStringExtra("state");
//            getDataList(player_id,state);
			String Player_namea=intent.getStringExtra("Player_namea");
			String Player_nameb=intent.getStringExtra("Player_nameb");
			String Player_heada=intent.getStringExtra("Player_heada");
			String Player_headb=intent.getStringExtra("Player_headb");
			String Player_success=intent.getStringExtra("Player_success");
			String Video_time=intent.getStringExtra("Video_time");
			String Video_content=intent.getStringExtra("Video_content");
			String Video_pic=intent.getStringExtra("Video_pic");
			String Video_id=intent.getStringExtra("Video_id");
			String Is_coll=intent.getStringExtra("Is_coll");
			video_liveguessids = intent.getStringExtra("video_liveguessid");
			getMessageBoardData(video_liveguessids);


			tv_reviewD_fighterPhotoOne
					.setText(Player_namea);
			tv_reviewD_fighterPhotoTwo
					.setText(Player_nameb);
			tv_reviewD_successFighter.setText(Player_success+ "胜");
			String strTime = configUtils.getStrTimes(Video_time);
			video_time.setText(strTime);
			video_time.setVisibility(View.GONE);
			video_content.setText(Video_content);

			//回看详情页面的图片加载
			x.image().bind(video, Config.ip + Video_pic);
			x.image().bind(iv_reviewD_fighterPhotoOne,
					Config.ip + Player_heada);
			x.image().bind(iv_reviewD_fighterPhotoTwo,
					Config.ip + Player_headb);
			video_id = Video_id;
			flag = false;
			if (Is_coll.equals("0")) {
				collectionIV.setImageResource(R.drawable.shoucang_0);
				flag=true;
			} else if (Is_coll.equals("1")) {
				collectionIV.setImageResource(R.drawable.shoucang_1);
				flag=false;
			}


		} else if (TAG == 3) {
			//选手相关过来的条目
			position = intent.getIntExtra("position", -1);
			for (int i = 0; i < page; i++) {
				getNetWorkData(String.valueOf(weightvideo_id),
						String.valueOf(areavideo_id), String.valueOf(zhanwuji),
						String.valueOf(i), video_liveguessid);
			}

		}

	}

    /**
     * 获取选手相关的视频
     * @param
     */
    private void getDataList(String player_id, String state) {
        // http://115.28.69.240/boxing/app/video_playerlist.php?player_id=1&status=1
		list.clear();
		System.out.println("请求的数据"+Config.ip + Config.app
				+ "/video_playerlist.php?player_id=" + player_id
				+ "&status=" + state);
		x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/video_playerlist.php?player_id=" + player_id
                        + "&status=" + state), new CommonCallback<String>() {
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


                                JSONArray js = json
                                        .getJSONArray("video_playerlist");
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
	protected void showListView(List<GameDetailsInfo> list) {
		if (list.size()==0) {
			return;
		}

		try {
			int a=position;
			tv_reviewD_fighterPhotoOne
                    .setText(list.get(position).getPlayer_namea());
			tv_reviewD_fighterPhotoTwo
                    .setText(list.get(position).getPlayer_nameb());
			tv_reviewD_successFighter.setText(list.get(position)
                    .getPlayer_success() + "胜");
			String strTime = configUtils.getStrTimes(list.get(position).getVideo_time());
			video_time.setText(strTime);
			video_time.setVisibility(View.GONE);
			video_content.setText(list.get(position).getVideo_content());

		//回看详情页面的图片加载
		x.image().bind(video, Config.ip + list.get(position).getVideo_pic());
		x.image().bind(iv_reviewD_fighterPhotoOne,
				Config.ip + list.get(position).getPlayer_heada());
		x.image().bind(iv_reviewD_fighterPhotoTwo,
				Config.ip + list.get(position).getPlayer_headb());
		video_id = list.get(position).getVideo_id();
		flag = false;
		if (list.get(position).getIs_coll().equals("0")) {
			collectionIV.setImageResource(R.drawable.shoucang_0);
			flag=true;
		} else if (list.get(position).getIs_coll().equals("1")) {
			collectionIV.setImageResource(R.drawable.shoucang_1);
			flag=false;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		video.setOnClickListener(this);

		// 获取留言板数据
//		getMessageBoardData(String video_liveguessid);
	}

	// 赛事回看筛选的网络数据
	public void getNetWorkData(String weightvideo_id, String areavideo_id,
							   String zhanwuji, String page, String video_liveguessid) {
		// http://115.28.69.240/boxing/app/video_list.php?weightvideo_id=2&areavideo_id=1&page=1
		System.out.println("赛事回看传递过来的留言" + video_liveguessid);
		list.clear();
		System.out.println("集合清空,进行二次获取数据");
		if (weightvideo_id.isEmpty()) {
			weightvideo_id = "0";
		}
		if (areavideo_id.isEmpty()) {
			areavideo_id = "0";
		}
		if (page.isEmpty()) {
			page = "1";
		}
		 if (zhanwuji.isEmpty()) {
		 weightvideo_id="0";
		 }
		System.out.println("--++"+Config.ip + Config.app + "/video_list."
				+ "php?weightvideo_id=" + weightvideo_id
				+ "&areavideo_id=" + areavideo_id + "&page=" + page
				+ "&user_id=" + Config.userID);
		x.http().get(new RequestParams(Config.ip + Config.app + "/video_list."
						+ "php?weightvideo_id=" + weightvideo_id
						+ "&areavideo_id=" + areavideo_id + "&page=" + page
						+ "&user_id=" + Config.userID),
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
							org.json.JSONArray js = json.getJSONArray("video");
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
							msg.what = 120;
							msg.obj = list;
							handler.sendMessage(msg);
						}
					}
				});
//		// 获取留言板数据
//		x.http().get(
//				new RequestParams(Config.ip + Config.app
//						+ "/video_message.php?video_liveguessid="
//						+ list.get(position).getVideo_liveguessid()), new CommonCallback<String>() {
//
//					@Override
//					public void onCancelled(CancelledException arg0) {
//					}
//
//					@Override
//					public void onError(Throwable arg0, boolean arg1) {
//					}
//
//					@Override
//					public void onFinished() {
//					}
//
//					@Override
//					public void onSuccess(String arg0) {
//						try {
//							JSONObject json = new JSONObject(arg0);
//							org.json.JSONArray js = json
//									.getJSONArray("message");
//							for (int i = 0; i < js.length(); i++) {
//								JSONObject json1 = js.getJSONObject(i);
//								String message_id = json1
//										.getString("message_id");
//								String message_userid = json1
//										.getString("message_userid");
//								String message_liveguessid = json1
//										.getString("message_liveguessid");
//								String message_content = json1
//										.getString("message_content");
//								String message_date = json1
//										.getString("message_date");
//								String user_username = json1
//										.getString("user_username");
//								String user_head = json1.getString("user_head");
//								messageBoard
//										.add(new MessageBoardBean(message_id,
//												message_userid,
//												message_liveguessid,
//												message_content, message_date,
//												user_username, user_head));
//							}
//							Message msg = Message.obtain();
//							msg.what = 100;
//							msg.obj = messageBoard;
//							handler.sendMessage(msg);
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				});
	}

	// 通过搜索得到的数据
	private void initNetwork(String content) {

		// http://115.28.69.240/boxing/app/video_search.php?keywords=%E7%94%B7%E5%AD%90
		// http://115.28.69.240/boxing/app/video_search.php?user_id=1&keywords=%E7%94%B7%E5%AD%90
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/video_search.php?user_id=" + Config.userID
						+ "&keywords=" + content),
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
							org.json.JSONArray js = json.getJSONArray("video");
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
						}
						System.out.println("集合大小" + list.size());
					}
				});
	}

	/*
	 * 初始化组件
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();

		Log.e("----fdf", "00000000000");
		mathGameLV = (ListViewForScrollView) findViewById(R.id.lv_reviewD_mathGame);
		mathGameAdapter = new MathGameAdapter(ReviewDetailsActivity.this,
				finghterOne);
		mathGameLV.setAdapter(mathGameAdapter);
		// 发送留言
		te_reviewD_discuss = (EditText) findViewById(R.id.te_reviewD_discuss);
		btn_reviewD_send = (Button) findViewById(R.id.btn_reviewD_send);
		btn_reviewD_send.setOnClickListener(this);
		// 留言板的布局

		collectionIV = (ImageView) findViewById(R.id.iv_itt_collection);
		collectionIV.setOnClickListener(this);

	}

	private void getMessageBoardData(String video_liveguessid) {
		// http://115.28.69.240/boxing/app/video_message.php?video_liveguessid=15
		// TODO: 2017/5/23 后台返回的数据一直是0: 		地址:http://47.93.113.190/app/video_list.php?weightvideo_id=0&areavideo_id=0&page=0&user_id=100004
		messageBoard = new ArrayList<MessageBoardBean>();
		if (list != null) {
			x.http().get(
					new RequestParams(Config.ip + Config.app
							+ "/video_message.php?video_liveguessid="
							+ video_liveguessid),
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
								org.json.JSONArray js = json
										.getJSONArray("message");
								for (int i = 0; i < js.length(); i++) {
									JSONObject json1 = js.getJSONObject(i);
									String message_id = json1
											.getString("message_id");
									String message_userid = json1
											.getString("message_userid");
									String message_liveguessid = json1
											.getString("message_liveguessid");
									String message_content = json1
											.getString("message_content");
									String message_date = json1
											.getString("message_date");
									String user_username = json1
											.getString("user_username");
									String user_head = json1
											.getString("user_head");
									messageBoard.add(new MessageBoardBean(
											message_id, message_userid,
											message_liveguessid,
											message_content, message_date,
											user_username, user_head));
								}
								Message msg = Message.obtain();
								msg.what = 100;
								msg.obj = messageBoard;
								handler.sendMessage(msg);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
		}

	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
				finish();
//                System.exit(0);
//                moveTaskToBack(false);
//				Intent backHome = new Intent(Intent.ACTION_MAIN);
//				backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				backHome.addCategory(Intent.CATEGORY_HOME);
//				startActivity(backHome);
//				ActivityManager manager = (ActivityManager) InterViewWebActivity.this.getSystemService(ACTIVITY_SERVICE); //获取应用程序管理器
//				manager.killBackgroundProcesses(getPackageName()); //强制结束当前应用程序
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			// 点击收藏ImageView更换图标
			case R.id.iv_itt_collection:

				if (flag) {
					// 收藏视频
					super.setRightImage(R.drawable.shoucang_1);
					flag = false;
					// 向服务器发送数据video_id
					// http://115.28.69.240/boxing/app/video_colladd.php?user_id=1&video_id=10
					// http://115.28.69.240/boxing/app/video_colladd.php?user_id=1&video_id=10
					x.http().get(
							new RequestParams(Config.ip + Config.app
									+ "/video_colladd.php?user_id=" + Config.userID
									+ "&video_id=" + video_id),
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
									showToast("收藏成功");
								}
							});
				} else {
					// 取消收藏
					super.setRightImage(R.drawable.shoucang_0);
					// http://115.28.69.240/boxing/app/video_collcannel.php?user_id=1&video_id=10
					x.http().get(
							new RequestParams(Config.ip + Config.app
									+ "/video_collcannel.php?user_id="
									+ Config.userID + "&video_id=" + video_id),
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
									showToast("取消收藏");
								}
							});
					flag = true;
				}

				break;
			case R.id.btn_reviewD_send:

				String text = te_reviewD_discuss.getText().toString();
				if (te_reviewD_discuss.getText().toString().isEmpty()) {
					Toast.makeText(ReviewDetailsActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					text = URLEncoder.encode(text, "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// http://192.168.33.10/boxing/app/video_messageadd.php?user_id=1&video_liveguessid=15&content=%E4
				// %B8%80%E6%9E%AA%E6%95%B2%E7%A2%8E%E4%BD%A0%E7%9A%84%E8%84%91%E8%A2%8B%E5%93%88%E5%93%88%E5%93%88
				String s=video_liveguessid;
				x.http().get(
						new RequestParams(Config.ip + Config.app
								+ "/video_messageadd.php?user_id=" + Config.userID
								+ "&video_liveguessid=" + video_liveguessid+"&content="+text), new CommonCallback<String>() {
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
									JSONObject json=new JSONObject(arg0);
									String code=json.getString("code");
									if (code.equals("1")) {
										Toast.makeText(ReviewDetailsActivity.this, "留言成功", Toast.LENGTH_SHORT).show();
										te_reviewD_discuss.setText("");
										getMessageBoardData(video_liveguessids);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
				break;
			case R.id.video_bofang:
				//播放按钮
			case R.id.videoView:
				Intent intent=new Intent(ReviewDetailsActivity.this,InterViewWebActivity.class);
				intent.putExtra("PlayUrl",Config.ip+Config.app+"/video_youku.php?video_id="+video_id);
				startActivity(intent);
				break;
			default:
				break;
		}
	}

}
