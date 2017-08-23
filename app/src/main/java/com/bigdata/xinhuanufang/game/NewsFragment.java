package com.bigdata.xinhuanufang.game;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bigdata.xinhuanufang.R;
import com.bigdata.xinhuanufang.adapter.GameNewsAdapter;
import com.bigdata.xinhuanufang.adapter.TopNewsAdapter;
import com.bigdata.xinhuanufang.game.bean.List1;
import com.bigdata.xinhuanufang.game.bean.List2;
import com.bigdata.xinhuanufang.game.bean.NewsBean;
import com.bigdata.xinhuanufang.utils.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/*
 * 新闻资讯Fragment
 * */
public class NewsFragment extends Fragment implements OnClickListener {
	private ListView topNewsLV; // 头条资讯ListView
	private TopNewsAdapter TopNewsAdapter;// 头条资讯adapter
	public String TAG = "success";

	private static final int TYPE_NET = 1;
	private ListView gameNewsLV;// 赛事新闻ListView
	private LinearLayout moreNewsLL; // 更多头条资讯布局
	private LinearLayout morematchNewsLL; // 更多比赛新闻
	private GameNewsAdapter gamenewsAdapter;
	private List<NewsBean> list2 = new ArrayList<NewsBean>();
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case TYPE_NET:
					list2 = (List<NewsBean>) msg.obj;
					System.out.println("集合的大小:" + list2.size());
					TopNewsAdapter = new TopNewsAdapter(getActivity(), list2);
					topNewsLV.setAdapter(TopNewsAdapter);

					gamenewsAdapter=new GameNewsAdapter(getActivity(), list2);
//
					gameNewsLV.setAdapter(gamenewsAdapter);
					break;
				default:
					break;
			}

		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_news, container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		topNewsLV = (ListView) view.findViewById(R.id.lv_fragN_topNews);
		gameNewsLV = (ListView) view.findViewById(R.id.lv_fragN_gameNews);
		moreNewsLL = (LinearLayout) view.findViewById(R.id.ll_fragN_more);
		morematchNewsLL = (LinearLayout) view
				.findViewById(R.id.lv_fragN_matchNews);
		/*
		 * moreNewsLL.setOnClickListener(this);
		 * gameNewsLV.setOnClickListener(this);
		 */
		topNewsLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("news_id", list2.get(0).getmList1().get(position).getNews_id());
				Intent intent=new Intent(getActivity(),Information.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		gameNewsLV.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle=new Bundle();
				bundle.putString("news_id", list2.get(0).getmList2().get(position).getNews_id());
				Intent intent=new Intent(getActivity(),Information.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		moreNewsLL.setOnClickListener(this);
		morematchNewsLL.setOnClickListener(this);
		x.http().get(new RequestParams(Config.ip+Config.app+"/news_list.php"), new CommonCallback<String>() {


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
				if (TextUtils.isEmpty(arg0)) {
					return;
				} else {
					try {
						JSONObject json1 = new JSONObject(arg0);

						JSONArray list = json1.getJSONArray("list1");

						List<NewsBean> record = new ArrayList<NewsBean>();
						List<List1> mList1 = new ArrayList<List1>();
						List<List2> mList2 = new ArrayList<List2>();

						for (int i = 0; i < list.length(); i++) {
							JSONObject json = list.getJSONObject(i);

							String news_id = json.getString("news_id");
							String news_pic = json.getString("news_pic");
							String news_title = json.getString("news_title");
							String news_title2 = (json.getString("news_title2"));
							String news_date = json.getString("news_date");
							mList1.add(new List1(news_id,news_pic,news_title,news_title2,news_date));
						}
						JSONArray match = json1.getJSONArray("list2");
						for (int i = 0; i < match.length(); i++) {
							JSONObject json = match.getJSONObject(i);

							String news_id = json.getString("news_id");
							String news_pic = json.getString("news_pic");
							String news_title = json.getString("news_title");
							String news_title2 = (json.getString("news_title2"));
							String news_date = json.getString("news_date");
							mList2.add(new List2(news_id,news_pic,news_title,news_title2,news_date));
						}

						record.add(new NewsBean(mList1, mList2));


						Message msg = Message.obtain();
						msg.what = TYPE_NET;
						msg.obj = record;
						mHandler.sendMessage(msg);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			// 点击更多跳转至头条资讯页面
			case R.id.ll_fragN_more:
				Intent TopNewsintent = new Intent(getActivity(),
						TopNewsActivity.class);
				startActivity(TopNewsintent);

				break;
			case R.id.lv_fragN_matchNews:
				Intent matchmore = new Intent(getActivity(), MoreMatch.class);
				startActivity(matchmore);
				break;

			default:
				break;
		}
	}

}
