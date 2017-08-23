package com.bigdata.xinhuanufang.game.bean;

public class GameGuanZhuFighterHonors {
	/**
	 * "honors_id":"5",
                "honors_content":"T1����ھ�"
	 */
	private String honors_id;
	private String honors_content;
	public String getHonors_id() {
		return honors_id;
	}
	public void setHonors_id(String honors_id) {
		this.honors_id = honors_id;
	}
	public String getHonors_content() {
		return honors_content;
	}
	public void setHonors_content(String honors_content) {
		this.honors_content = honors_content;
	}
	public GameGuanZhuFighterHonors(String honors_id, String honors_content) {
		this.honors_id = honors_id;
		this.honors_content = honors_content;
	}
	
	
}
