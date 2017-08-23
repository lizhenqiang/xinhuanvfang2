package com.bigdata.xinhuanufang.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by weiyu$ on 2017/4/27.
 */

public class configUtils {
    public static String getStrTime(String cc_time) {
        if (cc_time.equals("0")) {
            return "0";
        }
        String re_StrTime = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH时mm分ss秒");
        Date d;
        try {
//            d = sdf.parse(user_time);
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        }catch (Exception e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }
    public static String getStrTimes(String cc_time) {
        if (cc_time.equals("0")) {
            return "0";
        }
        String re_StrTime = null;
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH时mm分");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
    public static String getStrTimess(String cc_time) {
        if (cc_time.equals("0")) {
            return "0";
        }
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
}
