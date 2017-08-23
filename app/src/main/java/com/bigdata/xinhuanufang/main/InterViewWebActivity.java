package com.bigdata.xinhuanufang.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.utils.Config;

/**
 * @author 李智
 * @version V1.0
 * @Title: HotInterViewActivity.java
 * @Package com.bigdata.doctor.activity.room
 * @Description: TODO 节目H5播放详情
 * @date 2016年12月1日11:25:09
 */
public class InterViewWebActivity extends Activity {

    private FrameLayout mFullscreenContainer;
    private FrameLayout mContentView;
    private View mCustomView = null;
    private WebView mWebView;
    private boolean isOnPause;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interviewweb);

        initViews();
        initWebView();

        if (getPhoneAndroidSDK() >= 14) {// 4.0Ðè´ò¿ªÓ²¼þ¼ÓËÙ
            getWindow().setFlags(0x1000000, 0x1000000);
        }
        Intent intent = getIntent();
        String PlayUrl = intent.getStringExtra("PlayUrl");

        mWebView.loadUrl(PlayUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initViews() {
        mFullscreenContainer = (FrameLayout) findViewById(R.id.fullscreen_custom_content);
        mContentView = (FrameLayout) findViewById(R.id.main_content);
        mWebView = (WebView) findViewById(R.id.webview_player);
        //在安全的站点加载非安全站点的内容,在非必要的情况下不要使用这种模式
        WebSettings webSettings = mWebView.getSettings();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if (Config.isSave) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
//		settings.setPluginsEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    class MyWebChromeClient extends WebChromeClient {

        private CustomViewCallback mCustomViewCallback;
        private int mOriginalOrientation = 1;

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            // TODO Auto-generated method stub
            onShowCustomView(view, mOriginalOrientation, callback);
            super.onShowCustomView(view, callback);

        }

        public void onShowCustomView(View view, int requestedOrientation,
                                     WebChromeClient.CustomViewCallback callback) {
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            if (getPhoneAndroidSDK() >= 14) {
                mFullscreenContainer.addView(view);
                mCustomView = view;
                mCustomViewCallback = callback;
                mOriginalOrientation = getRequestedOrientation();
                mContentView.setVisibility(View.INVISIBLE);
                mFullscreenContainer.setVisibility(View.VISIBLE);
                mFullscreenContainer.bringToFront();

                setRequestedOrientation(mOriginalOrientation);
            }

        }

        public void onHideCustomView() {
            mContentView.setVisibility(View.VISIBLE);
            if (mCustomView == null) {
                return;
            }
            mCustomView.setVisibility(View.GONE);
            mFullscreenContainer.removeView(mCustomView);
            mCustomView = null;
            mFullscreenContainer.setVisibility(View.GONE);
            try {
                mCustomViewCallback.onCustomViewHidden();
            } catch (Exception e) {
            }
            // Show the content view.

            setRequestedOrientation(mOriginalOrientation);
        }

    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出视频", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);
//                moveTaskToBack(false);
//				Intent backHome = new Intent(Intent.ACTION_MAIN);
//				backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				backHome.addCategory(Intent.CATEGORY_HOME);
//				startActivity(backHome);
//				ActivityManager manager = (ActivityManager) InterViewWebActivity.this.getSystemService(ACTIVITY_SERVICE); //获取应用程序管理器
//				manager.killBackgroundProcesses(getPackageName()); //强制结束当前应用程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mWebView != null) {
                mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
                isOnPause = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (isOnPause) {
                if (mWebView != null) {
                    mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
                }
                isOnPause = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * onPause和onResume以及下面的方法用于处理在activity退出后,还有声音存在的情况
     * 该处的处理尤为重要:
     * 应该在内置缩放控件消失以后,再执行mWebView.destroy()
     * 否则报错WindowLeaked
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.setVisibility(View.GONE);
            long delayTime = ViewConfiguration.getZoomControlsTimeout();
            //webview的处理必须放在同一个线程当中
//			new Timer().schedule(new TimerTask() {
//				@Override
//				public void run() {
//          mWebView.destroy();
//			mWebView = null;
//				}
//			}, delayTime);
            mWebView.destroy();
            mWebView = null;

        }
        isOnPause = false;
    }

    public static int getPhoneAndroidSDK() {
        // TODO Auto-generated method stub
        int version = 0;
        try {
            version = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return version;

    }
}
