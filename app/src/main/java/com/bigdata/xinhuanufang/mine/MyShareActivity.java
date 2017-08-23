package com.bigdata.xinhuanufang.mine;

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

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.MyShareAdapter;
import com.bigdata.xinhuanufang.adapter.MyShareAdapter.onCheckBoxChangeListener;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.bean.MyShare;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MyShareActivity extends BaseActivity implements OnClickListener {
	private ListView myShareLV;
	private MyShareAdapter myShareAdapter;
	private String[] num = { "1", "2", "3" };
	private List<MyShare> dataList;// 网络数据
	private ImageView iv_itt_delete;// 删除
	private RelativeLayout share_quanxuan_delete;// 全选的父布局
	private CheckBox share_checkbox_quanxuan;// 全选
	private Button share_delete;
	private String share_id = null;
	private int index=-1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 100:
					dataList = (List<MyShare>) msg.obj;
					showDataAdapter(dataList);
					break;

				default:
					break;
			}
		};
	};

	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_myshare;
	}

	protected void showDataAdapter(final List<MyShare> dataList) {
		myShareAdapter = new MyShareAdapter(MyShareActivity.this, dataList, "");
		myShareLV.setAdapter(myShareAdapter);
		listener();
		myShareLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(MyShareActivity.this, "点击了"+position, Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setTitle("我的分享");
		setTextRight("完成");
		setBack();
		dataList = new ArrayList<MyShare>();

		// 获取数据
		getNetWorkData();
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		myShareLV = (ListView) findViewById(R.id.lv_ams_myShare);
		iv_itt_delete = (ImageView) findViewById(R.id.iv_itt_delete);
		// 全选
		share_checkbox_quanxuan = (CheckBox) findViewById(R.id.share_checkbox_quanxuan);
		share_checkbox_quanxuan.setVisibility(View.GONE);
		// 删除按钮
		share_delete = (Button) findViewById(R.id.share_delete);
		// 全选的父布局,决定全选是否显示
		share_quanxuan_delete = (RelativeLayout) findViewById(R.id.share_quanxuan_delete);
		// 全选按钮
		iv_itt_delete.setVisibility(View.VISIBLE);
		tv_itt_save.setVisibility(View.GONE);
		tv_itt_save.setOnClickListener(this);// 完成按钮
		iv_itt_delete.setOnClickListener(this);// 删除图标
		share_checkbox_quanxuan.setOnClickListener(this);// 全选按钮
		share_delete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_itt_save:
				// 点击了完成,隐藏删除图标和他布局
				iv_itt_delete.setVisibility(View.VISIBLE);
				tv_itt_save.setVisibility(View.GONE);
				share_quanxuan_delete.setVisibility(View.INVISIBLE);
				// 发送标识
				myShareAdapter = new MyShareAdapter(MyShareActivity.this, dataList,
						"完成");
				myShareLV.setAdapter(myShareAdapter);
				listener();
				break;
			case R.id.iv_itt_delete:
				// 点击了删除图标,显示其他图标
				if (dataList.size() == 0) {
					return;
				}
				iv_itt_delete.setVisibility(View.GONE);
				tv_itt_save.setVisibility(View.VISIBLE);
				share_quanxuan_delete.setVisibility(View.VISIBLE);
				myShareAdapter = new MyShareAdapter(MyShareActivity.this, dataList,
						"删除");
				myShareLV.setAdapter(myShareAdapter);
				listener();
				break;
			case R.id.share_checkbox_quanxuan:
				// 全选,点击了之后就让listview的所有条目都选定
				if (share_checkbox_quanxuan.isChecked()) {
					myShareAdapter = new MyShareAdapter(MyShareActivity.this,
							dataList, "全选");
					System.out.println("全选");
					myShareLV.setAdapter(myShareAdapter);
					listener();
				} else if (!share_checkbox_quanxuan.isChecked()) {
					myShareAdapter = new MyShareAdapter(MyShareActivity.this,
							dataList, "不全选");
					System.out.println("不全选");
					myShareLV.setAdapter(myShareAdapter);
					listener();
				}

				break;
			case R.id.share_delete:
				if (share_id != null) {
					if (index!=-1) {
						shareDelete(share_id,index);
					}

				}

				break;
			default:
				break;
		}

	}

	public void shareDelete(final String share_id,final int index) {
				// TODO Auto-generated method stub

				// 如果是选中的就发起删除分享的请求
				// http://115.28.69.240/boxing/app/my_sharecannel.php?share_id=4
				x.http().get(
						new RequestParams(Config.ip + Config.app
								+ "/my_sharecannel.php?share_id=" + share_id),
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
									JSONObject json=new JSONObject(arg0);
									if (json.getString("code").equals("1")) {
										showToast("删除成功");
										dataList.remove(index);
										myShareAdapter.notifyDataSetChanged();
//										finish();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						});
	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/my_share.php?user_id=1
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/my_share.php?user_id=" + Config.userID),
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
							JSONObject json = new JSONObject(arg0);
							JSONArray js = json.getJSONArray("share");
							for (int i = 0; i < js.length(); i++) {
								JSONObject json1 = js.getJSONObject(i);
								String share_id = json1.getString("share_id");
								String share_userid = json1
										.getString("share_userid");
								String share_liveguessid = json1
										.getString("share_liveguessid");
								String share_pic = json1.getString("share_pic");
								String share_playernamea = json1
										.getString("share_playernamea");
								String share_playernameb = json1
										.getString("share_playernameb");
								String share_title = json1
										.getString("share_title");
								String share_time = json1
										.getString("share_time");
								dataList.add(new MyShare(share_id,
										share_userid, share_liveguessid,
										share_pic, share_playernamea,
										share_playernameb, share_title,
										share_time));
							}
							Message msg = Message.obtain();
							msg.what = 100;
							msg.obj = dataList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	private void listener() {
		myShareAdapter.setOnBoxChangeListener(new onCheckBoxChangeListener() {

			@Override
			public void onChanged(int position, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					share_id = dataList.get(position).getShare_id();
					index=position;
				}
			}
		});
	}

}
