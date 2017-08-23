package com.bigdata.xinhuanufang.wxapi;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.mine.GoldenGloveActivity;
import com.bigdata.xinhuanufang.mine.MyShoppingMall;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.shoppingconfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.x;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{

	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

	private IWXAPI api;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 0:
//					finish();
					weixin_pay_result.setText("支付失败");
					weixin_pay_result.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							finish();
						}
					});
					break;
				case 1:
//					finish();
					weixin_pay_result.setText("支付成功");
					weixin_pay_result.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (shoppingconfig.weixin_pay_result_type.equals("1")) {
								//该订单是用户充值金手套的,支付完成后跳转到金手套交易
								Intent intent=new Intent(WXPayEntryActivity.this, GoldenGloveActivity.class);
								startActivity(intent);
							}else if (shoppingconfig.weixin_pay_result_type.equals("0")) {
								//该订单是用户商城购买商品的
								Intent intent = new Intent(WXPayEntryActivity.this, MyShoppingMall.class);
								intent.putExtra("isfukuan", "yifukuan");
								startActivity(intent);
							}
						}
					});
					break;
			}
		}
	};
    private TextView shoping_address_name;
    private TextView shoping_address_tel;
    private TextView shoping_address_content;
    private ImageView weixin_pay_result_pic;
    private TextView weixin_pay_result_shopping_name;
    private TextView weixin_pay_result_fenlei;
    private TextView weixin_pay_result_price;
    private TextView weixin_pay_result_num;
    private Button weixin_pay_result;
	private LinearLayout shopping_weixin_dizhilan;
	private LinearLayout shopping_weixin_details;
	private Button weixin_chongzhi_jinshoutao_number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pay_result);
		ImageView iv_itt_back= (ImageView) findViewById(R.id.iv_itt_back);
		iv_itt_back.setVisibility(View.GONE);
		TextView tv_itt_title= (TextView) findViewById(R.id.tv_itt_title);
		tv_itt_title.setText("支付结果");
		ImageView iv_itt_collection= (ImageView) findViewById(R.id.iv_itt_collection);
		iv_itt_collection.setVisibility(View.GONE);
		ImageView iv_itt_delete= (ImageView) findViewById(R.id.iv_itt_delete);
		iv_itt_delete.setVisibility(View.GONE);
		TextView tv_itt_save= (TextView) findViewById(R.id.tv_itt_save);
		tv_itt_save.setVisibility(View.GONE);
		api = WXAPIFactory.createWXAPI(this, Config.APP_ID);
		api.handleIntent(getIntent(), this);
        //展现出商品信息
		shopping_weixin_dizhilan = (LinearLayout) findViewById(R.id.shopping_weixin_dizhilan);
		shopping_weixin_details = (LinearLayout) findViewById(R.id.shopping_weixin_details);
        //收货人姓名
        shoping_address_name = (TextView) findViewById(R.id.shoping_address_name);
        //收货人的电话
        shoping_address_tel = (TextView) findViewById(R.id.shoping_address_tel);
        //收货地址
        shoping_address_content = (TextView) findViewById(R.id.shoping_address_content);
        //商品图片
        weixin_pay_result_pic = (ImageView) findViewById(R.id.weixin_pay_result_pic);
        //商品名称
        weixin_pay_result_shopping_name = (TextView) findViewById(R.id.weixin_pay_result_shopping_name);
        //商品属性分类
        weixin_pay_result_fenlei = (TextView) findViewById(R.id.weixin_pay_result_fenlei);
        //上平价格
        weixin_pay_result_price = (TextView) findViewById(R.id.weixin_pay_result_price);
        //商品数量
        weixin_pay_result_num = (TextView) findViewById(R.id.weixin_pay_result_num);
        //支付结果
        weixin_pay_result = (Button) findViewById(R.id.weixin_pay_result);

		//充值金手套的户数量
		weixin_chongzhi_jinshoutao_number = (Button) findViewById(R.id.weixin_chongzhi_jinshoutao_number);
		if (shoppingconfig.weixin_pay_result_type.equals("1")) {
			shopping_weixin_dizhilan.setVisibility(View.GONE);
			shopping_weixin_details.setVisibility(View.GONE);
		}else if (shoppingconfig.weixin_pay_result_type.equals("0")) {
			weixin_chongzhi_jinshoutao_number.setVisibility(View.GONE);
		}
		weixin_chongzhi_jinshoutao_number.setText("充值金手套"+shoppingconfig.jinshoutao_number+" 个");
        shoping_address_name.setText(shoppingconfig.shoping_address_name);
        shoping_address_tel.setText(shoppingconfig.shoping_address_tel);
        shoping_address_content.setText(shoppingconfig.shoping_address_content);
        weixin_pay_result_shopping_name.setText(shoppingconfig.weixin_pay_result_shopping_name);
        weixin_pay_result_fenlei.setText(shoppingconfig.weixin_pay_result_fenlei);
        weixin_pay_result_price.setText(shoppingconfig.weixin_pay_result_price);
        x.image().bind(weixin_pay_result_pic,Config.ip+shoppingconfig.weixin_pay_result_pic);

    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
        Log.e("eeee",req.getType() + "");
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	@SuppressLint("LongLogTag")
	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if (resp.errCode==-2) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(this);
//				builder.setTitle(R.string.app_tip);
//				builder.setMessage("取消支付");
//				builder.show();

				Message msg1=Message.obtain();
				msg1.what=0;
				handler.sendEmptyMessageDelayed(0,2000);
			}
			if (resp.errCode==0) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(this);
//				builder.setTitle(R.string.app_tip);
//				builder.setMessage("支付成功");
//				builder.show();

				Message msg2=Message.obtain();
				msg2.what=1;
				handler.sendEmptyMessageDelayed(1,2000);
			}
		}
	}
}