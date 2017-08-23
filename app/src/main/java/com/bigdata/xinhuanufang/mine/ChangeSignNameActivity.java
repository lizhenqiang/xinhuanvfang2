package com.bigdata.xinhuanufang.mine;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;


/**
 * @author asus 修改个性签名activity
 */
public class ChangeSignNameActivity extends BaseActivity {

	private EditText ed_qianming;
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_changesignname;
	}

	@Override
	public void initView() {
		Intent intent=getIntent();
		String sign=intent.getStringExtra("sign");
		setTextRight("保存");
		setTitle("修改个性签名");
		setBack();
		// 签名
		ed_qianming= (EditText) findViewById(R.id.ed_qianming);
		ed_qianming.setText(sign);
		ed_qianming.setSelection(ed_qianming.getText().length());
		tv_itt_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content=ed_qianming.getText().toString();
				Config.USER_SIGN=content;
				if (!content.isEmpty()) {
					//发送数据
					x.http().get(
							new RequestParams(com.bigdata.xinhuanufang.utils.Config.ip
									+ com.bigdata.xinhuanufang.utils.Config.app
									+ "/user_edit.php?user_id="
									+ com.bigdata.xinhuanufang.utils.Config.userID
									+ "&status=5&xxxxx=" + content),
							new CommonCallback<String>() {
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
									String success="1";
									String fail="0";
									try {
										JSONObject json=new JSONObject(arg0);
										String code=json.getString("code");
										if (success.equals(code)) {
											showToast("签名设置完毕");

										}
										if (fail.equals(code)) {
											showToast("签名设置失败,请检查网络");
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
					Intent intent=new Intent();
					intent.putExtra("content", content);
					setResult(3, intent);
					finish();
				}
			}
		});
	}
}
