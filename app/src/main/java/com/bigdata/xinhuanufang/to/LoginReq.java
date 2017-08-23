package com.bigdata.xinhuanufang.to;

import org.apache.commons.lang.StringUtils;
/*
 * ��Login����ʵ����ת��Ϊxml��ʽ
 * */
public class LoginReq {
	private String user_pwd;
	private String user_tel;

	public String toString() {
		StringBuffer requestXml = new StringBuffer(4000);
		requestXml.append("<LoginReq>");
		addNode(requestXml, "user_tel", user_tel);
		addNode(requestXml, "user_pwd", user_pwd);	
		requestXml.append("</LoginReq>");
		return requestXml.toString();
	}

	public static void addNode(StringBuffer buffer, String name, String value) {
		value = StringUtils.replace(value, "<", "&lt;");
		value = StringUtils.replace(value, ">", "&gt;");
		value = StringUtils.replace(value, "&", "&amp;");
		value = StringUtils.replace(value, "'", "&apos;");
		value = StringUtils.replace(value, "\"", "&quot;");

		buffer.append("<");
		buffer.append(name);
		buffer.append(">");
		buffer.append(value);
		buffer.append("</");
		buffer.append(name);
		buffer.append(">");
		buffer.append("\r\n");
	}

	public String getUser_pwd() {
		return user_pwd;
	}

	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}

	public String getUser_tel() {
		return user_tel;
	}

	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}

}
