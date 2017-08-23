package com.bigdata.xinhuanufang.to;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bigdata.xinhuanufang.R;

/**
 * 注册页面的协议
 */
public class zhuceRule extends Activity {

    private WebView zhuce_webview_rule;
    private ImageView zhuce_black;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zhuce_rule);
        initview();
    }

    private void initview() {
        zhuce_black = (ImageView) findViewById(R.id.zhuce_black);
        zhuce_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        zhuce_webview_rule = (WebView) findViewById(R.id.zhuce_webview_rule);
//        zhuce_webview_rule.loadUrl(Config.zhuce_rule);
    }
}
