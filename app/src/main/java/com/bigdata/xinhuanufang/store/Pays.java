package com.bigdata.xinhuanufang.store;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.wxapi.MD5;
import com.bigdata.xinhuanufang.wxapi.Util;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by weiyu$ on 2017/4/8.
 * 支付类(微信)
 */

public class Pays {

    private String tv_sli_productName;
    private IWXAPI msgApi;
    private Context context;
    private PayReq req;
    private StringBuffer sb;
    private String notify;
    private String type;
    private String outTradeNo;
    private static String money="";
    private static String shoppingname="";
    private Map<String,String> resultunifiedorder=new HashMap<>();
    public Pays(Context context , String shoppingname,String money,String type,String outTradeNo) {
        this.shoppingname = shoppingname;
        this.money = money;
        this.context=context;
        this.type=type;
        this.outTradeNo=outTradeNo;

        msgApi= WXAPIFactory.createWXAPI(context, null);
        req=new PayReq();
        sb=new StringBuffer();
        String urlString="https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrePayIdAsyncTask prePayIdAsyncTask=new PrePayIdAsyncTask();
        prePayIdAsyncTask.execute(urlString);//生成prepayId
    }

    /*
        * 调起微信支付
        */
    private void sendPayReq() {


        msgApi.registerApp(Config.APP_ID);
        msgApi.sendReq(req);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private void genPayReq() {

        req.appId = Config.APP_ID;
        req.partnerId = Config.MCH_ID;
        if (resultunifiedorder!=null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            req.packageValue = "prepay_id="+resultunifiedorder.get("prepay_id");
        }
        else {
            Toast.makeText(context, "prepayid为空", Toast.LENGTH_SHORT).show();
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());


        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);

        sb.append("sign\n"+req.sign+"\n\n");

//        textView.setText(sb.toString());

        Log.e("Simon", "----"+signParams.toString());

    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.APP_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes());
        Log.e("Simon","----"+appSign);
        return appSign;
    }
    private class PrePayIdAsyncTask extends AsyncTask<String,Void, Map<String, String>>
    {
        private ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            dialog = ProgressDialog.show(context, "提示", "正在提交订单");

        }
        @Override
        protected Map<String, String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url=String.format(params[0]);
            String entity=getProductArgs();
            Log.e("Simon",">>>>"+entity);
            byte[] buf= Util.httpPost(url, entity);
            String content = new String(buf);
            Log.e("orion", "----"+content);
            Map<String,String> xml=decodeXml(content);

            return xml;
        }
        public Map<String,String> decodeXml(String content) {

            try {
                Map<String, String> xml = new HashMap<String, String>();
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(new StringReader(content));
                int event = parser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {

                    String nodeName=parser.getName();
                    switch (event) {
                        case XmlPullParser.START_DOCUMENT:

                            break;
                        case XmlPullParser.START_TAG:

                            if("xml".equals(nodeName)==false){
                                //实例化student对象
                                xml.put(nodeName,parser.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            break;
                    }
                    event = parser.next();
                }

                return xml;
            } catch (Exception e) {
                Log.e("Simon","----"+e.toString());
            }
            return null;

        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            super.onPostExecute(result);
            if (dialog != null) {
                dialog.dismiss();
            }
            sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
//            textView.setText(sb.toString());
            String prepay_id=result.get("prepay_id");
            resultunifiedorder=result;
            genPayReq();//生成签名参数
            sendPayReq();//调起支付
        }
    }
    private String getProductArgs() {
        // TODO Auto-generated method stub
        StringBuffer xml=new StringBuffer();
        String name=Config.ShoppingPrice.replace(".","");
        try {
            String nonceStr=getNonceStr();
            xml.append("<xml>");
            if (type.equals("0")) {
                notify=Config.weixinhuidiao;
            }else if (type.equals("1")) {
                notify=Config.weixinjinshoutaohuidiao;
                Config.ShoppingName="金手套充值";
            }
            if(outTradeNo == null||outTradeNo.equals("")){
                Toast.makeText(context,"订单号错误",Toast.LENGTH_SHORT).show();
                return "";
            }
//            Toast.makeText(context, "微信支付订单信息"+outTradeNo, Toast.LENGTH_SHORT).show();
            List<NameValuePair> packageParams=new LinkedList<NameValuePair>();
            packageParams.add(new BasicNameValuePair("appid",Config.APP_ID));
            packageParams.add(new BasicNameValuePair("body", Config.ShoppingName));
            packageParams.add(new BasicNameValuePair("mch_id", Config.MCH_ID));
            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
            packageParams.add(new BasicNameValuePair("notify_url", notify));//写自己的回调地址
            packageParams.add(new BasicNameValuePair("out_trade_no",outTradeNo));
            packageParams.add(new BasicNameValuePair("total_fee", money));
            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign=getPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));
            String xmlString=toXml(packageParams);
            return xmlString;
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    //生成订单号,测试用，在客户端生成
    private String genOutTradNo() {
        Random random = new Random();
//      return "dasgfsdg1234"; //订单号写死的话只能支付一次，第二次不能生成订单
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Config.APP_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("Simon",">>>>"+packageSign);
        return packageSign;
    }
    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random=new Random();

        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /*
    * 转换成xml
    */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("Simon",">>>>"+sb.toString());
        try {
            return new String(sb.toString().getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }


}
