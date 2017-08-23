package com.bigdata.xinhuanufang.mine;

import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.utils.Config;

/**
 * @author asus
 * 关于我们Activity
 */
public class AboutUsActivity extends BaseActivity{
	private WebView about_me_webview;
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_aboutus;
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("关于我们");
		super.setGone();
		super.setBack();
		about_me_webview=(WebView) findViewById(R.id.about_me_webview);
		about_me_webview.getSettings().setSupportZoom(true);
		about_me_webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		about_me_webview.getSettings().setLoadWithOverviewMode(true);
		about_me_webview.getSettings().setUseWideViewPort(true);
		about_me_webview.loadUrl(Config.AboutMe);
	}
}
