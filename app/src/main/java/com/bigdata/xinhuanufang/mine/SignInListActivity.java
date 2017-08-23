package com.bigdata.xinhuanufang.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.custom.SignCalendar;
import com.bigdata.xinhuanufang.game.bean.SignBean;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignInListActivity extends Activity {

	private String date = null;// 设置默认选中的日期 格式为 “2014-04-05” 标准DATE格式
	private TextView popupwindow_calendar_month;
	private SignCalendar calendar;
	private Date[] days;
	private Button btn_signIn;
	private String data;
	private List<String> list = new ArrayList<String>(); // 设置标记列表
	private List<SignBean> dataList;
	private TextView tv_award_jinshoutao;
	private TextView tv_get_award;
	private String jinshoutao_number;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 100:
					dataList=(List<SignBean>) msg.obj;
					setData(dataList);
					break;

				default:
					break;
			}
		};
	};
	private ImageView qiandaobang_back;
	private boolean isget=false;
	private int years;
	private int month;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_signinlist);
		popupwindow_calendar_month = (TextView) findViewById(R.id.popupwindow_calendar_month);
//		btn_signIn = (Button) findViewById(R.id.btn_signIn);
		tv_award_jinshoutao=(TextView) findViewById(R.id.tv_award_jinshoutao);
		//领取奖励
		tv_get_award=(TextView) findViewById(R.id.tv_get_award);
		//返回键
		qiandaobang_back = (ImageView) findViewById(R.id.qiandaobang_back);
		qiandaobang_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		calendar = (SignCalendar) this.findViewById(R.id.sign_calendar);
		popupwindow_calendar_month.setText(calendar.getCalendarYear() + "年"
				+ calendar.getCalendarMonth() + "月");
		//获取当前的月份和年份
		if (calendar.getCalendarMonth() < 10) {
			data=null;
			data = calendar.getCalendarYear() + "0" + calendar.getCalendarMonth();
		} else {
			data=null;
			data = calendar.getCalendarYear() + calendar.getCalendarMonth() + "";
		}
		//签到显现
		Date today = calendar.getThisday();
		calendar.addMark(today, 0);
		calendar.setCalendarDayBgColor(today, R.drawable.bg_sign_today);



		if (null != date) {

			years = Integer.parseInt(date.substring(0, date.indexOf("-")));
			month = Integer.parseInt(date.substring(date.indexOf("-") + 1,
					date.lastIndexOf("-")));
			popupwindow_calendar_month.setText(years + "年" + month + "月");

			calendar.showCalendar(years, month);

			calendar.setCalendarDayBgColor(date,
					R.drawable.calendar_date_focused);
		}

//		list.add("2015-11-10");
//		list.add("2015-11-02");
//		calendar.addMarks(list, 0);

//		btn_signIn.setOnClickListener(new OnClickListener() {
//			// 进行签到的操作
//			@Override
//			public void onClick(View v) {
//				Date today = calendar.getThisday();
//				calendar.addMark(today, 0);
//				// HashMap<String, Integer> bg = new HashMap<String, Integer>();
//				calendar.setCalendarDayBgColor(today, R.drawable.bg_sign_today);
//				btn_signIn.setText("今日已签，明日继续");
//				btn_signIn.setEnabled(false);
//			}
//		});

			//获取金手套的奖励
			tv_get_award.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//http://115.28.69.240/boxing/app/calendar_go.php?user_id=1&d=201703&sum=6
					x.http().get(new RequestParams(Config.ip + Config.app + "/calendar_go.php?user_id=" + Config.userID + "&d=" + data + "&sum=" + jinshoutao_number), new CommonCallback<String>() {
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
							try {
								JSONObject json = new JSONObject(arg0);
								String code = json.getString("code");
								if (code.equals("1")) {
									Toast.makeText(SignInListActivity.this, "领取成功", Toast.LENGTH_SHORT).show();
									tv_get_award.setVisibility(View.GONE);
									tv_award_jinshoutao.setText("奖励金手套:" + 0);
									isget = true;
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				}
			});

		dataList=new ArrayList<SignBean>();
		// 获取网络数据
		getNetWorkData();
		// 监听所选中的日期
		// calendar.setOnCalendarClickListener(new OnCalendarClickListener() {
		//
		// public void onCalendarClick(int row, int col, String dateFormat) {
		// int month = Integer.parseInt(dateFormat.substring(
		// dateFormat.indexOf("-") + 1,
		// dateFormat.lastIndexOf("-")));
		//
		// if (calendar.getCalendarMonth() - month == 1//跨年跳转
		// || calendar.getCalendarMonth() - month == -11) {
		// calendar.lastMonth();
		//
		// } else if (month - calendar.getCalendarMonth() == 1 //跨年跳转
		// || month - calendar.getCalendarMonth() == -11) {
		// calendar.nextMonth();
		//
		// } else {
		// list.add(dateFormat);
		// calendar.addMarks(list, 0);
		// calendar.removeAllBgColor();
		// calendar.setCalendarDayBgColor(dateFormat,
		// R.drawable.calendar_date_focused);
		// date = dateFormat;//最后返回给全局 date
		// }
		// }
		// });

		// 监听当前月份
		calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
			public void onCalendarDateChanged(int year, int month) {
				popupwindow_calendar_month.setText(year + "年" + month + "月");

			}
		});
	}

	protected void setData(List<SignBean> dataList) {
		// TODO Auto-generated method stub
		jinshoutao_number=null;
		jinshoutao_number=dataList.get(0).getSum();
		tv_award_jinshoutao.setText("奖励金手套:"+dataList.get(0).getSum());
		Intent intent=new Intent();
		if (isget) {
			intent.putExtra("number",dataList.get(0).getSum());
        }else{
			intent.putExtra("number","0");
		}
		setResult(2,intent);
		days=new Date[31];
		Toast.makeText(this, ""+dataList.get(0).getDate(), Toast.LENGTH_SHORT).show();
		String riqi=dataList.get(0).getDate();
		String[] zhuanhuan=riqi.split(",");
		List<String> ls=new ArrayList<>();
		Calendar c = Calendar.getInstance();//
		int mYear = c.get(Calendar.YEAR); // 获取当前年份
		int mMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
		System.out.println("显示的日期"+mYear+"年"+mMonth+"月");
		for (int i = 0; i < zhuanhuan.length; i++) {
			if ((mMonth<10)) {
				if (Integer.parseInt(zhuanhuan[i])<10) {
					ls.add(mYear+"-0"+mMonth+"-0"+zhuanhuan[i]);
				}else if (Integer.parseInt(zhuanhuan[i])>=10) {
					ls.add(mYear+"-0"+mMonth+"-"+zhuanhuan[i]);
				}

				System.out.println("显示的日期"+mYear+"-0"+mMonth+"-"+zhuanhuan[i]);
			}else if ((mMonth>=10)){
				if (Integer.parseInt(zhuanhuan[i])<10) {
					ls.add(mYear + "-" + mMonth + "-0" + zhuanhuan[i]);
				}else if (Integer.parseInt(zhuanhuan[i])>=10) {
					ls.add(mYear + "-" + mMonth + "-" + zhuanhuan[i]);
				}
				System.out.println("显示的日期"+mYear+"-"+mMonth+"-"+zhuanhuan[i]);
			}



		}
		for (int i = 0; i < ls.size(); i++) {
			System.out.println("日期"+ls.get(i));
		}
/**
 * list.add("2015-11-10");
 list.add("2015-11-02");
 calendar.addMarks(list, 0);
 */

		calendar.addMarks(ls, 0);
//		calendar.setCalendarDaysBgColor(ls, R.drawable.bg_sign_today);

	}

	private void getNetWorkData() {
		// http://115.28.69.240/boxing/app/calendar_show.php?user_id=1&d=201703
		System.out.println("签到"+Config.ip + Config.app
				+ "/calendar_show.php?user_id=" + Config.userID + "&d="
				+ data);
		x.http().get(
				new RequestParams(Config.ip + Config.app
						+ "/calendar_show.php?user_id=" + Config.userID + "&d="
						+ data), new CommonCallback<String>() {
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
						try {
							JSONObject json=new JSONObject(arg0);
							String date=json.getString("date");
							String sum=json.getString("sum");
							if (sum.equals("0")) {
								tv_get_award.setVisibility(View.GONE);
							}
							dataList.add(new SignBean(date, sum));
							Message msg=Message.obtain();
							msg.what=100;
							msg.obj=dataList;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

}
