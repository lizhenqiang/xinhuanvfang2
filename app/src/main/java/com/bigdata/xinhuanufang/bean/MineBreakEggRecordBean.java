package com.bigdata.xinhuanufang.bean;

public class MineBreakEggRecordBean {
	/**
	 *  "egg2_id":"5",
	 "egg2_userid":"1",
	 "egg2_num":"100金手套",
	 "egg2_date":"1490963770"
	 */
	private String egg2_id;
	private String egg2_userid;
	private String egg2_num;
	private String egg2_date;
	public String getEgg2_id() {
		return egg2_id;
	}
	public void setEgg2_id(String egg2_id) {
		this.egg2_id = egg2_id;
	}
	public String getEgg2_userid() {
		return egg2_userid;
	}
	public void setEgg2_userid(String egg2_userid) {
		this.egg2_userid = egg2_userid;
	}
	public String getEgg2_num() {
		return egg2_num;
	}
	public void setEgg2_num(String egg2_num) {
		this.egg2_num = egg2_num;
	}
	public String getEgg2_date() {
		return egg2_date;
	}
	public void setEgg2_date(String egg2_date) {
		this.egg2_date = egg2_date;
	}
	public MineBreakEggRecordBean(String egg2_id, String egg2_userid,
								  String egg2_num, String egg2_date) {
		this.egg2_id = egg2_id;
		this.egg2_userid = egg2_userid;
		this.egg2_num = egg2_num;
		this.egg2_date = egg2_date;
	}


}
