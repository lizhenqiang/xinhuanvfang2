package com.bigdata.xinhuanufang.bean;

public class MainPrices {
	/**
	 * "layer_id":"40",
	 "layer_flag":"0",
	 "layer_pic":"/uploads/20170218211820234022.jpg",
	 "layer_status":"0"
	 */
	private String layer_id;
	private String layer_flag;
	private String layer_pic;
	private String layer_status;
	public String getLayer_id() {
		return layer_id;
	}
	public void setLayer_id(String layer_id) {
		this.layer_id = layer_id;
	}
	public String getLayer_flag() {
		return layer_flag;
	}
	public void setLayer_flag(String layer_flag) {
		this.layer_flag = layer_flag;
	}
	public String getLayer_pic() {
		return layer_pic;
	}
	public void setLayer_pic(String layer_pic) {
		this.layer_pic = layer_pic;
	}
	public String getLayer_status() {
		return layer_status;
	}
	public void setLayer_status(String layer_status) {
		this.layer_status = layer_status;
	}
	public MainPrices(String layer_id, String layer_flag, String layer_pic,
					  String layer_status) {
		this.layer_id = layer_id;
		this.layer_flag = layer_flag;
		this.layer_pic = layer_pic;
		this.layer_status = layer_status;
	}

}
