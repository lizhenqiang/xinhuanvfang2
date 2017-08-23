package com.bigdata.xinhuanufang.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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
 * /** 编辑地址activity
 */

public class EditAddress extends Activity {

	private ImageView iv_itt_back; // title布局返回ImageView

	private TextView tv_itt_title; // title布局标题TextView

	public TextView tv_itt_save; // title布局保存TextView

	private ImageView iv_itt_collection; // title布局收藏ImageView
	private EditText address_input_name;
	private EditText address_input_tel;
	private EditText address_input_postcode;
	private EditText address_input_content;
	private String address_id;
	private String name;
	private String tel;
	private String postcode;
	private String content;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editadress);
		Intent intent = getIntent();
		/**
		 * intent.putExtra("address_id", tempPosition); intent.putExtra("name",
		 * name); intent.putExtra("tel", tel); intent.putExtra("postcode",
		 * postcode); intent.putExtra("content", content);
		 */
		address_id = intent.getStringExtra("address_id");
		name = intent.getStringExtra("name");
		tel = intent.getStringExtra("tel");
		postcode = intent.getStringExtra("postcode");
		index = intent.getIntExtra("index", index);
		content = intent.getStringExtra("content");
		tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
		iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
		tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
		iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
		address_input_name = (EditText) findViewById(R.id.address_input_name);
		address_input_tel = (EditText) findViewById(R.id.address_input_tel);
		address_input_postcode = (EditText) findViewById(R.id.address_input_postcode);
		address_input_content = (EditText) findViewById(R.id.address_input_content);
		address_input_name.setText(name);
		address_input_name.setSelection(name.length());
		address_input_tel.setText(tel);
		address_input_postcode.setText(postcode);
		address_input_content.setText(content);
		iv_itt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("name", name);
				intent.putExtra("tel", tel);
				intent.putExtra("postcode", postcode);
				intent.putExtra("content", content);
				intent.putExtra("index", index);
				setResult(2, intent);
				finish();
			}
		});
		initData();
		initView();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Intent intent = new Intent();
		intent.putExtra("name", name);
		intent.putExtra("tel", tel);
		intent.putExtra("postcode", postcode);
		intent.putExtra("content", content);
		intent.putExtra("index", index);
		setResult(2, intent);
		return super.onKeyDown(keyCode, event);
	}

	public void initData() {

	}

	public void initView() {
		// TODO Auto-generated method stub
		tv_itt_save.setText("保存");
		tv_itt_title.setText("编辑地址");

		tv_itt_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * http://115.28.69.240/boxing/app/address_edit.php?address_id=5
				 * &name=%E5%BC%A0%E9%9B%A8%E6%AC%A3&tel=18519011618&postcode=
				 * 182900
				 * &content=%E5%8C%97%E4%BA%AC%E5%B8%82%E6%B5%B7%E6%B7%80%E5
				 * %8C%BA
				 * %E4%B8%8A%E5%9C%B0%E5%8D%97%E8%B7%AF%E7%A7%91%E8%B4%B8%E5
				 * %A4%A7%E5%8E%A6409
				 *
				 * http://115.28.69.240/boxing/app/address_edit.php?&address_id=
				 * null&name=九爷&tel=18519011617&postcode=100390&content=
				 * 北京市海淀区上地南路科贸大厦408
				 */
				String name = address_input_name.getText().toString();
				String tel = address_input_tel.getText().toString();
				String postcode = address_input_postcode.getText().toString();
				String content = address_input_content.getText().toString();
				Intent intent = new Intent();
				intent.putExtra("name", name);
				intent.putExtra("tel", tel);
				intent.putExtra("postcode", postcode);
				intent.putExtra("content", content);
				intent.putExtra("index", index);
				setResult(2, intent);
				RequestParams params = new RequestParams(Config.ip + Config.app
						+ "/address_edit.php?");
				params.addBodyParameter("address_id", address_id);
				params.addBodyParameter("name", name);
				params.addBodyParameter("tel", tel);
				params.addBodyParameter("postcode", postcode);
				params.addBodyParameter("content", content);
				x.http().post(params, new CommonCallback<String>() {

					@Override
					public void onCancelled(CancelledException arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onError(Throwable arg0, boolean arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onFinished() {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						System.out.println("修改地址:" + arg0);
						try {
							JSONObject json=new JSONObject(arg0);
							String code=json.getString("code");
                            if (code.equals("1")) {
                                Toast.makeText(EditAddress.this, "地址修改完毕", Toast.LENGTH_SHORT).show();
                            }else  {
                                Toast.makeText(EditAddress.this, "地址修改失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
				finish();
			}
		});
	}

}
