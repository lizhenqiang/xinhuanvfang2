package com.bigdata.xinhuanufang.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;

/**
 * 
 * @author asus 
 * 2017年3月3日18:06:29 
 * Activity父类
 */
public abstract class BaseActivity extends Activity {

	public ImageView iv_itt_back; // title布局返回ImageView

	public TextView tv_itt_title; // title布局标题TextView

	public TextView tv_itt_save; // title布局保存TextView
	
	public ImageView iv_itt_collection; //title布局收藏ImageView
	
	public Bundle bundle;
	
	public Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = BaseActivity.this;
		setContentView(getView());
		
		tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
		iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
		tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
		iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
		initData();
		initView();

	}


	/**
	 * 获取当前布局
	 */
	public abstract int getView();

	/**
	 * 初始化信息
	 */
	public void initView() {
		
	}
	/**
	 * 初始化组件信息
	 */
	public void initData() {
		
	}
	/**
	 * 设置返回
	 */
	public void setBack() {
		iv_itt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tv_itt_title.setText(title);
	}

	/**
	 * 设置标题行文字
	 * 
	 * @param title
	 */
	public void setTextRight(String textRight) {
		tv_itt_save.setText(textRight);
	}
	/**
	 * 隐藏标题行文字
	 * 
	 * @param title
	 */
	public void setGone() {
		tv_itt_save.setVisibility(View.GONE);
	}
	/**
	 * 显示标题行文字
	 * 
	 * @param title
	 */
	public void setVisible() {
		tv_itt_save.setVisibility(View.VISIBLE);
	}
	/**
	 * 显示标题行ImageView
	 * 
	 * @param title
	 */
	public void setImageVisible() {
		iv_itt_collection.setVisibility(View.VISIBLE);
	}
	/**
	 * 设置标题行ImageView
	 * 
	 * @param title
	 */
	public void setRightImage(int resId) {
		iv_itt_collection.setImageResource(resId);
	}
	public void showToast(String toast) {
		Toast.makeText(mContext, toast, 0).show();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Context getBaseContent(){
		return mContext;
	}
	
}
