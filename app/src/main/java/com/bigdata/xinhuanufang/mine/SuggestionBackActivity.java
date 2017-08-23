package com.bigdata.xinhuanufang.mine;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.SuggestImageAdapter;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.store.picture.ImgFileListActivity;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static android.R.attr.path;

/**
 * @author asus 意见反馈Activity
 */
public class SuggestionBackActivity extends BaseActivity implements
        OnClickListener {
    private EditText suggest_message;
    private Button enter_send_suddest;
    private ImageView add_image;
    private GridView listView;
    private String message;
    private ArrayList<String> listfile = new ArrayList<String>();
    // 调用系统相册-选择图片
    private static final int IMAGE = 1;
    private TextView yijianfankui_call;
    public boolean ispicturesuggessup=false;
    private String img;

    @Override
    public int getView() {
        // TODO Auto-generated method stub
        return R.layout.activity_suggestionback;
    }

    @Override
    public void initView() {
        // TODO Auto-generated method stub
        super.initView();
        super.setTitle("意见反馈");
        super.setGone();
        setBack();
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        message = intent.getStringExtra("message");
        suggest_message = (EditText) findViewById(R.id.suggest_message);
        enter_send_suddest = (Button) findViewById(R.id.enter_send_suddest);
        listView = (GridView) findViewById(R.id.image_list);
        //拨打电话进行意见反馈
        yijianfankui_call = (TextView) findViewById(R.id.yijianfankui_call);
        yijianfankui_call.setOnClickListener(this);
        add_image = (ImageView) findViewById(R.id.add_image);
        add_image.setOnClickListener(this);
        enter_send_suddest.setOnClickListener(this);
        if (bundle != null) {
            if (bundle.getStringArrayList("files") != null) {
                listfile = bundle.getStringArrayList("files");
                listView.setVisibility(View.VISIBLE);
                SuggestImageAdapter arryAdapter = new SuggestImageAdapter(
                        SuggestionBackActivity.this, listfile);
                listView.setAdapter(arryAdapter);
            }
        }
        suggest_message.setText(message);
        suggest_message.setSelection(suggest_message.getText().length());
    }

    public String bitmapToBase64(Bitmap bm) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enter_send_suddest:
                // 获取文字建议
                message = suggest_message.getText().toString();
                // 获取图片的路劲
                if (Config.isphoto) {
                    StringBuilder build = new StringBuilder();
                    if (listfile.size() != 0) {
                        for (int i = 0; i < listfile.size(); i++) {
                            if (listfile.size() - 1 == i) {
                                build.append(listfile.get(i));
                            } else {
                                build.append(listfile.get(i) + ",");
                            }

                        }
                    }


                    //先上传图片
                    Bitmap bms = BitmapFactory.decodeFile(build.toString());
                    Bitmap bm=ratio(bms,120,120);
                    img = bitmapToBase64(bm);
                }


                if (Config.isphoto) {
                    RequestParams params = new RequestParams(Config.ip + Config.app + "/upload_img.php?");
                    String url=Config.ip + Config.app + "/upload_img.php?";
                    params.addBodyParameter("img", img);
                    x.http().post(params, new CommonCallback<String>() {

                        @Override
                        public void onSuccess(String s) {
                            try {
                                JSONObject json = new JSONObject(s);
                                String code = json.getString("code");
                                String path = json.getString("path");
                                if (code.equals("1")) {
                                    Config.jianyi=path;
                                    ispicturesuggessup = true;
                                    // http://115.28.69.240/boxing/app/feedback.php?user_id=1&pic=111&content=9999


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


                //建议
                RequestParams param = new RequestParams(Config.ip + Config.app + "/feedback.php?");

//				String mime = MimeTypeMap.getSingleton()
//						.getMimeTypeFromExtension("png");
//				params.setMultipart(true);

                param.addBodyParameter("user_id", Config.userID);
                if (Config.isphoto) {

                    param.addBodyParameter("pic", Config.ip + path);
                }
                param.addBodyParameter("content", message);
                x.http().post(param, new CommonCallback<String>() {

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
                        try {
                            JSONObject json = new JSONObject(arg0);
                            String code = json.getString("code");
                            if (code.equals("1")) {
                                if (ispicturesuggessup) {
                                    showToast("感谢你的建议");
                                    finish();
                                }

                            } else if (code.equals("0")) {
                                showToast("反馈失败");
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

                break;
            case R.id.add_image:
                // 打开系统相册

                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 120);
                        return;
                    } else {
                        //打开相册
                        chise(v);
                        Config.isphoto = true;
                    }
                } else {
                    chise(v);
                    Config.isphoto = true;
                }

                break;
            case R.id.yijianfankui_call:
                call();
                break;

            default:
                break;
        }

    }

    public void chise(View v) {

        Intent intent = new Intent();
        message = suggest_message.getText().toString();
        intent.putExtra("message", message);
        intent.setClass(this, ImgFileListActivity.class);
        startActivity(intent);
        finish();
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

    public void call() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Config.phonenumber));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 120);
            return;
        }
        startActivity(intent);
    }
}
