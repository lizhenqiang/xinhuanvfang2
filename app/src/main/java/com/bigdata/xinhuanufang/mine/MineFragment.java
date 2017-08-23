package com.bigdata.xinhuanufang.mine;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.CircleImageView;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/*
 * 我的 Fragement
 *
 * 17/02/28
 * */
public class MineFragment extends Fragment implements OnClickListener {
    private LinearLayout personalMsgLL; // 个人资料LinearLayout
    private TextView myAttentionTV; // 我的关注TextView
    private TextView myCollectionTV; // 我的收藏TextView
    private TextView myShareTV;// 我的分享TextView
    private LinearLayout gloveDealLL; // 金手套交易LinearLayout
    private LinearLayout luckyGameLL; // 我的抽奖游戏LinearLayout
    private LinearLayout superDreamLL; // 我的超级梦想LinearLayout
    private LinearLayout settingll; // 设置LinearLayout
    private Button chongZhiBtn; // 充值Button
    private LinearLayout ll_fragMine_gameGussing;

    private LinearLayout ll_fragM_signIn; // 签到布局
    private TextView TV_fragMine_signInList; // 签到榜
    private LinearLayout my_mall;
    private TextView tv_fragMine_name;
    private TextView tv_fragMine_myGloveNum;
    private TextView tv_fragMine_signName;
    private CircleImageView iv_fragMine_photo;
    private String imagepath;
    private ImageView tv_fragMine_sex;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        // 我的界面个人信息展示条目
        personalMsgLL = (LinearLayout) view
                .findViewById(R.id.ll_fragMine_personalMsg);
        personalMsgLL.setOnClickListener(this);
        //我的关注
        myAttentionTV = (TextView) view
                .findViewById(R.id.tv_fragMine_myAttention);
        myAttentionTV.setOnClickListener(this);
        myCollectionTV = (TextView) view
                .findViewById(R.id.tv_fragMine_myCollection);
        myCollectionTV.setOnClickListener(this);
        myShareTV = (TextView) view.findViewById(R.id.tv_fragMine_myShare);
        myShareTV.setOnClickListener(this);
        gloveDealLL = (LinearLayout) view
                .findViewById(R.id.ll_fragMine_gloveDeal);
        gloveDealLL.setOnClickListener(this);
        ll_fragMine_gameGussing = (LinearLayout) view.findViewById(R.id.ll_fragMine_gameGussing);
        ll_fragMine_gameGussing.setOnClickListener(this);
        luckyGameLL = (LinearLayout) view.findViewById(R.id.ll_fragMine_myGame);
        luckyGameLL.setOnClickListener(this);
        superDreamLL = (LinearLayout) view
                .findViewById(R.id.ll_fragMine_superDream);
        superDreamLL.setOnClickListener(this);
        settingll = (LinearLayout) view.findViewById(R.id.ll_fragMine_setting);
        settingll.setOnClickListener(this);
        chongZhiBtn = (Button) view.findViewById(R.id.btn_fragMine_chongZhi);
        chongZhiBtn.setOnClickListener(this);

        ll_fragM_signIn = (LinearLayout) view
                .findViewById(R.id.ll_fragM_signIn);
        ll_fragM_signIn.setOnClickListener(this);
        TV_fragMine_signInList = (TextView) view
                .findViewById(R.id.TV_fragMine_signInList);
        TV_fragMine_signInList.setOnClickListener(this);
        //个人信息的展示
        //昵称
        tv_fragMine_name = (TextView) view.findViewById(R.id.tv_fragMine_name);
        tv_fragMine_name.setText(Config.USER_USERNAME);
        //金手套
        tv_fragMine_myGloveNum = (TextView) view.findViewById(R.id.tv_fragMine_myGloveNum);
        tv_fragMine_myGloveNum.setText(Config.USER_GLOVES);
        //性别
        tv_fragMine_sex = (ImageView) view.findViewById(R.id.tv_fragMine_sex);
        if (Config.USER_SEX.equals("男")) {
            tv_fragMine_sex.setImageResource(R.drawable.nan);
        }

        if (Config.USER_SEX.equals("女")) {
            tv_fragMine_sex.setImageResource(R.drawable.nv);
        }
        //个性签名
        tv_fragMine_signName = (TextView) view.findViewById(R.id.tv_fragMine_signName);
        tv_fragMine_signName.setText(Config.USER_SIGN);
        //头像
        iv_fragMine_photo = (CircleImageView) view.findViewById(R.id.iv_fragMine_photo);
        if (!Config.USER_HEAD.equals("1")) {
//            Bitmap bmp = BitmapFactory.decodeFile(Config.USER_HEAD + ".jpg");
//            iv_fragMine_photo.setImageBitmap(bmp);
            if (!Config.RELEVANCE_TYPE.equals("3")) {
                x.image().bind(iv_fragMine_photo,Config.USER_HEAD);
                String asd=Config.USER_HEAD;
            }else {
                x.image().bind(iv_fragMine_photo, Config.ip + Config.USER_HEAD);
//            Picasso.with(getActivity()).load(Config.ip+Config.USER_HEAD).into(iv_fragMine_photo);
            }
        }
        //商城订单
        my_mall = (LinearLayout) view.findViewById(R.id.my_mall);
        my_mall.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //http://115.28.69.240/boxing/app/login.php?user_tel=18519011617&user_pwd=F379EAF3C831B04DE153469D1BEC345E
       refresh("0");
    }

    private void refresh(String number) {
        if (!Config.RELEVANCE_TYPE.equals("3")) {
            //进行三方账号登录的,就在本地进行金手套数量的计算
            Config.USER_GLOVES= String.valueOf(Integer.parseInt(Config.USER_GLOVES)+Integer.parseInt(number));
            String s=Config.USER_GLOVES;
            tv_fragMine_myGloveNum.setText(Config.USER_GLOVES);
        }else {
            //如果不是使用三方账号登录的,直接刷新
            SharedPreferences sp = getActivity().getSharedPreferences("config", 0);
            String password = sp.getString("password", "");
            x.http().get(new RequestParams(Config.ip + Config.app + "/login.php?user_tel=" + Config.USER_TEL + "&user_pwd=" + password), new CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    try {
                        JSONObject js = new JSONObject(s);
                        JSONObject json = js.getJSONObject("user");
                        String user_id = json.getString("user_id");
                        String user_delete = json.getString("user_delete");
                        String user_tel = json.getString("user_tel");
                        String user_gloves = json.getString("user_gloves");
                        String user_head = json.getString("user_head");
                        String user_username = json.getString("user_username");
                        String user_pwd = json.getString("user_pwd");
                        String user_sex = json.getString("user_sex");
                        String user_sign = json.getString("user_sign");
                        String user_date = json.getString("user_date");

                        if (Config.RELEVANCE_TYPE.equals("3")) {
                            tv_fragMine_myGloveNum.setText(user_gloves);
                            Config.USER_GLOVES=user_gloves;
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_fragMine_personalMsg:
                // 跳转到个人资料
                Intent intent = new Intent(getActivity(),
                        PersonalInfoActivity.class);
                intent.putExtra("imagepath", imagepath);
//				startActivity(intent);
                startActivityForResult(intent, 1);

                break;
            case R.id.tv_fragMine_myAttention:
                //我的关注
                Intent attentionIntent = new Intent(getActivity(),
                        MyAttentionActivity.class);
                startActivity(attentionIntent);
                break;
            case R.id.tv_fragMine_myCollection:
                //我的收藏
                Intent collectionIntent = new Intent(getActivity(),
                        MyCollectionActivity.class);
                startActivity(collectionIntent);
                break;
            case R.id.tv_fragMine_myShare:
                //我的分享
                Intent shareIntent = new Intent(getActivity(),
                        MyShareActivity.class);
                startActivity(shareIntent);
                break;
            case R.id.ll_fragMine_gloveDeal:
                //金手套交易
                Intent gloveIntent = new Intent(getActivity(),
                        GoldenGloveActivity.class);
                gloveIntent.putExtra("tv_fragMine_myGloveNum",tv_fragMine_myGloveNum.getText());
                startActivity(gloveIntent);
                break;
            case R.id.ll_fragMine_myGame:
                //我的抽奖游戏
                Intent gameIntent = new Intent(getActivity(),
                        LuckyGameActivity.class);
                startActivity(gameIntent);
                break;
            case R.id.ll_fragMine_superDream:
                //我的超级梦想
                Intent superIntent = new Intent(getActivity(),
                        MySuperDreamActivity.class);
                startActivity(superIntent);
                break;
            case R.id.ll_fragMine_setting:
                Intent settingIntent = new Intent(getActivity(),
                        SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.btn_fragMine_chongZhi:
                // 充值
                // 注意:由于充值涉及到了支付,所以应该充值完毕后重新获取我的界面的数据
                Intent chongZhiIntent = new Intent(getActivity(),
                        BuyGoldenGloveActivity.class);
                chongZhiIntent.putExtra("tv_fragMine_myGloveNum",tv_fragMine_myGloveNum.getText());
                startActivity(chongZhiIntent);

                break;
            case R.id.ll_fragM_signIn:
                // 在这里首先应该从服务器拿到数据,进行判断,用户当天是否签到,从而确定点击之后是显示签到榜还是签到两个字
                // 签到的判断
                isRegister();

                break;
            case R.id.TV_fragMine_signInList:
                //签到榜
                Intent signListIntent = new Intent(getActivity(),
                        SignInListActivity.class);
                startActivityForResult(signListIntent,2);
                break;
            case R.id.my_mall:
                //商城订单
                Intent intentshopping = new Intent(getActivity(),
                        MyShoppingMall.class);
                startActivity(intentshopping);
                break;
            case R.id.ll_fragMine_gameGussing:
                //我的加油竞猜
                Intent intent11 = new Intent(getActivity(),
                        MineJiaYouGuessing.class);
                startActivity(intent11);
                break;
            default:
                break;
        }
    }

    private void isRegister() {
        // http://115.28.69.240/boxing/app/calendar_goed.php?user_id=1
        x.http().get(
                new RequestParams(Config.ip + Config.app
                        + "/calendar_goed.php?user_id=" + Config.userID),
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
                        String success = "1";
                        String fail = "0";
                        try {
                            JSONObject json = new JSONObject(arg0);
                            String code = json.getString("code");
                            if (success.equals(code)) {
                                // 返回1 已经签到过了,应该显示签到榜,进入签到榜
                                ll_fragM_signIn.setVisibility(View.GONE);
                                TV_fragMine_signInList
                                        .setVisibility(View.VISIBLE); // 签到榜TextView可见
                                Intent signListIntent = new Intent(getActivity(),
                                        SignInListActivity.class);
                                startActivityForResult(signListIntent,2);

                            }
                            if (fail.equals(code)) {
                                // 返回 0未签到.点击之后如果签到成功就显示签到榜
                                // 进行签到http://115.28.69.240/boxing/app/calendar_add.php?user_id=1
                                x.http().get(
                                        new RequestParams(Config.ip
                                                + Config.app
                                                + "/calendar_add.php?user_id="
                                                + Config.userID), new CommonCallback<String>() {
                                            @Override
                                            public void onCancelled(
                                                    CancelledException arg0) {
                                            }

                                            @Override
                                            public void onError(
                                                    Throwable arg0,
                                                    boolean arg1) {
                                            }

                                            @Override
                                            public void onFinished() {
                                            }

                                            @Override
                                            public void onSuccess(
                                                    String arg0) {
                                                String success = "1";
                                                String fail = "0";
                                                try {
                                                    JSONObject json = new JSONObject(arg0);
                                                    String code = json.getString("code");
                                                    if (success.equals(code)) {
                                                        // 返回1 签到成功,签到榜可见
                                                        ll_fragM_signIn.setVisibility(View.GONE);
                                                        TV_fragMine_signInList
                                                                .setVisibility(View.VISIBLE); // 签到榜TextView可见
                                                    }
                                                    if (fail.equals(code)) {
                                                        Toast.makeText(getActivity(), "签到失败,请检查网络", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (JSONException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();

                                                }
                                            }
                                        });

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
    }

//	@Override
//	public void onPause() {
//		super.onPause();
//		Bitmap bmp= BitmapFactory.decodeFile(com.bigdata.xinhuanufang.utils.Config.USER_HEAD+".jpg");
//		System.out.println("头像的路径"+com.bigdata.xinhuanufang.utils.Config.USER_HEAD);
//		iv_fragMine_photo.setImageBitmap(bmp);
//	}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data != null) {
            imagepath = data.getStringExtra("imagepath");
            String name=data.getStringExtra("name");
            String sex=data.getStringExtra("sex");
            String sign=data.getStringExtra("sign");
            if (!Config.USER_hosted_HEAD.equals("1")) {
                Bitmap bmp= BitmapFactory.decodeFile(com.bigdata.xinhuanufang.utils.Config.USER_hosted_HEAD);
                iv_fragMine_photo.setImageBitmap(bmp);
            }else {
                if (!com.bigdata.xinhuanufang.utils.Config.RELEVANCE_TYPE.equals("3")) {
                    x.image().bind(iv_fragMine_photo, Config.USER_HEAD);
                } else {
                    x.image().bind(iv_fragMine_photo, Config.ip + imagepath);
//            Picasso.with(getActivity()).load(Config.ip+Config.USER_HEAD).into(iv_fragMine_photo);
                }
            }
            tv_fragMine_name.setText(name);
            if (sex.equals("男")) {
                tv_fragMine_sex.setImageResource(R.drawable.nan);
            }

            if (sex.equals("女")) {
                tv_fragMine_sex.setImageResource(R.drawable.nv);
            }
            tv_fragMine_signName.setText(sign);
        }
        if (requestCode == 2 && data != null) {
//            String number=data.getStringExtra("number");
//            int old=Integer.parseInt(tv_fragMine_myGloveNum.getText().toString());
//            System.out.println("----------"+"旧的"+old);
//            int now=Integer.parseInt(number);
//            System.out.println("----------"+"新的"+now);
//            System.out.println("----------"+"总数"+(old+now));
            String number=data.getStringExtra("number");
            refresh(number);
        }
    }

}
