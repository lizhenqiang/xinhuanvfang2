package com.bigdata.xinhuanufang.bean;

public class AddJinShouTao {
/**
 *  "think_id":"2",
    "think_percent":"21",
    "thinkjoin_gloves":"5500",
    "user_id":"1",
    "thinkjoin_bili":"55.00000"
 */
	
	private String think_id;
	private String think_percent;
	private String thinkjoin_gloves;
	private String user_id;
	private String thinkjoin_bili;
	public String getThink_id() {
		return think_id;
	}
	public void setThink_id(String think_id) {
		this.think_id = think_id;
	}
	public String getThink_percent() {
		return think_percent;
	}
	public void setThink_percent(String think_percent) {
		this.think_percent = think_percent;
	}
	public String getThinkjoin_gloves() {
		return thinkjoin_gloves;
	}
	public void setThinkjoin_gloves(String thinkjoin_gloves) {
		this.thinkjoin_gloves = thinkjoin_gloves;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getThinkjoin_bili() {
		return thinkjoin_bili;
	}
	public void setThinkjoin_bili(String thinkjoin_bili) {
		this.thinkjoin_bili = thinkjoin_bili;
	}
	public AddJinShouTao(String think_id, String think_percent,
			String thinkjoin_gloves, String user_id, String thinkjoin_bili) {
		this.think_id = think_id;
		this.think_percent = think_percent;
		this.thinkjoin_gloves = thinkjoin_gloves;
		this.user_id = user_id;
		this.thinkjoin_bili = thinkjoin_bili;
	}
	
	
}
