package com.bigdata.xinhuanufang.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by weiyu$ on 2017/4/12.
 */

public class ForgetPassword extends Activity implements View.OnClickListener {
    private TextView tv_itt_save;
    private ImageView iv_itt_delete;
    private ImageView iv_itt_collection;
    private ImageView iv_itt_back;
    private TextView tv_itt_title;
    private EditText et_login_phone_number;
    private EditText et_login_yanzhengma;
    private Button get_message_get_jiaoyan;
    private EditText et_login_new_password;
    private EditText et_login_new_two_password;
    private Button btn_login_xiugai;
    private TimeCount time;
    private SmsSample message;
    private String getjiaoyan=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget_password);
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

        et_login_phone_number = (EditText) findViewById(R.id.et_login_phone_number);
        et_login_yanzhengma = (EditText) findViewById(R.id.et_login_yanzhengma);
        get_message_get_jiaoyan = (Button) findViewById(R.id.get_message_get_jiaoyan);
        et_login_new_password = (EditText) findViewById(R.id.et_login_new_password);
        et_login_new_two_password = (EditText) findViewById(R.id.et_login_new_two_password);
        btn_login_xiugai = (Button) findViewById(R.id.btn_login_xiugai);
        time = new TimeCount(60000, 1000);//构造CountDownTimer对象
        get_message_get_jiaoyan.setOnClickListener(this);
        btn_login_xiugai.setOnClickListener(this);
    }
    /* 定义一个倒计时的内部类 */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            get_message_get_jiaoyan.setText("重新验证");
            get_message_get_jiaoyan.setClickable(true);
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            get_message_get_jiaoyan.setClickable(false);
            get_message_get_jiaoyan.setText(millisUntilFinished /1000+"秒");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_message_get_jiaoyan:
                //获取验证码
                if (et_login_phone_number==null||"".equals(et_login_phone_number.getText().toString())) {
                    Toast.makeText(ForgetPassword.this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                getjiaoyan="1";
                //进行倒计时
                time.start();
                //获取验证码
                getIsCorrect(et_login_phone_number.getText().toString());
                break;
            case R.id.btn_login_xiugai:
                if (et_login_phone_number==null||"".equals(et_login_phone_number.getText().toString())) {
                    Toast.makeText(ForgetPassword.this,"手机号码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_login_yanzhengma==null||"".equals(et_login_yanzhengma.getText().toString())) {
                    Toast.makeText(ForgetPassword.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_login_new_password==null||"".equals(et_login_new_password.getText().toString())) {
                    Toast.makeText(ForgetPassword.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (et_login_new_two_password==null||"".equals(et_login_new_two_password.getText().toString())) {
                    Toast.makeText(ForgetPassword.this,"再次确认密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!et_login_new_password.getText().toString().equals(et_login_new_two_password.getText().toString())) {
                    Toast.makeText(ForgetPassword.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (message!=null) {
                    if (!et_login_yanzhengma.getText().toString().equals(String.valueOf(message.getRandom()))) {
                        Toast.makeText(ForgetPassword.this,"验证码错误",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (getjiaoyan==null) {
                    Toast.makeText(ForgetPassword.this,"请获取验证码",Toast.LENGTH_SHORT).show();
                    return;
                }
                // 115.28.69.240/boxing/app/user_pass.php?user_id=1&password1=1111&password2=2222&password3=2222
                /**
                 * http://115.28.69.240/boxing/app/user_forget.php?tel=18519011617&password1=111&password2=111
                 传递参数
                 tel电话
                 password1新密码第一次
                 password2新密码第二次
                 code值
                 0失败 1成功 2参数错误 3两次新密码输入不一致
                 返回值
                 {"code":1}
                 */
                RequestParams params = new RequestParams(Config.ip + Config.app+ "/user_forget.php?");
                params.addBodyParameter("tel", et_login_phone_number.getText().toString());
                params.addBodyParameter("password1", MD5Util.getMD5String(et_login_new_password.getText().toString()));
                params.addBodyParameter("password2", MD5Util.getMD5String(et_login_new_two_password.getText().toString()));
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String s) {
                        try {
                            JSONObject json=new JSONObject(s);
                            String code=json.getString("code");
                            System.out.println("修改密码"+code);
                            if (code.equals("0")) {
                                Toast.makeText(ForgetPassword.this,"修改失败",Toast.LENGTH_SHORT).show();
                            }else if (code.equals("1")) {
                                Toast.makeText(ForgetPassword.this,"修改成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else if (code.equals("2")) {
                                Toast.makeText(ForgetPassword.this,"参数错误",Toast.LENGTH_SHORT).show();
                            }else if (code.equals("4")) {
                                Toast.makeText(ForgetPassword.this,"账号不存在",Toast.LENGTH_SHORT).show();
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
                break;
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
}
