package com.bigdata.xinhuanufang.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;

/**
 * @author asus 我的关注activity
 */
public class MyAttentionActivity extends BaseActivity implements
		OnClickListener {
	private MyAttentionGameFragment myAttentionGameFragment; // 我的关注-比赛Fragment
	private MyAttentionFighterFragment myAttentionFighterFragment; // 我的关注-选手Fragment

	private Button gameBtn; // 比赛Button
	private Button fighterBtn; // 选手Button

	private TextView myAttentionTV; // 我的关注TextView
	private TextView hiddenTextView; // 要隐藏的TextView
	private View my_guanzhu_bisai;
	private View my_guanzhu_xuanshou;
	private FragmentManager fragmentManager; // 对Fragment进行管理

	@Override
	public int getView() {
		// TODO Auto-generated method stub

		return R.layout.activity_myattention;
	}

	/*
	 * 初始化界面方法
	 */
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("我的关注");
		setGone();// 隐藏右侧文字
		setBack();

	}

	/*
	 * 初始化组件方法
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		myAttentionTV = (TextView) findViewById(R.id.tv_itt_title);
		myAttentionTV.setText("我的关注");
		hiddenTextView = (TextView) findViewById(R.id.tv_itt_save);
		hiddenTextView.setVisibility(View.GONE);
		gameBtn = (Button) findViewById(R.id.btn_ama_game);
		fighterBtn = (Button) findViewById(R.id.btn_ama_fighter);
		// 比赛
		my_guanzhu_bisai= findViewById(R.id.my_guanzhu_bisai);
		// 选手
		my_guanzhu_xuanshou = findViewById(R.id.my_guanzhu_xuanshou);
		gameBtn.setOnClickListener(this);
		fighterBtn.setOnClickListener(this);

		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.btn_ama_game:
				// 当点击了主页tab时，选中第1个tab
				setTabSelection(0);
				my_guanzhu_bisai.setVisibility(View.VISIBLE);
				my_guanzhu_xuanshou.setVisibility(View.GONE);
				break;
			case R.id.btn_ama_fighter:
				// 当点击了主页tab时，选中第1个tab
				setTabSelection(1);
				my_guanzhu_bisai.setVisibility(View.GONE);
				my_guanzhu_xuanshou.setVisibility(View.VISIBLE);
				break;

			default:
				break;
		}
	}

	private void setTabSelection(int index) {
		// TODO Auto-generated method stub
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
			case 0:
				gameBtn.setTextColor(0xff333333);
				if (myAttentionGameFragment == null) {
					// 如果myAttentionGameFragment为空，则创建一个并添加到界面上
					myAttentionGameFragment = new MyAttentionGameFragment();
					transaction.add(R.id.fl_ama_content, myAttentionGameFragment);
				} else {
					// 如果myAttentionGameFragment不为空，则直接将它显示出来
					transaction.show(myAttentionGameFragment);
				}
				break;
			case 1:
				fighterBtn.setTextColor(0xff333333);
				if (myAttentionFighterFragment == null) {
					// 如果gameFragment为空，则创建一个并添加到界面上
					myAttentionFighterFragment = new MyAttentionFighterFragment();
					transaction
							.add(R.id.fl_ama_content, myAttentionFighterFragment);
				} else {
					// 如果gameFragment不为空，则直接将它显示出来
					transaction.show(myAttentionFighterFragment);
				}
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
		if (myAttentionGameFragment != null) {
			transaction.hide(myAttentionGameFragment);
		}
		if (myAttentionFighterFragment != null) {
			transaction.hide(myAttentionFighterFragment);
		}
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		// TODO Auto-generated method stub
		gameBtn.setTextColor(0xff777777);
		fighterBtn.setTextColor(0xff777777);
	}

}
