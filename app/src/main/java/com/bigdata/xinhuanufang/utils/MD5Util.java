package com.bigdata.xinhuanufang.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

public class MD5Util 
{	
	protected static MessageDigest messagedigest = null;
	
	static
	{
		try
		{
			messagedigest = MessageDigest.getInstance("MD5");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	

	/**
	 * 取得大文件的MD5信息摘要；
	 * 适用于上G大的文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException
	{
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messagedigest.update(byteBuffer);
		return DataSwitch.bytesToHexString(messagedigest.digest()).toUpperCase();
	}
	
	
	/**
	 * 取得文件输入流的MD5信息摘要；
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(FileInputStream input,int length)throws IOException
	{
		FileChannel ch = input.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, length);
		messagedigest.update(byteBuffer);
		return DataSwitch.bytesToHexString(messagedigest.digest()).toUpperCase();
	}
	
	

	/**
	 * 取得给定字符串的MD5信息摘要；
	 * @param s
	 * @return
	 */
	public static String getMD5String(String s) 
	{
		return getMD5String(s.getBytes());
	}

	
	
	/**
	 * 取得给定字节数组的MD5信息摘要；
	 * @param bytes
	 * @return
	 */
	public static String getMD5String(byte[] bytes) 
	{
		messagedigest.update(bytes);
		return DataSwitch.bytesToHexString(messagedigest.digest()).toUpperCase();
	}

}
