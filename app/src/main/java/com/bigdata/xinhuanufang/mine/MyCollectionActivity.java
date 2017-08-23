package com.bigdata.xinhuanufang.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MyCollectionAdapter;
import com.bigdata.xinhuanufang.adapter.MyCollectionAdapter.onCheckBoxChangeListener;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.ReviewDetailsActivity;
import com.bigdata.xinhuanufang.game.bean.GameDetailsInfo;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author asus 我的收藏Activity
 */
public class MyCollectionActivity extends BaseActivity implements
		OnClickListener {
	private TextView myCollectionTV;// 我的收藏TextView
	private ImageView deleteIV;// 删除ImageView
	private String[] num = { "1", "2", "3", "4" }; // 用于控制ListView的数量
	private List<GameDetailsInfo> dataList;// 存储网络数据
	private ListView myCollectionLV; // 我的收藏ListView
	private MyCollectionAdapter myCollectionAdapter; // 我的收藏Adapter
	private RelativeLayout collect_quanxuan_delete;
	private CheckBox collect_checkbox_quanxuan;
	private Button collect_delete;
	private ImageView iv_itt_delete;// 删除
	private String video_id = null;
	private int index=-1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 100:
					dataList = (List<GameDetailsInfo>) msg.obj;

					showAdapterData(dataList);
					break;

				default:
					break;
			}
		};
	};

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_mycollection;
	}

	protected void showAdapterData(final List<GameDetailsInfo> dataList) {
		myCollectionAdapter = new MyCollectionAdapter(
				MyCollectionActivity.this, dataList, "");
		myCollectionLV.setAdapter(myCollectionAdapter);
		myCollectionLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("context", null);
				bundle.putInt("position", position);
				bundle.putInt("tag", 3);
				bundle.putString("video_liveguessid", dataList.get(position).getVideo_id());
				Intent intent = new Intent(MyCollectionActivity.this, ReviewDetailsActivity.class);

				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		listener();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("我的收藏");
		setTextRight("完成");
		setBack();
		dataList = new ArrayList<GameDetailsInfo>();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		myCollectionLV = (ListView) findViewById(R.id.lv_amc_collection);
		// 用于控制是否显示删除按钮的父布局
		collect_quanxuan_delete = (RelativeLayout) findViewById(R.id.collect_quanxuan_delete);
		// 全选操作的checkbox
		collect_checkbox_quanxuan = (CheckBox) findViewById(R.id.collect_checkbox_quanxuan);
		// 删除按钮
		collect_delete = (Button) findViewById(R.id.collect_delete);
		// 右上角删除的图标
		iv_itt_delete = (ImageView) findViewById(R.id.iv_itt_delete);
		iv_itt_delete.setVisibility(View.VISIBLE);
		tv_itt_save.setVisibility(View.GONE);
		tv_itt_save.setOnClickListener(this);// 完成按钮
		iv_itt_delete.setOnClickListener(this);// 删除图标
		collect_checkbox_quanxuan.setOnClickListener(this);
		collect_delete.setOnClickListener(this);

		// 获取数据
		getNetWorkData();

	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
			case R.id.iv_itt_delete:
				// 点击了删除的图标,进入编辑删除模式
				if (dataList.size()==0) {
					return;
				}
				iv_itt_delete.setVisibility(View.GONE);
				tv_itt_save.setVisibility(View.VISIBLE);
				collect_quanxuan_delete.setVisibility(View.VISIBLE);
				myCollectionAdapter = new MyCollectionAdapter(
						MyCollectionActivity.this, dataList, "删除");
				myCollectionLV.setAdapter(myCollectionAdapter);
				listener();
				break;
			case R.id.tv_itt_save:
				// 点击了完成,退出编辑模式
				iv_itt_delete.setVisibility(View.VISIBLE);
				tv_itt_save.setVisibility(View.GONE);
				collect_quanxuan_delete.setVisibility(View.INVISIBLE);
				myCollectionAdapter = new MyCollectionAdapter(
						MyCollectionActivity.this, dataList, "完成");
				myCollectionLV.setAdapter(myCollectionAdapter);
				listener();
				break;
			case R.id.collect_checkbox_quanxuan:
				// 全选,点击了之后就让listview的所有条目都选定
				if (collect_checkbox_quanxuan.isChecked()) {
					myCollectionAdapter = new MyCollectionAdapter(
							MyCollectionActivity.this, dataList, "全选");
					myCollectionLV.setAdapter(myCollectionAdapter);
					listener();
				} else if (!collect_checkbox_quanxuan.isChecked()) {
					myCollectionAdapter = new MyCollectionAdapter(
							MyCollectionActivity.this, dataList, "不全选");
					myCollectionLV.setAdapter(myCollectionAdapter);
					listener();
				}
				break;
			case R.id.collect_delete:
				//取消收藏的操作
				collectDelete(video_id,index);
				myCollectionAdapter = new MyCollectionAdapter(
						MyCollectionActivity.this, dataList, "不全选");
				myCollectionLV.setAdapter(myCollectionAdapter);
				myCollectionAdapter.notifyDataSetChanged();
				break;
			default:
				break;
		}
	}

	private void collectDelete(String video_id,final int index) {
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/my_collcannel.php?user_id=" + Config.userID
						+ "&video_id=" + video_id),
				new CommonCallback<String>() {
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
						System.out.println(arg0);
						showToast("取消成功");
						dataList.remove(index);
						myCollectionAdapter.notifyDataSetChanged();
					}
				});
	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/my_coll.php?user_id=1
		System.out.println("请求数据的地址:" + Config.ip + Config.app
				+ "/my_coll.php?user_id=" + Config.userID);
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/my_coll.php?user_id=" + Config.userID),
				new CommonCallback<String>() {
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
							JSONObject json1 = new JSONObject(arg0);
							JSONArray json = json1.getJSONArray("video");
							for (int i = 0; i < json.length(); i++) {
								JSONObject js = json.getJSONObject(i);
								String video_id = js.getString("video_id");
								String video_playeruserid = js
										.getString("video_playeruserid");
								String video_playeruserid2 = js
										.getString("video_playeruserid2");
								String video_time = js.getString("video_time");
								String video_title = js
										.getString("video_title");
								String video_success = js
										.getString("video_success");
								String video_pic = js.getString("video_pic");
								String video_content = js
										.getString("video_content");
								String video_liveguessid = js
										.getString("video_liveguessid");
								String player_namea = js
										.getString("player_namea");
								String player_heada = js
										.getString("player_heada");
								String player_nameb = js
										.getString("player_nameb");
								String player_headb = js
										.getString("player_headb");
								String player_success = js
										.getString("player_success");
//								String is_coll=js.getString("is_coll");
								dataList.add(new GameDetailsInfo(video_id,
										video_playeruserid,
										video_playeruserid2, video_time,
										video_title, video_success, video_pic,
										video_content, video_liveguessid,
										player_namea, player_heada,
										player_nameb, player_headb,
										player_success,""));
							}
							Message msg = Message.obtain();
							msg.what = 100;
							System.out.println("我的收藏的数据大小:" + dataList.size());
							msg.obj = dataList;
							handler.sendMessage(msg);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
	private void listener(){
		myCollectionAdapter
				.setOnBoxChangeListener(new onCheckBoxChangeListener() {

					@Override
					public void onChanged(int position, boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							video_id = dataList.get(position).getVideo_id();
							myCollectionAdapter.getIsSelected().put(position, true);
							index=position;
							boolean isquanxuan=true;
							LinkedHashMap<Integer, Boolean> isSelected = myCollectionAdapter.getIsSelected();
							for (int i = 0; i < isSelected.size(); i++) {
								isquanxuan=isquanxuan&isSelected.get(i);
							}
							if (isquanxuan) {
								collect_checkbox_quanxuan.setChecked(true);
							}else{
								collect_checkbox_quanxuan.setChecked(false);
							}
						}else {
							myCollectionAdapter.getIsSelected().put(position, false);
							boolean isquanxuan=true;
							LinkedHashMap<Integer, Boolean> isSelected = myCollectionAdapter.getIsSelected();
							for (int i = 0; i < isSelected.size(); i++) {
								isquanxuan=isquanxuan&isSelected.get(i);
							}
							if (isquanxuan) {
								collect_checkbox_quanxuan.setChecked(true);
							}else{
								collect_checkbox_quanxuan.setChecked(false);
							}
						}
					}
				});
	}

}
