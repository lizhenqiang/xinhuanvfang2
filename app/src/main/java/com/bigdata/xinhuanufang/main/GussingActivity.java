package com.bigdata.xinhuanufang.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;

/*
 * 加油竞猜Activity
 * 17/03/02
 * */
public class GussingActivity extends BaseActivity implements OnClickListener {
	private GameGussingFragment gameGussingFragment; // 比赛竞猜Fagment
	private GussingResultFragment gussingResultFragment; // 竞猜结果Fragment

	private TextView gameGuessingTV; // 比赛竞猜TEXTVIEW
	private TextView gussingResultTV; // 竞猜结果TEXTVIEW

	private View gameTab;// 比赛竞猜下方的tab
	private View guessingTab;// 竞猜结果下方的tab

	private FragmentManager fragmentManager; // 对Fragment进行管理

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_guessing;
	}

	/*
	 * 初始化信息
	 */
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		super.setTitle("加油竞猜");
		super.setGone(); // 去掉标题栏右侧文字
		/*
		 * 为返回键添加点击事件
		 */
		super.setBack();
		super.tv_itt_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent breakgoldegg = new Intent(GussingActivity.this,
						BreakGoldEgg.class);
				breakgoldegg.putExtra("title", "砸金蛋");
				startActivity(breakgoldegg);
			}
		});
	}

	/*
	 * 初始化组件信息
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		gameGuessingTV = (TextView) findViewById(R.id.tv_gus_gameGuessing);
		gussingResultTV = (TextView) findViewById(R.id.tv_gus_guessingResult);
		gameGuessingTV.setOnClickListener(this);
		gussingResultTV.setOnClickListener(this);

		gameTab = findViewById(R.id.v_gus_gameTab);
		guessingTab = findViewById(R.id.v_gus_guessingTab);
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
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
				gameGuessingTV.setTextColor(0xffd63c3c);
				gameTab.setVisibility(View.VISIBLE);
				super.setGone();
				if (gameGussingFragment == null) {
					// 如果MessageFragment为空，则创建一个并添加到界面上
					gameGussingFragment = new GameGussingFragment();
					transaction.add(R.id.guessing_content, gameGussingFragment);
				} else {
					// 如果MessageFragment不为空，则直接将它显示出来
					transaction.show(gameGussingFragment);
				}
				break;
			case 1:
				gussingResultTV.setTextColor(0xffd63c3c);
				guessingTab.setVisibility(View.VISIBLE);
				super.setTextRight("砸金蛋");
				super.setVisible();
				if (gussingResultFragment == null) {
					// 如果ContactsFragment为空，则创建一个并添加到界面上
					gussingResultFragment = new GussingResultFragment();
					transaction.add(R.id.guessing_content, gussingResultFragment);
				} else {
					// 如果ContactsFragment不为空，则直接将它显示出来
					transaction.show(gussingResultFragment);
				}
				break;
		}
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_gus_gameGuessing:
				// 当点击了主页tab时，选中第1个tab
				setTabSelection(0);
				break;
			case R.id.tv_gus_guessingResult:
				// 当点击了比赛tab时，选中第2个tab
				setTabSelection(1);
				break;
			default:
				break;
		}
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 *
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
		if (gameGussingFragment != null) {
			transaction.hide(gameGussingFragment);
		}
		if (gussingResultFragment != null) {
			transaction.hide(gussingResultFragment);
		}
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		// TODO Auto-generated method stub
		gameGuessingTV.setTextColor(0xff333333);
		gussingResultTV.setTextColor(0xff333333);
		gameTab.setVisibility(View.GONE);
		guessingTab.setVisibility(View.GONE);
	}
}
