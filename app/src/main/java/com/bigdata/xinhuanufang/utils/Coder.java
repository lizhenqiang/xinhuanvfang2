package com.bigdata.xinhuanufang.utils;

import com.bigdata.xinhuanufang.service.to.LoginReq;
import com.bigdata.xinhuanufang.service.to.LoginRsp;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.JDomDriver;
/*
 * 组装报文、解析报文工具类
 * */
public class Coder<T> {

	public Coder() {

	}

	/**
	 * 登录接口组装报文
	 * 
	 * @param o
	 * @return
	 */
	public String encodeLogin(Object o) {
		XStream xs = new XStream();
		xs.alias("LoginReq", LoginReq.class);
		return xs.toXML(o);
	}

	/**
	 * 登录报文解析
	 * 
	 * @param inXML
	 * @return
	 */
	public Object decodeLogin(String inXML) {

		String code = deleteXMLTag(inXML);
		XStream xs = new XStream(new JDomDriver());
		xs.alias("LoginRsp", LoginRsp.class);
		// 基本数据类型判断注册
		xs.registerConverter(new StringSingleValueConverter());
		if (code.indexOf("<<") > -1 || code.indexOf("<<") > -1) {
			code = code.replaceAll("<<", "“");
			code = code.replaceAll(">>", "”");
		}
		return (Object) xs.fromXML(code);
	}

	/**
	 * 删除附加信息
	 * 
	 * @param claimXML
	 * @return
	 */
	public String deleteXMLTag(String inXML) {

		String requestXML = inXML;
		requestXML = requestXML.replaceAll(
				"<PACKET type=\"RESPONSE\" version=\"1.0\">", "");
		requestXML = requestXML.replaceAll("<BODY>", "");
		requestXML = requestXML.replaceAll("</BODY>", "");
		requestXML = requestXML.replaceAll("</PACKET>", "");
		return requestXML;
	}

}
