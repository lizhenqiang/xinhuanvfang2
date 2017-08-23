package com.bigdata.xinhuanufang.model;


public class UserInfo {
	public static final String USERNUM = "user_tel";
	public static final String PASSWORD = "user_pwd";
	
	private String user_pwd;// 用户密码
	private String user_tel;// 用户手机号

	@Override
	public String toString() {
		return "UserInfo [user_tel=" + user_tel
				+ "]";
	}
	public String getuserTel() {
		return user_tel;
	}
	public void setUserTel(String user_tel) {
		this.user_tel = user_tel;
	}

	public String getUserPwd() {
		return user_pwd;
	}

	public void setUserPwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

}
