package com.bigdata.xinhuanufang.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * /** 编辑地址activity
 */

public class AddAddress extends Activity {

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
	private TextView add_address_delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editadress);

		tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
		iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
		tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
		iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
		address_input_name = (EditText) findViewById(R.id.address_input_name);
		address_input_tel = (EditText) findViewById(R.id.address_input_tel);
		address_input_postcode = (EditText) findViewById(R.id.address_input_postcode);
		address_input_content = (EditText) findViewById(R.id.address_input_content);
		add_address_delete=(TextView) findViewById(R.id.add_address_delete);
		iv_itt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		initData();
		initView();

	}

	public void initData() {

	}

	public void initView() {
		// TODO Auto-generated method stub
		tv_itt_save.setText("添加");
		tv_itt_title.setText("添加地址");
		add_address_delete.setVisibility(View.GONE);
		tv_itt_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * http://115.28.69.240/boxing/app/address_add.php?user_id=2&
				 * name
				 * =%E5%BC%A0%E9%9B%A8%E9%A6%A8&tel=18519011617&postcode=182999
				 * &content
				 * =%E5%8C%97%E4%BA%AC%E5%B8%82%E6%B5%B7%E6%B7%80%E5%8C%BA
				 * %E4%B8%
				 * 8A%E5%9C%B0%E5%8D%97%E8%B7%AF%E7%A7%91%E8%B4%B8%E5%A4%A7
				 * %E5%8E%A6408
				 *
				 user_id  name  tel  postcode  content
				 */

				String name = address_input_name.getText().toString();
				String tel = address_input_tel.getText().toString();
				String postcode = address_input_postcode.getText().toString();
				String content = address_input_content.getText().toString();
				Intent intent = new Intent();

				setResult(3, intent);
				RequestParams params = new RequestParams(Config.ip + Config.app
						+ "/address_add.php?");
				params.addBodyParameter("user_id", Config.userID);
				params.addBodyParameter("name", name);
				params.addBodyParameter("tel", tel);
				params.addBodyParameter("postcode", postcode);
				params.addBodyParameter("content", content);
				System.out.println(params.toString());
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
						System.out.println("添加地址成功:" + arg0);
					}
				});
				finish();
			}
		});
	}

}
