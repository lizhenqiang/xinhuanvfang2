package com.bigdata.xinhuanufang.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;

public class MyShoppingMall extends BaseActivity implements OnClickListener {
	private shoppingMallDaiZhiFuFragment shoppingmalldaizhifufragment;//待支付
	private shoppingMallYiZhiFuFragment shoppingmallyizhifufragment;//已支付
	private Button btn_shopping_daizhifu;
	private View shopping_daizhifu;
	private Button btn_shopping_yizhifu;
	private View shopping_yizhifu;
	private FragmentManager fragmentManager; // 对Fragment进行管理
	private String isfukuan="";

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_shoppingmall;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setGone();
		setTitle("商城订单");
		setBack();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		Intent intent=getIntent();
		isfukuan = intent.getStringExtra("isfukuan");
		// 待支付
		btn_shopping_daizhifu = (Button) findViewById(R.id.btn_shopping_daizhifu);
		shopping_daizhifu = findViewById(R.id.shopping_daizhifu);
		btn_shopping_daizhifu.setOnClickListener(this);
		btn_shopping_daizhifu.setTextColor(Color.BLACK);
		// 已支付
		btn_shopping_yizhifu = (Button) findViewById(R.id.btn_shopping_yizhifu);
		shopping_yizhifu = findViewById(R.id.shopping_yizhifu);
		btn_shopping_yizhifu.setOnClickListener(this);
		//fragment
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		if (isfukuan!=null) {
			if (isfukuan.equals("yifukuan")) {
				setTabSelection(1);
			}

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_shopping_daizhifu:
				btn_shopping_yizhifu.setTextColor(Color.GRAY);
				btn_shopping_daizhifu.setTextColor(Color.BLACK);
				shopping_daizhifu.setVisibility(View.VISIBLE);
				shopping_yizhifu.setVisibility(View.INVISIBLE);
				setTabSelection(0);
				break;
			case R.id.btn_shopping_yizhifu:
				btn_shopping_yizhifu.setTextColor(Color.BLACK);
				btn_shopping_daizhifu.setTextColor(Color.GRAY);
				shopping_daizhifu.setVisibility(View.INVISIBLE);
				shopping_yizhifu.setVisibility(View.VISIBLE);
				setTabSelection(1);
				break;

			default:
				break;
		}
	}

	private void setTabSelection(int index) {
		// TODO Auto-generated method stub
		// 每次选中之前先清楚掉上次的选中状态
//		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
			case 0:
				if (shoppingmalldaizhifufragment == null) {
					// 如果myAttentionGameFragment为空，则创建一个并添加到界面上
					shoppingmalldaizhifufragment = new shoppingMallDaiZhiFuFragment();
					transaction.add(R.id.fl_shopping_content, shoppingmalldaizhifufragment);
				} else {
					// 如果myAttentionGameFragment不为空，则直接将它显示出来
					transaction.show(shoppingmalldaizhifufragment);
				}
				break;
			case 1:
				if (shoppingmallyizhifufragment == null) {
					// 如果gameFragment为空，则创建一个并添加到界面上
					shoppingmallyizhifufragment = new shoppingMallYiZhiFuFragment();
					transaction.add(R.id.fl_shopping_content, shoppingmallyizhifufragment);
				} else {
					// 如果gameFragment不为空，则直接将它显示出来
					transaction.show(shoppingmallyizhifufragment);
				}
                shopping_daizhifu.setVisibility(View.INVISIBLE);
                shopping_yizhifu.setVisibility(View.VISIBLE);
				break;
			case 3:
			default:
				break;
		}
		transaction.commit();
	}
	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (shoppingmalldaizhifufragment != null) {
			transaction.hide(shoppingmalldaizhifufragment);
		}
		if (shoppingmallyizhifufragment != null) {
			transaction.hide(shoppingmallyizhifufragment);
		}
	}
}
