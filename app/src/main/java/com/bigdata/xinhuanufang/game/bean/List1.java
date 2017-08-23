package com.bigdata.xinhuanufang.game.bean;

public class List1 {
	/**
	 * ���������������Ѷ
	 */
	private String list;
	private String news_id;
	private String news_pic;
	private String news_title;
	private String news_title2;
	private String news_date;
	
	
	
	public String getList() {
		return list;
	}
	public void setList(String list) {
		this.list = list;
	}
	public String getNews_id() {
		return news_id;
	}
	public void setNews_id(String news_id) {
		this.news_id = news_id;
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
	public List1(String news_id, String news_pic, String news_title,
			String news_title2, String news_date) {
//		this.list=list;
		this.news_id = news_id;
		this.news_pic = news_pic;
		this.news_title = news_title;
		this.news_title2 = news_title2;
		this.news_date = news_date;
	}
}
