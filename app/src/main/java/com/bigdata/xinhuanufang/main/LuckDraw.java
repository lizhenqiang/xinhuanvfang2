package com.bigdata.xinhuanufang.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.main.weibo.AccessTokenKeeper;
import com.bigdata.xinhuanufang.utils.Config;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.constant.WBConstants;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.tencent.connect.share.QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT;

/**
 * 抽奖游戏
 */
@SuppressLint("SetJavaScriptEnabled")
public class LuckDraw extends Activity implements OnClickListener,IWeiboHandler.Response {
	public static final int SHARE_CLIENT = 1;
	public static final int SHARE_ALL_IN_ONE = 2;
	private WebView luck_draw_webview;
	private PopupWindow popupWindow;
	private ImageView weibo_share;
	private ImageView weixin_share;
	private ImageView pengyou_share;
	private ImageView qq_share;
	private AuthInfo mAuthInfo;
	/** 微博微博分享接口实例 */
	private IWeiboShareAPI mWeiboShareAPI = null;
	private int mShareType = SHARE_CLIENT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment_luckdraw);
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
		if (savedInstanceState != null) {
			mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
		}
		TextView tv_itt_title=(TextView) findViewById(R.id.tv_itt_title);
		tv_itt_title.setText("抽奖游戏");
		ImageView iv_itt_back=(ImageView) findViewById(R.id.iv_itt_back);
		iv_itt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		ImageView iv_itt_collection=(ImageView) findViewById(R.id.iv_itt_collection);
		iv_itt_collection.setVisibility(View.VISIBLE);
		iv_itt_collection.setImageResource(R.drawable.fenxiang);
		iv_itt_collection.setOnClickListener(this);
		ImageView iv_itt_delete=(ImageView) findViewById(R.id.iv_itt_delete);
		iv_itt_delete.setVisibility(View.GONE);
		TextView tv_itt_save=(TextView) findViewById(R.id.tv_itt_save);
		tv_itt_save.setVisibility(View.GONE);
		luck_draw_webview = (WebView) findViewById(R.id.luck_draw_webview);
		// http://115.28.69.240/boxing/dazhuanpan/index.php?user_id=1
		luck_draw_webview.loadUrl(Config.ip + "/dazhuanpan/index.php?user_id="
				+ Config.userID);
		luck_draw_webview.getSettings().setJavaScriptEnabled(true);
		luck_draw_webview.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.iv_itt_collection:
				popupwindowShow();
				break;
			case R.id.weibo_share:
				weiboshareMessage();
				break;
			case R.id.weixin_share:
				shareMessage("微信");
				break;
			case R.id.pengyou_share:
				shareMessage("朋友圈");
				break;
			case R.id.qq_share:
				QQshareMessage();
				break;
			case R.id.kongjian_share:
				shareToQzone();
				break;
		}
	}

	private void weiboshareMessage() {
		sendMessage(true,true,false,false,false,false);
	}

	//QQ空间分享
	private void shareToQzone () {
		ArrayList list=new ArrayList(){};
		list.add(0,Config.QQpictureurl);
//		list.add(0,Config.ip+liveguess_pic.getText());
		Tencent mTencent = Tencent.createInstance("1106076127", LuckDraw.this);
		//分享类型
		final Bundle params = new Bundle();
		params.putString(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, String.valueOf(SHARE_TO_QZONE_TYPE_IMAGE_TEXT));
		params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "看直播，猜胜负,砸金蛋，让你心花怒放！");//必填
		params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "看直播，猜胜负,砸金蛋，让你心花怒放！");//选填
		params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, Config.shareUrl);//必填
		params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
		mTencent.shareToQzone(LuckDraw.this, params, new BaseUiListener());
	}
	/**
	 * 微博分享
	 * @param intent
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
		// 来接收微博客户端返回的数据；执行成功，返回 true，并调用
		// {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
		mWeiboShareAPI.handleWeiboResponse(intent, this);
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
		mWeiboShareAPI.sendRequest(LuckDraw.this, request);
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
		textObject.text = "砸金蛋";
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
			mWeiboShareAPI.sendRequest(LuckDraw.this, request);
		}
		else if (mShareType == SHARE_ALL_IN_ONE) {
			AuthInfo authInfo = new AuthInfo(LuckDraw.this,
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

	/**
	 * 微博分享
	 * @param baseResponse
	 */
	@Override
	public void onResponse(BaseResponse baseResponse) {
		if(baseResponse!= null){
			switch (baseResponse.errCode) {
				case WBConstants.ErrorCode.ERR_OK:
					Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
					break;
				case WBConstants.ErrorCode.ERR_CANCEL:
					Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
					break;
				case WBConstants.ErrorCode.ERR_FAIL:
					Toast.makeText(this,
							"分享失败" + "Error Message: " + baseResponse.errMsg,
							Toast.LENGTH_LONG).show();
					break;
			}
		}
	}

	private class BaseUiListener implements IUiListener {
		public void onComplete(Object response) {
			try {
				Toast.makeText(LuckDraw.this, "分享成功", Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(LuckDraw.this, "分享失败", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

		}
		@Override
		public void onError(UiError e) {
			Toast.makeText(LuckDraw.this, "分享失败", Toast.LENGTH_SHORT).show();
		}
		@Override
		public void onCancel() {
			Toast.makeText(LuckDraw.this, "分享失败", Toast.LENGTH_SHORT).show();
		}
	}


	//QQ好友分享
	private void QQshareMessage() {
		Tencent mTencent = Tencent.createInstance("1106076127", LuckDraw.this);
		ShareListener myListener = new ShareListener();
		final Bundle params = new Bundle();
		params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "看直播，猜胜负,砸金蛋，让你心花怒放！");
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  "看直播，猜胜负,砸金蛋，让你心花怒放！");
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  Config.shareUrl);
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,Config.QQpictureurl);
		mTencent.shareToQQ(LuckDraw.this, params, myListener);
	}
	/**
	 * QQ分享的结果监听
	 */
	private class ShareListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Toast.makeText(LuckDraw.this, "分享取消", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(LuckDraw.this, "分享成功", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(LuckDraw.this, "分享出错", Toast.LENGTH_SHORT).show();
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
		msg.title="看直播，猜胜负,砸金蛋，让你心花怒放！";
//				msg.title="大声VS的撒";
		msg.description="看直播，猜胜负,砸金蛋，让你心花怒放！";
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
		ImageView kongjian_share = (ImageView) contentView.findViewById(R.id.kongjian_share);
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
