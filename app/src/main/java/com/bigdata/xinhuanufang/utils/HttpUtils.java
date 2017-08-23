package com.bigdata.xinhuanufang.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
/*
 * 网络请求工具类
 * 
 * */
public class HttpUtils {
	int length = 0;
	int ch = -1;

	public String sendPost(final String httpURL, String outstring) {
		String responseXml = "";
		try {
			URL url = new URL(httpURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDefaultUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "GBK");
			conn.setRequestProperty("Content-Type", "text/html");
			System.out.println(conn.getOutputStream());
			OutputStreamWriter out = new OutputStreamWriter(
					conn.getOutputStream(), "GBK");
			out.write(outstring);
			out.flush();
			out.close();

			DataInputStream in = new DataInputStream(conn.getInputStream());
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] wr = new byte[1024];
			while ((ch = in.read(wr)) != -1) {
				bout.write(wr, 0, ch);
			}

			bout.flush();
			bout.close();
			responseXml = new String(bout.toByteArray(), "GBK");
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.i("login GBKxml", responseXml);
		return responseXml;

	}

}
