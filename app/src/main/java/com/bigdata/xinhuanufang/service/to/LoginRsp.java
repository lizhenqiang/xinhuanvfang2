package com.bigdata.xinhuanufang.service.to;
/*
 * 返回Login信息的实体类
 * */
public class LoginRsp {
	private int code; // 请求结果代码 0失败 1成功
	private int user_id; // 用户编号
	private String user_tel; // 手机号
	private int user_gloves; // 金手套数量

	private String use_head; // 头像
	private String user_username; // 昵称

	private String user_pwd; // 密码
	private String user_sex; // 性别
	private String user_sign; // 个人签名
	private String user_date; // 日期

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

	public int getUser_gloves() {
		return user_gloves;
	}

	public void setUser_gloves(int user_gloves) {
		this.user_gloves = user_gloves;
	}

	public String getUse_head() {
		return use_head;
	}

	public void setUse_head(String use_head) {
		this.use_head = use_head;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_sign() {
		return user_sign;
	}

	public void setUser_sign(String user_sign) {
		this.user_sign = user_sign;
	}

	public String getUser_date() {
		return user_date;
	}

	public void setUser_date(String user_date) {
		this.user_date = user_date;
	}

}
