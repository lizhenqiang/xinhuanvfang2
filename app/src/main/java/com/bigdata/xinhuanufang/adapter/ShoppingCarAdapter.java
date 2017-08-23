package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.NetworkDataCar;
import com.bigdata.xinhuanufang.game.bean.shopingInfoRecord;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.LinkedHashMap;
import java.util.List;

public class ShoppingCarAdapter extends BaseAdapter  {

	private Context context;
	private List<NetworkDataCar> lists;
	private LayoutInflater mInflater;
	private String bianji = "编辑";
	private String money;// 价格统计
	public boolean itemIsCheckState = false;
	private int positionb = 0;// 显示
	private String choose = "-1";
	private shopingInfoRecord shopingInfo;// 记录shangping
	private onClick click;
	private LinkedHashMap<Integer, Boolean> isSelected;
	private NetworkDataCar network;
	public String isquanchoose="-1";
	private boolean isdianji=false;
	public ShoppingCarAdapter(Context context,LinkedHashMap<Integer, Boolean> isSelected) {
		this.context = context;
		this.isSelected = isSelected;
	}

	public ShoppingCarAdapter(Context context, List<NetworkDataCar> finghterOne) {
		this.context = context;
		this.lists = finghterOne;
		isSelected = new LinkedHashMap();
		mInflater = LayoutInflater.from(context);
		initData();
	}

	public List<NetworkDataCar> getList() {
		return lists;
	}

	private void initData() {
		for (int i = 0; i < lists.get(0).getCart().size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	public LinkedHashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}
	public List<NetworkDataCar> getlists() {
		return lists;
	}

	public void setIsSelected(LinkedHashMap<Integer, Boolean> isSelected) {
		this.isSelected = isSelected;
	}
	public void setisdianji(boolean isdianji) {
		this.isdianji = isdianji;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.get(0).getCart().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(0).getCart().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.shoppingcar_listview_item,
					null);
			holder = new ViewHolder();
			holder.state_choose = (CheckBox) convertView
					.findViewById(R.id.cb_sli_choiceOrNot);
			holder.shoping_picture = (ImageView) convertView
					.findViewById(R.id.iv_sli_productPhoto);
			holder.shoping_name = (TextView) convertView
					.findViewById(R.id.tv_sli_productName);
			holder.shoping_color = (TextView) convertView
					.findViewById(R.id.shoping_color);
			holder.shoping_spec = (TextView) convertView
					.findViewById(R.id.shoping_spec);
			holder.shoping_model = (TextView) convertView
					.findViewById(R.id.shoping_model);
			holder.shoping_rate = (TextView) convertView
					.findViewById(R.id.tv_sli_rate);
			holder.shoping_delete = (TextView) convertView
					.findViewById(R.id.tv_sli_delete);
			holder.shoping_add = (ImageButton) convertView
					.findViewById(R.id.shoping_zengjia);
			holder.shoping_count = (TextView) convertView
					.findViewById(R.id.shoping_count);
			holder.shoping_reduce = (ImageButton) convertView
					.findViewById(R.id.shoping_jianshao);
			holder.shopingcar_number = (TextView) convertView
					.findViewById(R.id.shopingcar_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		// 获取商品的数量进行记录

		// 商品的图片根据拿过来的URL地址进行加载
		// 商品的属性在拿到数据装进集合里面一起传进来
		holder.shoping_name.setText(lists.get(0).getCart().get(position)
				.getShop_title());
		holder.shoping_rate.setText("￥"
				+ lists.get(0).getCart().get(position).getCart_price());
		// 商品图片
		x.image().bind(holder.shoping_picture,
				Config.ip + lists.get(0).getCart().get(position).getShop_pic());

		// 设置商品的属性
		// 先截取网络数据中属性的字段
		String s = new String(lists.get(0).getCart().get(position)
				.getCart_attr());

		String a[] = s.split(",");
		String rolume = "";// 内存
		String network = "";// 网络
		String color = "";// 颜色
		if (a.length == 1) {
			rolume = a[0];
		} else if (a.length == 2) {
			rolume = a[0];
			network = a[1];
		} else if (a.length == 3) {
			rolume = a[0];
			network = a[1];
			color = a[2];
		}
		for (int i = 0; i < lists.get(0).getCart().get(position).getAttrs()
				.size(); i++) {
			for (int j = 0; j < lists.get(0).getCart().get(position).getAttrs()
					.get(i).getChilds().size(); j++) {
				if (color.equals(lists.get(0).getCart().get(position)
						.getAttrs().get(i).getChilds().get(j).getAttr_id())) {
					holder.shoping_color.setText(lists.get(0).getCart()
							.get(position).getAttrs().get(i).getChilds().get(j)
							.getAttr_name());

				}
				if (network.equals(lists.get(0).getCart().get(position)
						.getAttrs().get(i).getChilds().get(j).getAttr_id())) {
					holder.shoping_spec.setText(lists.get(0).getCart()
							.get(position).getAttrs().get(i).getChilds().get(j)
							.getAttr_name());
				}
				if (rolume.equals(lists.get(0).getCart().get(position)
						.getAttrs().get(i).getChilds().get(j).getAttr_id())) {
					holder.shoping_model.setText(lists.get(0).getCart()
							.get(position).getAttrs().get(i).getChilds().get(j)
							.getAttr_name());
				}
			}

		}



		// holder.state_choose.setOnClickListener(this);
		if (bianji.equals("编辑")) {
			// 隐藏

			holder.shoping_add.setVisibility(View.INVISIBLE);
			holder.shoping_reduce.setVisibility(View.INVISIBLE);
			holder.shoping_count.setVisibility(View.INVISIBLE);
			holder.shopingcar_number.setVisibility(View.VISIBLE);
//			holder.state_choose.setChecked(itemIsCheckState);

		}
		if (bianji.equals("完成")) {
			// 显示

			holder.shoping_add.setVisibility(View.VISIBLE);
			holder.shoping_reduce.setVisibility(View.VISIBLE);
			holder.shoping_count.setVisibility(View.VISIBLE);
			holder.shopingcar_number.setVisibility(View.INVISIBLE);
//			holder.state_choose.setChecked(itemIsCheckState);
		}
		System.out.println("是否选中----"+choose);
		if (choose.equals("全选")) {
			// 显示
			holder.state_choose.setChecked(true);
			for (int i = 0; i < lists.get(0).getCart().size(); i++) {
				lists.get(0).getCart().get(i).setIsxuanzhong(true);
			}
			isquanchoose="全选";
			choose="1";
		}
		if (choose.equals("不全选")) {
			isquanchoose="不全选";
			holder.state_choose.setChecked(false);
			for (int i = 0; i < lists.get(0).getCart().size(); i++) {
				lists.get(0).getCart().get(i).setIsxuanzhong(false);
			}
			choose="1";
		}
		holder.state_choose.setChecked(isSelected.get(position));
		// 设置商品的数量
		int num = Integer.parseInt(lists.get(0).getCart().get(position)
				.getCart_num());
		// 设置商品数量的,要从网络上的数据中拿出来
		holder.shoping_count.setText(lists.get(0).getCart().get(position)
				.getCart_num());
		double aPrice = Double.parseDouble((lists.get(0).getCart()
				.get(position).getCart_price()));

		// 在非编辑状态下显示商品的数量
		holder.shopingcar_number.setText("×" + num);

		holder.shoping_rate.setText(String.valueOf(num * aPrice));
		// 商品数量的增加和减少的监听
		holder.shoping_reduce.setOnClickListener(new ClickListener(position,
				holder.shoping_count, holder.shoping_rate));
		holder.shoping_add.setOnClickListener(new ClickListener(position,
				holder.shoping_count, holder.shoping_rate));
		if (positionb == position && choose.equals("不全选")) {
			holder.state_choose.setChecked(getIsSelected().get(position));
		}
//		final int pos=position;
		holder.state_choose.setTag(position);
		holder.state_choose.setOnCheckedChangeListener(null);
			if (lists.get(0).getCart().get(position).isxuanzhong()) {
				holder.state_choose.setChecked(true);
			}else if (!lists.get(0).getCart().get(position).isxuanzhong()) {
				holder.state_choose.setChecked(false);
			}

		holder.state_choose
				.setOnCheckedChangeListener(new CheckBoxChangeListener());
		//删除商品的按钮
		holder.shoping_delete.setId(position);
		holder.shoping_delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int index=v.getId();
				// TODO Auto-generated method stub
				x.http().get(
						new RequestParams(Config.ip + Config.app
								+ "/shopcart_del.php?cartids="
								+ lists.get(0).getCart().get(index).getCart_id()),
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
								Toast.makeText(context, "移除商品成功", Toast.LENGTH_SHORT).show();
								lists.get(0).getCart().remove(index);
								notifyDataSetChanged();

							}

						});

			}

		});

		return convertView;
	}

	static class ViewHolder {
		CheckBox state_choose;// 商品的选择状态
		ImageView shoping_picture;// 商品的图片展示
		TextView shoping_name;// 商品的名称

		TextView shoping_color;// 商品的颜色
		TextView shoping_spec;// 商品的规格
		TextView shoping_model;// 商品的型号

		TextView shoping_rate;// 商品的价格
		TextView shoping_delete;// 删除这一款商品
		ImageButton shoping_add;// 增加单个商品的数量
		TextView shoping_count;// 单个商品的数量
		ImageButton shoping_reduce;// 减少单个商品
		TextView shopingcar_number;// 商品单个数量的显示
	}

	private class CheckBoxChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(buttonView.isPressed()) {
				int position = (Integer) buttonView.getTag();
				lists.get(0).getCart().get(position).setIsxuanzhong(isChecked);
				System.out.println("是否选中"+isChecked);
				getIsSelected().put(position, isChecked);
				if (changeListener != null) {
					changeListener.onChanged(position, isChecked, isquanchoose, lists.get(0).getCart().size());
				}
			}
		}
	}

	/**
	 * 选中按钮监听回调
	 */
	private onCheckBoxChangeListener changeListener;

	public void setOnBoxChangeListener(onCheckBoxChangeListener changeListener) {
		this.changeListener = changeListener;
	}

	public interface onCheckBoxChangeListener {
		void onChanged(int position, boolean isChecked,String isquanchoose,int size);
	}

	/**
	 * 加减按钮的监听
	 *
	 * @author weiyu$
	 *
	 */
	private class ClickListener implements OnClickListener {
		private int position;// 条目的索引
		private TextView edt;// 商品数量
		private String totalPrice;// 单个商品的总价格
		private TextView totalTv;// 单个商品的总价格

		public ClickListener(int position, TextView edt, TextView totalTv) {
			this.position = position;// 条目的索引
			this.edt = edt; // 商品数量的编辑
			this.totalTv = totalTv; // 单个商品价格的总和
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 减少
			case R.id.shoping_jianshao:
				int num = Integer.parseInt(lists.get(0).getCart().get(position)
						.getCart_num().trim());
				if (num > 0) {
					lists.get(0).getCart().get(position)
							.setCart_num((num - 1) + "".trim());
					edt.setText(lists.get(0).getCart().get(position)
							.getCart_num());
					double itemTotalPrice = Double.parseDouble((lists
							.get(0).getCart().get(position).getCart_num()))
							* Double.parseDouble((lists.get(0).getCart()
									.get(position).getCart_price()));
					totalTv.setText(String.valueOf(itemTotalPrice));
					if (click != null) {
						click.mins(
								position,
								Integer.parseInt(lists.get(0).getCart()
										.get(position).getCart_num()), itemTotalPrice);
					}
				}
				break;
			// 增加 void add(int position, int num, double itemTotal);
			case R.id.shoping_zengjia:
				int num1 = Integer.parseInt(lists.get(0).getCart()
						.get(position).getCart_num().trim());

				lists.get(0).getCart().get(position)
						.setCart_num((num1 + 1) + "".trim());

				edt.setText(lists.get(0).getCart().get(position).getCart_num()
						+ "".trim());
				// int
				double itemTotalPrice = Double.parseDouble((lists.get(0)
						.getCart().get(position).getCart_num()))
						* Double.parseDouble((lists.get(0).getCart()
								.get(position).getCart_price()));

				totalTv.setText(String.valueOf(itemTotalPrice));
				int s = Integer.parseInt(lists.get(0).getCart().get(position)
						.getCart_num());
				if (click != null) {
					click.add(position, s, itemTotalPrice);
				}
				break;

			default:
				break;
			}

		}

	}

	public void setOnClick(onClick click) {
		this.click = click;
	}

	/**
	 * 加减号的点击监听
	 *
	 * @author Administrator
	 *
	 */

	public interface onClick {
		void add(int position, int num, double itemTotal);

		void mins(int position, int num, double itemTotal);
	}

	public void setEditState(String s) {
		bianji = s;
	}

	// 将条目选择状态和索引传递过来
	public void setShopingChooseState(int positionb, boolean b) {
		itemIsCheckState = b;
		this.positionb = positionb;
	}

	public void setAllChooseState(String s) {
		choose = s;
	}


}