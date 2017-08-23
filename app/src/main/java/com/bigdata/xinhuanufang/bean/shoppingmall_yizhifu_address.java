package com.bigdata.xinhuanufang.bean;

public class shoppingmall_yizhifu_address {
	/**
	 *  "address_name":"九爷",
	 "address_tel":"18519011617",
	 "address_content":"北京市海淀区上地南路科贸大厦408"
	 */
	private String address_name;
	private String address_tel;
	private String address_content;
	public String getAddress_name() {
		return address_name;
	}
	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
	public String getAddress_tel() {
		return address_tel;
	}
	public void setAddress_tel(String address_tel) {
		this.address_tel = address_tel;
	}
	public String getAddress_content() {
		return address_content;
	}
	public void setAddress_content(String address_content) {
		this.address_content = address_content;
	}
	public shoppingmall_yizhifu_address(String address_name,
										String address_tel, String address_content) {
		this.address_name = address_name;
		this.address_tel = address_tel;
		this.address_content = address_content;
	}

}
