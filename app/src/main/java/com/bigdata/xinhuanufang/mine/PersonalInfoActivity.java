package com.bigdata.xinhuanufang.mine;

import android.Manifest;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.custom.CircleImageView;
import com.bigdata.xinhuanufang.custom.yuanjiaoImage;
import com.bigdata.xinhuanufang.store.GoodsAdressActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author asus 个人资料Activity
 */
public class PersonalInfoActivity extends BaseActivity implements
        OnClickListener {
    private ImageView listIV; // 要隐藏掉的ImageView
    private TextView titleTV;// 个人资料TextView
    private RelativeLayout secondNameRL; // 昵称RelativeLayout
    private RelativeLayout signNameRL; // 签名RelativeLayout
    private RelativeLayout manageAdressRL; // 管理收货地址RelativeLayout
    private RelativeLayout sexRL; // 性别RelativeLayout
    private RelativeLayout thirdMethodRL; // 支付方式RelativeLayout
    private LinearLayout my_header_image;// 我的头像
    private CircleImageView iv_api_right;// 设置的头像
    private LinearLayout popupwindow_sex_nv;
    private LinearLayout popupwindow_sex_nan;
    // 调用系统相册-选择图片
    private static final int IMAGE = 1;
    private TextView my_secondname;//个人资料的昵称
    // 记录设置的头像的路径
    private String headerImage;
    //性别
    private TextView tv_sex;
    //签名
    private TextView my_qianming;
    private TextView user_number;
    private TextView mine_user_id;
    private TextView my_qianming1;
    private TextView my_secondname1;
    private ImageView user_flag;
    private yuanjiaoImage yuanjiao_picture;
    private byte[] bs;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    public int getView() {
        return R.layout.activity_personalinformations;
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("个人资料");
        setGone();
        super.iv_itt_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.putExtra("sex", com.bigdata.xinhuanufang.utils.Config.USER_SEX);
//                setResult(4, intent);
                finish();
            }
        });

    }

    @Override
    public void initData() {
        secondNameRL = (RelativeLayout) findViewById(R.id.rl_api_secondName);
        secondNameRL.setOnClickListener(this);
        //签名
        signNameRL = (RelativeLayout) findViewById(R.id.rl_api_signName);
        signNameRL.setOnClickListener(this);
        manageAdressRL = (RelativeLayout) findViewById(R.id.rl_api_manageAdress);
        manageAdressRL.setOnClickListener(this);
        sexRL = (RelativeLayout) findViewById(R.id.rl_api_sex);
        sexRL.setOnClickListener(this);
        user_flag = (ImageView) findViewById(R.id.user_flag);
        if (com.bigdata.xinhuanufang.utils.Config.RELEVANCE_TYPE.equals("0")) {
            user_flag.setImageResource(R.drawable.weixin_0);
        }else if (com.bigdata.xinhuanufang.utils.Config.RELEVANCE_TYPE.equals("1")) {
            user_flag.setImageResource(R.drawable.qq_0);
        }else if (com.bigdata.xinhuanufang.utils.Config.RELEVANCE_TYPE.equals("2")) {
            user_flag.setImageResource(R.drawable.weibo_0);
        }else if (com.bigdata.xinhuanufang.utils.Config.RELEVANCE_TYPE.equals("4")) {
            user_flag.setImageResource(0);
        }

        thirdMethodRL = (RelativeLayout) findViewById(R.id.rl_api_thirdMethod);
        thirdMethodRL.setOnClickListener(this);
        // 我的头像
        my_header_image = (LinearLayout) findViewById(R.id.my_header_image);
        my_header_image.setOnClickListener(this);
        yuanjiao_picture = (yuanjiaoImage) findViewById(R.id.yuanjiao_picture);
        iv_api_right = (CircleImageView) findViewById(R.id.iv_api_right);
        if (!com.bigdata.xinhuanufang.utils.Config.USER_HEAD.equals("1")) {
//            Bitmap bmp = BitmapFactory.decodeFile(com.bigdata.xinhuanufang.utils.Config.USER_HEAD);
            if (!com.bigdata.xinhuanufang.utils.Config.RELEVANCE_TYPE.equals("3")) {
                x.image().bind(iv_api_right,com.bigdata.xinhuanufang.utils.Config.USER_HEAD);
            }else {
                x.image().bind(iv_api_right, com.bigdata.xinhuanufang.utils.Config.ip + com.bigdata.xinhuanufang.utils.Config.USER_HEAD);
//            Picasso.with(getActivity()).load(Config.ip+Config.USER_HEAD).into(iv_fragMine_photo);
            }
//            iv_api_right.setImageBitmap(bmp);
        }
//        Intent intent=getIntent();
//        String path=intent.getStringExtra("imagepath");
//        if (path!=null) {
//            Bitmap bmp = BitmapFactory.decodeFile(path);
//            iv_api_right.setImageBitmap(bmp);
//        }


        my_secondname = (TextView) findViewById(R.id.my_secondname);
        //性别
        tv_sex = (TextView) findViewById(R.id.tv_sex);
        tv_sex.setText(com.bigdata.xinhuanufang.utils.Config.USER_SEX);
        //签名
        my_qianming = (TextView) findViewById(R.id.my_qianming);
        //手机号
        user_number = (TextView) findViewById(R.id.user_number);
        user_number.setText(com.bigdata.xinhuanufang.utils.Config.USER_TEL);
        //用户id
        mine_user_id = (TextView) findViewById(R.id.mine_user_id);
        mine_user_id.setText(com.bigdata.xinhuanufang.utils.Config.userID);
        my_qianming1 = (TextView) findViewById(R.id.my_qianming);
        my_qianming1.setText(com.bigdata.xinhuanufang.utils.Config.USER_SIGN);
        my_secondname1 = (TextView) findViewById(R.id.my_secondname);
        my_secondname1.setText(com.bigdata.xinhuanufang.utils.Config.USER_USERNAME);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("imagepath", com.bigdata.xinhuanufang.utils.Config.USER_HEAD);
        intent.putExtra("sign", com.bigdata.xinhuanufang.utils.Config.USER_SIGN);
        intent.putExtra("sex", com.bigdata.xinhuanufang.utils.Config.USER_SEX);
        intent.putExtra("name", com.bigdata.xinhuanufang.utils.Config.USER_USERNAME);
        setResult(1, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
            case R.id.rl_api_secondName:
                //修改昵称
                Intent intent = new Intent(PersonalInfoActivity.this,
                        ChangeNameActivity.class);
                intent.putExtra("name",my_secondname.getText());
                startActivityForResult(intent, 2);
                break;
            case R.id.rl_api_signName:
                //签名
                Intent signIntent = new Intent(PersonalInfoActivity.this,
                        ChangeSignNameActivity.class);
                String a=my_qianming.getText().toString();
                signIntent.putExtra("sign",my_qianming.getText());
                startActivityForResult(signIntent, 3);
                break;
            case R.id.rl_api_manageAdress:
                Intent adressIntent = new Intent(PersonalInfoActivity.this,
                        GoodsAdressActivity.class);
                startActivity(adressIntent);
                break;
            case R.id.rl_api_sex:
                //性别
                showPopupWindow(view, "性别");
                break;
            case R.id.my_header_image:
                // 设置头像
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
            case R.id.popupwindow_sex_nv:
                popupwindow_sex_nv.setBackgroundColor(Color.RED);
                popupwindow_sex_nan.setBackgroundColor(Color.WHITE);
                tv_sex.setText("女");
                com.bigdata.xinhuanufang.utils.Config.USER_SEX="女";
                break;
            case R.id.popupwindow_sex_nan:
                popupwindow_sex_nan.setBackgroundColor(Color.RED);
                popupwindow_sex_nv.setBackgroundColor(Color.WHITE);
                tv_sex.setText("男");
                com.bigdata.xinhuanufang.utils.Config.USER_SEX="男";
                break;
            case R.id.rl_api_thirdMethod:
//                showPopupWindow(view, "绑定");
                break;
            default:
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


    /*
     * 显示popuWindow的方法
     */
    private void showPopupWindow(View v, String type) {
        // TODO Auto-generated method stub
        View contentView = null;
        if (type.equals("性别")) {
            // 一个自定义的布局，作为显示的内容
            contentView = LayoutInflater.from(PersonalInfoActivity.this).inflate(
                    R.layout.popu_sex, null);
            //查找布局里面的控件
            popupwindow_sex_nv = (LinearLayout) contentView.findViewById(R.id.popupwindow_sex_nv);
            popupwindow_sex_nan = (LinearLayout) contentView.findViewById(R.id.popupwindow_sex_nan);
            popupwindow_sex_nv.setOnClickListener(this);
            popupwindow_sex_nan.setOnClickListener(this);

        }
        if (type.equals("绑定")) {
            contentView = LayoutInflater.from(PersonalInfoActivity.this).inflate(
                    R.layout.popu_correlation_account, null);
        }

        final PopupWindow popupWindow = new PopupWindow(contentView,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                // TODO Auto-generated method stub
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.juxingbejing));
        // 设置显示的位置
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(v);

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
                showImage(imagePath);


                c.close();
                //设置头像的
                setHeaderImage(headerImage);
            }


        }
        if (resultCode == 2 && data != null) {
            //昵称
            String name = data.getStringExtra("name");
            my_secondname.setText(name);
        }
        if (resultCode == 3 && data != null) {
            //签名
            String name = data.getStringExtra("content");
            my_qianming.setText(name);
        }


    }

    private void setHeaderImage(String headerImage) {
        if (!headerImage.isEmpty()) {
            //先向服务器获取图像网络地址
            //http://115.28.69.240/boxing/app/upload_img.php?pic=11111
            com.bigdata.xinhuanufang.utils.Config.USER_hosted_HEAD = headerImage;
            //本地的路径/system/media/Pre-loaded/Pictures/Picture_01_Greenery.jpg


//            String mime = MimeTypeMap.getSingleton()
//                    .getMimeTypeFromExtension("png");
//            params.setMultipart(true);
//            params.addBodyParameter("img", headerImage, mime);
            //以文件流上传图片

//            InputStream in = null;
//            ByteArrayOutputStream out = null;
//            try {
//                in = new BufferedInputStream(new FileInputStream(new File(headerImage)));
//                out = new ByteArrayOutputStream();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            bs = new byte[16 * 1024];
//            int len;
//// 读取数据，并进行处理
//            try {
//                while ((len = in.read(bs)) != -1) {
//                    for (int i = 0; i < len; i++) {
//                        out.write(bs[i]);
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }finally {
//                if (in != null) {
//                    try {
//                        in.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (out!=null) {
//                    try {
//                        out.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
            Bitmap bms = BitmapFactory.decodeFile(headerImage);
            Bitmap bm=ratio(bms,120,120);
            String img=bitmapToBase64(bm);

            //http://115.28.69.240/boxing/app/upload_img.php?pic=11111
            //com.bigdata.xinhuanufang.utils.Config.ip+ com.bigdata.xinhuanufang.utils.Config.app+

            String url=com.bigdata.xinhuanufang.utils.Config.ip+ com.bigdata.xinhuanufang.utils.Config.app+"/upload_img.php?";
            RequestParams params = new RequestParams(url);
            params.addBodyParameter("img", img);
//            params.addBodyParameter("img", new File(headerImage));
            x.http().post(params, new CommonCallback<String>() {

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
                                                    showToast("头像设置成功");
                                                }
                                                if (fail.equals(code)) {
                                                    showToast("头像设置失败,请检查网络");
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
        newOpts.inPreferredConfig = Config.RGB_565;
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

    public  String bitmapToBase64(Bitmap bm) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    private void showImage(String imaePath) {

        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        yuanjiao_picture.setVisibility(View.VISIBLE);
        iv_api_right.setVisibility(View.GONE);
//        iv_api_right.setImageBitmap(toRoundBitmap(bm));
        yuanjiao_picture.setImageBitmap(toRoundBitmap(bm));
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
                Config.ARGB_8888);
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
        p.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        // canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, p);
        return backgroundBm;
    }

    /**
     * 去掉图片的后缀名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }




}
