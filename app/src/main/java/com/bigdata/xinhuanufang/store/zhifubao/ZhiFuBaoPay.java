package com.bigdata.xinhuanufang.store.zhifubao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.mine.GoldenGloveActivity;
import com.bigdata.xinhuanufang.mine.MyShoppingMall;
import com.bigdata.xinhuanufang.store.zhifubao.domain.PayResult;
import com.bigdata.xinhuanufang.store.zhifubao.util.SignUtils;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.shoppingconfig;

import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


public class ZhiFuBaoPay extends Activity {
    public static final String PARTNER = "2088621655143351";

    // 商户收款账号
    public static final String SELLER = "china_bks@163.com";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKysz2s0TCm6uIrYb00JY5cDoUK5D1TpQNPEUo01N7m4s7FQFCfant5qOxPyC4Aj+1jbdIq/5xwnDleaUaIxkTpqrDv6VlM9A5EEs5PmiCPDqQbgPfJot2jc6o1ZN2KpdUEOgHnS/qB/Z1tRw+6VoOj14RTD9cUjwznm4TvM3ZhdAgMBAAECgYEApW6a+haMsZ4Wl89sMt4/77qmkQpDyhH+7eQ1Qtwtm119qu8RoG0vr98O4rkOk6HleOgn65aXkL1KXFuYwk/C1iUBZthMiqP+9ZyQ1yJur2hKPTb8kT/w+wA5dcrR42mOMPonscaKzvdzTaomw4uoAnl0z9QFG8L1V/f3K2639gECQQDgwiyniN0iFtEpEN/Lcxk/ZOdD4XLsrk4uqS1T4E1HiAJhuPGxlWxvPiA/YCULQXW9Wh/TETFRyGRGsoMFITo9AkEAxK1LL7EvphTpXawdbmm5teMg7UMjAuJkLgRa/yC3j1tNjBPTKI1Fu5L5CAgsbXFZN/FmQGDeOI7Xdpk0aO9YoQJBAI+A6un9JNPDJcDGmV1ZG+GyF0bsB2i+0gRQ2DQZnr3xRTQH1anA8f8E96n/RSeun/JKPM+Sxp6lCAFrsOPxlzECQQC+rMdU0HJCkMmCoCfUcLm0BiRZAhrCZBv9pQlXiS+M01bGND4mdXgilyXKTKpN3HND0kxwo7ktmgZ4G6X9CfahAkAJByQ+JJB8gCGOskC8r5Ui230VMeGjuhfH/GbMhTnwOXryvLAopXWHX/hBUavWB5Qqzxb5edl2lTxP0qU/2R+6";

    private static final int SDK_PAY_FLAG = 1;


    //服务器订单号
    private String tradeNo = null;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(ZhiFuBaoPay.this, "支付成功", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessageDelayed(110, 1500);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(ZhiFuBaoPay.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                            mHandler.sendEmptyMessageDelayed(100, 1500);
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(ZhiFuBaoPay.this, "支付失败", Toast.LENGTH_SHORT).show();
                            mHandler.sendEmptyMessageDelayed(100, 1500);

                        }
                    }
                    break;
                }
                case 100:
                    zhifubao_pay_result.setText("支付失败");
                    zhifubao_pay_result.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    break;
                case 110:
                    //支付成功
                    zhifubao_pay_result.setText("支付成功");
                    zhifubao_pay_result.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (shoppingconfig.weixin_pay_result_type.equals("1")) {
                                //该充值是金手套充值,如果支付完成后条跳转到金手套交易里面
                                Intent intent=new Intent(ZhiFuBaoPay.this, GoldenGloveActivity.class);
                                startActivity(intent);
                            }else if (shoppingconfig.weixin_pay_result_type.equals("0")) {
                                //该订单是商城购买商品的支付信息
                                Intent intent = new Intent(ZhiFuBaoPay.this, MyShoppingMall.class);
                                intent.putExtra("isfukuan", "yifukuan");
                                startActivity(intent);
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }

    };
    private String price;
    private String name;
    private String type;
    private TextView zhifubao_shoping_address_name;
    private TextView zhifubao_shoping_address_tel;
    private TextView zhifubao_shoping_address_content;
    private ImageView zhifubao_pay_result_pic;
    private TextView zhifubao_pay_result_shopping_name;
    private TextView zhifubao_pay_result_fenlei;
    private TextView zhifubao_pay_result_price;
    private TextView zhifubao_pay_result_num;
    private Button zhifubao_pay_result;
    private LinearLayout shopping_zhifubao_dizhilan;
    private LinearLayout shopping_zhifubao_details;
    private Button chongzhi_jinshoutao_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pay_external);
        Intent intent = getIntent();
        name = intent.getStringExtra("shoppingname");
        price = intent.getStringExtra("shoppingprice");
        tradeNo = intent.getStringExtra("tradeNo");
        type = intent.getStringExtra("type");
        if (tradeNo == null || tradeNo.equals("")) {
            Toast.makeText(this, "订单号错误", Toast.LENGTH_SHORT).show();
            finish();
        }

        //返回
        ImageView iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
        iv_itt_back.setVisibility(View.GONE);
        //标题
        TextView tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
        tv_itt_title.setText("支付页面");
        //收藏图片
        ImageView iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
        iv_itt_collection.setVisibility(View.GONE);
        //删除图标
        ImageView iv_itt_delete = (ImageView) findViewById(R.id.iv_itt_delete);
        iv_itt_delete.setVisibility(View.GONE);
        //保存文字
        TextView tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
        tv_itt_save.setVisibility(View.GONE);

        //展现出商品信息
        shopping_zhifubao_dizhilan = (LinearLayout) findViewById(R.id.shopping_zhifubao_dizhilan);
        shopping_zhifubao_details = (LinearLayout) findViewById(R.id.shopping_zhifubao_details);
        //收货人姓名
        zhifubao_shoping_address_name = (TextView) findViewById(R.id.zhifubao_shoping_address_name);
        //收货人的电话
        zhifubao_shoping_address_tel = (TextView) findViewById(R.id.zhifubao_shoping_address_tel);
        //收货地址
        zhifubao_shoping_address_content = (TextView) findViewById(R.id.zhifubao_shoping_address_content);
        //商品图片
        zhifubao_pay_result_pic = (ImageView) findViewById(R.id.zhifubao_pay_result_pic);
        //商品名称
        zhifubao_pay_result_shopping_name = (TextView) findViewById(R.id.zhifubao_pay_result_shopping_name);
        //商品属性分类
        zhifubao_pay_result_fenlei = (TextView) findViewById(R.id.zhifubao_pay_result_fenlei);
        //上平价格
        zhifubao_pay_result_price = (TextView) findViewById(R.id.zhifubao_pay_result_price);
        //商品数量
        zhifubao_pay_result_num = (TextView) findViewById(R.id.zhifubao_pay_result_num);
        //充值金手套的户数量
        chongzhi_jinshoutao_number = (Button) findViewById(R.id.chongzhi_jinshoutao_number);
        //支付结果
        zhifubao_pay_result = (Button) findViewById(R.id.zhifubao_pay_result);
        if (shoppingconfig.weixin_pay_result_type.equals("1")) {
            shopping_zhifubao_dizhilan.setVisibility(View.GONE);
            shopping_zhifubao_details.setVisibility(View.GONE);
        }else if (shoppingconfig.weixin_pay_result_type.equals("0")) {
            chongzhi_jinshoutao_number.setVisibility(View.GONE);
        }
        chongzhi_jinshoutao_number.setText("充值金手套"+shoppingconfig.jinshoutao_number+" 个");
        zhifubao_shoping_address_name.setText(shoppingconfig.shoping_address_name);
        zhifubao_shoping_address_tel.setText(shoppingconfig.shoping_address_tel);
        zhifubao_shoping_address_content.setText(shoppingconfig.shoping_address_content);
        zhifubao_pay_result_shopping_name.setText(shoppingconfig.weixin_pay_result_shopping_name);
        zhifubao_pay_result_fenlei.setText(shoppingconfig.weixin_pay_result_fenlei);
        zhifubao_pay_result_price.setText(shoppingconfig.weixin_pay_result_price);
        x.image().bind(zhifubao_pay_result_pic, Config.ip + shoppingconfig.weixin_pay_result_pic);


        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        String orderInfo = getOrderInfo(name, "该测试商品的详细描述", price);

/**
 * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
 */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

/**
 * 完整的符合支付宝参数规范的订单信息
 */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(ZhiFuBaoPay.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        /**
         * tradeNo为服务器订单号
         * getOutTradeNo() 为系统生成订单
         */
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + tradeNo + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径http://115.28.69.240/boxing/ alipay/ notify_url2.php
        if (type.equals("0")) {
            //商城订单,支付宝的回调接口http://47.93.113.190/alipay/notify_url2.php
            orderInfo += "&notify_url=" + "\"" + "http://47.93.113.190/alipay/notify_url2.php" + "\"";
        } else if (type.equals("1")) {
            //金手套充值,支付宝的回调接口,http://47.93.113.190/alipay/notify_url1.php
            orderInfo += "&notify_url=" + "\"" + "http://47.93.113.190/alipay/notify_url1.php" + "\"";
        }

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

}
