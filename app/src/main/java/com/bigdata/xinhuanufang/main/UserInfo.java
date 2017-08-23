package com.bigdata.xinhuanufang.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.yuanjiaoImage;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static com.bigdata.xinhuanufang.mine.PersonalInfoActivity.getFileNameNoEx;

/**
 * Created by weiyu$ on 2017/4/12.
 * 用户注册时候进行头像设置
 */

public class UserInfo extends Activity implements View.OnClickListener {
    private TextView tv_itt_save;
    private ImageView iv_itt_delete;
    private ImageView iv_itt_collection;
    private ImageView iv_itt_back;
    private TextView tv_itt_title;
    private yuanjiaoImage register_user_header;
    // 记录设置的头像的路径
    private String headerImage;
    // 调用系统相册-选择图片
    private static final int IMAGE = 1;
    private EditText register_username;
    private EditText register_usersex;
    private EditText register_usersign;
    private Button btn_register_finish;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_userinfo);
        initView();
    }

    private void initView() {
        tv_itt_save = (TextView) findViewById(R.id.tv_itt_save);
        tv_itt_save.setText("跳过");
        tv_itt_save.setOnClickListener(this);
        iv_itt_delete = (ImageView) findViewById(R.id.iv_itt_delete);
        iv_itt_delete.setVisibility(View.GONE);
        iv_itt_collection = (ImageView) findViewById(R.id.iv_itt_collection);
        iv_itt_collection.setVisibility(View.GONE);
        iv_itt_back = (ImageView) findViewById(R.id.iv_itt_back);
        iv_itt_back.setVisibility(View.GONE);
        tv_itt_title = (TextView) findViewById(R.id.tv_itt_title);
        tv_itt_title.setText("完善信息");

        register_user_header = (yuanjiaoImage) findViewById(R.id.register_user_header);
        register_user_header.setOnClickListener(this);

        register_username = (EditText) findViewById(R.id.register_username);
        register_usersex = (EditText) findViewById(R.id.register_usersex);
        register_usersign = (EditText) findViewById(R.id.register_usersign);
        btn_register_finish = (Button) findViewById(R.id.btn_register_finish);
        btn_register_finish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_itt_save:
                Intent in=new Intent(this,MainActivity.class);
                startActivity(in);
                break;
            case R.id.register_user_header:
                // 设置头像

//                Intent headerIntent = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(headerIntent, IMAGE);
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},120);
                        return;
                    }else{
                        Intent headerIntent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(headerIntent, IMAGE);
                    }
                } else {
                    //上面已经写好的拨号方法
                    Intent headerIntent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(headerIntent, IMAGE);
                }
                break;
            case R.id.btn_register_finish:
                if (register_username==null||"".equals(register_username.getText().toString())) {
                    Toast.makeText(UserInfo.this,"昵称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (register_usersex==null||"".equals(register_usersex.getText().toString())) {
                    Toast.makeText(UserInfo.this,"性别不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (register_usersign==null||"".equals(register_usersign.getText().toString())) {
                    Toast.makeText(UserInfo.this,"签名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!register_usersex.getText().toString().equals("1")) {
                    Config.USER_SEX=register_usersex.getText().toString();
                }
                Config.USER_USERNAME=register_username.getText().toString();
                Config.USER_SIGN=register_usersign.getText().toString();
                Intent intent =new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 120:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(this, "没有授权", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK
                && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage,
                    filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            if (!imagePath.isEmpty()) {
                headerImage = null;
                headerImage = imagePath;
            }
            showImage(imagePath);
            Intent intent=new Intent();
            intent.putExtra("imagepath",imagePath);
            setResult(1,intent);
            com.bigdata.xinhuanufang.utils.Config.USER_HEAD=getFileNameNoEx(imagePath);
            c.close();
            //设置头像的
            setHeaderImage(headerImage);
        }
    }

        private void showImage(String imaePath) {
            Bitmap bm = BitmapFactory.decodeFile(imaePath);
        register_user_header.setImageBitmap(toRoundBitmap(bm));
            com.bigdata.xinhuanufang.utils.Config.USER_HEAD = getFileNameNoEx(imaePath);
        }

    /**
     * 把bitmap转成圆形
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int r = 0;
        // 取最短边做边长
        if (width < height) {
            r = width;
        } else {
            r = height;
        }
        // 构建一个bitmap
        Bitmap backgroundBm = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBm);
        Paint p = new Paint();
        // 设置边缘光滑，去掉锯齿
        p.setAntiAlias(true);
        RectF rect = new RectF(0, 0, r, r);
        // 通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        // 且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r / 2, r / 2, p);
        // 设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }
    public  String bitmapToBase64(Bitmap bm) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     *
     * @param image
     * @param pixelW
     * @param pixelH
     * @return
     */
    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            os.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.ALPHA_8.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        //压缩好比例大小后再进行质量压缩
//      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }

    private void setHeaderImage(String headerImage) {
        // 设置完毕头像,向服务器发送头像地址
        // http://115.28.69.240/boxing/app/user_edit.php?user_id=1&status=5&xxxxx=%E6%97%A0%E4%B8%BA%E6%97%A0%E6%88%91
        if (!headerImage.isEmpty()) {

            Bitmap bms = BitmapFactory.decodeFile(headerImage);
            Bitmap bm=ratio(bms,120,120);
            String img=bitmapToBase64(bm);


            RequestParams params = new RequestParams(Config.ip+Config.app+"/upload_img.php?");
            params.addBodyParameter("img", img);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String s) {
                    String a = s;
                    try {
                        JSONObject json = new JSONObject(s);
                        String code = json.getString("code");
                        String path = json.getString("path");
                        com.bigdata.xinhuanufang.utils.Config.USER_HEAD = path;
                        if (code.equals("1")) {
                            //上传头像成功
                            // 设置完毕头像,向服务器发送头像地址
                            // http://115.28.69.240/boxing/app/user_edit.php?user_id=1&status=5&xxxxx=%E6%97%A0%E4%B8%BA%E6%97%A0%E6%88%91

                            x.http().get(
                                    new RequestParams(com.bigdata.xinhuanufang.utils.Config.ip
                                            + com.bigdata.xinhuanufang.utils.Config.app
                                            + "/user_edit.php?user_id="
                                            + com.bigdata.xinhuanufang.utils.Config.userID
                                            + "&status=1&xxxxx=" + path),
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
                                                    Toast.makeText(UserInfo.this, "头像设置成功", Toast.LENGTH_SHORT).show();
                                                }
                                                if (fail.equals(code)) {
                                                    Toast.makeText(UserInfo.this, "头像设置失败", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }

                                    });
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
}
