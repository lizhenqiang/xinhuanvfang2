package com.bigdata.xinhuanufang.main;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;

/**
 * @author asus
 * 消息详情Activity
 */
public class MessageDetailActivity extends BaseActivity {

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_messagedetail;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("消息详情"); // 设置标题栏文字
		super.setGone(); // 设置标题栏右侧文字不可见

		super.setBack(); // 设置返回键
	}
}
