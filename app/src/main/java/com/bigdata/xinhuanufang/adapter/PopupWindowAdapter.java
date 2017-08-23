package com.bigdata.xinhuanufang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.bean.Shop;
import com.bigdata.xinhuanufang.game.bean.shopingAttrsParams;

import java.util.HashMap;
import java.util.List;

public class PopupWindowAdapter extends BaseAdapter {

	private Context context;
	private List<Shop> list;
	private LayoutInflater mInflater;
	private shopingAttrsParams shopingattrs;
	private NatureDataListener setdatas;
	private int index;
	private HashMap<String, String> hashmap;

	public PopupWindowAdapter(Context context, List<Shop> list,
			shopingAttrsParams shopingattrs) {
		this.context = context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
		this.shopingattrs = shopingattrs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.get(0).getShopAttr().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(0).getShopAttr().get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if (convertView==null) {
			convertView = mInflater.inflate(
					R.layout.popupwindow_list, null);
			holder=new ViewHolder();
			
			holder.joinshoping_attr_id=(TextView) convertView.findViewById(R.id.joinshoping_attr_id);
			holder.joinshopingcar_color1=(Button) convertView.findViewById(R.id.joinshopingcar_color1);
			holder.joinshopingcar_color2=(Button) convertView.findViewById(R.id.joinshopingcar_color2);
			holder.joinshopingcar_color3=(Button) convertView.findViewById(R.id.joinshopingcar_color3);
			holder.joinshopingcar_color4=(Button) convertView.findViewById(R.id.joinshopingcar_color4);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		System.out.println("数据++"+position);
//		index=position;
		hashmap=new HashMap<String, String>();
		int a=list.get(0).getShopAttr().get(position).getChilds().size();
		holder.joinshoping_attr_id.setText(list.get(0).getShopAttr().get(position).getAttrName());
		if(list.get(0).getShopAttr().get(position).getChilds().size()>3) {
			// 商品颜色的id
			holder.joinshopingcar_color1.setText(list.get(0).getShopAttr()
						.get(position).getChilds().get(0).getAttrName());
			System.out.println("数据++"+list.get(0).getShopAttr()
					.get(position).getChilds().get(0).getAttrName());
			holder.joinshopingcar_color2.setText(list.get(0).getShopAttr()
					.get(position).getChilds().get(1).getAttrName());
			holder.joinshopingcar_color3.setText(list.get(0).getShopAttr()
					.get(position).getChilds().get(2).getAttrName());
			holder.joinshopingcar_color4.setText(list.get(0).getShopAttr()
					.get(position).getChilds().get(3).getAttrName());
			}else if(list.get(0).getShopAttr().get(position).getChilds().size()==3) {
			// 商品颜色的id
			holder.joinshopingcar_color1.setText(list.get(0).getShopAttr()
						.get(position).getChilds().get(0).getAttrName());
			holder.joinshopingcar_color2.setText(list.get(0).getShopAttr()
					.get(position).getChilds().get(1).getAttrName());
			holder.joinshopingcar_color3.setText(list.get(0).getShopAttr()
					.get(position).getChilds().get(2).getAttrName());
			holder.joinshopingcar_color4.setText("");
			holder.joinshopingcar_color4.setVisibility(View.INVISIBLE);
			}else if(list.get(0).getShopAttr().get(position).getChilds().size()==2) {
			// 商品颜色的id
			holder.joinshopingcar_color1.setText(list.get(0).getShopAttr()
						.get(position).getChilds().get(0).getAttrName());
			holder.joinshopingcar_color2.setText(list.get(0).getShopAttr()
					.get(position).getChilds().get(1).getAttrName());
			holder.joinshopingcar_color3.setText("");
			holder.joinshopingcar_color3.setVisibility(View.INVISIBLE);
			holder.joinshopingcar_color4.setText("");
			holder.joinshopingcar_color4.setVisibility(View.INVISIBLE);
			}
		if(list.get(0).getShopAttr().get(position).getChilds().size()==1) {
			// 商品颜色的id
			holder.joinshopingcar_color1.setText(list.get(0).getShopAttr()
						.get(position).getChilds().get(0).getAttrName());
			holder.joinshopingcar_color2.setText("");
			holder.joinshopingcar_color2.setVisibility(View.INVISIBLE);
			holder.joinshopingcar_color3.setText("");
			holder.joinshopingcar_color3.setVisibility(View.INVISIBLE);
			holder.joinshopingcar_color4.setText("");
			holder.joinshopingcar_color4.setVisibility(View.INVISIBLE);

			}
		holder.joinshopingcar_color1.setId(position);
		final ViewHolder viewholder=holder;
		holder.joinshopingcar_color1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewholder.joinshopingcar_color1.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color2.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color3.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color4.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color1.setBackgroundColor(Color.RED);
				int position1=v.getId();
				if (position1==0) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color1.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingColor(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
							shopingattrs.setShopingColorId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
						}
					
					}
				}if (position1==1) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color1.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingVolumeId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
							shopingattrs.setShopingVolume(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
						}
					}
				
				}if (position1==2) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color1.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingNetWorkId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
							shopingattrs.setShopingNetWork(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
						}
					}
				
				}
//				shoping_color_result.setText(button.getText().toString());
					setdatas.setdataing(shopingattrs);
			}
		});
		holder.joinshopingcar_color2.setId(position);
		holder.joinshopingcar_color2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewholder.joinshopingcar_color1.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color2.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color3.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color4.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color2.setBackgroundColor(Color.RED);
				
					System.out.println("现在点击的position"+v.getId());
					// 商品颜色的id
				int position2=v.getId();
					if (position2==0) {
						for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
								.size(); i++) {
							if (viewholder.joinshopingcar_color2.getText().equals(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName())) {
								shopingattrs.setShopingColor(list.get(0).getShopAttr()
										.get(v.getId()).getChilds().get(i).getAttrName());
								shopingattrs.setShopingColorId(list.get(0).getShopAttr()
										.get(v.getId()).getChilds().get(i).getAttrId());
							}
						
						}
					}if (position2==1) {
						for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
								.size(); i++) {
							if (viewholder.joinshopingcar_color2.getText().equals(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName())) {
								shopingattrs.setShopingVolumeId(list.get(0).getShopAttr()
										.get(v.getId()).getChilds().get(i).getAttrId());
								shopingattrs.setShopingVolume(list.get(0).getShopAttr()
										.get(v.getId()).getChilds().get(i).getAttrName());
							}
						}
					
					}if (position2==2) {
						for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
								.size(); i++) {
							if (viewholder.joinshopingcar_color2.getText().equals(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName())) {
								shopingattrs.setShopingNetWorkId(list.get(0).getShopAttr()
										.get(v.getId()).getChilds().get(i).getAttrId());
								shopingattrs.setShopingNetWork(list.get(0).getShopAttr()
										.get(v.getId()).getChilds().get(i).getAttrName());
							}
						}
					
					}
//				shoping_color_result.setText(button.getText().toString());
					setdatas.setdataing(shopingattrs);
			}
		});
		holder.joinshopingcar_color3.setId(position);
		holder.joinshopingcar_color3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewholder.joinshopingcar_color1.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color2.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color3.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color4.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color3.setBackgroundColor(Color.RED);
				int position3=v.getId();
				if (position3==0) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color3.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingColor(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
							shopingattrs.setShopingColorId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
						}
					
					}
				}if (position3==1) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color3.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingVolumeId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
							shopingattrs.setShopingVolume(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
						}
					}
				
				}if (position3==2) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color3.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingNetWorkId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
							shopingattrs.setShopingNetWork(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
						}
					}
				
				}
//				shoping_color_result.setText(button.getText().toString());
					setdatas.setdataing(shopingattrs);
			}
		});
		holder.joinshopingcar_color4.setId(position);
		holder.joinshopingcar_color4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewholder.joinshopingcar_color1.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color2.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color3.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color4.setBackgroundColor(Color.parseColor("#06000000"));
				viewholder.joinshopingcar_color4.setBackgroundColor(Color.RED);
				int position4=v.getId();
				if (position4==0) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color4.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingColor(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
							shopingattrs.setShopingColorId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
						}
					
					}
				}if (position4==1) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color4.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingVolumeId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
							shopingattrs.setShopingVolume(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
						}
					}
				
				}if (position4==2) {
					for (int i = 0; i < (list.get(0).getShopAttr().get(v.getId()).getChilds())
							.size(); i++) {
						if (viewholder.joinshopingcar_color4.getText().equals(list.get(0).getShopAttr()
								.get(v.getId()).getChilds().get(i).getAttrName())) {
							shopingattrs.setShopingNetWorkId(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrId());
							shopingattrs.setShopingNetWork(list.get(0).getShopAttr()
									.get(v.getId()).getChilds().get(i).getAttrName());
						}
					}
				
				}
//				shoping_color_result.setText(button.getText().toString());
					setdatas.setdataing(shopingattrs);
			}
		});
		
		return convertView;
	}

	static class ViewHolder {
		TextView joinshoping_attr_id;
		Button joinshopingcar_color1;
		Button joinshopingcar_color2;
		Button joinshopingcar_color3;
		Button joinshopingcar_color4;
	}

	public interface NatureDataListener {
		void setdataing(shopingAttrsParams shopingattrs);
	}

	public void setNatureDataListener(NatureDataListener naturedatalistener) {
		this.setdatas = naturedatalistener;
	}


}
