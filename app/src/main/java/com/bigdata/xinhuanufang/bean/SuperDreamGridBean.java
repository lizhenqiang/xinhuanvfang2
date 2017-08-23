package com.bigdata.xinhuanufang.bean;

import java.util.List;

public class SuperDreamGridBean {
	/**
	 *   "think_id":"2",
	 "think_shopid":"1",
	 "think_attrid":"15",
	 "think_percent":"2",
	 "think_title":"拳王背心",
	 "think_pic":"/uploads/20170223135215.jpg",
	 "think_price":"1000",
	 */
	private String think_id;
	private String think_shopid;
	private String think_attrid;
	private String think_percent;
	private String think_title;
	private String think_pic;
	private String think_price;
	private List<SuperDreamGridPicBean> pic;
	private String is_add;
	public String getThink_id() {
		return think_id;
	}
	public void setThink_id(String think_id) {
		this.think_id = think_id;
	}
	public String getThink_shopid() {
		return think_shopid;
	}
	public void setThink_shopid(String think_shopid) {
		this.think_shopid = think_shopid;
	}
	public String getThink_attrid() {
		return think_attrid;
	}
	public void setThink_attrid(String think_attrid) {
		this.think_attrid = think_attrid;
	}
	public String getThink_percent() {
		return think_percent;
	}
	public void setThink_percent(String think_percent) {
		this.think_percent = think_percent;
	}
	public String getThink_title() {
		return think_title;
	}
	public void setThink_title(String think_title) {
		this.think_title = think_title;
	}
	public String getThink_pic() {
		return think_pic;
	}
	public void setThink_pic(String think_pic) {
		this.think_pic = think_pic;
	}
	public String getThink_price() {
		return think_price;
	}
	public void setThink_price(String think_price) {
		this.think_price = think_price;
	}
	public List<SuperDreamGridPicBean> getPic() {
		return pic;
	}
	public void setPic(List<SuperDreamGridPicBean> pic) {
		this.pic = pic;
	}
	public String getIs_add() {
		return is_add;
	}
	public void setIs_add(String is_add) {
		this.is_add = is_add;
	}
	public SuperDreamGridBean(String think_id, String think_shopid,
							  String think_attrid, String think_percent, String think_title,
							  String think_pic, String think_price,
							  List<SuperDreamGridPicBean> pic, String is_add) {
		this.think_id = think_id;
		this.think_shopid = think_shopid;
		this.think_attrid = think_attrid;
		this.think_percent = think_percent;
		this.think_title = think_title;
		this.think_pic = think_pic;
		this.think_price = think_price;
		this.pic = pic;
		this.is_add = is_add;
	}


}
