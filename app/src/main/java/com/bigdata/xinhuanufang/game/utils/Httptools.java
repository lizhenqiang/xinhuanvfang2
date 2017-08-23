package com.bigdata.xinhuanufang.game.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Httptools  {

	public static byte[] getData(String path){
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		HttpURLConnection conn=null;
		BufferedInputStream bis=null;
		byte[] buffer = new byte[1024*4];
		try {
			URL url=new URL(path);

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			conn.connect();


			if(conn.getResponseCode() == 200){
				is = conn.getInputStream();
				bis = new BufferedInputStream(is);
				bos = new ByteArrayOutputStream();
				int len = -1;
				while((len=bis.read(buffer)) != -1){
					bos.write(buffer, 0, len);
					buffer=bos.toByteArray();
				}
			}

//			return bos.toByteArray();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (conn != null) {
				// 释放资源
				conn.disconnect();
			}
		}
		return buffer;
	}
}
