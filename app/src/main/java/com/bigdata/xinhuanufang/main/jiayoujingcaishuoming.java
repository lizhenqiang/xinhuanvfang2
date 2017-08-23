package com.bigdata.xinhuanufang.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bigdata.xinhuanufang.R;

/**加油竞猜规则
 * Created by weiyu$ on 2017/6/1.
 */

public class jiayoujingcaishuoming extends Activity {
    private ImageView super_dream_black;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.jiayoujingcai_item);
        super_dream_black = (ImageView) findViewById(R.id.super_dream_black);
        super_dream_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
