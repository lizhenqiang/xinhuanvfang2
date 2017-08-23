package com.bigdata.xinhuanufang.to;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bigdata.xinhuanufang.R;

/**
 * 超级梦想规则
 */
public class SuperDreamRule extends Activity {

    private WebView superfream_webview;
    private ImageView super_dream_black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_super_dream_rule);
        super_dream_black = (ImageView) findViewById(R.id.super_dream_black);
        super_dream_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        superfream_webview = (WebView) findViewById(R.id.superfream_webview);
//        superfream_webview.loadUrl(Config.superdream_rule);
    }
}
