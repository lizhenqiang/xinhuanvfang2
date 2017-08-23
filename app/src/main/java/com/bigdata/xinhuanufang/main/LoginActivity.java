package com.bigdata.xinhuanufang.main;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.bean.SinaUserInfo;
import com.bigdata.xinhuanufang.db.DataHelper;
import com.bigdata.xinhuanufang.main.weibo.AccessTokenKeeper;
import com.bigdata.xinhuanufang.main.weibo.UsersAPI;
import com.bigdata.xinhuanufang.model.UserInfo;
import com.bigdata.xinhuanufang.service.LoginService;
import com.bigdata.xinhuanufang.service.to.LoginRsp;
import com.bigdata.xinhuanufang.to.zhuceRule;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.MD5Util;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends Activity implements OnClickListener {
    private Button login_button;    //登录按钮
    public static EditText phoneNumEdit;    //手机号码输入框
    public static EditText passwordEdit;    //密码输入框
    private com.bigdata.xinhuanufang.bean.UserInfo userinfo;
    private String phoneNum;    //手机号码
    private String password;    //密码
    private DataHelper db;
    private SharedPreferences sp;
    private UserInfo user;    //用户信息实体类
    private TextView register_account;
    private LinearLayout ll_register_account;
    private boolean isSuccess = false;//记录是否注册成功;
    private IWXAPI api;
    private double banben=0.00;
    Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int number = msg.what;
            switch (msg.what) {
                case 1:
                    //开启微博登录
                    weiboweibologin();
                    break;
                case 242:
                    //版本检查
                    String version=(String) msg.obj;
                    banben= Double.parseDouble(version);
                    showbanbendialog(banben);
                    break;
                case 20:
                    Toast.makeText(LoginActivity.this, "成功", Toast.LENGTH_LONG).show();
                    break;
                case 21:
                    Toast.makeText(LoginActivity.this, "登陆失败:\n无法连接服务器，请检查您的网络!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    //开启QQ登录
                    QQlogin();
                    break;
                default:
                    break;
            }
        }


    };
    private String verName;

    private void showbanbendialog(double banben) {
        System.out.println("服务器的版本号"+banben);
        System.out.println("服务器--本地的版本号"+verCode);
        if (banben>verCode) {
//            Toast.makeText(this, "去应用宝下载新版本", Toast.LENGTH_SHORT).show();
            //弹出是否去应用宝下载最新版本
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            View view = View.inflate(LoginActivity.this, R.layout.dialog_banben, null);

            //查找控件

            Button btOk = (Button) view.findViewById(R.id.dialog_btn_ok);

            Button btnCancel = (Button) view.findViewById(R.id.dialog_btn_cancel);

            builder.setView(view);

            final AlertDialog dialog = builder.show();

            // 确定按钮

            btOk.setOnClickListener(new OnClickListener() {


                @Override

                public void onClick(View v) {
                    if (isMobile_spExist()) {
                        Uri uri = Uri.parse("market://details?id=" + "com.bigdata.xinhuanufang");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }else{
                        //打开默认浏览器
                        Uri uri = Uri.parse("http://app.qq.com/#id=detail&appid=1106076127");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        dialog.dismiss();
                    }



                }

            });

            // 取消按钮

            btnCancel.setOnClickListener(new OnClickListener() {


                @Override

                public void onClick(View v) {

                    // 关闭窗口

                    dialog.dismiss();

                }

            });





        }
    }

    public boolean isMobile_spExist() {
        PackageManager manager = this.getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.tencent.android.qqdownloader"))
                return true;
        }
        return false;
    }

        private Tencent mTencent;
    private TextView login_lock_rule;
    private double verCode;

    private void QQlogin() {
        //先获取用户信息


        IUiListener userInfoListener = new IUiListener() {

            @Override
            public void onError(UiError arg0) {
                // TODO Auto-generated method stub

            }

            /**
             * 返回用户信息样例
             *
             * {"is_yellow_year_vip":"0","ret":0,
             * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
             * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
             * "city":"黄冈","
             * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
             * "vip":"0","level":"0",
             * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
             * "province":"湖北",
             * "is_yellow_vip":"0","gender":"男",
             * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
             */
            @Override
            public void onComplete(Object arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    int ret = jo.getInt("ret");
                    System.out.println("json=" + String.valueOf(jo));
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");
                    String figureurl = jo.getString("figureurl");
                    Config.USER_HEAD=figureurl;
                    Config.USER_SEX=gender;
                    Config.USER_USERNAME = nickName;

//                    Toast.makeText(LoginActivity.this, "你好，" + nickName,
//                            Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // TODO: handle exception
                }


            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }
        };
        com.tencent.connect.UserInfo userInfo = new com.tencent.connect.UserInfo(LoginActivity.this, mTencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);
        //现在开始登录
        //http://115.28.69.240/boxing/app/login.php?user_three=559296&user_flag=1
        x.http().get(new RequestParams(Config.ip + Config.app + "/login.php?user_three=" + Config.SINABIAOSHI + "&user_flag=1"), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String code = json.getString("code");
                    if (code.equals("1")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        JSONObject js = json.getJSONObject("user");
                        Config.userID = js.getString("user_id");
                        Config.RELEVANCE_TYPE = js.getString("user_flag");
                        Config.USER_ACCOUNT = js.getString("user_three");
                        Config.USER_TEL = js.getString("user_tel");
                        Config.USER_GLOVES = js.getString("user_gloves");
                        String a = Config.USER_GLOVES;
                        //这里返回来的用户密码没有解析
                        Config.USER_SIGN = js.getString("user_sign");
                        Config.USER_DATE = js.getString("user_date");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
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

    private LoginTIme loginTIme;

    private void weiboweibologin() {
        //http://115.28.69.240/boxing/app/login.php?user_three=559296&user_flag=1
        x.http().get(new RequestParams(Config.ip + Config.app + "/login.php?user_three=" + Config.SINABIAOSHI + "&user_flag=2"), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String code = json.getString("code");
                    if (code.equals("1")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        JSONObject js = json.getJSONObject("user");
                        Config.userID = js.getString("user_id");
                        Config.RELEVANCE_TYPE = js.getString("user_flag");
                        Config.USER_ACCOUNT = js.getString("user_three");
                        Config.USER_TEL = js.getString("user_tel");
                        Config.USER_GLOVES = js.getString("user_gloves");
                        String a = Config.USER_GLOVES;
                        //这里返回来的用户密码没有解析
                        Config.USER_SIGN = js.getString("user_sign");
                        Config.USER_DATE = js.getString("user_date");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
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

    private LinearLayout enter_account;
    private TextView return_login;
    private EditText et_login_enter_account;
    private EditText et_login_enter_message;
    private Button get_message_jiaoyan;
    private EditText et_login_enter_password;
    private EditText et_login_enter_two_password;
    private CheckBox register_protocol;
    private CheckBox register_protocol1;
    private Button btn_login_next;
    private TextView tv_itt_save;
    private ImageView iv_itt_delete;
    private ImageView iv_itt_collection;
    private ImageView iv_itt_back;
    private TextView tv_itt_title;
    private TimeCount time;
    private SmsSample message;
    private TextView login_zhaohui_mima;
    private String account;
    private String password1;
    private ImageView qq_login;
    private final String APP_ID = "1106076127";
    private ImageView weixin_login;
    private ImageView weibo_login;
    //新浪微博的
    private SsoHandler mSsoHandler;
    private AuthInfo mAuthInfo;
    private Oauth2AccessToken mAccessToken;
    private SinaUserInfo userInfo;

    private TextView tvbanben;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        db = DataHelper.getInstance(this);

        initView();    //初始化组件方法
        // 1.获取系统服务
        ConnectivityManager cm = (ConnectivityManager) LoginActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 2.获取net信息
        NetworkInfo info = cm.getActiveNetworkInfo();
        // 3.判断网络是否可用
        if (info != null && info.isConnected()) {
        } else {
            Toast.makeText(LoginActivity.this, "网络当前不可用，请检查设置！",
                    Toast.LENGTH_SHORT).show();
        }
        //检查版本号
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verCode = LoginActivity.this.getPackageManager().getPackageInfo(
                    "com.bigdata.xinhuanufang", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        //检查版本名称
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verName = LoginActivity.this.getPackageManager().getPackageInfo(
                    "com.bigdata.xinhuanufang", 0).versionName;
            tvbanben.setText("v " +verName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        getversion(verCode);


        //获取权限
        getpermission();

//        //获取手机型号
//        System.out.println("手机型号1"+ Build.DEVICE);
//        if (Build.DEVICE.equals("hwmt7")) {
//            JPushInterface.resumePush(getApplicationContext());
//        }

    }



    private void getversion(double verCode) {
        //http://47.93.113.190/app/banben.php
        //获取版本号
        RequestParams params=new RequestParams(Config.ip+Config.app+"/banben.php");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json=new JSONObject(s);
                    String banben=json.getString("banben");
                    Message msg=Message.obtain();
                    msg.what=242;
                    msg.obj=banben;
                    loginHandler.sendMessage(msg);
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

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
//                System.exit(0);
//                moveTaskToBack(false);
                Intent backHome = new Intent(Intent.ACTION_MAIN);
                backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                backHome.addCategory(Intent.CATEGORY_HOME);
                startActivity(backHome);

                try {
                    ActivityManager manager = (ActivityManager) LoginActivity.this.getSystemService(ACTIVITY_SERVICE); //获取应用程序管理器
                    manager.killBackgroundProcesses(getPackageName()); //强制结束当前应用程序
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void initView() {
        tvbanben = (TextView) findViewById(R.id.tv_banben);
        // TODO Auto-generated method stub
        login_button = (Button) findViewById(R.id.btn_login_submit);
        loginTIme = new LoginTIme(6000, 1000);
        phoneNumEdit = (EditText) findViewById(R.id.et_login_phoneNum);
        phoneNumEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = phoneNumEdit.getCompoundDrawables()[2];
                if (event.getX() > phoneNumEdit.getWidth()
                        - phoneNumEdit.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    phoneNumEdit.setText("");
                }
                return false;
            }
        });
        passwordEdit = (EditText) findViewById(R.id.et_login_passWorld);
        //注册账户(切换到注册界面)
        register_account = (TextView) findViewById(R.id.register_account);
        register_account.setOnClickListener(this);
        sp = getSharedPreferences("config", 0);
        account = sp.getString("account", "");
        password1 = sp.getString("password", "");
        phoneNumEdit.setText(account);
        passwordEdit.setText(password1);
        login_button.setOnClickListener(this);
        //登录界面的显示
        enter_account = (LinearLayout) findViewById(R.id.enter_account);
        //QQ登录
        qq_login = (ImageView) findViewById(R.id.QQ_login);
        qq_login.setOnClickListener(this);
        //微信登录
        weixin_login = (ImageView) findViewById(R.id.weixin_login);
        weixin_login.setOnClickListener(this);
        //微博登录
        weibo_login = (ImageView) findViewById(R.id.weibo_login);
        weibo_login.setOnClickListener(this);
        //切换到注册界面
        return_login = (TextView) findViewById(R.id.return_login);
        return_login.setOnClickListener(this);
        //标题头
        tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
        tv_itt_save.setVisibility(View.GONE);
        iv_itt_delete = (ImageView) findViewById(R.id.iv_itt_delete);
        iv_itt_delete.setVisibility(View.GONE);
        iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
        iv_itt_collection.setVisibility(View.GONE);
        iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
        iv_itt_back.setVisibility(View.GONE);
        tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
        tv_itt_title.setText("心花怒放");


        //注册界面的显示
        ll_register_account = (LinearLayout) findViewById(R.id.ll_register_account);
        //手机号
        et_login_enter_account = (EditText) findViewById(R.id.et_login_enter_account);
        et_login_enter_account.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = et_login_enter_account.getCompoundDrawables()[2];
                if (event.getX() > et_login_enter_account.getWidth()
                        - et_login_enter_account.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    et_login_enter_account.setText("");
                }
                return false;
            }
        });
        //验证码
        et_login_enter_message = (EditText) findViewById(R.id.et_login_enter_message);
        //获取验证码
        get_message_jiaoyan = (Button) findViewById(R.id.get_message_jiaoyan);
        get_message_jiaoyan.setOnClickListener(this);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        //填写密码
        et_login_enter_password = (EditText) findViewById(R.id.et_login_enter_password);
        //确认密码
        et_login_enter_two_password = (EditText) findViewById(R.id.et_login_enter_two_password);
        //是否阅读注册协议
        register_protocol1 = (CheckBox) findViewById(R.id.register_protocol);
        //下一步
        btn_login_next = (Button) findViewById(R.id.btn_login_next);
        btn_login_next.setOnClickListener(this);
        //忘记密码
        login_zhaohui_mima = (TextView) findViewById(R.id.login_zhaohui_mima);
        login_zhaohui_mima.setOnClickListener(this);
        //查看注册条款
        login_lock_rule = (TextView) findViewById(R.id.login_lock_rule);
        login_lock_rule.setOnClickListener(this);



        //进行自动登录
//        if (phoneNumEdit.getText().equals("-1")) {
//            return;
//        }
//        passwordEdit.getText().equals("-1");

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_lock_rule:
                Intent webview_rule=new Intent(LoginActivity.this, zhuceRule.class);
                startActivity(webview_rule);
                break;
            case R.id.login_zhaohui_mima:
                //跳转到忘记密码的布局
                Intent intent = new Intent(this, ForgetPassword.class);
                startActivity(intent);
                break;
            case R.id.btn_login_submit:
                loginTIme.start();
                //拿到用户输入的用户名和密码
                phoneNum = phoneNumEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if (null == phoneNum || "".equals(phoneNum)) {
                    Toast.makeText(LoginActivity.this, "请输入手机号码",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null == password || "".equals(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                //开启一个新的线程去做登录操作
                if (phoneNum.equals(account)) {
                    if (!password1.equals(password)) {
                        Logincount(phoneNum, MD5Util.getMD5String(password), "1");
                    } else if (password1.equals(password)) {
                        Logincount(phoneNum, password, "1");
                    }
                }
                if (!phoneNum.equals(account)) {
                    Logincount(phoneNum, MD5Util.getMD5String(password), "1");
                }
                break;

            case R.id.register_account:
                //注册账户
                enter_account.setVisibility(View.GONE);
                ll_register_account.setVisibility(View.VISIBLE);
                phoneNumEdit.setText("");
                passwordEdit.setText("");
                tv_itt_title.setText("注册");
                break;
            case R.id.return_login:
                //进行登录
                enter_account.setVisibility(View.VISIBLE);
                ll_register_account.setVisibility(View.GONE);
                tv_itt_title.setText("心花怒放");
                break;
            case R.id.btn_login_next:

                if (et_login_enter_account == null || "".equals(et_login_enter_account.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_login_enter_message == null || "".equals(et_login_enter_message.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_login_enter_password == null || "".equals(et_login_enter_password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_login_enter_two_password == null || "".equals(et_login_enter_two_password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "再次确认密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!et_login_enter_password.getText().toString().equals(et_login_enter_two_password.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!register_protocol1.isChecked()) {
                    Toast.makeText(LoginActivity.this, "请阅读条款", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!et_login_enter_message.getText().toString().equals(String.valueOf(message.getRandom()))) {
                    Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                //进行注册
                registerAccount();

                break;
            case R.id.get_message_jiaoyan:
                if (et_login_enter_account == null || "".equals(et_login_enter_account.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                //进行倒计时
                time.start();
                //获取验证码
                getIsCorrect(et_login_enter_account.getText().toString());

                break;
            case R.id.QQ_login:
                LoginListener myListener = new LoginListener();
                mTencent = Tencent.createInstance(APP_ID, this.getApplicationContext());
                if (!mTencent.isSessionValid()) {
                    mTencent.login(this, "all", myListener);
                }
                break;
            case R.id.weixin_login:
                //api注册
                api = WXAPIFactory.createWXAPI(this, Config.APP_ID, true);
                api.registerApp(Config.APP_ID);
                if (!api.isWXAppInstalled()) {
                    Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
                    return;
                }
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo_test";
                api.sendReq(req);
                break;
            case R.id.weibo_login:
                //微博登录

                mAuthInfo = new AuthInfo(LoginActivity.this,
                        Config.SINA_APPKEY, Config.SINA_REDIRECT_URL,
                        Config.SINA_SCOPE);
                mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
                mSsoHandler.authorize(new AuthListener());
//                mSsoHandler.authorizeClientSso(new AuthListener());
                break;
            default:
                break;
        }


    }




    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     * http://115.28.69.240/boxing/app/login.php?user_three=559296&user_flag=1
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息
            String token = values.getString("access_token");
            String expires_in = values.getString("expires_in");
            // 唯一标识符(uid)
            final String idstr = values.getString("uid");
            if (mAccessToken.isSessionValid()) {
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken); // 保存Token
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        UsersAPI usersAPI = new UsersAPI(LoginActivity.this, Config.SINA_APPKEY, mAccessToken);
                        usersAPI.show(Long.valueOf(mAccessToken.getUid()), new SinaRequestListener());

                    }
                }).start();

            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                if (!TextUtils.isEmpty(code)) {
                }
                Toast.makeText(LoginActivity.this, "信息有误" + code, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    "用户取消", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(LoginActivity.this,
                    "Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public class SinaRequestListener implements RequestListener {
        @Override
        public void onComplete(String response) {// TODO Auto-generated method stub
            try {
                JSONObject jsonObject = new JSONObject(response);
                String idStr = jsonObject.getString("idstr");// 唯一标识符(uid)
                Config.SINABIAOSHI = idStr;
                String name = jsonObject.getString("name");// 姓名
                Config.USER_USERNAME = name;
                String avatarHd = jsonObject.getString("avatar_hd");// 头像
                Config.USER_HEAD = avatarHd;
                userInfo = new SinaUserInfo();
                userInfo.setUid(idStr);
                userInfo.setName(name);
                userInfo.setAvatarHd(avatarHd);
                Message message = Message.obtain();
                message.what = 1;
                loginHandler.sendMessage(message);
            } catch (JSONException e) {// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {// TODO Auto-generated method stubToast.makeText(context, "Auth exception : " + e.getMessage(),Toast.LENGTH_LONG).show();
            Log.i("mylog", "Auth exception : " + e.getMessage());
        }
    }


    private void getIsCorrect(final String phonenumber) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                message = new SmsSample(phonenumber);
            }
        }).start();

    }

    /**
     * 注册账号
     */
    private void registerAccount() {
        //http://115.28.69.240/boxing/app/register.php?user_tel=18519011618&user_pwd=666666
        x.http().get(new RequestParams(Config.ip + Config.app + "/register.php?user_tel=" + et_login_enter_account.getText().toString() + "&user_pwd=" + MD5Util.getMD5String(et_login_enter_password.getText().toString())), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject json = new JSONObject(s);
                    String code = json.getString("code");
                    if (code.equals("0")) {
                        Toast.makeText(LoginActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("1")) {
                        Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        //注册成功
//						enter_account.setVisibility(View.VISIBLE);
//						ll_register_account.setVisibility(View.GONE);
                        Logincount(et_login_enter_account.getText().toString(), MD5Util.getMD5String(et_login_enter_password.getText().toString()), "0");
                        //跳转到完善资料
//                        Intent intent = new Intent(LoginActivity.this, com.bigdata.xinhuanufang.main.UserInfo.class);
//                        startActivity(intent);
//                        finish();
                    } else if (code.equals("3")) {
                        Toast.makeText(LoginActivity.this, "该手机号已经注册过了", Toast.LENGTH_SHORT).show();
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

    private void Logincount(String phoneNum, String password, final String tag) {
        //http://115.28.69.240/boxing/app/login.php?user_tel=18519011617&user_pwd=F379EAF3C831B04DE153469D1BEC345E
        String s = Config.ip + Config.app + "/login.php?user_tel=" + phoneNum + "&user_pwd=" + password;
        System.out.println("登录链接"+Config.ip + Config.app + "/login.php?user_tel=" + phoneNum + "&user_pwd=" + password);
        x.http().get(new RequestParams(Config.ip + Config.app + "/login.php?user_tel=" + phoneNum + "&user_pwd=" + password), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                try {
                    JSONObject js = new JSONObject(s);

                    String code = js.getString("code");
                    if (code.equals("0")) {
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (code.equals("1")) {
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
                        userinfo = new com.bigdata.xinhuanufang.bean.UserInfo(user_id, user_delete, user_tel, user_gloves, user_head, user_username, user_pwd, user_sex, user_sign, user_date);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("account", user_tel);
                        edit.putString("password", user_pwd);
                        edit.commit();
                        Config.userID = user_id;
                        Config.USER_GLOVES = user_gloves;
                        Config.USER_TEL = user_tel;
                        Config.USER_HEAD = user_head;
                        Config.USER_USERNAME = user_username;
                        Config.USER_SIGN = user_sign;
                        Config.USER_SEX = user_sex;


                        if (tag.equals("1")) {
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else if (tag.equals("0")) {
                            Intent intent = new Intent(LoginActivity.this, com.bigdata.xinhuanufang.main.UserInfo.class);
                            startActivity(intent);
                            finish();
                        }

                    } else if (code.equals("3")) {
                        Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                Toast.makeText(x.app(), "网络异常", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /*
     * 返回登录状态方法
     * */
    public boolean loginValid(String phoneNum, String password) {

        boolean loginState = false;
        LoginService service = new LoginService();
        try {
            LoginRsp loginRsp = service.login(phoneNum, password,
                    LoginActivity.this);


            if ("0".equals(loginRsp.getCode())) {
                loginState = false;
            } else if ("1".equals(loginRsp.getCode())) {
                loginState = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            // String a = e.toString();
        }
        return loginState;

        /**
         * 验证通过则保存用户和密码
         */
    }

    class LoginFailureHandler implements Runnable {

        @Override
        public void run() {
            //拿到用户输入的用户名、密码
            phoneNum = phoneNumEdit.getText().toString();
            password = passwordEdit.getText().toString();
            //loginValid 返回登录状态  false or true
            boolean loginState = loginValid(phoneNum, password);
            if (loginState) {
                SharedPreferences sharedPrefs = getSharedPreferences(
                        "user_preferences", Context.MODE_PRIVATE);
                Editor editor = sharedPrefs.edit();
                editor.putString("phoneNum", phoneNum);
                editor.putString("password", password);
                editor.commit();

                loginHandler.sendEmptyMessage(20);
            } else {
                loginHandler.sendEmptyMessage(21);
            }
        }

    }

    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            get_message_jiaoyan.setText("重新验证");
            get_message_jiaoyan.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            get_message_jiaoyan.setClickable(false);
            get_message_jiaoyan.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    /**
     * 判断某一个时间段,只能点击一次
     */
/* 定义一个倒计时的内部类 */
    class LoginTIme extends CountDownTimer {
        public LoginTIme(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            login_button.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            login_button.setClickable(false);
        }
    }

    private class LoginListener implements IUiListener {

        private String openid;

        @Override
        public void onCancel() {
            // TODO Auto-generated method stub
            Toast.makeText(LoginActivity.this, "登录取消", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(Object arg0) {
            if (arg0 == null) {
                return;
            }

            try {
                JSONObject jo = (JSONObject) arg0;

                int ret = jo.getInt("ret");

                System.out.println("json=" + String.valueOf(jo));

                if (ret == 0) {
                    Toast.makeText(LoginActivity.this, "登录成功",
                            Toast.LENGTH_LONG).show();

                    String openID = jo.getString("openid");
                    String accessToken = jo.getString("access_token");
                    String expires = jo.getString("expires_in");
                    mTencent.setOpenId(openID);
                    mTencent.setAccessToken(accessToken, expires);

                    Message msg=Message.obtain();
                    msg.what=6;
                    msg.obj=openID;
                    loginHandler.sendMessage(msg);
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        @Override
        public void onError(UiError arg0) {
            // TODO Auto-generated method stub
            Toast.makeText(LoginActivity.this, "登录出错", Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LoginListener myListener = new LoginListener();
        Tencent.onActivityResultData(requestCode, resultCode, data, myListener);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    public void getpermission(){
        //获取存储的权限
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE}, 120);
        }
    }
}
