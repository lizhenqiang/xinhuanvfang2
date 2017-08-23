package com.bigdata.xinhuanufang.mine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.main.LoginActivity;

import java.io.File;
import java.math.BigDecimal;

import cn.jpush.android.api.JPushInterface;

public class SettingActivity extends BaseActivity implements OnClickListener{
    private LinearLayout changePasswordLL; //修改密码LinearLayout
    private LinearLayout suggestionBackLL; //意见反馈LinearLayout
    private LinearLayout aboutUsLL; //关于我们LinearLayout
    private LinearLayout setting_clear_cache;
    private TextView setting_clear_cache_data;
    private LinearLayout ll_fragMine_gloveDeall;
    private ImageView jpush_derail;
    private boolean flag=true;//记录是否接受消息推送
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tvbanben;
    private String verName;

    @Override
    public int getView() {
        // TODO Auto-generated method stub
        return R.layout.activity_setting;
    }
    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        super.setTitle("设置");
        super.setGone();
        setBack();
    }
    @Override
    public void initData() {

        sp = getSharedPreferences("config",0);
        editor = sp.edit();
        changePasswordLL = (LinearLayout) findViewById(R.id.ll_setting_changePassword);
        changePasswordLL.setOnClickListener(this);
        suggestionBackLL = (LinearLayout) findViewById(R.id.ll_setting_suggestionBack);
        suggestionBackLL.setOnClickListener(this);
        aboutUsLL = (LinearLayout) findViewById(R.id.ll_setting_aboutUs);
        //清理缓存
        setting_clear_cache=(LinearLayout) findViewById(R.id.setting_clear_cache);
        setting_clear_cache_data=(TextView) findViewById(R.id.setting_clear_cache_data);
        setting_clear_cache.setOnClickListener(this);
        aboutUsLL.setOnClickListener(this);


        //当前版本
        tvbanben = (TextView) findViewById(R.id.tv_banben2);
        //检查版本名称
        try {
            //注意："com.example.try_downloadfile_progress"对应AndroidManifest.xml里的package="……"部分
            verName = SettingActivity.this.getPackageManager().getPackageInfo(
                    "com.bigdata.xinhuanufang", 0).versionName;
            tvbanben.setText(verName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("msg",e.getMessage());
        }
        //退出登录
        ll_fragMine_gloveDeall = (LinearLayout) findViewById(R.id.ll_fragMine_gloveDeall);
        ll_fragMine_gloveDeall.setOnClickListener(this);
        //是否接受推送的消息
        jpush_derail = (ImageView) findViewById(R.id.jpush_derail);
        if (sp.getString("reveiveMessage","").equals("-1")) {
            //不接受推送
            jpush_derail.setImageResource(R.drawable.guanbi);
        }else if (sp.getString("reveiveMessage","").equals("0")) {
            //不接受推送
            jpush_derail.setImageResource(R.drawable.kaiguan);
        }
        jpush_derail.setOnClickListener(this);

        try {
            setting_clear_cache_data.setText(getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ll_setting_changePassword:
                Intent intent = new Intent(SettingActivity.this,ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.jpush_derail:
                //是否接受消息推送

                if (flag) {
                    jpush_derail.setImageResource(R.drawable.guanbi);
                    JPushInterface.stopPush(getApplicationContext());
                    editor.putString("reveiveMessage","-1");
                    editor.commit();
                    flag=false;
                }else if (!flag){
                    jpush_derail.setImageResource(R.drawable.kaiguan);
                    JPushInterface.resumePush(getApplicationContext());
                    editor.putString("reveiveMessage","0");
                    editor.commit();
                    flag=true;
                }

                break;
            case R.id.ll_setting_suggestionBack:
                //意见反馈
                Intent suggestionIntent = new Intent(SettingActivity.this,SuggestionBackActivity.class);
                startActivity(suggestionIntent);
                finish();
                break;
            case R.id.ll_setting_aboutUs:
                //关于我们
                Intent aboutIntent = new Intent(SettingActivity.this,AboutUsActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.setting_clear_cache:
                //开始清空缓存操作
                clearAllCache(SettingActivity.this);
                try {
                    setting_clear_cache_data.setText(getTotalCacheSize(SettingActivity.this));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.ll_fragMine_gloveDeall:
                //清楚保存的密码
                SharedPreferences sp=getSharedPreferences("config",0);
                sp.edit().clear().commit();
                Intent intent2=new Intent(this, LoginActivity.class);
                startActivity(intent2);
                finish();
                break;
            default:
                break;
        }
    }

    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    // 获取文件大小
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 清除缓存
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
