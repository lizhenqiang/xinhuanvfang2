package com.bigdata.xinhuanufang.mine;

import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.DeleteCollectionAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;

/**
 * @author asus 删除我的收藏Activity
 */
public class DeleteCollectionActivity extends BaseActivity {
	private ListView deleteCollectionLV;
	private String[] num = { "1", "2", "3", "4" }; // 控制ListView的数量
	private DeleteCollectionAdapter deleteCollectionAdapter;

	// 重写加载布局的方法
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_deletecollection;
	}

	// 初始化组件方法
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		super.setTitle("我的收藏");// 设置title的标题
		super.setTextRight("完成");
		setBack();
		deleteCollectionLV = (ListView) findViewById(R.id.lv_adc_deleteCollection);
		deleteCollectionAdapter = new DeleteCollectionAdapter(
				DeleteCollectionActivity.this, num);
		deleteCollectionLV.setAdapter(deleteCollectionAdapter);
	}

}
