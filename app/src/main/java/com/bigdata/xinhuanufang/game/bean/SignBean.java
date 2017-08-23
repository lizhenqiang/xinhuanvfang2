package com.bigdata.xinhuanufang.game.bean;

public class SignBean {
/**
 *  "date":"31",
    "sum":"2"
 */
	private String date;
	private String sum;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public SignBean(String date, String sum) {
		this.date = date;
		this.sum = sum;
	}
	
}
