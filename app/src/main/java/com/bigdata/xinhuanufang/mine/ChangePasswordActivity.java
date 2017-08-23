package com.bigdata.xinhuanufang.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.main.LoginActivity;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author asus 修改密码Activity
 */
public class ChangePasswordActivity extends BaseActivity implements
		OnClickListener {
	private EditText edit_old_password;
	private EditText edit_new_password;
	private EditText enter_password;
	private Button bt_enter_password;
	private SharedPreferences sp;
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_changepassword;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("修改密码");
		super.setGone(); // 将布局右侧图标隐藏
		setBack();
		edit_old_password = (EditText) findViewById(R.id.edit_old_password);
		edit_new_password = (EditText) findViewById(R.id.edit_new_password);
		enter_password = (EditText) findViewById(R.id.enter_password);
		bt_enter_password = (Button) findViewById(R.id.bt_enter_password);
		bt_enter_password.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.bt_enter_password:
				String old = edit_old_password.getText().toString().trim();
				String now = edit_new_password.getText().toString().trim();
				String enter = enter_password.getText().toString().trim();
				if (old.isEmpty()) {
					showToast("旧密码不能为空");
					return;
				}
				if (now.isEmpty()) {
					showToast("新密码不能为空");
					return;
				}
				if (enter.isEmpty()) {
					showToast("确认密码不能为空");
					return;
				}
				if (!now.equals(enter)) {
					showToast("新密码与确认密码不相同");
					return;
				}
				if (now.length() < 6) {
					showToast("密码长度不能少于6位");
					return;
				}
				if (now.length() > 16) {
					showToast("密码长度不能超过16位");
					return;
				}
//				if (now.length() < 8) {
//					showToast("密码长度不能超过16位");
//					return;
//				}
				// http: //
				// 115.28.69.240/boxing/app/user_pass.php?user_id=1&password1=1111&password2=2222&password3=2222
				RequestParams params = new RequestParams(Config.ip + Config.app+ "/user_pass.php?");
				params.addBodyParameter("user_id", Config.userID);
				params.addBodyParameter("password1", MD5Util.getMD5String(old));
				params.addBodyParameter("password2", MD5Util.getMD5String(now));
				params.addBodyParameter("password3", MD5Util.getMD5String(enter));
				x.http().post(params, new CommonCallback<String>() {

					@Override
					public void onCancelled(CancelledException arg0) {
					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
					}

					@Override
					public void onFinished() {
					}

					@Override
					public void onSuccess(String arg0) {

						try {
							JSONObject json=new JSONObject(arg0);
							String code=json.getString("code");
							if (code.equals("1")) {
								showToast("密码修改成功");
								Intent intent=new Intent(ChangePasswordActivity.this, LoginActivity.class);
								SharedPreferences sp=getSharedPreferences("config",0);
								sp.edit().clear().commit();
								startActivity(intent);
								finish();
							}else if (code.equals("0")) {
								showToast("密码修改失败");
							}else if (code.equals("3")) {
								showToast("两次新密码输入不一致");
							}else if (code.equals("4")) {
								showToast("旧密码错误");
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}


						//在这里,当密码修改成功后,紧接着下线,让用户重新登录

						//关闭当前页面


					}
				});
				break;

			default:
				break;
		}
	}
}
