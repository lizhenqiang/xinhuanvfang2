package com.bigdata.xinhuanufang.game.bean;

import java.util.List;

public class LiveUseing_shoping {
/**
 * "code":1,
    "shop_list
 */
	private String code;
	private List<LiveUseing_shoping_List> shop_list;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<LiveUseing_shoping_List> getShop_list() {
		return shop_list;
	}
	public void setShop_list(List<LiveUseing_shoping_List> shop_list) {
		this.shop_list = shop_list;
	}
	public LiveUseing_shoping(String code,
			List<LiveUseing_shoping_List> shop_list) {
		this.code = code;
		this.shop_list = shop_list;
	}
	
	
}
