package com.bigdata.xinhuanufang.game.bean;

import java.util.List;

public class shoppingmall_yizhifu_cart {
/**
 *  "cart_id":"2",
                    "cart_shopid":"1",
                    "cart_num":"1",
                    "cart_price":"8999.00",
                    "attrs":"����� 16GB ",
                    "shop":{
                        "shop_title":"����VS����ȭ��",
                        "shop_pic":"/uploads/20170223135251.jpg"
                    }
 */
	private String cart_id;
	private String cart_shopid;
	private String cart_num;
	private String cart_price;
	private String attrs;
	private List<shoppingmall_yizhifu_cart_shop> shop;
	public String getCart_id() {
		return cart_id;
	}
	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
	public String getCart_shopid() {
		return cart_shopid;
	}
	public void setCart_shopid(String cart_shopid) {
		this.cart_shopid = cart_shopid;
	}
	public String getCart_num() {
		return cart_num;
	}
	public void setCart_num(String cart_num) {
		this.cart_num = cart_num;
	}
	public String getCart_price() {
		return cart_price;
	}
	public void setCart_price(String cart_price) {
		this.cart_price = cart_price;
	}
	public String getAttrs() {
		return attrs;
	}
	public void setAttrs(String attrs) {
		this.attrs = attrs;
	}
	public List<shoppingmall_yizhifu_cart_shop> getShop() {
		return shop;
	}
	public void setShop(List<shoppingmall_yizhifu_cart_shop> shop) {
		this.shop = shop;
	}
	public shoppingmall_yizhifu_cart(String cart_id, String cart_shopid,
			String cart_num, String cart_price, String attrs,
			List<shoppingmall_yizhifu_cart_shop> shop) {
		this.cart_id = cart_id;
		this.cart_shopid = cart_shopid;
		this.cart_num = cart_num;
		this.cart_price = cart_price;
		this.attrs = attrs;
		this.shop = shop;
	}
	
	
}
