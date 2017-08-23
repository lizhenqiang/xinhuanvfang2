package com.bigdata.xinhuanufang.game;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.cart;

/*
 * 赛事回看Fragment
 * */
public class GameFragment extends Fragment implements OnClickListener {
	private FighterMessageFragment fighterMessageFragment;// 选手信息Fragment
	private GameReviewFragment gameReviewFragment;// 赛事回看Fragment
	private NewsFragment newsFragment;// 新闻资讯Fragment

	private TextView gameReviewTV; // 赛事回看TextView
	private TextView fighterMsg;// 选手信息TextView
	private TextView newsTV;// 新闻资讯TextView

	private View gameTab; // 赛事回看tab
	private View fighterTab; // 选手信息Tab
	private View newsTab; // 新闻资讯Tab
	// ImageView sousuo; //搜索栏
	private LinearLayout game_search;// 跳转到搜索页
	private EditText searchET; // 搜索EditText
	private TextView game_sousuo;
	private FragmentManager fragmentManager; // 对Fragment进行管理

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_game, container, false);
		// 初始化组件方法
		initViews(view);

		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		search();
		return view;
	}

	private void search() {
		// TODO Auto-generated method stub
		searchET.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		searchET.setInputType(EditorInfo.TYPE_CLASS_TEXT);

		searchET.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				// TODO Auto-generated method stub
				if (actionId == EditorInfo.IME_ACTION_SEND
						|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
					// do something;

					return true;
				}
				return false;
			}
		});
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		// sousuo=(ImageView) view.findViewById(R.id.sousuo_picture);
		gameReviewTV = (TextView) view.findViewById(R.id.tv_fragG_gameReview);
		fighterMsg = (TextView) view.findViewById(R.id.tv_fragG_fighterMsg);
		newsTV = (TextView) view.findViewById(R.id.tv_fragG_news);
		gameTab = view.findViewById(R.id.v_fragG_gameTab);
		fighterTab = view.findViewById(R.id.v_fragG_fighterTab);
		newsTab = view.findViewById(R.id.v_fragG_newsTab);
		// 搜索框
		searchET = (EditText) view.findViewById(R.id.tv_fragG_search);
		game_search = (LinearLayout) view.findViewById(R.id.game_search);
		// 搜索按钮
		game_sousuo = (TextView) view.findViewById(R.id.game_sousuo);
		game_search.setOnClickListener(this);
		gameReviewTV.setOnClickListener(this);
		fighterMsg.setOnClickListener(this);
		newsTV.setOnClickListener(this);
		game_sousuo.setOnClickListener(this);

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
				gameReviewTV.setTextColor(0xffffffff);
				gameTab.setVisibility(View.VISIBLE);
				searchET.getText().clear();
				searchET.setHint("搜索比赛");
				if (gameReviewFragment == null) {
					// 如果gameReviewFragment为空，则创建一个并添加到界面上
					gameReviewFragment = new GameReviewFragment();
					transaction.add(R.id.fl_fragG_content, gameReviewFragment);
				} else {
					// 如果gameReviewFragment不为空，则直接将它显示出来
					transaction.show(gameReviewFragment);
				}
				break;
			case 1:
				fighterMsg.setTextColor(0xffffffff);
				fighterTab.setVisibility(View.VISIBLE);
				searchET.getText().clear();
				searchET.setHint("搜索选手");
				if (fighterMessageFragment == null) {
					// 如果fighterMessageFragment为空，则创建一个并添加到界面上
					fighterMessageFragment = new FighterMessageFragment();
					transaction.add(R.id.fl_fragG_content, fighterMessageFragment);
				} else {
					// 如果fighterMessageFragment不为空，则直接将它显示出来
					transaction.show(fighterMessageFragment);
				}
				break;
			case 2:
				newsTV.setTextColor(0xffffffff);
				newsTab.setVisibility(View.VISIBLE);
				searchET.getText().clear();
				searchET.setHint("搜索新闻资讯");
				if (newsFragment == null) {
					// 如果newsFragment为空，则创建一个并添加到界面上
					newsFragment = new NewsFragment();
					transaction.add(R.id.fl_fragG_content, newsFragment);
				} else {
					// 如果newsFragment不为空，则直接将它显示出来
					transaction.show(newsFragment);
				}

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
		if (gameReviewFragment != null) {
			transaction.hide(gameReviewFragment);
		}
		if (fighterMessageFragment != null) {
			transaction.hide(fighterMessageFragment);
		}
		if (newsFragment != null) {
			transaction.hide(newsFragment);
		}
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		// TODO Auto-generated method stub
		gameTab.setVisibility(View.GONE);
		fighterTab.setVisibility(View.GONE);
		newsTab.setVisibility(View.GONE);
		searchET.setHint(null);
	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(getActivity().INPUT_METHOD_SERVICE);
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_fragG_gameReview:
				// 当点击了赛事回看tab时，选中第1个tab
				setTabSelection(0);
				searchET.clearFocus();
				// searchET.setFocusable(false);
				boolean isOpen1 = imm.isActive();
				if (isOpen1) {
					// imm.toggleSoftInput(0,
					// InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
					imm.hideSoftInputFromWindow(searchET.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
				break;
			case R.id.tv_fragG_fighterMsg:
				// 当点击了选手信息tab时，选中第2个tab
				setTabSelection(1);
				searchET.clearFocus();
				// searchET.setFocusable(false);
				boolean isOpen2 = imm.isActive();
				if (isOpen2) {
					// imm.toggleSoftInput(0,
					// InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
					imm.hideSoftInputFromWindow(searchET.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}

				break;
			case R.id.tv_fragG_news:
				// 当点击了新闻资讯tab时，选中第3个tab
				setTabSelection(2);
				searchET.clearFocus();
				// searchET.setFocusable(false);
				boolean isOpen3 = imm.isActive();
				if (isOpen3) {
					// imm.toggleSoftInput(0,
					// InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
					imm.hideSoftInputFromWindow(searchET.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
				break;
			case R.id.game_sousuo:
				// 点击之后进行搜索
				String context = searchET.getText().toString().trim();
				// 进行判断当前是搜索比赛还是选手或者说是新闻资讯
				if (gameTab.getVisibility() == View.VISIBLE) {
					// 搜索比赛
					if (context.isEmpty()) {
						Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent intent=new Intent(getActivity(),SearchpGameActivity.class);
					intent.putExtra("title", "搜索结果");
					intent.putExtra("context", context);
					startActivity(intent);
				}
				if (fighterTab.getVisibility() == View.VISIBLE) {
					//搜索选手
					// 搜索比赛
					if (context.isEmpty()) {
						Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent intent=new Intent(getActivity(),SearchpFinghterActivity.class);
					intent.putExtra("title", "搜索结果");
					intent.putExtra("context", context);
					startActivity(intent);
				}
				if (newsTab.getVisibility() == View.VISIBLE) {
					//搜索新闻资讯
					//跳转到新闻资讯结果搜索页
					if (context.isEmpty()) {
						Toast.makeText(getActivity(), "搜索内容不能为空", Toast.LENGTH_SHORT).show();
						return;
					}
					Intent intent=new Intent(getActivity(),SearchpNewsActivity.class);
					intent.putExtra("title", "搜索结果");
					intent.putExtra("context", context);
					startActivity(intent);
				}
				break;
			default:
				break;
		}
	}
}
