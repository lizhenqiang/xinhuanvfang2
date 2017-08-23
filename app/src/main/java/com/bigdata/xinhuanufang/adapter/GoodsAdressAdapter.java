package com.bigdata.xinhuanufang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.UserAddress;
import com.bigdata.xinhuanufang.store.EditAddress;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * @author asus 选择收货地址adapter
 */
public class GoodsAdressAdapter extends BaseAdapter  {
	private Context context;
	private List<UserAddress> AddressLists;
	private LayoutInflater mInflater;
	private int index = -1;// 用来记录索引的变化的
	private boolean state = true;// 用来记录索引变化的次数的;
//	private RefreshData refreshdata;
	private int tempPosition = -1;// 记录已经点击的checkbox的位置
	private int positionb=-1;
	private String address_id="-1";
	private String delete_address_id="-1";

	public GoodsAdressAdapter(Context context, List<UserAddress> AddressLists) {
		this.context = context;
		this.AddressLists = AddressLists;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return AddressLists.get(0).getAddress().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return AddressLists.get(0).getAddress().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;
		View view = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.goodsadress_listview_item,
					null);
			holder = new ViewHolder();
			holder.receiver_name = (TextView) convertView
					.findViewById(R.id.receiver_name);
			holder.receiver_tel = (TextView) convertView
					.findViewById(R.id.receiver_tel);
			holder.receiver_content = (TextView) convertView
					.findViewById(R.id.receiver_content);
			holder.address_default = (CheckBox) convertView
					.findViewById(R.id.address_default);
			holder.address_edit_icon = (ImageView) convertView
					.findViewById(R.id.address_edit_icon);
			holder.address_edit = (TextView) convertView
					.findViewById(R.id.address_edit);
			holder.address_delete_icon = (ImageView) convertView
					.findViewById(R.id.address_delete_icon);
			holder.address_delete = (TextView) convertView
					.findViewById(R.id.address_delete);
			holder.lv_address_edit=(LinearLayout) convertView.findViewById(R.id.lv_address_edit);
			holder.lv_address_delete=(LinearLayout) convertView.findViewById(R.id.lv_address_delete);
			convertView.setTag(holder);

		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		positionb=position;
		holder.receiver_name.setText(AddressLists.get(0).getAddress()
				.get(position).getAddress_name());
		holder.receiver_tel.setText(AddressLists.get(0).getAddress()
				.get(position).getAddress_tel());
		holder.receiver_content.setText(AddressLists.get(0).getAddress()
				.get(position).getAddress_content());
		holder.address_default.setChecked(false);
		// holder.address_edit_icon.setOnClickListener(this);

		holder.lv_address_edit.setTag(position);
		holder.lv_address_delete.setTag(position);
		if (position == tempPosition) { // 比较位置是否一样,一样就设置为选中,否则就设置为未选中.
			holder.address_default.setChecked(true);
		} else {
			holder.address_default.setChecked(false);

		}
		if (AddressLists.get(0).getAddress().get(position).getAddress_status()
				.equals("1")) {
			// if (state) {
			// holder.address_default.setChecked(true);
			// state=false;
			// }
			holder.address_default.setChecked(true);
			Config.Address_name=AddressLists.get(0).getAddress().get(position).getAddress_name();
			Config.Address_tel=AddressLists.get(0).getAddress().get(position).getAddress_tel();
			Config.Address_address=AddressLists.get(0).getAddress().get(position).getAddress_content();
			for (int i = 0; i < AddressLists.get(0).getAddress().size(); i++) {
				if (AddressLists.get(0).getAddress().get(i).getAddress_status()
						.equals("1")) {
					// 修正索引
					tempPosition = i;
//					position = i;
				}
			}

			//
		}
		// 默认地址的选择
		holder.address_default.setId(position);// 设置当前的position为checkbox的id
		holder.address_default
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							if (tempPosition != -1) {
								// 根据id找到上次点击的CheckBox,将它设置为false.
								CheckBox tempCheckBox = (CheckBox) ((Activity) context)
										.findViewById(tempPosition);
								if (tempCheckBox != null) {
									tempCheckBox.setChecked(false);
								}
							}
							// 保存当前选中CheckBox的id值
							tempPosition = buttonView.getId();
						} else {// 当CheckBox被选中,又被取消时,将tempPosition重新初始化.
							tempPosition = -1;

						}

					}
				});
//		positionb = position;
		holder.address_default.setOnClickListener(new OnClickListener() {

			// http://115.28.69.240/boxing/app/address_default.php?address_id=1&user_id=1
			@Override
			public void onClick(View v) {
				if (tempPosition==-1) {
					return;
				}
				address_id = AddressLists.get(0).getAddress().get(tempPosition)
						.getAddress_id();
				Config.Address_name= AddressLists.get(0).getAddress().get(tempPosition).getAddress_name();
				Config.Address_tel= AddressLists.get(0).getAddress().get(tempPosition).getAddress_tel();
				Config.Address_address= AddressLists.get(0).getAddress().get(tempPosition).getAddress_content();
				x.http().get(
						new RequestParams(Config.ip + Config.app
								+ "/address_default.php?address_id=" + address_id
								+ "&user_id=" + Config.userID),
						new CommonCallback<String>() {

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(String arg0) {
								// 关闭选择默认地址的,当选定新的默认地址后,会自动给关闭
								// if(context instanceof Activity){
								// if(Activity.class.isInstance(context))
								// {
								// // 转化为activity，然后finish就行了
								// Activity activity = (Activity)context;
								// activity.finish();
								// }
								// }
								try {
									JSONObject json=new JSONObject(arg0);
									String code=json.getString("code");
									if (code.equals("1")) {
										Toast.makeText(context, "默认地址修改完毕", Toast.LENGTH_SHORT).show();
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						});

			}
		});
		// 删除操作
		holder.lv_address_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// http://115.28.69.240/boxing/app/address_del.php?address_id=5
				int a = (Integer) v.getTag();
				delete_address_id = AddressLists.get(0).getAddress().get(a).getAddress_id();
				x.http().get(
						new RequestParams(Config.ip
								+ Config.app
								+ "/address_del.php?address_id="
								+ delete_address_id),
						new CommonCallback<String>() {

							@Override
							public void onCancelled(CancelledException arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onError(Throwable arg0, boolean arg1) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFinished() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(String arg0) {
								System.out.println("删除数据成功");

							}
						});
				AddressLists.get(0).getAddress().remove(a);
				if (GoodsAdressAdapter.this.address_id.equals("-1")) {
//					if (GoodsAdressAdapter.this.address_id.equals(delete_address_id)) {
						Config.Address_name=" ";
						Config.Address_tel=" ";
						Config.Address_address=" ";
//					}
				}

				notifyDataSetChanged();

			}
		});
		//编辑操作
		holder.lv_address_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int i = (Integer) v.getTag();
				if (i>=0) {
					Intent intent=new Intent(context,EditAddress.class);
					String address_id=AddressLists.get(0).getAddress().get(i).getAddress_id();
					String name=AddressLists.get(0).getAddress().get(i).getAddress_name();
					String tel=AddressLists.get(0).getAddress().get(i).getAddress_tel();
					String postcode=AddressLists.get(0).getAddress().get(i).getAddress_postcode();
					String content=AddressLists.get(0).getAddress().get(i).getAddress_content();
					intent.putExtra("index", i);
					intent.putExtra("address_id", address_id);
					intent.putExtra("name", name);
					intent.putExtra("tel", tel);
					intent.putExtra("postcode", postcode);
					intent.putExtra("content", content);
					((Activity)context).startActivityForResult(intent, 2);
				}
			}
		});

		return convertView;
	}

	public static class ViewHolder {
		TextView receiver_name;
		TextView receiver_tel;
		TextView receiver_content;
		CheckBox address_default;
		ImageView address_edit_icon;
		TextView address_edit;
		ImageView address_delete_icon;
		TextView address_delete;
		LinearLayout lv_address_edit;
		LinearLayout lv_address_delete;

	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch (v.getId()) {
//		case R.id.address_edit_icon:
//		case R.id.address_edit:
//			
//			break;
//		case R.id.address_delete_icon:
//		case R.id.address_delete:
//			// http://115.28.69.240/boxing/app/address_del.php?address_id=5
//			if (tempPosition != -1) {
//				x.http().get(
//						new RequestParams(Config.ip
//								+ Config.app
//								+ "/address_del.php?address_id="
//								+ AddressLists.get(0).getAddress()
//										.get(tempPosition).getAddress_id()),
//						new CommonCallback<String>() {
//
//							@Override
//							public void onCancelled(CancelledException arg0) {
//								// TODO Auto-generated method stub
//								System.out.println("删除失败" + tempPosition);
//							}
//
//							@Override
//							public void onError(Throwable arg0, boolean arg1) {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onFinished() {
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onSuccess(String arg0) {
//								// TODO Auto-generated method stub
//								System.out.println("删除地址的状态码:" + arg0);
//							}
//						});
//				AddressLists.get(0).getAddress().remove(getItem(tempPosition));
//				notifyDataSetChanged();
//			}
//
//			break;
//
//		default:
//			break;
//		}
//	}

//	public void SetRefreshData(RefreshData refreshdata) {
//		this.refreshdata = refreshdata;
//	}

	/**
//	 * 回调,用于将编辑的点击事件传递到activity中
//	 */
//	public interface RefreshData {
//		void refreshing(View v);
//	}
//
	// 返回当前CheckBox选中的位置,便于获取值.
	public int getSelectPosition() {
		return tempPosition;
	}


}
