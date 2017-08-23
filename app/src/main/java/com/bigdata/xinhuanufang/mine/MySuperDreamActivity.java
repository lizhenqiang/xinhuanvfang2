package com.bigdata.xinhuanufang.mine;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MySuperDreamAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;

/**
 * @author asus 我的超级梦想Activity
 */
public class MySuperDreamActivity extends BaseActivity implements
		OnClickListener {
	private ListView superDreamLV;// 我的超级梦想listVIEW
	private MySuperDreamAdapter mySuperDreamAdapter; // 我的超级梦想Adapter
	private Button btn_superdream_daishixian;
	private Button btn_superdream_yishixian;
	private View superdream_daishixian;
	private View superdream_yishixian;
	private FragmentManager fragmentManager;

	private MySuperDreamFragmentYishixian mysuperdreamfragmentyishixian;//待实现
	private MySuperDreamFragmentDaishixian mysuperdreamfragmentdaishixian;//已实现

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_superdream;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("我的超级梦想");
		super.setGone();
		setBack();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		// 待实现
		btn_superdream_daishixian = (Button) findViewById(R.id.btn_superdream_daishixian);
		superdream_daishixian = findViewById(R.id.superdream_daishixian);
		btn_superdream_daishixian.setOnClickListener(this);

		// 以实现
		btn_superdream_yishixian = (Button) findViewById(R.id.btn_superdream_yishixian);
		superdream_yishixian = findViewById(R.id.superdream_yishixian);
		btn_superdream_yishixian.setOnClickListener(this);
		//fragent
		fragmentManager=getFragmentManager();
		//默认选择待实现
		setTabSelection(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_superdream_daishixian:
				//待实现
				superdream_daishixian.setVisibility(View.VISIBLE);
				superdream_yishixian.setVisibility(View.INVISIBLE);
				setTabSelection(0);
				break;
			case R.id.btn_superdream_yishixian:
				//已实现
				superdream_daishixian.setVisibility(View.INVISIBLE);
				superdream_yishixian.setVisibility(View.VISIBLE);
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
				if (mysuperdreamfragmentdaishixian == null) {
					// 如果myAttentionGameFragment为空，则创建一个并添加到界面上
					mysuperdreamfragmentdaishixian = new MySuperDreamFragmentDaishixian();
					transaction.add(R.id.fl_superdream, mysuperdreamfragmentdaishixian);
				} else {
					// 如果myAttentionGameFragment不为空，则直接将它显示出来
					transaction.show(mysuperdreamfragmentdaishixian);
				}
				System.out.println("fragment1");
				break;
			case 1:
				if (mysuperdreamfragmentyishixian == null) {
					// 如果gameFragment为空，则创建一个并添加到界面上
					mysuperdreamfragmentyishixian = new MySuperDreamFragmentYishixian();
					transaction
							.add(R.id.fl_superdream, mysuperdreamfragmentyishixian);
				} else {
					// 如果gameFragment不为空，则直接将它显示出来
					transaction.show(mysuperdreamfragmentyishixian);
				}
				System.out.println("fragment2");
				break;
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
		if (mysuperdreamfragmentyishixian != null) {
			transaction.hide(mysuperdreamfragmentyishixian);
		}
		if (mysuperdreamfragmentdaishixian != null) {
			transaction.hide(mysuperdreamfragmentdaishixian);
		}
	}
}
