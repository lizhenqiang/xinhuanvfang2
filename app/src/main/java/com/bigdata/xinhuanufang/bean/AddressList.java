package com.bigdata.xinhuanufang.bean;

/**
 * "address_id":"2",
            "address_status":"0",
            "address_name":"С�ž�",
            "address_tel":"18311490115",
            "address_postcode":"100390",
            "address_content":"�����к������ϵ���·��ó����408"
 * @author weiyu$
 *
 */
public class AddressList {
	private String address_id;
	private String address_status;
	private String address_name;
	private String address_tel;
	private String address_postcode;
	private String address_content;
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public String getAddress_status() {
		return address_status;
	}
	public void setAddress_status(String address_status) {
		this.address_status = address_status;
	}
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
	public String getAddress_postcode() {
		return address_postcode;
	}
	public void setAddress_postcode(String address_postcode) {
		this.address_postcode = address_postcode;
	}
	public String getAddress_content() {
		return address_content;
	}
	public void setAddress_content(String address_content) {
		this.address_content = address_content;
	}
	public AddressList(String address_id, String address_status,
			String address_name, String address_tel, String address_postcode,
			String address_content) {
		this.address_id = address_id;
		this.address_status = address_status;
		this.address_name = address_name;
		this.address_tel = address_tel;
		this.address_postcode = address_postcode;
		this.address_content = address_content;
	}
	
}
