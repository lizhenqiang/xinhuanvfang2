package com.bigdata.xinhuanufang.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MainBiSaiBeanAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.bean.MainBiSaiBean;
import com.bigdata.xinhuanufang.game.bean.GameJiaYouJinCaiBiSai;
import com.bigdata.xinhuanufang.main.weibo.AccessTokenKeeper;
import com.bigdata.xinhuanufang.mine.BuyGoldenGloveActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;
import com.bigdata.xinhuanufang.utils.configUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static com.bigdata.xinhuanufang.main.LuckDraw.SHARE_ALL_IN_ONE;
import static com.bigdata.xinhuanufang.main.LuckDraw.SHARE_CLIENT;
import static com.tencent.connect.share.QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;
import static java.lang.Integer.parseInt;


/**
 * @author Administrator 比赛竞猜预告Activity
 */
public class GameGussingDetailActivity extends BaseActivity implements OnClickListener {

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
	private ImageView xiangyou;
	private boolean flag=true;
	private PopupWindow popupWindow;
	private ImageView weibo_share;
	private ImageView weixin_share;
	private ImageView pengyou_share;
	private ImageView qq_share;
	private ImageView kongjian_share;
	private ProgressBar iv_agrd_red;
	private ProgressBar imageView2;
	private String liveguess_id;
	private EditText te_reviewD_discuss;
	private ListViewForScrollView lv_reviewD_messageBoard;
	private List<MainBiSaiBean> messageBoard;
	private MainBiSaiBeanAdapter messageBoardAdapter;
	private String playerid;
	private int jinshoutaonumber=20;
	private int jinshoutaoa=-1;
	private int jinshoutaob=-1;
	private List<GameJiaYouJinCaiBiSai> dataList;
	/** 微博微博分享接口实例 */
	private IWeiboShareAPI mWeiboShareAPI = null;
	private int mShareType = SHARE_CLIENT;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 100:
					messageBoard = (List<MainBiSaiBean>) msg.obj;
					showMessageData(messageBoard);
					break;
				case 110:
					board_message_jiayoujingcai_iserror.setVisibility(View.VISIBLE);
					board_message_jiayoujingcai_iserror.setText("暂时没有留言");
					break;
				case 0:
					dataList= (List<GameJiaYouJinCaiBiSai>) msg.obj;
					break;
				default:
					break;
			}
		}
	};
	private String text;
	private TextView bisaiyugao_hava_jinshoutao;
	private String guess_playerid;
	private String liveguess_playera;
	private String liveguess_playerb;
	private String liveguess_pic;
	private String liveguess_title;
	private String liveguess_time;
	private String liveguess_content;
	private String guess_isprize;
	private String guess_num;
	private String playera_head;
	private String playera_name;
	private String playerb_head;
	private String playerb_name;
	private String joina;
	private String joinb;
	private String suma;
	private String sumb;
	private String concern;
	private TextView board_message_jiayoujingcai_iserror;
	private ImageView jinshoutao_jianshao;
	private EditText shuru_jishoutao_number;
	private ImageView jinshoutao_zengjia;
	private int jinshoutao_number;
	private String yazhu_xuanshou="-1";


	private void showMessageData(List<MainBiSaiBean> messageBoard) {
		messageBoardAdapter = new MainBiSaiBeanAdapter(
				GameGussingDetailActivity.this, messageBoard);
		lv_reviewD_messageBoard.setAdapter(messageBoardAdapter);
		lv_reviewD_messageBoard.deferNotifyDataSetChanged();
	}

	@Override
	public int getView() {
		return R.layout.activity_gussingresultdetail;
	}

	@Override
	public void initView() {
		super.initView();
		setGone();
		setTitle("比赛预告");
		setBack();
		super.iv_itt_collection.setImageResource(R.drawable.fenxiang);
		super.iv_itt_collection.setVisibility(View.VISIBLE);
		super.iv_itt_collection.setOnClickListener(this);
		// 接受传递过来的
		Intent intent = getIntent();
		liveguess_id = intent.getStringExtra("liveguess_id");
		liveguess_pic = intent.getStringExtra("liveguess_pic");
		liveguess_title = intent.getStringExtra("liveguess_title");
		liveguess_time = intent.getStringExtra("liveguess_time");
		System.out.println("首页过来的比赛预告的时间"+liveguess_time);
		liveguess_content = intent.getStringExtra("liveguess_content");
		liveguess_playera = intent.getStringExtra("liveguess_playera");
		liveguess_playerb = intent.getStringExtra("liveguess_playerb");
		guess_id= intent.getStringExtra("guess_id");
		guess_playerid = intent.getStringExtra("guess_playerid");
		guess_isprize = intent.getStringExtra("guess_isprize");
		guess_num = intent.getStringExtra("guess_num");
		playera_head = intent.getStringExtra("playera_head");
		playera_name = intent.getStringExtra("playera_name");
		playerb_head = intent.getStringExtra("playerb_head");
		playerb_name = intent.getStringExtra("playerb_name");
		joina = intent.getStringExtra("joina");
		joinb = intent.getStringExtra("joinb");
		suma = intent.getStringExtra("suma");
		sumb = intent.getStringExtra("sumb");
		if (!suma.equals("")) {
			jinshoutaoa= Integer.parseInt(suma);
		}
		if (!sumb.equals("")) {
			jinshoutaob= Integer.parseInt(sumb);
		}
		concern = intent.getStringExtra("concern");
		messageBoard=new ArrayList<>();
		// 创建微博分享接口实例
		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Config.SINA_APPKEY);
		// 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
		// 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
		// NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
		mWeiboShareAPI.registerApp();
		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
		// 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
		// 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
		// 失败返回 false，不调用上述回调
//		if (savedInstanceState != null) {
//			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
//		}
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
		String strTime = configUtils.getStrTime(liveguess_time);
		game_start_time.setText(strTime);
		// 押注的是哪一个
		iv_agrd_zuodianzan = (ImageView) findViewById(R.id.iv_agrd_zuodianzan);// 选手1
		iv_agrd_youdianzan = (ImageView) findViewById(R.id.iv_agrd_youdianzan);// 选手2
		iv_agrd_zuodianzan.setOnClickListener(this);
		iv_agrd_youdianzan.setOnClickListener(this);
//		if (joina.equals("1")) {
//			iv_agrd_zuodianzan.setImageResource(R.drawable.zuodianzan_1);
//		}
//		if (joinb.equals("1")) {
//			iv_agrd_youdianzan.setImageResource(R.drawable.youdianzan_1);
//		}
		tv_agrd_zuodianzan = (TextView) findViewById(R.id.tv_agrd_zuodianzan);
		tv_agrd_zuodianzan.setText(suma);
		tv_agrd_youdianzan = (TextView) findViewById(R.id.tv_agrd_youdianzan);
		tv_agrd_youdianzan.setText(sumb);
		//查找进度条 选手1
		iv_agrd_red = (ProgressBar) findViewById(R.id.iv_agrd_red);
		iv_agrd_red.setProgress(parseInt(suma));
		//查找进度条 选手1
		imageView2 = (ProgressBar) findViewById(R.id.imageView2);
		imageView2.setProgress(10000-parseInt(sumb));
		btn_agrd_success = (Button) findViewById(R.id.btn_agrd_success);
		btn_agrd_success.setText("立即竞猜");
//		if (guess_playerid.equals(liveguess_playerid)) {
//			btn_agrd_success.setText("竞猜成功");
//		}
		game_jincai_result_content = (TextView) findViewById(R.id.game_jincai_result_content);
		game_jincai_result_content.setText(liveguess_content);
		//删除竞猜
		game_delete_jincai=(Button) findViewById(R.id.game_delete_jincai);
		if (concern.equals("0")) {
			game_delete_jincai.setTextColor(Color.WHITE);
			game_delete_jincai.setText("关注竞猜");
		}else if (concern.equals("1")) {
			game_delete_jincai.setTextColor(Color.RED);
			game_delete_jincai.setText("取消关注");
		}
		//去抽奖
		tv_agrd_choujiang=(TextView) findViewById(R.id.tv_agrd_choujiang);
		xiangyou = (ImageView) findViewById(R.id.xiangyou);
		xiangyou.setVisibility(View.GONE);
		tv_agrd_choujiang.setVisibility(View.GONE);
		game_delete_jincai.setOnClickListener(this);
		tv_agrd_choujiang.setOnClickListener(this);
		btn_agrd_success.setOnClickListener(this);
		//发送进行留言
		te_reviewD_discuss = (EditText) findViewById(R.id.te_reviewD_discuss);
		Button btn_reviewD_send= (Button) findViewById(R.id.btn_reviewD_send);
		btn_reviewD_send.setOnClickListener(this);
		board_message_jiayoujingcai_iserror = (TextView) findViewById(R.id.board_message_jiayoujingcai_iserror);
		board_message_jiayoujingcai_iserror.setVisibility(View.GONE);
		//留言列表
		lv_reviewD_messageBoard = (ListViewForScrollView) findViewById(R.id.lv_reviewD_messageBoard);
		//获取留言列表数据
		getmessagelist();
		//存储比赛预告的数据
		dataList = new ArrayList<GameJiaYouJinCaiBiSai>();

		refreshjinshoutao();

		//加油竞猜说明
		TextView jingcaijieguo_shuoming= (TextView) findViewById(R.id.jingcaijieguo_shuoming);
		jingcaijieguo_shuoming.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(GameGussingDetailActivity.this,jiayoujingcaishuoming.class);
				startActivity(intent);
			}
		});


	}

	private void refreshjinshoutao() {
		//如果不是使用三方账号登录的,直接刷新
		SharedPreferences sp = GameGussingDetailActivity.this.getSharedPreferences("config", 0);
		String password = sp.getString("password", "");
		x.http().get(new RequestParams(Config.ip + Config.app + "/login.php?user_tel=" + Config.USER_TEL + "&user_pwd=" + password), new CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				try {
					JSONObject js = new JSONObject(s);
					JSONObject json = js.getJSONObject("user");
					String user_id = json.getString("user_id");
					String user_delete = json.getString("user_delete");
					String user_tel = json.getString("user_tel");
					String user_gloves = json.getString("user_gloves");
					String user_head = json.getString("user_head");
					String user_username = json.getString("user_username");
					String user_pwd = json.getString("user_pwd");
					String user_sex = json.getString("user_sex");
					String user_sign = json.getString("user_sign");
					String user_date = json.getString("user_date");

					if (Config.RELEVANCE_TYPE.equals("3")) {
						Config.USER_GLOVES=user_gloves;
					}
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

	private void getmessagelist() {
		messageBoard.clear();
		//http://115.28.69.240/boxing/app/liveguess_messagelist.php?liveguess_id=15
		x.http().get(new RequestParams(Config.ip + Config.app + "/liveguess_messagelist.php?liveguess_id=" + liveguess_id), new CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				try {
					/**
					 *  "message_list":[
					 {
					 "user_username":"sadwaas",
					 "user_head":"/system/media/Pre-loaded/Pictures/Picture_01_Greenery",
					 "message_content":"拳王就是拳王，永远支持拳王",
					 "message_date":"1487583592"
					 },
					 */
					JSONObject json = new JSONObject(s);
					String code=json.getString("code");
					if (code.equals("1")) {
						org.json.JSONArray js = json
								.getJSONArray("message_list");
						for (int i = 0; i < js.length(); i++) {
							JSONObject json1 = js.getJSONObject(i);
							String message_content = json1
									.getString("message_content");
							String message_date = json1
									.getString("message_date");
							String user_username = json1
									.getString("user_username");
							String user_head = json1.getString("user_head");
							messageBoard.add(new MainBiSaiBean(user_username,user_head,message_content,message_date));
						}
						Message msg = Message.obtain();
						msg.what = 100;
						msg.obj = messageBoard;
						handler.sendMessage(msg);
					}else if (code.equals("0")) {
						Message msg = Message.obtain();
						msg.what = 110;
						handler.sendMessage(msg);

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
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
			case R.id.btn_reviewD_send:
				if (te_reviewD_discuss.getText().toString().isEmpty()) {
					Toast.makeText(GameGussingDetailActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				try {
//					text = URLEncoder.encode(te_reviewD_discuss.getText().toString(), "utf-8");
					text = te_reviewD_discuss.getText().toString();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String aaa=liveguess_id;
				//http://115.28.69.240/boxing/app/liveguess_messageadd.php?user_id=1&liveguess_id=15&content=
				x.http().get(new RequestParams(Config.ip + Config.app + "/liveguess_messageadd.php?user_id=" +
						Config.userID + "&liveguess_id=" + liveguess_id + "&content=" +text ), new CommonCallback<String>() {
					@Override
					public void onSuccess(String s) {
						try {
							JSONObject json=new JSONObject(s);
							String code=json.getString("code");
							if (code.equals("1")) {
								Toast.makeText(mContext, "留言成功", Toast.LENGTH_SHORT).show();
								te_reviewD_discuss.setText("");
								getmessagelist();
							}
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
				break;
			case R.id.tv_agrd_choujiang:
				//去抽奖-->跳转到抽奖页面
				Intent luckdraw = new Intent(this,
						LuckDraw.class);
				startActivity(luckdraw);
				break;
			case R.id.game_delete_jincai:
				if (game_delete_jincai.getText().equals("关注竞猜")) {
					//关注比赛
					//http://192.168.33.10/boxing/app/liveguess_concern.php?user_id=1&liveguess_id=3
					// &pic=222&playernamea=ni&playerheada=11&playerglovesa=11
					// &playernameb=32&playerheadb=328&playerglovesb=42398&title=322&time=432&content=329
					RequestParams params=new RequestParams(Config.ip+Config.app+"/liveguess_concern.php");
					params.addBodyParameter("user_id",Config.userID);
					params.addBodyParameter("liveguess_id",liveguess_id);
					params.addBodyParameter("pic",liveguess_pic);
					params.addBodyParameter("playernamea",playera_name);
					params.addBodyParameter("playerheada",playera_head);
					params.addBodyParameter("playerglovesa",suma);
					params.addBodyParameter("playernameb",playerb_name);
					params.addBodyParameter("playerheadb",playerb_head);
					params.addBodyParameter("playerglovesb",sumb);
					params.addBodyParameter("title",liveguess_title);
					params.addBodyParameter("time",liveguess_time);
					params.addBodyParameter("content",liveguess_content);
					x.http().get(params, new CommonCallback<String>() {
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
									Toast.makeText(GameGussingDetailActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
									game_delete_jincai.setText("取消关注");
									game_delete_jincai.setTextColor(Color.RED);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
				}else if (game_delete_jincai.getText().equals("取消关注")) {
					//取消关注比赛
					//http://115.28.69.240/boxing/app/liveguess_concern.php?user_id=1&liveguess_id=15
					RequestParams params=new RequestParams(Config.ip+Config.app+"/liveguess_concern.php");
					params.addBodyParameter("user_id",Config.userID);
					params.addBodyParameter("liveguess_id",liveguess_id);
					x.http().get(params, new CommonCallback<String>() {
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
									Toast.makeText(GameGussingDetailActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
									game_delete_jincai.setText("关注竞猜");
									game_delete_jincai.setTextColor(Color.WHITE);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});
				}



				break;
			case R.id.btn_agrd_success:
				//押注金手套
					if (!flag){
						//在这里在做一次判断,判断当前的比赛预告列表里面是否还存在当前的预告
						//两种方案,第一种就是竞猜结束后,继续竞猜,后台返回一个标识
						//在一种就是在点击竞猜后获取直播列表数据,然后判断当前的预告id是否存在于直播列表当中
//						getNetWorkData();
//						boolean isbisaiyugao=false;
//						String a=liveguess_id;
//						if (dataList.size()>0&&dataList!=null) {
//							for (int i = 0; i < dataList.size(); i++) {
//								if (liveguess_id.equals(dataList.get(i).getLiveguess_id())) {
//									isbisaiyugao=true;
//								}
//							}
//							if (isbisaiyugao==false) {
//								showToast("竞猜时间已经停止!");
//								return;
//							}
//						}



						//http://115.28.69.240/boxing/app/liveguess_add.php?guess_userid=1&guess_playerid=1&guess_liveguessid=15&guess_gloves=150
						AlertDialog.Builder builder = new AlertDialog.Builder(GameGussingDetailActivity.this);
						final AlertDialog dialog = builder.create();

						View view = View.inflate(this, R.layout.join_jingcai, null);
						TextView cancler= (TextView) view.findViewById(R.id.cancler);
						TextView enter= (TextView) view.findViewById(R.id.enter);
						TextView bisaiyugao_quchongzhi= (TextView) view.findViewById(R.id.bisaiyugao_quchongzhi);
						bisaiyugao_hava_jinshoutao = (TextView) view.findViewById(R.id.bisaiyugao_hava_jinshoutao);
						shuru_jishoutao_number = (EditText) view.findViewById(R.id.shuru_jishoutao_number);
						jinshoutao_number = 100;
						shuru_jishoutao_number.setText(jinshoutao_number+"");
						shuru_jishoutao_number.setSelection(shuru_jishoutao_number.getText().length());
						jinshoutao_jianshao = (ImageView) view.findViewById(R.id.jinshoutao_jianshao);
						shuru_jishoutao_number.addTextChangedListener(new TextWatcher() {
							@Override
							public void beforeTextChanged(CharSequence s, int start, int count, int after) {

							}

							@Override
							public void onTextChanged(CharSequence s, int start, int before, int count) {

							}

							@Override
							public void afterTextChanged(Editable s) {
								try {
									jinshoutao_number= Integer.parseInt(shuru_jishoutao_number.getText().toString());
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
							}
						});
						jinshoutao_jianshao.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								//减少金手套
								if (jinshoutao_number>20) {
									jinshoutao_number=jinshoutao_number-10;
									shuru_jishoutao_number.setText(jinshoutao_number+"");
									shuru_jishoutao_number.setSelection(shuru_jishoutao_number.getText().length());
								}else{
									Toast.makeText(GameGussingDetailActivity.this, "金手套数量不能再少了", Toast.LENGTH_SHORT).show();
								}
							}
						});
						
						jinshoutao_zengjia = (ImageView) view.findViewById(R.id.jinshoutao_zengjia);
						jinshoutao_zengjia.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (jinshoutao_number<Integer.parseInt(Config.USER_GLOVES)) {
									jinshoutao_number=jinshoutao_number+10;
									shuru_jishoutao_number.setText(jinshoutao_number+"");
									shuru_jishoutao_number.setSelection(shuru_jishoutao_number.getText().length());
								}else{
									Toast.makeText(GameGussingDetailActivity.this, "金手套数量不足,请充值", Toast.LENGTH_SHORT).show();
								}
							}
						});
						bisaiyugao_quchongzhi.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent=new Intent(GameGussingDetailActivity.this, BuyGoldenGloveActivity.class);
								startActivity(intent);
							}
						});
						bisaiyugao_hava_jinshoutao.setText(Config.USER_GLOVES);
						// dialog.setView(view);// 将自定义的布局文件设置给dialog
						dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题
						dialog.show();
						cancler.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
						});
						enter.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								if (jinshoutao_number>Integer.parseInt(Config.USER_GLOVES)) {
									showToast("金手套超额");
									return;
								}
								String a= String.valueOf(jinshoutao_number);
								String aa=Config.ip + Config.app + "/liveguess_add.php?guess_userid=" + Config.userID + "&guess_playerid=" + playerid + "&guess_liveguessid=" + liveguess_id + "&guess_gloves=" + jinshoutao_number;
			//http://115.28.69.240/boxing/app/liveguess_add.php?guess_userid=1&guess_playerid=1&guess_liveguessid=15&guess_gloves=150
								x.http().get(new RequestParams(Config.ip + Config.app + "/liveguess_add.php?guess_userid=" + Config.userID + "&guess_playerid=" + playerid + "&guess_liveguessid=" + liveguess_id + "&guess_gloves=" + jinshoutao_number), new CommonCallback<String>() {
									@Override
									public void onSuccess(String s) {
										try {
											JSONObject json=new JSONObject(s);
											String code=json.getString("code");
											if (code.equals("1")) {
												//成功之后,将所选择的选手的状态和记录全部清除
												Toast.makeText(GameGussingDetailActivity.this, "加入竞猜成功", Toast.LENGTH_SHORT).show();
												Config.USER_GLOVES= String.valueOf(Integer.parseInt(Config.USER_GLOVES)-jinshoutao_number);
												if (yazhu_xuanshou.equals("0")) {
													jinshoutaoa=jinshoutaoa+jinshoutao_number;
													tv_agrd_zuodianzan.setText(jinshoutaoa+"");
													iv_agrd_youdianzan.setImageResource(R.drawable.youdianzan_0);
												}else if (yazhu_xuanshou.equals("1")) {
													jinshoutaob=jinshoutaob+jinshoutao_number;
													tv_agrd_youdianzan.setText(jinshoutaob+"");
													iv_agrd_zuodianzan.setImageResource(R.drawable.zuodianzan_0);
												}
												setResult(88);
//												notifyAll();
											}
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
								dialog.dismiss();
							}
						});
					}else{
						Toast.makeText(GameGussingDetailActivity.this,"请先选择竞猜获胜方",Toast.LENGTH_SHORT).show();
					}

				break;
			case R.id.iv_agrd_zuodianzan:
					iv_agrd_zuodianzan.setImageResource(R.drawable.zuodianzan_1);
					playerid=liveguess_playera;
					yazhu_xuanshou = "0";
					flag=false;
					iv_agrd_youdianzan.setImageResource(R.drawable.youdianzan_0);

				break;
			case R.id.iv_agrd_youdianzan:
				iv_agrd_youdianzan.setImageResource(R.drawable.youdianzan_1);
					playerid=liveguess_playerb;
					yazhu_xuanshou = "1";
					flag=false;
					iv_agrd_zuodianzan.setImageResource(R.drawable.zuodianzan_0);
				break;
			case R.id.iv_itt_collection:
				//弹出分享的popupwindows
				//三方的分享
				popupwindowShow();
				//我的分享
				mine_bisaiyugao_share();
				break;
			case R.id.weibo_share:
				//微博
				weiboshareMessage();
				break;
			case R.id.weixin_share:
				shareMessage("微信");
				break;
			case R.id.pengyou_share:
				shareMessage("朋友圈");
				break;
			case R.id.qq_share:
				//qq分享
				QQshareMessage();
				break;
			case R.id.kongjian_share:
				//QQ空间
				shareToQzone();
				break;

			default:
				break;
		}
	}

	/**
	 * 获取首页直播列表
	 */
	private void getNetWorkData() {
		dataList.clear();
		// http://115.28.69.240/boxing/app/liveguess_list.php?user_id=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/liveguess_list.php?user_id=" + Config.userID),

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
							JSONArray json1 = json.getJSONArray("liveguess");
							for (int i = 0; i < json1.length(); i++) {
								JSONObject json2 = json1.getJSONObject(i);
								String liveguess_id = json2
										.getString("liveguess_id");
								String liveguess_pic = json2
										.getString("liveguess_pic");
								String liveguess_time = json2
										.getString("liveguess_time");
								String liveguess_title = json2
										.getString("liveguess_title");
								String liveguess_playera = json2
										.getString("liveguess_playera");
								String liveguess_playerb = json2
										.getString("liveguess_playerb");
								String liveguess_content = json2
										.getString("liveguess_content");
								String playera_head = json2
										.getString("playera_head");
								String playera_name = json2
										.getString("playera_name");
								String playerb_head = json2
										.getString("playerb_head");
								String playerb_name = json2
										.getString("playerb_name");
								String joina = json2.getString("joina");
								String joinb = json2.getString("joinb");
								String suma = json2.getString("suma");
								String sumb = json2.getString("sumb");
								String concern = json2.getString("concern");
								dataList.add(new GameJiaYouJinCaiBiSai(
										liveguess_id, liveguess_pic,
										liveguess_time, liveguess_title,
										liveguess_playera, liveguess_playerb,
										liveguess_content, playera_head,
										playera_name, playerb_head,
										playerb_name, joina, joinb, suma, sumb,
										concern));
							}
							Message msg=Message.obtain();
							msg.what=0;
							msg.obj=dataList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	/**
	 * 加油竞猜分享
	 */
	private void mine_bisaiyugao_share() {
		//http://115.28.69.240/boxing/app/liveguess_share.php?user_id=1&liveguess_id=3&pic=222&playernamea=ni&playernameb=32&title=322&time=432
		RequestParams params=new RequestParams(Config.ip+Config.app+"/liveguess_share.php?");
		params.addBodyParameter("user_id",Config.userID);
		params.addBodyParameter("liveguess_id",liveguess_id);
		params.addBodyParameter("pic",liveguess_pic);
		params.addBodyParameter("playernamea",playera_name);
		params.addBodyParameter("playernameb",playerb_name);
		params.addBodyParameter("title",liveguess_title);
		params.addBodyParameter("time",liveguess_time);
		x.http().post(params, new CommonCallback<String>() {
			@Override
			public void onSuccess(String s) {
				try {
					JSONObject json=new JSONObject(s);
					String code=json.getString("code");
					if (code.equals("1")) {
						Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
					}else if (code.equals("0")) {
						Toast.makeText(mContext, "分享失败", Toast.LENGTH_SHORT).show();
					}
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


	/**
	 * 微博分享
	 */
	private void weiboshareMessage() {
		sendMessage(true,true,false,false,false,false);
	}

	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。
	 */
	private void sendMessage(boolean hasText, boolean hasImage,
							 boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {

		if (mShareType == SHARE_CLIENT) {
			if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
				int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
				if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
					sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
				} else {
					sendSingleMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo/*, hasVoice*/);
				}
			} else {
				Toast.makeText(this, "微博客户端不支持 SDK 分享或微博客户端未安装或微博客户端是非官方版本", Toast.LENGTH_SHORT).show();
			}
		}
		else if (mShareType == SHARE_ALL_IN_ONE) {
			sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
		}
	}

	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。
	 * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
	 * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
	 *
	 * @param hasText    分享的内容是否有文本
	 * @param hasImage   分享的内容是否有图片
	 * @param hasWebpage 分享的内容是否有网页
	 * @param hasMusic   分享的内容是否有音乐
	 * @param hasVideo   分享的内容是否有视频
	 */
	private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
								   boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {

		// 1. 初始化微博的分享消息
		// 用户可以分享文本、图片、网页、音乐、视频中的一种
		WeiboMessage weiboMessage = new WeiboMessage();
		if (hasText) {
			weiboMessage.mediaObject = getTextObj();
		}
		if (hasImage) {
			weiboMessage.mediaObject = getImageObj();
		}
//		if (hasWebpage) {
//			weiboMessage.mediaObject = getWebpageObj();
//		}
//		if (hasVideo) {
//			weiboMessage.mediaObject = getVideoObj();
//		}
        /*if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }*/

		// 2. 初始化从第三方到微博的消息请求
		SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.message = weiboMessage;

		// 3. 发送请求消息到微博，唤起微博分享界面
		mWeiboShareAPI.sendRequest(GameGussingDetailActivity.this, request);
	}

	/**
	 * 创建图片消息对象。
	 *
	 * @return 图片消息对象。
	 */
	private ImageObject getImageObj() {
		ImageObject imageObject = new ImageObject();
//		BitmapDrawable bitmapDrawable = (BitmapDrawable) mImageView.getDrawable();
		//设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
		Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bisai_share_picture);
		Bitmap  bitmaps=compressImage(bitmap);
		imageObject.setImageObject(bitmaps);
		return imageObject;
	}

	/**
	 * 对图片的大小进行压缩
	 * @param image
	 * @return
	 */
	private Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( (baos.toByteArray().length / 1024)>32) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/**
	 * 创建文本消息对象。
	 *
	 * @return 文本消息对象。
	 */
	private TextObject getTextObj() {
		TextObject textObject = new TextObject();
		textObject.text = liveguess_content;
		return textObject;
	}
	/**
	 * 第三方应用发送请求消息到微博，唤起微博分享界面。
	 * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
	 * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
	 *
	 * @param hasText    分享的内容是否有文本
	 * @param hasImage   分享的内容是否有图片
	 * @param hasWebpage 分享的内容是否有网页
	 * @param hasMusic   分享的内容是否有音乐
	 * @param hasVideo   分享的内容是否有视频
	 * @param hasVoice   分享的内容是否有声音
	 */
	private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
								  boolean hasMusic, boolean hasVideo, boolean hasVoice) {

		// 1. 初始化微博的分享消息
		WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
		if (hasText) {
			weiboMessage.textObject = getTextObj();
		}

		if (hasImage) {
			weiboMessage.imageObject = getImageObj();
		}

		// 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
//		if (hasWebpage) {
//			weiboMessage.mediaObject = getWebpageObj();
//		}
//		if (hasMusic) {
//			weiboMessage.mediaObject = getMusicObj();
//		}
//		if (hasVideo) {
//			weiboMessage.mediaObject = getVideoObj();
//		}
//		if (hasVoice) {
//			weiboMessage.mediaObject = getVoiceObj();
//		}

		// 2. 初始化从第三方到微博的消息请求
		SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
		// 用transaction唯一标识一个请求
		request.transaction = String.valueOf(System.currentTimeMillis());
		request.multiMessage = weiboMessage;

		// 3. 发送请求消息到微博，唤起微博分享界面
		if (mShareType == SHARE_CLIENT) {
			mWeiboShareAPI.sendRequest(GameGussingDetailActivity.this, request);
		}
		else if (mShareType == SHARE_ALL_IN_ONE) {
			AuthInfo authInfo = new AuthInfo(GameGussingDetailActivity.this,
					Config.SINA_APPKEY, Config.SINA_REDIRECT_URL,
					Config.SINA_SCOPE);
			Oauth2AccessToken accessToken = AccessTokenKeeper.readAccessToken(getApplicationContext());
			String token = "";
			if (accessToken != null) {
				token = accessToken.getToken();
			}
			mWeiboShareAPI.sendRequest(this, request, authInfo, token, new WeiboAuthListener() {

				@Override
				public void onWeiboException( WeiboException arg0 ) {
				}

				@Override
				public void onComplete( Bundle bundle ) {
					// TODO Auto-generated method stub
					Oauth2AccessToken newToken = Oauth2AccessToken.parseAccessToken(bundle);
					AccessTokenKeeper.writeAccessToken(getApplicationContext(), newToken);
					Toast.makeText(getApplicationContext(), "onAuthorizeComplete token = " + newToken.getToken(), Toast.LENGTH_SHORT).show();
				}

				@Override
				public void onCancel() {
				}
			});
		}
	}
	//QQ好友分享
	private void QQshareMessage() {
		Tencent mTencent = Tencent.createInstance("1106076127", GameGussingDetailActivity.this);
		ShareListener myListener = new ShareListener();

		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, tv_reviewD_fighterPhotoOne.getText()+"VS"+tv_reviewD_fighterPhotoTwo.getText());
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  game_jincai_result_content.getText().toString());
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  Config.shareUrl);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,Config.QQpictureurl);
		mTencent.shareToQQ(GameGussingDetailActivity.this, params, myListener);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ShareListener myListener = new ShareListener();
		Tencent.onActivityResultData(requestCode,resultCode,data,myListener);
	}
	//QQ空间分享
	private void shareToQzone () {
		ArrayList list=new ArrayList(){};
		list.add(0,Config.QQpictureurl);
//		list.add(0,Config.ip+liveguess_pic.getText());
		Tencent mTencent = Tencent.createInstance("1106076127", GameGussingDetailActivity.this);
		//分享类型
		final Bundle params = new Bundle();
		params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, String.valueOf(SHARE_TO_QZONE_TYPE_IMAGE_TEXT));
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, tv_reviewD_fighterPhotoOne.getText()+"VS"+tv_reviewD_fighterPhotoTwo.getText());//必填
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, game_jincai_result_content.getText().toString());//选填
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, Config.shareUrl);//必填
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
		mTencent.shareToQzone(GameGussingDetailActivity.this, params, new BaseUiListener());
	}
	private class BaseUiListener implements IUiListener {
		public void onComplete(Object response) {
			try {
				showToast("分享成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				showToast("分享失败");
				e.printStackTrace();
			}

		}
		@Override
		public void onError(UiError e) {
			showToast("分享失败");
		}
		@Override
		public void onCancel() {
			showToast("分享失败");
		}
	}

	/**
	 * QQ分享的结果监听
	 */
	private class ShareListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			showToast("分享取消");
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			showToast("分享成功");
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			showToast("分享出错");
		}

	}
	private void shareMessage(String messages) {
		IWXAPI api = WXAPIFactory.createWXAPI(this, null);
		api.registerApp(Config.APP_ID);
		Bitmap bmp= BitmapFactory.decodeResource(getResources(), R.drawable.bisai_share_picture);
		//初始化对象
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl=Config.shareUrl;
//				WXImageObject imgObj=new WXImageObject(bmp);
		WXMediaMessage msg=new WXMediaMessage(webpage);
//				msg.mediaObject=imgObj;
		//设置缩略图
		Bitmap thumbBmp=Bitmap.createScaledBitmap(bmp,150,150,true);
		bmp.recycle();
		msg.thumbData= com.bigdata.xinhuanufang.wxapi.Util.bmpToByteArray(thumbBmp,true);
		msg.title=tv_reviewD_fighterPhotoOne.getText()+"VS"+tv_reviewD_fighterPhotoTwo.getText();
//				msg.title="大声VS的撒";
		msg.description=liveguess_content;
		//构造一个req
		SendMessageToWX.Req req=new SendMessageToWX.Req();
		req.transaction="png"+String.valueOf(System.currentTimeMillis());
		req.message=msg;
		if (messages.equals("微信")) {
			req.scene = SendMessageToWX.Req.WXSceneSession;
		}else if (messages.equals("朋友圈")) {
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		}


		//调用api
		api.sendReq(req);
	}


	private void popupwindowShow() {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.shareshow, null);
		View rootview = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.activity_gussingresultdetail, null);
		//微博分享
		weibo_share = (ImageView) contentView.findViewById(R.id.weibo_share);
		weibo_share.setOnClickListener(this);
		//微信分享
		weixin_share = (ImageView) contentView.findViewById(R.id.weixin_share);
		weixin_share.setOnClickListener(this);
		//朋友圈
		pengyou_share = (ImageView) contentView.findViewById(R.id.pengyou_share);
		pengyou_share.setOnClickListener(this);
		//qq分享
		qq_share = (ImageView) contentView.findViewById(R.id.qq_share);
		qq_share.setOnClickListener(this);
		//qq空间
		kongjian_share = (ImageView) contentView.findViewById(R.id.kongjian_share);
		kongjian_share.setOnClickListener(this);
		popupWindow = new PopupWindow(this);
		popupWindow.setContentView(contentView);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
		popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);


	}

}
