package com.bigdata.xinhuanufang.game.bean;

import java.util.List;

public class UserAddress {
/**
 * "code":1,
    "address
 */
	private String code;
	private List<AddressList> address;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<AddressList> getAddress() {
		return address;
	}
	public void setAddress(List<AddressList> address) {
		this.address = address;
	}
	public UserAddress(String code, List<AddressList> address) {
		this.code = code;
		this.address = address;
	}
	
	

}
