package com.bigdata.xinhuanufang.bean;

public class MessageBoardBean {
	/**
	 *  "message_id":"8",
	 "message_userid":"1",
	 "message_liveguessid":"15",
	 "message_content":"一枪敲碎你的脑袋哈哈哈",
	 "message_date":"1490524815",
	 "user_username":"九爷",
	 "user_head":"/uploads/20170220141429.jpg"
	 */
	private String message_id;
	private String message_userid;
	private String message_liveguessid;
	private String message_content;
	private String message_date;
	private String user_username;
	private String user_head;
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getMessage_userid() {
		return message_userid;
	}
	public void setMessage_userid(String message_userid) {
		this.message_userid = message_userid;
	}
	public String getMessage_liveguessid() {
		return message_liveguessid;
	}
	public void setMessage_liveguessid(String message_liveguessid) {
		this.message_liveguessid = message_liveguessid;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getMessage_date() {
		return message_date;
	}
	public void setMessage_date(String message_date) {
		this.message_date = message_date;
	}
	public String getUser_username() {
		return user_username;
	}
	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}
	public String getUser_head() {
		return user_head;
	}
	public void setUser_head(String user_head) {
		this.user_head = user_head;
	}
	public MessageBoardBean(String message_id, String message_userid,
							String message_liveguessid, String message_content,
							String message_date, String user_username, String user_head) {
		this.message_id = message_id;
		this.message_userid = message_userid;
		this.message_liveguessid = message_liveguessid;
		this.message_content = message_content;
		this.message_date = message_date;
		this.user_username = user_username;
		this.user_head = user_head;
	}

}
