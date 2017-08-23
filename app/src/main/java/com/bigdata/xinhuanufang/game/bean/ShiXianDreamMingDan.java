package com.bigdata.xinhuanufang.game.bean;

public class ShiXianDreamMingDan {
/**
 * "think_id":"3",
            "think_userid":"1",
            "think_title":"ƻ���ֻ�",
            "think_pic":"/uploads/20170223135215.jpg",
            "user_username":"��ү",
            "count":"1"
 */
	private String think_id;
	private String think_userid;
	private String think_title;
	private String think_pic;
	private String user_username;
	private String count;
	public String getThink_id() {
		return think_id;
	}
	public void setThink_id(String think_id) {
		this.think_id = think_id;
	}
	public String getThink_userid() {
		return think_userid;
	}
	public void setThink_userid(String think_userid) {
		this.think_userid = think_userid;
	}
	public String getThink_title() {
		return think_title;
	}
	public void setThink_title(String think_title) {
		this.think_title = think_title;
	}
	public String getThink_pic() {
		return think_pic;
	}
	public void setThink_pic(String think_pic) {
		this.think_pic = think_pic;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public ShiXianDreamMingDan(String think_id, String think_userid,
			String think_title, String think_pic, String user_username,
			String count) {
		this.think_id = think_id;
		this.think_userid = think_userid;
		this.think_title = think_title;
		this.think_pic = think_pic;
		this.user_username = user_username;
		this.count = count;
	}
	
}
