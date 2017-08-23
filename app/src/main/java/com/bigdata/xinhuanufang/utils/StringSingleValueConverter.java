package com.bigdata.xinhuanufang.utils;

import com.thoughtworks.xstream.converters.SingleValueConverter;
/*
 * �������������ж�ע��
 * */
@SuppressWarnings("unchecked")
public class StringSingleValueConverter implements SingleValueConverter {

	public Object fromString(String arg0) {
		if (arg0 == null || "".equals(arg0)) {
			// logger.info("�ַ���Ϊ��");
			return null;

		} else {

			return arg0;
		}

	}

	public String toString(Object arg0) {
		if (arg0 != null) {
			return arg0.toString();
		} else {
			return null;
		}
	}

	public boolean canConvert(Class arg0) {
		if (arg0.equals(String.class)) {
			return true;
		}
		return false;
	}

}
