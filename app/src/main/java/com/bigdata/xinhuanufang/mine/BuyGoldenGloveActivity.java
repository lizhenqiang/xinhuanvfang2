package com.bigdata.xinhuanufang.mine;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.base.BaseActivity;
import com.bigdata.xinhuanufang.mine.payDialogFragment.payType;
import com.bigdata.xinhuanufang.store.Pays;
import com.bigdata.xinhuanufang.store.zhifubao.ZhiFuBaoPay;
import com.bigdata.xinhuanufang.utils.Config;
import com.bigdata.xinhuanufang.utils.shoppingconfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 充值金手套
 *
 * @author weiyu$
 *
 */
public class BuyGoldenGloveActivity extends BaseActivity implements
		OnClickListener {
	private TextView detail; // 明细
	private LinearLayout taocan1;
	private LinearLayout taocan2;
	private LinearLayout taocan3;
	private LinearLayout taocan4;
	private LinearLayout taocan5;
	private LinearLayout taocan6;
	private TextView jinshoutao_balance;
	private EditText jinshoutao_count;
	private LinearLayout zhifu_type;
	private Button liji_zhifu;
	private List<LinearLayout> taocanList;
	private Iterator<LinearLayout> it; // 集合遍历器
	private double price = 10;
	private ImageView pay_image;//支付方式的图片
	private TextView pay_text;//支付方式的名称
	private String paytype="1";
	private String outtradeno;
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case  0:
					outtradeno= (String) msg.obj;
					if (paytype.equals("0")) {
						//调用微信进行金手套充值支付
						shoppingconfig.jinshoutao_number= String.valueOf((int)(price*10));
						shoppingconfig.weixin_pay_result_type="1";
						String jine=String.valueOf(price);
						StringBuffer sb=new StringBuffer();
						String sendjiage="";
						for (int i = 0; i < jine.length(); i++) {
							if (jine.charAt(i)=='.') {
								sendjiage= sb.toString();
							}else{
								sb.append(jine.charAt(i));
							}

						}
						String s=sendjiage+"00";
						// new Pays(ConfirmOrderActivity.this, tv_sli_productName.getText().toString(), money, "0",outTradeNo);
						new Pays(BuyGoldenGloveActivity.this, "充值金手套",sendjiage+"00" ,"1",outtradeno);
						//new Pays(ConfirmOrderActivity.this, tv_sli_productName.getText().toString(), tv_sli_price.getText().toString(), "0",outTradeNo);
					}else if (paytype.equals("1")) {
						//调用支付宝进行充值金手套的支付
						shoppingconfig.jinshoutao_number= String.valueOf((int)(price*10));
						Intent intent = new Intent(BuyGoldenGloveActivity.this, ZhiFuBaoPay.class);
						//拿到当前商品的必要参数,商品的名称,价格,详细信息
						//商品的名称
						intent.putExtra("shoppingname", "充值金手套");
						//商品的价格
//                        intent.putExtra("shoppingprice",tv_sli_price.getText());
						intent.putExtra("shoppingprice",price );
						intent.putExtra("tradeNo", outtradeno);
						intent.putExtra("type","1");
						shoppingconfig.weixin_pay_result_type="1";
						startActivity(intent);
					}
					break;
			}
		}
	};
	@Override
	public int getView() {
		// TODO Auto-generated method stub
		return R.layout.activity_buygolve;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		setTitle("购买金手套");
		setTextRight("明细");
		setBack();
		taocanList = new ArrayList<LinearLayout>();
		Intent intent=getIntent();
		String tv_fragMine_myGloveNum=intent.getStringExtra("tv_fragMine_myGloveNum");


		// 套餐
		taocan1 = (LinearLayout) findViewById(R.id.taocan1);
		taocan2 = (LinearLayout) findViewById(R.id.taocan2);
		taocan3 = (LinearLayout) findViewById(R.id.taocan3);
		taocan4 = (LinearLayout) findViewById(R.id.taocan4);
		taocan5 = (LinearLayout) findViewById(R.id.taocan5);
		taocan6 = (LinearLayout) findViewById(R.id.taocan6);
		taocanList.add(taocan1);
		taocanList.add(taocan2);
		taocanList.add(taocan3);
		taocanList.add(taocan4);
		taocanList.add(taocan5);
		taocanList.add(taocan6);
		// 金手套余额
		jinshoutao_balance = (TextView) findViewById(R.id.jinshoutao_balance);
		jinshoutao_balance.setText(tv_fragMine_myGloveNum);
		// 输入购买金手套的个数
		jinshoutao_count = (EditText) findViewById(R.id.jinshoutao_count);
		jinshoutao_count.setText("100");
		jinshoutao_count.setSelection(jinshoutao_count.getText().length());
		// 选择购买金手套的支付方式,选择支付方式的时候弹出一个对话框
		zhifu_type = (LinearLayout) findViewById(R.id.zhifu_type);
		// 立即支付
		liji_zhifu = (Button) findViewById(R.id.liji_zhifu);
		//支付方式的图片
		pay_image= (ImageView) findViewById(R.id.pay_image);
		pay_text= (TextView) findViewById(R.id.pay_text);
		taocan1.setOnClickListener(this);
		taocan2.setOnClickListener(this);
		taocan3.setOnClickListener(this);
		taocan4.setOnClickListener(this);
		taocan5.setOnClickListener(this);
		taocan6.setOnClickListener(this);
		jinshoutao_balance.setOnClickListener(this);

		zhifu_type.setOnClickListener(this);
		liji_zhifu.setOnClickListener(this);
		jinshoutao_count.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
                String num=jinshoutao_count.getText().toString();
                if (num.equals("")) {
                    num="0";
                }
				jinshoutao_count.setSelection(jinshoutao_count.getText().length());
				price = Integer.parseInt(num)/10;
				liji_zhifu.setText("￥" + price + " 立即支付");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		detail = (TextView) findViewById(R.id.tv_itt_save);
		detail.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tv_itt_save:
				Intent intent = new Intent(BuyGoldenGloveActivity.this,
						DetailActivity.class);
				startActivity(intent);
				break;
			case R.id.taocan1:
				taocan1.setBackgroundResource(R.drawable.yuanjiao_1);
				taocan2.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan3.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan4.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan5.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan6.setBackgroundResource(R.drawable.yuanjiao_0);
				price = 10;
				jinshoutao_count.setText("100");
				liji_zhifu.setText("￥" + price + " 立即支付");
				break;
			case R.id.taocan2:
				taocan1.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan2.setBackgroundResource(R.drawable.yuanjiao_1);
				taocan3.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan4.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan5.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan6.setBackgroundResource(R.drawable.yuanjiao_0);
				price = 20;
				jinshoutao_count.setText("200");
				liji_zhifu.setText("￥" + price + " 立即支付");
				break;
			case R.id.taocan3:
				taocan1.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan2.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan3.setBackgroundResource(R.drawable.yuanjiao_1);
				taocan4.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan5.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan6.setBackgroundResource(R.drawable.yuanjiao_0);
				price = 30;
				jinshoutao_count.setText("300");
				liji_zhifu.setText("￥" + price + " 立即支付");
				break;
			case R.id.taocan4:
				taocan1.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan2.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan3.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan4.setBackgroundResource(R.drawable.yuanjiao_1);
				taocan5.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan6.setBackgroundResource(R.drawable.yuanjiao_0);
				price = 40;
				jinshoutao_count.setText("400");
				liji_zhifu.setText("￥" + price + " 立即支付");
				break;
			case R.id.taocan5:
				taocan1.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan2.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan3.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan4.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan5.setBackgroundResource(R.drawable.yuanjiao_1);
				taocan6.setBackgroundResource(R.drawable.yuanjiao_0);
				price = 50;
				jinshoutao_count.setText("500");
				liji_zhifu.setText("￥" + price + " 立即支付");
				break;
			case R.id.taocan6:
				taocan1.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan2.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan3.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan4.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan5.setBackgroundResource(R.drawable.yuanjiao_0);
				taocan6.setBackgroundResource(R.drawable.yuanjiao_1);
				price = 60;
				jinshoutao_count.setText("600");
				liji_zhifu.setText("￥" + price + " 立即支付");
				break;
			case R.id.liji_zhifu:
				// 获取到购买金手套的数量,然后发起支付请求
				// http://115.28.69.240/boxing/app/user_glovesadd.php?user_id=1&num=200
				jinShouTaoPay();
				//走完充值之后,要将充值后的金手套数量就行同步
				break;
			case R.id.zhifu_type:
				//选择支付方式
				payDialogFragment editNameDialog = payDialogFragment.getInstance();
				editNameDialog.show(getFragmentManager(), "PayDialog");
				editNameDialog.setPay(new payType() {

					@Override
					public void setPayType(String type) {
						// TODO Auto-generated method stub
						if (type.equals("微信支付")) {
							paytype="0";
							pay_image.setImageResource(R.drawable.zhifu_weixin);
							pay_text.setText("微信支付");
						}
						if (type.equals("支付宝支付")) {
							paytype="1";
							pay_image.setImageResource(R.drawable.zhifu_zhifubao);
							pay_text.setText("支付宝支付");
						}
					}
				});
				break;
			default:
				break;
		}
	}

	private void jinShouTaoPay() {
		String a= jinshoutao_count.getText().toString();
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/user_glovesadd.php?user_id=" + Config.userID+"&num="+jinshoutao_count.getText()+"&flag="+paytype),
				new Callback.CommonCallback<String>() {
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
						String success="1";
						String fail="0";
						try {
							JSONObject json=new JSONObject(arg0);
							String code=json.getString("code");
							if (success.equals(code)) {
								outtradeno = json.getString("outtradeno");
								Message msg=Message.obtain();
								msg.obj=outtradeno;
								msg.what=0;
								handler.sendMessage(msg);
							}
							if (fail.equals(code)) {
								showToast("充值失败,请检查网络");
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
						}
					}
				});


	}
}
