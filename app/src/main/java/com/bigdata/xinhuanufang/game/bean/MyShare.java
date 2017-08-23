package com.bigdata.xinhuanufang.game.bean;

public class MyShare {
/**
 * "share_id":"3",
            "share_userid":"1",
            "share_liveguessid":"15",
            "share_pic":"/uploads/20170218184037196160.jpg",
            "share_playernamea":"̩ɭ",
            "share_playernameb":"�����ƶ���",
            "share_title":"����300����������ȭ��",
            "share_time":"1489128748
 */
	
	private String share_id;
	private String share_userid;
	private String share_liveguessid;
	private String share_pic;
	private String share_playernamea;
	private String share_playernameb;
	private String share_title;
	private String share_time;
	public String getShare_id() {
		return share_id;
	}
	public void setShare_id(String share_id) {
		this.share_id = share_id;
	}
	public String getShare_userid() {
		return share_userid;
	}
	public void setShare_userid(String share_userid) {
		this.share_userid = share_userid;
	}
	public String getShare_liveguessid() {
		return share_liveguessid;
	}
	public void setShare_liveguessid(String share_liveguessid) {
		this.share_liveguessid = share_liveguessid;
	}
	public String getShare_pic() {
		return share_pic;
	}
	public void setShare_pic(String share_pic) {
		this.share_pic = share_pic;
	}
	public String getShare_playernamea() {
		return share_playernamea;
	}
	public void setShare_playernamea(String share_playernamea) {
		this.share_playernamea = share_playernamea;
	}
	public String getShare_playernameb() {
		return share_playernameb;
	}
	public void setShare_playernameb(String share_playernameb) {
		this.share_playernameb = share_playernameb;
	}
	public String getShare_title() {
		return share_title;
	}
	public void setShare_title(String share_title) {
		this.share_title = share_title;
	}
	public String getShare_time() {
		return share_time;
	}
	public void setShare_time(String share_time) {
		this.share_time = share_time;
	}
	public MyShare(String share_id, String share_userid,
			String share_liveguessid, String share_pic,
			String share_playernamea, String share_playernameb,
			String share_title, String share_time) {
		this.share_id = share_id;
		this.share_userid = share_userid;
		this.share_liveguessid = share_liveguessid;
		this.share_pic = share_pic;
		this.share_playernamea = share_playernamea;
		this.share_playernameb = share_playernameb;
		this.share_title = share_title;
		this.share_time = share_time;
	}
	
}
