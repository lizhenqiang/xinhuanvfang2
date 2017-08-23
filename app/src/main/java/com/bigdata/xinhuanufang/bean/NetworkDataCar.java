package com.bigdata.xinhuanufang.bean;

import java.util.List;

public class NetworkDataCar {

	private String code;
	private boolean isCheckbox;//记录checkbox是否选中
	private List<cart> cart;

	public String getCode() {
		return code;
	}

	public boolean isCheckbox() {
		return isCheckbox;
	}



	public void setCheckbox(boolean isCheckbox) {
		this.isCheckbox = isCheckbox;
	}






	public void setCode(String code) {
		this.code = code;
	}

	public List<cart> getCart() {
		return cart;
	}

	public void setCart(List<cart> cart) {
		this.cart = cart;
	}

	public NetworkDataCar(String code, List<cart> cart) {
		this.code = code;
		this.cart = cart;
	}


}
