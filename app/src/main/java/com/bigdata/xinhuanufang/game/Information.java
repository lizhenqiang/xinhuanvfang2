package com.bigdata.xinhuanufang.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.utils.Config;

public class Information extends Activity implements OnClickListener {
	private WebView webview;
	private String id;

	private ImageView info_back;
	private TextView info_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_information);
		Bundle bundle=new Bundle();
		bundle=this.getIntent().getExtras();
		id=bundle.getString("news_id");
		initview();
		initweb();


	}



	private void initview() {
		// TODO Auto-generated method stub
		webview=(WebView) findViewById(R.id.web);
		info_back=(ImageView) findViewById(R.id.info_back);
		info_title=(TextView) findViewById(R.id.info_title);
		info_title.setText("资讯详情");
		info_back.setOnClickListener(this);


	}



	public void initweb() {
		// TODO Auto-generated method stub
		//http://115.28.69.240/boxing/app/news_show.php?news_id=1
//		webview=new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		try {
			Log.e("aa", id);
			webview.loadUrl(Config.ip+Config.app+"/news_show.php?news_id="+id);
			Log.e("url", Config.ip+Config.app+"/news_show.php?news_id="+id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.info_back:
				finish();
				break;

			default:
				break;
		}

	}

}
