package com.bigdata.xinhuanufang.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bigdata.xinhuanufang.main.MainActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by weiyu$ on 2017/4/15.
 */

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    public static final String APP_SECRET = "53968e01b794e078d378c4cea11bcf0e";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bundle bundle1 = (Bundle) msg.obj;
                    String accessToken = bundle1.getString("access_token");
                    String openId = bundle1.getString("openid");
                    //记录下用户的标识,用于在我的界面进行金手套的刷新
                    Config.SINABIAOSHI=openId;
                    getUID(openId, accessToken);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化api并向微信注册应用。
        api = WXAPIFactory.createWXAPI(this, Config.APP_ID);
        api.registerApp(Config.APP_ID);
        api.handleIntent(getIntent(), this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp instanceof SendAuth.Resp) {
            SendAuth.Resp newResp = (SendAuth.Resp) resp;

            //获取微信传回的code
            String code = newResp.code;
            getResult(code);
            System.out.println("返回值" + code);
        }
//        SendAuth.Resp newResp = (SendAuth.Resp) resp;
////
//            //获取微信传回的code
//            String code1 = newResp.code;
//        int errOk = BaseResp.ErrCode.ERR_OK;
//        String code = ((SendAuth.Resp) resp).code;
//        getResult(code);
    }

    /**
     * 获取openid accessToken值用于后期操作
     *
     * @param code 请求码
     */
    private void getResult(final String code) {
        x.http().get(new RequestParams("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Config.APP_ID + "&secret=" + APP_SECRET + "&code="
                + code
                + "&grant_type=authorization_code"), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String openid = json.getString("openid");
                    String access_token = json.getString("access_token");
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    Bundle bundle = new Bundle();
                    bundle.putString("openid", openid);
                    bundle.putString("access_token", access_token);
                    msg.obj = bundle;
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

    /**
     * 获取用户唯一标识
     *
     * @param openId
     * @param accessToken
     */
    private void getUID(final String openId, final String accessToken) {
        x.http().get(new RequestParams("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    //用户名称
                    String nickname = json.getString("nickname");
                    //性别
                    String sex = json.getString("sex");
                    //用户的头像
                    String headimgurl = json.getString("headimgurl");
                    String unionid = json.getString("unionid");
                    Config.USER_USERNAME = nickname;
                    if (sex.equals("1")) {
                        Config.USER_SEX = "男";
                    } else if (sex.equals("2")) {
                        Config.USER_SEX = "女";
                    }

                    Config.USER_HEAD = headimgurl;
                    Config.weixin_unionid = unionid;
//                    Intent intent=new Intent(WXEntryActivity.this, MainActivity.class);
//                    startActivity(intent);
                    //进行登录的请求
                    //http://115.28.69.240/boxing/app/login.php?user_three=559296&user_flag=1
                    String g=openId;
                    System.out.println("标识"+openId);
                    x.http().get(new RequestParams(Config.ip + Config.app + "/login.php?user_three=" + openId + "&user_flag=" + 0), new CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            try {

                                JSONObject json = new JSONObject(s);
                                String code = json.getString("code");
                                if (code.equals("1")) {
                                    JSONObject js = json.getJSONObject("user");
                                    Config.userID = js.getString("user_id");
                                    Config.RELEVANCE_TYPE = js.getString("user_flag");
                                    Config.USER_ACCOUNT = js.getString("user_three");
                                    Config.USER_TEL = js.getString("user_tel");
                                    Config.USER_GLOVES = js.getString("user_gloves");
                                    String a=Config.USER_GLOVES;
                                    Config.USER_HEAD=js.getString("user_head");
                                    String aa=Config.USER_HEAD;
                                    //这里返回来的用户密码没有解析
                                    Config.USER_SIGN = js.getString("user_sign");
                                    Config.USER_DATE = js.getString("user_date");

                                    Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    System.out.println("获取到的头像地址"+Config.USER_HEAD);
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

}
