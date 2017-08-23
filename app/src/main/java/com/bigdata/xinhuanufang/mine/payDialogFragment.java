package com.bigdata.xinhuanufang.mine;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;

import com.bigdata.xinhuanufang.R;

public class payDialogFragment extends DialogFragment implements
		OnClickListener {
	private static volatile payDialogFragment dialog = null;
	private CheckBox weixin_pay;// 微信支付
	private CheckBox zhifubao_pay;// 微信支付
	private payType paytype;




	/**
	 * 单例模式：创建 Fragment：
	 *
	 * @return
	 */
	public static payDialogFragment getInstance() {
		if (dialog == null) {
			synchronized (payDialogFragment.class) {
				if (dialog == null) {
					dialog = new payDialogFragment();
				}
			}
		}
		return dialog;
	}

	/**
	 * 使用 onCreateView 创建 对话框的样式 使用自定义视图
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		/**
		 * 先设置 无标题样式的 对话框
		 */
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

		View view = inflater
				.inflate(R.layout.pay_type_dialog, container, false);
		weixin_pay = (CheckBox) view.findViewById(R.id.weixin_pay);
		zhifubao_pay = (CheckBox) view.findViewById(R.id.zhifubao_pay);
		weixin_pay.setOnClickListener(this);
		zhifubao_pay.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.weixin_pay:
				weixin_pay.setChecked(true);
				zhifubao_pay.setChecked(false);
				paytype.setPayType("微信支付");
				break;
			case R.id.zhifubao_pay:
				weixin_pay.setChecked(false);
				zhifubao_pay.setChecked(true);
				paytype.setPayType("支付宝支付");
				break;

			default:
				break;
		}
	}
	public void setPay(payType paytype){
		this.paytype=paytype;
	}

	public interface payType{
		void setPayType(String type);
	}
}
