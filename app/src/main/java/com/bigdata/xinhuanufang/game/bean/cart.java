package com.bigdata.xinhuanufang.game.bean;

import java.util.List;


public class cart {
	/**
	 * "cart_id":"9", "cart_attr":"2,7", "cart_userid":"1",
	 * "cart_shopid":"1", "cart_num":"1", "cart_price":"5999.00",
	 * "shop_title":"Iphone6s plus全网通买一送二",
	 * "shop_pic":"/uploads/20170223135251.jpg", "attr":"商务黑 16GB",
	 */
	private String cart_id;
	private String cart_attr;
	private String cart_userid;
	private String cart_shopid;
	private String cart_num;
	private String cart_price;
	private String shop_title;
	private String shop_pic;
	private String attr;
	private boolean isxuanzhong;
	private List<attrs> attrs;

	public boolean isxuanzhong() {
		return isxuanzhong;
	}

	public void setIsxuanzhong(boolean isxuanzhong) {
		this.isxuanzhong = isxuanzhong;
	}

	public String getCart_id() {
		return cart_id;
	}

	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}

	public String getCart_attr() {
		return cart_attr;
	}

	public void setCart_attr(String cart_attr) {
		this.cart_attr = cart_attr;
	}

	public String getCart_userid() {
		return cart_userid;
	}

	public void setCart_userid(String cart_userid) {
		this.cart_userid = cart_userid;
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

	public String getShop_title() {
		return shop_title;
	}

	public void setShop_title(String shop_title) {
		this.shop_title = shop_title;
	}

	public String getShop_pic() {
		return shop_pic;
	}

	public void setShop_pic(String shop_pic) {
		this.shop_pic = shop_pic;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public List<attrs> getAttrs() {
		return attrs;
	}

	public void setAttrs(List<attrs> attrs) {
		this.attrs = attrs;
	}

	public cart(String cart_id, String cart_attr,
				String cart_userid, String cart_shopid, String cart_num,
				String cart_price, String shop_title, String shop_pic,
				String attr, List<attrs> attrs) {
		this.cart_id = cart_id;
		this.cart_attr = cart_attr;
		this.cart_userid = cart_userid;
		this.cart_shopid = cart_shopid;
		this.cart_num = cart_num;
		this.cart_price = cart_price;
		this.shop_title = shop_title;
		this.shop_pic = shop_pic;
		this.attr = attr;
		this.attrs = attrs;
	}


}