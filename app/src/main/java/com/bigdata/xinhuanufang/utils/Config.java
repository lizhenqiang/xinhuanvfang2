package com.bigdata.xinhuanufang.utils;

public class Config {
	// 比赛模块的新闻资讯
	// "http://115.28.69.240/boxing/app/news_list1.php?status=0&page=1"
	public static final String ip = "http://47.93.113.190";
	public static String app = "/app";

	public static String configGoodsPath(String app, String php, int status,
			int page) {
		return ip + app + "/" + php + "?" + "status=" + status + "&page="
				+ page;
	}
	//注册界面的注册协议
	public static String zhuce_rule="http://115.28.69.240/boxing/app/about.php";
	//超级梦想界面的注册协议
	public static String superdream_rule="http://115.28.69.240/boxing/app/about.php";

	//联系电话
	public static String phonenumber="010-65260271";
	//分享地址
	public  static String shareUrl="http://app.qq.com/#id=detail&appid=1106076127";
	//QQ空间分享的图片
	public static String QQpictureurl="http://i.gtimg.cn/open/app_icon/06/07/61/27/1106076127_100_m.png";
	//建议
	public static String jianyi="-1";
	//购买商品的微信回调
	public static String weixinhuidiao = "http://47.93.113.190/wxpay/example/notify2.php";
	//购买商品的微信回调
	public static String weixinjinshoutaohuidiao = "http://47.93.113.190/wxpay/example/notify1.php";
	//新浪的用户唯一标识
	public static String SINABIAOSHI = "1";

	// 用户的测试id
	public static String userID = "1";
	// 用户的金手套数量
	public static String USER_GLOVES = "0";
	// 用户的手机号
	public static String USER_TEL = "1";
	// 用户的头像
	public static String USER_HEAD = "1";
	//用户的本地头像
	public static String USER_hosted_HEAD = "1";
	//用户登录的三方账号
	public static String USER_ACCOUNT="0";
	//三方登录的平台
	public  static String  RELEVANCE_TYPE="3";
	// 用户的昵称
	public static String USER_USERNAME = "1";
	// 用户的个性签名
	public static String USER_SIGN = "1";
	// 用户的性别
	public static String USER_SEX = "1";
	// 商城的生活用品
	public static String LiveUseing = "0";
	// 商城的电子产品
	public static String Electron = "1";
	// 商城的电子产品
	public static String LuickWare = "2";
	// 全部商品
	public static String AllShoping = "4";
	// 超级梦想
	public static String BigDream = "3";
	//收货人的姓名
	public static String Address_name="0";

	//收货人的手机号
	public static String Address_tel="0";
	//收货人的地址
	public static String Address_address="0";
	//关于我们的h5
	public static String AboutMe="http://47.93.113.190/app/about.php";
	//APPid
	public static final String APP_ID = "wx6a6866a6926c0f06";
	//NOTIFY_URL
	public static final String NOTIFY_URL = "http://47.93.113.190/wxpayapi/example/notify2.php";
	//APPkey
	public static final String APP_KEY = "BKSbks2017BKSbks2017BKSbks2017BK";
	//商户id
	public static final String MCH_ID = "1454116302";
	//签名
	public static final String SIGN = "46e7e1dd43a25666a6d9a40f6cf35adf";
	//包名
	public static final String PACKAGE="com.bigdata.xinhuanufang";
	//新浪微博的
	public static final String SINA_APPKEY = "139214169";
	//注册成功之后的REDIRECT_URL
	public static final String SINA_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html" ;
	public static final String SINA_SCOPE =   "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";
	//购物陈商品属性的
	public static String SHOPPING_ATTRS = "0";
	//微信登录的凭证
	public  static String weixin_unionid="";
	//用户的三方登录的日期
	public  static String USER_DATE="";
	//记录微信支付所需要的参数
	public static  String ShoppingName = "0";
	public static String ShoppingPrice="0";
	public static String percent="0";
	public static String type_old_gloves="0";
	public static String thinkjoin_gloves="0";

	//商品的信息
	public  static String shopingColorIDInfo="";
	public  static String shopingNetWorkIDInfo="";
	public  static String shopingVolumekIDInfo="";
	public  static String volume="";
	public  static String network="";
	public  static String color="";

	public static boolean isSave=true;
	public static String isbiaoshi="-1";
	public static boolean isphoto=false;
	public static String chaojimengxiangjinshoutao="-1";






	public static class ShowMsgActivity {
		public static final String STitle = "showmsg_title";
		public static final String SMessage = "showmsg_message";
		public static final String BAThumbData = "showmsg_thumb_data";
	}

}
