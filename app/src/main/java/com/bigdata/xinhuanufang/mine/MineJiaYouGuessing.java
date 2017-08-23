package com.bigdata.xinhuanufang.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.main.BreakGoldEgg;
import com.bigdata.xinhuanufang.main.GussingResultFragment;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 我的加油竞猜
 */
public class MineJiaYouGuessing extends BaseActivity implements OnClickListener {

	private TextView mine_choujiang_number;
	private LinearLayout mine_zajindan;
	private TextView refresh_date;
	private Button btn_jincaijilu;
	private View superdream_jincaijilu;
	private View superdream_zajindan;
	private Button btn_zajindan;
	private FragmentManager fragmentManager; // 对Fragment进行管理
	private GussingResultFragment gussingresultfragment;//竞猜记录
	private BreakEggFragmentRecord breakeggfragmentrecord;//砸金蛋记录
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_mine_jiayou_fuessing;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		setBack();
		super.setTitle("我的加油竞猜");
		setGone();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		// 砸金蛋次数
		mine_choujiang_number = (TextView) findViewById(R.id.mine_choujiang_number);
		// 去砸金蛋
		mine_zajindan = (LinearLayout) findViewById(R.id.mine_zajindan);
		mine_zajindan.setOnClickListener(this);
		// 刷新数据的时间
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy年MM月dd日    HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		refresh_date = (TextView) findViewById(R.id.refresh_date);
		refresh_date.setText(str);
		// 竞猜记录
		btn_jincaijilu = (Button) findViewById(R.id.btn_jincaijilu);
		superdream_jincaijilu = findViewById(R.id.superdream_jincaijilu);
		btn_jincaijilu.setOnClickListener(this);
		// 砸金蛋记录
		btn_zajindan = (Button) findViewById(R.id.btn_zajindan);
		superdream_zajindan = findViewById(R.id.superdream_zajindan);
		btn_zajindan.setOnClickListener(this);
		fragmentManager = getFragmentManager();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		//获取砸金蛋的个数
		getBreakEggCount();
	}

	private void getBreakEggCount() {
		// http://115.28.69.240/boxing/app/egg_count.php?user_id=1
		x.http().get(new RequestParams(Config.ip+Config.app+"/egg_count.php?user_id="+Config.userID), new CommonCallback<String>() {
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
				try {
					JSONObject json=new JSONObject(arg0);
					String egg_count=json.getString("egg_count");
					mine_choujiang_number.setText(egg_count);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_jincaijilu:
				superdream_jincaijilu.setVisibility(View.VISIBLE);
				superdream_zajindan.setVisibility(View.INVISIBLE);
				setTabSelection(0);
				break;
			case R.id.btn_zajindan:
				superdream_jincaijilu.setVisibility(View.INVISIBLE);
				superdream_zajindan.setVisibility(View.VISIBLE);
				setTabSelection(1);
				break;
			case R.id.mine_zajindan:
				Intent intent = new Intent(this, BreakGoldEgg.class);
				startActivity(intent);
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
				btn_jincaijilu.setTextColor(0xff333333);
				if (gussingresultfragment == null) {
					// 如果myAttentionGameFragment为空，则创建一个并添加到界面上
					gussingresultfragment = new GussingResultFragment();
					transaction.add(R.id.fl_mine_guessing, gussingresultfragment);
				} else {
					// 如果myAttentionGameFragment不为空，则直接将它显示出来
					transaction.show(gussingresultfragment);
				}
				break;
			case 1:
				btn_zajindan.setTextColor(0xff333333);
				if (breakeggfragmentrecord == null) {
					// 如果gameFragment为空，则创建一个并添加到界面上
					breakeggfragmentrecord = new BreakEggFragmentRecord();
					transaction
							.add(R.id.fl_mine_guessing, breakeggfragmentrecord);
				} else {
					// 如果gameFragment不为空，则直接将它显示出来
					transaction.show(breakeggfragmentrecord);
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
		if (gussingresultfragment != null) {
			transaction.hide(gussingresultfragment);
		}
		if (breakeggfragmentrecord != null) {
			transaction.hide(breakeggfragmentrecord);
		}
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		// TODO Auto-generated method stub
		btn_jincaijilu.setTextColor(0xff777777);
		btn_zajindan.setTextColor(0xff777777);
	}

}
