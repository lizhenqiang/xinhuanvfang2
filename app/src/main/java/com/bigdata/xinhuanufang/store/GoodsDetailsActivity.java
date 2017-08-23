package com.bigdata.xinhuanufang.store;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GoodsDetailsViewpagerAdapter;
import com.bigdata.xinhuanufang.adapter.PopupWindowAdapter;
import com.bigdata.xinhuanufang.adapter.PopupWindowAdapter.NatureDataListener;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.yuanjiaoImage;
import com.bigdata.xinhuanufang.game.bean.Childs;
import com.bigdata.xinhuanufang.game.bean.Shop;
import com.bigdata.xinhuanufang.game.bean.ShopAttr;
import com.bigdata.xinhuanufang.game.bean.Shoppic;
import com.bigdata.xinhuanufang.game.bean.shopingAttrsParams;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author asus 商品详情activity
 */
public class GoodsDetailsActivity extends BaseActivity implements
        OnClickListener {
    private static final String TAG = "GoodsDetailsActivity";
    private List<String> imageResIds = new ArrayList<String>(); // Viewpager要显示的图片
    private static final int SHOW_NEXT_PAGE = 0; // 显示下一页
    private ViewPager goodsDetaolsViewPager; // 商品详情的ViewPager
    private Button buyBtn; // 购买Button
    private Button joinshopingcar;// 加入购物车button
    private Context content;
    private TextView tv_shoping_name;// 商品名称
    private TextView tv_agd_money;// 商品价格
    private String shop_id;// 记录商品的id
    private List<shopingAttrsParams> mParamsAttrs = new ArrayList<shopingAttrsParams>();// 商品参数集合
    private shopingAttrsParams shopingattrs = new shopingAttrsParams();
    private ListView shoping_nature;
    // 存储商品信息的集合
    private List<Shop> shopList;

    // popupwindows里面的属性值
    private TextView shoping_color_result; // 颜色选择结果
    private TextView shoping_volume_result; // 容量选择结果
    private TextView shoping_network_result; // 网络选择结果
    private ImageView shoping_params_close; // 关闭商品属性选择界面
    private PopupWindow popupWindow;
    private WebView shoping_details_webview; // 商品详情界面的网页
    private Button shoping_enter;// 确定
    private PopupWindowAdapter popupwindowadapter;
    private byte[] buffer;// 用来接收网络数据
    private List<Shop> vShop = new ArrayList<Shop>();
    private Handler handler = new Handler() {
        @SuppressWarnings("unchecked")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_NEXT_PAGE:
                    showNextPage();
                    break;
                case 110:
                    vShop = (List<Shop>) msg.obj;
                    isdianzan(vShop);
                    Log.e(TAG, "传递过来的数据是" + vShop.size());
                    break;
                case 120:
                    imageResIds = (List<String>) msg.obj;
                    Log.e(TAG, "传递过来的数据是图片" + imageResIds.size());
                    /*
         * ViewPager的一些配置
		 */
                    goodsDetaolsViewPager.setAdapter(new GoodsDetailsViewpagerAdapter(
                            imageResIds, GoodsDetailsActivity.this));
                    break;
            }
        }
    };

    private void isdianzan(List<Shop> vShop) {
        iv_itt_collection.setVisibility(View.VISIBLE);
        if (is_wish.equals("0")) {
            iv_itt_collection.setImageResource(R.drawable.weidianzan);
        } else if (is_wish.equals("1")) {
            iv_itt_collection.setImageResource(R.drawable.yidianzan);
        }
    }

    private String shopingID;
    private ImageView iv_itt_collection;
    private String is_wish;

    @Override
    public int getView() {
        // TODO Auto-generated method stub
        content = this;
        return R.layout.activity_goodsdetails;
    }

    @Override
    public void initData() {
        super.initData();
        Intent intent = getIntent();
        shop_id = intent.getStringExtra("shop_id");
        initnetworkdata(shop_id);
        shopingID = shop_id;
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        super.setTitle("商品详情");
        super.setGone();
        super.setBack();

        handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000); // 3秒后显示下一页
        goodsDetaolsViewPager = (ViewPager) findViewById(R.id.vp_agd_goodsPhoto);
        // 商品名称的获取
        tv_shoping_name = (TextView) findViewById(R.id.tv_agd_goodsPhotoDetails);
        // 价格
        tv_agd_money = (TextView) findViewById(R.id.tv_agd_money);

        buyBtn = (Button) findViewById(R.id.btn_agds_buy);
        // 加入购物车
        joinshopingcar = (Button) findViewById(R.id.btn_agd_joinShoppingCar);
        // 以网页的形式显示图文详情
        shoping_details_webview = (WebView) findViewById(R.id.shoping_details_webview);
        buyBtn.setOnClickListener(this);
        joinshopingcar.setOnClickListener(this);

        iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
        iv_itt_collection.setVisibility(View.GONE);

        iv_itt_collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://115.28.69.240/boxing/app/shop_wish.php?user_id=1&shop_id=2
                if (is_wish.equals("0")) {

                    x.http().get(new RequestParams(Config.ip + Config.app + "/shop_wish.php?user_id=" + Config.userID + "&shop_id=" + shop_id), new CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            try {
                                JSONObject json = new JSONObject(s);
                                String code = json.getString("code");
                                if (code.equals("1")) {
                                    Toast.makeText(content, "点赞成功", Toast.LENGTH_SHORT).show();
                                    is_wish = "1";
                                    iv_itt_collection.setImageResource(R.drawable.yidianzan);
                                } else if (code.equals("0")) {
                                    Toast.makeText(content, "点赞失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                } else if (is_wish.equals("1")) {
                    x.http().get(new RequestParams(Config.ip + Config.app + "/shop_wish.php?user_id=" + Config.userID + "&shop_id=" + shop_id), new CommonCallback<String>() {
                        @Override
                        public void onSuccess(String s) {
                            try {
                                JSONObject json = new JSONObject(s);
                                String code = json.getString("code");
                                if (code.equals("1")) {
                                    Toast.makeText(content, "取消点赞成功", Toast.LENGTH_SHORT).show();
                                    iv_itt_collection.setImageResource(R.drawable.weidianzan);
                                    is_wish = "0";
                                } else if (code.equals("0")) {
                                    Toast.makeText(content, "取消点赞失败", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
        });


        // handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000); // 3秒后显示下一页

        initWeb();// 图文详情的web界面
//        showNextPage();
        // 监听popupwindows,如果关闭之后,将透明度改回来

    }

    /**
     * 获取网络数据
     */
    private void initnetworkdata(String shop_id) {
        Log.e("AAA", "url=="+Config.ip + Config.app + "/shop_show.php?shop_id=" + shop_id + "&user_id=" + Config.userID);
        //http://115.28.69.240/boxing/app/shop_show.php?shop_id=12&user_id=100004
        x.http()
                .get(new RequestParams(Config.ip + Config.app + "/shop_show.php?shop_id=" + shop_id + "&user_id=" + Config.userID),
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
                                List<Shop> mShop = new ArrayList<Shop>();

                                try {
                                    JSONObject json = new JSONObject(arg0);
                                    JSONObject shop = json
                                            .getJSONObject("shop");
                                    String shop_id = shop
                                            .getString("shop_id");

                                    String shop_attrid = shop
                                            .getString("shop_attrid");
                                    String shop_title = shop
                                            .getString("shop_title");
                                    String shop_pic = shop
                                            .getString("shop_pic");
                                    String shop_price = shop
                                            .getString("shop_price");
                                    is_wish = shop
                                            .getString("is_wish");
                                    JSONArray shoppic = shop
                                            .getJSONArray("shoppic");
                                    tv_shoping_name.setText(shop_title);
                                    tv_agd_money.setText(shop_price);
                                    imageResIds.add(shop_pic);
                                    List<Shoppic> mShoppic = new ArrayList<Shoppic>();
                                    for (int i = 0; i < shoppic.length(); i++) {
                                        JSONObject js = shoppic
                                                .getJSONObject(i);
                                        String shoppic_id = js
                                                .getString("shoppic_id");
                                        String shoppic_pic = js
                                                .getString("shoppic_pic");


                                        imageResIds.add(shoppic_pic);
                                        mShoppic.add(new Shoppic(
                                                shoppic_id, shoppic_pic));
                                    }
                                    JSONArray shop_attr = shop
                                            .getJSONArray("shop_attr");
                                    List<ShopAttr> mShopAttr = new ArrayList<ShopAttr>();
                                    for (int i = 0; i < shop_attr.length(); i++) {
                                        JSONObject js = shop_attr
                                                .getJSONObject(i);
                                        String attr_id = js
                                                .getString("attr_id");
                                        String attr_name = js
                                                .getString("attr_name");
                                        JSONArray childs = js
                                                .getJSONArray("childs");
                                        List<Childs> mChilds = new ArrayList<Childs>();
                                        for (int j = 0; j < childs.length(); j++) {
                                            JSONObject js_1 = childs
                                                    .getJSONObject(j);
                                            String attr_id_1 = js_1
                                                    .getString("attr_id");
                                            String attr_name_1 = js_1
                                                    .getString("attr_name");
                                            mChilds.add(new Childs(
                                                    attr_id_1, attr_name_1));
                                        }
                                        mShopAttr.add(new ShopAttr(attr_id,
                                                attr_name, mChilds));
                                    }
                                    mShop.add(new Shop(shop_id,
                                            shop_attrid, shop_title,
                                            shop_pic, shop_price, mShoppic,
                                            mShopAttr));

                                    Message msg = Message.obtain();
                                    msg.what = 110;
                                    msg.obj = mShop;
                                    handler.sendMessage(msg);
                                    Message msg1 = Message.obtain();
                                    msg1.what = 120;
                                    msg1.obj = imageResIds;
                                    handler.sendMessage(msg1);
                                } catch (Exception e) {
                                    // TODO: handle exception
                                }
                            }

                        });

    }

    private void initWeb() {
        // TODO Auto-generated method stub
        shoping_details_webview.getSettings().setJavaScriptEnabled(true);
        shoping_details_webview.getSettings().setSupportZoom(true);
        shoping_details_webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        shoping_details_webview.getSettings().setLoadWithOverviewMode(true);
        shoping_details_webview.getSettings().setUseWideViewPort(true);
        // http://115.28.69.240/boxing/app/shop_content.php?shop_id=1
        shoping_details_webview.loadUrl(Config.ip + Config.app
                + "/shop_content.php?shop_id=" + shop_id);

    }

    /*
     * 显示下一页
     */
    public void showNextPage() {
        int currentItem = goodsDetaolsViewPager.getCurrentItem();
        currentItem=currentItem + 1;
        System.out.println("商品详情的轮播图"+currentItem);
        goodsDetaolsViewPager.setCurrentItem(currentItem);
        handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.btn_agds_buy:
                // 跳转到确认订单界面
                // Intent intent = new Intent(GoodsDetailsActivity.this,
                // ConfirmOrderActivity.class);
                // startActivity(intent);
                showPopupWindows("购买");
                break;
            case R.id.btn_agd_joinShoppingCar:
                showPopupWindows("加入购物车");
                break;
            case R.id.joinshopingcar_close:
                popupWindow.dismiss();
                break;
            // case R.id.joinshoping_enter:
            // /**加入购物车
            // * cart_userid用户编号 cart_shopid商品编号 cart_num数量 cart_price商品价格
            // * cart_attr属性编号集合
            // * ，中间用逗号分隔。注意必须是子分类的attr_id，只传一个子分类编号如24，如果传递多个子分类编号如24,128,39
            // */
            //
            // break;

            default:
                break;
        }
    }

    // 型号选择
    private void showPopupWindows(String s) {

        // TODO Auto-generated method stub
        View contentView = LayoutInflater.from(content).inflate(
                R.layout.joinshopingcar, null);
        popupWindow = new PopupWindow(content);
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
        final String sign = s;

        // listview的适配
        View headerView = LayoutInflater.from(content).inflate(R.layout.popupwindow_header, null);
        // 商品属性选择结果
        shoping_color_result = (TextView) headerView
                .findViewById(R.id.joinshopingcolor_result);
        shoping_volume_result = (TextView) headerView
                .findViewById(R.id.joinshopingvolume_result);
        shoping_network_result = (TextView) headerView
                .findViewById(R.id.joinshopnetwork_result);
        shoping_nature = (ListView) contentView
                .findViewById(R.id.shoping_nature);
        shoping_nature.addHeaderView(headerView);
        if (vShop.size() == 0) {
            Toast.makeText(content, "网络异常", Toast.LENGTH_SHORT).show();
            return;
        }
        popupwindowadapter = new PopupWindowAdapter(getApplicationContext(),
                vShop, shopingattrs);
        shoping_nature.setAdapter(popupwindowadapter);
        NatureDataListener onitemActionClick = new NatureDataListener() {

            @Override
            public void setdataing(shopingAttrsParams shopingattrs) {
                if (shopingattrs.getShopingColor() != null) {
                    shoping_color_result.setText(shopingattrs.getShopingColor());
                }
                if (shopingattrs.getShopingVolume() != null) {
                    shoping_volume_result.setText(shopingattrs.getShopingVolume());
                }
                if (shopingattrs.getShopingNetWork() != null) {
                    shoping_network_result.setText(shopingattrs.getShopingNetWork());
                }


            }
        };

        popupwindowadapter.setNatureDataListener(onitemActionClick);

        // 确定
        shoping_enter = (Button) contentView
                .findViewById(R.id.joinshoping_enter);
        // 关闭商品属性选择
        shoping_params_close = (ImageView) headerView
                .findViewById(R.id.joinshopingcar_close);
        initPopupwindowsParams(headerView);// popupwindows里面的数据
        shoping_params_close.setOnClickListener(this);
        shoping_enter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //进行属性选择判断
                if (shopingattrs.getShopingColor() == null || shopingattrs.getShopingColor() == null || shopingattrs.getShopingColor() == null) {
                    Toast.makeText(content, "请选择商品属性", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sign.equals("加入购物车")) {

                    // 点击确定按钮之后,获取到拿到的商品所有的属性进行跳转到购物车
                    // 在购物车进行结算的时候,首先校验收货地址和收货人的联系方式是否完整,最后发起支付请求
                    Intent intent1 = new Intent(content,
                            ShoppingCarActivity.class);
                    // 将商品的所有参数传递给服务器,进入购物车是从服务器那数据
                    // 拼接数据.想服务器发送数据
                    // vShop.get(0).getShopAttr().get(0).getChilds().get(i).getAttrName()
                    // 假定用户id为1
                    shopingattrs.setUserId(Config.userID);
                    // 商品id
                    shopingattrs.setShopingId(vShop.get(0).getShopId());
                    // 数量
                    shopingattrs.setShopingCount(1);
                    Config.SHOPPING_ATTRS = shopingattrs.getShopingColorId() + "," + shopingattrs.getShopingVolumeId() + "," + shopingattrs.getShopingNetWorkId();
                    // 价格
                    shopingattrs.setShopingPrice(Double.parseDouble(vShop
                            .get(0).getShopPrice()));
                    StringBuffer arts = new StringBuffer();
                    if (shopingattrs.getShopingColorId() != null) {
                        arts.append(shopingattrs.getShopingColorId());
                    }
                    if (shopingattrs.getShopingVolumeId() != null) {
                        arts.append("," + shopingattrs.getShopingVolumeId());
                    }
                    if (shopingattrs.getShopingNetWorkId() != null) {
                        arts.append("," + shopingattrs.getShopingNetWorkId());
                    }
                    String a = arts.toString();
                    // 开始向服务器发送数据
                    // http://115.28.69.240/boxing/app/shopcart_add.php?cart_userid=1&cart_shopid=1&cart_num=1&cart_price=5999.00&cart_attr=2,7
                    x.http().get(
                            new RequestParams(Config.ip + Config.app
                                    + "/shopcart_add.php?cart_userid="
                                    + Config.userID + "&cart_shopid="
                                    + vShop.get(0).getShopId() + "&cart_num="
                                    + shopingattrs.getShopingCount()
                                    + "&cart_price="
                                    + shopingattrs.getShopingPrice()
                                    + "&cart_attr=" +
                                    arts),
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
                                    try {
                                        JSONObject json = new JSONObject(arg0);
                                        String code = json.getString("code");
                                        if (code.equals("1")) {
                                            Toast.makeText(GoodsDetailsActivity.this,
                                                    "成功加入购物车", Toast.LENGTH_SHORT).show();
                                            popupWindow.dismiss();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                    // startActivity(intent1);
                    popupWindow.dismiss();
                }
                if (sign.equals("购买")) {
                    Intent intent = new Intent(GoodsDetailsActivity.this,
                            ConfirmOrderActivity.class);
                    // 将选定的对象在这里得到后进行传递
                    // 获取选定的颜色等属性
                    String shopingColorIDInfo = shopingattrs
                            .getShopingColorId();

                    String shopingColorInfo = shopingattrs.getShopingColor();
//                    shopingID = shopingattrs.getShopingId();
                    String shopingNetWorkIDInfo = shopingattrs
                            .getShopingNetWorkId();
                    String shopingNetWorkInfo = shopingattrs
                            .getShopingNetWork();
                    String shopingVolumekInfo = shopingattrs.getShopingVolume();
                    String shopingVolumekIDInfo = shopingattrs
                            .getShopingVolumeId();
                    // 获取商品的单价,数量,名称,图片vShop 住:数量默认是1
                    String shopingPrice = vShop.get(0).getShopPrice();
                    String shopingTitle = vShop.get(0).getShopTitle();
                    String shopingPic = vShop.get(0).getShopPic();
                    Config.volume = shopingVolumekInfo;
                    Config.network = shopingNetWorkInfo;
                    Config.color = shopingColorInfo;
                    Config.shopingColorIDInfo = shopingColorIDInfo;
                    Config.shopingNetWorkIDInfo = shopingNetWorkIDInfo;
                    Config.shopingVolumekIDInfo = shopingVolumekIDInfo;
                    Bundle bundle = new Bundle();
                    bundle.putString("biaoji", "2");
                    bundle.putString("shopingColorIDInfo", shopingColorIDInfo);
                    bundle.putString("shopingColorInfo", shopingColorInfo);
                    bundle.putString("shopingID", shopingID);
                    bundle.putString("shopingNetWorkIDInfo",
                            shopingNetWorkIDInfo);
                    bundle.putString("shopingNetWorkInfo", shopingNetWorkInfo);
                    bundle.putString("shopingVolumekInfo", shopingVolumekInfo);
                    bundle.putString("shopingVolumekIDInfo",
                            shopingVolumekIDInfo);
                    bundle.putString("shopingPrice", shopingPrice);
                    bundle.putString("shopingTitle", shopingTitle);
                    bundle.putString("shopingPic", shopingPic);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            }
        });
        // popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // setContentView(contentView);
        View rootview = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.activity_goodsdetails, null);
//		backgroundAlpha(0.4f);
//		popupWindow.setBackgroundDrawable(null);
        ColorDrawable dw = new ColorDrawable(Color.WHITE);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
//				backgroundAlpha(1f);
                shoping_color_result.setText("");
                shoping_volume_result.setText("");
                shoping_network_result.setText("");
            }
        });
    }

    private void initPopupwindowsParams(View headerView) {
        // TODO Auto-generated method stub
        yuanjiaoImage iv_shoping = (yuanjiaoImage) headerView
                .findViewById(R.id.iv_shoping);
        x.image().bind(iv_shoping, Config.ip + imageResIds.get(0));
        TextView shoping_rate = (TextView) headerView
                .findViewById(R.id.shoping_rate);
        shoping_rate.setText(tv_agd_money.getText());
        // 设置机身颜色
        TextView joinshoping_attr_id = (TextView) headerView
                .findViewById(R.id.joinshoping_attr_id);
        // joinshoping_attr_id.setText(vShop.get(0).getShopAttr().get(0)
        // .getAttrName());
    }

    /**
     * 设置屏幕 的透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        getWindow().setAttributes(lp);
    }

}
