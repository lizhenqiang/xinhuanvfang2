package com.bigdata.xinhuanufang.app;



import android.app.Application;

import com.bigdata.xinhuanufang.utils.Config;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by weiyu$ on 2017/4/9.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

		IWXAPI msgApi = WXAPIFactory.createWXAPI(this, Config.APP_ID);
		msgApi.registerApp(Config.APP_ID);
        //初始化sdk,进行注册
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
//        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
//        Set<String> set = new HashSet<>();
//        set.add("andfixdemo");//名字任意，可多添加几个
//        JPushInterface.setTags(this, set, null);//设置标签
        x.Ext.init(this);
        x.view().inject(this, null);
    }


}
