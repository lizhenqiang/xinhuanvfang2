package com.bigdata.xinhuanufang.bean;

public class GameDetailsInfo {
	/**
	 *  "video_id":"10",
	 "video_playeruserid":"1",
	 "video_playeruserid2":"2",
	 "video_time":"1490869800",
	 "video_title":"男子300公斤重量级拳赛",
	 "video_success":"1",
	 "video_pic":"/uploads/20170218184037196160.jpg",
	 "video_content":"霍利菲尔德同时也被评为当年最佳拳手。两人早在90年代初就达成了比赛协议,但由于泰森坐牢,那场比赛取消了。泰森在赛前根本没有把挑战者霍利菲尔德放眼里...",
	 "video_liveguessid":"15",
	 "player_namea":"泰森",
	 "player_heada":"/uploads/2198921992912.jpg",
	 "player_nameb":"霍利菲尔德",
	 "player_headb":"/uploads/32456435334564.jpg",
	 "player_success":"泰森"
	 "is_coll":0
	 */
	private String video_id;
	private String video_playeruserid;
	private String video_playeruserid2;
	private String video_time;
	private String video_title;
	private String video_success;
	private String video_pic;
	private String video_content;
	private String video_liveguessid;
	private String player_namea;
	private String player_heada;
	private String player_nameb;
	private String player_headb;
	private String player_success;
	private String is_coll;
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public String getVideo_playeruserid() {
		return video_playeruserid;
	}
	public void setVideo_playeruserid(String video_playeruserid) {
		this.video_playeruserid = video_playeruserid;
	}
	public String getVideo_playeruserid2() {
		return video_playeruserid2;
	}
	public void setVideo_playeruserid2(String video_playeruserid2) {
		this.video_playeruserid2 = video_playeruserid2;
	}
	public String getVideo_time() {
		return video_time;
	}
	public void setVideo_time(String video_time) {
		this.video_time = video_time;
	}
	public String getVideo_title() {
		return video_title;
	}
	public void setVideo_title(String video_title) {
		this.video_title = video_title;
	}
	public String getVideo_success() {
		return video_success;
	}
	public void setVideo_success(String video_success) {
		this.video_success = video_success;
	}
	public String getVideo_pic() {
		return video_pic;
	}
	public void setVideo_pic(String video_pic) {
		this.video_pic = video_pic;
	}
	public String getVideo_content() {
		return video_content;
	}
	public void setVideo_content(String video_content) {
		this.video_content = video_content;
	}
	public String getVideo_liveguessid() {
		return video_liveguessid;
	}
	public void setVideo_liveguessid(String video_liveguessid) {
		this.video_liveguessid = video_liveguessid;
	}
	public String getPlayer_namea() {
		return player_namea;
	}
	public void setPlayer_namea(String player_namea) {
		this.player_namea = player_namea;
	}
	public String getPlayer_heada() {
		return player_heada;
	}
	public void setPlayer_heada(String player_heada) {
		this.player_heada = player_heada;
	}
	public String getPlayer_nameb() {
		return player_nameb;
	}
	public void setPlayer_nameb(String player_nameb) {
		this.player_nameb = player_nameb;
	}
	public String getPlayer_headb() {
		return player_headb;
	}
	public void setPlayer_headb(String player_headb) {
		this.player_headb = player_headb;
	}
	public String getPlayer_success() {
		return player_success;
	}
	public void setPlayer_success(String player_success) {
		this.player_success = player_success;
	}
	public String getIs_coll() {
		return is_coll;
	}
	public void setIs_coll(String is_coll) {
		this.is_coll = is_coll;
	}
	public GameDetailsInfo(String video_id, String video_playeruserid,
						   String video_playeruserid2, String video_time, String video_title,
						   String video_success, String video_pic, String video_content,
						   String video_liveguessid, String player_namea, String player_heada,
						   String player_nameb, String player_headb, String player_success,
						   String is_coll) {
		this.video_id = video_id;
		this.video_playeruserid = video_playeruserid;
		this.video_playeruserid2 = video_playeruserid2;
		this.video_time = video_time;
		this.video_title = video_title;
		this.video_success = video_success;
		this.video_pic = video_pic;
		this.video_content = video_content;
		this.video_liveguessid = video_liveguessid;
		this.player_namea = player_namea;
		this.player_heada = player_heada;
		this.player_nameb = player_nameb;
		this.player_headb = player_headb;
		this.player_success = player_success;
		this.is_coll = is_coll;
	}


}
