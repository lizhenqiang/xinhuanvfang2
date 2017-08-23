package com.bigdata.xinhuanufang.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GoodsAdressAdapter;
import com.bigdata.xinhuanufang.game.bean.AddressList;
import com.bigdata.xinhuanufang.game.bean.UserAddress;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asus 选择收货地址adress
 */
public class GoodsAdressActivity extends Activity implements
		OnItemClickListener {
	private GoodsAdressAdapter goodsAdressAdapter;
	public static int RESULT_CODE = 1;
	private ListView addAdressLV;
	private List<UserAddress> AddressLists;
	private List<UserAddress> list;
	private ImageView iv_itt_back; // title布局返回ImageView

	private TextView tv_itt_title; // title布局标题TextView

	public TextView tv_itt_save; // title布局保存TextView

	private ImageView iv_itt_collection; // title布局收藏ImageView
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					AddressLists = (List<UserAddress>) msg.obj;
					list=AddressLists;
					showAddressList(AddressLists);
					break;

				default:
					break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_goodsadress);

		tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
		iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
		tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
		iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
		iv_itt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				int i = goodsAdressAdapter.getSelectPosition();
//				String name = AddressLists.get(0).getAddress().get(i)
//						.getAddress_name();
//				String tel = AddressLists.get(0).getAddress().get(i)
//						.getAddress_tel();
//				String content = AddressLists.get(0).getAddress().get(i)
//						.getAddress_content();
//				intent.putExtra("name", name);
//				Config.Address_name=name;
//				intent.putExtra("tel", tel);
//				Config.Address_tel=tel;
//				intent.putExtra("content", content);
//				Config.Address_address=content;
//				setResult(RESULT_CODE, intent);// 设置resultCode，onActivityResult()中能获取到,为确认订单界面带回数据
//				Intent intent=new Intent();
//				for (int i = 0; i < AddressLists.size(); i++) {
//					for (int i1 = 0; i1 < AddressLists.get(i).getAddress().size(); i1++) {
//						if (AddressLists.get(i).getAddress().get(i1).getAddress_status().equals("1")) {
//							Config.Address_name=AddressLists.get(i).getAddress().get(i1).getAddress_name();
//							Config.Address_tel=AddressLists.get(i).getAddress().get(i1).getAddress_tel();
//							Config.Address_address=AddressLists.get(i).getAddress().get(i1).getAddress_content();
//						}
//					}
//				}
//
//				setResult(1, intent);
				finish();
//				Intent intent = new Intent(GoodsAdressActivity.this,ConfirmOrderActivity.class);
//				startActivity(intent);
			}
		});
		initData();
		initView();

	}

	/**
	 * 显示收货地址
	 */
	protected void showAddressList(List<UserAddress> AddressLists) {
		// TODO Auto-generated method stub
		addAdressLV = (ListView) findViewById(R.id.lv_aga_addAdress);
		goodsAdressAdapter = new GoodsAdressAdapter(GoodsAdressActivity.this,
				AddressLists);
		addAdressLV.setAdapter(goodsAdressAdapter);
		addAdressLV.setOnItemClickListener(this);

	}

	public void initView() {
		// TODO Auto-generated method stub
		tv_itt_save.setText("添加");
		tv_itt_title.setText("选择收货地址");
		List<UserAddress> getAddressInfo = new ArrayList<UserAddress>();
		getAddressList();
		getAddressInfo.addAll(AddressLists);
		tv_itt_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 添加收货地址,打开地址的编辑页面
				Intent intent=new Intent(GoodsAdressActivity.this,AddAddress.class);
				startActivityForResult(intent, 3);

			}
		});
	}

	public void initData() {
		// TODO Auto-generated method stub
		// 从网络获取数据
		getAddressList();

	}

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent=new Intent();
		for (int i = 0; i < AddressLists.size(); i++) {
			for (int i1 = 0; i1 < AddressLists.get(i).getAddress().size(); i1++) {
				if (AddressLists.get(i).getAddress().get(i1).getAddress_status().equals("1")) {
					intent.putExtra("Address_name",AddressLists.get(i).getAddress().get(i1).getAddress_name());
					intent.putExtra("Address_tel",AddressLists.get(i).getAddress().get(i1).getAddress_tel());
					intent.putExtra("Address_address",AddressLists.get(i).getAddress().get(i1).getAddress_content());
//					Config.Address_name=AddressLists.get(i).getAddress().get(i1).getAddress_name();
//					Config.Address_tel=AddressLists.get(i).getAddress().get(i1).getAddress_tel();
//					Config.Address_address=AddressLists.get(i).getAddress().get(i1).getAddress_content();
				}
			}
		}

        setResult(1, intent);
    }

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		Intent intent=new Intent();
//		for (int i = 0; i < AddressLists.size(); i++) {
//			for (int i1 = 0; i1 < AddressLists.get(i).getAddress().size(); i1++) {
//				if (AddressLists.get(i).getAddress().get(i1).getAddress_status().equals("1")) {
//					Config.Address_name=AddressLists.get(i).getAddress().get(i1).getAddress_name();
//					Config.Address_tel=AddressLists.get(i).getAddress().get(i1).getAddress_tel();
//					Config.Address_address=AddressLists.get(i).getAddress().get(i1).getAddress_content();
////					intent.putExtra("Address_name",AddressLists.get(i).getAddress().get(i1).getAddress_name());
////					intent.putExtra("Address_tel",AddressLists.get(i).getAddress().get(i1).getAddress_tel());
////					intent.putExtra("Address_address",AddressLists.get(i).getAddress().get(i1).getAddress_content());
//				}
//			}
//		}
//
//		setResult(1, intent);
//		return super.onKeyDown(keyCode, event);
//
//	}

	private void getAddressList() {
		// TODO Auto-generated method stub
		AddressLists = new ArrayList<UserAddress>();
        x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/address_list.php?user_id=" + Config.userID),
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

						if (TextUtils.isEmpty(arg0)) {
							return;
						} else {
							try {
								JSONObject json = new JSONObject(arg0);
								String code = json.getString("code");
								JSONArray address = json
										.getJSONArray("address");
								List<AddressList> addresslist = new ArrayList<AddressList>();
								for (int i = 0; i < address.length(); i++) {
									JSONObject json1 = address.getJSONObject(i);

									String address_id = json1
											.getString("address_id");
									String address_status = json1
											.getString("address_status");
									String address_name = json1
											.getString("address_name");
									String address_tel = json1
											.getString("address_tel");
									String address_postcode = json1
											.getString("address_postcode");
									String address_content = json1
											.getString("address_content");
									addresslist.add(new AddressList(address_id,
											address_status, address_name,
											address_tel, address_postcode,
											address_content));
								}
								AddressLists.add(new UserAddress(code,
										addresslist));
								Message msg = Message.obtain();
								msg.obj = AddressLists;
								msg.what = 0;
								handler.sendMessage(msg);

							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
		// TODO Auto-generated method stub
		GoodsAdressAdapter.ViewHolder holder = (GoodsAdressAdapter.ViewHolder) view
				.getTag();
		// holder.checkBox.toggle();

	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// getAddressList();
	//
	// }
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode==2) {
			Bundle bundle=data.getExtras();
			/**
			 * intent.putExtra("name", name);
			 intent.putExtra("tel", tel);
			 intent.putExtra("postcode", postcode);
			 intent.putExtra("content", content);
			 intent.putExtra("index", index);
			 */
			String name=bundle.getString("name");
			String tel=bundle.getString("tel");
			String postcode=bundle.getString("postcode");
			String content=bundle.getString("content");
			int index=bundle.getInt("index");
			AddressLists.get(0).getAddress().get(index).setAddress_name(name);
			AddressLists.get(0).getAddress().get(index).setAddress_tel(tel);
			AddressLists.get(0).getAddress().get(index).setAddress_postcode(postcode);
			AddressLists.get(0).getAddress().get(index).setAddress_content(content);
			goodsAdressAdapter = new GoodsAdressAdapter(GoodsAdressActivity.this,
					AddressLists);
			addAdressLV.setAdapter(goodsAdressAdapter);
		}
		if (requestCode==3) {
			getAddressList();
		}
	}

}
