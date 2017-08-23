package com.bigdata.xinhuanufang.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * @author asus
 * 修改昵称Activity
 */
public class ChangeNameActivity extends Activity {
	//	@Override
//	public int getView() {
//		// TODO Auto-generated method stub
//		return R.layout.activity_changename;
//	}
//	@Override
//	public void initView() {
//		// TODO Auto-generated method stub
//		setTextRight("保存"); //修改title右侧文字
//		setTitle("修改昵称"); //修改Title
//		setBack();
//		EditText edit_name=(EditText) findViewById(R.id.edit_name);
//
//	}
	private ImageView iv_itt_back; // title布局返回ImageView

	private TextView tv_itt_title; // title布局标题TextView

	public TextView tv_itt_save; // title布局保存TextView

	private EditText edit_name;//昵称修改的内容

	private ImageView iv_itt_collection; //title布局收藏ImageView
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changename);
		Intent intent=getIntent();
		String name=intent.getStringExtra("name");

		edit_name=(EditText) findViewById(R.id.edit_name);
		edit_name.setText(name);
		edit_name.setSelection(edit_name.getText().length());
		tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
		iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
		tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
		iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
		tv_itt_title.setText("修改昵称");
		tv_itt_save.setText("保存");
		iv_itt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_itt_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=edit_name.getText().toString();
				Config.USER_USERNAME=name;
				if (!name.isEmpty()) {
					//发送数据
					x.http().get(
							new RequestParams(com.bigdata.xinhuanufang.utils.Config.ip
									+ com.bigdata.xinhuanufang.utils.Config.app
									+ "/user_edit.php?user_id="
									+ com.bigdata.xinhuanufang.utils.Config.userID
									+ "&status=2&xxxxx=" + name),
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
											Toast.makeText(ChangeNameActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
										}
										if (fail.equals(code)) {
											Toast.makeText(ChangeNameActivity.this, "保存失败,请检查网络", Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							});
					Intent intent=new Intent();
					intent.putExtra("name", name);
					ChangeNameActivity.this.setResult(2, intent);
					ChangeNameActivity.this.finish();
				}
			}
		});
	}
}
