package com.bigdata.xinhuanufang.mine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.ShoppingCarAdapter;
import com.bigdata.xinhuanufang.game.bean.NetworkDataCar;
import com.bigdata.xinhuanufang.game.bean.attrs;
import com.bigdata.xinhuanufang.game.bean.cart;
import com.bigdata.xinhuanufang.game.bean.shopingCarchilds;
import com.bigdata.xinhuanufang.store.ConfirmOrderActivity;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class shoppingMallDaiZhiFuFragment extends Fragment implements View.OnClickListener {

    private ListView daifukuan_list;
    private CheckBox daifukuan_checkbox;
    private Button btn_daifukuan;
    private TextView daifukuan_money;
    private TextView tv_asc_total;
    private ShoppingCarAdapter shoppingcaradapter;
    private ListView shoppingCarLV; // 购物车listView

    private String  bianji = "编辑";
    private String choose="全选";
    private TextView tv_edit;
    private CheckBox all_Choose;
    public TextView shoping_money;//商品总价格id
    private String userID=Config.userID;//假设用户id为 1
    private int shopingCheckState;//单个商品条目索引

    private List<NetworkDataCar> shopingCarData;//商品数据
    private int sumNums;//用来记录商品的数量
    private TextView mSumNum;// 单个商品的总数量
    private ShoppingCarAdapter shoppingCarAdapter;
    private double onlyShoping;//一类商品的价格
    private double allPrice;
    private TextView shopingcar_number;	//所有商品总价
    private Button btn_asc_emptyAll;
    private String shoppingname;//商品的名称
    private int positionIndex=1;//记录哪一个条目选中
    private String shopingColorIDInfo;
    private String shopingNetWorkIDInfo;
    private String shopingVolumekIDInfo;
    private boolean isquanxuanchoose=true;
    private HashMap<Integer,Integer> ls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoppingmall_daizhifu_list, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        //数据列表
        shoppingCarLV = (ListView) view.findViewById(R.id.daifukuan_list);
//        shoppingcaradapter = new ShoppingCarAdapter(getActivity(),shopingCarData);
//        daifukuan_list.setAdapter(shoppingcaradapter);
        //全选
        all_Choose=(CheckBox) view.findViewById(R.id.daifukuan_checkbox);
        all_Choose.setOnClickListener(this);
        //结算
        btn_asc_emptyAll = (Button) view.findViewById(R.id.btn_daifukuan);
        //结算金额
        shoping_money=(TextView) view.findViewById(R.id.daifukuan_money);
        shopingcar_number=(TextView) view.findViewById(R.id.shopingcar_number);

        //结算按钮
        initData();
    }
    List<NetworkDataCar> lists1;
    protected void showListView(final List<NetworkDataCar> shopingCarData) {
        // 查找到list控件


        btn_asc_emptyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isquanxuan=true;
                LinkedHashMap<Integer, Boolean> isSelected = shoppingCarAdapter.getIsSelected();
                for (int i = 0; i < isSelected.size(); i++) {
                    isquanxuan=isquanxuan|isSelected.get(i);
                }
                if (!isquanxuan) {
                    Toast.makeText(getActivity(), "请选择支付商品", Toast.LENGTH_SHORT).show();
                    return;
                }
                //调起支付
                //拿到商品信息
//				new Pays(ShoppingCarActivity.this,shoppingname,shoping_money.getText().toString());
                //跳转到支付的详情页面,就需要下面的这些数据
                Intent intent=new Intent(getActivity(),ConfirmOrderActivity.class);
                //拿到条目索引
//                int position=positionIndex;
                int position=shopingCarData.get(0).getCart().size()-1;
                String shopingPrice=shoping_money.getText().toString();
                String shopingID=shopingCarData.get(0).getCart().get(positionIndex).getCart_id();
                String shopingPic=shopingCarData.get(0).getCart().get(positionIndex).getShop_pic();
                String shopingTitle=shopingCarData.get(0).getCart().get(positionIndex).getShop_title();
                String shoppingAttrs = Config.SHOPPING_ATTRS;
                String shopingColorInfo=Config.color;
                String shopingNetWorkInfo=Config.network;
                String shopingVolumekInfo=Config.volume;
                String shopingColorIDInfo=Config.shopingColorIDInfo;
                String shopingNetWorkIDInfo=Config.shopingNetWorkIDInfo;
                String shopingVolumekIDInfo=Config.shopingVolumekIDInfo;
                Bundle bundle = new Bundle();
                String ids=shopingCarData.get(0).getCart().get(position).getCart_id();
                StringBuffer sb=new StringBuffer();

                for (int i = 0; i < ls.size(); i++) {
                    if (i==ls.size()-1) {
                        sb.append(shopingCarData.get(0).getCart().get(i).getCart_id());
                    }else {
                        sb.append(shopingCarData.get(0).getCart().get(i).getCart_id()+ ",");
                    }
                }
                bundle.putString("biaoji","1");
                bundle.putString("ids", sb.toString());
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
                getActivity().finish();
            }
        });
        //在底部加载一个j
        shoppingCarAdapter = new ShoppingCarAdapter(getActivity(),
                shopingCarData);
        shoppingCarLV.setAdapter(shoppingCarAdapter);
        lists1=shoppingCarAdapter.getList();
        shoppingCarAdapter.setOnClick(new ShoppingCarAdapter.onClick() {

            @Override
            public void mins(int position, int num, double itemTotal) {
                // TODO Auto-generated method stub
                Log.e("add", num + "---" + itemTotal);
                Message msg = Message.obtain();
                msg.what = 0;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }

            @Override
            public void add(int position, int num, double itemTotal) {
                // TODO Auto-generated method stub
                Log.e("mins", num + "---" + itemTotal);
                Message msg = Message.obtain();
                msg.what = 1;
                msg.arg1 = position;
                handler.sendMessage(msg);
            }
        });

        ls = new HashMap<>();
        shoppingCarAdapter.setOnBoxChangeListener(new ShoppingCarAdapter.onCheckBoxChangeListener() {

            @Override
            public void onChanged(int position, boolean isChecked,String isquanchoose,int size) {

                if (isChecked) {
                    positionIndex=position;
                    shoppingCarAdapter.getIsSelected().put(position, true);
                    sumNums += Integer.parseInt(lists1.get(0).getCart()
                            .get(position).getCart_num());
                    //显示选择的数量

                    allPrice += (Double.parseDouble((lists1.get(0)
                            .getCart().get(position).getCart_price())) * Double.parseDouble((lists1
                            .get(0).getCart().get(position).getCart_num())));
                    Log.e("每个条目的价格", allPrice+"");
                    shoppingname=lists1.get(0).getCart().get(position).getShop_title();
                    //显示金额
                    shoping_money.setText(String.valueOf(allPrice));
                    if (isquanchoose.equals("全选")) {

                        for (int i = 0; i < size; i++) {
                            ls.put(i,i);

                        }
                    }else {
                        ls.put(position,position);
                    }
                    boolean isquanxuan=true;
                    List<NetworkDataCar> list = shoppingCarAdapter.getList();
                    for (int i = 0; i < list.get(0).getCart().size(); i++) {
                        System.out.println("选择状态"+list.get(0).getCart().get(i).isxuanzhong());
                        isquanxuan=isquanxuan&list.get(0).getCart().get(i).isxuanzhong();
                    }


                    if (isquanxuan) {
                        all_Choose.setChecked(true);
                        choose="全选";
                        shoppingCarAdapter.setAllChooseState(choose);
                        shoppingCarAdapter.notifyDataSetChanged();
//					all_Choose.setChecked(true);
                        isquanxuanchoose=false;
                    }
                } else {
                    isquanxuanchoose=true;
                    shoppingCarAdapter.getIsSelected().put(position, false);
                    sumNums -= Integer.parseInt(lists1.get(0).getCart()
                            .get(position).getCart_num());

                    allPrice -= (Double.parseDouble((lists1.get(0)
                            .getCart().get(position).getCart_price())) * Double.parseDouble((lists1
                            .get(0).getCart().get(position).getCart_num())));
                    Log.e("每个条目的价格", allPrice+"");
                    shoping_money.setText(String.valueOf(allPrice));
                    for (int i = 0; i < ls.size(); i++) {
                        try {
                            if (ls.get(i)==position) {
                                ls.remove(position);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    boolean isquanxuan=true;
                    LinkedHashMap<Integer, Boolean> isSelected = shoppingCarAdapter.getIsSelected();
                    for (int i = 0; i < isSelected.size(); i++) {
                        isquanxuan=isquanxuan&isSelected.get(i);
                    }
                    if (isquanxuan) {
                        all_Choose.setChecked(true);
                    }else{
                        all_Choose.setChecked(false);
                    }
                }
            }
        });

    }
    private void getNetWorkShopingCarData() {
        //http://115.28.69.240/boxing/app/shopcart_list.php?user_id=1
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/shopcart_list.php?user_id=" + userID.trim()),
                new Callback.CommonCallback<String>() {

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

    /**
     * 接受adapter传出来的价格
     * @param d
     */
    public void setShopingRate(double d){
        Log.e("jiage", d+"");
        shoping_money.setText(d+"");
    }

    public void initData() {
        // TODO Auto-generated method stub
        //想服务器那购物车的数据
        getNetWorkShopingCarData();
//        tv_itt_save.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//
//                if (shopingCarData.size()==0) {
//                    return;
//                }
//                if ((tv_itt_save.getText()).equals("编辑")) {
//                    tv_itt_save.setText("完成");
//                    bianji="完成";
////					shoppingCarAdapter = new ShoppingCarAdapter(ShoppingCarActivity.this,
////							shopingCarData,bianji,"");
////					shoppingCarLV.setAdapter(shoppingCarAdapter);
//                    shoppingCarAdapter.setShopingChooseState(shopingCheckState, shoppingCarAdapter.getIsSelected().get(shopingCheckState));
//                    shoppingCarAdapter.setEditState(bianji);
////					shoppingCarAdapter.getIsSelected().get(shopingCheckState);
//
//                    shoppingCarAdapter.notifyDataSetChanged();
//                    return;
//                }
//                if ((tv_itt_save.getText()).equals("完成")) {
//                    tv_itt_save.setText("编辑");
//                    bianji="编辑";
////					shoppingCarAdapter = new ShoppingCarAdapter(ShoppingCarActivity.this,
////							shopingCarData,bianji,"");
////					shoppingCarLV.setAdapter(shoppingCarAdapter);
//                    shoppingCarAdapter.setShopingChooseState(shopingCheckState, shoppingCarAdapter.getIsSelected().get(shopingCheckState));
//                    shoppingCarAdapter.setEditState(bianji);
//
//                    shoppingCarAdapter.notifyDataSetChanged();
//                    return;
//                }
//            }
//        });
    }



    private String jisuan() {
        double shangpinjiage=0.0;
        for (int i = 0; i < shopingCarData.get(0).getCart().size(); i++) {
            shangpinjiage=shangpinjiage+Double.parseDouble(shopingCarData.get(0).getCart().get(i).getCart_price());
        }

        return String.valueOf(shangpinjiage);
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()){
            case R.id.daifukuan_checkbox:
                if (isquanxuanchoose) {
                    choose="全选";
                    shoppingCarAdapter.setAllChooseState(choose);
                    shoppingCarAdapter.notifyDataSetChanged();
//					all_Choose.setChecked(true);
                    isquanxuanchoose=false;
                    //计算购物车后所有的价格
                    String jiage=jisuan();
                    allPrice= Double.parseDouble(jiage);
                    shoping_money.setText(jiage);

                    if (choose.equals("全选")) {

                        for (int i = 0; i < shopingCarData.get(0).getCart().size(); i++) {
                            ls.put(i,i);

                        }
                    }
                }else if (!isquanxuanchoose) {
                    choose="不全选";
                    shoppingCarAdapter.setAllChooseState(choose);
                    shoppingCarAdapter.notifyDataSetChanged();
//					all_Choose.setChecked(false);
                    isquanxuanchoose=true;
                    shoping_money.setText("0.0");
                }
                if(all_Choose.isChecked()){
                    choose="全选";
//			shoppingCarAdapter = new ShoppingCarAdapter(ShoppingCarActivity.this,
//					shopingCarData,bianji,choose);
//
//			shoppingCarLV.setAdapter(shoppingCarAdapter);
//			shoppingCarAdapter.notifyDataSetChanged();
                    shoppingCarAdapter.setAllChooseState(choose);
                    shoppingCarAdapter.notifyDataSetChanged();
                }else if(!all_Choose.isChecked()){
                    choose="不全选";
//			shoppingCarAdapter = new ShoppingCarAdapter(ShoppingCarActivity.this,
//					shopingCarData,bianji,choose);
//			shoppingCarLV.setAdapter(shoppingCarAdapter);
//			shoppingCarAdapter.notifyDataSetChanged();
                    shoppingCarAdapter.setAllChooseState(choose);
                    shoppingCarAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.btn_asc_emptyAll:
//				//调起支付
//				//拿到商品信息
////				new Pays(ShoppingCarActivity.this,shoppingname,shoping_money.getText().toString());
//				//跳转到支付的详情页面,就需要下面的这些数据
//				Intent intent=new Intent(ShoppingCarActivity.this,ConfirmOrderActivity.class);
//				//拿到条目索引
//				int position=positionIndex;
//
//				String shopingPrice=shoping_money.getText().toString();
//				String shopingID=shopingCarData.get(0).getCart().get(positionIndex).getCart_id();
//				String shopingPic=shopingCarData.get(0).getCart().get(positionIndex).getShop_pic();
//				String shopingTitle=shopingCarData.get(0).getCart().get(positionIndex).getShop_title();
//				String shoppingAttrs = Config.SHOPPING_ATTRS;
//				String shopingColorInfo=Config.color;
//				String shopingNetWorkInfo=Config.network;
//				String shopingVolumekInfo=Config.volume;
//				String shopingColorIDInfo=Config.shopingColorIDInfo;
//				String shopingNetWorkIDInfo=Config.shopingNetWorkIDInfo;
//				String shopingVolumekIDInfo=Config.shopingVolumekIDInfo;
//				Bundle bundle = new Bundle();
//				String ids=shopingCarData.get(0).getCart().get(position).getCart_id();
//				StringBuffer sb=new StringBuffer();
//
//				for (int i = 0; i < ls.size(); i++) {
//					if (i==ls.size()-1) {
//						sb.append(shopingCarData.get(0).getCart().get(i).getCart_id());
//					}else {
//						sb.append(shopingCarData.get(0).getCart().get(i).getCart_id()+ ",");
//					}
//				}
//				bundle.putString("biaoji","1");
//				bundle.putString("ids", sb.toString());
//				bundle.putString("shopingColorIDInfo", shopingColorIDInfo);
//				bundle.putString("shopingColorInfo", shopingColorInfo);
//				bundle.putString("shopingID", shopingID);
//				bundle.putString("shopingNetWorkIDInfo",
//						shopingNetWorkIDInfo);
//				bundle.putString("shopingNetWorkInfo", shopingNetWorkInfo);
//				bundle.putString("shopingVolumekInfo", shopingVolumekInfo);
//				bundle.putString("shopingVolumekIDInfo",
//						shopingVolumekIDInfo);
//				bundle.putString("shopingPrice", shopingPrice);
//				bundle.putString("shopingTitle", shopingTitle);
//				bundle.putString("shopingPic", shopingPic);
//				intent.putExtras(bundle);
//				startActivity(intent);
                break;
        }



    }

    private Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 120:
                    shopingCarData=(List<NetworkDataCar>) msg.obj;
                    showListView(shopingCarData);
                    break;
                case 0://减
                    int position = msg.arg1;
                    shopingCheckState=position;

                    if (shoppingCarAdapter.getIsSelected().get(position)) {
                        sumNums--;
//					shopingcar_number.setText(String.valueOf(sumNums));

                        allPrice = (allPrice - Double.parseDouble(lists1.get(0).getCart().get(position).getCart_price()));
                        shoping_money.setText(allPrice+"".trim());
                    }
                    break;
                case 1://加
                    int position1 = msg.arg1;
                    shopingCheckState=position1;
                    if (shoppingCarAdapter.getIsSelected().get(position1)) {
                        sumNums++;
//					shopingcar_number.setText(String.valueOf(sumNums));

                        allPrice = (allPrice + Double.parseDouble(lists1.get(0).getCart().get(position1).getCart_price()));
                        shoping_money.setText(String.valueOf(allPrice));
                    }

                    break;
                case 2:

                    break;

                default:
                    break;
            }
        };

    };








}

