package com.bigdata.xinhuanufang.to;
/*
 * ����Login��Ϣ��ʵ����
 * */
public class LoginRsp {
	private int code; // ���������� 0ʧ�� 1�ɹ�
	private int user_id; // �û����
	private String user_tel; // �ֻ���
	private int user_gloves; // ����������

	private String use_head; // ͷ��
	private String user_username; // �ǳ�

	private String user_pwd; // ����
	private String user_sex; // �Ա�
	private String user_sign; // ����ǩ��
	private String user_date; // ����

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
