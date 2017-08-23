package com.bigdata.xinhuanufang.mine.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;

/**
 * 鍔犺浇R鏂囦欢閲岄潰鐨勫唴瀹�
 * 

 */
public class Res {

	// 鏂囦欢璺緞鍚�
	private static String pkgName;
	// R鏂囦欢鐨勫璞�
	private static Resources resources;

	// 鍒濆鍖栨枃浠跺す璺緞鍜孯璧勬簮
	public static void init(Context context) {
		pkgName = context.getPackageName();
		resources = context.getResources();
	}

	/**
	 * layout鏂囦欢澶逛笅鐨剎ml鏂囦欢id鑾峰彇
	 * 
	 */
	public static int getLayoutID(String layoutName) {
		return resources.getIdentifier(layoutName, "layout", pkgName);
	}

	// 鑾峰彇鍒版帶浠剁殑ID
	public static int getWidgetID(String widgetName) {
		return resources.getIdentifier(widgetName, "id", pkgName);
	}

	/**
	 * anim鏂囦欢澶逛笅鐨剎ml鏂囦欢id鑾峰彇
	 * 
	 */
	public static int getAnimID(String animName) {
		return resources.getIdentifier(animName, "anim", pkgName);
	}

	/**
	 * xml鏂囦欢澶逛笅id鑾峰彇
	 * 
	 */
	public static int getXmlID(String xmlName) {
		return resources.getIdentifier(xmlName, "xml", pkgName);
	}

	// 鑾峰彇xml鏂囦欢
	public static XmlResourceParser getXml(String xmlName) {
		int xmlId = getXmlID(xmlName);
		return (XmlResourceParser) resources.getXml(xmlId);
	}

	/**
	 * raw鏂囦欢澶逛笅id鑾峰彇
	 * 
	 */
	public static int getRawID(String rawName) {
		return resources.getIdentifier(rawName, "raw", pkgName);
	}

	/**
	 * drawable鏂囦欢澶逛笅鏂囦欢鐨刬d
	 * 
	 */
	public static int getDrawableID(String drawName) {
		return resources.getIdentifier(drawName, "drawable", pkgName);
	}

	// 鑾峰彇鍒癉rawable鏂囦欢
	public static Drawable getDrawable(String drawName) {
		int drawId = getDrawableID(drawName);
		return resources.getDrawable(drawId);
	}

	/**
	 * value鏂囦欢澶�
	 * 
	 */
	// 鑾峰彇鍒皏alue鏂囦欢澶逛笅鐨刟ttr.xml閲岀殑鍏冪礌鐨刬d
	public static int getAttrID(String attrName) {
		return resources.getIdentifier(attrName, "attr", pkgName);
	}

	// 鑾峰彇鍒癲imen.xml鏂囦欢閲岀殑鍏冪礌鐨刬d
	public static int getDimenID(String dimenName) {
		return resources.getIdentifier(dimenName, "dimen", pkgName);
	}

	// 鑾峰彇鍒癱olor.xml鏂囦欢閲岀殑鍏冪礌鐨刬d
	public static int getColorID(String colorName) {
		return resources.getIdentifier(colorName, "color", pkgName);
	}

	// 鑾峰彇鍒癱olor.xml鏂囦欢閲岀殑鍏冪礌鐨刬d
	public static int getColor(String colorName) {
		return resources.getColor(getColorID(colorName));
	}

	// 鑾峰彇鍒皊tyle.xml鏂囦欢閲岀殑鍏冪礌id
	public static int getStyleID(String styleName) {
		return resources.getIdentifier(styleName, "style", pkgName);
	}

	// 鑾峰彇鍒癝tring.xml鏂囦欢閲岀殑鍏冪礌id
	public static int getStringID(String strName) {
		return resources.getIdentifier(strName, "string", pkgName);
	}

	// 鑾峰彇鍒癝tring.xml鏂囦欢閲岀殑鍏冪礌
	public static String getString(String strName) {
		int strId = getStringID(strName);
		return resources.getString(strId);
	}

	// 鑾峰彇color.xml鏂囦欢閲岀殑integer-array鍏冪礌
	public static int[] getInteger(String strName) {
		return resources.getIntArray(resources.getIdentifier(strName, "array",
				pkgName));
	}

}
