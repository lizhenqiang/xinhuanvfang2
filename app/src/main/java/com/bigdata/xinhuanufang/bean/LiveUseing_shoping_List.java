package com.bigdata.xinhuanufang.bean;

public class LiveUseing_shoping_List {
	/**
	 *  "shop_id":"1",
	 "shop_title":"Iphone6s plus全网通买一送二",
	 "shop_price":"8999.00",
	 "shop_pic":"/uploads/20170223135251.jpg"
	 */
	private String shop_id;
	private String shop_title;
	private String shop_price;
	private String shop_pic;
	public String getShop_id() {
		return shop_id;
	}
	public void setShop_id(String shop_id) {
		this.shop_id = shop_id;
	}
	public String getShop_title() {
		return shop_title;
	}
	public void setShop_title(String shop_title) {
		this.shop_title = shop_title;
	}
	public String getShop_price() {
		return shop_price;
	}
	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}
	public String getShop_pic() {
		return shop_pic;
	}
	public void setShop_pic(String shop_pic) {
		this.shop_pic = shop_pic;
	}
	public LiveUseing_shoping_List(String shop_id, String shop_title,
								   String shop_price, String shop_pic) {
		this.shop_id = shop_id;
		this.shop_title = shop_title;
		this.shop_price = shop_price;
		this.shop_pic = shop_pic;
	}

}
