package com.bigdata.xinhuanufang.store;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.querendingdanshoppingInfoAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.game.bean.AddressList;
import com.bigdata.xinhuanufang.game.bean.NetworkDataCar;
import com.bigdata.xinhuanufang.game.bean.Shop;
import com.bigdata.xinhuanufang.game.bean.UserAddress;
import com.bigdata.xinhuanufang.game.bean.attrs;
import com.bigdata.xinhuanufang.game.bean.cart;
import com.bigdata.xinhuanufang.game.bean.shopingCarchilds;
import com.bigdata.xinhuanufang.mine.BuyGoldenGloveActivity;
import com.bigdata.xinhuanufang.mine.MyShoppingMall;
import com.bigdata.xinhuanufang.store.zhifubao.ZhiFuBaoPay;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.ListViewForScrollView;
import com.bigdata.xinhuanufang.utils.NetConfig;
import com.bigdata.xinhuanufang.utils.SetXml;
import com.bigdata.xinhuanufang.utils.shoppingconfig;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asus 确认订单Activity
 */
public class ConfirmOrderActivity extends BaseActivity implements
        OnClickListener {
    private List<UserAddress> AddressLists;
    private TextView address_name;
    private TextView address_tel;
    private TextView address_content;
    private LinearLayout ll_aco_adress;
    private TextView tv_sli_productName;
    private TextView tv_sli_price;
    private TextView enter_shoping_color_info;
    private ImageView iv_sli_productPhoto;
    private TextView enter_shoping_bill;
    private Button put_bill;
    private CheckBox enter_chose_pay_zhi;
    private CheckBox enter_chose_pay_wei;
    private String way = "6";// 记录支付方式
    private EditText leave_message;// 给商家的留言
    private TextView tv_aco_gloveNum;
    private String flag;//记录支付方式
    private String defaultAddress;//记录默认地址的id
    private Bundle bundle;
    private List<Shop> shopList;
    private String time;
    private String jinshoutaoprice;//兑换该商品所需要的金手套
    private final IWXAPI msgApi = WXAPIFactory.createWXAPI(this, null);
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    AddressLists = (List<UserAddress>) msg.obj;
                    dataInitializtion();
                    break;
                case 100:
                    time = (String) msg.obj;
                    if (flag.equals("0")) {
                        //调起微信支付
                        String outTradeNo = String.valueOf(msg.obj);
                        shoppingconfig.weixin_pay_result_type="0";
                        Config.ShoppingName = tv_sli_productName.getText().toString();
                        Config.ShoppingPrice = tv_sli_price.getText().toString();
                        StringBuffer sb=new StringBuffer();
                        String money="";
                        for (int i = 0; i < tv_sli_price.getText().toString().length(); i++) {
                            if (tv_sli_price.getText().toString().charAt(i)=='.') {
                                money=sb.toString();
                            }else{
                                sb.append(tv_sli_price.getText().toString().charAt(i));
                            }

                        }
                        new Pays(ConfirmOrderActivity.this, tv_sli_productName.getText().toString(), money+"00", "0",outTradeNo);
                    }
                    if (flag.equals("1")) {
                        shoppingconfig.weixin_pay_result_type="0";
                        String outTradeNo = String.valueOf(msg.obj);
                        //调起支付宝支付
                        Intent intent = new Intent(ConfirmOrderActivity.this, ZhiFuBaoPay.class);
                        //拿到当前商品的必要参数,商品的名称,价格,详细信息
                        //商品的名称
                        intent.putExtra("shoppingname", tv_sli_productName.getText());
                        intent.putExtra("tradeNo",outTradeNo);
                        intent.putExtra("type","0");

                        //商品的价格
//                        intent.putExtra("shoppingprice",tv_sli_price.getText());
                        intent.putExtra("shoppingprice", tv_sli_price.getText());
                        startActivity(intent);
                    }

                    break;
                case 120:
                    //购物车过来的信息适配
                    shopingCarData= (List<NetworkDataCar>) msg.obj;
                    showshoppingcar(shopingCarData);
                    break;

                default:
                    break;
            }
        }

        ;
    };
    private ListViewForScrollView shopping_info_list;
    private LinearLayout shopping_info;
    private boolean isxunhuan;

    private void showshoppingcar(List<NetworkDataCar> shopingCarData) {
        if (biaoji.equals("1")) {
            int num=0;
            String attrs=ids;
//        for (int i = 0; i < attrs.length(); i++) {
//            if (ids.charAt(i) == ',') {
//                num=num+1;
//            }
//        }

            String[]  str=attrs.split(",");
            for (int i = 0; i < str.length; i++) {
                String a=str[i];
            }

            shopping_info.setVisibility(View.GONE);
            shopping_info_list.setVisibility(View.VISIBLE);
//        List<NetworkDataCar> list=new ArrayList<>();

//        if (str.length>0&&str!=null) {
//            int a= shopingCarData.get(0).getCart().size();
//                for (int i = 0; i < shopingCarData.get(0).getCart().size(); i++) {
//
//                    String sss=shopingCarData.get(0).getCart().get(i).getCart_id();
//
//                    if (!str[i].equals(sss)) {
//                        shopingCarData.get(0).getCart().remove(i);
//                    }
//                }
//
//            int b= shopingCarData.get(0).getCart().size();
            String sdf=str[0];
            int asdf=str.length;
            shopping_info_list.setAdapter(new querendingdanshoppingInfoAdapter(ConfirmOrderActivity.this,shopingCarData,str));
        }

//        }


    }

    private TextView shoping_details_jinshoutao_number;
    private TextView shoping_details_jinshoutao_chongzhi;
    private PopupWindow popupWindow;
    private Button shoping_details_jinshoutao_cancler;
    private Button shoping_details_jinshoutao_enter;
    private String ids;
    private String shopingID;
    private String biaoji;
    private String shoppingpic;
    private String think_attrid;
    private List<NetworkDataCar> shopingCarData;//商品数据
    private void pay(IWXAPI msgApi) {
        WXTextObject textObj = new WXTextObject();
        textObj.text = "123";

// 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
// 发送文本类型的消息时，title字段不起作用
// msg.title = "Will be ignored";
        msg.description = "123";

// 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = new SetXml(ConfirmOrderActivity.this).getSystemTiem(); // transaction字段用于唯一标识一个请求
        req.message = msg;
// 调用api接口发送数据到微信
        msgApi.sendReq(req);

    }


    @Override
    public int getView() {
        return R.layout.activity_confirmorder;
    }

    protected void dataInitializtion() {
        if (AddressLists != null) {
            for (int i = 0; i < AddressLists.get(0).getAddress().size(); i++) {
                if (AddressLists.get(0).getAddress().get(i).getAddress_status()
                        .equals("1")) {
                    address_name.setText(AddressLists.get(0).getAddress().get(i)
                            .getAddress_name()
                            + "");
                    address_tel.setText(AddressLists.get(0).getAddress().get(i)
                            .getAddress_tel());
                    address_content.setText(AddressLists.get(0).getAddress().get(i)
                            .getAddress_content());
                    defaultAddress = AddressLists.get(0).getAddress().get(i).getAddress_id();

                }
            }
        }

    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        setTitle("确认订单");
        setGone();
        setBack();

        // 给确认订单界面的控件设置数据
        // 给默认地址进行绑定数据,表示是1
    //获取购物车的数据
        getshoppingcar();
    }

    private void getshoppingcar() {
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/shopcart_list.php?user_id=" + Config.userID.trim()),
                new CommonCallback<String>() {

                    @Override
                    public void onCancelled(CancelledException arg0) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onError(Throwable arg0, boolean arg1) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onFinished() {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onSuccess(String arg0) {
                        // TODO Auto-generated method stub
                        Log.e("购物车额数据", arg0);
                        shopingCarData = new ArrayList<NetworkDataCar>();
                        if (TextUtils.isEmpty(arg0)) {
                            return;
                        } else {
                            try {
                                JSONObject json = new JSONObject(arg0);
                                String code=json.getString("code");
                                JSONArray cart=json.getJSONArray("cart");
                                List<com.bigdata.xinhuanufang.game.bean.cart> mCart=new ArrayList<cart>();
                                for (int i = 0; i < cart.length(); i++) {
                                    JSONObject jsoncart=cart.getJSONObject(i);
                                    String cart_id=jsoncart.getString("cart_id");
                                    String cart_attr=jsoncart.getString("cart_attr");
                                    String cart_userid=jsoncart.getString("cart_userid");
                                    String cart_shopid=jsoncart.getString("cart_shopid");
                                    String cart_num=jsoncart.getString("cart_num");
                                    String cart_price=jsoncart.getString("cart_price");
                                    String shop_title=jsoncart.getString("shop_title");
                                    String shop_pic=jsoncart.getString("shop_pic");
                                    String attr=jsoncart.getString("attr");
                                    JSONArray attrs=jsoncart.getJSONArray("attrs");
                                    //存储attrs发热数据
                                    List<com.bigdata.xinhuanufang.game.bean.attrs> mAttrs=new ArrayList<attrs>();
                                    for (int j = 0; j < attrs.length(); j++) {
                                        JSONObject js=attrs.getJSONObject(j);
                                        String attr_id=js.getString("attr_id");
                                        String attr_pid=js.getString("attr_pid");
                                        String attr_name=js.getString("attr_name");
                                        JSONArray childs=js.getJSONArray("childs");
                                        List<shopingCarchilds> mChilds=new ArrayList<shopingCarchilds>();
                                        for (int k = 0; k < childs.length(); k++) {
                                            JSONObject js2=childs.getJSONObject(k);
                                            String attr_id1=js2.getString("attr_id");
                                            String attr_name1=js2.getString("attr_name");
                                            mChilds.add(new shopingCarchilds(attr_id1, attr_name1));
                                        }
                                        mAttrs.add(new attrs(attr_id, attr_pid, attr_name, mChilds));
                                    }
                                    mCart.add(new cart(cart_id, cart_attr, cart_userid, cart_shopid, cart_num, cart_price, shop_title, shop_pic, attr, mAttrs));

                                }

                                shopingCarData.add(new NetworkDataCar(code, mCart));
                                Message msg = Message.obtain();
                                msg.what = 120;
                                msg.obj = shopingCarData;
                                handler.sendMessage(msg);
                            } catch (Exception e) {
                                // TODO: handle exception
                            }
                        }
                    }
                });
    }

    @Override
    public void initData() {
        // TODO Auto-generated method stub
        /**
         * bundle.putString("shopingColorIDInfo", shopingColorIDInfo);
         * bundle.putString("shopingColorInfo", shopingColorInfo);
         * bundle.putString("shopingID", shopingID);
         * bundle.putString("shopingNetWorkIDInfo", shopingNetWorkIDInfo);
         * bundle.putString("shopingNetWorkInfo", shopingNetWorkInfo);
         * bundle.putString("shopingVolumekInfo", shopingVolumekInfo);
         * bundle.putString("shopingVolumekIDInfo", shopingVolumekIDInfo);
         * bundle.putString("shopingPrice", shopingPrice);
         * bundle.putString("shopingTitle", shopingTitle);
         * bundle.putString("shopingPic", shopingPic);
         */
        bundle = getIntent().getExtras();
        ids = bundle.getString("ids");
        shopingID = bundle.getString("shopingID");
        biaoji = bundle.getString("biaoji");
        if (biaoji.equals("mengxiang")) {

            think_attrid = bundle.getString("think_attrid");
        }
        setBundle(bundle);
        // 地址栏
        address_name = (TextView) this.findViewById(R.id.shoping_address_name);
        address_tel = (TextView) this.findViewById(R.id.shoping_address_tel);
        address_content = (TextView) this
                .findViewById(R.id.shoping_address_content);
        ll_aco_adress = (LinearLayout) findViewById(R.id.ll_aco_adress);
        // 商品栏
        tv_sli_productName = (TextView) findViewById(R.id.tv_sli_productName);
        enter_shoping_color_info = (TextView) findViewById(R.id.enter_shoping_color_info);
        tv_sli_price = (TextView) findViewById(R.id.tv_sli_price);
        iv_sli_productPhoto = (ImageView) findViewById(R.id.iv_sli_productPhoto);
        enter_shoping_bill = (TextView) findViewById(R.id.enter_shoping_bill);
        tv_sli_productName.setText(bundle.getString("shopingTitle"));
        //商品信息的条目

        shopping_info_list = (ListViewForScrollView) findViewById(R.id.shopping_info_list);
        shopping_info_list.setVisibility(View.GONE);
        shopping_info = (LinearLayout) findViewById(R.id.shopping_info);
        //判断商品的属性
        String ColorInfo = "";
        String VolumekInfo = "";
        String NetWorkInfo = "";
        if (!(bundle.getString("shopingColorInfo") == null)) {
            ColorInfo = bundle.getString("shopingColorInfo") + " ";
        }
        if (!(bundle.getString("shopingVolumekInfo") == null)) {
            VolumekInfo = bundle.getString("shopingVolumekInfo") + " ";
        }
        if (!(bundle.getString("shopingNetWorkInfo") == null)) {
            NetWorkInfo = bundle.getString("shopingNetWorkInfo");
        }

        enter_shoping_color_info.setText(ColorInfo + VolumekInfo + NetWorkInfo);
        if (biaoji.equals("mengxiang")) {
            tv_sli_price.setText(bundle.getString("shopingPrice")+".00");
        }else {
            tv_sli_price.setText(bundle.getString("shopingPrice"));
        }
        enter_shoping_bill.setText(bundle.getString("shopingPrice"));
        x.image().bind(iv_sli_productPhoto,
                Config.ip + bundle.getString("shopingPic"));
        shoppingpic = bundle.getString("shopingPic");
        // 支付方式
        enter_chose_pay_zhi = (CheckBox) findViewById(R.id.enter_chose_pay_zhi);
        enter_chose_pay_wei = (CheckBox) findViewById(R.id.enter_chose_pay_wei);
        // 金手套兑换
        tv_aco_gloveNum = (TextView) findViewById(R.id.tv_aco_gloveNum);
        jinshoutaoprice = bundle.getString("shopingPrice");
        StringBuffer sb = new StringBuffer();
        boolean isdian=false;
        for (int i = 0; i < jinshoutaoprice.length(); i++) {
            if (jinshoutaoprice.charAt(i) == '.') {
                sb.append("0.");
                isdian=true;
            } else {
                sb.append(jinshoutaoprice.charAt(i));
                if ((jinshoutaoprice.length()-1)==i) {
                    if (!isdian) {
                        sb.append('0');
                    }

                }
            }
        }
        tv_aco_gloveNum.setText("需要使用" + sb + "个金手套");
        tv_aco_gloveNum.setOnClickListener(this);
        // 留言
        leave_message = (EditText) findViewById(R.id.leave_message);
        // 提交订单的按钮
        put_bill = (Button) findViewById(R.id.put_bill);
        put_bill.setOnClickListener(this);
        ll_aco_adress.setOnClickListener(this);
        enter_chose_pay_wei.setOnClickListener(this);
        enter_chose_pay_zhi.setOnClickListener(this);
        AddressLists = new ArrayList<UserAddress>();
        // 刚进来获取地址列表
        // 如果有则显示服务器拿到的默认地址,如果没有,则提示用户设置默认收货地址
        // http://115.28.69.240/boxing/app/address_list.php?user_id=1
        getData();

    }

//    @Override
//    protected void onResume() {
//        super.onPause();
//        getData();
//        System.out.println("重新获取到焦点走了没有");
//        address_name.setText(Config.Address_name);
//        address_tel.setText(Config.Address_tel);
//        address_content.setText(Config.Address_address);
//    }


    /**
     * 获取收货地址的网络数据
     */
    public void getData() {
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/address_list.php?user_id=" + Config.userID),
                new CommonCallback<String>() {

                    @Override
                    public void onCancelled(CancelledException arg0) {
                    }

                    @Override
                    public void onError(Throwable arg0, boolean arg1) {
                    }

                    @Override
                    public void onFinished() {
                    }

                    @Override
                    public void onSuccess(String arg0) {

                        if (TextUtils.isEmpty(arg0)) {
                            return;
                        } else {
                            try {
                                JSONObject json = new JSONObject(arg0);
                                String code = json.getString("code");
                                JSONArray address = json
                                        .getJSONArray("address");
                                List<AddressList> addresslist = new ArrayList<AddressList>();
                                for (int i = 0; i < address.length(); i++) {
                                    JSONObject json1 = address.getJSONObject(i);

                                    String address_id = json1
                                            .getString("address_id");
                                    String address_status = json1
                                            .getString("address_status");
                                    String address_name = json1
                                            .getString("address_name");
                                    String address_tel = json1
                                            .getString("address_tel");
                                    String address_postcode = json1
                                            .getString("address_postcode");
                                    String address_content = json1
                                            .getString("address_content");
                                    addresslist.add(new AddressList(address_id,
                                            address_status, address_name,
                                            address_tel, address_postcode,
                                            address_content));
                                }
                                AddressLists.add(new UserAddress(code,
                                        addresslist));
                                Message msg = Message.obtain();
                                msg.obj = AddressLists;
                                msg.what = 0;
                                handler.sendMessage(msg);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.ll_aco_adress:
                Intent intent = new Intent(ConfirmOrderActivity.this,
                        GoodsAdressActivity.class);
                Bundle bundle = new Bundle();
                // 需要传递的ArrayList<Object>
                startActivityForResult(intent, 1);
                break;
            case R.id.enter_chose_pay_zhi:
                way = "支付宝";
                enter_chose_pay_wei.setChecked(false);
                break;
            case R.id.enter_chose_pay_wei:
                way = "微信";
                enter_chose_pay_zhi.setChecked(false);
                break;
            case R.id.tv_aco_gloveNum:
                way = "金手套兑换";
                //弹出popupwindow
                enter_chose_pay_zhi.setChecked(false);
                enter_chose_pay_wei.setChecked(false);

                showPayType();
                break;
            case R.id.put_bill:
                //在提交订单前将订单信息保存起来
                //地址信息
                shoppingconfig.shoping_address_name=address_name.getText().toString();
                shoppingconfig.shoping_address_tel=address_tel.getText().toString();
                shoppingconfig.shoping_address_content=address_content.getText().toString();
                //商品信息
                shoppingconfig.weixin_pay_result_fenlei=enter_shoping_color_info.getText().toString();
                shoppingconfig.weixin_pay_result_pic=shoppingpic;
                shoppingconfig.weixin_pay_result_shopping_name=tv_sli_productName.getText().toString();
                shoppingconfig.weixin_pay_result_price=tv_sli_price.getText().toString();

                //提交订单之前判断是否设置了收货地址和付款方式
                if (TextUtils.isEmpty(address_name.getText()) && TextUtils.isEmpty(address_tel.getText()) && TextUtils.isEmpty(address_content.getText())) {
                    showToast("请填写收货地址");
                    return;
                }
                if (way.equals("6")) {
                    showToast("请选择付款方式");
                    return;
                }
                if (way.equals("微信")) {
                    flag = String.valueOf(0);
                } else if (way.equals("支付宝")) {
                    flag = String.valueOf(1);
                } else if (way.equals("金手套兑换")) {
                    flag = String.valueOf(2);
                }
                /**
                 * (getBundle().getString("shopingVolumekIDInfo")==null?"":getBundle().getString("shopingVolumekIDInfo")+",") +
                 (getBundle().getString("shopingNetWorkIDInfo")==null?"":getBundle().getString("shopingNetWorkIDInfo")+",") +
                 (getBundle().getString("shopingColorIDInfo")==null?"":getBundle().getString("shopingColorIDInfo"))
                 */
                StringBuffer arts = new StringBuffer();
                if ((getBundle().getString("shopingVolumekIDInfo") != null && !(getBundle().getString("shopingVolumekIDInfo")).equals(""))) {
                    arts.append(getBundle().getString("shopingVolumekIDInfo") + ",");
                }
                if ((getBundle().getString("shopingNetWorkIDInfo") != null && !(getBundle().getString("shopingNetWorkIDInfo")).equals(""))) {
                    arts.append(getBundle().getString("shopingNetWorkIDInfo") + ",");
                }
                if ((getBundle().getString("shopingColorIDInfo")) != null && !(getBundle().getString("shopingColorIDInfo")).equals("")) {
                    arts.append(getBundle().getString("shopingColorIDInfo"));
                }
                //在这里进行判断是从哪里过来的订单信息,分两种情况进行支付
                if (biaoji.equals("2")) {
                    //直接下单和超级梦想过来的订单
                    NewPay(arts);
                } else if (biaoji.equals("mengxiang")) {
                    //直接下单和超级梦想过来的订单
                    NewPay(arts);
                } else if (biaoji.equals("1")) {
                    //这里是购物车过来的订单信息
                    ShoppingCarPay();
                }
                finish();

                break;
            case R.id.shoping_details_jinshoutao_chongzhi:
                //去充值,跳转到充值页面
                Intent intent1 = new Intent(ConfirmOrderActivity.this, BuyGoldenGloveActivity.class);
                startActivity(intent1);

                break;
            case R.id.shoping_details_jinshoutao_cancler:
                //取消
                enter_chose_pay_zhi.setEnabled(true);
                enter_chose_pay_wei.setEnabled(true);
                popupWindow.dismiss();
                break;
            case R.id.shoping_details_jinshoutao_enter:
                //确定
                enter_chose_pay_zhi.setEnabled(false);
                enter_chose_pay_wei.setEnabled(false);
                way = "金手套兑换";
                if (Double.parseDouble(Config.USER_GLOVES) >= Double.parseDouble(jinshoutaoprice)) {
                    popupWindow.dismiss();
                } else {
                    showToast("金手套数量不足");
                }

                break;
            default:
                break;
        }
    }

    /**
     * 直接下单和超级梦想过来的订单信息
     */
    private void NewPay(StringBuffer arts) {
        /**
         * 14，超级梦想、商城直接下单
         传递参数
         flag 0微信 1支付宝 2金手套
         user_id用户编号
         money价格
         message留言
         address_id地址编号
         shop_id商品编号
         cart_attr参数，代表商品属性编号

         */
        //http://47.93.113.190/app/shopcart_submit2.php?&flag=1&user_id=100004&cart_attr=7&money=370.00&message=&shop_id=null&address_id=55
        RequestParams params = new RequestParams(Config.ip + Config.app + "/shopcart_submit2.php?");
//        params.addHeader("Content-Type", "UTF-8");
        params.addBodyParameter("flag", flag);
        params.addBodyParameter("user_id", Config.userID);
        if (biaoji.equals("mengxiang")) {
            params.addBodyParameter("cart_attr", think_attrid);
            params.addBodyParameter("money", getBundle().getString("shopingPrice")+".00");
        }else{
            params.addBodyParameter("cart_attr", arts.toString());
            params.addBodyParameter("money", getBundle().getString("shopingPrice"));
        }
//        params.addBodyParameter("money", getBundle().getString("shopingPrice"));
        params.addBodyParameter("message", leave_message.getText() + "");
        params.addBodyParameter("shop_id", shopingID);//商品编号
        params.addBodyParameter("address_id", defaultAddress);
//        params.addBodyParameter("nums", String.valueOf(1));
        String a = params.toString();
        System.out.println("请求参数"+a);
        x.http().post(params, new CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {
                // TODO Auto-generated method stub
                //提交订单成功

                try {
                    JSONObject json = new JSONObject(arg0);
                    //时间戳
                    String code = json.getString("code");
                    time = json.getString("orders_outtradeno");
                    Message msg = Message.obtain();
                    msg.what = 100;
                    msg.obj = time;
                    handler.sendMessage(msg);
                    if (code.equals("1")) {
                        //判断如果是金手套兑换,就将时间戳返回给服务器,2代表金手套兑换
                        if (flag.equals("2")) {
                            RequestParams paramsGold = new RequestParams(NetConfig.PAY_GOLE_RESULT);
                            paramsGold.addBodyParameter("orders_outtradeno", time);
                            x.http().post(paramsGold, new CommonCallback<String>() {

                                @Override
                                public void onSuccess(String s) {
//                                    try {
//                                        JSONObject json = new JSONObject(s);
//                                        String code = json.getString("code");
//                                        if (code.equals("1")) {
                                    showToast("兑换成功");
                                    Intent intent=new Intent(ConfirmOrderActivity.this, MyShoppingMall.class);
                                    intent.putExtra("isfukuan","yifukuan");
                                    startActivity(intent);
                                    finish();
//                                        }else{
//                                            showToast("兑换失败");
//                                        }
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
                                }

                                @Override
                                public void onError(Throwable throwable, boolean b) {

                                }

                                @Override
                                public void onCancelled(CancelledException e) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /**
     * 购物车过来的订单信息
     */
    private void ShoppingCarPay() {
        RequestParams params = new RequestParams(Config.ip + Config.app + "/shopcart_submit.php?");
        String s=getBundle().getString("shopingPrice");
        params.addHeader("Content-Type", "UTF-8");
        params.addBodyParameter("flag", flag);
        params.addBodyParameter("user_id", Config.userID);
        params.addBodyParameter("ids", ids);
//                params.addBodyParameter("ids", arts.toString());
        params.addBodyParameter("money", getBundle().getString("shopingPrice"));
        params.addBodyParameter("message", leave_message.getText() + "");
        params.addBodyParameter("address_id", defaultAddress);
        String[]  str=ids.split(",");
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if ((str.length-1)==i) {
                sb.append(1);
            }else {
                sb.append(1 + ",");
            }
        }
        params.addBodyParameter("nums", sb.toString());
        x.http().post(params, new CommonCallback<String>() {
            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onSuccess(String arg0) {
                // TODO Auto-generated method stub
                //提交订单成功

                try {
                    JSONObject json = new JSONObject(arg0);
                    //时间戳
                    time = json.getString("orders_outtradeno");
                    String code = json.getString("code");
                    showToast(time);
                    Message msg = Message.obtain();
                    msg.what = 100;
                    msg.obj = time;
                    handler.sendMessage(msg);
                    if (code.equals("1")) {
                        //判断如果是金手套兑换,就将时间戳返回给服务器,2代表金手套兑换
                        if (flag.equals("2")) {
                            //http://115.28.69.240/boxing/app/shopcart_notify.php?orders_outtradeno=20170224175211834
                            x.http().get(new RequestParams(Config.ip + Config.app + "/shopcart_notify.php?orders_outtradeno=" + time), new CommonCallback<String>() {
                                @Override
                                public void onSuccess(String s) {
                                            showToast("下单成功");
                                            Intent intent=new Intent(ConfirmOrderActivity.this, MyShoppingMall.class);
                                            intent.putExtra("isfukuan","yifukuan");
                                            startActivity(intent);
                                            finish();
                                }

                                @Override
                                public void onError(Throwable throwable, boolean b) {
                                }

                                @Override
                                public void onCancelled(CancelledException e) {
                                }

                                @Override
                                public void onFinished() {
                                }
                            });
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void showPayType() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.store_jinshoutao_duihuan_ppw, null);
        View root = LayoutInflater.from(this).inflate(
                R.layout.activity_confirmorder, null);

        popupWindow = new PopupWindow(this);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //查找出popupwindow上的控件
        //当前用户金手套的数量
        shoping_details_jinshoutao_number = (TextView) contentView.findViewById(R.id.shoping_details_jinshoutao_number);
        shoping_details_jinshoutao_number.setText(Config.USER_GLOVES);
        shoping_details_jinshoutao_chongzhi = (TextView) contentView.findViewById(R.id.shoping_details_jinshoutao_chongzhi);
        //确定和取消按钮
        shoping_details_jinshoutao_cancler = (Button) contentView.findViewById(R.id.shoping_details_jinshoutao_cancler);
        shoping_details_jinshoutao_enter = (Button) contentView.findViewById(R.id.shoping_details_jinshoutao_enter);
        if ((Double.parseDouble(shoping_details_jinshoutao_number.getText().toString())) > (Double.parseDouble(tv_sli_price.getText().toString()))) {
            shoping_details_jinshoutao_enter.setTextColor(Color.BLACK);
        }
        shoping_details_jinshoutao_cancler.setOnClickListener(this);
        shoping_details_jinshoutao_enter.setOnClickListener(this);
        shoping_details_jinshoutao_chongzhi.setOnClickListener(this);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
//            Intent intent=getIntent();
//            /**
//             * intent.putExtra("name", name);
//             intent.putExtra("tel", tel);
//             intent.putExtra("content", content);
//             */
//            String name=intent.getStringExtra("name");
//            String tel=intent.getStringExtra("tel");
//            String content=intent.getStringExtra("content");
            if (!Config.Address_name.equals("0")) {
                address_name.setText(Config.Address_name);
            }

            Toast.makeText(this, ""+Config.Address_name, Toast.LENGTH_SHORT).show();
            if (!Config.Address_tel.equals("0")) {
                address_tel.setText(Config.Address_tel);
            }
            if (!Config.Address_address.equals("0")) {
                address_content.setText(Config.Address_address);
            }


        }
//        if (requestCode==1){
//
//        }
    }

}
