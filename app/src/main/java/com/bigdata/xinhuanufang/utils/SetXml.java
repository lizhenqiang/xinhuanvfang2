package com.bigdata.xinhuanufang.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

/**
 * Created by weiyu$ on 2017/4/5.
 */

public class SetXml {
private Context context;
    public SetXml(Context context) {
        this.context=context;
    }

    public String getxml() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        // <appid>wx2421b1c4370ec43b</appid>
        sb.append("<appid>");
        sb.append(Config.APP_ID);
        sb.append("</appid>");
        // mch_id
        sb.append("<mch_id>");
        sb.append(Config.MCH_ID);
        sb.append("</mch_id>");
        // nonce_str  随机字符串，不长于32位。推荐随机数生成算法
        sb.append("<nonce_str>");
        sb.append(getRandomStringByLength(32));
        sb.append("</nonce_str>");
        //sign  签名
        sb.append("<sign>");
        sb.append(MD5Util.getMD5String(Config.SIGN));
        sb.append("</sign>");
        //body  商品描述
        sb.append("<body>");
        sb.append("微信支付测试");
        sb.append("</body>");
        //out_trade_no 商户订单号
        sb.append("<out_trade_no>");
        sb.append(getSystemTiem());
        sb.append("</out_trade_no>");
        //total_fee  总金额
        sb.append("<total_fee>");
        sb.append("0.01");
        sb.append("</total_fee>");
        //spbill_create_ip  终端ip
        sb.append("<spbill_create_ip>");
        sb.append(getIPAddress(context));
        sb.append("</spbill_create_ip>");
        //notify_url  通知地址
        sb.append("<notify_url>");
        sb.append(Config.ip+"/wxpayapi/example/notify2.php");
        sb.append("</notify_url>");
        //trade_type  交易类型
        sb.append("<trade_type>");
        sb.append("APP");
        sb.append("</trade_type>");
        sb.append("</xml>");
        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 随机数生成
     * @param length
     * @return
     */
    public  String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    /**
     * 获取当前的系统时间
     */
    public String getSystemTiem(){
        Date dt= new Date();
        Long time= dt.getTime();

        return time+getRandomStringByLength(10);
    }

    public  String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }


}
