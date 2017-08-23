package com.bigdata.xinhuanufang.bean;

public class shoppingmall_yizhifu_cart_shop {
	/**
	 * "shop_title":"黑人VS白人拳赛",
	 "shop_pic":"/uploads/20170223135251.jpg"
	 商城  我的订单-->已支付
	 */

	private String shop_title;
	private String shop_pic;
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
	public shoppingmall_yizhifu_cart_shop(String shop_title, String shop_pic) {
		this.shop_title = shop_title;
		this.shop_pic = shop_pic;
	}

}
