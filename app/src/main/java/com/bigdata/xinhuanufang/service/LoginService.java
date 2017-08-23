package com.bigdata.xinhuanufang.service;

import android.content.Context;

import com.bigdata.xinhuanufang.service.to.LoginReq;
import com.bigdata.xinhuanufang.service.to.LoginRsp;
import com.bigdata.xinhuanufang.utils.Coder;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.HttpUtils;
import com.bigdata.xinhuanufang.utils.MD5Util;

import java.net.URLDecoder;
import java.net.URLEncoder;
/*
 * ��װ��¼�ӿڵı��ġ������������󡢽�����¼�ӿڵı���
 * */
public class LoginService {
	public LoginRsp login(String user_tel , String user_pwd ,Context  context) throws Exception{
		LoginReq loginReq = new LoginReq();

		loginReq.setUser_tel(user_tel);
		loginReq.setUser_pwd(MD5Util.getMD5String(user_pwd));
		
		String reqXML = new Coder<LoginReq>().encodeLogin(loginReq); //��װ��¼�ӿڵı���
		System.out.println("---repXML:"+reqXML);
		reqXML = URLEncoder.encode(reqXML,"utf-8"); 
		HttpUtils htp = new HttpUtils();
		String url  = Config.ip+"/app/register.php" ;
		String outXML = htp.sendPost(url, reqXML);
		outXML = URLDecoder.decode(outXML, "utf-8");
		System.out.println("---outXML:"+outXML);
		LoginRsp loginRsp = (LoginRsp) new Coder<LoginRsp>().decodeLogin(outXML);  //������¼�ӿڵı���
		return loginRsp;
	}
}
