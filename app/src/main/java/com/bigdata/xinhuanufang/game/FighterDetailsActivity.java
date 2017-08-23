package com.bigdata.xinhuanufang.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GameReviewAdapter;
import com.bigdata.xinhuanufang.custom.CircleImageView;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;

import net.qiujuer.genius.blur.StackBlur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/*
 * 选手信息详情Activity
 * */
public class FighterDetailsActivity extends Activity implements OnClickListener {
	private ListViewForScrollView fighterDetailsLV;// 比赛视频ListView
	private GameReviewAdapter fighterDetailsAdapter;
	private String[] finghterOne = { "青格勒1", "青格勒2" }; // 比赛人员
	private LinearLayout fighterIntroduceLL; // 选手信息背景图
	private ImageView iv_afd_back; // 返回键
	private CircleImageView iv_afd_fighterPhoto;
	private TextView tv_afd_fighterName;
	private ImageView iv_afd_fighterSex;
	private TextView tv_afd_age;
	private TextView tv_afd_country;
	private TextView tv_afd_jibie;
	private TextView tv_afd_honor;
	private TextView tv_afd_zhanJi;
	private TextView tv_afd_height;
	private TextView tv_afd_armspan;
	private TextView tv_afd_weight;
	private TextView tv_afd_speciality;
	private TextView tv_afd_group;
	private TextView tv_afd_content;
	private TextView loock_all;
	private ImageView iv_afd_shoucang;
	private boolean flag;
	private String is_concern;
	// 数据集合
	private List<GameDetailsInfo> list;
	private String player_id = "";
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 110:
					list = (List<GameDetailsInfo>) msg.obj;
					// 将数据加载得listview上
					showListView(list);
					break;
				case 120:
					//暂无视频
					break;

				default:
					break;
			}
		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_fighterdetails);
		initView();
		Intent intent = getIntent();
		player_id = intent.getStringExtra("player_id");
		String player_head = intent.getStringExtra("player_head");
		is_concern= intent.getStringExtra("is_concern");
		String player_name = intent.getStringExtra("player_name");
		String player_sex = intent.getStringExtra("player_sex");
		String player_age = intent.getStringExtra("player_age");
		String player_area = intent.getStringExtra("player_area");
		String player_level = intent.getStringExtra("player_level");
		String drew = intent.getStringExtra("drew");
		String win = intent.getStringExtra("win");
		String ko = intent.getStringExtra("ko");
		String player_height = intent.getStringExtra("player_height");
		String player_weight = intent.getStringExtra("player_weight");
		String player_cm = intent.getStringExtra("player_cm");
		String player_special = intent.getStringExtra("player_special");
		String player_group = intent.getStringExtra("player_group");
		String honors_content = intent.getStringExtra("honors_content");
		String content = intent.getStringExtra("content");
		new DownloadImageTask().execute(Config.ip + player_head);
		fighterIntroduceLL.getBackground().setAlpha(50); // 设置选手信息背景图透明度
		// 数据填充
		tv_afd_fighterName.setText(player_name);
		x.image().bind(iv_afd_fighterPhoto, Config.ip + player_head);

		if (player_sex.equals("男")) {
			iv_afd_fighterSex.setImageResource(R.drawable.nan);
		} else if (player_sex.equals("女")) {
			iv_afd_fighterSex.setImageResource(R.drawable.nv);
		}
		tv_afd_age.setText(player_age);
		tv_afd_country.setText(player_area);
		tv_afd_jibie.setText(player_level);
		tv_afd_honor.setText(honors_content);
		String zhanji="战" + win + "胜" + ko + "KO";
		StringBuffer sb=new StringBuffer();
		sb.append(zhanji);
		System.out.println("长度"+sb.length());
//		for (int i = 0; i < honors_content.length(); i++) {
		try {
			if (sb.length()<honors_content.length()) {
				sb.append("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (honors_content.length()>20) {
                sb.append("\n");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
//		System.out.println("长度1"+sb.length());
//		System.out.println("长度2"+honors_content.length());
		tv_afd_zhanJi.setText(drew +sb.toString() );
		tv_afd_height.setText(player_height+"cm");
		tv_afd_weight.setText(player_weight+"kg");
		tv_afd_armspan.setText(player_cm+"cm");
		tv_afd_speciality.setText(player_special);
		tv_afd_group.setText(player_group);
		tv_afd_content.setText(content);
		flag=false;
		if (is_concern.equals("0")) {
			iv_afd_shoucang.setImageResource(R.drawable.shoucang_0);
			flag=true;
		} else if (is_concern.equals("1")) {
			iv_afd_shoucang.setImageResource(R.drawable.shoucang_1);
			flag=false;
		}

		list = new ArrayList<GameDetailsInfo>();
		// 获取网络数据
		getDataList(1);
	}
	private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {

		protected Drawable doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		protected void onPostExecute(Drawable result) {
			try {
				BitmapDrawable bd = (BitmapDrawable) result;
				Bitmap newBitmap = StackBlur.blur( bd.getBitmap(), 14, false);
				Drawable drawable = new BitmapDrawable(newBitmap);
				fighterIntroduceLL.setBackgroundDrawable(drawable);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			// 可以在这里通过第二个参数(文件名)来判断，是否本地有此图片
			drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), null);
		} catch (IOException e) {
			Log.d("skythinking", e.getMessage());
		}
		if (drawable == null) {
			Log.d("skythinking", "null drawable");
		} else {
			Log.d("skythinking", "not null drawable");
		}

		return drawable;
	}

	protected void showListView(final List<GameDetailsInfo> list) {
		// 选手相关视频的FighterDetailsAdapter
		fighterDetailsAdapter = new GameReviewAdapter(
				FighterDetailsActivity.this, list);
		fighterDetailsLV.setAdapter(fighterDetailsAdapter);
		fighterDetailsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("context", null);
				bundle.putString("weightvideo_id", list.get(position).getVideo_id());
				bundle.putString("video_liveguessid", list.get(position).getVideo_liveguessid());
				bundle.putInt("position", position);
				bundle.putInt("tag", 2);
                bundle.putString("player_id",player_id);
                bundle.putString("state","1");
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
				Intent intent=new Intent(FighterDetailsActivity.this,ReviewDetailsActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void getDataList(int state) {
		// http://115.28.69.240/boxing/app/video_playerlist.php?player_id=1&status=1
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

	private void initView() {
		// TODO Auto-generated method stub
		fighterIntroduceLL = (LinearLayout) findViewById(R.id.ll_afd_fighterIntroduce);
		fighterDetailsLV = (ListViewForScrollView) findViewById(R.id.lv_afd_gameVido);
		iv_afd_back = (ImageView) findViewById(R.id.iv_afd_back);
		// 头像
		iv_afd_fighterPhoto = (CircleImageView) findViewById(R.id.iv_afd_fighterPhoto);
		// 姓名
		tv_afd_fighterName = (TextView) findViewById(R.id.tv_afd_fighterName);
		// 性别
		iv_afd_fighterSex = (ImageView) findViewById(R.id.iv_afd_fighterSex);
		// 年龄
		tv_afd_age = (TextView) findViewById(R.id.tv_afd_age);
		// 国籍
		tv_afd_country = (TextView) findViewById(R.id.tv_afd_country);
		// 级别
		tv_afd_jibie = (TextView) findViewById(R.id.tv_afd_jibie);
		// 荣誉
		tv_afd_honor = (TextView) findViewById(R.id.tv_afd_honor);
		// 战绩
		tv_afd_zhanJi = (TextView) findViewById(R.id.tv_afd_zhanJi);
		// 身高
		tv_afd_height = (TextView) findViewById(R.id.tv_afd_height);
		// 体重
		tv_afd_weight = (TextView) findViewById(R.id.tv_afd_weight);
		// 臂展
		tv_afd_armspan = (TextView) findViewById(R.id.tv_afd_armspan);
		// 技术特点
		tv_afd_speciality = (TextView) findViewById(R.id.tv_afd_speciality);
		// 所属运动队
		tv_afd_group = (TextView) findViewById(R.id.tv_afd_group);
		// 全部荣誉
		tv_afd_content = (TextView) findViewById(R.id.tv_afd_content);
		loock_all = (TextView) findViewById(R.id.loock_all);
		// 收藏
		iv_afd_shoucang = (ImageView) findViewById(R.id.iv_afd_shoucang);
		iv_afd_back.setOnClickListener(this);
		loock_all.setOnClickListener(this);
		iv_afd_shoucang.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.iv_afd_back:
				finish();
				break;
			case R.id.iv_afd_shoucang:
				if (flag) {
//					iv_afd_shoucang.setImageResource(R.drawable.shoucang_1);
					// 进行关注请求
					//http://115.28.69.240/boxing/app/player_concern.php?user_id=1&player_id=3
					x.http().get(
							new RequestParams(Config.ip + Config.app
									+ "/player_concern.php?user_id="
									+ Config.userID + "&player_id=" + player_id),
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
										JSONObject json=new JSONObject(arg0);
										String code=json.getString("code");
										if (code.equals("1")) {
											Toast.makeText(FighterDetailsActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
					flag=false;
					iv_afd_shoucang.setImageResource(R.drawable.shoucang_1);
				} else {
//						iv_afd_shoucang.setImageResource(R.drawable.shoucang_0);
						// 取消关注
						// http://115.28.69.240/boxing/app/player_concern.php?user_id=1&player_id=3
						x.http().get(
								new RequestParams(Config.ip + Config.app
										+ "/player_concern.php?user_id="
										+ Config.userID + "&player_id=" + player_id),
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
											JSONObject json=new JSONObject(arg0);
											String code=json.getString("code");
											if (code.equals("1")) {
												Toast.makeText(FighterDetailsActivity.this, "取消关注", Toast.LENGTH_SHORT).show();
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								});
						flag=true;
						iv_afd_shoucang.setImageResource(R.drawable.shoucang_0);

				}

				break;
			case R.id.loock_all:
				int loock_all = 2;
				Intent intent = new Intent(this, SearchpGameActivity.class);
				intent.putExtra("loock_all", loock_all);
				intent.putExtra("player_id", player_id);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
