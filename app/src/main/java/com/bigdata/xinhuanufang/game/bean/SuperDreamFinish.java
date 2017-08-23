package com.bigdata.xinhuanufang.game.bean;

public class SuperDreamFinish {
/**
 * "think_id":"3",
            "thinkjoin_id":"8",
            "think_title":"ƻ���ֻ�",
            "think_pic":"/uploads/20170223135215.jpg",
            "thinkjoin_gloves":"4000",
            "count":"1",
            "flag":2
 */
	private String think_id;
	private String thinkjoin_id;
	private String think_title;
	private String think_pic;
	private String thinkjoin_gloves;
	private String count;
	private String flag;
	public String getThink_id() {
		return think_id;
	}
	public void setThink_id(String think_id) {
		this.think_id = think_id;
	}
	public String getThinkjoin_id() {
		return thinkjoin_id;
	}
	public void setThinkjoin_id(String thinkjoin_id) {
		this.thinkjoin_id = thinkjoin_id;
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
	public String getThinkjoin_gloves() {
		return thinkjoin_gloves;
	}
	public void setThinkjoin_gloves(String thinkjoin_gloves) {
		this.thinkjoin_gloves = thinkjoin_gloves;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public SuperDreamFinish(String think_id, String thinkjoin_id,
			String think_title, String think_pic, String thinkjoin_gloves,
			String count, String flag) {
		this.think_id = think_id;
		this.thinkjoin_id = thinkjoin_id;
		this.think_title = think_title;
		this.think_pic = think_pic;
		this.thinkjoin_gloves = thinkjoin_gloves;
		this.count = count;
		this.flag = flag;
	}
	
	
	
}
