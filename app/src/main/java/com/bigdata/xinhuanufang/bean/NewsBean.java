package com.bigdata.xinhuanufang.bean;

import java.util.List;

/**
 * list1是头条资讯；list2是赛事新闻
 news_id编号
 news_pic列表小图
 news_title标题
 news_title2副标题
 news_date发布日期。时间戳

 * @author weiyu$
 *
 */
public class NewsBean {
	private List<List1> mList1;

	private List<List2> mList2;
	public NewsBean(List<List1> mList1,List<List2> mList2){
		this.mList1 = mList1;
		this.mList2 = mList2;
	}
	public List<List1> getmList1() {
		return mList1;
	}
	public void setmList1(List<List1> mList1) {
		this.mList1 = mList1;
	}
	public List<List2> getmList2() {
		return mList2;
	}
	public void setmList2(List<List2> mList2) {
		this.mList2 = mList2;
	}




}
