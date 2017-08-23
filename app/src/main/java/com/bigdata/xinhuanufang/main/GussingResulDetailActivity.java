package com.bigdata.xinhuanufang.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.bean.jiayoujingcaiLIUYAN;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator 竞猜结果详情Activity
 */
public class GussingResulDetailActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_reviewD_fighterPhotoOne;
	private ImageView iv_reviewD_fighterPhotoTwo;
	private TextView game_start_time;
	private TextView tv_reviewD_fighterPhotoTwo;
	private TextView tv_reviewD_fighterPhotoOne;
	private TextView tv_agrd_zuodianzan;
	private TextView tv_agrd_youdianzan;
	private Button btn_agrd_success;
	private TextView game_jincai_result_content;
	private ImageView iv_agrd_zuodianzan;
	private ImageView iv_agrd_youdianzan;
	private Button game_delete_jincai;
	private TextView tv_agrd_choujiang;
	private String guess_id;
	private ListViewForScrollView lv_reviewD_messageBoard;
	private String liveguess_id;
	private List<jiayoujingcaiLIUYAN> boardmessage;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					boardmessage = (List<jiayoujingcaiLIUYAN>) msg.obj;
					setboardData(boardmessage);
					break;
			}
		}
	};
	private List<jiayoujingcaiLIUYAN> boardData;
	private ProgressBar iv_agrd_red;
	private ProgressBar imageView2;


	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_resultdetails;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setGone();
		setTitle("竞猜结果详情");
		setBack();
		boardmessage=new ArrayList<>();
		// 接受传递过来的
		Intent intent = getIntent();
		liveguess_id = intent.getStringExtra("liveguess_id");
		String liveguess_pic = intent.getStringExtra("liveguess_pic");
		String liveguess_title = intent.getStringExtra("liveguess_title");
		String liveguess_time2 = intent.getStringExtra("liveguess_time2");
		String liveguess_content = intent.getStringExtra("liveguess_content");
		String liveguess_playera = intent.getStringExtra("liveguess_playera");
		String liveguess_playerb = intent.getStringExtra("liveguess_playerb");
		String liveguess_playerid = intent.getStringExtra("liveguess_playerid");
		guess_id= intent.getStringExtra("guess_id");
		String guess_playerid = intent.getStringExtra("guess_playerid");
		String guess_isprize = intent.getStringExtra("guess_isprize");
		String guess_num = intent.getStringExtra("guess_num");
		String playera_head = intent.getStringExtra("playera_head");
		String playera_name = intent.getStringExtra("playera_name");
		String playerb_head = intent.getStringExtra("playerb_head");
		String playerb_name = intent.getStringExtra("playerb_name");
		String joina = intent.getStringExtra("joina");
		String joinb = intent.getStringExtra("joinb");
		String suma = intent.getStringExtra("suma");
		String sumb = intent.getStringExtra("sumb");
		String sucess = intent.getStringExtra("sucess");
		//在这个页面需要隐藏的控件
		TextView jingcaijieguo_shuoming= (TextView) findViewById(R.id.jingcaijieguo_shuoming);
		jingcaijieguo_shuoming.setVisibility(View.GONE);
		LinearLayout game_send_message= (LinearLayout) findViewById(R.id.game_send_message);
		game_send_message.setVisibility(View.GONE);
		TextView board_message_jiayoujingcai_iserror= (TextView) findViewById(R.id.board_message_jiayoujingcai_iserror);
		board_message_jiayoujingcai_iserror.setVisibility(View.GONE);
		TextView jingcaijieguo_liuyanban= (TextView) findViewById(R.id.jingcaijieguo_liuyanban);
		jingcaijieguo_liuyanban.setVisibility(View.GONE);
		View jingcaijieguo_fengexian=findViewById(R.id.jingcaijieguo_fengexian);
		jingcaijieguo_fengexian.setVisibility(View.GONE);
		// 查找id
		iv_reviewD_fighterPhotoOne = (ImageView) findViewById(R.id.iv_reviewD_fighterPhotoOne);
		x.image().bind(iv_reviewD_fighterPhotoOne, Config.ip + playera_head);
		tv_reviewD_fighterPhotoOne = (TextView) findViewById(R.id.tv_reviewD_fighterPhotoOne);
		tv_reviewD_fighterPhotoOne.setText(playera_name);
		iv_reviewD_fighterPhotoTwo = (ImageView) findViewById(R.id.iv_reviewD_fighterPhotoTwo);
		x.image().bind(iv_reviewD_fighterPhotoTwo, Config.ip + playerb_head);
		tv_reviewD_fighterPhotoTwo = (TextView) findViewById(R.id.tv_reviewD_fighterPhotoTwo);
		tv_reviewD_fighterPhotoTwo.setText(playerb_name);
		game_start_time = (TextView) findViewById(R.id.game_start_time);
		//转换的时间
//		String strTime = configUtils.getStrTime(liveguess_time2);
		game_start_time.setText(sucess);
		if (sucess.equals(playera_name)) {
			game_start_time.setTextColor(Color.RED);
		}else if (sucess.equals(playerb_name)) {
			game_start_time.setTextColor(Color.BLUE);
		}
		// 押注的是哪一个
		iv_agrd_zuodianzan = (ImageView) findViewById(R.id.iv_agrd_zuodianzan);// 选手1
		iv_agrd_youdianzan = (ImageView) findViewById(R.id.iv_agrd_youdianzan);// 选手2
		if (guess_playerid.equals("1")) {
			iv_agrd_zuodianzan.setImageResource(R.drawable.zuodianzan_1);
		}else if (guess_playerid.equals("2")) {
			iv_agrd_youdianzan.setImageResource(R.drawable.zuodianzan_1);
		}
		tv_agrd_zuodianzan = (TextView) findViewById(R.id.tv_agrd_zuodianzan);
		tv_agrd_zuodianzan.setText(suma);
		tv_agrd_youdianzan = (TextView) findViewById(R.id.tv_agrd_youdianzan);
		tv_agrd_youdianzan.setText(sumb);
		btn_agrd_success = (Button) findViewById(R.id.btn_agrd_success);
		if (guess_playerid.equals(liveguess_playerid)) {
			btn_agrd_success.setText("竞猜成功");
		}
		//左边的进度条
		iv_agrd_red = (ProgressBar) findViewById(R.id.iv_agrd_red);
		int numbera= (int) Double.parseDouble(suma);
		iv_agrd_red.setProgress(numbera);
		//右边的进度条
		imageView2 = (ProgressBar) findViewById(R.id.imageView2);
		int numberb= (int) Double.parseDouble(sumb);
		imageView2.setProgress(10000-numberb);
		game_jincai_result_content = (TextView) findViewById(R.id.game_jincai_result_content);
		game_jincai_result_content.setText(liveguess_content);
		//删除竞猜
		game_delete_jincai=(Button) findViewById(R.id.game_delete_jincai);
		game_delete_jincai.setText("删除竞猜");
		//去抽奖
		tv_agrd_choujiang=(TextView) findViewById(R.id.tv_agrd_choujiang);
		game_delete_jincai.setOnClickListener(this);
		tv_agrd_choujiang.setOnClickListener(this);
		//留言板
		lv_reviewD_messageBoard = (ListViewForScrollView) findViewById(R.id.lv_reviewD_messageBoard);
		lv_reviewD_messageBoard.setVisibility(View.GONE);
		//获取留言列表
		getdataliuyan();

	}

	private void getdataliuyan() {
		http://115.28.69.240/boxing/app/liveguess_messagelist.php?liveguess_id=15
		x.http().get(new RequestParams(Config.ip + Config.app + "/liveguess_messagelist.php?liveguess_id=" + liveguess_id), new CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				try {
					JSONObject json=new JSONObject(s);
					JSONArray js=json.getJSONArray("message_list");
					for (int i = 0; i < js.length(); i++) {
						JSONObject json1=js.getJSONObject(i);
						String user_username=json1.getString("user_username");
						String user_head=json1.getString("user_head");
						String message_content=json1.getString("message_content");
						String message_date=json1.getString("message_date");
						boardmessage.add(new jiayoujingcaiLIUYAN(user_username,user_head,message_content,message_date));
					}
					Message msg=Message.obtain();
					msg.what=0;
					msg.obj=boardmessage;
					handler.sendMessage(msg);
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_agrd_choujiang:
				//去抽奖-->跳转到抽奖页面
				Intent luckdraw = new Intent(this,
						BreakGoldEgg.class);
				luckdraw.putExtra("title","砸金蛋");
				startActivity(luckdraw);
				break;
			case R.id.game_delete_jincai:
				//发起删除请求
				//http://115.28.69.240/boxing/app/guess_del.php?guess_id=1
				x.http().get(new RequestParams(Config.ip+Config.app+"/guess_del.php?guess_id="+guess_id), new CommonCallback<String>() {
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
								Toast.makeText(GussingResulDetailActivity.this, "成功删除", Toast.LENGTH_SHORT).show();
								finish();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

				break;
			default:
				break;
		}
	}



	public void setboardData(List<jiayoujingcaiLIUYAN> boardData) {
	}
}
