package com.bigdata.xinhuanufang.bean;

public class GameFighterBeanHonors {
	/**
	 *  "honors_id":"1",
	 "honors_playerid":"1",
	 "honors_content":"K1世界冠军"
	 */
	private String honors_id;
	private String honors_playerid;
	private String honors_content;
	public String getHonors_id() {
		return honors_id;
	}
	public void setHonors_id(String honors_id) {
		this.honors_id = honors_id;
	}
	public String getHonors_playerid() {
		return honors_playerid;
	}
	public void setHonors_playerid(String honors_playerid) {
		this.honors_playerid = honors_playerid;
	}
	public String getHonors_content() {
		return honors_content;
	}
	public void setHonors_content(String honors_content) {
		this.honors_content = honors_content;
	}
	public GameFighterBeanHonors(String honors_id, String honors_playerid,
								 String honors_content) {
		this.honors_id = honors_id;
		this.honors_playerid = honors_playerid;
		this.honors_content = honors_content;
	}

}
