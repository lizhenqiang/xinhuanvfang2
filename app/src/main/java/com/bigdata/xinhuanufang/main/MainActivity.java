package com.bigdata.xinhuanufang.main;

import android.app.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.game.GameFragment;
import com.bigdata.xinhuanufang.home.fragment.HomeFragment;
import com.bigdata.xinhuanufang.mine.MineFragment;
import com.bigdata.xinhuanufang.store.StoreFragment;
import com.bigdata.xinhuanufang.utils.Config;

public class MainActivity extends FragmentActivity implements OnClickListener {

    private HomeFragment homeFragment;
    private MainFragment mainFragment; // 用于展示首页的Fragment
    private GameFragment gameFragment; // 用于展示游戏的Fragment
    private StoreFragment storeFragment;// 用于展示商城的Fragment
    private MineFragment mineFragment; // 用于展示我的的Fragment

    private View mainLayout; // 首页页面布局
    private View gameLayout; // 游戏页面布局
    private View storeLayout; // 商城页面布局
    private View mineLayout; // 我的页面布局

    private ImageView mainImage; // 首页ImageView
    private ImageView gameImage; // 比赛ImageView
    private ImageView storeImage; // 商城ImageView
    private ImageView mineImage; // 我的ImageView

    private TextView mainText; // 首页文本
    private TextView gameText; // 比赛文本
    private TextView storeText;// 商城文本
    private TextView mineText; // 我的文本

    private FragmentManager fragmentManager; // 对Fragment进行管理

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏

        setContentView(R.layout.activity_main);
        initViews();
        fragmentManager = getSupportFragmentManager();

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            //接收name值
            String name = bundle.getString("name");
            if (name == "game") {
                setTabSelection(1); //启动游戏tab
            } else {
                // 第一次启动时选中第0个tab
                setTabSelection(0);
            }
        } else {

            setTabSelection(0);
        }
        if (Config.isbiaoshi.equals("shopping")) {
            setTabSelection(2);
        }

//        /**
//         *版本测试
//         */
//        int[] sdkver = TXRtmpApi.getSDKVersion();
//        if (sdkver != null && sdkver.length >= 3) {
//            Log.d("rtmpsdk", "rtmp sdk version is:" + sdkver[0] + "." + sdkver[1] + "." + sdkver[2]);
//        }


    }

    /**
     * 获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
     */
    private void initViews() {
        // TODO Auto-generated method stub
        mainImage = (ImageView) findViewById(R.id.main_image);
        gameImage = (ImageView) findViewById(R.id.game_image);
        storeImage = (ImageView) findViewById(R.id.store_image);
        mineImage = (ImageView) findViewById(R.id.mine_image);

        mainText = (TextView) findViewById(R.id.main_text);
        gameText = (TextView) findViewById(R.id.game_text);
        storeText = (TextView) findViewById(R.id.store_text);
        mineText = (TextView) findViewById(R.id.mine_text);

        mainLayout = findViewById(R.id.main_layout);
        gameLayout = findViewById(R.id.game_Layout);
        storeLayout = findViewById(R.id.store_layout);
        mineLayout = findViewById(R.id.mine_layout);
        mainLayout.setOnClickListener(this);
        gameLayout.setOnClickListener(this);
        storeLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.main_layout:
                // 当点击了主页tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.game_Layout:
                // 当点击了比赛tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.store_layout:
                // 当点击了商城tab时，选中第3个tab
                setTabSelection(2);
                break;
            case R.id.mine_layout:
                // 当点击了我的tab时，选中第4个tab
                setTabSelection(3);
                break;
            default:
                break;
        }
    }

    private void setTabSelection(int index) {
        // TODO Auto-generated method stub
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                mainImage.setImageResource(R.drawable.shouye_1);
                mainText.setTextColor(0xffd63c3c);
                if (homeFragment == null) {
                    // 如果mainFragment为空，则创建一个并添加到界面上
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_content, homeFragment);
                } else {
                    // 如果mainFragment不为空，则直接将它显示出来
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                gameImage.setImageResource(R.drawable.sy_bisai_1);
                gameText.setTextColor(0xffd63c3c);
                if (gameFragment == null) {
                    // 如果gameFragment为空，则创建一个并添加到界面上
                    gameFragment = new GameFragment();
                    transaction.add(R.id.main_content, gameFragment);
                } else {
                    // 如果gameFragment不为空，则直接将它显示出来
                    transaction.show(gameFragment);
                }
                break;
            case 2:
                storeImage.setImageResource(R.drawable.shangcheng_1);
                storeText.setTextColor(0xffd63c3c);
                if (storeFragment == null) {
                    // 如果storeFragment为空，则创建一个并添加到界面上
                    storeFragment = new StoreFragment();
                    transaction.add(R.id.main_content, storeFragment);
                } else {
                    // 如果storeFragment不为空，则直接将它显示出来
                    transaction.show(storeFragment);
                }
                //在切换fragment的时候进行获取数据

                break;
            case 3:
            default:
                mineImage.setImageResource(R.drawable.wode_1);
                mineText.setTextColor(0xffd63c3c);
                if (mineFragment == null) {
                    // 如果mineFragment为空，则创建一个并添加到界面上
                    mineFragment = new MineFragment();
                    transaction.add(R.id.main_content, mineFragment);
                } else {
                    // 如果mineFragment不为空，则直接将它显示出来
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        // TODO Auto-generated method stub
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (gameFragment != null) {
            transaction.hide(gameFragment);
        }
        if (storeFragment != null) {
            transaction.hide(storeFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        // TODO Auto-generated method stub
        mainImage.setImageResource(R.drawable.shouye_0);
        mainText.setTextColor(0xff333333);
        gameImage.setImageResource(R.drawable.sy_bisai_0);
        gameText.setTextColor(0xff333333);
        storeImage.setImageResource(R.drawable.shangcheng_0);
        storeText.setTextColor(0xff333333);
        mineImage.setImageResource(R.drawable.wode_0);
        mineText.setTextColor(0xff333333);
    }

    public interface IOnFocusListener {

        public void onWindowFocusChanged(boolean hasFocus);
    }

}
