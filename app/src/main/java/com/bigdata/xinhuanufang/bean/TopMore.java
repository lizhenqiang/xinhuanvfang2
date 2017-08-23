package com.bigdata.xinhuanufang.bean;

public class TopMore {
	/**
	 * "news_id":"1",
	 *  "news_pic":"/uploads/20170218211820234022.jpg",
	 * "news_title":"热热订单",
	 *  "news_title2":"222222222222222222",
	 * "news_date":"444444444"
	 */

	private String id;
	private String news_pic;
	private String news_title;
	private String news_title2;
	private String news_date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNews_pic() {
		return news_pic;
	}

	public void setNews_pic(String news_pic) {
		this.news_pic = news_pic;
	}

	public String getNews_title() {
		return news_title;
	}

	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}

	public String getNews_title2() {
		return news_title2;
	}

	public void setNews_title2(String news_title2) {
		this.news_title2 = news_title2;
	}

	public String getNews_date() {
		return news_date;
	}

	public void setNews_date(String news_date) {
		this.news_date = news_date;
	}

	public TopMore(String id, String news_pic, String news_title,
				   String news_title2, String news_date) {
		super();
		this.id = id;
		this.news_pic = news_pic;
		this.news_title = news_title;
		this.news_title2 = news_title2;
		this.news_date = news_date;
	}

}
