package com.bigdata.xinhuanufang.bean;

public class LuckUserBean {
	/**
	 *  "think_userid":"1",
	 "think_title":"苹果手机",
	 "user_username":"九爷"
	 */
	private String think_userid;
	private String think_title;
	private String user_username;
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
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public LuckUserBean(String think_userid, String think_title,
						String user_username) {
		this.think_userid = think_userid;
		this.think_title = think_title;
		this.user_username = user_username;
	}

}
