package com.bigdata.xinhuanufang.mine;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.bean.mysuperdreamdaishixian;
import com.bigdata.xinhuanufang.game.bean.AddJinShouTao;

import org.json.JSONObject;

import java.util.List;

public class RechargeJinShouTao extends DialogFragment implements
		OnClickListener {
	private List<AddJinShouTao> dataList;// 比赛数据
	private int index;
	private List<mysuperdreamdaishixian> dataList2;
	private static volatile RechargeJinShouTao dialog = null;
	private TextView jinshoutao_count;
	private ImageButton jinshoutao_zengjia;
	private ImageButton jinshoutao_jianshao;
	private int count = 0;// 这个数据应该从网络获取,每个条目的金手套数量都不一样
	private payType paytype;
	private Button tijiao_dream_quxiao;
	private Button tijiao_dream_queding;
	private int tag=0;


	/**
	 * 单例模式：创建 Fragment：
	 *
	 * @return
	 */
	public static RechargeJinShouTao getInstance() {
		if (dialog == null) {
			synchronized (payDialogFragment.class) {
				if (dialog == null) {
					dialog = new RechargeJinShouTao();
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

		View view = inflater.inflate(R.layout.my_super_dream_chongzhi,
				container, false);
		jinshoutao_count = (EditText) view.findViewById(R.id.jinshoutao_count);
		jinshoutao_zengjia = (ImageButton) view
				.findViewById(R.id.jinshoutao_zengjia);
		jinshoutao_jianshao = (ImageButton) view
				.findViewById(R.id.jinshoutao_jianshao);
		tijiao_dream_quxiao = (Button) view.findViewById(R.id.tijiao_dream_quxiao);
		tijiao_dream_queding = (Button) view.findViewById(R.id.tijiao_dream_queding);
		jinshoutao_count.setText(count + "");
		jinshoutao_zengjia.setOnClickListener(this);
		tijiao_dream_quxiao.setOnClickListener(this);
		tijiao_dream_queding.setOnClickListener(this);
		 jinshoutao_jianshao.setOnClickListener(this);
		jinshoutao_count.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					count= Integer.parseInt(jinshoutao_count.getText().toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tijiao_dream_quxiao:
				dismiss();
				break;
			case R.id.tijiao_dream_queding:
				if (tag>count) {
					Toast.makeText(getActivity(), "金手套数量不能再少了", Toast.LENGTH_SHORT).show();
					return;
				}
				paytype.setPayType(count);
				JSONObject object = new JSONObject();
//				try {
//					object.put("think_id", dataList.get(0).getThink_id());
//					object.put("thinkjoin_id", "7");
//					object.put("think_percent", dataList.get(0).getThink_percent());
//					object.put("thinkjoin_gloves", dataList.get(0)
//							.getThinkjoin_gloves());
//					object.put("thinkjoin_bili", dataList.get(0)
//							.getThinkjoin_bili());
//					System.out.println(object);
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				x.http().get(
//						new RequestParams(Config.ip + Config.app
//								+ "/think_submit.php?user_id=" + Config.userID
//								+ "&data=" + object), new CommonCallback<String>() {
//							@Override
//							public void onCancelled(CancelledException arg0) {
//							}
//							@Override
//							public void onError(Throwable arg0, boolean arg1) {
//							}
//							@Override
//							public void onFinished() {
//							}
//							@Override
//							public void onSuccess(String arg0) {
//								System.out.println("超级梦想提交:"+arg0);
//							}
//						});
				break;

			case R.id.jinshoutao_zengjia:
				count = count + 10;
				jinshoutao_count.setText(count + "");

				// 由于没有减少的接口,所以先注释掉减少的按钮,在这里只可以增加
				// http://115.28.69.240/boxing/app/think_submit.php?user_id=1&data=[]
				// [{"think_id":2,"thinkjoin_id":7,"think_percent":"16","thinkjoin_gloves":4000,"thinkjoin_bili":"21.00000"},{"think_id":1,"thinkjoin_id":5,"think_percent":"90","thinkjoin_gloves":5000,"thinkjoin_bili":"90.000000"}]
				// 进行了网络的请求
				//在这里应该当dialog关闭后,通知fragment刷新数据
				// 拼接json数据

				break;
			case R.id.jinshoutao_jianshao:
				if (count <= tag) {
					Toast.makeText(getActivity(), "数量再不能少了", Toast.LENGTH_SHORT).show();
					return;
				}
				count = count - 10;
				jinshoutao_count.setText(count + "");
				break;

			default:
				break;
		}
	}

	public void setPay(payType paytype) {
		this.paytype = paytype;
	}

	public interface payType {
		void setPayType(int type);
	}

	public void setData(List<AddJinShouTao> jinshoutaoList,
						List<mysuperdreamdaishixian> dataList2, int position) {
		this.dataList = jinshoutaoList;
		this.index = position;
		this.dataList2 = dataList2;
		count=Integer.parseInt(dataList2.get(position).getThinkjoin_gloves());
		System.out.println("押注的金手套"+count+"索引"+position);
		tag=count;
	}

}