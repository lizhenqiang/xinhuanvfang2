package com.bigdata.xinhuanufang.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.yuanjiaoImage;
import com.bigdata.xinhuanufang.utils.Config;

import org.xutils.x;

public class ShoppingOrderInfo extends BaseActivity{
private TextView shoping_address_name;
private TextView shoping_address_tel;
private TextView shoping_address_content;
private TextView shoppingmall_details_shopping_bianhao;
private yuanjiaoImage shoppingmall_details_shopping_pic;
private TextView shoppingmall_details_shopping_title;
private TextView shoppingmall_details_shopping_attrs;
private TextView shoppingmall_details_shopping_price;
private TextView shoppingmall_details_shopping_num;
private TextView shoppingmall_details_shopping_money;
private Button shoppingmall_details_shopping_contact;
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.avtivity_shoppingmall_details;
	}
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setGone();
		setTitle("订单详情");
		setBack();
		//收货人姓名
		shoping_address_name=(TextView) findViewById(R.id.shoping_address_name);
		//手机号
		shoping_address_tel=(TextView) findViewById(R.id.shoping_address_tel);
		//收货地址
		shoping_address_content=(TextView) findViewById(R.id.shoping_address_content);
		//订单编号
		shoppingmall_details_shopping_bianhao=(TextView) findViewById(R.id.shoppingmall_details_shopping_bianhao);
		//商品图片
		shoppingmall_details_shopping_pic=(yuanjiaoImage) findViewById(R.id.shoppingmall_details_shopping_pic);
		//商品名称
		shoppingmall_details_shopping_title=(TextView) findViewById(R.id.shoppingmall_details_shopping_title);
		//商品属性
		shoppingmall_details_shopping_attrs=(TextView) findViewById(R.id.shoppingmall_details_shopping_attrs);
		//商品价格
		shoppingmall_details_shopping_price=(TextView) findViewById(R.id.shoppingmall_details_shopping_price);
		//商品数量
		shoppingmall_details_shopping_num=(TextView) findViewById(R.id.shoppingmall_details_shopping_num);
		//实际付款金额
		shoppingmall_details_shopping_money=(TextView) findViewById(R.id.shoppingmall_details_shopping_money);
		//联系商家
		shoppingmall_details_shopping_contact=(Button) findViewById(R.id.shoppingmall_details_shopping_contact);
		shoppingmall_details_shopping_contact.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.phonenumber));
				if (ActivityCompat.checkSelfPermission(ShoppingOrderInfo.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					ActivityCompat.requestPermissions(ShoppingOrderInfo.this, new String[]{Manifest.permission.CALL_PHONE}, 120);
					return;
				}
				startActivity(intent);
			}
		});
		
		//给控件设置数据
		Intent intent=getIntent();
		shoping_address_name.setText(intent.getStringExtra("shoping_address_name"));
		shoping_address_tel.setText(intent.getStringExtra("shoping_address_tel"));
		shoping_address_content.setText(intent.getStringExtra("shoping_address_content"));
		shoppingmall_details_shopping_bianhao.setText("订单编号:  "+intent.getStringExtra("shoppingmall_details_shopping_bianhao"));
		shoppingmall_details_shopping_title.setText(intent.getStringExtra("shoppingmall_details_shopping_title"));
		shoppingmall_details_shopping_attrs.setText("商品规格分类: "+intent.getStringExtra("shoppingmall_details_shopping_attrs"));
		shoppingmall_details_shopping_price.setText("¥ "+intent.getStringExtra("shoppingmall_details_shopping_price"));
		shoppingmall_details_shopping_num.setText("× "+intent.getStringExtra("shoppingmall_details_shopping_num"));
		shoppingmall_details_shopping_money.setText("实付款:  ¥"+intent.getStringExtra("shoppingmall_details_shopping_money"));
		x.image().bind(shoppingmall_details_shopping_pic, Config.ip+intent.getStringExtra("shoppingmall_details_shopping_pic"));
		
	}

}
