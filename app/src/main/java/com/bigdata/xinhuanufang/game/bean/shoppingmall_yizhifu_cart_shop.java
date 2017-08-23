package com.bigdata.xinhuanufang.game.bean;

public class shoppingmall_yizhifu_cart_shop {
/**
 * "shop_title":"����VS����ȭ��",
                        "shop_pic":"/uploads/20170223135251.jpg"
                        �̳�  �ҵĶ���-->��֧��
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
